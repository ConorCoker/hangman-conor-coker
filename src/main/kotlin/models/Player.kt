package models

class Player (
    val name: String,
    var score: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var gamesPlayed: Int = 0,
    var totalGuesses: Int = 0,
    var averageGuessesPerGame: Double = 0.0,
    var highestScore: Int = 0,
) {
}