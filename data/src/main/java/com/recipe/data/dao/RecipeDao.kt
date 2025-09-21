package com.recipe.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.recipe.data.entity.RecipeEntity
import com.recipe.data.entity.RecipeWithDetailsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: List<RecipeEntity>)

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("DELETE FROM recipes")
    suspend fun clearRecipes()

    @Query("UPDATE recipes SET isFavorite = :isFav WHERE id = :recipeId")
    suspend fun updateFavorite(recipeId: Int, isFav: Boolean)

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeWithDetails(recipeId: Int): Flow<RecipeWithDetailsRelation?>

    @Query(
        """
    SELECT * 
    FROM recipes
    WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%'
    ORDER BY id DESC
"""
    )
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>
}
