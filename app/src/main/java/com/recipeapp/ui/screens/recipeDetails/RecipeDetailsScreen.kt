package com.recipeapp.ui.screens.recipeDetails

import HtmlText
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.recipe.data.model.Equipment
import com.recipe.data.model.Ingredient
import com.recipeapp.ui.screens.recipeDetails.model.EnumTime
import com.recipeapp.ui.screens.recipeDetails.model.RecipeDetailsEvents
import com.recipeapp.ui.snackbar.SnackBarWithActon
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.BackgroundColorSecondary
import com.recipeapp.ui.theme.BorderColor
import com.recipeapp.ui.theme.ButtonColorPrimary
import com.recipeapp.ui.theme.TextColorSecondary
import com.recipeapp.ui.utils.CommonBottomSheet
import com.recipeapp.ui_component.CircularImageWithText
import com.recipeapp.ui_component.EquipmentLazyRow
import com.recipeapp.ui_component.ImageWithPlaceHolder
import com.recipeapp.ui_component.IngredientsGridView
import com.recipeapp.ui_component.TextHeadline
import com.recipeapp.ui_component.TextMedium
import com.recipeapp.ui_component.TextSmall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import recipeassignment.ui.utils.CustomImageButton
import java.net.URL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int,
    recipeDetailViewModel: RecipeDetailsScreenViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()

    // Detect if user has scrolled down
    val isTopBarVisible by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0 }
    }
    val uiState by recipeDetailViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        recipeDetailViewModel.onEvent(RecipeDetailsEvents.LoadRecipes(recipeId))
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            ) {
                SnackBarWithActon(
                    it,
                    dismissIcon = Icons.Default.Add
                ) {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    if (snackbarHostState.currentSnackbarData?.visuals?.withDismissAction == true)
                        recipeDetailViewModel.onEvent(RecipeDetailsEvents.AddReminderClick)
                }
            }
        },
        topBar = {
            AnimatedVisibility(
                visible = !isTopBarVisible,
                exit = shrinkVertically(),
                enter = expandVertically()
            ) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        CustomImageButton(
                            icon = Icons.AutoMirrored.Default.ArrowBack,
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .border(1.dp, BorderColor, RoundedCornerShape(8.dp))
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }

        },
        containerColor = BackgroundColorSecondary
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            stickyHeader {
                uiState.recipeDetails?.recipe?.image?.let {
                    TopHeader(
                        it,
                        showIcons = isTopBarVisible,
                        isFavSelected = uiState.isMarkedFav,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onFavClick = {
                            recipeDetailViewModel.onEvent(RecipeDetailsEvents.ToggleFavorite)
                            val added = uiState.isMarkedFav
                            snackbarHostState.currentSnackbarData?.dismiss()
                            if (added) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Removed from Favorites",
                                        withDismissAction = false,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Added to Favorites",
                                        actionLabel = "Add Reminder",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        })
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RounderCardView(
                        title = "ReadyIn",
                        value = uiState.recipeDetails?.recipe?.readyInMinutes.toString() + " min"
                    )
                    RounderCardView(
                        title = "Servings",
                        value = uiState.recipeDetails?.recipe?.servings.toString()
                    )
                    RounderCardView(
                        title = "Price/serving",
                        value = "₹${uiState.recipeDetails?.recipe?.pricePerServing.toString()}"
                    )

                }
            }

            item {
                FullRecipeDetails(
                    ingredients = uiState.recipeDetails?.ingredients ?: emptyList(),
                    equipments = uiState.recipeDetails?.equipment ?: emptyList(),
                    instructions = uiState.recipeDetails?.recipe?.instructions,
                    summary = uiState.recipeDetails?.recipe?.summary
                )
            }

        }
    }

    if (uiState.showBottomSheet) {
        CommonBottomSheet(
            iconArrangement = Arrangement.Center,
            onIconClick = {
                recipeDetailViewModel.onEvent(RecipeDetailsEvents.DismissBottomSheet)
            },
            content = {
                BottomSheetContent { enumTime ->
                    recipeDetailViewModel.onEvent(RecipeDetailsEvents.OnTimeSelected(enumTime))
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "You will be reminded ${enumTime.text}",
                            actionLabel = "Ok",
                            duration = SnackbarDuration.Short
                        )
                    }

                }
            }
        )
    }
}


@Composable
private fun FullRecipeDetails(
    ingredients: List<Ingredient>,
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
            text = "Ingredients",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        IngredientsGridView(ingredientsList = ingredients)


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


@Composable
private fun BottomSheetContent(
    onTimeSelected: (EnumTime) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextHeadline(
            text = "Set a Reminder",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        TextSmall(
            text = "You will be reminded in",
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            color = TextColorSecondary
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = BorderColor,
            modifier = Modifier.padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EnumTime.entries.forEach { item ->
                TextMedium(
                    text = item.text,
                    color = ButtonColorPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .clickable { onTimeSelected(item) }
                )
            }
        }

    }
}


@Composable
fun RounderCardView(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    width: Dp = 120.dp,
    height: Dp = 80.dp,
    titleStyle: TextStyle = TextStyle(fontSize = 12.sp, color = Color.Gray),
    valueStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF6600)
    ),
    shape: CornerBasedShape = RoundedCornerShape(12.dp),
    elevation: Dp = 4.dp,
    backgroundColor: Color = BackgroundColorPrimary,
    contentPadding: Dp = 8.dp
) {
    Card(
        modifier = modifier
            .width(width)
            .height(height),
        shape = shape,
        border = BorderStroke(1.dp, BorderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = titleStyle)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = valueStyle)
        }
    }
}


@Composable
fun NativeUrlImage(url: String, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            try {
                val stream = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Composable
fun TopHeader(
    imageUrl: String,
    showIcons: Boolean,
    isFavSelected: Boolean,
    onFavClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ImageWithPlaceHolder(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imageUrl,
            errorState = 0,
            onError = { },
            onSuccess = {},
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = showIcons,
            exit = fadeOut(),
            enter = fadeIn()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomImageButton(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = onBackClick
                )
                CustomImageButton(
                    icon = if (isFavSelected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    onClick = onFavClick,
                    tint = if (isFavSelected) Color.Red else Color.Black
                )
            }
        }

        Text(
            "Sahi Paneer",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

fun parseHtml(summary: String?): String {
    if (summary.isNullOrBlank()) return "No content fount"
    return HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}