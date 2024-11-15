package github.crisacm.composepaging3.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.crisacm.composepaging3.data.database.entity.GitRepoEntity

@Dao
interface GitRepoDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(repos: GitRepoEntity)

  @Query("SELECT * FROM git_repos WHERE owner = :username")
  suspend fun getRepositories(username: String): List<GitRepoEntity>
}
