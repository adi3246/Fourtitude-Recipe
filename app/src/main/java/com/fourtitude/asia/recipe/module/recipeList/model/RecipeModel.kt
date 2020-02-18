package com.fourtitude.asia.recipe.module.recipeList.model

import com.fourtitude.asia.recipe.room.entity.Recipe
import java.io.Serializable



class RecipeModel(): Serializable {

    var id: Int = 0

    var recipe_name: String = ""

    var recipe_image: String = ""

    var recipe_ingredient: String = ""

    var recipe_steps: String = ""

    var recipe_type: Int = 0

    constructor(data: Recipe): this(){

        this.id = data.id

        this.recipe_name = data.recipe_name

        this.recipe_image = data.recipe_image

        this.recipe_ingredient = data.recipe_ingredient

        this.recipe_steps = data.recipe_steps

        this.recipe_type = data.recipe_type
    }
}