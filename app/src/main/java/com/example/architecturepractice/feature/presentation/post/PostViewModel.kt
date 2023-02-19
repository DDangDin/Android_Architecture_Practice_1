package com.example.architecturepractice.feature.presentation.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturepractice.core.util.Constants
import com.example.architecturepractice.core.util.Resource
import com.example.architecturepractice.feature.domain.model.Post
import com.example.architecturepractice.feature.domain.use_case.GetPostUseCase
import com.example.architecturepractice.feature.presentation.post_list.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(PostState())
    val state: State<PostState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        if (query.isNotBlank() && query.isNotEmpty()) {
            // TextField에 검색했다가 지우면 오류나서
            // 임시적으로 빈칸이 아닐 때만 검색허용
            // 이렇게 처리하면 빈칸일 때 전에 검색했던 내용이 그대로 떠있음
            _state.value = PostState(isActive = true)
            searchJob = viewModelScope.launch {
                delay(Constants.SEARCH_QUERY_DELAY)
                getPostUseCase(query)
                    .onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value =
                                    PostState(
                                        post = result.data ?: Post("", 1, ""),
                                        isLoading = false,
                                        isActive = true
                                    )
                            }
                            is Resource.Error -> {
                                _state.value =
                                    PostState(
                                        error = result.message ?: "An unexpected error occured",
                                        isLoading = false,
                                        isActive = true
                                    )
                                _eventFlow.emit(
                                    UIEvent.ShowSnackbar(
                                        result.message ?: "Unknown error"
                                    )
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = PostState(isLoading = true, isActive = true)
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        } else {
            _state.value = PostState(isActive = false)
        }
    }


    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}

