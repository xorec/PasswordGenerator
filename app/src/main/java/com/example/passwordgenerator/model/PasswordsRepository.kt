package com.example.passwordgenerator.model

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.passwordgenerator.PasswordGeneratorApplication
import com.example.passwordgenerator.generateTimestampString
import com.example.passwordgenerator.model.db.Password
import com.example.passwordgenerator.model.db.PasswordDatabase
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.*
import java.io.FileOutputStream

const val ALL_PASSWORDS_EXPORT_KEY = "EXPORT_ALL"

/* Этот объект является единственным репозиторием приложения и управляет базой данных, он же отвечает
*  за экспортирование и импортирование списков. */
class PasswordsRepository private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: PasswordsRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: PasswordsRepository(context).also { instance = it }
            }
    }

    private val db: PasswordDatabase = Room.databaseBuilder(
        context,
        PasswordDatabase::class.java, "local-db").build()

    private val scope = MainScope()

    fun insertPassword(password: Password) {
        scope.launch(Dispatchers.IO) {
            db.passwordDao().insert(password)
        }
    }

    fun removeList(listName: String) {
        scope.launch(Dispatchers.IO) {
            db.passwordDao().deleteList(listName)
        }
    }

    fun removePassword(password: Password) {
        scope.launch(Dispatchers.IO) {
            db.passwordDao().delete(password)
        }
    }

    fun getListNames(): LiveData<List<String>> {
        return db.passwordDao().getListNames()
    }

    fun getPasswordsFromList(listName: String): LiveData<List<Password>> {
        return db.passwordDao().getAllFromList(listName)
    }

    fun getAllPasswords(): LiveData<List<Password>> {
        return db.passwordDao().getAll()
    }

    fun exportData(listName: String?, pfd: ParcelFileDescriptor) {
        if (listName == null) {
            return
        }

        scope.launch(Dispatchers.IO) {
            (if (listName != ALL_PASSWORDS_EXPORT_KEY) db.passwordDao().getAllFromListOnce(listName)
            else db.passwordDao().getAllOnce()).let {
                csvWriter().writeAll(it.map { password ->
                    listOf(password.password)
                }, FileOutputStream(pfd.fileDescriptor))
            }
        }
    }

    fun importData(uri: Uri) {
        scope.launch(Dispatchers.IO) {
            val inputStream = PasswordGeneratorApplication.instance.applicationContext.contentResolver.openInputStream(uri)!!
            try {
                val result: List<List<String>> = csvReader().readAll(inputStream)
                if (result[0][0] == "") {
                    return@launch
                }

                db.passwordDao().insertAll(result.map { it -> Password(it[0], generateTimestampString())})
            } catch (e: Exception) {
                return@launch
            }
        }
    }
}