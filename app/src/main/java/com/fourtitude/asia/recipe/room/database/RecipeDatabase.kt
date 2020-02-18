package com.fourtitude.asia.recipe.room.database

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fourtitude.asia.recipe.room.dao.RecipeDao
import com.fourtitude.asia.recipe.room.entity.Recipe


@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    private class PopulateDbAsyncTask internal constructor(db: RecipeDatabase) : AsyncTask<Void, Void, Void>() {
        private val recipeDao: RecipeDao = db.recipeDao()

        override fun doInBackground(vararg voids: Void): Void? {

            if (recipeDao.allRecipes.isEmpty()){
                recipeDao.insert(Recipe("western img1", "mac cheese", "western ingredient1","western steps1", 0))
                recipeDao.insert(Recipe("western img2", "spaghetti meat ball", "western ingredient2","western steps2", 0))
                recipeDao.insert(Recipe("western img3", "chicken chop", "western ingredient3","western steps3", 0))

                recipeDao.insert(Recipe("thai img1", "tomyam1", "thai ingredient1","thai steps1", 1))
                recipeDao.insert(Recipe("thai img2", "tomyam2", "thai ingredient2","thai steps2", 1))
                recipeDao.insert(Recipe("thai img3", "tomyam3", "thai ingredient3","thai steps3", 1))

                recipeDao.insert(Recipe("malay img1", "nasi goreng belacan", "malay ingredient1","malay steps1", 2))
                recipeDao.insert(Recipe("malay img2", "nasi lemak", "malay ingredient2","malay steps2", 2))
                recipeDao.insert(Recipe("malay img3", "rendang", "malay ingredient3","malay steps3", 2))
            }

            return null
        }
    }

    companion object {

        private var instance: RecipeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecipeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java, "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance!!).execute()
            }
        }
    }
}