package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentWelcomeRegisterUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeRegisterUserFragment : PapaFragment() {
    private lateinit var mBinding: FragmentWelcomeRegisterUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentWelcomeRegisterUserBinding.inflate(layoutInflater)

        val args: WelcomeRegisterUserFragmentArgs by navArgs()

        mBinding.welcomeMsg.text = getString(R.string.welcome_registered_user, args.userFirstName)
        mBinding.accountInfoMsg.text = getString(R.string.your_acc_no_is, args.accountNumber)

        mBinding.loginBtn.setOnClickListener {
            findNavController().popBackStack(R.id.loginFragment, false)
        }

        return mBinding.root
    }
}