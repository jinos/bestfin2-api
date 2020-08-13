package com.fin.best.bestfin.api.app.domain.product.model

import com.fin.best.bestfin.api.app.domain.product.entity.ProductBase
import com.fin.best.bestfin.api.app.domain.product.entity.ProductItem
import com.fin.best.bestfin.api.app.domain.product.entity.ProductOption

data class ProductResult(
        var base: ProductBase?,
        var options: List<ProductOptionResult>?
) {

}

data class ProductOptionResult(
        var option: ProductOption,
        var items: List<ProductItem>
)
