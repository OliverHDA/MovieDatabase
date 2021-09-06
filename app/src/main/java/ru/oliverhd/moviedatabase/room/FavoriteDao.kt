package ru.oliverhd.moviedatabase.room

import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoriteEntity")
    fun allFavorite(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(entity: FavoriteEntity)

    @Update
    fun updateFavorite(entity: FavoriteEntity)

    @Delete
    fun deleteFavorite(entity: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity")
    fun clearFavorites()

    @Query("DELETE FROM FavoriteEntity WHERE movie_id = :movieId")
    fun deleteByMovieId(movieId: Int)

    @Query("SELECT EXISTS (SELECT movie_id FROM FavoriteEntity WHERE movie_id = :movieId)")
    fun checkFavorite(movieId: Int): Boolean
}