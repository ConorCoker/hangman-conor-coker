package controllers

import models.Player
import utils.Utils

class PlayerAPI {

    private val players: ArrayList<Player> = ArrayList()

    fun addPlayer(player: Player): Boolean {
        return if (Utils.checkIsUniqueName(player.name, players)) {
            players.add(player)
            true
        } else {
            false
        }
    }

    fun getPlayers() = players

    fun getLoggedInPlayers() = players.filter { it.loggedIn }

    fun numberOfPlayers() = players.size

    fun getPlayerByName(name: String) = players.find { it.name == name }

    fun removePlayerByIndex(indexToRemove: Int) = players.removeAt(indexToRemove)

    fun login(name: String, password: String): Boolean {
        val player = players.find { it.name == name && it.password == password }
        return if (player != null) {
            player.loggedIn = true
            true
        } else false
    }

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

    fun listAllPlayers(): String {
        var str = "No players in system!"
        if (players.isNotEmpty()) {
            str = players.joinToString(separator = ", ") { it.name }
        }
        return str
    }

    fun getLeaderboard(): String {
        return if (players.isNotEmpty()) {
            players.joinToString(separator = "\n") { "|Name: ${it.name}|Games Played: ${it.gamesPlayed}|Wins: ${it.wins}|Losses: ${it.losses}|Highest Score: ${it.highestScore}|" }
        } else "No players in system!"
    }

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

    fun deleteAccount(name: String, password: String): Int {
        val player = players.find { it.name.lowercase() == name.lowercase() }
        return if (player != null) {
            if (password == player.password) {
                players.remove(player)
                1 // if success
            } else 0 // if password wrong
        } else -1 // if player dont exist
    }
}
