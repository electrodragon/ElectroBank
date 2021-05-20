package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentSplashBinding
import com.electrodragon.electrobank.model.viewmodel.fragments.SplashFragmentViewModel
import com.electrodragon.electrobank.model.viewmodel.fragments.SplashFragmentViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : PapaFragment() {
    private val mViewModel: SplashFragmentViewModel by viewModels()
    private lateinit var mBinding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentSplashBinding.inflate(layoutInflater)

        mViewModel.userLoginSession.observe(viewLifecycleOwner) {
            if (it == UserLoginSession.OK) findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
            if (it == UserLoginSession.NOT_OK) findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            mViewModel.checkSession()
        }, 3000)

        return mBinding.root
    }
}