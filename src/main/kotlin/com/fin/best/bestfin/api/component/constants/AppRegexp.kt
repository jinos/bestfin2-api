package com.fin.best.bestfin.api.component.constants

object AppRegexp {
    object Auth {
        const val PlatformType = "^(BROWSER|IOS|ANDROID)$"
        const val PinType = "^(FIND_ID|FIND_PW|JOIN|CHANGE_MOBILE)$"
        const val UserType = "^(USR|MNG)$"
        const val MobileNumber = "^[0][1-9]\\d{8,9}$"
    }

    object User {
        // 아이디: 영문 숫자 혼용 5자이상 20자 이내
        const val UserId = "^[a-zA-Z]([a-zA-Z0-9]){4,19}$"
        // 비밀번호: 영문, 숫자, 특수 혼용 6~12자 이내
        // ㄴ 비밀번호 최소 하나의 문자 + 하나의 숫자 + 하나의 특수 문자 포함, 최소 6자리
        const val Password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{6,12}$"
        // 이메일: ccdev@codecraft.co.kr
        const val Email = "^[a-zA-Z0-9][a-zA-Z0-9_.]+@[a-zA-Z.]+?\\.[a-zA-Z]{2,3}$"
    }

    object Admin {
        // 닉네임: 한글, 영문, 공백 혼용 2자이상 20자 이내
        const val Name = "^[a-zA-Z가-힣\\s]{2,20}$"
        // 아이디: 영문 숫자 혼용 5자이상 20자 이내
        const val Id = "^[a-z]([a-z0-9]){4,19}$"
        // 비밀번호: 영문, 숫자, 특수 혼용 6~12자 이내
        // ㄴ 비밀번호 최소 하나의 문자 + 하나의 숫자 + 하나의 특수 문자 포함, 최소 6자리
        const val Password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{6,12}$"
    }

    object Common {
        // 실수
        const val Decimal = "^\\d*(\\.?\\d*)\$)"
        // 도메인
        const val Url = "^((http|https)://)(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-z0-9.?#]+)?"
    }
}