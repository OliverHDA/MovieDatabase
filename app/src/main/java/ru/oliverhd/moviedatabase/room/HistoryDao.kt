package ru.oliverhd.moviedatabase.room

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun allHistory(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHistory(entity: HistoryEntity)

    @Update
    fun updateHistory(entity: HistoryEntity)

    @Delete
    fun deleteHistory(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    fun deleteHistoryById(id: Long)

    @Query("DELETE FROM HistoryEntity")
    fun clearHistory()
}
