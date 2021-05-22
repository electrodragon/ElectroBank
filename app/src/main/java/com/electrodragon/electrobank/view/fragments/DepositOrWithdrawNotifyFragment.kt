package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentDepositOrWithdrawNotifyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositOrWithdrawNotifyFragment : PapaFragment() {
    private lateinit var mBinding: FragmentDepositOrWithdrawNotifyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentDepositOrWithdrawNotifyBinding.inflate(layoutInflater)

        val args: DepositOrWithdrawNotifyFragmentArgs by navArgs()

        mBinding.msg.text = getString(if (args.depositMode) R.string.notify_deposit else R.string.notify_withdraw, args.amount)

        mBinding.goBackBtn.setOnClickListener {
            findNavController().popBackStack(R.id.depositOrWithdrawNotifyFragment, true)
        }

        return mBinding.root
    }
}
