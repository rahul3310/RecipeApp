package com.recipeapp.ui_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularImageWithText(
    imageRes: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(100.dp)
    ) {
        ImageWithPlaceHolder(
            imageUrl = imageRes,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(90.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextMedium(
            text = label,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 2, // allow wrapping into two lines
        )
    }
}
