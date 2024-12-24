package com.example.split_eat.data.remote

import com.example.split_eat.data.local.TokenStorage
import com.example.split_eat.data.model.auth.UpdateAccessTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = tokenStorage.getRefreshToken()

        if (refreshToken != null) {
            val newAccessToken = runBlocking {
                try {
                    val tokenResponse = authApi.updateAccessToken(UpdateAccessTokenRequest(refreshToken))
                    if (tokenResponse.isSuccessful) {
                        tokenResponse.body()?.access
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }

            if (newAccessToken != null) {
                tokenStorage.updateAccessToken(newAccessToken)
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
            }
        }

        tokenStorage.clear()
        return null
    }
}
