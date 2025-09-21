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
fun TextMedium(
    modifier: Modifier = Modifier,
    text : String,
    color :Color = TextColorPrimary,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines : Int = 1,
    textAlign: TextAlign? = null
) {
    Text(text = text,
        fontSize = 16.sp,
        color = color,
        modifier = modifier,
        fontWeight= fontWeight,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

