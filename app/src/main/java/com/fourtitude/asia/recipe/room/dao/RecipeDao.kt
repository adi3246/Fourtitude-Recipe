package com.fourtitude.asia.recipe.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fourtitude.asia.recipe.room.entity.Recipe

@Dao
interface RecipeDao {

    @get:Query("SELECT * FROM recipe_table ORDER BY id DESC")
    val allRecipes: List<Recipe>

    @Query("SELECT * FROM recipe_table WHERE recipe_type = :recipeType ORDER BY id DESC")
    fun allRecipesByType(recipeType: Int): List<Recipe>

    @Insert
    fun insert(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("DELETE FROM recipe_table")
    fun deleteAllRecipes()
}