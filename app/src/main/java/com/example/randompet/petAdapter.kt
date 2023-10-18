package com.example.randompet;

import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

public class petAdapter(private val petList: List<String>): RecyclerView.Adapter<petAdapter.ViewHolder>() {

    // Create the ViewHolder class within MyAdapter
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val petImage: ImageView

            init {
            // Find our RecyclerView item's ImageView for future use
                petImage = view.findViewById(R.id.pet_image)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.petitem, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(petList[position])
            .centerCrop()
            .into(holder.petImage)
    }
}
