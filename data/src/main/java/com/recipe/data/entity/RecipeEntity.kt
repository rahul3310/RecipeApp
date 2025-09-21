package com.recipe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    indices = [
        Index(value = ["title"]), // for text search
        Index(value = ["instructions"]) // optional, only if needed
    ]
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "servings")
    val servings: Int? = null,

    @ColumnInfo(name = "pricePerServing")
    val pricePerServing: Double? = null,

    @ColumnInfo(name = "instructions")
    val instructions: String? = null,

    @ColumnInfo(name = "summary")
    val summary: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "readyInMinutes")
    val readyInMinutes: Int? = null,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)
