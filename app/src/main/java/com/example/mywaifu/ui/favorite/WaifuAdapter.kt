package com.example.mywaifu.ui.favorite

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mywaifu.R

class WaifuAdapter(): RecyclerView.Adapter<WaifuAdapter.WaifuViewHolder>() {

    var waifu = listOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaifuViewHolder =
        WaifuViewHolder(parent)

    override fun getItemCount(): Int = waifu.size

    override fun onBindViewHolder(holder: WaifuViewHolder, position: Int) {
        holder.bind(waifu[position])
    }

    class WaifuViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.waifu_view, parent, false)
    ) {


        private val waifuView: ImageView = itemView.findViewById(R.id.waifuImageView)


        fun bind(url: String) {

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(waifuView)
        }
    }
}
