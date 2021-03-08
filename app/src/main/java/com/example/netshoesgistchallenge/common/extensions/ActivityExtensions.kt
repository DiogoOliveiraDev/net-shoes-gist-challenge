package com.example.netshoesgistchallenge.common.extensions

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.Constants.USER_PREFERENCES_KEY

fun Activity.hasPrefs(key: String) =
        prefsEditor().contains(key)

fun Activity.putPrefsBool(key: String, value: Boolean) {
    prefsEditor().edit().putBoolean(key, value).apply()
}

private fun Activity.prefsEditor() =
        getSharedPreferences(
                USER_PREFERENCES_KEY,
                AppCompatActivity.MODE_PRIVATE)

fun Activity.showQuitDialog() {
    AlertDialog.Builder(this).apply {
        setTitle(R.string.dialog_exit_title)
        setPositiveButton(R.string.yes) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}
