package models

/**
 * Data class representing a word with its hint, difficulty, and definition.
 *
 * @property word The word.
 * @property hint The hint for the word.
 * @property difficulty The difficulty level of the word.
 * @property definition The definition of the word.
 * @property solved Indicates whether the word has been solved by the player or not.
 */
data class Word(
    val word: String,
    val hint: String,
    val difficulty: Int,
    val definition: String
) {
    var solved = false
}
