package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentLoginBinding
import com.electrodragon.electrobank.model.viewmodel.fragments.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: PapaFragment() {
    private val mViewModel: LoginFragmentViewModel by viewModels()

    private lateinit var mBinding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentLoginBinding.inflate(layoutInflater)

        shortToast(mViewModel.abc)

        return mBinding.root
    }
}