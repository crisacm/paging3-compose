package github.crisacm.composepaging3.data.api.clients

import github.crisacm.composepaging3.data.api.model.RepoResponses

interface GithubApiClient {
  suspend fun fetchRepositories(username: String): List<RepoResponses>
}
