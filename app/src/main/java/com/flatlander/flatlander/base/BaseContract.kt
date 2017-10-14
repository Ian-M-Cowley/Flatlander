package com.flatlander.flatlander.base

import android.support.annotation.StringRes

/**
 * Created by iancowley on 8/10/17.
 */
interface BaseContract {

    interface View {
        fun showMessageDialog(id: Int,
                              @StringRes messageTextRes: Int,
                              @StringRes positiveTextRes: Int,
                              @StringRes negativeTextRes: Int)

        fun showMessageDialog(id: Int,
                              messageText: String,
                              @StringRes positiveTextRes: Int,
                              @StringRes negativeTextRes: Int)

        fun showError(id: Int, @StringRes errorTextRes: Int)

        fun showError(id: Int, errorText: String)

        fun showProgress(@StringRes progressTextRes: Int)

        fun showProgress(progressText : String)

        fun hideProgress()
    }

    interface Presenter<V : View> {
        val view : V
        fun onViewAdded()
        fun onViewRemoved()
        fun onErrorDismissed(id : Int)
        fun onMessagePositive(id : Int)
        fun onMessageNegative(id : Int)
    }
}