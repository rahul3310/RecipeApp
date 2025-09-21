package com.recipeapp.ui.screens.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.recipe.data.model.Equipment
import com.recipe.data.model.Ingredient
import com.recipe.data.model.Recipe
import com.recipeapp.ui.screens.home.model.BottomSheetState
import com.recipeapp.ui.screens.home.model.HomeUiState
import com.recipeapp.ui.screens.recipeDetails.RounderCardView
import com.recipeapp.ui.screens.recipeDetails.parseHtml
import com.recipeapp.ui.theme.BorderColor
import com.recipeapp.ui.theme.TextColorSecondary
import com.recipeapp.ui.utils.CommonBottomSheet
import com.recipeapp.ui.utils.RecipeCard
import com.recipeapp.ui_component.EquipmentLazyRow
import com.recipeapp.ui_component.ImageWithPlaceHolder
import com.recipeapp.ui_component.IngredientsGridView
import com.recipeapp.ui_component.TextLarge
import com.recipeapp.ui_component.TextMedium
import com.recipeapp.ui_component.TextSmall
import recipeassignment.ui.utils.CustomImageButton
import recipeassignment.ui.utils.LazyColumnCustom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    uiState: HomeUiState,
    onFavClick: (Recipe) -> Unit,
    onCloseClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onBackClick: (BottomSheetState) -> Unit,
    onRecipeClick: (Recipe) -> Unit,
    onGetIngredientClick: (Recipe) -> Unit,
    onGetSimilarRecipeClick: (Recipe) -> Unit
) {
    when (val state = uiState.bottomSheetState) {
        is BottomSheetState.IngredientBottomSheet -> {
            IngredientBottomSheet(
                recipeName = state.recipe.title,
                isFavSelected = uiState.recipeDetails?.recipe?.isFavorite ?: false,
                ingredients = uiState.recipeDetails?.ingredients ?: emptyList(),
                equipments = uiState.recipeDetails?.equipment ?: emptyList(),
                instructions = uiState.recipeDetails?.recipe?.instructions,
                summary = uiState.recipeDetails?.recipe?.summary,
                onBackClick = { onBackClick(BottomSheetState.SearchBottomSheet(state.recipe)) },
                onGetSimilarRecipeClick = {
                    onGetSimilarRecipeClick(state.recipe)
                },
                onFavClick = {
                    uiState.recipeDetails?.recipe?.let { onFavClick(it) }
                },
                onDismissRequest = onDismissRequest
            )
        }

        is BottomSheetState.SearchBottomSheet -> {
            SearchResultBottomSheet(
                recipe = state.recipe,
                onCloseClick = onCloseClick,
                onGetIngredientClick = onGetIngredientClick,
                onFavClick = onFavClick,
                onDismissRequest = onDismissRequest
            )
        }

        is BottomSheetState.SimilarRecipeBottomSheet -> {
            SimilarRecipeBottomSheet(
                recipes = uiState.allRecipes.take(5),
                onBackClick = {
                    onBackClick(BottomSheetState.IngredientBottomSheet(state.recipe))
                },
                onRecipeClick = onRecipeClick,
                recipeName = state.recipe.title,
                isFavSelected = state.recipe.isFavorite,
                onFavClick = {
                    onFavClick(state.recipe)
                },
                onDismissRequest = onDismissRequest,
            )
        }

        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultBottomSheet(
    recipe: Recipe,
    onCloseClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onFavClick: (Recipe) -> Unit,
    onGetIngredientClick: (Recipe) -> Unit
) {
    CommonBottomSheet(
        buttonText = "Get Ingredients",
        icon = Icons.Default.Close,
        onDismissRequest = onDismissRequest,
        iconArrangement = Arrangement.Center,
        onButtonClick = {
            onGetIngredientClick(recipe)
        },
        onIconClick = {
            onCloseClick()
        },
        content = {
            TitleWithFavIcon(
                title = recipe.title,
                onFavClick = {
                    onFavClick(recipe)
                },
                isFavSelected = recipe.isFavorite
            )

            ImageWithPlaceHolder(
                imageUrl = recipe.image!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(top = 22.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RounderCardView(
                    title = "ReadyIn",
                    value = recipe.readyInMinutes.toString() + " min"
                )
                RounderCardView(
                    title = "Servings",
                    value = recipe.servings.toString()
                )
                RounderCardView(
                    title = "Price/serving",
                    value = "₹${recipe.pricePerServing.toString()}"
                )

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientBottomSheet(
    recipeName: String,
    isFavSelected: Boolean,
    ingredients: List<Ingredient>,
    equipments: List<Equipment>,
    instructions: String?,
    summary: String?,
    onBackClick: () -> Unit,
    onFavClick: () -> Unit,
    onGetSimilarRecipeClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    CommonBottomSheet(
        buttonText = "Get Similar Recipe",
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        onButtonClick = onGetSimilarRecipeClick,
        onDismissRequest = onDismissRequest,
        onIconClick = onBackClick,
        content = {
            TitleWithFavIcon(
                title = "Ingredient & Recipe",
                subTitle = recipeName,
                onFavClick = onFavClick,
                isFavSelected = isFavSelected
            )

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    ExpandableContent(
                        modifier = Modifier.padding(top = 16.dp),
                        title = "Ingredients ",
                        content = {
                            IngredientsGridView(
                                modifier = Modifier,
                                ingredientsList = ingredients
                            )
                        }
                    )
                }

                item {
                    ExpandableContent(
                        modifier = Modifier.padding(top = 16.dp),
                        title = "Full Recipe ",
                        content = {
                            FullRecipeDetails(
                                equipments = equipments,
                                instructions = instructions,
                                summary = summary
                            )
                        }
                    )
                }

            }
        }
    )
}


@Composable
private fun ExpandableContent(
    modifier: Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    val isExpand = remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextLarge(
                text = title,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = { isExpand.value = !isExpand.value }) {
                Icon(
                    imageVector = if (isExpand.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "image"
                )
            }
        }

        AnimatedVisibility(
            visible = isExpand.value,
            exit = shrinkVertically(),
            enter = expandVertically()
        ) {
            content()
        }


    }
}

@Composable
private fun FullRecipeDetails(
    equipments: List<Equipment>,
    instructions: String?,
    summary: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextMedium(
            text = "Instructions",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        TextSmall(
            text = parseHtml(instructions),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = TextColorSecondary
        )


        TextMedium(
            text = "Equipment",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        EquipmentLazyRow(equipments)


        TextMedium(
            text = "Quick Summary",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        TextSmall(
            text = parseHtml(summary),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(100.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SimilarRecipeBottomSheet(
    recipes: List<Recipe>,
    recipeName: String,
    isFavSelected: Boolean,
    onRecipeClick: (Recipe) -> Unit,
    onFavClick: () -> Unit,
    onBackClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    CommonBottomSheet(
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        onIconClick = onBackClick,
        onDismissRequest = onDismissRequest,
        content = {
            TitleWithFavIcon(
                title = "Similar Recipes",
                subTitle = recipeName,
                onFavClick = onFavClick,
                isFavSelected = isFavSelected
            )


            LazyColumnCustom(
                items = recipes,
                modifier = Modifier,
                emptyListContent = {},
                content = { index, item ->
                    RecipeCard(recipe = item, modifier = Modifier) {
                        onRecipeClick(item)
                    }
                }
            )
        }
    )
}

@Composable
private fun TitleWithFavIcon(
    title: String,
    subTitle: String? = null,
    isFavSelected: Boolean,
    onFavClick: () -> Unit
) {
    var isFavMarked by remember { mutableStateOf(isFavSelected) }
    Column(
        modifier = Modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                TextLarge(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                )
                if (subTitle != null) {
                    TextMedium(
                        text = subTitle,
                        color = TextColorSecondary,
                        modifier = Modifier.padding(top = 4.dp),
                        maxLines = 1,
                    )
                }
            }
            CustomImageButton(
                icon = if (isFavMarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                onClick = {
                    isFavMarked = !isFavMarked
                    onFavClick()
                },
                tint = if (isFavMarked) Color.Red else Color.Black,
                border = BorderStroke(1.dp, BorderColor)
            )
        }


        HorizontalDivider(
            thickness = 1.dp,
            color = BorderColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }

}