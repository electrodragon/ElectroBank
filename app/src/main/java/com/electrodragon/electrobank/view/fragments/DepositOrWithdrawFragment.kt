package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentDepositOrWithdrawBinding
import com.electrodragon.electrobank.model.viewmodel.fragments.DepositOrWithdrawFragmentViewModel
import com.electrodragon.electrobank.model.viewmodel.fragments.DepositOrWithdrawFragmentViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositOrWithdrawFragment : PapaFragment() {
    private val mViewModel: DepositOrWithdrawFragmentViewModel by viewModels()
    private lateinit var mBinding: FragmentDepositOrWithdrawBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentDepositOrWithdrawBinding.inflate(layoutInflater)

        val args: DepositOrWithdrawFragmentArgs by navArgs()

        if (!args.depositMode) {
            mBinding.action.text = "Withdraw!!"
            mBinding.depositOrWithdrawBtn.text = "Withdraw"
        }

        mViewModel.getLoggedInUser().observe(viewLifecycleOwner) {
            mViewModel.setUserEntity(it)
        }

        mViewModel.depositState.observe(viewLifecycleOwner) {
            if (it == DepositState.DEPOSIT_SUCCESS) {
                findNavController().navigate(
                    DepositOrWithdrawFragmentDirections
                        .actionDepositOrWithdrawFragmentToDepositOrWithdrawNotifyFragment(mViewModel.amount.toString())
                )
            }
            if (it == DepositState.DEPOSIT_FAIL) {
                shortToast("Failed, Try Again!")
                mViewModel.depositState.postValue(DepositState.IDLE)
            }
        }

        mViewModel.withdrawState.observe(viewLifecycleOwner) {
            if (it == WithdrawState.WITHDRAW_SUCCESS) {
                findNavController().navigate(
                    DepositOrWithdrawFragmentDirections
                        .actionDepositOrWithdrawFragmentToDepositOrWithdrawNotifyFragment(mViewModel.amount.toString())
                        .setDepositMode(false)
                )
            }
            if (it == WithdrawState.WITHDRAW_FAIL) {
                shortToast("Something went wrong, Try Again!")
            }
            if (it == WithdrawState.NOT_ENOUGH_MONEY) {
                shortToast("You don't have enough money")
            }
            if (it == WithdrawState.WITHDRAW_FAIL || it == WithdrawState.NOT_ENOUGH_MONEY) {
                mViewModel.withdrawState.postValue(WithdrawState.IDLE)
            }
        }

        mBinding.depositOrWithdrawBtn.setOnClickListener {
            val amount = mBinding.amountInput.text.toString().trim()
            when {
                amount.isEmpty() -> requestFocusWithError(mBinding.amountInput, "Please Enter Amount")
                else -> {
                    if (args.depositMode) {
                        mViewModel.deposit(amount.toDouble())
                    } else {
                        mViewModel.withdraw(amount.toDouble())
                    }
                }
            }
        }

        return mBinding.root
    }
}