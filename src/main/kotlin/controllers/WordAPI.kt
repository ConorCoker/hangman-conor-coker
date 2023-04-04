package controllers

import models.Word
import utils.Utils
import java.util.Random

class WordAPI {

    private val words: ArrayList<Word> = ArrayList()

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
                .toString() + " " + it.word +", Difficulty: " + it.difficulty
        }
    }

    fun getRandomWord(difficulty: Int): Word {
        val random = Random()
        val filteredList = words.filter { it.difficulty == difficulty }
        if (filteredList.isNotEmpty()){
            return filteredList[random.nextInt(filteredList.size)]
        } else {
            val lowerDifficulty = difficulty - 1
            if (lowerDifficulty > 0) {
                return getRandomWord(lowerDifficulty)
            } else {
                val higherDifficulty = difficulty + 1
                if (words.any { it.difficulty == higherDifficulty }) {
                    return getRandomWord(higherDifficulty)
                }
            }
        }
        return words[random.nextInt(words.size)]
    }

    fun getWordByIndex(index: Int): Word? {
        return if (Utils.isValidIndex(index, words)) {
            words[index]
        } else null
    }

    init {
//        words.add(
//            Word(
//                "computer",
//                "An electronic device for storing and processing data",
//                3,
//                "An electronic device that can receive, store, and process data, and produce a result or output based on a stored program or sequence of instructions."
//            )
//        )
//        words.add(
//            Word(
//                "television",
//                "A system for transmitting visual images and sound that are reproduced on screens",
//                2,
//                "A system for transmitting visual images and sound that are reproduced on screens, chiefly used to broadcast programs for entertainment, information, and education."
//            )
//        )
//        words.add(
//            Word(
//                "restaurant",
//                "A place where people pay to sit and eat meals that are cooked and served on the premises",
//                2,
//                "A place where people go to eat meals that are cooked and served on the premises, typically paying for the food and service."
//            )
//        )
//        words.add(
//            Word(
//                "mountain",
//                "A large natural elevation of the earth's surface rising abruptly from the surrounding level",
//                2,
//                "A large natural elevation of the earth's surface rising abruptly from the surrounding level; a large steep hill."
//            )
//        )
//        words.add(
//            Word(
//                "guitar",
//                "A stringed musical instrument played with a plectrum or finger, typically having six strings",
//                2,
//                "A musical instrument played with a plectrum or the fingers, typically having six strings tuned to E, A, D, G, B, and E."
//            )
//        )
//        words.add(
//            Word(
//                "ocean",
//                "A very large expanse of sea, in particular each of the main areas into which the sea is divided geographically",
//                2,
//                "A very large expanse of sea, especially each of the main areas into which the sea is divided geographically."
//            )
//        )
//        words.add(
//            Word(
//                "smartphone",
//                "A mobile phone that performs many of the functions of a computer, typically having a touchscreen interface, Internet access, and an operating system capable of running downloaded apps",
//                4,
//                "A mobile phone that has advanced features beyond traditional mobile phones, such as the ability to access the Internet, run software applications (apps), and typically a touchscreen interface."
//            )
//        )
//        words.add(
//            Word(
//                "university",
//                "A high-level educational institution in which students study for degrees and academic research is done",
//                2,
//                "A high-level educational institution where students study for degrees and academic research is done."
//            )
//        )
//        words.add(
//            Word(
//                "aviation",
//                "The flying or operating of aircraft",
//                3,
//                "The flying or operating of aircraft, or the design, construction, and maintenance of aircraft."
//            )
//        )
//        words.add(
//            Word(
//                "soccer",
//                "A game played by two teams of eleven players with a round ball that may not be touched with the hands or arms during play except by the goalkeeper",
//                2,
//                "A game played by two teams of eleven players with a round ball that may not be touched with the hands or arms during play except by the goalkeeper, the objective of which is to score goals by kicking or heading the ball into the opposing team's goal."
//            )
//        )
//        words.add(
//            Word(
//                "medicine",
//                "The science or practice of the diagnosis, treatment, and prevention of disease",
//                3,
//                "The science or practice of the diagnosis, treatment, and prevention of disease, as well as the maintenance of good health."
//            )
//        )
//        words.add(
//            Word(
//                "astronomy",
//                "The branch of science that deals with celestial objects, space, and the physical universe as a whole",
//                4,
//                "The branch of science that deals with celestial objects, space, and the physical universe as a whole."
//            )
//        )
//        words.add(
//            Word(
//                "biology",
//                "The study of living organisms, divided into many specialized fields that cover their morphology, physiology, anatomy, behavior, origin, and distribution",
//                3,
//                "The study of living organisms, including their morphology, physiology, anatomy, behavior, origin, and distribution."
//            )
//        )
//    }
    }
}