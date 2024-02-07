package com.example.testtaskeffectivemobile.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.databinding.ItemPageBinding

class ViewPagerAdapter(private val listLinks: List<String>, private val action: ()->Unit) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = ItemPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PagerVH(
            parent.context,
            binding,
            action
        )
    }

    override fun getItemCount(): Int = listLinks.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        holder.bind(listLinks[position])
    }
}

class PagerVH(val context: Context, private val binding: ItemPageBinding,val action: ()->Unit) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(link: String) {
        binding.root.apply {
            setImageBitmap(BitmapFactory.decodeStream(context.assets.open(link)))
            setOnClickListener {
                action()
            }
        }
    }
}