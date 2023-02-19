package com.example.architecturepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.architecturepractice.feature.presentation.PostListScreen
import com.example.architecturepractice.feature.presentation.post.PostScreen
import com.example.architecturepractice.feature.presentation.post.PostViewModel
import com.example.architecturepractice.feature.presentation.post_list.PostListViewModel
import com.example.architecturepractice.ui.theme.ArchitecturePracticeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitecturePracticeTheme {

                val postListViewModel: PostListViewModel = hiltViewModel()
                val postViewModel: PostViewModel = hiltViewModel()
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    postViewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is PostViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp)
                    ) {
                        TextField(
                            value = postViewModel.searchQuery.value,
                            onValueChange = postViewModel::onSearch,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            placeholder = {
                                Text(text = "Input post id")
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (postViewModel.state.value.isActive) {
                            PostScreen(postViewModel)
                        } else {
                            PostListScreen(postListViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArchitecturePracticeTheme {
        Greeting("Android")
    }
}