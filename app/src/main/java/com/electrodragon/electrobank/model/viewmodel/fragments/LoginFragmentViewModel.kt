package com.electrodragon.electrobank.model.viewmodel.fragments

import android.app.Application
import android.util.Log
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
class LoginFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    enum class LoginUserState { IDLE, SUCCESS, INVALID_LOGIN }
    val loginUserState = MutableLiveData(LoginUserState.IDLE)

    fun loginUser(email: String, accountNumber: String, password: String) {
        Observable.just(1)
            .map {
                repository.getUserWithEmailOrAccountNumber(email, accountNumber)
            }
            .filter {
                if (it == null) {
                    loginUserState.value = LoginUserState.INVALID_LOGIN
                }
                true
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.password == password) {
                    prefsController.saveUserId(it.id)
                    loginUserState.value = LoginUserState.SUCCESS
                } else {
                    loginUserState.value = LoginUserState.INVALID_LOGIN
                }
            }, {
                loginUserState.value = LoginUserState.INVALID_LOGIN
            }, {
                Log.d(TAG, "loginUser: Process Completed!")
            })
    }

    companion object {
        private const val TAG = "LoginFragmentViewModel"
    }
}