package com.kirchhoff.presentation.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.kirchhoff.presentation.R
import com.kirchhoff.presentation.utils.Event
import com.kirchhoff.presentation.utils.EventObserver

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var progressDialog: AlertDialog? = null

    protected fun <T> LiveData<T>.subscribe(func: (T) -> Unit) {
        this.observe(viewLifecycleOwner, { func(it) })
    }

    protected fun <T> LiveData<Event<T>>.subscribeEvent(func: (T) -> Unit) {
        this.observe(viewLifecycleOwner, EventObserver { func(it) })
    }

    protected fun showProgressDialog(loading: Boolean, cancelAction: (() -> Unit)? = null) {
        if (loading) {
            progressDialog?.dismiss()
            progressDialog = createProgressDialog(cancelAction)
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
        }
    }

    protected fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun createProgressDialog(cancelAction: (() -> Unit)? = null): AlertDialog {
        val view = layoutInflater.inflate(R.layout.dialog_progress, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)
            .setOnCancelListener { cancelAction?.invoke() }
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}
