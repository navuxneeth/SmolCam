package com.example.smolcam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView

class FullscreenImageAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<FullscreenImageAdapter.FullscreenViewHolder>() {

    class FullscreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: PhotoView = itemView.findViewById(R.id.fullscreen_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullscreenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fullscreen_image, parent, false)
        return FullscreenViewHolder(view)
    }

    override fun onBindViewHolder(holder: FullscreenViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount() = images.size
}