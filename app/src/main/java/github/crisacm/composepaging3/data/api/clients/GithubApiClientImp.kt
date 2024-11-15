package github.crisacm.composepaging3.data.api.clients

import github.crisacm.composepaging3.data.api.model.RepoResponses
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import javax.inject.Inject

class GithubApiClientImp @Inject constructor(
  private val httpClient: HttpClient
) : GithubApiClient {

  override suspend fun fetchRepositories(username: String): List<RepoResponses> {
    val url = URLBuilder().apply {
      takeFrom("https://api.github.com/users/${username}/repos")
    }

    return httpClient.get(url.build()).body<List<RepoResponses>>()
  }
}
