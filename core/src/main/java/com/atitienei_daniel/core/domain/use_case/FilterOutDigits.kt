package com.atitienei_daniel.core.domain.use_case

class FilterOutDigits {

    fun execute(text: String): String {
        var alreadyHasDot = false

        return text.filter {
            if (it.isDigit()) {
                true
            } else if (it == '.' && !alreadyHasDot) {
                alreadyHasDot = true
                true
            } else {
                false
            }
        }
    }
}