package com.flatlander.flatlander.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.flatlander.flatlander.R


/**
 * Created by iancowley on 8/10/17.
 */
abstract class BaseContractActivity : AppCompatActivity(), BaseContract.View {


    protected abstract @LayoutRes fun getLayoutResourceId(): Int
    protected abstract fun onErrorDismissed(id: Int)
    protected abstract fun onMessagePositive(id: Int)
    protected abstract fun onMessageNegative(id: Int)

    private var progressDialog : ProgressDialog ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
    }

    override fun showMessageDialog(id: Int,
                                   @StringRes messageTextRes: Int,
                                   @StringRes positiveTextRes: Int,
                                   @StringRes negativeTextRes: Int) {
        showMessageDialog(id, getString(messageTextRes), positiveTextRes, negativeTextRes)

    }

    override fun showMessageDialog(id: Int,
                                   messageText: String,
                                   @StringRes positiveTextRes: Int,
                                   @StringRes negativeTextRes: Int) {
        AlertDialog.Builder(this)
                .setMessage(messageText)
                .setPositiveButton(positiveTextRes) { dialog, which -> onMessagePositive(id) }
                .setNegativeButton(negativeTextRes) { dialog, which -> onMessageNegative(id) }
                .show()

    }

    override fun showError(id: Int,
                           @StringRes errorTextRes: Int) {
        showError(id, getString(errorTextRes))
    }

    override fun showError(id: Int,
                           errorText: String) {
        AlertDialog.Builder(this)
                .setMessage(errorText)
                .setPositiveButton(R.string.ok) { dialog, which -> onErrorDismissed(id) }
                .setCancelable(false)
                .show()
    }

    override fun showProgress(progressTextRes: Int) {
        showProgress(getString(progressTextRes))
    }

    override fun showProgress(progressText: String) {
        hideProgress()
        progressDialog = ProgressDialog.show(this, null, progressText, false)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }
}