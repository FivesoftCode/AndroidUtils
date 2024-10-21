package com.fivesoft.androidutils.logging

/**
 * Log this object with [X.Priority.VERBOSE] priority. Tag will be automatically generated.
 * Equivalent to calling [X.v] with this object as the parameter.
 * @param fieldName Optional field name to include in the log message.
 * When provided, the log message will be in the format "fieldName = this".
 * @return The object itself.
 * @see X.v
 */
fun <T> T.logv(fieldName: String? = null) : T {
    val msg : Any? = if (fieldName == null) this else "$fieldName = $this"
    X.log(X.Priority.VERBOSE, X.TAG_AUTO, msg, 1)
    return this
}

/**
 * Log this object with [X.Priority.DEBUG] priority. Tag will be automatically generated.
 * Equivalent to calling [X.d] with this object as the parameter.
 * @param fieldName Optional field name to include in the log message.
 * When provided, the log message will be in the format "fieldName = this".
 * @see X.d
 */
fun <T> T.logd(fieldName: String? = null) : T {
    val msg : Any? = if (fieldName == null) this else "$fieldName = $this"
    X.log(X.Priority.DEBUG, X.TAG_AUTO, msg, 1)
    return this
}

/**
 * Log this object with [X.Priority.INFO] priority. Tag will be automatically generated.
 * Equivalent to calling [X.i] with this object as the parameter.
 * @param fieldName Optional field name to include in the log message.
 * When provided, the log message will be in the format "fieldName = this".
 * @see X.i
 */
fun <T> T.logi(fieldName: String? = null) : T {
    val msg : Any? = if (fieldName == null) this else "$fieldName = $this"
    X.log(X.Priority.INFO, X.TAG_AUTO, msg, 1)
    return this
}

/**
 * Log this object with the [X.Priority.WARN] priority. Tag will be automatically generated.
 * Equivalent to calling [X.w] with this object as the parameter.
 * @param fieldName Optional field name to include in the log message.
 * When provided, the log message will be in the format "fieldName = this".
 * @see X.w
 */
fun <T> T.logw(fieldName: String? = null) : T {
    val msg : Any? = if (fieldName == null) this else "$fieldName = $this"
    X.log(X.Priority.WARN, X.TAG_AUTO, msg, 1)
    return this
}

/**
 * Log this object with the [X.Priority.ERROR] priority. Tag will be automatically generated.
 * Equivalent to calling [X.e] with this object as the parameter.
 * @param fieldName Optional field name to include in the log message.
 * When provided, the log message will be in the format "fieldName = this".
 * @see X.e
 */
fun <T> T.loge(fieldName: String? = null) : T {
    val msg : Any? = if (fieldName == null) this else "$fieldName = $this"
    X.log(X.Priority.ERROR, X.TAG_AUTO, msg, 1)
    return this
}

/**
 * Log this object with the [X.Priority.ASSERT] priority. Tag will be automatically generated.
 * Equivalent to calling [X.a] with this object as the parameter.
 * @param fieldName Optional field name to include in the log message.
 * When provided, the log message will be in the format "fieldName = this".
 * @see X.a
 */
fun <T> T.loga(fieldName: String? = null) : T {
    val msg : Any? = if (fieldName == null) this else "$fieldName = $this"
    X.log(X.Priority.ASSERT, X.TAG_AUTO, msg, 1)
    return this
}
