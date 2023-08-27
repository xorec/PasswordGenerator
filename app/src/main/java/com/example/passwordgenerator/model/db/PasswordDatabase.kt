package com.example.passwordgenerator.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Password::class], version = 1, exportSchema = false)
@TypeConverters(PasswordDatabaseConverters::class)
abstract class PasswordDatabase: RoomDatabase() {
    abstract fun passwordDao(): PasswordDatabaseDao
}