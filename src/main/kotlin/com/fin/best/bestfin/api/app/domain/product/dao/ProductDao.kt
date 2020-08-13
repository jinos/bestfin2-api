package com.fin.best.bestfin.api.app.domain.product.dao

import com.fin.best.bestfin.api.app.domain.product.entity.*
import com.fin.best.bestfin.api.app.domain.product.repository.KbProductPriceRepository
import com.fin.best.bestfin.api.app.domain.product.repository.ProductBaseRepository
import com.fin.best.bestfin.api.app.domain.product.repository.ProductItemRepository
import com.fin.best.bestfin.api.app.domain.product.repository.ProductOptionRepository
import com.fin.best.bestfin.api.component.constants.AppConst
import org.hibernate.criterion.CriteriaQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import org.springframework.util.StringUtils

@Repository
class ProductDao
@Autowired constructor(
        private val productBaseRepository: ProductBaseRepository,
        private val productOptionRepository: ProductOptionRepository,
        private val productItemRepository: ProductItemRepository,
        private val kbProductPriceRepository: KbProductPriceRepository

) {
    fun persistKbProductPrices(entityManager: EntityManager, products: KbProductPrice) {
        entityManager.persist(products)
    }

    fun storeKbProductPrice(product: KbProductPrice): KbProductPrice {
        return kbProductPriceRepository.save(product)
    }

    fun persistFlushKbProductPrices(entityManager: EntityManager) {
        entityManager.flush()
        entityManager.clear()
    }

    fun fetchKbProductPrice(product: KbProductPrice): Optional<KbProductPrice> {
        return kbProductPriceRepository.findById(KbProductPriceId(
                sido = product.sido!!,
                sigungu = product.sigungu!!,
                dong = product.dong!!,
                apartment = product.apartment!!,
                areaSize1 = product.areaSize1!!,
                areaSize2 = product.areaSize2!!,
                buildingCd = product.buildingCd!!
        ))
    }

    fun storeProductBase(productBase: ProductBase): ProductBase {
        return productBaseRepository.save(productBase)
    }

    fun storeProductOption(productOption: ProductOption): ProductOption? {
        return productOptionRepository.save(productOption)
    }

    fun storeProductItem(productItem: ProductItem): ProductItem {
        return productItemRepository.save(productItem)
    }

    fun fetchProductBase(seq: Long): ProductBase? {
        return productBaseRepository.findById(seq).orElse(null)
    }

    fun fetchProductOptionsBySeq(seq: Long): List<ProductOption> {
        return productOptionRepository.findBySeq(seq)
    }

    fun fetchProductItemBySeq(seq: Long): List<ProductItem> {
        return productItemRepository.findBySeq(seq)
    }

    fun fetchProductOption(id: Long): ProductOption? {
        return productOptionRepository.findById(id).orElse(null)
    }

    fun fetchProductItem(id: Long): ProductItem? {
        return productItemRepository.findById(id).orElse(null)
    }
}