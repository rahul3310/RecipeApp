package com.recipeapp.ui.screens.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.recipeapp.navigation.Screen
import com.recipeapp.ui.screens.favourites.model.FavouriteUiEvents
import com.recipeapp.ui.screens.recipeDetails.model.RecipeDetailsEvents
import com.recipeapp.ui.snackbar.SnackBarWithActon
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.BackgroundColorSecondary
import com.recipeapp.ui.utils.RecipeCard
import com.recipeapp.ui_component.LazyColumnCustom
import com.recipeapp.ui_component.SwipeableCard
import com.recipeapp.ui_component.TextHeadline
import com.recipeapp.ui_component.TextMedium
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collect { events ->
                when (events) {
                    is FavouriteUiEvents.NavigateToRecipeDetails -> {
                        navController.navigate(Screen.RecipeDetails.createRoute(events.recipeId))
                    }
                }
            }
        }
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
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    TextHeadline(
                        text = "Favourite Recipes"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColorSecondary,
                )
            )
        },
        containerColor = BackgroundColorPrimary
    ) { paddingValues ->

        LazyColumnCustom(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxSize()
                .padding(paddingValues),
            items = uiState.favouriteRecipeDetails,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            emptyListContent = {
                TextMedium(
                    text = "You have not selected any favorite recipe yet.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            },
            content = { index, recipe ->
                SwipeableCard(
                    item = recipe,
                    onDeleteClick = {
                        viewModel.removeFavorites(it.id)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Removed from Favorites",
                                withDismissAction = false,
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    content = {
                        RecipeCard(
                            recipe,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onRecipeClick = {
                                viewModel.onRecipeClick(Screen.RecipeDetails.route, recipe.id)
                            }
                        )
                    }
                )

            }
        )
    }
}