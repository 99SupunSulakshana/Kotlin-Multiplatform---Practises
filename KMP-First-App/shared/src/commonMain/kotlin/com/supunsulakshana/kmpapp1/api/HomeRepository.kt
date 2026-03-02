package com.supunsulakshana.kmpapp1.api

import com.supunsulakshana.kmpapp1.data.RandomUser
import com.supunsulakshana.kmpapp1.data.RandomUserResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

class HomeRepository {

    suspend fun getRandomUsers() : RandomUserResponse {
        val response = httpClient.get(
            urlString = "https://randomuser.me/api/?page=1&results=20&seed=abc",
        )
        return response.body()
    }

    fun getUsers() = flow {
        emit(getRandomUsers().results)
    }

    suspend fun getUsersWithoutFlow() : List<RandomUser>  {
        return getRandomUsers().results
    }
}