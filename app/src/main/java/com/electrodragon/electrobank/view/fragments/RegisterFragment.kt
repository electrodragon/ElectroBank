package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentRegisterBinding
import com.electrodragon.electrobank.model.room_database.entities.UserEntity
import com.electrodragon.electrobank.model.viewmodel.fragments.RegisterFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterFragment : PapaFragment() {
    private val mViewModel: RegisterFragmentViewModel by viewModels()
    private lateinit var mBinding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentRegisterBinding.inflate(layoutInflater)

        mBinding.registerBtn.setOnClickListener {
            val firstName = mBinding.firstNameEditText.text.toString().trim()
            val lastName = mBinding.lastNameEditTxt.text.toString().trim()
            val email = mBinding.emailEditText.text.toString().trim().lowercase(Locale.ENGLISH)
            val password = mBinding.paswordEdittxt.text.toString().trim()
            when {
                firstName.isEmpty() -> {
                    requestFocusWithError(mBinding.firstNameEditText, "Please enter first name")
                }
                lastName.isEmpty() -> {
                    requestFocusWithError(mBinding.lastNameEditTxt, "Please enter last name")
                }
                email.isEmpty() -> {
                    requestFocusWithError(mBinding.emailEditText, "Please enter your email")
                }
                password.isEmpty() -> {
                    requestFocusWithError(mBinding.paswordEdittxt, "Please enter your password")
                }
                else -> {
                    mViewModel.saveUser(UserEntity(
                        firstName,
                        lastName,
                        email,
                        password,
                        System.currentTimeMillis().toString()
                    ))
                }
            }
        }

        return mBinding.root
    }
}