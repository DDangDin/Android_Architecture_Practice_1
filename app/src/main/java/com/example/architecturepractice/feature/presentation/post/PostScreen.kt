package com.example.architecturepractice.feature.presentation.post


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.architecturepractice.feature.domain.model.Post
import com.example.architecturepractice.feature.presentation.PostListItem
import com.example.architecturepractice.feature.presentation.post_list.PostListViewModel

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        state.post?.let { post ->
            PostItem(post = post)
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
fun testScreen() { // viewModel을 preview에서 어떻게 처리해야하는지 모르겠움...

    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        PostItem(post = Post("body...", 10, "title..."))
    }
}