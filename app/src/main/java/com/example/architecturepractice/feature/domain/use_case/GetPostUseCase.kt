package com.example.architecturepractice.feature.domain.use_case

import android.util.Log
import com.example.architecturepractice.core.util.Resource
import com.example.architecturepractice.feature.domain.model.Post
import com.example.architecturepractice.feature.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(postId: String): Flow<Resource<Post>> = flow {
        try {
            emit(Resource.Loading<Post>())
            val post = repository.getPostById(postId).toPost()
            emit(Resource.Success<Post>(post))
        } catch (e: HttpException) {
            emit(Resource.Error<Post>(e.localizedMessage ?: "HTTP error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<Post>("Internet Connection error occured"))
        }
    }
}