package com.fin.best.bestfin.api.component.utils

import com.fin.best.bestfin.api.component.constants.AppConst
import org.slf4j.LoggerFactory
import java.lang.reflect.Field

class LogParser {
    companion object {
        private val logger = LoggerFactory.getLogger(LogParser::class.java)

        fun makeLog(msg: String, exception: Exception): String {
            val stringBuffer = StringBuffer()
            stringBuffer.append(msg).append(AppConst.Delimiters.PadBar).append(convertException(exception))
            return stringBuffer.toString()
        }

        fun makeLog(msg: String, value: Any): String {
            val stringBuffer = StringBuffer()
            stringBuffer.append(msg).append(AppConst.Delimiters.PadBar).append(convertObject(value))
            return stringBuffer.toString()
        }

        fun makeLog(value: Any, exception: Exception): String {
            val stringBuffer = StringBuffer()
            stringBuffer.append(convertObject(value)).append(AppConst.Delimiters.PadBar).append(convertException(exception))
            return stringBuffer.toString()
        }

        fun makeLog(count: Int, vararg values: Any): String {
            val stringBuffer = StringBuffer()
            if (count > values.size) {
                stringBuffer.append("[Error] Delimiter size Can not be Greater than values size")
            } else {
                for (i in 0 until count) {
                    stringBuffer.append(values[i])
                    stringBuffer.append(AppConst.Delimiters.PadBar)
                }
                var isUriPattern = false
                var i = count
                var devideCount = 0
                while (i < values.size) {
                    stringBuffer.append(values[i])
                    if (isUriPattern || values[i] === AppConst.Delimiters.Question) {
                        isUriPattern = false
                        i++
                        devideCount++
                        continue
                    }
                    if (i == values.size - 1) break
                    stringBuffer.append(if (devideCount % 2 == 0) AppConst.Delimiters.IsVal else AppConst.Delimiters.Comma)
                    isUriPattern = values[i] === AppConst.Log.Uri
                    i++
                    devideCount++
                }
            }

            return stringBuffer.toString()
        }

        private fun convertObject(value: Any): String {
            val stringBuffer = StringBuffer()
            var isFirst = true

            when (value) {
                is String -> {
                    stringBuffer.append(value)
                }
                is Array<*>, is List<*> -> {
                    parseIterator(isFirst, stringBuffer, if (value is Array<*>) value.iterator() else (value as List<*>).iterator())
                }
                is Map<*, *> -> {
                    for (str: Any? in value.keys) {
                        stringBuffer.append(str).append(AppConst.Delimiters.IsVal).append(value[str]).append(AppConst.Delimiters.LineBreak)
                    }
                }
                else -> {
                    try {
                        for (str: Field in value.javaClass.fields) {
                            if (!isFirst) stringBuffer.append(AppConst.Delimiters.Comma)
                            else isFirst = false
                            stringBuffer.append(str.name).append(AppConst.Delimiters.IsVal).append(str[value])
                        }
                    } catch (e: Exception) {
                        logger.error(e.message)
                        stringBuffer.append(e)
                    }
                }
            }
            return stringBuffer.toString()
        }

        private fun parseIterator(flag: Boolean, stringBuffer: StringBuffer, values: Iterator<*>) {
            var isFirst = flag
            for (str: Any? in values) {
                if (!isFirst) stringBuffer.append(AppConst.Delimiters.LineBreak)
                else isFirst = false
                stringBuffer.append(str.toString())
            }
        }


        private fun convertException(exception: Exception): String {
            val stringBuffer = StringBuffer()
            stringBuffer.append(exception.javaClass.simpleName).append(AppConst.Delimiters.Colon).append(exception.message)
            for (stackTraceElement: StackTraceElement in exception.stackTrace) {
                if (stackTraceElement.toString().startsWith(AppConst.Log.BasePackage)) {
                    stringBuffer.append(AppConst.Delimiters.LineBreakAt).append(stackTraceElement.toString())
                }
            }
            return stringBuffer.toString()
        }
    }


}