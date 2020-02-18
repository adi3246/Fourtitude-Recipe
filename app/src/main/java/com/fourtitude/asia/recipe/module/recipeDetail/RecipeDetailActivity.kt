package com.fourtitude.asia.recipe.module.recipeDetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fourtitude.asia.recipe.ApplicationConstants
import com.fourtitude.asia.recipe.R
import com.fourtitude.asia.recipe.base.BaseActivity
import com.fourtitude.asia.recipe.databinding.ActivityRecipeDetailBinding
import com.fourtitude.asia.recipe.module.recipeList.model.RecipeModel
import com.fourtitude.asia.recipe.utils.ImagePicker
import kotlinx.android.synthetic.main.toolbar.view.*
import me.tankery.permission.PermissionRequestActivity
import java.util.*
import kotlin.collections.ArrayList

class RecipeDetailActivity : BaseActivity() {
    lateinit var viewModel: RecipeDetailViewModel
    lateinit var activityBinding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail)

        if (intent.getBooleanExtra(ApplicationConstants.CREATE_NEW_RECIPE, false))
            activityBinding.toolbar.toolbar_title.text = resources.getString(R.string.new_recipe).toUpperCase()
        else
            activityBinding.toolbar.toolbar_title.text = resources.getString(R.string.recipe_detail).toUpperCase()

        setSupportActionBar(activityBinding.toolbar as Toolbar)
        (activityBinding.toolbar as Toolbar).setNavigationOnClickListener { onBackPressed() }

        if (savedInstanceState == null) {}

        viewModel = ViewModelProviders.of(this@RecipeDetailActivity).get(RecipeDetailViewModel::class.java)
        activityBinding.viewModel = viewModel

        setupViewModelData()

        setupObserver()

        setupButtonListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.activity_recipe_detail_menu, menu)

        if (intent.getBooleanExtra(ApplicationConstants.CREATE_NEW_RECIPE, false))
            menu.findItem(R.id.delete_recipe).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_recipe -> {
                showAlertOkBtnDialog("", getString(R.string.are_you_sure), object: OnAlertDialogActionListener{
                    override fun onOkClick() {
                        viewModel.saveRecipe()
                    }
                })
                true
            }
            R.id.delete_recipe -> {
                showAlertOkBtnDialog("", getString(R.string.are_you_sure), object: OnAlertDialogActionListener{
                    override fun onOkClick() {
                        viewModel.delete()
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (viewModel.updateRecipeList)
            setResult(RESULT_OK, intent)
        super.onBackPressed()
    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ApplicationConstants.REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
            ImagePicker.instance.open(this, true, object: ImagePicker.OnImagePickerListener{
                override var imageList: ArrayList<String>
                    get() = ArrayList()
                    set(value) {
                        viewModel.updateRecipeImage(value[0])
                    }
            })

        }
    }

    private fun setupButtonListener(){
        activityBinding.btnSelectImage.setOnClickListener {
            PermissionRequestActivity.start(
                this@RecipeDetailActivity, ApplicationConstants.REQUEST_CODE_PHOTO,
                PHOTO_PERMISSION, getString(R.string.allow_camera_gallery_access), getString(R.string.allow_camera_gallery_access)
            )
        }
    }

    private fun setupViewModelData(){
        if (intent.getSerializableExtra(ApplicationConstants.RECIPE_MODEL) != null)
            viewModel.setRecipeDetail(intent.getSerializableExtra(ApplicationConstants.RECIPE_MODEL) as RecipeModel)

        viewModel.setNewRecipe(intent.getBooleanExtra(ApplicationConstants.CREATE_NEW_RECIPE, false))

        viewModel.recipeType = intent.getIntExtra(ApplicationConstants.RECIPE_TYPE, 0)
    }

    private fun setupObserver(){
        viewModel.statusMessage.observe(this, Observer{
            if(it == "success")
                showSnackBar(activityBinding.root, true, it)
            else if(it == "Recipe deleted"){
                showSnackBar(activityBinding.root, true, it)
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            onBackPressed()
                        }
                    }
                }, 500)
            }
        })
    }
}

fun Context.recipesDetailIntent(recipeModel: RecipeModel?, recipeType: Int, isCreateNew: Boolean): Intent {
    return Intent(this, RecipeDetailActivity::class.java).apply {
        putExtra(ApplicationConstants.RECIPE_MODEL, recipeModel)
        putExtra(ApplicationConstants.CREATE_NEW_RECIPE, isCreateNew)
        putExtra(ApplicationConstants.RECIPE_TYPE, recipeType)
    }
}