package com.recipe.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recipe.data.entity.InstructionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InstructionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructions(instructions: List<InstructionEntity>)

    @Query("SELECT * FROM instructions WHERE recipeId = :recipeId ORDER BY stepNumber ASC")
    fun getInstructionsForRecipe(recipeId: Int): Flow<List<InstructionEntity>>

    @Query("DELETE FROM instructions WHERE recipeId = :recipeId")
    suspend fun deleteInstructionsForRecipe(recipeId: Int)
}
