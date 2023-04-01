package models

import controllers.PlayerAPI

class Game(private val gameWord: Word) {

    var turnsLeft = gameWord.maxGuesses
    var gameWordAsCharArray = gameWord.word.toCharArray()
    val playersPlaying = PlayerAPI().getPlayersPlaying()


}