package com.example.testtaskeffectivemobile.fragment


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.adapter.ProductAdapter
import com.example.testtaskeffectivemobile.databinding.FragmentCatalogBinding
import com.example.testtaskeffectivemobile.viewModel.ProductViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding
    private lateinit var productAdapter: ProductAdapter
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        productAdapter = ProductAdapter()
        binding.apply {
            productRecycleView.adapter = productAdapter
            sortButton.setOnClickListener {view->
                PopupMenu(requireContext(),view,Gravity.BOTTOM).apply {
                    inflate(R.menu.sort_by)
                    setOnMenuItemClickListener {
                        (view as Button).text = it.title
                        when(it.itemId){
                            R.id.sort_by_populate-> productAdapter.sortProductByPopulate()
                            R.id.sort_by_increasing_price->productAdapter.sortProductByIncreasingPrice()
                            R.id.sort_by_reducing_price->productAdapter.sortProductByReducingPrice()
                        }
                     return@setOnMenuItemClickListener true
                    }
                }.show()
            }
        }
        productViewModel.data.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
            it.flatMap { product ->
                product.tags
            }.toSet().forEach {tag->
                addChip(tag, binding.chipGroup)
            }
        }
        productViewModel.selectedTags.observe(viewLifecycleOwner){
            productAdapter.run {
                submitList(productViewModel.data.value?.filter { product->
                    product.tags.containsAll(it)
                })
            }
        }
        return binding.root
    }
    private fun addChip(pItem: String, pChipGroup: ChipGroup) {
        val lChip = layoutInflater.inflate(
            R.layout.chip,
            pChipGroup,
            false
        ) as Chip
        lChip.text = pItem
        lChip.setOnCheckedChangeListener { _, isChecked ->
            lChip.setTextAppearance(
                if (isChecked) R.style.TextAppearance_App_Title4 else R.style.TextAppearance_App_ButtonText2
            )
            lChip.isCheckable = !isChecked
            lChip.setTextColor(requireContext().getColor(if (isChecked) R.color.white else R.color.grey))
            lChip.isCloseIconVisible = isChecked
            if (isChecked)
                productViewModel.selectTag(pItem)
        }
        lChip.setOnCloseIconClickListener {
            productAdapter.submitList(emptyList())
            productViewModel.unselectTag(pItem)
            lChip.isCheckable = true
            lChip.isChecked = false
        }
        pChipGroup.addView(lChip)
    }

}