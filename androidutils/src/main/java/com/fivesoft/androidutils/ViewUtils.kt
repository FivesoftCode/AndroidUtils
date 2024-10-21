package com.fivesoft.androidutils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.UiThread
import androidx.core.util.TypedValueCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round

/**
 * Set the margins of a view.
 * @param left The left margin in pixels.
 * @param top The top margin in pixels.
 * @param right The right margin in pixels.
 * @param bottom The bottom margin in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMargins(
    left: Int, top: Int, right: Int, bottom: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(
            applyUnit(left, unit),
            applyUnit(top, unit),
            applyUnit(right, unit),
            applyUnit(bottom, unit)
        )
        requestLayout()
    }
    return this
}

/**
 * Set the margins of a view to the same value on all sides.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMargins(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setMargins(
        applyUnit(margin, unit),
        applyUnit(margin, unit),
        applyUnit(margin, unit),
        applyUnit(margin, unit)
    )
    return this
}

/**
 * Set the left margin of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginLeft(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(
            applyUnit(margin, unit),
            lp.topMargin, lp.rightMargin, lp.bottomMargin
        )
        requestLayout()
    }
    return this
}

/**
 * Set the right margin of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginRight(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(
            lp.leftMargin, lp.topMargin,
            applyUnit(margin, unit),
            lp.bottomMargin
        )
        requestLayout()
    }
    return this
}

/**
 * Set the top margin of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginTop(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(
            lp.leftMargin, applyUnit(margin, unit),
            lp.rightMargin, lp.bottomMargin
        )
        requestLayout()
    }
    return this
}

/**
 * Set the bottom margin of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginBottom(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(lp.leftMargin, lp.topMargin, lp.rightMargin, applyUnit(margin, unit))
        requestLayout()
    }
    return this
}

/**
 * Set the vertical margins of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginVertical(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(
            lp.leftMargin,
            applyUnit(margin, unit),
            lp.rightMargin,
            applyUnit(margin, unit)
        )
        requestLayout()
    }
    return this
}

/**
 * Set the horizontal margins of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginHorizontal(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(
            applyUnit(margin, unit),
            lp.topMargin,
            applyUnit(margin, unit),
            lp.bottomMargin
        )
        requestLayout()
    }
    return this
}

/**
 * Set the start margin of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginStart(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            lp.setMargins(
                applyUnit(margin, unit),
                lp.topMargin, lp.rightMargin, lp.bottomMargin
            )
        } else {
            lp.setMargins(
                lp.leftMargin, lp.topMargin,
                applyUnit(margin, unit),
                lp.bottomMargin
            )
        }
        requestLayout()
    }
    return this
}

/**
 * Set the end margin of a view.
 * @param margin The margin value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setMarginEnd(
    margin: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            lp.setMargins(
                lp.leftMargin, lp.topMargin,
                applyUnit(margin, unit), lp.bottomMargin
            )
        } else {
            lp.setMargins(
                applyUnit(margin, unit),
                lp.topMargin, lp.rightMargin, lp.bottomMargin
            )
        }
        requestLayout()
    }
    return this
}

/**
 * Sets padding of the view to the same value on all sides.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPadding(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(
        applyUnit(value, unit),
        applyUnit(value, unit),
        applyUnit(value, unit),
        applyUnit(value, unit)
    )
    return this
}

/**
 * Sets left padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingLeft(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(
        applyUnit(value, unit),
        paddingTop, paddingRight, paddingBottom
    )
    return this
}

/**
 * Sets right padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingRight(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(
        paddingLeft, paddingTop,
        applyUnit(value, unit),
        paddingBottom
    )
    return this
}

/**
 * Sets top padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingTop(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(
        paddingLeft,
        applyUnit(value, unit),
        paddingRight, paddingBottom
    )
    return this
}

/**
 * Sets bottom padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingBottom(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(paddingLeft, paddingTop, paddingRight, applyUnit(value, unit))
    return this
}

/**
 * Sets vertical padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingVertical(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(
        paddingLeft, applyUnit(value, unit),
        paddingRight,
        applyUnit(value, unit)
    )
    return this
}

/**
 * Sets horizontal padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingHorizontal(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    setPadding(
        applyUnit(value, unit),
        paddingTop, applyUnit(value, unit),
        paddingBottom
    )
    return this
}

/**
 * Sets start padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingStart(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
        setPadding(
            applyUnit(value, unit),
            paddingTop, paddingRight, paddingBottom
        )
    } else {
        setPadding(
            paddingLeft, paddingTop, applyUnit(value, unit),
            paddingBottom
        )
    }
    return this
}

/**
 * Sets end padding of the view.
 * @param value The padding value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setPaddingEnd(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
        setPadding(
            paddingLeft, paddingTop,
            applyUnit(value, unit),
            paddingBottom
        )
    } else {
        setPadding(
            applyUnit(value, unit),
            paddingTop, paddingRight, paddingBottom
        )
    }
    return this
}

/**
 * Gets the left margin of the view.
 * @return The left margin value in pixels.
 */
fun View.getMarginLeft(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        lp.leftMargin
    } else {
        0
    }
}

/**
 * Gets the right margin of the view.
 * @return The right margin value in pixels.
 */
fun View.getMarginRight(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        lp.rightMargin
    } else {
        0
    }
}

/**
 * Gets the top margin of the view.
 * @return The top margin value in pixels.
 */
fun View.getMarginTop(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        lp.topMargin
    } else {
        0
    }
}

/**
 * Gets the bottom margin of the view.
 * @return The bottom margin value in pixels.
 */
fun View.getMarginBottom(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        lp.bottomMargin
    } else {
        0
    }
}

/**
 * Gets the vertical margin of the view.
 * This will be equal to the sum of top and bottom margins.
 * @return The vertical margin value in pixels.
 */
fun View.getMarginVertical(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        lp.topMargin + lp.bottomMargin
    } else {
        0
    }
}

/**
 * Gets the horizontal margin of the view.
 * This will be equal to the sum of left and right margins.
 * @return The horizontal margin value in pixels.
 */
fun View.getMarginHorizontal(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        lp.leftMargin + lp.rightMargin
    } else {
        0
    }
}

/**
 * Gets the start margin of the view.
 * @return The start margin value in pixels.
 */
fun View.getMarginStart(): Int {
    val lp = layoutParams
    return if (lp is MarginLayoutParams) {
        if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            lp.leftMargin
        } else {
            lp.rightMargin
        }
    } else {
        0
    }
}

/**
 * Sets the width of the view.
 * @param value The width value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setWidth(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit
    unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    layoutParams.width = applyUnit(value, unit)
    requestLayout()
    return this
}

/**
 * Sets the height of the view.
 * @param value The height value in pixels.
 * @param unit The unit of the value. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setHeight(
    value: Int,
    @TypedValueCompat.ComplexDimensionUnit
    unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    layoutParams.height = applyUnit(value, unit)
    requestLayout()
    return this
}

/**
 * Sets width and height of the view.
 * @param width The width value in pixels.
 * @param height The height value in pixels.
 * @param unit The unit of the values. Default is [TypedValue.COMPLEX_UNIT_PX].
 */
fun View.setSize(
    width: Int,
    height: Int,
    @TypedValueCompat.ComplexDimensionUnit unit: Int = TypedValue.COMPLEX_UNIT_PX
) : View {
    layoutParams.width = applyUnit(width, unit)
    layoutParams.height = applyUnit(height, unit)
    requestLayout()
    return this
}

/**
 * Sets (overrides if exists) the animation of the view.
 * Previous animations are cancelled. (if performing)
 * <b>IMPORTANT NOTE: </b>When setting [showOnStart] or [hideOnEnd] to true, don't use [ViewPropertyAnimator.withEndAction] or [ViewPropertyAnimator.withStartAction]
 * on the returned [ViewPropertyAnimator] object. This method uses these callbacks internally,
 * so the auto show/hide feature may not work as expected.
 * @param duration The duration of the animation in milliseconds. Default is 300.
 * @param interpolator The interpolator of the animation. Default is [AccelerateDecelerateInterpolator].
 * @param startDelay The delay before the animation starts in milliseconds. Default is 0.
 * @param cancelListeners If true, cancels all listeners of the view. Default is true.
 * @param showOnStart If true, sets the visibility of
 * the view to [View.VISIBLE] when the animation starts. Default is false.
 * @param hideOnEnd If true, sets the visibility of
 * the view to [View.GONE]/[View.INVISIBLE] (depending on [useInvisibleOnHide] parameter) when the animation ends. Default is false.
 * @param useInvisibleOnHide If true, sets the visibility of the view to
 * [View.INVISIBLE] when the animation ends if [hideOnEnd] is enabled. Default is false.
 */
@UiThread
fun View.setAnimation(
    duration: Long = 300,
    interpolator: Interpolator = AccelerateDecelerateInterpolator(),
    startDelay: Long = 0,
    cancelListeners: Boolean = true,
    showOnStart: Boolean = false,
    hideOnEnd: Boolean = false,
    useInvisibleOnHide: Boolean = false
): ViewPropertyAnimator {
    val res: ViewPropertyAnimator = animate()
    if (cancelListeners) {
        res.setListener(null)
        res.setUpdateListener(null)
    }
    res.setStartDelay(startDelay)
    res.cancel()
    if (showOnStart) {
        res.withStartAction { visibility = View.VISIBLE }
    }
    if (hideOnEnd) {
        res.withEndAction {
            visibility = if (useInvisibleOnHide) View.INVISIBLE else View.GONE
        }
    }
    return res
        .setInterpolator(interpolator)
        .setDuration(duration)
}

/**
 * Sets view active state by changing [View.setEnabled] and [View.setAlpha] properties.
 * Focus is cleared and soft keyboard is hidden if the view is disabled.
 * Active state will be also applied to all child views.
 * @param active If true, the view will be enabled and alpha will be set to 1.0f.
 * Otherwise, the view will be disabled and alpha will be set to [inactiveAlpha].
 * @param inactiveAlpha The alpha value of the view when it is inactive. Default is 0.7f.
 */
fun View.setActive(active: Boolean, inactiveAlpha: Float = 0.7f) : View {
    runOnUiThread {
        setViewActiveInternal(
            this,
            active,
            true,
            inactiveAlpha
        )
    }
    return this
}

private fun setViewActiveInternal(
    view: View?,
    active: Boolean,
    changeAlpha: Boolean,
    inactiveAlpha: Float
) {
    if (view == null) return
    view.isEnabled = active
    if (changeAlpha) {
        view.alpha = if (active) 1f else inactiveAlpha
    }
    if (!active) {
        if (view.hasFocus()) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    } else {
        view.setOnTouchListener(null)
    }
    if (view is ViewGroup) {
        val vg = view
        for (i in 0 until vg.childCount) {
            setViewActiveInternal(vg.getChildAt(i), active, false, inactiveAlpha)
        }
    }
}

/**
 * Computes the total height of the content of a RecyclerView.
 * This method may be very expensive if the adapter has many items, for both CPU and memory.
 * Should be used with caution.
 */
fun RecyclerView.computeContentHeight(): Int {
    val adapter = adapter
    return if (adapter != null) {
        var height = 0
        for (i in 0 until adapter.itemCount) {
            val vh = adapter.createViewHolder(this, adapter.getItemViewType(i))
            adapter.onBindViewHolder(vh, i)
            vh.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            height += vh.itemView.measuredHeight
            height += vh.itemView.getMarginVertical()
        }
        height
    } else {
        0
    }
}

/**
 * Computes the total width of the content of a RecyclerView.
 * This method may be very expensive if the adapter has many items, for both CPU and memory.
 * Should be used with caution.
 */
fun RecyclerView.computeContentWidth(): Int {
    val adapter = adapter
    return if (adapter != null) {
        var width = 0
        for (i in 0 until adapter.itemCount) {
            val vh = adapter.createViewHolder(this, adapter.getItemViewType(i))
            adapter.onBindViewHolder(vh, i)
            vh.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            )
            width += vh.itemView.measuredWidth
            width += vh.itemView.getMarginHorizontal()
        }
        width
    } else {
        0
    }
}

//Internal function to apply unit to a value
private fun View.applyUnit(value: Int, unit: Int): Int {
    return round(TypedValue.applyDimension(unit, value.toFloat(), resources.displayMetrics))
        .toInt()
}