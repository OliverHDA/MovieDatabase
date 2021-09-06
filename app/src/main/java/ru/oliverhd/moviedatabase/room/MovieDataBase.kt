package ru.oliverhd.moviedatabase.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao
}
