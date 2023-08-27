package com.example.passwordgenerator.model.db

import androidx.room.Entity
import com.example.passwordgenerator.calculateEntropy
import com.example.passwordgenerator.getVocabulary

/* Первичным ключом для таблицы является комбинация пароля и списка, т.е. могут быть одинаковые пароли
*  в разных списках, но в одном списке не может быть два пароля с одинаковым содержанием */
@Entity(primaryKeys = ["password", "listName"])
data class Password(
    val password: String,
    val vocabulary: String,
    val listName: String,
    val entropy: Double,
    val grade: PasswordGrade) {

    constructor(password: String, vocabulary: String, listName: String, entropy: Double):
            this(password, vocabulary, listName, entropy, calculateGrade(entropy))

    constructor(password: String, listName: String): this(password,
        getVocabulary(password),
        listName,
        calculateEntropy(password))
}
