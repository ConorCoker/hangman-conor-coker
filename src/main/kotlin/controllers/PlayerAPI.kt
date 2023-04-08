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

    fun getLoggedInPlayers() = players.filter { it.loggedIn }

    fun numberOfPlayers() = players.size

    fun getPlayerByName(name: String) = players.find { it.name == name }

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
        var str = "No players in system!"
        if (players.isNotEmpty()){
            str = "No logged in players!"
            val list = players.filter { it.loggedIn }
            if (list.isNotEmpty()){
                str = list.joinToString(separator = ", ") {
                    it.name
                }
            }
        }
       return str
    }

    fun listAllPlayers():String{
        var str = "No players in system!"
        if (players.isNotEmpty()){
            str = players.joinToString(separator = ", ") { it.name  }
        }
        return str
    }

    fun getLeaderboard() = players.joinToString (separator = "\n"){"|Name: ${it.name}|Games Played: ${it.gamesPlayed}|Wins: ${it.wins}|Losses: ${it.losses}|Highest Score: ${it.highestScore}|"}


}