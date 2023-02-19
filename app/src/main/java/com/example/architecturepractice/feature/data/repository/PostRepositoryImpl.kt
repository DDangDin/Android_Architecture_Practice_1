package com.example.architecturepractice.feature.data.repository

import android.util.Log
import com.example.architecturepractice.core.util.Resource
import com.example.architecturepractice.feature.data.remote.PostApi
import com.example.architecturepractice.feature.data.remote.dto.PostDto
import com.example.architecturepractice.feature.domain.model.Post
import com.example.architecturepractice.feature.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostApi
): PostRepository {
    override suspend fun getPost(): List<PostDto> {
        return api.getPost()
    }

    override suspend fun getPostById(postId: String): PostDto {
        return api.getPostById(postId)
    }
}