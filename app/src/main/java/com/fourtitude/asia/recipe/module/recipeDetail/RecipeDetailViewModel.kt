package com.fourtitude.asia.recipe.module.recipeDetail

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import com.fourtitude.asia.recipe.ApplicationClass
import com.fourtitude.asia.recipe.base.BaseViewModel
import com.fourtitude.asia.recipe.module.recipeDetail.model.RecipeDetailForm
import com.fourtitude.asia.recipe.RecipeRepository
import com.fourtitude.asia.recipe.module.recipeList.model.RecipeModel
import com.fourtitude.asia.recipe.room.entity.Recipe

/**
 * Created by Isa Andi on 18/02/2020.
 * <p>
 * RecipeDetailViewModel where the business logic for show recipe detail.
 *
 * @author Isa Andi
 * @version 1
 * @see RecipeModel
 * @see Recipe
 */
class RecipeDetailViewModel: BaseViewModel()  {

    val recipeDetailForm = RecipeDetailForm()
    var recipeType = 0
    var updateRecipeList = false
    private var repository: RecipeRepository =
        RecipeRepository(ApplicationClass.contextApp as Application)

    fun saveRecipe(){
        updateRecipeList = true

        if (recipeDetailForm.newRecipe)
            insert()
        else
            update()
    }

    fun delete() {
        val execute = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void): String {
                val recipe : Recipe = setupRecipeData()

                recipe.id = recipeDetailForm.recipeModel.id

                repository.delete(recipe)
                return ""
            }

            override fun onPostExecute(msg: String) {
                updateRecipeList = true
                statusMessage.value = "Recipe deleted"
            }
        }
        execute.execute()
    }

    fun setRecipeDetail(recipeModel: RecipeModel) {
        recipeDetailForm.recipeModel = recipeModel
    }

    fun setNewRecipe(isNew: Boolean) {
        recipeDetailForm.newRecipe = isNew
    }

    fun updateRecipeImage(path: String){
        val recipeModelTemp = recipeDetailForm.recipeModel
        recipeModelTemp.recipe_image = path

        recipeDetailForm.recipeModel = recipeModelTemp
    }

    private fun insert() {
        val execute = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void): String {
                val recipe : Recipe = setupRecipeData()
                repository.insert(recipe)
                return ""
            }

            override fun onPostExecute(msg: String) {
                statusMessage.value = "success"
            }
        }
        execute.execute()
    }

    private fun update() {
        val execute = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void): String {
                val recipe : Recipe = setupRecipeData()

                recipe.id = recipeDetailForm.recipeModel.id

                repository.update(recipe)
                return ""
            }

            override fun onPostExecute(msg: String) {
                statusMessage.value = "success"
            }
        }
        execute.execute()
    }

    private fun setupRecipeData():Recipe{
        return Recipe(recipeDetailForm.recipeModel.recipe_image, recipeDetailForm.recipeModel.recipe_name, recipeDetailForm.recipeModel.recipe_ingredient,
            recipeDetailForm.recipeModel.recipe_steps, recipeType)
    }
}