package com.recipe.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recipe.data.entity.EquipmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipments(equipments: List<EquipmentEntity>)

    @Query("SELECT * FROM equipment WHERE recipeId = :recipeId")
    fun getEquipmentsForRecipe(recipeId: Int): Flow<List<EquipmentEntity>>

    @Query("DELETE FROM equipment WHERE recipeId = :recipeId")
    suspend fun deleteEquipmentsForRecipe(recipeId: Int)
}
