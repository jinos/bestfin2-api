package com.fin.best.bestfin.api.app.domain.product.repository

import com.fin.best.bestfin.api.app.domain.product.entity.ProductItem
import org.springframework.data.jpa.repository.JpaRepository

interface ProductItemRepository : JpaRepository<ProductItem, Long> {
    fun findBySeq(seq: Long): List<ProductItem>
}