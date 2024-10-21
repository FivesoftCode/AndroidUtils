package com.fivesoft.androidutils

import android.content.Context
import android.util.TypedValue
import android.view.View
import kotlin.math.roundToInt

/**
 * Converts [TypedValue.COMPLEX_UNIT_DIP] to pixels.
 * @param dp value in dp
 */
fun Context.dpToPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

/**
 * Converts [TypedValue.COMPLEX_UNIT_SP] to pixels.
 * @param sp value in sp
 */
fun Context.spToPx(sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)

/**
 * Converts [TypedValue.COMPLEX_UNIT_MM] (millimeters) to pixels.
 * @param mm value in millimeters
 */
fun Context.mmToPx(mm: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, resources.displayMetrics)

/**
 * Converts inches to pixels.
 * @param inValue value in inches
 */
fun Context.inToPx(inValue: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inValue, resources.displayMetrics)

/**
 * Converts [TypedValue.COMPLEX_UNIT_PT] to pixels.
 * @param pt value in points
 */
fun Context.ptToPx(pt: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, pt, resources.displayMetrics)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_DIP].
 * @param px value in pixels
 */
fun Context.pxToDp(px: Float) = px / (resources.displayMetrics.xdpi / 160.0f)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_SP].
 * @param px value in pixels
 */
fun Context.pxToSp(px: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, resources.displayMetrics)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_MM].
 * @param px value in pixels
 */
fun Context.pxToMm(px: Float) = px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, resources.displayMetrics)

/**
 * Converts pixels to inches.
 * @param px value in pixels
 */
fun Context.pxToIn(px: Float) = px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 1f, resources.displayMetrics)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_PT].
 * @param px value in pixels
 */
fun Context.pxToPt(px: Float) = px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 1f, resources.displayMetrics)

/**
 * Converts [TypedValue.COMPLEX_UNIT_DIP] to pixels.
 * @param dp value in dp
 */
fun Context.dpToPx(dp: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).roundToInt()

/**
 * Converts [TypedValue.COMPLEX_UNIT_SP] to pixels.
 * @param sp value in sp
 */
fun Context.spToPx(sp: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics).roundToInt()

/**
 * Converts [TypedValue.COMPLEX_UNIT_MM] (millimeters) to pixels.
 * @param mm value in millimeters
 */
fun Context.mmToPx(mm: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm.toFloat(), resources.displayMetrics).roundToInt()

/**
 * Converts inches to pixels.
 * @param inValue value in inches
 */
fun Context.inToPx(inValue: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inValue.toFloat(), resources.displayMetrics).roundToInt()

/**
 * Converts [TypedValue.COMPLEX_UNIT_PT] to pixels.
 * @param pt value in points
 */
fun Context.ptToPx(pt: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, pt.toFloat(), resources.displayMetrics).roundToInt()

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_DIP].
 * @param px value in pixels
 */
fun Context.pxToDp(px: Int) = (px.toFloat() / (resources.displayMetrics.xdpi / 160.0f)).roundToInt()

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_SP].
 * @param px value in pixels
 */
fun Context.pxToSp(px: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px.toFloat(), resources.displayMetrics).roundToInt()

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_MM].
 * @param px value in pixels
 */
fun Context.pxToMm(px: Int) = (px.toFloat() / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, resources.displayMetrics)).roundToInt()

/**
 * Converts pixels to inches.
 * @param px value in pixels
 */
fun Context.pxToIn(px: Int) = (px.toFloat() / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 1f, resources.displayMetrics)).roundToInt()

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_PT].
 * @param px value in pixels
 */
fun Context.pxToPt(px: Int) = (px.toFloat() / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 1f, resources.displayMetrics)).roundToInt()

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_DIP].
 * @param px value in pixels
 */
fun View.pxToDp(px: Float) = context.pxToDp(px)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_SP].
 * @param px value in pixels
 */
fun View.pxToSp(px: Float) = context.pxToSp(px)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_MM].
 * @param px value in pixels
 */
fun View.pxToMm(px: Float): Float {
    return context.pxToMm(px)
}

/**
 * Converts pixels to inches.
 * @param px value in pixels
 */
fun View.pxToIn(px: Float) = context.pxToIn(px)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_PT].
 * @param px value in pixels
 */
fun View.pxToPt(px: Float) = context.pxToPt(px)

/**
 * Converts [TypedValue.COMPLEX_UNIT_DIP] to pixels.
 * @param dp value in dp
 */
fun View.dpToPx(dp: Float) = context.dpToPx(dp)

/**
 * Converts [TypedValue.COMPLEX_UNIT_SP] to pixels.
 * @param sp value in sp
 */
fun View.spToPx(sp: Float) = context.spToPx(sp)

/**
 * Converts [TypedValue.COMPLEX_UNIT_MM] (millimeters) to pixels.
 * @param mm value in millimeters
 */
fun View.mmToPx(mm: Float) = context.mmToPx(mm)

/**
 * Converts inches to pixels.
 * @param in value in inches
 */
fun View.inToPx(inValue: Float) = context.inToPx(inValue)

/**
 * Converts [TypedValue.COMPLEX_UNIT_PT] to pixels.
 * @param pt value in points
 */
fun View.ptToPx(pt: Float) = context.ptToPx(pt)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_DIP].
 * @param px value in pixels
 */
fun View.pxToDp(px: Int) = context.pxToDp(px)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_SP].
 * @param px value in pixels
 */
fun View.pxToSp(px: Int) = context.pxToSp(px)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_MM].
 * @param px value in pixels
 */
fun View.pxToMm(px: Int) = context.pxToMm(px)

/**
 * Converts pixels to inches.
 * @param px value in pixels
 */
fun View.pxToIn(px: Int) = context.pxToIn(px)

/**
 * Converts pixels to [TypedValue.COMPLEX_UNIT_PT].
 * @param px value in pixels
 */
fun View.pxToPt(px: Int) = context.pxToPt(px)

/**
 * Converts [TypedValue.COMPLEX_UNIT_DIP] to pixels.
 * @param dp value in dp
 */
fun View.dpToPx(dp: Int) = context.dpToPx(dp)

/**
 * Converts [TypedValue.COMPLEX_UNIT_SP] to pixels.
 * @param sp value in sp
 */
fun View.spToPx(sp: Int) = context.spToPx(sp)

/**
 * Converts [TypedValue.COMPLEX_UNIT_MM] (millimeters) to pixels.
 * @param mm value in millimeters
 */
fun View.mmToPx(mm: Int) = context.mmToPx(mm)

/**
 * Converts inches to pixels.
 * @param in value in inches
 */
fun View.inToPx(inValue: Int) = context.inToPx(inValue)

/**
 * Converts [TypedValue.COMPLEX_UNIT_PT] to pixels.
 * @param pt value in points
 */
fun View.ptToPx(pt: Int) = context.ptToPx(pt)
