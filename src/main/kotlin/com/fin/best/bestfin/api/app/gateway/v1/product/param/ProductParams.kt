package com.fin.best.bestfin.api.app.gateway.v1.product.param

import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.beans.factory.annotation.Required
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ProductParams(
        val productNo: String?,
        val loanType: AppConst.Product.LoanType,
        val dclsMonth: String?,
        val finCoNo: String?,
        val korCoNm: String?,
        val finPrdtNm: String?,
        val description: String?,
        val weight: String?,
        val useYn: AppConst.YN?,

        val loanLmt: String?,            //주택담보, 전세자금, 자동차
        val loanLmtStatus: AppConst.YN?,
        val joinWay: String?,           //주택담보, 전세자금, 신용
        val joinWayStatus: AppConst.YN?,
        val loanInciExpn: String?,      //주택담보, 전세자금, 신용
        val loanInciExpnStatus: AppConst.YN?,
        val erlyRpayFee: String?,       //주택담보, 전세자금, 신용
        val erlyRpayFeeStatus: AppConst.YN?,
        val dlyRate: String?,           //주택담보, 전세자금, 신용
        val dlyRateStatus: AppConst.YN?,
        val dclsStrtDay: String?,       //주택담보, 전세자금, 신용
        val dclsEndDay: String?,        //주택담보, 전세자금, 신용
        val finCoSubmDay: String?,      //주택담보, 전세자금, 신용

        val mortgageType: AppConst.Product.MortgageType?,   //주택담보
        val cbName: String?,             //신용 대출용
        val crdtPrdtTypeNm: String?,     //신용 대출용
        val webUrl: String?,     //신용 대출용, 자동차 대출용
        val bankType: AppConst.YN?,     //신용 대출용
        val carCondition: String?,       //자동차 대출용
        val lendRateMin: String?,       //자동차 대출용
        val lendRateMax: String?,       //자동차 대출용
        val guaranteeType: AppConst.Product.GuaranteeType?,     //전세자금
        val options: List<OptionParams>?    //주택담보, 전세자금
)

data class OptionParams(
        val idx: String?,
        val mrtgType: AppConst.Product.MrtgType?,                               //주택담보, 전세자금
        val mrtgTypeNm: String?,                                                //주택담보, 전세자금
        val rpayType: AppConst.Product.RpayType?,                               //주택담보, 전세자금
        val rpayTypeNm: String?,                                                //주택담보, 전세자금
        val lendRateType: AppConst.Product.LendRateType?,                       //주택담보, 전세자금
        val lendRateTypeNm: String?,                                            //주택담보, 전세자금
        val lendRateMin: String?,                                               //주택담보, 전세자금
        val lendRateMinStatus: AppConst.YN?,
        val lendRateMax: String?,                                               //주택담보, 전세자금
        val lendRateMaxStatus: AppConst.YN?,
        val lendRateAvg: String?,                                               //주택담보, 전세자금
        val lendRateAvgStatus: AppConst.YN?,
        val useYn: AppConst.YN?,
        val preference: List<PreferenceParams>?,                                //주택담보, 전세자금

        val crdtLendRateType: AppConst.Product.CrdtLendRateType?,               //신용
        val crdtLendRateTypeNm: String?,                                        //신용
        val crdtGrad1: String?,                                                 //신용
        val crdtGrad1Status: AppConst.YN?,                                      //신용
        val crdtGrad4: String?,                                                 //신용
        val crdtGrad4Status: AppConst.YN?,                                      //신용
        val crdtGrad5: String?,                                                 //신용
        val crdtGrad5Status: AppConst.YN?,                                      //신용
        val crdtGrad6: String?,                                                 //신용
        val crdtGrad6Status: AppConst.YN?,                                      //신용
        val crdtGrad10: String?,                                                //신용
        val crdtGrad10Status: AppConst.YN?,                                     //신용
        val crdtGradAvg: String?,                                               //신용
        val crdtGradAvgStatus: AppConst.YN?,                                    //신용
        val weight: String?                                                     //신용
)

data class PreferenceParams(
        val idx: String?,
        val title: String?,
        val rate: String?,
        val useYn: AppConst.YN?
)
