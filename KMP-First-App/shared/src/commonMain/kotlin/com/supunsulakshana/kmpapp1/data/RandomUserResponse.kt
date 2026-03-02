package com.supunsulakshana.kmpapp1.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(
    @SerialName("info")
    val info: Info? = null,
    @SerialName("results")
    val results: List<RandomUser> = emptyList()
)