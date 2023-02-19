package com.example.architecturepractice.feature.domain.repository

import com.example.architecturepractice.core.util.Resource
import com.example.architecturepractice.feature.data.remote.dto.PostDto
import com.example.architecturepractice.feature.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPost(): List<PostDto>

    suspend fun getPostById(postId: String): PostDto
}