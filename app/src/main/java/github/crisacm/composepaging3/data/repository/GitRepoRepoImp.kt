package github.crisacm.composepaging3.data.repository

import androidx.annotation.WorkerThread
import github.crisacm.composepaging3.data.api.clients.GithubApiClient
import github.crisacm.composepaging3.data.database.dao.GitRepoDao
import github.crisacm.composepaging3.data.mapper.asDomain
import github.crisacm.composepaging3.data.mapper.asEntity
import github.crisacm.composepaging3.data.mapper.asGitRepo
import github.crisacm.composepaging3.domain.model.GitRepo
import github.crisacm.composepaging3.domain.repository.GitRepoRepo
import javax.inject.Inject

class GitRepoRepoImp @Inject constructor(
  private val gitRepoDao: GitRepoDao,
  private val githubApiClient: GithubApiClient
) : GitRepoRepo {

  @WorkerThread
  override suspend fun fetchRepositories(username: String): Result<List<GitRepo>> {
    val list = githubApiClient.fetchRepositories(username).map { it.asGitRepo() }

    return when (list.isEmpty()) {
      true -> Result.failure(Throwable("Empty list"))
      else -> {
        list.onEach { gitRepoDao.insert(it.asEntity()) }
        Result.success(list)
      }
    }
  }

  override suspend fun getRepositories(username: String): Result<List<GitRepo>> {
    val list = gitRepoDao.getRepositories(username).map { it.asDomain() }

    return when (list.isEmpty()) {
      true -> Result.failure(Throwable("Empty list"))
      else -> Result.success(list)
    }
  }
}
