package com.example.passwordgenerator

import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.log2
import kotlin.random.Random

/* В этом файле хранятся вспомогательные функции. */

fun getVocabulary(string: String): String {
    return string.toMutableList().distinct().joinToString(separator = "")
}

fun randomPassword(vocabulary: String, length: Int): String {
    var password = ""
    for (i in 1..length) {
        password += vocabulary[Random.nextInt(0, vocabulary.length)]
    }

    return password
}

fun generateTimestampString(): String {
    return SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(Calendar.getInstance().time)
}

fun calculateEntropy(password: String): Double {
    val vocabulary = getVocabulary(password)

    return log2(vocabulary.length.toDouble()) * password.length
}