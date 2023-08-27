package com.example.passwordgenerator

import android.app.Application
import com.example.passwordgenerator.model.PasswordsRepository
import com.example.passwordgenerator.model.PasswordPreferences

/* Наследуемся от класса Application, чтобы создать и держать singleton-объекты, которые необходимы
*  для работы приложения и время жизни которых совпадает со временем жизни процесса приложения.
*  В нашем случае это passwords - объект, который содержит соединение с локальной базой данных и
*  нужен чтобы взаимодействовать с ней, и preferences - объект, который содержит настройки и нужен
*  чтобы их запрашивать и обновлять. */
class PasswordGeneratorApplication: Application() {
    lateinit var passwords: PasswordsRepository
        private set
    lateinit var preferences: PasswordPreferences
        private set

    companion object {
        lateinit var instance: PasswordGeneratorApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = PasswordPreferences.getInstance(this)
        passwords = PasswordsRepository.getInstance(this)
    }
}