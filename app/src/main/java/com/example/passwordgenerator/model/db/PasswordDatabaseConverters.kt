package com.example.passwordgenerator.model.db

import androidx.room.TypeConverter

class PasswordDatabaseConverters {
    @TypeConverter
    fun toPasswordGrade(value: Int) = enumValues<PasswordGrade>()[value]

    @TypeConverter
    fun fromPasswordGrade(grade: PasswordGrade) = grade.ordinal
}