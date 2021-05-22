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
class SendMoneyFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    private lateinit var loggedInUser: UserEntity
    private val recipientUserEntity = MutableLiveData<UserEntity>()

    fun setRecipientUser(userEntity: UserEntity) {
        recipientUserEntity.value = userEntity
    }

    var amount: Double = 0.0 // PURPOSE TO BE SENT TO NEXT DIALOG
    var isMoneySent = false

    fun setLoggedInUser(userEntity: UserEntity) {
        if (!this::loggedInUser.isInitialized) {
            this.loggedInUser = userEntity
        }
    }

    fun getLoggedInUserAmount(): Double {
        return loggedInUser.amount
    }

    fun getLoggedInUserAccountPassword(): String {
        return loggedInUser.password
    }

    fun getUserWithAccountNumber(accountNumber: String): LiveData<UserEntity> {
        return repository.getUserWithAccountNumber(accountNumber)
    }

    enum class SendMoneyState {IDLE, SENT, FAIL}
    val sendMoneyState = MutableLiveData(SendMoneyState.IDLE)

    fun sendMoney(amount: Double) {
        this.amount = amount
        Observable.just(recipientUserEntity.value!!.amount + amount)
            .filter {
                isMoneySent = true
                repository.setAmount(it, recipientUserEntity.value!!.id)
                repository.setAmount(getLoggedInUserAmount() - amount, loggedInUser.id)
                true
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sendMoneyState.value = SendMoneyState.SENT
            }, {
                sendMoneyState.value = SendMoneyState.FAIL
            }, {
                Log.d(TAG, "sendMoney: Process Completed!")
            })
    }

    companion object {
        private const val TAG = "SendMoneyFragmentVM"
    }
}