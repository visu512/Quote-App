package com.hindi.hindisayri.Adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hindi.hindisayri.HomeActivity
import com.hindi.hindisayri.MainActivity
import com.hindi.hindisayri.Model.CategoryModel
import com.hindi.hindisayri.databinding.ItemCategoryBinding

class CatogeryAdapter(val mainActivity: MainActivity, val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CatogeryAdapter.CatViewHolder>() {

    // color arraylist
    val colorList =
        arrayListOf<String>(
            "#98DED9",
            "#B7B7B5",
            "#7E60BF",
            "#629584",
            "#697565",
            "#2480b9",
            "#3498db",
        )

    class CatViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        // for color list
        if (position % 8 == 0) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[0]))
        } else if (position % 6 == 1) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[1]))
        } else if (position % 6 == 2) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[2]))
        } else if (position % 6 == 3) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[3]))
        } else if (position % 6 == 4) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[4]))
        } else if (position % 6 == 5) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[5]))
        } else if (position % 6 == 6) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[6]))
        } else if (position % 6 == 7) {
            holder.binding.itemCard.setBackgroundColor(Color.parseColor(colorList[7]))
        }

        holder.binding.itemText.text = list[position].name.toString()
        // Click on item
        holder.binding.root.setOnClickListener {
            // Create the intent and pass the data first
            val intent = Intent(mainActivity, HomeActivity::class.java).apply {
                putExtra("id", list[position].id)
                putExtra("name", list[position].name)
            }

            // Then start the activity
            mainActivity.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size

}