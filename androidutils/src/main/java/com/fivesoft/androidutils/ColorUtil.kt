package com.fivesoft.androidutils

import android.content.res.ColorStateList
import android.graphics.Color

/**
 * Convert an integer to a ColorStateList.
 * Equivalent to ColorStateList.valueOf(int)
 * @return ColorStateList
 */
fun Int.toColorStateList(): ColorStateList {
    return ColorStateList.valueOf(this)
}

/**
 * Convert an integer to a hex color string
 * @param alpha Include alpha channel
 * @return Hex color string
 */
fun Int.toHexColorString(alpha: Boolean = true): String {
    return if (alpha) String.format("#%08X", this)
    else String.format("#%06X", 0xFFFFFF and this)
}

/**
 * Set the alpha channel of a color
 * @param alpha Alpha value (0-255)
 */
fun Int.setAlpha(alpha: UByte): Int {
    return this and 0x00FFFFFF or (alpha.toInt() shl 24)
}

/**
 * Set the red channel of a color
 * @param red Red value (0-255)
 */
fun Int.setRed(red: UByte): Int {
    return (this.toLong() and 0xFF00FFFF or (red.toLong() shl 16)).toInt()
}

/**
 * Set the green channel of a color
 * @param green Green value (0-255)
 */
fun Int.setGreen(green: UByte): Int {
    return (this.toLong() and 0xFFFF00FF or (green.toLong() shl 8)).toInt()
}

/**
 * Set the blue channel of a color
 * @param blue Blue value (0-255)
 */
fun Int.setBlue(blue: UByte): Int {
    return this and 0xFFFFFF00.toInt() or blue.toInt()
}

/**
 * Generates a random color
 * @param alphaMin Minimum alpha value (0-255)
 * @param alphaMax Maximum alpha value (0-255)
 * @param redMin Minimum red value (0-255)
 * @param redMax Maximum red value (0-255)
 * @param greenMin Minimum green value (0-255)
 * @param greenMax Maximum green value (0-255)
 * @param blueMin Minimum blue value (0-255)
 * @param blueMax Maximum blue value (0-255)
 * @return Random color
 */
fun randomColor(
    alphaMin: UByte = 0u,
    alphaMax: UByte = 255u,
    redMin: UByte = 0u,
    redMax: UByte = 255u,
    greenMin: UByte = 0u,
    greenMax: UByte = 255u,
    blueMin: UByte = 0u,
    blueMax: UByte = 255u
): Int {
    val alpha = (alphaMin.toInt()..alphaMax.toInt()).random().toUByte()
    val red = (redMin.toInt()..redMax.toInt()).random().toUByte()
    val green = (greenMin.toInt()..greenMax.toInt()).random().toUByte()
    val blue = (blueMin.toInt()..blueMax.toInt()).random().toUByte()
    return Color.argb(
        alpha.toInt(),
        red.toInt(),
        green.toInt(),
        blue.toInt()
    )
}

/**
 * Generates a random color with a specified brightness
 * @param minBrightness Minimum brightness (0-255) where 0 is dark and 255 is bright
 * @param maxBrightness Maximum brightness (0-255) where 0 is dark and 255 is bright
 * @return Random color
 */
fun randomColor(minBrightness: UByte = 0u,
                maxBrightness: UByte = 255u): Int {
    val brightness = (minBrightness.toInt()..maxBrightness.toInt()).random().toUByte()
    return Color.HSVToColor(floatArrayOf(
        (0..360).random().toFloat(),
        (0..100).random().toFloat(),
        (brightness.toFloat() / 255.0f)
    ))
}