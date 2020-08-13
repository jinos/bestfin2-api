package com.fin.best.bestfin.api.app.domain.product.repository

import com.fin.best.bestfin.api.app.domain.product.entity.ProductBase
import org.springframework.data.jpa.repository.JpaRepository

interface ProductBaseRepository : JpaRepository<ProductBase, Long> {
}