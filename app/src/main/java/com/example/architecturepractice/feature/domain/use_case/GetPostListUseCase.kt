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

class GetPostListUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> = flow {
        try {
            emit(Resource.Loading())
            val post = repository.getPost().map { it.toPost() }
            emit(Resource.Success(post))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Internet Connection error occured"))
        }
    }
}