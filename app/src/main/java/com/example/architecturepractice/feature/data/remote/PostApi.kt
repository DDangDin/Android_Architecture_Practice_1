package com.example.architecturepractice.feature.data.remote

import com.example.architecturepractice.feature.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("/posts")
    suspend fun getPost(): List<PostDto>

    @GET("/posts/{postId}")
    suspend fun getPostById(
        @Path("postId") postId: String
    ): PostDto
}