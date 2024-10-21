package com.fivesoft.androidutils.callbacks

import android.view.SurfaceHolder

/**
 * Adapter for [SurfaceHolder.Callback2]. Override only the methods you need.
 */
interface SurfaceHolderCallbackAdapter : SurfaceHolder.Callback2 {
    override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }
}
