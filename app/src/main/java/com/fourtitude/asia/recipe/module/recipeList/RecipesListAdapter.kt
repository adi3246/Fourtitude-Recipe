package com.fourtitude.asia.recipe.module.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.fourtitude.asia.recipe.databinding.ItemRecipeBinding
import com.fourtitude.asia.recipe.module.recipeList.model.RecipeModel

class RecipesListAdapter constructor(context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>(){

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    private var mData = ArrayList<RecipeModel>()

    private val mContext = context

    fun setData(items: List<RecipeModel>) {
        mData = items as ArrayList<RecipeModel>
        notifyDataSetChanged()
    }

    fun notifyDelete(position: Int) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }

    /*override fun changedPositions(positions: Set<Int>) {
        positions.forEach(this::notifyItemChanged)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingView = ItemRecipeBinding.inflate(mInflater, parent, false)
        return ViewHolder(bindingView.root, bindingView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < mData.size) {
            val item = mData[position]
            holder.mBinding.model = item

            holder.mBinding.root.setOnClickListener {
                listener.onItemClick(position, holder.mBinding.imgContainer)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder constructor(itemView: View, bindingView: ItemRecipeBinding) : RecyclerView.ViewHolder(itemView){
        var mBinding: ItemRecipeBinding = bindingView
        init {
            mBinding = bindingView
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, cardView: CardView)
    }
}