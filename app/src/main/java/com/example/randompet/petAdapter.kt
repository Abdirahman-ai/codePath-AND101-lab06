package com.example.randompet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randompet.R

/* Adapter class that extends RecyclerView.Adapter<PetAdapter.ViewHolder>() */
class PetAdapter(private val petList: List<String>): RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    // class constructor so we can define a custom ViewHolder:
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView

        init {
            // Find our RecyclerView item's ImageView for future use
            petImage = view.findViewById(R.id.pet_image)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from the dataset at this position and replace the contents of the view with that element
        Glide.with(holder.itemView)
            .load(petList[position])
            .centerCrop()
            .into(holder.petImage)
    }

    override fun getItemCount(): Int {
        return petList.size
    }
}
