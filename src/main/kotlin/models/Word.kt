package models

data class Word(
    val word: String,
    val hint: String,
    val maxGuesses: Int,
    val difficulty: Int,
    val definition: String
)