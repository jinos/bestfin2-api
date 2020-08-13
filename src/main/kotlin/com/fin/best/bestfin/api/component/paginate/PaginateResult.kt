package com.fin.best.bestfin.api.component.paginate

import kotlin.math.ceil

class PaginateResult private constructor(
        val pageNo: Int,
        val itemsPerPage: Int,
        val totalCount: Long
) {
    var offset: Int = 0
    var limit: Int = 0
    var lastPage: Int = 0
    var hasNext: Boolean = false
    var hasPrev: Boolean = false
    var pages: List<*>? = null
    init {
        this.offset = (this.pageNo - 1) * this.itemsPerPage
        this.limit = this.offset + this.itemsPerPage
        this.hasNext = this.totalCount > limit
        this.hasPrev = this.offset > 0
        this.lastPage = ceil(this.totalCount.toDouble() / this.itemsPerPage).toInt()
    }

    fun pages(pages: List<*>) = apply {
        this.pages = pages
    }

    class Builder {
        private var pageNo: Int? = null
        private var itemsPerPage: Int? = null
        private var totalCount: Long? = null

        fun page(pageNo: Int, itemsPerPage: Int) = apply {
            this.pageNo = pageNo
            this.itemsPerPage = itemsPerPage
        }

        fun totalCount(totalCount: Long) = apply {
            this.totalCount = totalCount
        }

        fun build(): PaginateResult =
                PaginateResult(
                        pageNo = pageNo?:1,
                        itemsPerPage = itemsPerPage?:20,
                        totalCount = totalCount?:0
                )
    }
}