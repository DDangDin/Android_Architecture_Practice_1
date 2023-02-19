package com.example.architecturepractice.feature.presentation.post_list

import com.example.architecturepractice.feature.domain.model.Post

data class PostListState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String = ""
)
