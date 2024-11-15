package github.crisacm.composepaging3.data.mapper

import github.crisacm.composepaging3.data.api.model.RepoResponses
import github.crisacm.composepaging3.data.database.entity.GitRepoEntity
import github.crisacm.composepaging3.domain.model.GitRepo

fun RepoResponses.asGitRepo(): GitRepo {
  return GitRepo(
    id = id,
    name = name,
    owner = owner.login
  )
}

fun GitRepo.asEntity(): GitRepoEntity {
  return GitRepoEntity(
    id = id,
    name = name,
    owner = owner,
  )
}

fun GitRepoEntity.asDomain(): GitRepo {
  return GitRepo(
    id = id,
    name = name,
    owner = owner,
  )
}
