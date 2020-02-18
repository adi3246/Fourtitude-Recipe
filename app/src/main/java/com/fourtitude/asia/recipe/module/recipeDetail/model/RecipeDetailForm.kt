package com.fourtitude.asia.recipe.module.recipeDetail.model

import androidx.databinding.Bindable
import com.fourtitude.asia.recipe.BR
import com.fourtitude.asia.recipe.base.ParentBaseObservable
import com.fourtitude.asia.recipe.module.recipeList.model.RecipeModel
import com.fourtitude.asia.recipe.utils.BindableDelegates

class RecipeDetailForm : ParentBaseObservable() {
    @get:Bindable
    var recipeModel: RecipeModel by BindableDelegates(RecipeModel(), BR.recipeModel)

    @get:Bindable
    var newRecipe: Boolean by BindableDelegates(true, BR.newRecipe)
}