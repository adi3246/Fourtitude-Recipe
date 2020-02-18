package com.fourtitude.asia.recipe.base

import androidx.lifecycle.ViewModel
import com.fourtitude.asia.recipe.SingleLiveEvent

open class BaseViewModel: ViewModel() {

    val statusMessage = SingleLiveEvent<String>()

}