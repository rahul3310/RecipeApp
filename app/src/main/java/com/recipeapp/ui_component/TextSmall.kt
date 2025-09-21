package com.recipeapp.ui_component


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.recipeapp.ui.theme.TextColorPrimary

@Composable
fun TextSmall(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = TextColorPrimary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
    )

}

@Composable
fun TextExtraSmall(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = TextColorPrimary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        fontSize = 13.sp,
        color = color,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )

}