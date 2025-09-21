package com.recipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.recipe.data.dao.*
import com.recipe.data.entity.*

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        NutrientEntity::class,
        InstructionEntity::class,
        EquipmentEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun nutrientDao(): NutrientDao
    abstract fun instructionDao(): InstructionDao
    abstract fun equipmentDao(): EquipmentDao
}
