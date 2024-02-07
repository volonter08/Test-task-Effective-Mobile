package com.example.testtaskeffectivemobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.adapter.ProductAdapter
import com.example.testtaskeffectivemobile.data.model.Product
import com.example.testtaskeffectivemobile.databinding.FragmentFavoriteProductsBinding
import com.example.testtaskeffectivemobile.entity.toDto
import com.example.testtaskeffectivemobile.listener.OnButtonClickListener
import com.example.testtaskeffectivemobile.viewModel.ProductViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteProductsBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteProductsBinding.inflate(inflater, container, false)
        productAdapter = ProductAdapter(object : OnButtonClickListener {
            override fun onProductClick(product: Product, listLinks: List<String>) {
                findNavController().navigate(
                    R.id.action_favoriteProductsFragment_to_productPageFragment2,
                    bundleOf(ARG_PRODUCT to product, ARG_LIST_LINKS to listLinks)
                )
            }

            override fun onFavoriteAddButtonClick(product: Product) {
                productViewModel.addToFavorite(product)
            }
        })
        productViewModel.dataFavoriteProducts.observe(viewLifecycleOwner) {
            productAdapter.submitList(it.toDto())
        }
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            favoriteProductRecycleView.adapter = productAdapter
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.favoriteProductRecycleView.isVisible = tab?.position == 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
        return binding.root
    }
}