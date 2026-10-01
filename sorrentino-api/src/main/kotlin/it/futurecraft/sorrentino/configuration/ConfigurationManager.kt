package it.futurecraft.sorrentino.configuration

/**
 * Manages configuration files for the plugin.
 * Files are cached in memory to minimize disk I/O and updates are only written when necessary.
 */
interface ConfigurationManager {
    /**
     * Indicates whether the file cache is empty.
     */
    val empty: Boolean

    /**
     * Saves the file data to disk if it has changed.
     *
     * Though this function is asynchronous, it is recommended to call it only when necessary,
     * since it involves disk I/O and configuration files may be large (especially messages files).
     *
     *
     * @param key The file key.
     * @param data The file data.
     * @return true only if saving was necessary.
     */
    suspend operator fun <S : File.Schema> set(key: File.Key<S>, data: S): Boolean

    /**
     * Loads the file data from disk or cache.
     *
     * @param key The file key.
     * @return The file data.
     */
    suspend operator fun <S : File.Schema> get(key: File.Key<S>): S

    /**
     * Saves the default value of the file if it doesn't exist.
     * @param key The file key.
     * @return true if the file has been created, false otherwise
     */
    suspend fun <S : File.Schema> default(key: File.Key<S>): Boolean

    /**
     * Clears the configuration cache.
     *
     * This will force all configurations to be reloaded from disk on the next access.
     */
    fun clear()
}