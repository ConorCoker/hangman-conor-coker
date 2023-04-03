package controllers

import models.Word
import utils.Utils
import java.util.Random

class WordAPI {

    private val words: ArrayList<Word> = ArrayList()



    init {
        words.add(Word("hello","",30,1,""))
    }

    fun addWord(word: Word): Boolean {
        return if (words.any { it.word.lowercase() == word.word.lowercase() }) {
            false
        } else {
            words.add(word)
        }
    }

    fun getWords() = words

    fun numberOfWords() = words.size

    fun removeWordByIndex(indexToRemove: Int) = words.removeAt(indexToRemove)

    fun showAllWords(): String {
        return if (words.isEmpty()) {
            "No saved words"
        } else words.joinToString(separator = "\n") {
            words.indexOf(it)
                .toString() + " " + it.word + ", Max Guesses: " + it.maxGuesses + ", Difficulty: " + it.difficulty
        }
    }

    fun getRandomWord(difficulty: Int): Word? {
        val random = Random()
        val filteredList = when (difficulty) {
            0 -> words
            else -> words.filter { it.difficulty == difficulty }
        }
        return if (filteredList.isEmpty()) {
            null
        } else filteredList[random.nextInt(filteredList.size)]
    }

    fun getWordByIndex(index: Int): Word? {
        return if (Utils.isValidIndex(index, words)) {
            words[index]
        } else null
    }
}