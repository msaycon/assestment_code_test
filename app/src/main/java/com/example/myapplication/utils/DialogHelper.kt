package com.example.myapplication.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R

/**
 * Created by msaycon on 25,Jul,2022
 */
fun Context.showDialog(
    title: String?,
    message: String? = null,
    positiveButtonText: String?,
    positiveAction: () -> Unit = {},
    negativeButtonText: String? = null,
    negativeAction: () -> Unit = {},
    neutralButtonText: String? = null,
    neutralAction: () -> Unit = {},
    cancellable: Boolean = false,
    neutralButtonHideDialog: Boolean = true,
    isToDismissFormerDialog: Boolean = true
): AlertDialog {
    val alertDialogBuilder = AlertDialog.Builder(this)
        .setCancelable(cancellable)
        .setPositiveButton(positiveButtonText) { _, _ -> positiveAction() }

    title?.let {
        alertDialogBuilder.setTitle(it)
    }

    message?.let {
        alertDialogBuilder.setMessage(it)
    }

    negativeButtonText?.let {
        alertDialogBuilder.setNegativeButton(negativeButtonText) { _, _ -> negativeAction() }
    }

    neutralButtonText?.let {
        alertDialogBuilder.setNeutralButton(neutralButtonText) { _, _ -> neutralAction() }
    }

    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
    if (!neutralButtonHideDialog) {
        val neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        neutralButton.setOnClickListener {
            neutralAction()
        }
    }

    return alertDialog
}

fun Context.showErrorDialog(message: String): AlertDialog {
    return this.showDialog(
        title = getString(R.string.error),
        message = message,
        positiveButtonText = getString(
            R.string.lbl_ok
        )
    )
}

fun Context.createProgressDialog(): android.app.AlertDialog {
    val dialogBuilder = android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)

    dialogBuilder.setCancelable(false)

    dialogBuilder.setView(R.layout.layout_progress_dialog)

    return dialogBuilder.create()
}