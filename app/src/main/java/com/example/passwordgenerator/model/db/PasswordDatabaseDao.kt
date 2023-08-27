package com.example.passwordgenerator.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/* В основном используются версии запросов, которые возвращают LiveData, что очень удобно, поскольку
   позволяет не хранить в памяти копию данных из базы данных, тем самым имеется
   single source of truth для отображения. one-shot версии запросов используются только для импорта и экспорта.*/
@Dao
interface PasswordDatabaseDao {
    @Query("SELECT * FROM password")
    fun getAll(): LiveData<List<Password>>

    @Query("SELECT * FROM password")
    suspend fun getAllOnce(): List<Password>

    @Query("SELECT * FROM password WHERE listName = :listName")
    fun getAllFromList(listName: String): LiveData<List<Password>>

    @Query("SELECT * FROM password WHERE listName = :listName")
    suspend fun getAllFromListOnce(listName: String): List<Password>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: Password)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(passwords: List<Password>)

    @Delete
    suspend fun delete(password: Password)

    @Query("SELECT DISTINCT listName FROM password")
    fun getListNames(): LiveData<List<String>>

    @Query("DELETE FROM password WHERE listName = :listName")
    suspend fun deleteList(listName: String)
}