package com.fourtitude.asia.recipe.base

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.fourtitude.asia.recipe.BR
import com.fourtitude.asia.recipe.utils.BindableDelegates

open class ParentBaseObservable : BaseObservable() {

    @get:Bindable
    var loadingProgress: Boolean by BindableDelegates(false, BR.loadingProgress)
}