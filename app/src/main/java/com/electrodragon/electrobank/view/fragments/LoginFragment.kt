package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentLoginBinding
import com.electrodragon.electrobank.model.viewmodel.fragments.LoginFragmentViewModel
import com.electrodragon.electrobank.model.viewmodel.fragments.LoginFragmentViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment: PapaFragment() {
    private val mViewModel: LoginFragmentViewModel by viewModels()

    private lateinit var mBinding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentLoginBinding.inflate(layoutInflater)

        mBinding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        mViewModel.loginUserState.observe(viewLifecycleOwner) {
            if (it == LoginUserState.SUCCESS) {
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }
            if (it == LoginUserState.INVALID_LOGIN) {
                shortToast("Invalid Login!")
            }
        }

        mBinding.loginBtn.setOnClickListener {
            val emailOrAccountNumber = mBinding.emailEditTxt.text.toString().trim().lowercase(Locale.ENGLISH)
            val password = mBinding.paswordEditText.text.toString().trim()

            when {
                emailOrAccountNumber.isEmpty() -> requestFocusWithError(mBinding.emailEditTxt, "Please Enter email or account number")
                password.isEmpty() -> requestFocusWithError(mBinding.paswordEditText, "Please Enter your password")
                else -> mViewModel.loginUser(emailOrAccountNumber, emailOrAccountNumber, password)
            }
        }

        return mBinding.root
    }
}