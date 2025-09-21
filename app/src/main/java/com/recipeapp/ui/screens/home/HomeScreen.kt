package com.recipeapp.ui.screens.home

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.recipe.data.model.Recipe
import com.recipeapp.MainActivity
import com.recipeapp.R
import com.recipeapp.navigation.Screen
import com.recipeapp.ui.screens.home.model.BottomSheetState
import com.recipeapp.ui.screens.home.model.HomeUiEvents
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.ButtonColorPrimary
import com.recipeapp.ui.theme.SurfaceColor
import com.recipeapp.ui.theme.TextColorSecondary
import com.recipeapp.ui.utils.PopularRecipeCard
import com.recipeapp.ui.utils.RecipeCard
import com.recipeapp.ui_component.HighlightedText
import com.recipeapp.ui_component.TextHeadline
import com.recipeapp.ui_component.TextMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val activity = LocalActivity.current
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeViewModel.uiEvent.collect { events ->
                when (events) {
                    is HomeUiEvents.NavigateToRecipeDetails -> {
                        navController.navigate(Screen.RecipeDetails.createRoute(events.recipeId))
                    }

                    is HomeUiEvents.OnSearchItemClick -> {
                        homeViewModel.updateBottomSheet(BottomSheetState.SearchBottomSheet(events.searchResult))
                    }
                }
            }
        }
    }
    DisposableEffect(Unit) {
        (activity as MainActivity).requestNotificationPermission()
        onDispose { }
    }

    Scaffold(
        topBar = {
            if (!isFocused) {
                TopAppBar(
                    modifier = Modifier.padding(vertical = 16.dp),
                    title = {
                        TopBarTitleView(scrollBehavior.state.collapsedFraction)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        scrolledContainerColor = Color.White
                    ),
                    scrollBehavior = scrollBehavior
                )
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = BackgroundColorPrimary
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            stickyHeader {
                SearchBar(
                    modifier = Modifier.background(
                        BackgroundColorPrimary
                    ),
                    searchQuery = uiState.searchQuery,
                    isFocused = isFocused,
                    onSearchQueryChanged = { homeViewModel.onSearchQueryChanged(it) },
                    onClearSearch = { homeViewModel.onSearchQueryChanged("") }
                ) { focusState ->
                    isFocused = focusState
                }

            }

            if (!isFocused) {
                item {
                    PopularRecipe(
                        uiState.popularRecipes,
                        onRecipeClick = {
                            homeViewModel.onRecipeClick(
                                Screen.RecipeDetails.route,
                                recipeId = it
                            )
                        }
                    )
                }

                item {
                    TextHeadline(
                        text = "All Recipe",
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 32.dp)
                    )
                }
                itemsIndexed(uiState.allRecipes) { index, recipe ->
                    RecipeCard(recipe = recipe,
                        modifier = Modifier.padding(horizontal = 16.dp)) {
                        homeViewModel.onRecipeClick(Screen.RecipeDetails.route, it)
                    }
                }
            }

            itemsIndexed(uiState.searchResult) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { homeViewModel.onSearchItemClick(item) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_recipe),
                        contentDescription = "Recipe Icon",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp)

                    )
                    HighlightedText(
                        fullText = item.title,
                        query = uiState.searchQuery
                    )
                }
            }
        }
    }

    HomeBottomSheet(
        uiState = uiState,
        onBackClick = {
            homeViewModel.updateBottomSheet(it)
        },
        onDismissRequest = {
            homeViewModel.updateBottomSheet(null)
        },
        onCloseClick = {
            homeViewModel.onSearchQueryChanged("")
            homeViewModel.updateBottomSheet(null)
        },
        onGetIngredientClick = {
            homeViewModel.updateBottomSheet(BottomSheetState.IngredientBottomSheet(it))
        },
        onRecipeClick = {
            homeViewModel.onRecipeClick(Screen.RecipeDetails.route, it.id)
            homeViewModel.updateBottomSheet(null)
            homeViewModel.onSearchQueryChanged("")
            isFocused = false
        },
        onFavClick = {
            homeViewModel.onFavoriteClick(it)
        },
        onGetSimilarRecipeClick = {
            homeViewModel.updateBottomSheet(BottomSheetState.SimilarRecipeBottomSheet(it))

        }
    )


}

@Composable
private fun TopBarTitleView(collapsedFraction: Float) {
    val isCollapsed = collapsedFraction > 0.5f

    AnimatedVisibility(visible = !isCollapsed) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TextHeadline(
                text = "\uD83D\uDC4B Hey Rahul",
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextMedium(
                text = "Discover tasty and healthy recipes",
                color = TextColorSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
private fun SearchBar(
    modifier: Modifier,
    searchQuery: String,
    isFocused: Boolean,
    onSearchQueryChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // Click outside -> clear focus & hide keyboard
                onClearSearch()
                focusManager.clearFocus()
                keyboardController?.hide()
            }
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChanged(it) },
            placeholder = { Text("Search Any Recipe") },
            leadingIcon = {
                Icon(
                    if (isFocused) Icons.AutoMirrored.Default.ArrowBack else Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.clickable {
                        if (isFocused) {
                            onClearSearch()
                            focusManager.clearFocus()
                        }
                    }
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    Icon(
                        Icons.Default.Close, contentDescription = "Search",
                        modifier = Modifier.clickable {
                            onClearSearch()
                            focusManager.clearFocus()
                        })
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ButtonColorPrimary,
                unfocusedBorderColor = SurfaceColor,
                disabledBorderColor = SurfaceColor,
                focusedContainerColor = Color(0xFFFEF7F3),
                unfocusedContainerColor = SurfaceColor
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        )
    }
}


@Composable
private fun PopularRecipe(
    popularRecipes: List<Recipe>,
    onRecipeClick: (recipeId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColorPrimary)
    ) {
        if (popularRecipes.isNotEmpty()) {
            TextHeadline(
                text = "Popular Recipe",
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyRow(
            modifier = Modifier
        ) {
            itemsIndexed(popularRecipes) { index, recipe ->
                PopularRecipeCard(
                    recipe,
                    onRecipeClick = { onRecipeClick(recipe.id) })
            }
        }
    }
}