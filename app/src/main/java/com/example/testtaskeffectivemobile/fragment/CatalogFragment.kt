package com.example.testtaskeffectivemobile.fragment


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.adapter.ProductAdapter
import com.example.testtaskeffectivemobile.data.model.Product
import com.example.testtaskeffectivemobile.databinding.FragmentCatalogBinding
import com.example.testtaskeffectivemobile.listener.OnButtonClickListener
import com.example.testtaskeffectivemobile.viewModel.ProductViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding
    private lateinit var productAdapter: ProductAdapter
    private val productViewModel: ProductViewModel by activityViewModels()

    private val mapTags = mapOf<String, String>(
        "watch_all" to "Смотреть все",
        "body" to "Тело",
        "face" to "Лицо",
        "suntan" to "Загар",
        "mask" to "Маски"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        productAdapter = ProductAdapter(object : OnButtonClickListener {
            override fun onProductClick(product: Product, listLinks: List<String>) {
                findNavController().navigate(
                    R.id.action_catalogFragment_to_productPageFragment,
                    bundleOf(ARG_PRODUCT to product, ARG_LIST_LINKS to listLinks)
                )
            }

            override fun onFavoriteAddButtonClick(product: Product) {
                productViewModel.addToFavorite(product)
            }
        })
        binding.apply {
            productRecycleView.adapter = productAdapter
            sortButton.setOnClickListener { view ->
                PopupMenu(requireContext(), view, Gravity.BOTTOM).apply {
                    inflate(R.menu.sort_by)
                    setOnMenuItemClickListener {
                        (view as Button).text = it.title
                        when (it.itemId) {
                            R.id.sort_by_populate -> productAdapter.sortProductByPopulate()
                            R.id.sort_by_increasing_price -> productAdapter.sortProductByIncreasingPrice()
                            R.id.sort_by_reducing_price -> productAdapter.sortProductByReducingPrice()
                        }
                        return@setOnMenuItemClickListener true
                    }
                }.show()
            }
            addChipWatchAll(binding.chipGroup)
        }
        productViewModel.data.observe(viewLifecycleOwner) {
            val tag = productViewModel.selectedTag.value
            productAdapter.run {
                submitList(it.filter { product ->
                    if (tag != "watch_all")
                        product.tags.contains(tag)
                    else
                        true
                })
            }
            binding.chipGroup.apply {
                removeViews(1, childCount - 1)
            }
            it.flatMap { product ->
                product.tags
            }.toSet().forEach { tag ->
                addChip(tag, binding.chipGroup)
            }
        }
        productViewModel.selectedTag.observe(viewLifecycleOwner) { tag ->
            productAdapter.run {
                submitList(productViewModel.data.value?.filter { product ->
                    if (tag != "watch_all")
                        product.tags.contains(tag)
                    else
                        true
                })
            }
            binding.chipGroup.children.map {
                it as Chip
            }.forEach {
                if (it.text != (mapTags[tag] ?: tag)) {
                    it.isCheckable = true
                    it.isChecked = false
                } else {
                    it.isChecked = true
                }
            }
        }
        return binding.root
    }

    private fun addChipWatchAll(pChipGroup: ChipGroup) {
        val lChip = layoutInflater.inflate(
            R.layout.chip,
            pChipGroup,
            false
        ) as Chip
        val text = mapTags["watch_all"]
        lChip.text = text
        lChip.setOnCheckedChangeListener { _, isChecked ->
            lChip.setTextAppearance(
                if (isChecked) R.style.TextAppearance_App_Title4 else R.style.TextAppearance_App_ButtonText2
            )
            lChip.setTextColor(requireContext().getColor(if (isChecked) R.color.white else R.color.grey))
            lChip.isCloseIconVisible = isChecked
            lChip.isClickable = !isChecked
        }
        lChip.setOnClickListener {
            productViewModel.selectTag("watch_all")
        }
        pChipGroup.addView(lChip)
    }

    private fun addChip(pItem: String, pChipGroup: ChipGroup) {
        val lChip = layoutInflater.inflate(
            R.layout.chip,
            pChipGroup,
            false
        ) as Chip
        val text = mapTags[pItem] ?: pItem
        lChip.text = text
        lChip.apply {
            setOnCheckedChangeListener { _, isChecked ->
                lChip.setTextAppearance(
                    if (isChecked) R.style.TextAppearance_App_Title4 else R.style.TextAppearance_App_ButtonText2
                )
                lChip.setTextColor(requireContext().getColor(if (isChecked) R.color.white else R.color.grey))
                lChip.isCloseIconVisible = isChecked
                lChip.isClickable = !isChecked
            }
            isChecked = pItem==productViewModel.selectedTag.value
            setOnClickListener {
                productViewModel.selectTag(pItem)
            }
            setOnCloseIconClickListener {
                productViewModel.unselectTag()
            }
        }
        pChipGroup.addView(lChip)
    }
}