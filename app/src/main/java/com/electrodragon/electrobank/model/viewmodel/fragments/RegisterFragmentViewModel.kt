package com.electrodragon.electrobank.model.viewmodel.fragments

import android.app.Application
import android.util.Log
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

    fun saveUser(user: UserEntity) {
        Observable.just(user)
            .filter {
                repository.createNewUser(it)
                true
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "saveUser: Used Saved Successfully!")
            }, {
                Log.d(TAG, "saveUser: Used Saving Failed MayBe!")
            }, {
                Log.d(TAG, "saveUser: Used Saving Process Completed!")
            })
    }

    companion object {
        private const val TAG = "RegisterFragmentViewMod"
    }

}