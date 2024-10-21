package com.fivesoft.androidutils.listeners

import com.fivesoft.androidutils.runOnUiThread
import java.util.Collections

/**
 * Helper class for managing listeners.
 * The listeners are stored in a map with a unique id for each listener.
 * Set listener by calling [setListener] and remove listener by calling [removeListener].
 * You can also clear all listeners by calling [clearListeners].
 * To call a function for each listener, use [forEachListenerWithKey].
 * @param T The type of the listener.
 */
open class ListenerManager<T> {

    //map cannot have null entries!!!
    private val listeners: MutableMap<String, ListenerEntry<T>> = Collections.synchronizedMap(HashMap())

    /**
     * Adds a listener. If a listener with the same id already exists, it will be replaced.
     * @param id The id of the listener.
     * @param listener The listener.
     * @return The previous listener with the same id or null if there was no such listener.
     */
    fun setListener(id: String, listener: T,
                    requireUiThread: Boolean = false): T? {
        return listeners.put(
            id,
            ListenerEntry(listener, requireUiThread)
        )?.listener
    }

    /**
     * Removes a listener with the specified id.<br></br>
     * If there is no such listener, nothing happens.
     * @param id The id of the listener.
     * @return The removed listener or null if there was no such listener.
     */
    fun removeListener(id: String): T? {
        return listeners.remove(id)?.listener
    }

    /**
     * Removes all listeners.
     * If there are no listeners, nothing happens.
     */
    fun clearListeners() {
        listeners.clear()
    }

    /**
     * Calls the action for each listener.
     * @param action The action.
     * @see forEachListenerWithKey
     */
    protected open fun forEachListener(action: (T) -> Unit) {
        val listeners = listeners.entries
        for ((_, value) in listeners) {
            if (value.requireUiThread) {
                runOnUiThread { action(value.listener) }
            } else {
                action(value.listener)
            }
        }
    }

    /**
     * Calls the action for each listener.
     * The action receives the id of the listener and the listener itself.
     * @param action The action.
     */
    protected open fun forEachListenerWithKey(action: (String, T) -> Unit) {
        val listeners = listeners.entries
        for ((key, value) in listeners) {
            if (value.requireUiThread) {
                runOnUiThread { action(key, value.listener) }
            } else {
                action(key, value.listener)
            }
        }
    }

    private data class ListenerEntry<T>(
        val listener: T,
        val requireUiThread: Boolean
    )

}