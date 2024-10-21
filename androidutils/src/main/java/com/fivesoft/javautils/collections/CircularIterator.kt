package com.fivesoft.javautils.collections

/**
 * An iterator that iterates over the elements of an iterable in a circular manner.
 * @param T The type of elements in the iterable.
 * @property iterable The iterable to iterate over.
 */
class CircularIterator<T>(private val iterable: Iterable<T>) : Iterator<T> {

    private var iterator = iterable.iterator()

    /**
     * Returns true if there are more elements to iterate over.
     * Returns false only if the iterable is empty.
     * @return True if there are more elements, false otherwise.
     */
    override fun hasNext(): Boolean {
        //When the iterable is a collection,
        // we can use the size to determine if there are more elements
        // which is more efficient than creating new iterator and checking hasNext()
        val size = tryGetSize()
        if (size >= 0) {
            return size > 0
        }
        //Otherwise, we need to create a new iterator and check hasNext()
        return iterable.iterator()
            .hasNext()
    }

    /**
     * Returns the next element in the iteration.
     * If the end of the iterable is reached, the iteration starts from the beginning.
     * @return The next element in the iteration.
     */
    override fun next(): T {
        if (!iterator.hasNext()) {
            // Create a new iterator when the current one reaches the end
            iterator = iterable.iterator()
        }
        return iterator.next()
    }

    //Try to get the size of the iterable
    //Returns -1 if the size is not available
    private fun tryGetSize() : Int {
        if (iterable is Collection<*>) {
            return (iterable as Collection<*>).size
        }
        return -1
    }

    companion object {
        /**
         * Returns circular iterator for this iterable.
         * @receiver The iterable to iterate over.
         * @return The circular iterator.
         */
        inline fun <reified T> Iterable<T>.circularIterator() = CircularIterator(this)
    }

}
