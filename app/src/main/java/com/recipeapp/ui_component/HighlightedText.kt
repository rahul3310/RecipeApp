package com.recipeapp.ui_component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.recipeapp.ui.theme.TextColorPrimary
import com.recipeapp.ui.theme.TextColorSecondary

@Composable
fun HighlightedText(fullText: String, query: String) {
    val lowerFull = fullText.lowercase()
    val lowerQuery = query.lowercase()
    val startIndex = lowerFull.indexOf(lowerQuery)

    if (startIndex == -1) {
        Text(fullText)
    } else {
        val endIndex = startIndex + query.length
        Text(
            buildAnnotatedString {
                append(fullText.substring(0, startIndex))
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = TextColorPrimary
                    )
                ) {
                    append(fullText.substring(startIndex, endIndex))
                }
                append(fullText.substring(endIndex))
            },
            color = TextColorSecondary,
            fontSize = 14.sp
        )
    }
}
