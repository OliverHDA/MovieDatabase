package ru.oliverhd.moviedatabase.app

import android.app.Application
import androidx.room.Room
import ru.oliverhd.moviedatabase.room.FavoriteDao
import ru.oliverhd.moviedatabase.room.HistoryDao
import ru.oliverhd.moviedatabase.room.MovieDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: MovieDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                synchronized(MovieDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MovieDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }

        fun getFavoriteDao(): FavoriteDao {
            if (db == null) {
                synchronized(MovieDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MovieDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.favoriteDao()
        }
    }
}