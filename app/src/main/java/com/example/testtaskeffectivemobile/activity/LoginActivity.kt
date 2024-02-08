package com.example.testtaskeffectivemobile.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.databinding.ActivityLoginBinding
import com.example.testtaskeffectivemobile.viewModel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.vicmikhailau.maskededittext.MaskedEditText
import com.vicmikhailau.maskededittext.doAfterMaskedTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneNumberEditText: MaskedEditText
    lateinit var nameCloseButton: MaterialButton
    lateinit var surnameCloseButton: MaterialButton
    private lateinit var phoneNumberCloseButton: MaterialButton

    private lateinit var loginButton: Button
    private var isValidLoginData: Boolean by Delegates.observable(false) { _, _, newValue ->
        loginButton.isEnabled = newValue
    }
    private var isValidName: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
            binding.nameLinearLayout.setBackgroundResource(if (newValue || binding.name.text.isEmpty()) R.drawable.controls_pl else R.drawable.controls_pl_outlined)
        isValidLoginData = newValue && isValidSurname && isValidPhoneNumber
    }
    private var isValidSurname: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
            binding.surnameLinearLayout.setBackgroundResource(if (newValue || binding.surname.text.isEmpty()) R.drawable.controls_pl else R.drawable.controls_pl_outlined)

        isValidLoginData = newValue && isValidPhoneNumber && isValidName
    }
    private var isValidPhoneNumber: Boolean by Delegates.observable(false) { _, _, newValue ->
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

        loginViewModel.loginLiveData.observe(this) {
            if (it != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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
                isValidName = s?.isNotEmpty() == true
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
                isValidSurname = s?.isNotEmpty() == true
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