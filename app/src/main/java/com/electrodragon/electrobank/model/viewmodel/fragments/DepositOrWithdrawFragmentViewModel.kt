package com.electrodragon.electrobank.model.viewmodel.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.electrodragon.electrobank.custom_parents.viewmodel.PapaViewModel
import com.electrodragon.electrobank.model.room_database.entities.UserEntity
import com.electrodragon.electrobank.repository.ElectroRepository
import com.electrodragon.electrobank.utils.prefs_controller.PrefsController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DepositOrWithdrawFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    private lateinit var userEntity: UserEntity
    var amount: Double = 0.0

    fun setUserEntity(userEntity: UserEntity) {
        if (!this::userEntity.isInitialized) {
            this.userEntity = userEntity
        }
    }

    enum class DepositState { IDLE, DEPOSIT_SUCCESS, DEPOSIT_FAIL }
    val depositState = MutableLiveData(DepositState.IDLE)

    fun deposit(amount: Double) {
        this.amount = amount
        Observable.just(amount + userEntity.amount)
            .filter {
                repository.setAmount(it, userEntity.id)
                true
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                depositState.value = DepositState.DEPOSIT_SUCCESS
            }, {
                depositState.value = DepositState.DEPOSIT_FAIL
            }, {
                Log.d(TAG, "deposit: Process Completed!")
            })
    }

    enum class WithdrawState { IDLE, WITHDRAW_SUCCESS, WITHDRAW_FAIL, NOT_ENOUGH_MONEY }
    val withdrawState = MutableLiveData(WithdrawState.IDLE)

    fun withdraw(amount: Double) {
        this.amount = amount
        if (amount > userEntity.amount) {
            withdrawState.value = WithdrawState.NOT_ENOUGH_MONEY
        } else {
            Observable.just(userEntity.amount - amount)
                .filter {
                    repository.setAmount(it, userEntity.id)
                    true
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    withdrawState.value = WithdrawState.WITHDRAW_SUCCESS
                }, {
                    withdrawState.value = WithdrawState.WITHDRAW_FAIL
                }, {
                    Log.d(TAG, "withdraw: Process Completed!")
                })
        }
    }

    companion object {
        private const val TAG = "DepositOrWithdrawFVM"
    }
}