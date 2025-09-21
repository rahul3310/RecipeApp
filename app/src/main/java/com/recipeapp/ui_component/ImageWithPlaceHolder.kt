package com.recipeapp.ui_component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.recipeapp.R

@Composable
fun ImageWithPlaceHolder(
    modifier: Modifier,
    imageUrl: String,
    errorState: Int = R.drawable.no_image,
    onError: () -> Unit = {},
    onSuccess: () -> Unit = {},
    contentScale: ContentScale = ContentScale.Inside
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = if (errorState != 0) painterResource(errorState) else null,
        contentDescription = "image",
        contentScale = contentScale,
        error = if (errorState != 0) painterResource(errorState) else null,
        onLoading = { onError() },
        onSuccess = { onSuccess() },
        onError = { onError() },
        modifier = modifier
    )
}