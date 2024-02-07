package com.example.testtaskeffectivemobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.adapter.ViewPagerAdapter
import com.example.testtaskeffectivemobile.data.model.Info
import com.example.testtaskeffectivemobile.data.model.Product
import com.example.testtaskeffectivemobile.databinding.FragmentProductPageBinding
import com.example.testtaskeffectivemobile.databinding.ItemBehaviorBinding
import com.example.testtaskeffectivemobile.util.CountStringUtil
import com.example.testtaskeffectivemobile.viewModel.ProductViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PRODUCT = "product"
const val ARG_LIST_LINKS = "list_links"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ProductPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var product: Product? = null
    private var listLinks: List<String>? = null

    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: FragmentProductPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCT) as Product
            listLinks = it.getStringArrayList(ARG_LIST_LINKS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductPageBinding.inflate(inflater, container, false)
        binding.apply {
            product?.let { product ->
                viewPager.adapter = ViewPagerAdapter(listLinks ?: emptyList()) {
                }
                TabLayoutMediator(paginatationLayout, viewPager) { tab, position ->
                    tab.icon = requireContext().getDrawable(R.drawable.pagination_icon)
                }.attach()
                topAppBar.setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                favoriteButton.apply {
                    isChecked = product.isFavorite
                    setOnClickListener {
                        productViewModel.addToFavorite(product)
                    }
                }
                titleTextView.text = product.title
                subtitleTextView.text = product.subtitle
                availableCountTextView.text =
                    CountStringUtil.getStringCountAvailable(product.available.toInt())
                if (product.feedback != null) {
                    drawStarsRatingViews(product)
                    ratingTextView.text = product.feedback.rating.toString()
                    feedbackTextView.text =
                        CountStringUtil.getStringCountFeedback(product.feedback.count)
                    transactionButton.text = product.title

                }
                priceTextView.text = getString(
                    R.string.price_format_text,
                    product.price.priceWithDiscount,
                    product.price.unit
                )
                oldPriceTextView.text =
                    getString(R.string.price_format_text, product.price.price, product.price.unit)
                discrountTextView.text =
                    getString(R.string.discount_format_text, product.price.discount)
                descriptionTextView.text = product.description
                disappearDescriptionButton.addOnCheckedChangeListener { button, isChecked ->
                    descriptionLayout.isVisible = !isChecked
                    button.text = getString(
                        if (isChecked) R.string.more_details_text else
                            R.string.disappear_text
                    )
                }
                appearIngredientsTextView.text = product.ingredients
                appearIngredientsButton.isVisible = appearIngredientsTextView.lineCount <= 2
                appearIngredientsButton.addOnCheckedChangeListener { button, isChecked ->
                    appearIngredientsTextView.maxLines = if (!isChecked) 2 else Int.MAX_VALUE
                    button.text = getString(
                        if (!isChecked) R.string.more_details_text else
                            R.string.disappear_text
                    )
                }
                priceTextViewButton.text =
                    getString(
                        R.string.price_format_text,
                        product.price.priceWithDiscount,
                        product.price.unit
                    )
                oldPriceTextViewButton.text =
                    getString(R.string.price_format_text, product.price.price, product.price.unit)
                product.info.forEach {
                    addBehaviorView(it)
                    println(it)
                }
            }

        }
        return binding.root
    }

    private fun drawStarsRatingViews(product: Product?) {
        if (product?.feedback != null) {
            binding.starsLayout.children.forEachIndexed { index, view ->
                (view as ImageView).setImageResource(
                    when {
                        (index < product.feedback.rating - 1) ->
                            R.drawable.icon_filled_star

                        (index - (product.feedback.rating - 1)) < 1 ->
                            R.drawable.icon_half_filled_star

                        else ->
                            R.drawable.icon_unfilled_star
                    }
                )
            }
        }
    }

    private fun addBehaviorView(behavior: Info) {
        val itemBehaviorBinding =
            ItemBehaviorBinding.inflate(layoutInflater, binding.behaviorsLayout, false)
        itemBehaviorBinding.run {
            titleTextView.text = behavior.title
            valueTextView.text = behavior.value
            binding.behaviorsLayout.addView(itemBehaviorBinding.root)
        }
    }


}