package models

import controllers.PlayerAPI
import controllers.WordAPI
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {

    private var game1:Game? = null
    private var game2:Game? = null
    private var game3:Game? = null
    private var game4:Game? = null
    private var game5:Game? = null

    private var player1:Player? = null
    private var player2:Player? = null

    private var wordAPI:WordAPI? = null
    private var playersAPI:PlayerAPI? = null

    @BeforeEach
    fun setup(){
        wordAPI = WordAPI()
        playersAPI = PlayerAPI()

        wordAPI!!.addWord(Word("Test","To check is something working",1,"a procedure intended to establish the quality, performance, or reliability of something, especially before it is taken into widespread use."))
        wordAPI!!.addWord(Word("Hangman","A game involving a man",2,"a game for two in which one player tries to guess the letters of a word, the other player recording failed attempts by drawing a gallows and someone hanging on it, line by line."))
        wordAPI!!.addWord(Word("Computer", "An electronic device for processing data", 3, "An electronic device that can store, retrieve, and process data."))
        wordAPI!!.addWord(Word("Chimpanzee", "A great ape native to Africa", 4, "A great ape found in tropical forests of Africa, closely related to the orangutan, gorilla, and human."))
        wordAPI!!.addWord(Word("Mississippi", "A state in the southern region of the United States", 5, "A river in North America that flows from Minnesota to the Gulf of Mexico"))


        playersAPI!!.addPlayer(Player("Conor","password"))
        playersAPI!!.addPlayer(Player("Aoife","test"))

        game1 = Game(wordAPI!!,playersAPI!!,1)
        game2 = Game(wordAPI!!,playersAPI!!,2)
        game3 = Game(wordAPI!!,playersAPI!!,3)
        game4 = Game(wordAPI!!,playersAPI!!,4)
        game5 = Game(wordAPI!!,playersAPI!!,5)


    }

    @AfterEach
    fun tearDown(){
        game1 = null
        game2 = null
        game3 = null
        game4 = null
        game5 = null

        player1 = null
        player2 = null

        playersAPI = null
        wordAPI = null
    }

    @Nested
    inner class Guessing{

        @Test
        fun `game starts with blank word represented by one underscore for each unknown letter`(){
            val underscoresBeforeCorrectGuess = game2!!.getUnderscores()
            assertTrue(underscoresBeforeCorrectGuess.size==7)
            assertTrue(underscoresBeforeCorrectGuess.toString().contains("_"))
            assertFalse(underscoresBeforeCorrectGuess.toString().contains("Hangman"))
        }

        @Test
        fun `making a correct guess reveals that letter`(){
            assertFalse(game1!!.getUnderscores().toString().contains("e"))
            assertTrue(game1!!.getUnderscores()[1] =='_')
            game1!!.makeGuess('e',"Conor")
            assertTrue(game1!!.getUnderscores()[1] =='e')
            assertEquals("[_, e, _, _]",game1!!.getUnderscores().toString())
            assertEquals('_',game1!!.getUnderscores()[3])
            game1!!.makeGuess('t',"Aoife")
            assertEquals("[_, e, _, t]",game1!!.getUnderscores().toString())
        }

        @Test
        fun `making a correct guess updates that players score`(){
            assertEquals(0,game2!!.getCurrentScore("Conor"))
            game2!!.makeGuess('a',"Conor")
            assertEquals(1,game2!!.getCurrentScore("Conor"))
        }



        @Test
        fun `making an incorrect guess adds that to incorrect letters array`(){
            assertEquals(0,game3!!.getIncorrectGuesses().size)
            game3!!.makeGuess('s',"Aoife")
            assertEquals(1,game3!!.getIncorrectGuesses().size)
            assertEquals('s',game3!!.getIncorrectGuesses()[0])
        }

        @Test
        fun `letters can be guessed more than once in words with duplicate letters`(){
            //testing word with two repeating letters
            assertEquals(0,game4!!.getCurrentScore("Conor"))
            assertEquals("[_, _, _, _, _, _, _, _, _, _]",game4!!.getUnderscores().toString())
            game4!!.makeGuess('e',"Conor")
            assertEquals("[_, _, _, _, _, _, _, _, e, _]",game4!!.getUnderscores().toString())
            assertEquals(1,game4!!.getCurrentScore("Conor"))
            game4!!.makeGuess('e',"Conor")
            assertEquals("[_, _, _, _, _, _, _, _, e, e]",game4!!.getUnderscores().toString())
            assertEquals(2,game4!!.getCurrentScore("Conor"))

            //testing word with 4 repeating letters with "Mississippi"
            assertEquals("[_, _, _, _, _, _, _, _, _, _, _]",game5!!.getUnderscores().toString())
            game5!!.makeGuess('s',"Aoife")
            assertEquals("[_, _, s, _, _, _, _, _, _, _, _]",game5!!.getUnderscores().toString())
            game5!!.makeGuess('s',"Aoife")
            assertEquals("[_, _, s, s, _, _, _, _, _, _, _]",game5!!.getUnderscores().toString())
            game5!!.makeGuess('s',"Aoife")
            assertEquals("[_, _, s, s, _, s, _, _, _, _, _]",game5!!.getUnderscores().toString())
            game5!!.makeGuess('s',"Aoife")
            assertEquals("[_, _, s, s, _, s, s, _, _, _, _]",game5!!.getUnderscores().toString())




        }



    }

    @Nested
    inner class GameSetup{
        @Test
        fun `a game will load a word of the selected difficulty when present`(){
            assertEquals(3,game3!!.getGameWord().difficulty)
        }
    }



}