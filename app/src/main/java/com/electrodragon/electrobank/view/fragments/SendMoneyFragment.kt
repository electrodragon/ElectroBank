package com.electrodragon.electrobank.view.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.custom_parents.fragment.PapaFragment
import com.electrodragon.electrobank.databinding.FragmentSendMoneyBinding
import com.electrodragon.electrobank.databinding.SendMoneyConfirmationDialogBinding
import com.electrodragon.electrobank.databinding.SendMoneyNotifyDialogBinding
import com.electrodragon.electrobank.model.viewmodel.fragments.SendMoneyFragmentViewModel
import com.electrodragon.electrobank.model.viewmodel.fragments.SendMoneyFragmentViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendMoneyFragment : PapaFragment() {
    private val mViewModel: SendMoneyFragmentViewModel by viewModels()
    private lateinit var mBinding: FragmentSendMoneyBinding
    private lateinit var mSMDiagBinding: SendMoneyConfirmationDialogBinding
    private lateinit var sendMoneyConfirmationDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentSendMoneyBinding.inflate(layoutInflater)

        if (!this::sendMoneyConfirmationDialog.isInitialized) {
            sendMoneyConfirmationDialog = Dialog(requireContext())
            sendMoneyConfirmationDialog.setCancelable(true)
        }

        mViewModel.getLoggedInUser().observe(viewLifecycleOwner) {
            mViewModel.setLoggedInUser(it)
        }

        mViewModel.sendMoneyState.observe(viewLifecycleOwner) {
            if (it == SendMoneyState.SENT) {
                sendMoneyConfirmationDialog.dismiss()
                showSentNotifyDialog()
            }
            if (it == SendMoneyState.FAIL) {
                shortToast("Failure, Try Again!")
                mViewModel.sendMoneyState.postValue(SendMoneyState.IDLE)
            }
        }

        mBinding.btnSend.setOnClickListener {
            val accountNumber = mBinding.accountNumberEditTxt.text.toString().trim()
            val amount = mBinding.totalAmountEdittx.text.toString().trim()

            when {
                accountNumber.isEmpty() -> requestFocusWithError(mBinding.accountNumberEditTxt, "Please Enter Account Number")
                amount.isEmpty() -> requestFocusWithError(mBinding.totalAmountEdittx, "Please Enter amount")
                else -> {
                    mViewModel.getUserWithAccountNumber(accountNumber).observe(viewLifecycleOwner) {
                        if (it == null) { // User doesn't exist
                            shortToast("User Doesn't Exist!")
                        } else if (mViewModel.getLoggedInUserAmount() < amount.toDouble()) {
                            shortToast("You don't have enough money to send")
                        } else {
                            if (!mViewModel.isMoneySent) {
                                mViewModel.setRecipientUser(it)
                                showSendDialog(amount, it.firstName, it.lastName)
                            }
                        }
                    }
                }
            }

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!this::mSMDiagBinding.isInitialized) {
            mSMDiagBinding = SendMoneyConfirmationDialogBinding.inflate(layoutInflater)
            sendMoneyConfirmationDialog.setContentView(mSMDiagBinding.root)
        }
    }

    private fun showSendDialog(amount: String, firstName: String, lastName: String) {
        mSMDiagBinding.sendingTo.text = getString(R.string.you_are_sending_money_to_user, amount, "$firstName $lastName")

        mSMDiagBinding.sendBtn.setOnClickListener {
            val password = mSMDiagBinding.password.text.toString().trim()
            when {
                password.isEmpty() -> requestFocusWithError(mSMDiagBinding.password, "Please enter your password")
                password != mViewModel.getLoggedInUserAccountPassword() -> {
                    requestFocusWithError(mSMDiagBinding.password, "Please Enter correct password")
                }
                else -> mViewModel.sendMoney(amount.toDouble())
            }
        }

        sendMoneyConfirmationDialog.show()
    }

    private fun showSentNotifyDialog() {
        val binding = SendMoneyNotifyDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)
        binding.sentTo.text = getString(R.string.you_have_sent_money, mViewModel.amount.toString())
        binding.goBackBtn.setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack(R.id.sendMoneyFragment, true)
        }
        dialog.show()
    }
}