package com.fin.best.bestfin.api.app.domain.user.service

import com.fin.best.bestfin.api.app.domain.auth.service.AuthCodeService
import com.fin.best.bestfin.api.app.domain.cert.dao.KcbCertDao
import com.fin.best.bestfin.api.app.domain.user.dao.UserDao
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCertHistory
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCredit
import com.fin.best.bestfin.api.app.domain.cert.service.CreditService
import com.fin.best.bestfin.api.app.domain.user.entity.User
import com.fin.best.bestfin.api.app.domain.user.entity.User3rdAccountConnection
import com.fin.best.bestfin.api.component.model.ConditionalResult
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserSignUpParams
import com.fin.best.bestfin.api.component.error.exception.*
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserJoinService
@Autowired constructor(
        private val authCodeService: AuthCodeService,
        private val creditService: CreditService,
        private val userDao: UserDao,
        private val kcbCertDao: KcbCertDao,
        private val passwordEncoder: PasswordEncoder
) {
    @Throws(UserIdDuplicateException::class,
            UserEmailDuplicateException::class,
            InvalidCertAuthCodeException::class,
            UserCertException::class,
            UserCiDiDuplicateException::class,
            UserInvalidInputPasswordException::class,
            IllegalStateException::class)
    fun signUpId(signUp: UserSignUpParams): ConditionalResult {
        if (checkJoinUserCount() < 30) {
            // 아이디 중복 체크
            if (isAlreadyExistId(userId = signUp.userId))
                throw UserIdDuplicateException()
            // 이메일 중복 체크
            if (isAlreadyExistEmail(email = signUp.email))
                throw UserEmailDuplicateException()

            // 입력한 비밀번호 불일치
            if (!StringUtils.equals(signUp.password, signUp.passwordConfirm))
                throw UserInvalidInputPasswordException()

            // 본인인증 정보 확인
            val certAuthCodeData = authCodeService.fetchCertAuthCode(signUp.certAuthCode)
            val cert = verifyCert(certAuthCodeData.certNo)

            // CI, DI 기준 중복 가입자 확인
            if (verifyDuplicateUser(ci = cert.ci, di = cert.di))
                throw UserCiDiDuplicateException()

            // 저장된 신용 정보 가져오기
            val credit = kcbCertDao.fetchKcbCreditUserNo(cert.kcbUserNo)

            // 사용자 저장
            userDao.storeUser(setUserEntity(
                    userId = signUp.userId,
                    password = signUp.password,
                    email = signUp.email,
                    serviceAgrement = signUp.serviceAgrement,
                    personalInfoAgreement = signUp.personalInfoAgreement,
                    cert = cert,
                    credit = credit
            ))

            return return ConditionalResult.Builder()
                    .success(true)
                    .build()
        } else {
            return ConditionalResult.Builder()
                    .success(false)
                    .reason(400, "Overflow Joined User count 30")
                    .build()
        }
    }

    @Throws(InvalidJoinAuthCodeException::class,
            UserSignUp3rdExistException::class,
            UserCiDiDuplicateException::class,
            UserIdDuplicateException::class,
            UserEmailDuplicateException::class,
            IllegalStateException::class)
    fun signUp3rd(signUp: UserSignUpParams): ConditionalResult {
        if (checkJoinUserCount() < 30) {
            val joinAuthCodeData = authCodeService.fetchJoinAuthCode(signUp.joinAuthCode)
            val accountNo = joinAuthCodeData.accountNo

            // 본인인증 정보 확인
            val certAuthCodeData = authCodeService.fetchCertAuthCode(signUp.certAuthCode)
            val cert = verifyCert(certAuthCodeData.certNo)

            // CI, DI 기준 중복 가입자 확인
            val joinedUser = userDao.fetchUserByCiAndDi(ci = cert.ci, di = cert.di)

            // 카카오 회원 가입시 기존 사용자 있으면 통합
            if (joinedUser.isPresent) {
                val existingUser3rdAccountConnection = userDao.fetchUser3rdAccountConnectionByAccountNo(accountNo)
                // 이미 사용자에 연결 되어 있으면 통합 불가
                if (existingUser3rdAccountConnection.isPresent)
                    throw UserSignUp3rdExistException()

                // 사용자로 제3자 인증 계정 연결
                val user3rdAccountConnection = storeUser3rdAccountConnection(joinedUser.get(), accountNo)

                return return ConditionalResult.Builder()
                        .success(true)
                        .build()
            } else {
                throw UserCiDiDuplicateException()
            }

            // 아이디 중복 체크
            if (isAlreadyExistId(userId = signUp.userId))
                throw UserIdDuplicateException()
            // 이메일 중복 체크
            if (isAlreadyExistEmail(email = signUp.email))
                throw UserEmailDuplicateException()

            // 저장된 신용 정보 가져오기
            val credit = kcbCertDao.fetchKcbCreditUserNo(cert.kcbUserNo)

            // 사용자 저장
            val user = userDao.storeUser(setUserEntity(
                    userId = signUp.userId,
                    password = signUp.password,
                    email = signUp.email,
                    serviceAgrement = signUp.serviceAgrement,
                    personalInfoAgreement = signUp.personalInfoAgreement,
                    cert = cert,
                    credit = credit
            ))

            // 사용자로 제3자 인증 계정 연결
            val user3rdAccountConnection = storeUser3rdAccountConnection(user, accountNo)

            return return ConditionalResult.Builder()
                    .success(true)
                    .build()
        } else {
            return ConditionalResult.Builder()
                    .success(false)
                    .reason(400, "Overflow Joined User count 30")
                    .build()
        }
    }

    private fun storeUser3rdAccountConnection(user: User, accountNo: Long): User3rdAccountConnection {
        return userDao.storeUser3rdAccountConnection(
                User3rdAccountConnection(userNo = user.userNo, accountNo = accountNo)
        )
    }

    fun checkJoinUserCount(): Long {
        //return userMapper.checkJoinUserCount(param);
        return userDao.countJoinUserCertCount()
    }

    fun isAlreadyExistId(userId: String): Boolean {
        return userDao.existUserId(userId)
    }

    fun isAlreadyExistEmail(email: String): Boolean {
        return userDao.existEmail(email)
    }

    @Throws(UserCertException::class)
    fun verifyCert(certNo: Long): KcbCertHistory {
        val cert = kcbCertDao.fetchCertHisroty(certNo).orElseThrow { throw UserCertException() }
        if (cert.authStatus != AppConst.User.AuthStatus.OK)
            throw UserCertException()
        return cert
    }

    fun verifyDuplicateUser(ci: String, di: String): Boolean {
        return userDao.fetchUserByCiAndDi(ci, di).isPresent
    }

    private fun setUserEntity(
            userId: String,
            password: String,
            email: String,
            serviceAgrement: AppConst.YN,
            personalInfoAgreement: AppConst.YN,
            cert: KcbCertHistory,
            credit: KcbCredit
    ): User {
        cert.authStatus = AppConst.User.AuthStatus.SIGN_UP
        cert.issueAt = LocalDateTime.now()
        kcbCertDao.storeKcbCertHisroty(cert)

        return User(
                userId = userId,
                password = passwordEncoder.encode(password),
                status = AppConst.User.UserStatus.NOR,
                email = email,
                serviceAgreement = serviceAgrement,
                piAgreement = personalInfoAgreement,
                certYn = when (cert.certNo) {
                    0 -> AppConst.YN.N
                    else -> AppConst.YN.Y
                },
                certNo = cert.certNo!!,
                creditNo = credit.creditNo!!,
                ci = cert.ci,
                di = cert.di,
                name = cert.authName,
                birth = cert.birth.toString(),
                gender = when (cert.gender) {
                    AppConst.Kcb.Gender.M -> AppConst.Gender.MALE
                    AppConst.Kcb.Gender.F -> AppConst.Gender.FEMALE
                    AppConst.Kcb.Gender.U -> AppConst.Gender.UNKNOWN
                },
                mobileNumber = cert.mobileNumber,
                annualIncome = creditService.fetchKcbPersonalInfo(credit)
        )
    }
}
