package com.recipe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "nutrients",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,             // Reference to parent table
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE      // Delete nutrients when recipe is deleted
        )
    ],
    indices = [
        Index(value = ["recipeId"]),  // for faster joins
        Index(value = ["name"])       // optional, in case you query by nutrient name
    ]
)
data class NutrientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "name")
    val name: String, // e.g. "Protein", "Calories"

    @ColumnInfo(name = "amount")
    val amount: Double? = null,

    @ColumnInfo(name = "unit")
    val unit: String? = null,

    @ColumnInfo(name = "percentOfDailyNeeds")
    val percentOfDailyNeeds: Double? = null
)
