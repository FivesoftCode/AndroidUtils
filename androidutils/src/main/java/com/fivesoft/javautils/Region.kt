package com.fivesoft.javautils

/**
 * Represents a region in the world.
 * @param name The name of the region.
 * @param symbol The symbol of the region. Example: "EU", "NA", "AS".
 * @param locales The locales of the region in ISO 3166-1 alpha-2 format. Example: "US", "DE", "FR".
 * @throws IllegalArgumentException If any of the locales is not a valid ISO 3166-1 alpha-2 code.
 */
class Region(
    val name: String? = null,
    val symbol: String? = null,
    locales: List<String>
) {

    companion object {
        private val WHITESPACE_REGEX = "\\s".toRegex()
    }

    init {
        for (locale in locales) {
            if (locale.length != 2) {
                throw IllegalArgumentException("Invalid locale: $locale")
            }
        }
    }

    /**
     * The locales of the region in ISO 3166-1 alpha-2 format. Example: "US", "DE", "FR".
     */
    val locales = locales.map { prepareLocale(it) }.toSet()
    
    /**
     * Checks if the region contains the given locale.
     * @param locale The locale in ISO 3166-1 alpha-2 format. Example: "US", "DE", "FR".
     * @return True if the region contains the locale, false otherwise.
     * @throws IllegalArgumentException If the locale is not a valid ISO 3166-1 alpha-2 code.
     */
    fun contains(locale: String): Boolean {
        require(locale.length == 2) { "Invalid locale: $locale" }
        return locales.contains(prepareLocale(locale))
    }
    
    /**
     * Checks if the region contains all locales of the given region.
     * @param region The region to check.
     * @return True if the region contains all locales of the given region, false otherwise.
     */
    fun contains(region: Region): Boolean {
        return locales.containsAll(region.locales)
    }
    
    /**
     * Checks if the region has at least one locale in common with the given region.
     * @param region The region to check.
     * @return True if the region intersects with the given region, false otherwise.
     */
    fun intersects(region: Region): Boolean {
        return locales.intersect(region.locales).isNotEmpty()
    }
    
    private fun prepareLocale(locale: String): String {
        return locale.uppercase()
            //Replace all whitespace
            .replace(WHITESPACE_REGEX, "")
    }
}