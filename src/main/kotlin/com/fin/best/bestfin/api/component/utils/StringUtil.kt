package com.fin.best.bestfin.api.component.utils

import com.fasterxml.uuid.Generators
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StringUtil {
    companion object {
        fun generateTimeBaseUUID(): String = Generators.timeBasedGenerator().generate().toString()
        fun generateTimeBaseUUIDOnlyAlphabet(): String = Generators.timeBasedGenerator().generate().toString().replace("-", "")
        fun generateRandomUUID(): String = Generators.randomBasedGenerator().generate().toString()
        fun generateTimeFormatString(format: String): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))

        private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        fun generateRandomNumber(fillIndex: Int): String {
            val sb = StringBuffer()
            val random = Random()
            for (i in 1..fillIndex) sb.append(random.nextInt(9))
            return sb.toString()
        }

        fun generateRandomCharecter(fillIndex: Int): String {
            return (1..fillIndex).map { kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")
        }
    }
}