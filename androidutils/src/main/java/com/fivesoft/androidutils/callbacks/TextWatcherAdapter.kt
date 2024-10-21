package com.fivesoft.androidutils.callbacks

import android.text.Editable
import android.text.TextWatcher

/**
 * [TextWatcher] adapter with default implementations
 * to not force to override all methods, making code cleaner.
 */
interface TextWatcherAdapter : TextWatcher {
    /**
     * @inheritdoc
     */
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    /**
     * @inheritdoc
     */
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    /**
     * @inheritdoc
     */
    override fun afterTextChanged(s: Editable)
}
