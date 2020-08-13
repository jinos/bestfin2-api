package com.fin.best.bestfin.api.app.gateway.v1.user.param

import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.component.constants.AppRegexp
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UserSignUpParams(
        // [필수] ID (영문 숫자 혼용 5자이상 20자 이내)
        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.UserId)
        val userId: String,

        // [필수] 비밀번호(영문, 숫자, 특수 혼용 6~12자 이내)
        //       비밀번호 최소 하나의 문자 + 하나의 숫자 + 하나의 특수 문자 포함, 최소 6자리
        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val password: String,

        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val passwordConfirm: String,

        // 이메일
        @get:Pattern(regexp = AppRegexp.User.Email)
        val email: String,

        // 이용 약관 동의
        val serviceAgrement: AppConst.YN,

        // 개인정보 수집 및 이용 동의
        var personalInfoAgreement: AppConst.YN,

        @get:NotBlank
        var joinAuthCode: String,

        @get:NotBlank
        val certAuthCode: String,

        @get:NotBlank
        val type: AppConst.User.ConfirmType,

        @get:NotBlank
        val value: String
)

data class UserSignUp3rdParams(
        // [필수] ID (영문 숫자 혼용 5자이상 20자 이내)
        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.UserId)
        val userId: String,

        // [필수] 비밀번호(영문, 숫자, 특수 혼용 6~12자 이내)
        //       비밀번호 최소 하나의 문자 + 하나의 숫자 + 하나의 특수 문자 포함, 최소 6자리
        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val password: String,

        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val passwordConfirm: String,

        // 이메일
        @get:Pattern(regexp = AppRegexp.User.Email)
        val email: String,

        // 이용 약관 동의
        val serviceAgrement: AppConst.YN,

        // 개인정보 수집 및 이용 동의
        var personalInfoAgreement: AppConst.YN,

        @get:NotBlank
        var joinAuthCode: String,

        @get:NotBlank
        val certAuthCode: String,

        @get:NotBlank
        val type: AppConst.User.ConfirmType,

        @get:NotBlank
        val value: String
)
