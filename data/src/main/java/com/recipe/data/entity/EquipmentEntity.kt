package com.recipe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "equipment",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,       // parent table
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE       // delete equipment if recipe is deleted
        )
    ],
    indices = [
        Index(value = ["recipeId"]),           // for joins
        Index(value = ["name"])                // for searching by equipment name
    ]
)
data class EquipmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image")
    val image: String? = null
)
