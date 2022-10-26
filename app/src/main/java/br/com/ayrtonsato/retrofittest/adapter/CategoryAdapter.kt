package br.com.ayrtonsato.retrofittest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.ayrtonsato.retrofittest.databinding.ItemDrinkCategoryBinding
import br.com.ayrtonsato.retrofittest.model.Drink
import br.com.ayrtonsato.retrofittest.model.DrinkCategory

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(
        itemView: ItemDrinkCategoryBinding
    ) : RecyclerView.ViewHolder(itemView.root) {
        private val tvCategory: TextView

        init {
            tvCategory = itemView.tvCategory
        }

        fun bind(category: Drink) {
            tvCategory.text = category.strCategory
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean =
            oldItem.strCategory == newItem.strCategory

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            ItemDrinkCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val drinkCategory = differ.currentList[position]
        holder.bind(drinkCategory)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun updateList(categories: List<Drink>) {
        differ.submitList(categories)
    }
}