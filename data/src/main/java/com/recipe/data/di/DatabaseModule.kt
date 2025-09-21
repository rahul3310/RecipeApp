package com.recipe.data.di

import android.content.Context
import androidx.room.Room
import com.recipe.data.dao.*
import com.recipe.data.local.AppDatabase
import com.recipe.data.local.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "recipe_database"
        ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()

    @Provides
    fun provideIngredientDao(db: AppDatabase): IngredientDao = db.ingredientDao()

    @Provides
    fun provideNutrientDao(db: AppDatabase): NutrientDao = db.nutrientDao()

    @Provides
    fun provideInstructionDao(db: AppDatabase): InstructionDao = db.instructionDao()

    @Provides
    fun provideEquipmentDao(db: AppDatabase): EquipmentDao = db.equipmentDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(
        recipeDao: RecipeDao,
        ingredientDao: IngredientDao,
        nutrientDao: NutrientDao,
        instructionDao: InstructionDao,
        equipmentDao: EquipmentDao
    ): LocalDataSource {
        return LocalDataSource(
            recipeDao,
            ingredientDao,
            nutrientDao,
            instructionDao,
            equipmentDao
        )
    }


}
