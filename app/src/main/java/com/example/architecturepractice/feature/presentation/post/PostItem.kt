package com.example.architecturepractice.feature.presentation.post

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.architecturepractice.feature.domain.model.Post

@Composable
fun PostItem(
    post: Post
) {

    var isLong by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = Color.LightGray.copy(alpha = .5f), shape = RoundedCornerShape(5.dp))
            .clickable {
                isLong = !isLong        // 연타 방지해야함
            }
            .animateContentSize(animationSpec = tween(250))
    ) {
        Text(
            text = "id: ${post.id} \ntitle: ${post.title} \nbody: ${post.body}",
            style = MaterialTheme.typography.body1,
            maxLines = if (isLong) 10 else 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp),
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun Test() {
    PostItem(post = Post("body...", 1, "title..."))
}