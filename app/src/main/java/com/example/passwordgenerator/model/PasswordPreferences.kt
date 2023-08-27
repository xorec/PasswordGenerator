package com.example.passwordgenerator.model

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.passwordgenerator.getVocabulary

/* Объект класса PasswordPreferences (singleton) представляет собой обертку для работы с SharedPreferences.
*  Данное приложение имеет две настройки: настраиваемый набор символов (vocabulary) и настраиваемую
*  длину пароля (length). Во время создания объект PasswordPreferences проверяет, имеются ли какие-либо
*  значения для данных настроек, если нет - ключи создаются и вставляются стандартные значения.
*  Также этот объект отвечает за обновление настроек и их соответствие требованиям,
*  а именно, во-первых, поданные значения должны быть не пустыми, во-вторых, для vocabulary перед обновлением
*  необходимо исключить повторяющиеся символы из строки, в-третьих, длина пароля должна находиться
*  в диапазоне от 8 до 128. Если переданные для обновления значения не соответствуют требованиям -
*  возвращается ошибка и значения не обновляются. */
const val VOCABULARY_PREFERENCE_KEY = "VOCABULARY"
const val VOCABULARY_PREFERENCE_DEFAULT_VALUE = "AaBbCcDdEeGgHhXxYyZzАаБбВвГгДдЕеЖжЗзЭэЮюЯя1234567890@#$%^&*()_-+"
const val PASSWORD_LENGTH_PREFERENCE_KEY = "PASSWORD_LENGTH"
const val PASSWORD_LENGTH_DEFAULT_VALUE = 32

class PasswordPreferences private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: PasswordPreferences? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: PasswordPreferences(context).also { instance = it }
            }
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(
        context)

    /* В init происходит проверка, существуют ли ключи настроек в базе, если нет - они создаются
    *  и по ключам заносятся стандартные значения */
    init {
        preferences.also { preferences ->
            if (!preferences.contains(VOCABULARY_PREFERENCE_KEY)) {
                preferences.edit().putString(VOCABULARY_PREFERENCE_KEY,
                    VOCABULARY_PREFERENCE_DEFAULT_VALUE).apply()
            }

            if (!preferences.contains(PASSWORD_LENGTH_PREFERENCE_KEY)) {
                preferences.edit().putInt(PASSWORD_LENGTH_PREFERENCE_KEY,
                    PASSWORD_LENGTH_DEFAULT_VALUE).apply()
            }
        }
    }

    /* Можно запросить набор символов и длину пароля */
    var vocabulary = preferences.getString(
        VOCABULARY_PREFERENCE_KEY, VOCABULARY_PREFERENCE_DEFAULT_VALUE
    )!!
        private set

    var passwordLength = preferences.getInt(
        PASSWORD_LENGTH_PREFERENCE_KEY, PASSWORD_LENGTH_DEFAULT_VALUE)
        private set

    /* Можно обновить vocabulary и passwordLength. Если значения для обновления не соответствуют
    *  требованиям - возвращается ошибка и значения не обновляются. */
    fun updateVocabulary(vocabulary: String): Pair<Boolean, String?> {

        /* Если набор символов обновлен, то возвращается <true, *обработанный_набор_символов*>,
        *  если же на вход подана пустая строка - то возвращается <false, null> */
        if (vocabulary.isEmpty()) {
            return Pair(false, null)
        }

        this.vocabulary = getVocabulary(vocabulary)
        preferences.edit().putString(VOCABULARY_PREFERENCE_KEY, this.vocabulary).apply()

        return Pair(true, this.vocabulary)
    }

    fun updatePassLength(length: String): Boolean {
        if (length.isEmpty()) {
            return false
        }

        /* Для длины пароля при обновлении также происходит проверка на то, представляет ли поданная
           на вход строка число, если нет - ловится NumberFormatException  */
        try {
            length.toInt().let {
                return if (it < 8 || it > 128) {
                    false
                } else {
                    passwordLength = it
                    preferences.edit().putInt(PASSWORD_LENGTH_PREFERENCE_KEY, it).apply()
                    true
                }
            }
        } catch (e: java.lang.NumberFormatException) {
            return false
        }
    }
}