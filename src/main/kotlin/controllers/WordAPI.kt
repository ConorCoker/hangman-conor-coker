package controllers

import models.Word

class WordAPI {

    private var words = hashMapOf<Int, Word>()
    private var nextIndex = 0

    fun addWord(word: Word):Word? {
        nextIndex++
         return words.put(nextIndex, word)
    }

    fun numberOfWords() = words.size


}