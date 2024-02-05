package com.example.testtaskeffectivemobile.viewHolder

import android.content.Context
import android.util.JsonReader
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.adapter.ViewPagerAdapter
import com.example.testtaskeffectivemobile.data.model.ImageLinkObject
import com.example.testtaskeffectivemobile.data.model.Product
import com.example.testtaskeffectivemobile.databinding.ItemProductBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okio.use
import org.json.JSONObject

class ProductViewHolder(val context: Context, val binding: ItemProductBinding) :
    ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.apply {
            titleTextView.text = product.title
            subtitleTextView.text = product.subtitle
            oldPriceTextView.text = context.getString(
                R.string.price_format_text,
                product.price.price,
                product.price.unit
            )
            priceTextView.text = context.getString(
                R.string.price_format_text,
                product.price.priceWithDiscount,
                product.price.unit
            )
            discrountTextView.text =
                context.getString(R.string.discount_format_text, product.price.discount)
            if (product.feedback != null) {
                feedbackLayout.isVisible = true
                ratingTextView.text = product.feedback.rating.toString()
                feedbackTextView.text =
                    context.getString(R.string.feedback_format_text, product.feedback.count)
            } else
                feedbackLayout.isVisible = false
            val listLinks = Gson().fromJson<List<ImageLinkObject>>(
                context.resources.openRawResource(R.raw.link_id_and_images).reader(),
                TypeToken.getParameterized(List::class.java, ImageLinkObject::class.java).type
            ).filter{
                it.id==product.id
            }.flatMap {
                it.links
            }
            viewPager.adapter = ViewPagerAdapter(listLinks)
        }
    }
}