package com.example.architecturepractice.feature.presentation.post

import com.example.architecturepractice.feature.domain.model.Post

data class PostState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val error: String = "",
    val isActive: Boolean = false
)
