package controllers

import models.Player

class PlayerAPI {

    private var players = ArrayList<Player>()


    fun addPlayer(player: Player): Boolean {
        return if (players.any { it.name.lowercase() == player.name.lowercase() }) {
            false
        } else {
            players.add(player)
            true
        }
    }


    fun numberOfPlayers() = players.size

    fun removePlayerByIndex(indexToRemove: Int) = players.removeAt(indexToRemove)

    fun correctGuess(player: Player) {
        val player = players.find { it.name == player.name }
        if (player != null) {
            let {
                player!!.score++
                player.totalGuesses++
            }
        }

    }


}