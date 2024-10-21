package com.fivesoft.androidutils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Hide the soft input. If the soft input is already hidden, do nothing.
 * May be safely called from any thread.
 * Context must be an instance of Activity, otherwise this method does nothing.
 */
fun Context.hideSoftInput() {
    runOnUiThread {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!isSoftInputVisible() || this !is Activity) {
            return@runOnUiThread
        }
        var cf: View? = currentFocus
        if (cf == null) {
            cf = window?.decorView
        }
        imm.hideSoftInputFromWindow(
            cf?.windowToken ?: return@runOnUiThread,
            0
        )
    }
}

/**
 * Show the soft input. If the soft input is already visible, do nothing.
 * May be safely called from any thread.
 * @receiver The view that will receive the soft input.
 */
fun View.showSoftInput() {
    runOnUiThread {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }
}

/**
 * Check if the soft input is visible.
 * @return true if the soft input is visible, false otherwise.
 */
fun Context.isSoftInputVisible(): Boolean {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.isAcceptingText
}

/**
 * Retrieves the single [WindowInsetsControllerCompat] of the window this view is attached
 * to.
 *
 * @return The [WindowInsetsControllerCompat] for the window.
 * @see Window.getInsetsController()
 */
fun Window.getInsetsControllerCompat(): WindowInsetsControllerCompat {
    val ctrl = WindowCompat.getInsetsController(this, this.decorView)
    ctrl.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    return ctrl
}

/**
 * Set the visibility of the system bars.
 * @param visible true to show the system bars, false to hide them.
 * @param type The type of insets to show/hide.
 */
fun Window.setInsetsVisible(visible: Boolean, @InsetsType type: Int) {
    runOnUiThread {
        val ctrl = getInsetsControllerCompat()
        if (visible) {
            ctrl.show(type)
        } else {
            ctrl.hide(type)
        }
    }
}

/**
 * Set the visibility of the status bar(s).
 * @param visible true to show the status bar, false to hide it.
 */
fun Window.setStatusBarVisible(visible: Boolean) =
    setInsetsVisible(visible, WindowInsetsCompat.Type.statusBars())

/**
 * Set the visibility of the navigation bar(s).
 * @param visible true to show the navigation bar, false to hide it.
 */
fun Window.setNavigationBarVisible(visible: Boolean) =
    setInsetsVisible(visible, WindowInsetsCompat.Type.navigationBars())

/**
 * Set the visibility of the system bars.
 * @param visible true to show the system bars, false to hide them.
 */
fun Activity.setSystemBarsVisible(visible: Boolean) =
    window?.setInsetsVisible(visible, WindowInsetsCompat.Type.systemBars())

/**
 * Set the visibility of the status bar(s).
 * @param visible true to show the status bar, false to hide it.
 */
fun Activity.setStatusBarVisible(visible: Boolean) = window?.setStatusBarVisible(visible)

/**
 * Set the visibility of the navigation bar(s).
 * @param visible true to show the navigation bar, false to hide it.
 */
fun Activity.setNavigationBarVisible(visible: Boolean) = window?.setNavigationBarVisible(visible)

