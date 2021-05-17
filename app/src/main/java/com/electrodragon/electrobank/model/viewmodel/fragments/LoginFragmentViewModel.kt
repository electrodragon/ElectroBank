package com.electrodragon.electrobank.model.viewmodel.fragments

import android.app.Application
import com.electrodragon.electrobank.custom_parents.viewmodel.PapaViewModel
import com.electrodragon.electrobank.repository.ElectroRepository
import com.electrodragon.electrobank.utils.prefs_controller.PrefsController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    app: Application,
    prefsController: PrefsController,
    repository: ElectroRepository
) : PapaViewModel(app, prefsController, repository) {

    var abc = "hello, World !"

}