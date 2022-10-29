package br.com.ayrtonsato.drinksapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.ayrtonsato.drinksapp.databinding.ItemDrinkBinding
import br.com.ayrtonsato.drinksapp.model.Drink
import com.bumptech.glide.Glide

class DrinkListAdapter(
    private val onClick: (Drink) -> Unit
) : RecyclerView.Adapter<DrinkListAdapter.DrinksViewHolder>() {
    inner class DrinksViewHolder(
        itemView: ItemDrinkBinding
    ) : RecyclerView.ViewHolder(itemView.root) {
        private val iv: ImageView by lazy {
            itemView.ivDrink
        }

        private val tv: TextView by lazy {
            itemView.tvDrinkName
        }

        fun bind(drink: Drink, onClick: (Drink) -> Unit) {
            tv.text = drink.strDrink
            Glide
                .with(itemView.rootView)
                .load(drink.strDrinkThumb)
                .centerCrop()
                .into(iv)
            itemView.setOnClickListener {
                onClick(drink)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean =
            oldItem.idDrink == newItem.idDrink

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        return DrinksViewHolder(
            ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val drink = differ.currentList[position]
        holder.bind(drink, onClick)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitNewList(drinks: List<Drink>) {
        differ.submitList(drinks)
    }
}