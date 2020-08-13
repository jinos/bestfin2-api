package com.fin.best.bestfin.api.app.domain.cert.service

import com.fin.best.bestfin.api.app.domain.cert.dao.KcbCertDao
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCredit
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditPersonalInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreditService
@Autowired constructor(
        private val kcbCertDao: KcbCertDao
) {

    fun fetchKcbPersonalInfo(credit: KcbCredit): Int {
        // kcb_credit_personal_info
        // 정보구분코드 : info_code
        //  1 : 자택정보
        //  2 : 직장정보
        //  4 : 연소득정보 (*)
        //  5 : 휴대폰정보
        // 연소득 : 가장 최근 등록일에 해당 하는 연소득 값을 사용
        //  annual_income
        //  원천 데이터는 천원단위 --> 만원단위 (만원 미만 버림)
        val kcbPersonalInfo = kcbCertDao.fetchCreditPersonalInfo(credit.creditNo)
        if (kcbPersonalInfo.isNotEmpty()) {
            val latest = kcbPersonalInfo.stream().filter { item -> item.infoCode == "4" }
                    .max(Comparator.comparing ( KcbCreditPersonalInfo::regDate ))
            if (latest.isPresent) {
                return latest.get().annualIncome.toInt() / 10  // 천원단위 --> 만원단위

            }
        }

        return 0
    }
}
