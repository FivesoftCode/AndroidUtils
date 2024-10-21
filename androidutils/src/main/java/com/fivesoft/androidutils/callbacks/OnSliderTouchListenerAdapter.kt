package com.fivesoft.androidutils.callbacks

import com.google.android.material.slider.Slider

/**
 * Adapter for [Slider.OnSliderTouchListener], with default implementations
 * to not force to override all methods, making code cleaner.
 */
interface OnSliderTouchListenerAdapter : Slider.OnSliderTouchListener {
    /**
     * @inheritdoc
     */
    override fun onStartTrackingTouch(slider: Slider) {
    }

    /**
     * @inheritdoc
     */
    override fun onStopTrackingTouch(slider: Slider) {
    }
}
