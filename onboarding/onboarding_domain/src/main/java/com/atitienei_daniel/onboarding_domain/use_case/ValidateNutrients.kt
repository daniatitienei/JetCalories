package com.atitienei_daniel.onboarding_domain.use_case

class ValidateNutrients {

    fun execute(fatRatioText: String, carbsRatioText: String, proteinRatioText: String): Result {
        val fatRatio = fatRatioText.toFloatOrNull()
        val carbsRatio = carbsRatioText.toFloatOrNull()
        val proteinRatio = proteinRatioText.toFloatOrNull()

        if (fatRatio == null || carbsRatio == null || proteinRatio == null) {
            return Result.Error(message = "Invalid values")
        }

        if (fatRatio + carbsRatio + proteinRatio != 100f) {
            return Result.Error(message = "Total is not 100 percent")
        }

        return Result.Success(
            fatRatio = fatRatio / 100f,
            carbsRatio = carbsRatio / 100f,
            proteinRatio = proteinRatio / 100f
        )
    }

    sealed interface Result {
        data class Success(
            val fatRatio: Float,
            val carbsRatio: Float,
            val proteinRatio: Float
        ) : Result

        data class Error(
            val message: String
        ) : Result
    }
}