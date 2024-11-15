package github.crisacm.composepaging3.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "git_repos")
data class GitRepoEntity(
  @PrimaryKey var id: String,
  var name: String,
  var owner: String
)
