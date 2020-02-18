package com.tribehired.recipe

import com.fourtitude.asia.recipe.module.recipeList.RecipesListViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EngineersListViewModelUnitTest {

    private val engineersListViewModel =
        RecipesListViewModel()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getEngineer_valid() {
        val engineersListResponse = engineersListViewModel.fetchEngineersList()
        assertNotNull(engineersListResponse)
    }
}
