package com.example.smolcam

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val images: List<Int>,
    private val titles: List<String>,
    private val dates: List<String>
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.gallery_image)
        val titleView: TextView = itemView.findViewById(R.id.gallery_title)
        val dateView: TextView = itemView.findViewById(R.id.gallery_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
        holder.titleView.text = titles.getOrElse(position) { "Title ${position + 1}" }
        holder.dateView.text = dates.getOrElse(position) { "Date" }

        // Set the click listener to open the new viewer activity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ImageViewerActivity::class.java).apply {
                putIntegerArrayListExtra("IMAGES", ArrayList(images))
                putExtra("POSITION", position)
            }

            // This creates the shared element transition animation
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                holder.imageView,
                "image_transition"
            )
            context.startActivity(intent, options.toBundle())
        }
    }

    override fun getItemCount() = images.size
}