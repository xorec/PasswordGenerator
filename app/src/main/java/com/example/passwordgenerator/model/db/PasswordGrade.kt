package com.example.passwordgenerator.model.db

enum class PasswordGrade {
    PASSWORD_GRADE_POOR,
    PASSWORD_GRADE_WEAK,
    PASSWORD_GRADE_REASONABLE,
    PASSWORD_GRADE_GOOD
}

fun calculateGrade(score: Double): PasswordGrade {
    return if (score < 24.0) {
        PasswordGrade.PASSWORD_GRADE_POOR
    } else if (score >= 24.0 && score < 49) {
        PasswordGrade.PASSWORD_GRADE_WEAK
    } else if (score >= 49.0 && score < 74.0) {
        PasswordGrade.PASSWORD_GRADE_REASONABLE
    } else PasswordGrade.PASSWORD_GRADE_GOOD
}