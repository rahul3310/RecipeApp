package com.recipeapp.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recipe.data.model.Recipe
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.BorderColorSecondary
import com.recipeapp.ui.theme.SurfaceColor
import com.recipeapp.ui.theme.TextColorSecondary
import com.recipeapp.ui_component.ImageWithPlaceHolder
import com.recipeapp.ui_component.TextLarge
import com.recipeapp.ui_component.TextMedium
import com.recipeapp.ui_component.TextSmall

@Composable
fun PopularRecipeCard(
    recipe: Recipe,
    onRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(start = 16.dp)
            .size(width = 156.dp, height = 156.dp)
            .clickable {
                onRecipeClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ImageWithPlaceHolder(
                modifier = Modifier
                    .fillMaxSize(),
                imageUrl = recipe.image!!,
                errorState = 0,
                onError = { },
                onSuccess = {},
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                TextLarge(
                    text = recipe.title,
                    color = Color.White
                )
                TextMedium(
                    text = "Ready in ${recipe.readyInMinutes} min",
                    color = TextColorSecondary
                )
            }
        }
    }
}


@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onRecipeClick: (recipeId: Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        onClick = { onRecipeClick(recipe.id) },
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColorPrimary
        ),
        border = BorderStroke(1.dp, BorderColorSecondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            recipe.image?.let {
                ImageWithPlaceHolder(
                    modifier = Modifier.size(width = 100.dp, height = 100.dp),
                    imageUrl = it,
                    errorState = 0,
                    onError = { },
                    onSuccess = {},
                    contentScale = ContentScale.Crop
                )
            }

            // Texts at bottom
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextLarge(
                    text = recipe.title,
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextMedium(
                    text = "Ready in ${recipe.readyInMinutes} min",
                    color = TextColorSecondary
                )
            }
        }
    }
}