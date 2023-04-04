package models

data class Word(
    val word: String,
    val hint: String,
    val difficulty: Int,
    val definition: String
)