package com.recipeapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object RecipeDetails : Screen("recipe_details/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe_details/$recipeId"
    }

    companion object {
        val allTabs = listOf(
            TabItem(
                route = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            TabItem(
                route = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder
            )
        )
    }
}

data class TabItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)
