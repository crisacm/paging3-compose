package github.crisacm.composepaging3.domain.repository

import androidx.annotation.WorkerThread
import github.crisacm.composepaging3.domain.model.GitRepo

interface GitRepoRepo {
  @WorkerThread
  suspend fun fetchRepositories(username: String): Result<List<GitRepo>>
  suspend fun getRepositories(username: String): Result<List<GitRepo>>
}