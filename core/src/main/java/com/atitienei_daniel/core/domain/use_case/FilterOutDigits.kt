package com.atitienei_daniel.core.domain.use_case

class FilterOutDigits {

    fun execute(text: String): String {
        return text.filter { it.isDigit() }
    }
}