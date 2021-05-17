package com.electrodragon.electrobank.custom_parents.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.electrodragon.electrobank.repository.ElectroRepository
import com.electrodragon.electrobank.utils.prefs_controller.PrefsController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class PapaViewModel @Inject constructor(
    app: Application,
    val prefsController: PrefsController,
    val repository: ElectroRepository
) : AndroidViewModel(app)
