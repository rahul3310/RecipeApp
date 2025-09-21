package com.recipe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "instructions",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,             // parent table
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE       // delete instructions if recipe is deleted
        )
    ],
    indices = [
        Index(value = ["recipeId"]),           // for joins
        Index(value = ["stepText"])            // optional: allows LIKE searches on text
    ]
)
data class InstructionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "stepNumber")
    val stepNumber: Int,

    @ColumnInfo(name = "stepText")
    val stepText: String
)
