package com.recipeapp.ui_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.recipe.data.model.Equipment
import com.recipe.data.model.Ingredient

@Composable
fun IngredientsGridView(
    modifier: Modifier = Modifier,
    ingredientsList: List<Ingredient>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 16.dp, start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(ingredientsList) { _, ingredient ->
            CircularImageWithText(
                imageRes = ingredient.image ?: "",
                label = ingredient.name
            )

        }
    }
}


@Composable
fun EquipmentLazyRow(equipmentsList: List<Equipment>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(equipmentsList) { index, ingredient ->
            ingredient.image?.let {
                CircularImageWithText(
                    imageRes = it,
                    label = ingredient.name
                )
            }
        }
    }
}