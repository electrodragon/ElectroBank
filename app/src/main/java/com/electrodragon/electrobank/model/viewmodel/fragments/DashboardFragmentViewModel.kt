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
class DashboardFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    fun getUser(): LiveData<UserEntity> {
        return repository.getUserWithId(prefsController.getUserId()!!)
    }

    enum class LogoutState { IDLE, LOGGED_OUT }
    val logoutState = MutableLiveData(LogoutState.IDLE)

    fun logoutUser() {
        prefsController.removeUserId()
        logoutState.value = LogoutState.LOGGED_OUT
    }

    companion object {
        private const val TAG = "DashboardFragmentViewMo"
    }
}