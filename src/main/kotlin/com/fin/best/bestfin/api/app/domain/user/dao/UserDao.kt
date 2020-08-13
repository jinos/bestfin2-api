package com.fin.best.bestfin.api.app.domain.user.dao

import com.fin.best.bestfin.api.app.domain.user.entity.*
import com.fin.best.bestfin.api.app.domain.user.repository.AdminUserRepository
import com.fin.best.bestfin.api.app.domain.user.repository.User3rdAccountConnectionRepository
import com.fin.best.bestfin.api.app.domain.user.repository.User3rdAccountRepository
import com.fin.best.bestfin.api.app.domain.user.repository.UserRepository
import com.fin.best.bestfin.api.component.constants.AppConst
import com.querydsl.jpa.impl.JPAQueryFactory
import net.sf.ehcache.search.Direction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.Resource

@Repository
class UserDao
@Autowired constructor(
        private val userRepository: UserRepository,
        private val user3rdAccountRepository: User3rdAccountRepository,
        private val user3rdAccountConnectionRepository: User3rdAccountConnectionRepository,
        private val adminUserRepository: AdminUserRepository,
        private val userRepositorySupport: UserRepositorySupport
) {
    fun countJoinUserCertCount(): Long {
        return userRepository.count()
    }

    fun existUserId(userId: String): Boolean {
        return userRepository.existsByUserId(userId)
    }

    fun existEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun fetchUserByCiAndDi(ci: String, di: String): Optional<User> {
        return userRepository.findByCiAndDi(ci, di)
    }

    fun storeUser(user: User): User {
        return userRepository.save(user)
    }

    fun fetchUser(userNo: Long): Optional<User> {
        return userRepository.findById(userNo)
    }

    fun deleteUser(user: User) {
        return userRepository.delete(user)
    }

    fun fetchUser3rdAccount(accountNo: Long): Optional<User3rdAccount> {
        return user3rdAccountRepository.findById(accountNo)
    }

    fun deleteUser3rdAccount(account: User3rdAccount) {
        user3rdAccountRepository.delete(account)
    }

    fun fetchUser3rdAccountConnections(userNo: Long): List<User3rdAccountConnection> {
        return user3rdAccountConnectionRepository.findByUserNo(userNo)
    }

    fun fetchUser3rdAccountConnectionByAccountNo(accountNo: Long): Optional<User3rdAccountConnection> {
        return user3rdAccountConnectionRepository.findByAccountNo(accountNo)
    }

    fun deleteUser3rdAccountConnection(accountConnection: User3rdAccountConnection) {
        user3rdAccountConnectionRepository.delete(accountConnection)
    }

    fun storeUser3rdAccountConnection(user3rdAccountConnection: User3rdAccountConnection): User3rdAccountConnection {
        return user3rdAccountConnectionRepository.save(user3rdAccountConnection)
    }

    fun fetchUserByUserId(userId: String): Optional<User> {
        return userRepository.findByUserId(userId)
    }

    fun countAdminManagers(searchValue: String): Long {
        return adminUserRepository.countByAdmNoNotAndAdmIdContainingOrAdmNmContainingAndUseYn(1L, searchValue, searchValue, AppConst.YN.Y)
    }

    fun fetchAdminManagers(searchValue: String, pageNo: Int, itemsPerPage: Int): Page<AdminUser> {
        val pageable = PageRequest.of(pageNo - 1, itemsPerPage)
        return userRepositorySupport.findByAdmNoNotAndAdmIdContainingOrAdmNmContainingAndUseYnOrderByAdmNoDesc(1L, searchValue, searchValue, AppConst.YN.Y, pageable)


        //return adminUserRepository.findByAdmNoNotAndAdmIdContainingOrAdmNmContainingAndUseYnOrderByAdmNoDesc(1L, searchValue, searchValue, AppConst.YN.Y, pageable)
    }
}

@Repository
class UserRepositorySupport(
        @Resource(name = "jpaQueryFactory")
        val query: JPAQueryFactory
) : QuerydslRepositorySupport(AdminUser::class.java) {
    fun findByAdmNoNotAndAdmIdContainingOrAdmNmContainingAndUseYnOrderByAdmNoDesc(
            admNo: Long, admId: String, admNm: String, useYn: AppConst.YN, pageable: Pageable
    ): Page<AdminUser> {
        var adminUser = QAdminUser.adminUser

        val query = query.selectFrom(adminUser)
                .where(
                        adminUser.admNo.ne(admNo)
                                .and(adminUser.useYn.eq(useYn))
                                .and((adminUser.admId.like("%${admId}%").or(adminUser.admNm.like("%${admNm}%"))))
                )
                .orderBy(adminUser.admNo.asc())

        return PageImpl(
                querydsl!!.applyPagination(pageable, query).fetch(),
                pageable,
                query.fetchCount()
        )
    }

}
