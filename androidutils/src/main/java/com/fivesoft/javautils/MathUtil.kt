package com.fivesoft.javautils

import kotlin.random.Random

/**
 * Returns a random integer between the specified [min] and [max] (inclusive).
 */
fun randomInt(min: Int, max: Int) = (min..max).random()

/**
 * Returns a random long between the specified [min] and [max] (inclusive).
 */
fun randomLong(min: Long, max: Long) = (min..max).random()

/**
 * Returns a random float between the specified [min] and [max] (inclusive).
 */
fun randomFloat(min: Float, max: Float) = (min + Random.nextFloat() * (max - min))

/**
 * Returns a random double between the specified [min] and [max] (inclusive).
 */
fun randomDouble(min: Double, max: Double) = (min + Random.nextDouble() * (max - min))