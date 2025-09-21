package com.recipeapp.ui_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> LazyColumnCustom(
    modifier: Modifier = Modifier,
    items: List<T>,
    content: @Composable ((index: Int, item: T) -> Unit),
    emptyListContent: @Composable (() -> Unit)
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (items.isEmpty()) {
            item {
                emptyListContent()
            }
        } else {
            itemsIndexed(items) { index, item ->
                content(index, item)
            }
        }
    }
}