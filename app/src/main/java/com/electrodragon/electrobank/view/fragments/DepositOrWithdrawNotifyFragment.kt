package com.electrodragon.electrobank.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.electrodragon.electrobank.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositOrWithdrawNotifyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_deposit_or_withdraw_notify, container, false)
    }
}
