package com.fin.best.bestfin.api.app.domain.cert.dao

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCertHistory
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCredit
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditPersonalInfo
import com.fin.best.bestfin.api.app.domain.cert.repository.KcbCertHistoryRepository
import com.fin.best.bestfin.api.app.domain.cert.repository.KcbCreditPersonalInfoRepository
import com.fin.best.bestfin.api.app.domain.cert.repository.KcbCreditRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class KcbCertDao
@Autowired constructor(
        private val kcbCertHistoryRepository: KcbCertHistoryRepository,
        private val kcbCreditRepository: KcbCreditRepository,
        private val kcbCreditPersonalInfoRepository: KcbCreditPersonalInfoRepository
) {
    fun fetchCertHisroty(certNo: Long): Optional<KcbCertHistory> {
        return kcbCertHistoryRepository.findById(certNo)
    }

    @Throws(IllegalStateException::class)
    fun fetchKcbCreditUserNo(kcbUserNo: String): KcbCredit {
        return kcbCreditRepository.findByKcbUserNo(kcbUserNo).orElseThrow { throw IllegalStateException("not found my credit info") }
    }

    fun fetchCreditPersonalInfo(creditNo: Int?): List<KcbCreditPersonalInfo> {
        return kcbCreditPersonalInfoRepository.findAll()
    }

    fun deleteKcbCertHistoryCiDi(ci: String, di: String) {
        kcbCertHistoryRepository.deleteByCiAndDi(ci, di)
    }

    fun storeKcbCertHisroty(cert: KcbCertHistory): KcbCertHistory {
        return kcbCertHistoryRepository.save(cert)
    }

}