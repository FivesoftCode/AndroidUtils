package com.fivesoft.androidutils.listeners

/**
 * A listener manager with public access to the [forEachListener] and [forEachListenerWithKey] methods.
 */
class InternalListenerManager<T> : ListenerManager<T>() {

    /**
     * @inheritDoc
     */
    public override fun forEachListener(action: (T) -> Unit) {
        super.forEachListener(action)
    }

    /**
     * @inheritDoc
     */
    public override fun forEachListenerWithKey(action: (String, T) -> Unit) {
        super.forEachListenerWithKey(action)
    }

}