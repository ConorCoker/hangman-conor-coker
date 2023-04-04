package controllers

import models.Player

class PlayerAPI {

    private val players: ArrayList<Player> = ArrayList()

    fun addPlayer(player: Player): Boolean {
        return if (players.any { it.name.lowercase() == player.name.lowercase() }) {
            false
        } else {
            players.add(player)
            true
        }
    }

    fun getPlayers() = players

    fun numberOfPlayers() = players.size

    fun getPlayerByName(name: String) = players.find { it.name == name }

    fun increasePlayerScore(name: String): Int? {
        val player = getPlayerByName(name)
        return if (player != null) {
            player.currentScore++
            player.currentScore
        } else {
            null
        }
    }

    fun removePlayerByIndex(indexToRemove: Int) = players.removeAt(indexToRemove)

    fun login(name: String, password: String): Boolean {
        val player = players.find { it.name == name && it.password == password }
        return if (player!=null){
            player.loggedIn = true
            true
        }
        else false
    }

    fun listLoggedInPlayers():String {
        return players.filter { it.loggedIn }.joinToString(separator = ", ") {
            it.name
        }
    }


}