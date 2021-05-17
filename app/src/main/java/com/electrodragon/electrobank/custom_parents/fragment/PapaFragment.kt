package com.electrodragon.electrobank.custom_parents.fragment

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

open class PapaFragment: Fragment() {
    private fun toast(s: String, toastLength: Int) {
        Toast.makeText(context, s, toastLength).show()
    }

    fun shortToast(s: String) {
        toast(s, Toast.LENGTH_SHORT)
    }

    fun longToast(s: String) {
        toast(s, Toast.LENGTH_LONG)
    }

    fun requestFocusWithError(editText: EditText, error: String) {
        editText.error = error
        editText.requestFocus()
    }
}