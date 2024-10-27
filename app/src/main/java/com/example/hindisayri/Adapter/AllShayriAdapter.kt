package com.example.hindisayri.Adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hindi.hindisayri.HomeActivity
import com.hindi.hindisayri.Model.ShayariModel
import com.hindi.hindisayri.R
import com.hindi.hindisayri.databinding.ItemSayriBinding

class AllShayriAdapter(
    private val homeActivity: HomeActivity,
    private val shayariList: ArrayList<ShayariModel>
) : RecyclerView.Adapter<AllShayriAdapter.AllShayriViewHolder>() {

    class AllShayriViewHolder(val binding: ItemSayriBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllShayriViewHolder {
        return AllShayriViewHolder(
            ItemSayriBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllShayriViewHolder, position: Int) {
        // Set background based on position
        when (position % 6) {
            0 -> holder.binding.linear.setBackgroundResource(R.drawable.gradient_1)
            1 -> holder.binding.linear.setBackgroundResource(R.drawable.gradient_2)
            2 -> holder.binding.linear.setBackgroundResource(R.drawable.gradient_3)
            3 -> holder.binding.linear.setBackgroundResource(R.drawable.gradient_4)
            4 -> holder.binding.linear.setBackgroundResource(R.drawable.gradient_5)
            5 -> holder.binding.linear.setBackgroundResource(R.drawable.gradient_6)
        }

        // Set text into itemSayri
        holder.binding.itemSayri.text = shayariList[position].data

        // Share shayari button
        holder.binding.shareShayariBtn.setOnClickListener {
            try {
                val shareMessage = "\n ${shayariList[position].data} \n"
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareMessage)
                }
                homeActivity.startActivity(Intent.createChooser(intent, "Share With"))
            } catch (e: Exception) {
                Toast.makeText(homeActivity, "Unable to share this app.", Toast.LENGTH_SHORT).show()
            }
        }

        // Copy shayari button
        holder.binding.copyBtn.setOnClickListener {
            val clipboard =
                homeActivity.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip =
                android.content.ClipData.newPlainText("Copied Text", shayariList[position].data)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(homeActivity, "Copied", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount() = shayariList.size
}
