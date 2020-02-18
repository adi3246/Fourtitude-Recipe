package com.fourtitude.asia.recipe.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "recipe_table")
class Recipe(val recipe_image: String, val recipe_name: String, val recipe_ingredient: String, val recipe_steps: String, val recipe_type: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}