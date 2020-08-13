package com.fin.best.bestfin.api.component.constants

object AppConst {
    enum class YN { Y, N }
    enum class Gender { UNKNOWN, MALE, FEMALE }

    object User {
        enum class UserStatus { NOR, SEC }
        enum class ConfirmType { ID, EMAIL }
        enum class AuthStatus { ON, OK, SIGN_UP, EXP }
        enum class AuthCodeType { LOGIN_3RD, JOIN_3RD, CERT_ON, CERT_OK, PIN }
        enum class UserType { USR, MNG, ADM }
        enum class PinType { FIND_ID, FIND_PW, JOIN, CHANGE_MOBILE }
        enum class PinStatus { ON, OK, DONE, EXP }
        enum class AuthProvider { LOCAL, KAKAO }
        enum class LoginType { USER_ID, USER_3RD, AGENT, COUNSELOR, ADMIN }

        object AuthToken {
            enum class TokenIssueType { ACCESS, REFRESH }
            enum class TokenType { ACC, REF }
            enum class ExpiredTimeUnit { HOURS, DAY, MONTH }
        }

        object AdminUser {
            enum class UserStatus { NOR, SEC }
        }
        object AgentUser {
            enum class UserStatus { WAIT, REJECT, CONFIRM, MODIFY }
            enum class WithdrawStatus { ON, APPLY, CONFIRM }
        }
    }

    object Product {
        enum class MrtgType { A, E, N }
        enum class RpayType { D, S, N }
        enum class LendRateType { C, F, M, N }
        enum class CrdtLendRateType { A, B, C, D, N }
        enum class LoanType { C, M, R, V }
        enum class MortgageType { NEST, STEP, NONE}
        enum class GuaranteeType { HOUSE, SEOUL, CITY, NONE}
    }

    object Kcb {
        enum class Gender { M, F, U}
        enum class ReqType { SCD_2_1, SCD_2_2, SCD_2_3 }
        enum class ReqStatus { ON, SUCCESS, FAIL }
    }

    object Request {
        const val RequestHeaderDeviceTokenKey = "device-token"
        const val RequestHeaderAccessTokenKey = "access-token"
        const val RequestHeaderRefreshTokenKey = "refresh-token"
        const val ReqeustHeaderCampaignIdKey = "campaign-id"
        const val RequestHeaderConsumeType = "consume-type"
        const val RequestHeaderIssuerType = "issuer-type"
        const val RequestHeaderAccessLanguage = "language"
    }

    object DateFormat {
        const val YearMonthHourMinSecFormat = "yyyyMMddHHmmss"
        const val YearMonthHourMinSecDashFormat = "yyyy-MM-dd HH:mm:ss"
        const val YearMonthHourFormat = "yyyyMMdd"
        const val YearMonthHourDotFormat = "yyyy.MM.dd"
        const val YearMonthHourDashFormat = "yyyy-MM-dd"
        const val YearForWeekFormat = "yyyyw"
    }

    object Cache {
        const val EncodingProcessListKey: String = "style:enc:q"
        const val StyleMetaKey: String = "smeta:sty"
        const val LookMetaKey: String = "smeta:lk"
        const val AgeMetaKey: String = "smeta:age"
        const val CachedPrefixAuthHashKey = "acc"
        const val AccessTokenExpiredValue: Long = 24
        const val RefreshTokenTokenExpiredValue: Long = 30
    }

    object Response {
        const val DefaultSuccessCode = 200
        const val DefaultEmptyCode = 0
        const val DefaultErrorCode = 999
        const val DefaultSuccessMessage = "success"
        const val DefaultEmptyMessage = ""
        const val DefaultFailedMessage = "failed"
    }

    object Log {
        const val Domain: String = "cc.glovv.main.api"
        const val BasePackage: String = "cc.glovv"
        const val ApplicationName: String = "glovv_service"
        const val Request: String = "REQ"
        const val Response: String = "RES"
        const val Result: String = "result"
        const val ContentType: String = "Content-Type"
        const val ContentLength: String = "Content-Length"
        const val UserAgent: String = "User-Agent"
        const val Method: String = "method"
        const val Uri: String = "uri"
        const val Status: String = "status"
        const val ReturnCode: String = "rtnCode"
        const val ReturnMessage: String = "rtnMsg"
        const val ErrorCode: String = "errorCode"
        const val ErrorMessage: String = "errorMessages"
        const val Elapsed: String = "elapsed"
    }

    object Delimiters {
        const val PadBar: String = " | "
        const val IsVal: String = "="
        const val Dash: String = "-"
        const val Blank: String = ""
        const val Unknown: String = "UNKNOWN"
        const val Comma: String = ","
        const val Dot: String = "."
        const val LineBreakAt: String = "\n \t at "
        const val Question: String = "?"
        const val LineBreak: String = "\n"
        const val Colon: String = ":"
        const val Utf8: String = "UTF-8"
        const val Slash: String = "/"
    }

    object File {
        const val DefaultSystemFileMime: String = "application/octet-stream"
        const val XlsFileMimeType: String = "application/vnd.ms-excel"
    }
}