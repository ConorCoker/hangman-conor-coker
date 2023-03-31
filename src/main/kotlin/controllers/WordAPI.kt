package controllers

import models.Word

class WordAPI {

    private var words = ArrayList<Word>()


    fun addWord(word: Word) = words.add(word)


    fun numberOfWords() = words.size

    fun removeWordByIndex(indexToRemove:Int) = words.removeAt(indexToRemove)



}