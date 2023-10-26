package com.example.randompet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Pet(
    val imageUrl: String,
    val name: String,
    val description: String
)

class PetAdapter(private val petList: MutableList<Pet>) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView = view.findViewById(R.id.petItemImage)
        val petName: TextView = view.findViewById(R.id.petItemName)
        val petDescription: TextView = view.findViewById(R.id.petItemDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = petList[position]
        Glide.with(holder.itemView)
            .load(pet.imageUrl)
            .centerCrop()
            .into(holder.petImage)

        holder.petName.text = pet.name
        holder.petDescription.text = pet.description
    }

    override fun getItemCount() = petList.size
}
