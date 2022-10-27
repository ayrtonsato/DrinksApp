package br.com.ayrtonsato.drinksapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.ayrtonsato.drinksapp.databinding.ItemDrinkCategoryBinding
import br.com.ayrtonsato.drinksapp.model.DrinkCategory

class CategoryAdapter(
    private val onClick: (DrinkCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(
        itemView: ItemDrinkCategoryBinding
    ) : RecyclerView.ViewHolder(itemView.root) {
        private val tvCategory: TextView

        init {
            tvCategory = itemView.tvCategory
        }

        fun bind(drinkCategory: DrinkCategory, onClick: (DrinkCategory) -> Unit) {
            tvCategory.text = drinkCategory.strCategory
            itemView.setOnClickListener {
                onClick(drinkCategory)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<DrinkCategory>() {
        override fun areItemsTheSame(oldItem: DrinkCategory, newItem: DrinkCategory): Boolean =
            oldItem.strCategory == newItem.strCategory

        override fun areContentsTheSame(oldItem: DrinkCategory, newItem: DrinkCategory): Boolean =
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
        holder.bind(drinkCategory, onClick)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun updateList(categories: List<DrinkCategory>) {
        differ.submitList(categories)
    }
}