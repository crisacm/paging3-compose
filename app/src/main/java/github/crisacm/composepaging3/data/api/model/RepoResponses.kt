package github.crisacm.composepaging3.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RepoResponses(
  var id: String,
  val name: String,
  val owner: Owner,
)

@Serializable
data class Owner(
  val login: String,
)
