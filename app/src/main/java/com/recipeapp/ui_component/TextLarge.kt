package com.recipeapp.ui_component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.recipeapp.ui.theme.TextColorPrimary

@Composable
fun TextLarge(
    modifier: Modifier = Modifier,
    text : String,
    color :Color = TextColorPrimary,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines : Int = 1,
    textAlign: TextAlign? = null
    ) {
    Text(text = text,
        fontSize = 18.sp,
        color = color,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines ,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
