package com.example.passwordgenerator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.passwordgenerator.*
import com.example.passwordgenerator.model.db.Password

/* Для всех экранов используется одна ViewModel, которая связана c MainActivity.
*  Поскольку по заданию необходимо иметь возможность экспортировать пароли, необходимо каким-то образом
*  хранить название списка, из которого экспортируются пароли (exportListName). Сначала
*  пользователь указывает место, где будет создан файл с паролями, затем приложение получает uri, затем
*  вызывается функция создания файла. Соответственно, в момент, когда вызывается ActivityResultLauncher,
*  MainActivity может быть удалена, причем вместе со своей ViewModel (данное поведение можно вызвать,
*  указав опцию "удалять activity после выхода" в настройках разработчика), поэтому необходимо
*  использовать SavedStateHandle, который будет сохранять exportListName. Таким образом, даже если
*  ViewModel будет удалена, приложение при получении результата в ActivityResultLauncher будет иметь
*  актуальный exportListName.  */
class MainActivityViewModel(private val state: SavedStateHandle): ViewModel() {

    /* В качестве свойств viewmodel имеет значения настроек и LiveData, которая позволяет подписаться
    *  на список папок с паролями */
    val preferences = PasswordGeneratorApplication.instance.preferences
    val listNames = PasswordGeneratorApplication.instance.passwords.getListNames()
    var exportListName: String?
        get() = state["exportListName"]
        set(value) {
            state["exportListName"] = value
        }

    /* Этот метод используется для получения LiveData списка паролей: либо всех имеющихся (если listName == null),
    *  либо паролей из определенной папки  */
    fun getPasswords(listName: String?): LiveData<List<Password>> {
        return if (listName != null) {
            PasswordGeneratorApplication.instance.passwords.getPasswordsFromList(listName)
        } else PasswordGeneratorApplication.instance.passwords.getAllPasswords()
    }

    /* Этот метод используется для обновления настроек. Возвращает Pair, который имеет следующий
    *  смысл: <(обновлены_ли_настройки), (скорректированная_строка_если_есть)>. Вообще говоря,
    *  объект PasswordPreferences позволяет отдельно обновлять длину и набор символов, но для простоты
    *  здесь ограничимся одним методом. */
    fun updatePreferences(vocabularyInput: String, lengthInput: String): Pair<Boolean, String?> {
        if (!preferences.updatePassLength(lengthInput)) {
            return Pair(false, null)
        }

        return preferences.updateVocabulary(vocabularyInput)
    }

    fun generatePassword() {
        PasswordGeneratorApplication.instance.let {
            it.passwords.insertPassword(Password(randomPassword(
                it.preferences.vocabulary,
                it.preferences.passwordLength
            ),
                PasswordGeneratorApplication.instance.getString(R.string.generated_passwords_list_name)))
        }
    }
}