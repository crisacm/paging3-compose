package github.crisacm.composepaging3.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.crisacm.composepaging3.data.api.clients.GithubApiClient
import github.crisacm.composepaging3.data.api.clients.GithubApiClientImp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val TIME_OUT = 60_000

  @Provides
  @Singleton
  fun provideKtorApiClient(): HttpClient = HttpClient(Android) {
    install(ContentNegotiation) {
      json(Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
      })
    }

    install(Logging) {
      level = LogLevel.ALL
      logger = object : Logger {
        override fun log(message: String) {
          Log.i("HttpLogging:", message)
        }
      }
    }

    engine {
      connectTimeout = TIME_OUT
      socketTimeout = TIME_OUT
    }

    install(ResponseObserver) {
      onResponse { response ->
        Log.d("HTTP status:", "${response.status.value}")
      }
    }

    install(DefaultRequest) {
      header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
  }

  @Provides
  @Singleton
  fun providesGithubApiClient(httpClient: HttpClient): GithubApiClient = GithubApiClientImp(httpClient)
}
