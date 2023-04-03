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

    fun getPlayersPlaying() = players.filter { it.loggedIn }

    fun numberOfPlayers() = players.size

    fun removePlayerByIndex(indexToRemove: Int) = players.removeAt(indexToRemove)

    fun login(name: String, password: String): Boolean {
        val player = players.find { it.name == name && it.password == password }
        return if (player != null) {
            player.loggedIn = true
            true
        } else false
    }

}