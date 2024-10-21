package com.fivesoft.androidutils.callbacks

import android.app.ActionBar
import android.view.MenuItem
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar

/**
 * [MenuItem.OnMenuItemClickListener] that can be used with all menu item click listeners.
 */
interface OnMenuItemClickListener : MenuItem.OnMenuItemClickListener,
    Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener,
    android.widget.Toolbar.OnMenuItemClickListener, ActionMenuView.OnMenuItemClickListener,
    android.widget.PopupMenu.OnMenuItemClickListener, ActionBar.OnMenuVisibilityListener

