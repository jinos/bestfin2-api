package com.fin.best.bestfin.api.app.domain.product.repository

import com.fin.best.bestfin.api.app.domain.product.entity.ProductOption
import org.springframework.data.jpa.repository.JpaRepository

interface ProductOptionRepository : JpaRepository<ProductOption, Long> {
    fun findBySeq(seq: Long): List<ProductOption>
}