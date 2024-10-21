package com.fivesoft.javautils.interfaces

/**
 * Interface for objects that can be recycled.
 */
interface Recyclable {

    /**
     * Recycles the object. Depending on the implementation,
     * this may reset the object's state and
     * make it ready for reuse, return it to a pool or other...
     */
    fun recycle();

}