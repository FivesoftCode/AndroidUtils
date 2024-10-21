package com.fivesoft.javautils

import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.absoluteValue

/**
 * Formats a number to a metric unit, for human-readable output.
 * @param srcMetric The source metric unit.
 * @param srcPrefix The source metric prefix.
 * @param dstPrefix The destination metric prefix. If null, the best prefix will be chosen automatically.
 * @param decimalPlaces The number of decimal places to show.
 * @param constantDecimalPlaces If true, the number of decimal places will be constant.
 * Missing decimal places will be filled with zeros.
 * @param unitSeparator The separator between the value and the unit.
 * @param showMetric If true, the metric symbol will be shown.
 * @param prefixIncludeOnly An array of prefixes to include in the search for the best prefix.
 * If null, all prefixes will be included.
 * @param prefixExcluded An array of prefixes to exclude from the search for the best prefix.
 * @return The formatted number.
 */
fun Number.formatMetric(
    srcMetric: Metric,
    srcPrefix: Prefix = Prefix.NONE,
    dstPrefix: Prefix? = null,
    decimalPlaces: Int = 2,
    constantDecimalPlaces: Boolean = false,
    unitSeparator: String = " ",
    showMetric: Boolean = true,
    prefixIncludeOnly: Array<Prefix>? = null,
    prefixExcluded: Array<Prefix> = arrayOf()
): String {
    val b10 = BigDecimal(10)
    //Convert the source value to the base unit
    val srcVal = BigDecimal(this.toString())
        .multiply(
            b10.pow(srcPrefix.power, MathContext.UNLIMITED)
        )
    //Check if auto prefix is enabled
    if (dstPrefix == null) {
        //Choose the best prefix
        val bestPrefix = findBestMetricPrefix(prefixIncludeOnly, prefixExcluded)
        return formatMetric(
            srcMetric,
            srcPrefix, bestPrefix,
            decimalPlaces, constantDecimalPlaces, unitSeparator
        )
    }
    //Convert the value to the destination prefix
    val dstValue = if(dstPrefix == Prefix.NONE) {
        srcVal
    } else {
        srcVal.divide(b10.pow(dstPrefix.power, MathContext(decimalPlaces)))
    }
    //Format the value
    val value = if (constantDecimalPlaces) {
        dstValue.setScale(decimalPlaces)
    } else {
        dstValue.stripTrailingZeros()
    }
    //Build the result string
    var res = "$value$unitSeparator"
    if (showMetric) {
        res += "${dstPrefix.symbol}${srcMetric.symbol}"
    }
    return res
}

/**
 * Finds the best metric prefix for a number.
 * @param onlyInclude An array of prefixes to include in the search for the best prefix.
 * If null, all prefixes will be included.
 * @param excluded An array of prefixes to exclude from the search for the best prefix.
 * @return The best metric prefix.
 */
fun Number.findBestMetricPrefix(
    onlyInclude: Array<Prefix>? = null,
    excluded: Array<Prefix> = arrayOf()
): Prefix {
    val value = BigDecimal(this.toString(), MathContext.UNLIMITED)
    var prefixes = onlyInclude?.toList()
        ?.sortedByDescending { it.power }
        ?: Prefix.entries
    prefixes = prefixes.filter {
        it != Prefix.NONE && !excluded.contains(it)
    }
    //Find power of 10 closest to value
    val b1 = BigDecimal(1)
    val b10 = BigDecimal(10)
    var pow = 0
    var v = value
    if(v < b1) {
        while(v.abs() < b1) {
            v = v.multiply(b10)
            pow--
        }
    } else {
        while(v.abs() > b1) {
            v = v.divide(b10)
            pow++
        }
    }
    //Find closest prefix to power found
    var smallestDiff = Int.MAX_VALUE
    var closestPrefix = Prefix.NONE
    for(prefix in prefixes) {
        val diff = (prefix.power - pow).absoluteValue
        if(diff < smallestDiff) {
            smallestDiff = diff
            closestPrefix = prefix
        }
    }
    return closestPrefix
}

/**
 * Represents a metric prefix.
 * For more details, see [Wikipedia](https://en.wikipedia.org/wiki/Metric_prefix)
 */
enum class Prefix(
    val power: Int,
    val symbol: String
)
{
    QUETTA(30, "Q"),
    RONNA(27, "R"),
    YOTTA(24, "Y"),
    ZETTA(21, "Z"),
    EXA(18, "E"),
    PETA(15, "P"),
    TERA(12, "T"),
    GIGA(9, "G"),
    MEGA(6, "M"),
    KILO(3, "k"),
    HECTO(2, "h"),
    DECA(1, "da"),
    NONE(0, ""),
    DECI(-1, "d"),
    CENTI(-2, "c"),
    MILLI(-3, "m"),
    MICRO(-6, "µ"),
    NANO(-9, "n"),
    PICO(-12, "p"),
    FEMTO(-15, "f"),
    ATTO(-18, "a"),
    ZEPTO(-21, "z"),
    YOCTO(-24, "y"),
    RONTO(27, "r"),
    QUONTO(30, "q")
}

/**
 * Represents a metric unit.
 * For more details, see [Wikipedia](https://en.wikipedia.org/wiki/International_System_of_Units)
 */
enum class Metric(val symbol: String) {
    METER("m"),
    GRAM("g"),
    SECOND("s"),
    AMPERE("A"),
    KELVIN("K"),
    MOLE("mol"),
    CANDELA("cd"),
    HERTZ("Hz"),
    NEWTON("N"),
    PASCAL("Pa"),
    JOULE("J"),
    WATT("W"),
    COULOMB("C"),
    VOLT("V"),
    FARAD("F"),
    OHM("Ω"),
    SIEMENS("S"),
    WEBER("Wb"),
    TESLA("T"),
    HENRY("H"),
    DEGREE_CELSIUS("°C"),
    LUMEN("lm"),
    LUX("lx"),
    BECQUEREL("Bq"),
    GRAY("Gy"),
    SIEVERT("Sv"),
    KATAL("kat"),
    RADIAN("rad"),
    STERADIAN("sr"),
    LITER("L"),
    TONNE("t"),
    BYTE("B"),
    BIT("b"),
    BIT_PER_SECOND("bps"),
    BYTE_PER_SECOND("Bps")
}