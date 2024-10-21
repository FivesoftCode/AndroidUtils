package com.fivesoft.androidutils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.CountDownLatch

/**
 * Run a runnable on the main thread if it is not already on the main thread.
 * @param task The runnable to run. If null, this method does nothing.
 */
fun runOnUiThread(task: Runnable?) {
    if (task == null) return
    if (isMainThread()) {
        task.run()
    } else {
        Handler(Looper.getMainLooper()).post(task)
    }
}

/**
 * Run a runnable on the main thread after a delay.
 * @param task The runnable to run. If null, this method does nothing.
 * @param delay The delay in milliseconds.
 */
fun runOnUiThread(task: Runnable?, delay: Long) {
    if (delay <= 0) {
        // If the delay is zero or negative, run the task immediately.
        runOnUiThread(task)
        return
    }
    if (task == null) return
    Handler(Looper.getMainLooper()).postDelayed(task, delay)
}

/**
 * Run a runnable on the main thread and block the calling thread until the runnable completes.
 * Interrupting the calling thread will not interrupt the runnable,
 * and won't cause this method to throw an [InterruptedException].
 * @param runnable The runnable to run. If null, this method does nothing.
 */
fun runOnUiThreadSync(runnable: Runnable?) {
    if (runnable == null) return
    val latch = CountDownLatch(1)
    runOnUiThread {
        try {
            runnable.run()
        } finally {
            latch.countDown()
        }
    }
    try {
        latch.await()
    } catch (ignore: InterruptedException) {
    }
}

/**
 * Check if the current thread is the main thread.
 * @return true if the current thread is the main thread, false otherwise.
 */
fun isMainThread(): Boolean {
    return Looper.getMainLooper() == Looper.myLooper()
}

/**
 * Ensure that the current thread is the main thread.
 * @throws IllegalStateException if the current thread is not the main thread.
 */
fun requireMainThread() {
    if (!isMainThread()) {
        throw IllegalStateException("This method must be called on the main thread")
    }
}

/**
 * Sleep on the current thread, catching and ignoring any [InterruptedException].
 * @param millis The number of milliseconds to sleep.
 */
fun Thread.sleepSafe(millis: Long) : Boolean {
    try {
        Thread.sleep(millis)
        return true
    } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
    }
    return false
}

/**
 * Run a runnable silently, catching and ignoring any exceptions.
 * @param task The runnable to run.
 */
fun runSilently(task: () -> Unit) {
    try {
        task()
    } catch (_: Exception) {
        // Ignore any exceptions
    }
}