package utils

import models.Player

/**
 * Utility functions for the Hangman game.
 */
object Utils {

    /**
     * Checks if the given index is valid for the given list.
     *
     * @param index The index to check.
     * @param list The list to check against.
     * @return `true` if the index is valid, `false` otherwise.
     */
    @JvmStatic
    fun isValidIndex(index: Int, list: List<Any>): Boolean {
        return index >= 0 && index < list.size
    }

    /**
     * Checks if the given object is present in the given list.
     *
     * @param obj The object to check for.
     * @param list The list to check against.
     * @return `true` if the object is present, `false` otherwise.
     */
    @JvmStatic
    fun checkIsObjectPresent(obj: Any, list: List<Any>) = list.contains(obj)

    /**
     * Checks if the given character is present in the given list (ignoring case).
     *
     * @param list The list to check against.
     * @param char The character to check for.
     * @return `true` if the character is present, `false` otherwise.
     */
    @JvmStatic
    fun charListContainsIgnoreCase(list: List<Any>, char: Char) = list.contains(char) || list.contains(char.lowercase())

    /**
     * Returns the index of the given character in the given list, ignoring case.
     *
     * @param list The list to search for the character.
     * @param char The character to search for.
     * @return The index of the character in the list, or -1 if it's not present.
     */
    @JvmStatic
    fun indexOfCharIgnoreCase(list: List<Any>, char: Char): Int {
        list.forEach {
            if (it == char || it == char.lowercase()) return list.indexOf(it)
        }
        return -1
    }

    /**
     * Checks if the given player name is unique among the given list of players.
     *
     * @param name The name to check.
     * @param list The list of players to check against.
     * @return `true` if the name is unique, `false` otherwise.
     */
    @JvmStatic
    fun checkIsUniqueName(name: String, list: List<Player>) = !list.any {
        it.name.lowercase() == name.lowercase()
    }
}
