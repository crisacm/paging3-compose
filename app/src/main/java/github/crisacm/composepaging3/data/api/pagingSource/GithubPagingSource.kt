package github.crisacm.composepaging3.data.api.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import github.crisacm.composepaging3.data.api.clients.GithubApiClient
import github.crisacm.composepaging3.data.mapper.asGitRepo
import github.crisacm.composepaging3.domain.model.GitRepo

const val INITIAL_PAGE = 1
const val PAGE_SIZE = 20

class GithubPagingSource(
  private val username: String,
  private val githubApiClient: GithubApiClient,
  // private val apiGithubService: ApiGithubService,
) : PagingSource<Int, GitRepo>() {

  override fun getRefreshKey(state: PagingState<Int, GitRepo>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitRepo> {
    return try {
      val page = params.key ?: INITIAL_PAGE
      val response = githubApiClient.fetchRepositories(username, page, PAGE_SIZE)

      LoadResult.Page(
        data = response.map { it.asGitRepo() },
        prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
        nextKey = if (response.isEmpty()) null else page.plus(1),
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }
}