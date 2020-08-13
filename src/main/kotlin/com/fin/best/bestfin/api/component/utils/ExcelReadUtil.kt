package com.fin.best.bestfin.api.component.utils

import com.fin.best.bestfin.api.app.domain.product.entity.KbProductPrice
import com.fin.best.bestfin.api.component.error.exception.NotExistFileHandleException
import com.fin.best.bestfin.api.component.error.exception.WrongExcelTemplateHandleException
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

@Suppress("DuplicatedCode")
@Component
class ExcelReadUtil {
    private val logger: Logger = LoggerFactory.getLogger(ExcelReadUtil::class.java)

    /**
     * XLS 파일을 분석하여 List<CustomerVo> 객체로 반환
     * @param filePath
     * @return
    </CustomerVo> */
    @Throws(NotExistFileHandleException::class, WrongExcelTemplateHandleException::class)
    fun xlsToCustomerVoList(filePath: String?): List<KbProductPrice>? {

        // 반환할 객체를 생성
        var list = mutableListOf<KbProductPrice>()
        var fis: FileInputStream? = null
        var workbook: HSSFWorkbook? = null
        try {
            fis = FileInputStream(filePath)
            // HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
            workbook = HSSFWorkbook(fis)

            // 탐색에 사용할 Sheet, Row, Cell 객체
            var curSheet: HSSFSheet
            var curRow: HSSFRow
            var curCell: HSSFCell
            var map: MutableMap<String, String>

            // Sheet 탐색 for문
            for (sheetIndex in 0 until workbook.numberOfSheets) {
                // 현재 Sheet 반환
                curSheet = workbook.getSheetAt(sheetIndex)
                // row 탐색 for문
                for (rowIndex in 0 until curSheet.physicalNumberOfRows) {
                    // row 0은 헤더정보이기 때문에 무시
                    if (rowIndex > 11) {
                        // 현재 row 반환
                        curRow = curSheet.getRow(rowIndex)
                        map = LinkedHashMap()
                        var value: String
                        // cell 탐색 for 문
                        for (cellIndex in 0 until curRow.physicalNumberOfCells) {
                            try {
                                curCell = curRow.getCell(cellIndex)
                                if (curCell != null) {
                                    // cell 스타일이 다르더라도 String으로 반환 받음
                                    value = if (curCell.cellType === CellType.FORMULA) {
                                        curCell.cellFormula
                                    } else if (curCell.cellType === CellType.NUMERIC) {
                                        curCell.cellType = CellType.STRING
                                        curCell.stringCellValue.toString() + ""
                                    } else if (curCell.cellType === CellType.STRING) {
                                        curCell.stringCellValue.toString() + ""
                                    } else if (curCell.cellType === CellType.BLANK) {
                                        curCell.booleanCellValue.toString() + ""
                                    } else if (curCell.cellType === CellType.ERROR) {
                                        curCell.errorCellValue.toString() + ""
                                    } else {
                                        String()
                                    }

                                    // 현재 column index에 따라서 map에 입력
                                    setCell(cellIndex, map, value)
                                }
                            } catch (e: Exception) {

                            }
                        }
                        // cell 탐색 이후 vo 추가
                        list.add(KbProductPrice(
                                sido = map["sido"]?.let{it}?:"",
                                sigungu = map["sigungu"]?.let{it}?:"",
                                dong = map["dong"]?.let{it}?:"",
                                apartment = map["apartment"]?.let{it}?:"",
                                areaSize1 = map["areaSize1"]?.let{it}?:"",
                                areaSize2 = map["areaSize2"]?.let{it}?:"",
                                nonRoyal1 = map["nonRoyal1"]?.let{it}?:"",
                                nonRoyal2 = map["nonRoyal2"]?.let{it}?:"",
                                nonRoyal3 = map["nonRoyal3"]?.let{it}?:"",
                                royal1 = map["royal1"]?.let{it}?:"",
                                royal2 = map["royal2"]?.let{it}?:"",
                                royal3 = map["royal3"]?.let{it}?:"",
                                tradeLower = map["tradeLower"]?.let{it}?:"",
                                tradeGeneral = map["tradeGeneral"]?.let{it}?:"",
                                tradeUpper = map["tradeUpper"]?.let{it}?:"",
                                roomCnt = map["roomCnt"]?.let{it.toInt()}?:0,
                                nonRoyalPreferentialPayment = map["nonRoyalPreferentialPayment"]?.let{it}?:"",
                                royalPreferentialPayment = map["royalPreferentialPayment"]?.let{it}?:"",
                                basePreferentialPayment = map["basePreferentialPayment"]?.let{it}?:"",
                                buildingCd = map["buildingCd"]?.let{it}?:"",
                                speculative = map["speculative"]?.let{it}?:"",
                                speculationRidden = map["speculationRidden"]?.let{it}?:"",
                                adjustTarget = map["adjustTarget"]?.let{it}?:"",
                                gubun1 = map["gubun1"]?.let{it.toFloat()}?:0F,
                                gubun2 = map["gubun2"]?.let{it.toFloat()}?:0F,
                                gubun3 = map["gubun3"]?.let{it.toFloat()}?:0F
                        ))
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            logger.info("[excel error] =>{}", e.message)
            e.printStackTrace()
            throw NotExistFileHandleException()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            logger.info("[excel error] =>{}", e.message)
            e.printStackTrace()
            throw WrongExcelTemplateHandleException()
        } catch (e: Exception) {
            logger.info("[excel error] =>{}", e.message)
            e.printStackTrace()
            throw WrongExcelTemplateHandleException()
        }
        return list
    }

    /**
     * XLSX 파일을 분석하여 List<CustomerVo> 객체로 반환
     * @param filePath
     * @return
    </CustomerVo> */
    @Throws(NotExistFileHandleException::class, WrongExcelTemplateHandleException::class)
    fun xlsxToCustomerVoList(filePath: String?): List<KbProductPrice>? {
        // 반환할 객체를 생성
        var list = mutableListOf<KbProductPrice>()
        var fis: FileInputStream? = null
        var workbook: XSSFWorkbook? = null
        try {
            fis = FileInputStream(filePath)
            // HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
            workbook = XSSFWorkbook(fis)

            // 탐색에 사용할 Sheet, Row, Cell 객체
            var curSheet: XSSFSheet
            var curRow: XSSFRow
            var curCell: XSSFCell
            var map: MutableMap<String, String> = HashMap()

            // Sheet 탐색 for문
            for (sheetIndex in 0 until workbook.numberOfSheets) {
                // 현재 Sheet 반환
                curSheet = workbook.getSheetAt(sheetIndex)
                // row 탐색 for문
                for (rowIndex in 0 until curSheet.physicalNumberOfRows) {
                    // row 0은 헤더정보이기 때문에 무시
                    if (rowIndex > 11) {
                        // 현재 row 반환
                        curRow = curSheet.getRow(rowIndex)
                        map = LinkedHashMap()
                        var value: String
                        // row의 두번째 cell값이 비어있지 않은 경우 만 cell탐색
                        if ("" != curRow.getCell(1).stringCellValue) {
                            // cell 탐색 for 문
                            for (cellIndex in 0 until curRow.physicalNumberOfCells) {
                                try {
                                    curCell = curRow.getCell(cellIndex)
                                    if (curCell != null) {
                                        value = ""
                                        // cell 스타일이 다르더라도 String으로 반환 받음
                                        value = if (curCell.cellType === CellType.FORMULA) {
                                            curCell.cellFormula
                                        } else if (curCell.cellType === CellType.NUMERIC) {
                                            curCell.cellType = CellType.STRING
                                            curCell.stringCellValue.toString() + ""
                                        } else if (curCell.cellType === CellType.STRING) {
                                            curCell.stringCellValue.toString() + ""
                                        } else if (curCell.cellType === CellType.BLANK) {
                                            curCell.booleanCellValue.toString() + ""
                                        } else if (curCell.cellType === CellType.ERROR) {
                                            curCell.errorCellValue.toString() + ""
                                        } else {
                                            String()
                                        }

                                        // 현재 column index에 따라서 map에 입력
                                        setCell(cellIndex, map, value)
                                    }
                                } catch (e: Exception) {

                                }
                            }
                            // cell 탐색 이후 vo 추가
                            list.add(KbProductPrice(
                                    sido = map["sido"]?.let{it}?:"",
                                    sigungu = map["sigungu"]?.let{it}?:"",
                                    dong = map["dong"]?.let{it}?:"",
                                    apartment = map["apartment"]?.let{it}?:"",
                                    areaSize1 = map["areaSize1"]?.let{it}?:"",
                                    areaSize2 = map["areaSize2"]?.let{it}?:"",
                                    nonRoyal1 = map["nonRoyal1"]?.let{it}?:"",
                                    nonRoyal2 = map["nonRoyal2"]?.let{it}?:"",
                                    nonRoyal3 = map["nonRoyal3"]?.let{it}?:"",
                                    royal1 = map["royal1"]?.let{it}?:"",
                                    royal2 = map["royal2"]?.let{it}?:"",
                                    royal3 = map["royal3"]?.let{it}?:"",
                                    tradeLower = map["tradeLower"]?.let{it}?:"",
                                    tradeGeneral = map["tradeGeneral"]?.let{it}?:"",
                                    tradeUpper = map["tradeUpper"]?.let{it}?:"",
                                    roomCnt = map["roomCnt"]?.let{it.toInt()}?:0,
                                    nonRoyalPreferentialPayment = map["nonRoyalPreferentialPayment"]?.let{it}?:"",
                                    royalPreferentialPayment = map["royalPreferentialPayment"]?.let{it}?:"",
                                    basePreferentialPayment = map["basePreferentialPayment"]?.let{it}?:"",
                                    buildingCd = map["buildingCd"]?.let{it}?:"",
                                    speculative = map["speculative"]?.let{it}?:"",
                                    speculationRidden = map["speculationRidden"]?.let{it}?:"",
                                    adjustTarget = map["adjustTarget"]?.let{it}?:"",
                                    gubun1 = map["gubun1"]?.let{it.toFloat()}?:0F,
                                    gubun2 = map["gubun2"]?.let{it.toFloat()}?:0F,
                                    gubun3 = map["gubun3"]?.let{it.toFloat()}?:0F
                            ))
                        }
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            logger.info("[excel error] =>{}", e.message)
            e.printStackTrace()
            throw NotExistFileHandleException()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            logger.info("[excel error] =>{}", e.message)
            e.printStackTrace()
            throw WrongExcelTemplateHandleException()
        } catch (e: Exception) {
            logger.info("[excel error] =>{}", e.message)
            e.printStackTrace()
            throw WrongExcelTemplateHandleException()
        }
        return list
    }

    private fun setCell(cellIndex: Int, map: MutableMap<String, String>, value: String): MutableMap<String, String> {
        // 현재 column index에 따라서 map에 입력
        when (cellIndex) {
            0 -> map["sido"] = value
            1 -> map["sigungu"] = value
            2 -> map["dong"] = value
            3 -> map["apartment"] = value
            4 -> map["areaSize1"] = value
            5 -> map["areaSize2"] = value
            12 -> map["nonRoyal1"] = value
            13 -> map["nonRoyal2"] = value
            14 -> map["nonRoyal3"] = value
            15 -> map["royal1"] = value
            16 -> map["royal2"] = value
            17 -> map["royal3"] = value
            18 -> map["tradeLower"] = value
            19 -> map["tradeGeneral"] = value
            20 -> map["tradeUpper"] = value
            21 -> map["roomCnt"] = value
            22 -> map["nonRoyalPreferentialPayment"] = value
            23 -> map["royalPreferentialPayment"] = value
            24 -> map["basePreferentialPayment"] = value
            25 -> map["buildingCd"] = value
            27 -> map["speculative"] = value
            28 -> map["speculationRidden"] = value
            29 -> map["adjustTarget"] = value
            30 -> map["gubun1"] = value
            31 -> map["gubun2"] = value
            32 -> map["gubun3"] = value
            else -> {
            }
        }
        return map
    }
    
}