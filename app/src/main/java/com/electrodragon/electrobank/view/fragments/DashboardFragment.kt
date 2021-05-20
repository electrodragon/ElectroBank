package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentDashboardBinding
import com.electrodragon.electrobank.model.viewmodel.fragments.DashboardFragmentViewModel
import com.electrodragon.electrobank.model.viewmodel.fragments.DashboardFragmentViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : PapaFragment() {
    private val mViewModel: DashboardFragmentViewModel by viewModels()
    private lateinit var mBinding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        mBinding = FragmentDashboardBinding.inflate(layoutInflater)

        mViewModel.getUser().observe(viewLifecycleOwner) {
            mBinding.totalAmount.text = getString(R.string.dollar_amount, it.amount.toString())
            mBinding.welcomeMsg.text = getString(R.string.welcome_dashboard_user, it.firstName)
        }

        mViewModel.logoutState.observe(viewLifecycleOwner) {
            if (it == LogoutState.LOGGED_OUT) {
                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
            }
        }

        mBinding.logoutBtn.setOnClickListener {
            mViewModel.logoutUser()
        }

        return mBinding.root
    }
}