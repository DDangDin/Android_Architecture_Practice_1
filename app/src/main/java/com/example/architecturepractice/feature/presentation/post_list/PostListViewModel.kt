package com.example.architecturepractice.feature.presentation.post_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturepractice.core.util.Resource
import com.example.architecturepractice.feature.domain.use_case.GetPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PostListState())
    val state: State<PostListState> = _state

    init {
        getPostList()
    }

    private fun getPostList() {
        getPostListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PostListState(posts = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = PostListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value =
                        PostListState(error = result.message ?: "An unexpected error occured")
                }
            }
        }.launchIn(viewModelScope)
    }
}