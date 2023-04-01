package controllers

import models.Player

class PlayerAPI {

    private var players = ArrayList<Player>()

    private var loggedInPlayers = ArrayList<Player>()

    fun addPlayer(player: Player): Boolean {
        return if (players.any { it.name.lowercase() == player.name.lowercase() }) {
            false
        } else {
            players.add(player)
            true
        }
    }

    private fun getPlayersByName(name: String): Player? {
        players.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun getPlayers() = players

    fun getPlayersPlaying() = loggedInPlayers

    fun numberOfPlayers() = players.size

    fun removePlayerByIndex(indexToRemove: Int) = players.removeAt(indexToRemove)

    fun login(name: String, password: String): Boolean {
        val player = players.find { it.name == name }
        return if (player != null) {
            loggedInPlayers.add(getPlayersByName(name)!!)
            player.password == password
        } else false
    }


//    fun correctGuess(player: Player) {
//        val player = players.find { it.name == player.name }
//        if (player != null) {
//            let {
//                player!!.score++
//                player.totalGuesses++
//            }
//        }
//
//    }


}