package github.crisacm.composepaging3.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.crisacm.composepaging3.data.repository.GitRepoRepoImp
import github.crisacm.composepaging3.domain.repository.GitRepoRepo

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
  @Binds
  fun bindsGitRepoRepo(impl: GitRepoRepoImp): GitRepoRepo
}
