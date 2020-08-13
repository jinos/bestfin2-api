package com.fin.best.bestfin.api.app.domain.common.dao

import com.fin.best.bestfin.api.app.domain.common.entity.BankCode
import com.fin.best.bestfin.api.app.domain.common.entity.CommonFiles
import com.fin.best.bestfin.api.app.domain.common.repository.BankCodeRepository
import com.fin.best.bestfin.api.app.domain.common.repository.CommonFilesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CommonDao
@Autowired constructor(
        private val commonFilesRepository: CommonFilesRepository,
        private val bankCodeResitory: BankCodeRepository
) {
    fun storeCommonFiles(commonFiles: CommonFiles): CommonFiles {
        return commonFilesRepository.save(commonFiles)
    }

    fun fetchProductProfileCount(): Long {
        return bankCodeResitory.count()
    }

    fun fetchProductProfile(pageNo: Int, itemsPerPage: Int): List<BankCode> {
        val pageable = PageRequest.of(pageNo - 1, itemsPerPage)
        return bankCodeResitory.findByOrderByKorCoNm(pageable)
    }

    fun storeBankCode(bankCode: BankCode): BankCode {
        return bankCodeResitory.save(bankCode)
    }

    fun fetchBankCode(finCoNo: Long): BankCode? {
        return bankCodeResitory.findById(finCoNo).orElse(null)
    }


}