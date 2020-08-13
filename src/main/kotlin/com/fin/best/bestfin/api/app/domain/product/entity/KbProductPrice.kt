package com.fin.best.bestfin.api.app.domain.product.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(KbProductPriceId::class)
class KbProductPrice {
    @get:Id
    @get:Column(name = "sido")
    lateinit var sido: String
    @get:Id
    @get:Column(name = "sigungu")
    lateinit var sigungu: String
    @get:Id
    @get:Column(name = "dong")
    lateinit var dong: String
    @get:Id
    @get:Column(name = "apartment")
    lateinit var apartment: String
    @get:Id
    @get:Column(name = "area_size1")
    lateinit var areaSize1: String
    @get:Id
    @get:Column(name = "area_size2")
    lateinit var areaSize2: String
    @get:Id
    @get:Column(name = "building_cd")
    lateinit var buildingCd: String

    var nonRoyal1: String = ""
    var nonRoyal2: String = ""
    var nonRoyal3: String = ""
    var royal1: String = ""
    var royal2: String = ""
    var royal3: String = ""
    var tradeLower: String = ""
    var tradeGeneral: String = ""
    var tradeUpper: String = ""
    var roomCnt: Int = 0
    var nonRoyalPreferentialPayment: String = ""
    var royalPreferentialPayment: String = ""
    var basePreferentialPayment: String = ""
    var speculative: String = ""
    var speculationRidden: String = ""
    var adjustTarget: String = ""
    var gubun1: Float = 0F
    var gubun2: Float = 0F
    var gubun3: Float = 0F
    var issuedAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(sido: String, sigungu: String, dong: String, apartment: String, areaSize1: String, areaSize2: String, nonRoyal1: String, nonRoyal2: String, nonRoyal3: String, royal1: String, royal2: String, royal3: String, tradeLower: String, tradeGeneral: String, tradeUpper: String, roomCnt: Int, nonRoyalPreferentialPayment: String, royalPreferentialPayment: String, basePreferentialPayment: String, buildingCd: String, speculative: String, speculationRidden: String, adjustTarget: String, gubun1: Float, gubun2: Float, gubun3: Float) {
        this.sido = sido
        this.sigungu = sigungu
        this.dong = dong
        this.apartment = apartment
        this.areaSize1 = areaSize1
        this.areaSize2 = areaSize2
        this.nonRoyal1 = nonRoyal1
        this.nonRoyal2 = nonRoyal2
        this.nonRoyal3 = nonRoyal3
        this.royal1 = royal1
        this.royal2 = royal2
        this.royal3 = royal3
        this.tradeLower = tradeLower
        this.tradeGeneral = tradeGeneral
        this.tradeUpper = tradeUpper
        this.roomCnt = roomCnt
        this.nonRoyalPreferentialPayment = nonRoyalPreferentialPayment
        this.royalPreferentialPayment = royalPreferentialPayment
        this.basePreferentialPayment = basePreferentialPayment
        this.buildingCd = buildingCd
        this.speculative = speculative
        this.speculationRidden = speculationRidden
        this.adjustTarget = adjustTarget
        this.gubun1 = gubun1
        this.gubun2 = gubun2
        this.gubun3 = gubun3
    }
}

data class KbProductPriceId(
        var sido: String = "",
        var sigungu: String = "",
        var dong: String = "",
        var apartment: String = "",
        var areaSize1: String = "",
        var areaSize2: String = "",
        var buildingCd: String = ""
) : Serializable