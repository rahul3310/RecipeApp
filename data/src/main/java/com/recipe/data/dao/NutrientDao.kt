package com.recipe.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recipe.data.entity.NutrientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NutrientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrients(nutrients: List<NutrientEntity>)

    @Query("SELECT * FROM nutrients WHERE recipeId = :recipeId")
    fun getNutrientsForRecipe(recipeId: Int): Flow<List<NutrientEntity>>

    @Query("DELETE FROM nutrients WHERE recipeId = :recipeId")
    suspend fun deleteNutrientsForRecipe(recipeId: Int)
}
