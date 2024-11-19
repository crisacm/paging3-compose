package github.crisacm.composepaging3.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import github.crisacm.composepaging3.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context: Context
  ): AppDatabase = AppDatabase.getInstance(context)

  @Provides
  @Singleton
  fun provideGitRepoDao(
    appDatabase: AppDatabase
  ) = appDatabase.gitRepoDao()
}
