package models

class Player(
    val name: String,
    val password: String,
) {
    var loggedIn = false
    var wins: Int = 0
    var losses: Int = 0
    var gamesPlayed: Int = 0
    var averageGuessesPerGame: Double = 0.0
    var highestScore: Int = 0
    var currentScore = 0
}