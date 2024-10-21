package com.fivesoft.javautils.collections

import java.util.LinkedList

/**
 * A list with a limited size. When the list reaches the maximum size, the oldest item is removed.
 * @param T The type of the items in the list.
 * @property maxSize The maximum size of the list.
 */
class LimitedSizeList<T>(private val maxSize: Int) : Collection<T> {

    private val list = LinkedList<T>()

    /**
     * Adds an item to the list. If the list reaches the maximum size, the oldest item is removed.
     * @param item The item to add.
     */
    fun add(item: T) {
        if (list.size == maxSize) {
            list.removeFirst() // Remove the oldest item
        }
        list.addLast(item) // Add the new item at the end
    }

    /**
     * Clears the list.
     */
    fun clear() {
        list.clear()
    }

    /**
     * Returns the item at the specified index.
     * @param index The index of the item.
     * @return The item at the specified index.
     */
    fun get(index: Int): T {
        return list[index]
    }

    /**
     * Removes the item at the specified index.
     * @param index The index of the item to remove.
     */
    fun remove(index: Int) {
        list.removeAt(index)
    }

    /**
     * Removes the last item from the list.
     */
    fun removeLast() {
        list.removeLast()
    }

    /**
     * Removes the first item from the list.
     */
    fun removeFirst() {
        list.removeFirst()
    }

    /**
     * Returns the number of elements in this list.
     * @return The number of elements in this list.
     */
    override val size: Int
        get() = list.size

    /**
     * Checks if the list contains any elements.
     * @return true if the list is empty, false otherwise.
     */
    override fun isEmpty() = list.isEmpty()

    /**
     * Checks if the list contains all the specified elements.
     * @param elements The elements to check.
     * @return true if the list contains all the elements, false otherwise.
     */
    override fun containsAll(elements: Collection<T>): Boolean {
        return list.containsAll(elements)
    }

    /**
     * Checks if the list contains the specified element.
     * @param element The element to check.
     * @return true if the list contains the element, false otherwise.
     */
    override fun contains(element: T) = list.contains(element)

    /**
     * Returns the list as a [List].
     * @return The list.
     */
    fun toList(): List<T> = list.toList()

    /**
     * Returns iterator for the list.
     * @return The iterator.
     */
    override fun iterator(): Iterator<T> = list.iterator()
}