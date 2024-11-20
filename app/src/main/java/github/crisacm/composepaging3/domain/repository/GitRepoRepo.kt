package github.crisacm.composepaging3.domain.repository

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import github.crisacm.composepaging3.domain.model.GitRepo
import kotlinx.coroutines.flow.Flow

interface GitRepoRepo {
  @WorkerThread
  suspend fun fetchRepositoriesPager(username: String): Flow<PagingData<GitRepo>>
}
