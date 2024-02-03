package com.example.testtaskeffectivemobile.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testtaskeffectivemobile.MainActivity
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.databinding.ActivityLoginBinding
import com.example.testtaskeffectivemobile.viewModel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.vicmikhailau.maskededittext.MaskedEditText
import com.vicmikhailau.maskededittext.doAfterMaskedTextChanged
import com.vicmikhailau.maskededittext.doOnMaskedTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    lateinit var preferences: SharedPreferences
    lateinit var nameEditText: EditText
    lateinit var surnameEditText: EditText
    lateinit var phoneNumberEditText: MaskedEditText
    lateinit var nameCloseButton: MaterialButton
    lateinit var surnameCloseButton: MaterialButton
    lateinit var phoneNumberCloseButton: MaterialButton
    lateinit var loginButton: Button
    private var isValidLoginData: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        println(newValue)
        loginButton.isEnabled = newValue
    }
    private var isValidName: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        if (oldValue != newValue)
            binding.nameLinearLayout.setBackgroundResource(if (newValue) R.drawable.controls_pl else R.drawable.controls_pl_outlined)
        isValidLoginData = newValue && isValidName && isValidPhoneNumber
    }
    private var isValidSurname: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        if (oldValue != newValue)
            binding.surnameLinearLayout.setBackgroundResource(if (newValue) R.drawable.controls_pl else R.drawable.controls_pl_outlined)
        isValidLoginData = newValue && isValidPhoneNumber && isValidSurname
    }
    private var isValidPhoneNumber: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        isValidLoginData = newValue && isValidName && isValidSurname
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        nameEditText = binding.name
        surnameEditText = binding.surname
        phoneNumberEditText = binding.phoneNumber
        nameCloseButton = binding.nameCloseButton
        surnameCloseButton = binding.surnameCloseButton
        phoneNumberCloseButton = binding.phoneNumberCloseButton
        loginButton = binding.login
        preferences = getSharedPreferences("isAuth", MODE_PRIVATE)

        if (preferences.getBoolean("isAuth", false)) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginViewModel.loginLiveData.observe(this) {
            if (it != null) {
                preferences.edit {
                    putBoolean("isAuth", true)
                }
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        nameCloseButton.setOnClickListener {
            nameEditText.text.clear()
            isValidName = false
        }
        surnameCloseButton.setOnClickListener {
            surnameEditText.text.clear()
            isValidSurname = false
        }
        phoneNumberCloseButton.setOnClickListener {
            phoneNumberEditText.setText("+7")
            isValidPhoneNumber = false
        }
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nameCloseButton.isVisible = s?.isNotBlank() ?: false
            }

            override fun afterTextChanged(s: Editable?) {
                s?.forEach {
                    if (it.code !in (0x0400..0x04FF)) {
                        isValidName = false
                        return
                    }
                }
                isValidName = true
            }

        })

        surnameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                surnameCloseButton.isVisible = s?.isNotBlank() ?: false
            }

            override fun afterTextChanged(s: Editable?) {
                s?.forEach {
                    if (it.code !in (0x0400..0x04FF)) {
                        isValidSurname = false
                        return
                    }
                }
                isValidSurname = true
            }

        })
        phoneNumberEditText.doAfterMaskedTextChanged {
            phoneNumberCloseButton.isVisible = it?.filter { char ->
                (char != '+') && (char != '7')
            }?.isNotBlank() ?: false
            isValidPhoneNumber = it?.length == 16
        }
        phoneNumberEditText.setOnFocusChangeListener { v, hasFocus ->
            val editText = v as EditText
            if (hasFocus && editText.text.isEmpty()) {
                editText.setText("+7")
            } else if (!hasFocus && editText.text.trim().toString() == "+7")
                editText.text.clear()
        }
        loginButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            loginViewModel.login(name, surname, phoneNumber)
        }
        setContentView(binding.root)
    }

}