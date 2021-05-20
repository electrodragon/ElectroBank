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
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    enum class UserRegistrationState { IDLE, SUCCESS, FAIL }
    val userRegistrationState = MutableLiveData(UserRegistrationState.IDLE)

    fun getUserWithEmail(email: String): LiveData<UserEntity> {
        return repository.getUserWithEmail(email)
    }

    var firstName = ""
    var accountNumber = ""

    fun saveUser(user: UserEntity) {
        this.firstName = user.firstName
        this.accountNumber = user.accountNumber
        Observable.just(user)
            .filter {
                if (repository.getUserWithEmail(it.email).value == null) {
                    repository.createNewUser(it)
                }
                true
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userRegistrationState.value = UserRegistrationState.SUCCESS
            }, {
                userRegistrationState.value = UserRegistrationState.FAIL
            }, {
                Log.d(TAG, "saveUser: Used Saving Process Completed!")
            })
    }

    companion object {
        private const val TAG = "RegisterFragmentViewMod"
    }

}