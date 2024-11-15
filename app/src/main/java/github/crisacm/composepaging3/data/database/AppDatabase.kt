package github.crisacm.composepaging3.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import github.crisacm.composepaging3.data.database.dao.GitRepoDao
import github.crisacm.composepaging3.data.database.entity.GitRepoEntity

@Database(entities = [GitRepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun gitRepoDao(): GitRepoDao

  companion object {
    fun getInstance(context: Context) = Room.databaseBuilder(
      context.applicationContext,
      AppDatabase::class.java,
      "app_database"
    ).build()
  }
}
