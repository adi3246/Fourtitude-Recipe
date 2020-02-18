package com.fourtitude.asia.recipe

import android.app.Application
import android.os.AsyncTask
import com.fourtitude.asia.recipe.room.dao.RecipeDao
import com.fourtitude.asia.recipe.room.database.RecipeDatabase
import com.fourtitude.asia.recipe.room.entity.Recipe


class RecipeRepository(application: Application) {
    private val recipeDao: RecipeDao
    var allRecipes: List<Recipe>

    init {
        val database = RecipeDatabase.getInstance(application)
        recipeDao = database.recipeDao()
        allRecipes = recipeDao.allRecipesByType(0)
    }

    fun getRecipesByType(recipeType: Int): List<Recipe> {
        return recipeDao.allRecipesByType(recipeType)
    }

    fun insert(recipe: Recipe) {
        InsertRecipeAsyncTask(recipeDao).execute(recipe)
    }

    fun update(recipe: Recipe) {
        UpdateRecipeAsyncTask(recipeDao).execute(recipe)
    }

    fun delete(recipe: Recipe) {
        DeleteRecipeAsyncTask(recipeDao).execute(recipe)
    }

    fun deleteAllRecipes() {
        DeleteAllRecipesAsyncTask(recipeDao).execute()
    }

    private class InsertRecipeAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg recipes: Recipe): Void? {
            recipeDao.insert(recipes[0])
            return null
        }
    }

    private class UpdateRecipeAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg recipes: Recipe): Void? {
            recipeDao.update(recipes[0])
            return null
        }
    }

    private class DeleteRecipeAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg recipes: Recipe): Void? {
            recipeDao.delete(recipes[0])
            return null
        }
    }

    private class DeleteAllRecipesAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            recipeDao.deleteAllRecipes()
            return null
        }
    }
}