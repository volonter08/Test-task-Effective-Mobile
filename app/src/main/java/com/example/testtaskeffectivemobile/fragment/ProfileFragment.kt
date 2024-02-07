package com.example.testtaskeffectivemobile.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.activity.LoginActivity
import com.example.testtaskeffectivemobile.dao.ProductDao
import com.example.testtaskeffectivemobile.databinding.FragmentProfileBinding
import com.example.testtaskeffectivemobile.util.CountStringUtil
import com.example.testtaskeffectivemobile.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var productDao: ProductDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.apply {
            loginViewModel.loginLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    personalAreaButton.text = "${it.name} ${it.surname}"
                    phoneNumberTextView.text = it.phoneNumber
                    viewLifecycleOwner.lifecycleScope.launch {
                        countFavoriteTextView.isVisible = productDao.count() != 0
                        countFavoriteTextView.text =
                            CountStringUtil.getStringCountFavoriteProduct(productDao.count())
                    }
                    favoriteButton.setOnClickListener {
                        findNavController().navigate(R.id.action_profileFragment_to_favoriteProductsFragment)
                    }
                } else
                    activity?.run {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
            }
            logout.setOnClickListener {
                loginViewModel.logout()
            }
        }
        return binding.root
    }
}