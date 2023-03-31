package controllers

import models.Player
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class PlayerAPITest {

    private var player1: Player? = null
    private var player2: Player? = null

    private var emptyPlayers: PlayerAPI? = PlayerAPI()
    private var populatedPlayers: PlayerAPI? = PlayerAPI()

    @BeforeEach
    fun setUp() {

        player1 = Player("Conor")
        player2 = Player("Aoife")

        populatedPlayers!!.addPlayer(player1!!)
        populatedPlayers!!.addPlayer(player2!!)



    }

    @AfterEach
    fun tearDown() {
        player1 = null
        player2 = null

        emptyPlayers = null
        populatedPlayers = null
    }

    @Nested
    inner class AddingRemovingPlayers{

        @Test
        fun `adding a player adds that player`(){
            assertEquals(2,populatedPlayers!!.numberOfPlayers())
            assertTrue(populatedPlayers!!.addPlayer(Player("test")))
            assertEquals(3,populatedPlayers!!.numberOfPlayers())

        }
        @Test
        fun `adding a player with the same name as another does not add and returns false (irrespective of case)`(){
            assertEquals(populatedPlayers!!.numberOfPlayers(),2)
            assertFalse(populatedPlayers!!.addPlayer(Player("CONOR")))
            assertEquals(populatedPlayers!!.numberOfPlayers(),2)
        }

        @Test
        fun `removing a player removes that player`(){
            assertEquals(2,populatedPlayers!!.numberOfPlayers())
            assertEquals(player2,populatedPlayers!!.removePlayerByIndex(1))
            assertEquals(1,populatedPlayers!!.numberOfPlayers())
        }

        @Test
        fun `adding a player to an empty list adds that player and returns valid size`(){
            assertEquals(0,emptyPlayers!!.numberOfPlayers())
            assertTrue(emptyPlayers!!.addPlayer(Player("test")))
            assertEquals(1,emptyPlayers!!.numberOfPlayers())
        }

    }

    @Nested
    inner class NewPlayerDefaultValuesAndUpdatingValues{
        @Test
        fun `a new player has 0 for all stats`(){
            assertEquals(0,player1!!.gamesPlayed)
            assertEquals(0,player1!!.highestScore)
            assertEquals(0,player1!!.losses)
            assertEquals(0,player1!!.score)
            assertEquals(0,player1!!.wins)
            assertEquals(0,player1!!.totalGuesses)
            assertEquals(0.0,player1!!.averageGuessesPerGame)
        }

        @Test
        fun `accessing players name returns correct name`(){
            assertEquals("Conor",player1!!.name)
            assertEquals("Aoife",player2!!.name)
        }

        @Test
        fun `a player scoring increases their current score`(){
            populatedPlayers!!.correctGuess(player1!!)
            assertEquals(1,player1!!.score)
        }


    }
}