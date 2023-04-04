package controllers

import models.Player
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


class PlayerAPITest {

    private var player1: Player? = null
    private var player2: Player? = null

    private var emptyPlayers: PlayerAPI? = PlayerAPI()
    private var populatedPlayers: PlayerAPI? = PlayerAPI()

    @BeforeEach
    fun setUp() {

        player1 = Player("Conor", "password")
        player2 = Player("Aoife", "12345")

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
    inner class AddingRemovingPlayers {

        @Test
        fun `adding a player adds that player`() {
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertTrue(populatedPlayers!!.addPlayer(Player("test", "test")))
            assertEquals(3, populatedPlayers!!.numberOfPlayers())

        }

        @Test
        fun `adding a player with the same name as another does not add and returns false (irrespective of case)`() {
            assertEquals(populatedPlayers!!.numberOfPlayers(), 2)
            assertFalse(populatedPlayers!!.addPlayer(Player("CONOR", "test")))
            assertEquals(populatedPlayers!!.numberOfPlayers(), 2)
        }

        @Test
        fun `removing a player removes that player`() {
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertEquals(player2, populatedPlayers!!.removePlayerByIndex(1))
            assertEquals(1, populatedPlayers!!.numberOfPlayers())
        }

        @Test
        fun `adding a player to an empty list adds that player and returns valid size`() {
            assertEquals(0, emptyPlayers!!.numberOfPlayers())
            assertTrue(emptyPlayers!!.addPlayer(Player("test", "test")))
            assertEquals(1, emptyPlayers!!.numberOfPlayers())
        }

    }

    @Nested
    inner class NewPlayerDefaultValuesAndUpdatingValues {
        @Test
        fun `a new player has 0 for all stats`() {
            assertEquals(0, player1!!.gamesPlayed)
            assertEquals(0, player1!!.highestScore)
            assertEquals(0, player1!!.losses)
            assertEquals(0, player1!!.wins)
            assertEquals(0.0, player1!!.averageGuessesPerGame)
        }

        @Test
        fun `accessing players name returns correct name`() {
            assertEquals("Conor", player1!!.name)
            assertEquals("Aoife", player2!!.name)
        }
    }
    @Nested
    inner class LoggingIn{

        @Test
        fun `logging in with correct name and password returns true`(){
            assertTrue(populatedPlayers!!.login("Conor","password"))
        }

        @Test
        fun `logging in with incorrect name and password returns false`(){
            assertFalse(populatedPlayers!!.login("Conor","login"))
        }
    }

    @Nested
    inner class GettingAllPlayersReturnsAllPlayers{

        @Test
        fun `getting all players returns all players`(){
            assertEquals(2,populatedPlayers!!.numberOfPlayers())
            assertEquals(2,populatedPlayers!!.getPlayers().size)
            assertTrue(populatedPlayers!!.getPlayers().contains(player1))
            assertTrue(populatedPlayers!!.getPlayers().contains(player2))
        }

        @Test
        fun `getting player by name returns that player or null if invalid name`(){
            assertEquals(player1,populatedPlayers!!.getPlayerByName("Conor"))
            assertEquals(player2,populatedPlayers!!.getPlayerByName("Aoife"))
            assertNull(populatedPlayers!!.getPlayerByName("Invalid Name"))
        }

        @Test
        fun `increasing a players score increases that players score`(){
            assertEquals(0,player1!!.currentScore)
            assertEquals(1,populatedPlayers!!.increasePlayerScore("Conor"))
            assertEquals(1,player1!!.currentScore)
        }

    }
}