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
class SplashFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    enum class UserLoginSession { IDLE, OK, NOT_OK }
    val userLoginSession = MutableLiveData(UserLoginSession.IDLE)

    fun checkSession() {
        if (prefsController.getUserId() == null) {
            userLoginSession.value = UserLoginSession.NOT_OK
        } else {
            userLoginSession.value = UserLoginSession.OK
        }
    }

    companion object {
        private const val TAG = "SplashFragmentViewModel"
    }
}