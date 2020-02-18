package com.fourtitude.asia.recipe.module.recipeList

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import com.fourtitude.asia.recipe.ApplicationClass
import com.fourtitude.asia.recipe.SingleLiveEvent
import com.fourtitude.asia.recipe.base.BaseViewModel
import com.fourtitude.asia.recipe.module.recipeList.model.RecipeModel
import com.fourtitude.asia.recipe.module.recipeList.model.RecipesListForm
import com.fourtitude.asia.recipe.room.entity.Recipe
import java.util.*


/**
 * Created by Isa Andi on 18/02/2020.
 * <p>
 * RecipesListViewModel where the business logic for fetching list of recipes.
 *
 * @author Isa Andi
 * @version 1
 * @see RecipeModel
 * @see Recipe
 */
class RecipesListViewModel: BaseViewModel()  {

    val recipesListForm = RecipesListForm()

    private var repository: RecipeRepository = RecipeRepository(ApplicationClass.contextApp as Application)
    private var allRecipes: List<Recipe>
    var recipeEntity: List<Recipe>? = null
    val recipesList: ArrayList<RecipeModel> = ArrayList()

    val updateRVAdapter = SingleLiveEvent<Boolean>()

    init {
        allRecipes = repository.allRecipes
    }

    fun insert(recipe: Recipe) {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) {
        repository.delete(recipe)
    }

    fun deleteAllRecipes() {
        repository.deleteAllRecipes()
    }

    fun getAllRecipes(): List<Recipe> {
        return allRecipes
    }

    fun getAllRecipesByType(recipeType: Int) {

        val execute = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void): String {
                recipeEntity = repository.getRecipesByType(recipeType)
                recipesList.clear()

                for (item in recipeEntity!!){
                    recipesList.add(RecipeModel(item))
                }
                return ""
            }

            override fun onPostExecute(msg: String) {
                updateRVAdapter.value = true
            }
        }
        execute.execute()
    }
}