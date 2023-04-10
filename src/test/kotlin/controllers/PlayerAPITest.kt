package controllers

import models.Game
import models.Player
import models.Word
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
    private var game: Game? = null

    private var emptyPlayers: PlayerAPI? = PlayerAPI()
    private var populatedPlayers: PlayerAPI? = PlayerAPI()

    @BeforeEach
    fun setUp() {

        player1 = Player("Conor", "password")
        player2 = Player("Aoife", "12345")

        populatedPlayers!!.addPlayer(player1!!)
        populatedPlayers!!.addPlayer(player2!!)

        game = Game(player1!!, word = Word("Test", "Test", 1, "Test"))
    }

    @AfterEach
    fun tearDown() {
        player1 = null
        player2 = null

        emptyPlayers = null
        populatedPlayers = null

        game = null
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
        }

        @Test
        fun `accessing players name returns correct name`() {
            assertEquals("Conor", player1!!.name)
            assertEquals("Aoife", player2!!.name)
        }
    }
    @Nested
    inner class LoggingIn {

        @Test
        fun `logging in with correct name and password returns true`() {
            assertTrue(populatedPlayers!!.login("Conor", "password"))
        }

        @Test
        fun `logging in with incorrect name and password returns false`() {
            assertFalse(populatedPlayers!!.login("Conor", "login"))
        }
    }

    @Nested
    inner class GettingAllPlayersReturnsAllPlayers {

        @Test
        fun `getting all players returns all players`() {
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertEquals(2, populatedPlayers!!.getPlayers().size)
            assertTrue(populatedPlayers!!.getPlayers().contains(player1))
            assertTrue(populatedPlayers!!.getPlayers().contains(player2))
        }

        @Test
        fun `getting player by name returns that player or null if invalid name`() {
            assertEquals(player1, populatedPlayers!!.getPlayerByName("Conor"))
            assertEquals(player2, populatedPlayers!!.getPlayerByName("Aoife"))
            assertNull(populatedPlayers!!.getPlayerByName("Invalid Name"))
        }
    }

    @Nested
    inner class ListingPlayers {

        @Test
        fun `list all players lists all players`() {
            assertTrue(populatedPlayers!!.listAllPlayers().contains(player1!!.name))
            assertTrue(populatedPlayers!!.listAllPlayers().contains(player2!!.name))
        }

        @Test
        fun `list all players returns no players when there is no players`() {
            assertTrue(emptyPlayers!!.getPlayers().isEmpty())
            assertTrue(emptyPlayers!!.listAllPlayers().contains("No players in system!"))
        }

        @Test
        fun `list all logged in players lists all players that are logged in`() {
            player1!!.loggedIn = true
            assertTrue(populatedPlayers!!.listLoggedInPlayers().contains(player1!!.name))
            assertTrue(!populatedPlayers!!.listLoggedInPlayers().contains(player2!!.name))
        }
        @Test
        fun `list all logged in players returns no logged in players when there is no players logged in`() {
            assertEquals("No logged in players!", populatedPlayers!!.listLoggedInPlayers())
            assertTrue(emptyPlayers!!.listLoggedInPlayers().contains("No players in system!"))
        }

        @Test
        fun `get leaderboard returns leaderboard with content when players are present`() {
            assertTrue(populatedPlayers!!.getPlayers().isNotEmpty())
            assertTrue(populatedPlayers!!.getLeaderboard().contains("|Name: Conor|Games Played: 0|Wins: 0|Losses: 0|Highest Score: 0|"))
            assertTrue(populatedPlayers!!.getLeaderboard().contains("|Name: Aoife|Games Played: 0|Wins: 0|Losses: 0|Highest Score: 0|"))
            game!!.makeGuess('e', "Conor")
            game!!.makeGuess('t', "Conor")
            game!!.makeGuess('s', "Conor")
            game!!.makeGuess('t', "Conor")
            assertTrue(populatedPlayers!!.getLeaderboard().contains("|Name: Conor|Games Played: 1|Wins: 1|Losses: 0|Highest Score: 4|"))
            assertTrue(populatedPlayers!!.getLeaderboard().contains("|Name: Aoife|Games Played: 0|Wins: 0|Losses: 0|Highest Score: 0|"))
        }

        @Test
        fun `get leaderboard returns no players in system message when no players active`() {
            assertTrue(emptyPlayers!!.getPlayers().isEmpty())
            assertEquals("No players in system!", emptyPlayers!!.getLeaderboard())
        }
    }

    @Nested
    inner class UpdatingPlayers {

        @Test
        fun`updating a player will update the player correctly`() {
            assertEquals("Conor", player1!!.name)
            assertEquals("password", player1!!.password)
            assertTrue(populatedPlayers!!.updateAccountDetails(player1!!, "updated name", "updated password"))
            assertEquals("updated password", player1!!.password)
            assertEquals("updated name", player1!!.name)
        }

        @Test
        fun`trying to update a player not in list returns false`() {
            assertFalse(populatedPlayers!!.updateAccountDetails(Player("name", "password"), "new name", "new password"))
        }

        @Test
        fun`updating a player with a already present name returns false and does not update player`() {
            assertEquals("Aoife", player2!!.name)
            assertFalse(populatedPlayers!!.updateAccountDetails(player2!!, "cOnOr", "password"))
            assertEquals("Aoife", player2!!.name)
            assertEquals("12345", player2!!.password)
        }
    }

    @Nested
    inner class DeletingPlayers {

        @Test
        fun`deleting a player deletes that player and returns 1`() {
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertEquals(1, populatedPlayers!!.deleteAccount("Conor", "password"))
            assertEquals(1, populatedPlayers!!.numberOfPlayers())
            assertFalse(populatedPlayers!!.listAllPlayers().contains("Conor"))
        }

        @Test
        fun `deleting a valid player but invalid password does not delete player and returns 0`() {
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertEquals(0, populatedPlayers!!.deleteAccount("Aoife", "incorrect"))
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertTrue(populatedPlayers!!.listAllPlayers().contains("Aoife"))
        }

        @Test
        fun `deleting a invalid player returns -1`() {
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
            assertEquals(-1, populatedPlayers!!.deleteAccount("invalid", "account"))
            assertEquals(2, populatedPlayers!!.numberOfPlayers())
        }
    }
}
