package com.recipeapp.ui_component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.recipeapp.ui.theme.TextColorPrimary

@Composable
fun TextHeadline(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = TextColorPrimary,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign  = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = 20.sp,
        color = color,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        fontWeight = fontWeight,
        maxLines = 1,
        textAlign = textAlign
    )

}
