package github.crisacm.composepaging3.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import github.crisacm.composepaging3.data.api.clients.GithubApiClient
import github.crisacm.composepaging3.data.api.pagingSource.GithubPagingSource
import github.crisacm.composepaging3.data.database.dao.GitRepoDao
import github.crisacm.composepaging3.data.mapper.asDomain
import github.crisacm.composepaging3.domain.model.GitRepo
import github.crisacm.composepaging3.domain.repository.GitRepoRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitRepoRepoImp @Inject constructor(
  // private val apiGithubService: ApiGithubService,
  private val githubApiClient: GithubApiClient,
  private val gitRepoDao: GitRepoDao,
) : GitRepoRepo {

  @WorkerThread
  override suspend fun fetchRepositoriesPager(username: String): Flow<PagingData<GitRepo>> {
    return Pager(PagingConfig(pageSize = 10, enablePlaceholders = true)) {
      GithubPagingSource(username, githubApiClient)
    }.flow
  }

  override suspend fun getRepositories(username: String): Result<List<GitRepo>> {
    val list = gitRepoDao.getRepositories(username).map { it.asDomain() }

    return when (list.isEmpty()) {
      true -> Result.failure(Throwable("Empty list"))
      else -> Result.success(list)
    }
  }
}
