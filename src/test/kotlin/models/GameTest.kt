package models

import controllers.PlayerAPI
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {

    private var game1: Game? = null
    private var game2: Game? = null
    private var game3: Game? = null
    private var game4: Game? = null
    private var game5: Game? = null

    private var player1: Player? = null
    private var player2: Player? = null

    private var players: PlayerAPI? = null

    @BeforeEach
    fun setup() {

        player1 = Player("Conor", "password")
        player2 = Player("Aoife", "test")

        players = PlayerAPI()

        players!!.addPlayer(player1!!)
        players!!.addPlayer(player2!!)

        game1 = Game(
            player1!!,
            player2!!,
            word = Word(
                "Test",
                "To check is something working",
                1,
                "a procedure intended to establish the quality, performance, or reliability of something, especially before it is taken into widespread use."
            )
        )
        game2 = Game(
            player1!!, player2!!,
            word = Word(
                "Hangman",
                "A game involving a man",
                2,
                "a game for two in which one player tries to guess the letters of a word, the other player recording failed attempts by drawing a gallows and someone hanging on it, line by line."
            )
        )
        game3 = Game(
            player1!!, player2!!,
            word = Word(
                "Computer",
                "An electronic device for processing data",
                3,
                "An electronic device that can store, retrieve, and process data."
            )
        )
        game4 = Game(
            player1!!, player2!!,
            word = Word(
                "Chimpanzee",
                "A great ape native to Africa",
                4,
                "A great ape found in tropical forests of Africa, closely related to the orangutan, gorilla, and human."
            )
        )
        game5 = Game(
            player1!!, player2!!,
            word = Word(
                "Mississippi",
                "A state in the southern region of the United States",
                5,
                "A river in North America that flows from Minnesota to the Gulf of Mexico"
            )
        )
    }

    @AfterEach
    fun tearDown() {
        game1 = null
        game2 = null
        game3 = null
        game4 = null
        game5 = null

        player1 = null
        player2 = null
    }

    @Nested
    inner class Guessing {

        @Test
        fun `game starts with blank word represented by one underscore for each unknown letter`() {
            val underscoresBeforeCorrectGuess = game2!!.getUnderscores()
            assertTrue(underscoresBeforeCorrectGuess.size == 7)
            assertTrue(underscoresBeforeCorrectGuess.toString().contains("_"))
            assertFalse(underscoresBeforeCorrectGuess.toString().contains("Hangman"))
        }

        @Test
        fun `making a correct guess reveals that letter`() {
            assertFalse(game1!!.getUnderscores().toString().contains("e"))
            assertTrue(game1!!.getUnderscores()[1] == '_')
            game1!!.makeGuess('e', "Conor")
            assertTrue(game1!!.getUnderscores()[1] == 'e')
            assertEquals("[_, e, _, _]", game1!!.getUnderscores().toString())
            assertEquals('_', game1!!.getUnderscores()[0])
            game1!!.makeGuess('t', "Aoife")
            assertEquals("[t, e, _, _]", game1!!.getUnderscores().toString())
        }

        @Test
        fun `making a correct guess updates that players score`() {
            assertEquals(0, game2!!.getScores()[player1])
            game2!!.makeGuess('a', "Conor")
            assertEquals(1, game2!!.getScores()[player1])
        }

        @Test
        fun `making an incorrect guess adds that to incorrect letters array`() {
            assertEquals(0, game3!!.getIncorrectGuesses().size)
            game3!!.makeGuess('s', "Aoife")
            assertEquals(1, game3!!.getIncorrectGuesses().size)
            assertEquals('s', game3!!.getIncorrectGuesses()[0])
        }

        @Test
        fun `letters can be guessed more than once in words with duplicate letters`() {
            // testing word with two repeating letters
            // Chimpanzee
            assertEquals(0, game4!!.getScores()[player1])
            assertEquals("[_, _, _, _, _, _, _, _, _, _]", game4!!.getUnderscores().toString())
            game4!!.makeGuess('e', "Conor")
            assertEquals("[_, _, _, _, _, _, _, _, e, _]", game4!!.getUnderscores().toString())
            assertEquals(1, game4!!.getScores()[player1])
            game4!!.makeGuess('e', "Conor")
            assertEquals("[_, _, _, _, _, _, _, _, e, e]", game4!!.getUnderscores().toString())
            assertEquals(2, game4!!.getScores()[player1])

            // testing word with 4 repeating letters with "Mississippi"
            assertEquals("[_, _, _, _, _, _, _, _, _, _, _]", game5!!.getUnderscores().toString())
            game5!!.makeGuess('s', "Aoife")
            assertEquals("[_, _, s, _, _, _, _, _, _, _, _]", game5!!.getUnderscores().toString())
            game5!!.makeGuess('s', "Aoife")
            assertEquals("[_, _, s, s, _, _, _, _, _, _, _]", game5!!.getUnderscores().toString())
            game5!!.makeGuess('s', "Aoife")
            assertEquals("[_, _, s, s, _, s, _, _, _, _, _]", game5!!.getUnderscores().toString())
            game5!!.makeGuess('s', "Aoife")
            assertEquals("[_, _, s, s, _, s, s, _, _, _, _]", game5!!.getUnderscores().toString())
        }

        @Test
        fun `wrong answers take away a turn and when no turns remaining game over is true`() {
            // Mississippi
            assertFalse(game5!!.isGameOver())
            assertEquals(6, game5!!.numOfRemainingGuesses())
            assertEquals(0, game5!!.getScores()[player1])
            game5!!.makeGuess('x', "Conor")
            assertEquals(5, game5!!.numOfRemainingGuesses())
            assertEquals(0, game5!!.getScores()[player1])
            game5!!.makeGuess('a', "Conor")
            assertEquals(4, game5!!.numOfRemainingGuesses())
            game5!!.makeGuess('a', "Conor")
            assertEquals(3, game5!!.numOfRemainingGuesses())
            game5!!.makeGuess('a', "Conor")
            game5!!.makeGuess('a', "Conor")
            assertEquals(1, game5!!.numOfRemainingGuesses())
            assertFalse(game5!!.isGameOver())
            game5!!.makeGuess('a', "Conor")
            assertTrue(game5!!.isGameOver())
            assertEquals(1, player1!!.gamesPlayed)
        }
    }

    @Nested
    inner class GameSetup {
        @Test
        fun `a game will load a word of the selected difficulty when present`() {
            assertEquals(3, game3!!.getGameWord()!!.difficulty)
        }

        @Test
        fun `a game starts with 6 turns remaining`() {
            assertFalse(game5!!.isGameOver())
            assertEquals(6, game3!!.numOfRemainingGuesses())
        }
    }

    @Nested
    inner class GameEnding {

        @Test
        fun `player stats are updated correctly on game ending with a win`() {
            assertEquals(0, game2!!.getScores()[player1])
            game2!!.makeGuess('H', "Conor")
            assertEquals(1, game2!!.getScores()[player1])
            game2!!.makeGuess('a', "Conor")
            assertEquals(2, game2!!.getScores()[player1])
            game2!!.makeGuess('n', "Conor")
            assertEquals(3, game2!!.getScores()[player1])
            game2!!.makeGuess('g', "Conor")
            game2!!.makeGuess('M', "Conor")
            game2!!.makeGuess('a', "Conor")
            game2!!.makeGuess('n', "Conor")
            assertEquals(7, game2!!.getScores()[player1])
            assertEquals("[H, a, n, g, M, a, n]", game2!!.getUnderscores().toString())
            assertTrue(game2!!.isSolved())
            assertEquals(0, player1!!.losses)
            assertEquals(1, player1!!.wins)
            assertEquals(1, players!!.getPlayers()[0].gamesPlayed)
            assertTrue(game2!!.isGameOver())
        }

        @Test
        fun `player stats are updated successfully on a game loss`() {
            // Computer
            assertFalse(game3!!.isGameOver())
            assertFalse(game3!!.isSolved())
            assertEquals(0, player2!!.highestScore)
            game3!!.makeGuess('o', "Aoife")
            assertEquals(1, game3!!.getScores()[player2!!])
            assertEquals(6, game3!!.numOfRemainingGuesses())
            game3!!.makeGuess('s', "Aoife")
            game3!!.makeGuess('h', "Aoife")
            game3!!.makeGuess('q', "Aoife")
            game3!!.makeGuess('v', "Aoife")
            game3!!.makeGuess('b', "Aoife")
            assertEquals(1, game3!!.numOfRemainingGuesses())
            assertFalse(game3!!.isGameOver())
            assertFalse(game3!!.isSolved())
            game3!!.makeGuess('x', "Aoife")
            assertTrue(game3!!.isGameOver())
            assertEquals(1, player2!!.highestScore)
            assertEquals(0, player2!!.wins)
            assertEquals(1, player2!!.gamesPlayed)
        }

        @Test
        fun `word is set to solved on game win`() {
            assertFalse(game3!!.isSolved())
            assertFalse(game3!!.getGameWord()!!.solved)
            game3!!.makeGuess('c', "Aoife")
            game3!!.makeGuess('o', "Aoife")
            game3!!.makeGuess('m', "Aoife")
            game3!!.makeGuess('p', "Aoife")
            game3!!.makeGuess('u', "Aoife")
            game3!!.makeGuess('t', "Aoife")
            game3!!.makeGuess('e', "Aoife")
            game3!!.makeGuess('r', "Aoife")
            assertTrue(game3!!.isSolved())
            assertTrue(game3!!.getGameWord()!!.solved)
            assertTrue(game3!!.isGameOver())
        }

        @Test
        fun `game is set to finished but word remains unsolved on loss`() {
            assertFalse(game2!!.isGameOver())
            assertFalse(game2!!.isSolved())
            assertFalse(game2!!.getGameWord()!!.solved)
            game2!!.makeGuess('i', "Aoife")
            game2!!.makeGuess('d', "Aoife")
            game2!!.makeGuess('j', "Aoife")
            game2!!.makeGuess('b', "Aoife")
            game2!!.makeGuess('x', "Aoife")
            assertEquals(1, game2!!.numOfRemainingGuesses())
            game2!!.makeGuess('z', "Aoife")
            assertTrue(game2!!.isGameOver())
            assertFalse(game2!!.isSolved())
            assertFalse(game2!!.getGameWord()!!.solved)
        }
    }

    @Nested
    inner class GameScreen {

        @Test
        fun `hint is displayed when prompted`() {
            assertFalse(game1!!.printGameScreen().contains("To check is something working"))
            game1!!.makeGuess('1', "Conor")
            assertTrue(game1!!.printGameScreen().contains("To check is something working"))
        }

        @Test
        fun `definition is displayed when prompted`() {
            assertFalse(game2!!.printGameScreen().contains("a game for two in which one player tries to guess the letters of a word, the other player recording failed attempts by drawing a gallows and someone hanging on it, line by line"))
            game2!!.makeGuess('2', "Aoife")
            assertTrue(game2!!.printGameScreen().contains("a game for two in which one player tries to guess the letters of a word, the other player recording failed attempts by drawing a gallows and someone hanging on it, line by line"))
        }

        @Test
        fun `scoreboard is updated correctly on each guess`() {
            assertTrue(game2!!.printGameScreen().contains("Conor | 0"))
            game2!!.makeGuess('a', "Conor")
            assertTrue(game2!!.printGameScreen().contains("Conor | 1") && game2!!.printGameScreen().contains("Aoife | 0"))
            game2!!.makeGuess('n', "Aoife")
            assertTrue(game2!!.printGameScreen().contains("Conor | 1") && game2!!.printGameScreen().contains("Aoife | 1"))
        }
    }
}
