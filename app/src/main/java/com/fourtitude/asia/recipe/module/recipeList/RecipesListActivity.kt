package com.fourtitude.asia.recipe.module.recipeList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.fourtitude.asia.recipe.ApplicationConstants
import com.fourtitude.asia.recipe.R
import com.fourtitude.asia.recipe.base.BaseActivity
import com.fourtitude.asia.recipe.databinding.ActivityRecipeListBinding
import com.fourtitude.asia.recipe.module.recipeDetail.recipesDetailIntent
import kotlinx.android.synthetic.main.toolbar.view.*

class RecipesListActivity : BaseActivity() {
    lateinit var viewModel: RecipesListViewModel
    lateinit var activityBinding: ActivityRecipeListBinding
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list)
        activityBinding.toolbar.toolbar_title.text = resources.getString(R.string.recipe_list).toUpperCase()

        setSupportActionBar(activityBinding.toolbar as Toolbar)
        (activityBinding.toolbar as Toolbar).setNavigationOnClickListener { onBackPressed() }
        hideBackButton()

        if (savedInstanceState == null) {}

        viewModel = ViewModelProviders.of(this@RecipesListActivity).get(RecipesListViewModel::class.java)
        activityBinding.viewModel = viewModel

        setupRvAdapter()

        setupObserver()

        setupSpinner()

        setupButtonListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == ApplicationConstants.EDIT_RECIPE || requestCode == ApplicationConstants.NEW_RECIPE) && resultCode == Activity.RESULT_OK) {
            viewModel.getAllRecipesByType(activityBinding.spinner.selectedItemPosition)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.activity_recipe_list_menu, menu)
        menu.findItem(R.id.add_recipe).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_recipe -> {
                startActivityForResult(recipesDetailIntent(null, activityBinding.spinner.selectedItemPosition, true),
                    ApplicationConstants.NEW_RECIPE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupButtonListener(){
        activityBinding.btnAddRecipe.setOnClickListener {
            startActivityForResult(recipesDetailIntent(null, activityBinding.spinner.selectedItemPosition, true),
                ApplicationConstants.NEW_RECIPE)
        }
    }

    private fun setupSpinner(){
        // Spinner Drop down elements
        val categories: ArrayList<String> = arrayListOf(*resources.getStringArray(R.array.recipe_type_names))

        // Creating adapter for spinner
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        activityBinding.spinner.adapter = dataAdapter
        // Spinner click listener
        activityBinding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getAllRecipesByType(position)
            }
        }
    }

    private fun setupRvAdapter(){
        activityBinding.rvRecipe.adapter =
            RecipesListAdapter(this, object: RecipesListAdapter.OnItemClickListener{
                override fun onItemClick(position: Int, cardView: CardView) {
                    /*startActivityForResult(recipesDetailIntent(viewModel.recipesList[position], activityBinding.spinner.selectedItemPosition,false),
                        ApplicationConstants.EDIT_RECIPE)*/

                    val transitionActivityOptions =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this@RecipesListActivity, cardView,  getString(R.string.transition_image))

                    startActivityForResult(recipesDetailIntent(viewModel.recipesList[position], activityBinding.spinner.selectedItemPosition,false),
                        ApplicationConstants.EDIT_RECIPE, transitionActivityOptions.toBundle())
                }
            })

        activityBinding.rvRecipe.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !activityBinding.btnAddRecipe.isShown) {
                    activityBinding.btnAddRecipe.show()
                    menu.findItem(R.id.add_recipe).isVisible = false
                }else if (dy > 0 && activityBinding.btnAddRecipe.isShown) {
                    activityBinding.btnAddRecipe.hide()
                    menu.findItem(R.id.add_recipe).isVisible = true
                }
            }
        })
    }

    private fun setupObserver(){
        viewModel.statusMessage.observe(this, Observer<String> {
            if(it != "")
                showAlertDialog(it, null)
        })

        viewModel.updateRVAdapter.observe(this, Observer<Boolean> {
            (activityBinding.rvRecipe.adapter as RecipesListAdapter).setData(viewModel.recipesList)
            menu.findItem(R.id.add_recipe).isVisible = false
            activityBinding.btnAddRecipe.show()
        })
    }
}

fun Context.recipesListIntent(): Intent {
    return Intent(this, RecipesListActivity::class.java)
}