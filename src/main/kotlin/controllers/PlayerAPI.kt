package controllers

import models.Player
import utils.Utils

/**
 * This class manages an arraylist of Players and provides methods to read or write Player data.
 * @constructor Creates an instance of PlayerAPI.
 */
class PlayerAPI {

    private val players: ArrayList<Player> = ArrayList()

    /**
     * Adds a new player to the list.
     *
     * @param player The player to add.
     * @return True if the player was added, false if the player's name is not unique.
     */
    fun addPlayer(player: Player): Boolean {
        return if (Utils.checkIsUniqueName(player.name, players)) {
            players.add(player)
            true
        } else {
            false
        }
    }

    /**
     * Retrieves a list of all players.
     *
     * @return A string representing the names of all players, or "No players in system!" if there are no players.
     */
    fun listAllPlayers(): String {
        var str = "No players in system!"
        if (players.isNotEmpty()) {
            str = players.joinToString(separator = ", ") { it.name }
        }
        return str
    }

    /**
     * Retrieves a list of logged in players.
     *
     * @return A list of logged in players.
     */
    fun getLoggedInPlayers() = players.filter { it.loggedIn }

    /**
     * Retrieves the number of players.
     *
     * @return The number of players.
     */
    fun numberOfPlayers() = players.size

    /**
     * Retrieves a player by name.
     *
     * @param name The name of the player to retrieve.
     * @return The player with the given name, or null if the player was not found.
     */
    fun getPlayerByName(name: String) = players.find { it.name == name }

    /**
     * Removes a player by index.
     *
     * @param indexToRemove The index of the player to remove.
     */
    fun removePlayerByIndex(indexToRemove: Int) = players.removeAt(indexToRemove)

    /**
     * Logs a player in.
     *
     * @param name The name of the player to log in.
     * @param password The password of the player to log in.
     * @return True if the player was logged in successfully, false otherwise.
     */
    fun login(name: String, password: String): Boolean {
        val player = players.find { it.name == name && it.password == password }
        return if (player != null) {
            player.loggedIn = true
            true
        } else false
    }

    /**
     * Logs a player out.
     *
     * @param name The name of the player to log out.
     */
    fun logout(name: String) {
        val player = players.find { it.name == name }
        if (player != null) {
            player.loggedIn = false
        }
    }

    /**
     * Updates account details for a player.
     *
     * @param player The player whose account details need to be updated.
     * @param name The new name to be updated.
     * @param password The new password to be updated.
     * @return `true` if the account details were updated successfully, `false` otherwise.
     */
    fun updateAccountDetails(player: Player?, name: String, password: String): Boolean {
        return if (player != null) {
            return if (Utils.checkIsObjectPresent(player, players)) {
                if (Utils.checkIsUniqueName(name, players)) {
                    players[players.indexOf(player)].name = name
                    players[players.indexOf(player)].password = password
                    true
                } else false
            } else false
        } else false
    }

    /**
     * Deletes a player account with the specified name and password.
     *
     * @param name The name of the player account to delete.
     * @param password The password of the player account to delete.
     * @return An integer indicating the result of the operation. Possible return values are:
     *  * 1 if the account was successfully deleted.
     *  * 0 if the password was incorrect.
     *  * -1 if the player account could not be found.
     */
    fun deleteAccount(name: String, password: String): Int {
        val player = players.find { it.name.lowercase() == name.lowercase() }
        return if (player != null) {
            if (password == player.password) {
                players.remove(player)
                1 // if success
            } else 0 // if password wrong
        } else -1 // if player don't exist
    }

    /**
     * Retrieves a list of logged in player names.
     *
     * @return A string representing the names of logged in players, or "No logged in players!" if there are no logged in players.
     */
    fun listLoggedInPlayers(): String {
        var str = "No players in system!"
        if (players.isNotEmpty()) {
            str = "No logged in players!"
            val list = players.filter { it.loggedIn }
            if (list.isNotEmpty()) {
                str = list.joinToString(separator = ", ") {
                    it.name
                }
            }
        }
        return str
    }

    /**
     * Retrieves a list of all players.
     *
     * @return A list of all the players.
     */
    fun getPlayers() = players

    /**

    Retrieves a leaderboard of players.
    @return A string representing a leaderboard of players, or "No players in system!" if no players in the system.
    @since 1.0
     */
    fun getLeaderboard(): String {
        return if (players.isNotEmpty()) {
            players.joinToString(separator = "\n") { "|Name: ${it.name}|Games Played: ${it.gamesPlayed}|Wins: ${it.wins}|Losses: ${it.losses}|Highest Score: ${it.highestScore}|" }
        } else "No players in system!"
    }
}

