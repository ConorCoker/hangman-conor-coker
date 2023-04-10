package models

class Player(
    var name: String,
    var password: String,
) {
    var loggedIn = false
    var wins: Int = 0
    var losses: Int = 0
    var gamesPlayed: Int = 0
    var highestScore: Int = 0


    fun updateStats(code: Int, score: Int) {
        when (code) {
            0 -> {
                gamesPlayed++
                losses++
                highestScore = findHighScore(score)
            }
            1 -> {
                gamesPlayed++
                wins++
                highestScore = findHighScore(score)
            }
        }
    }

    private fun findHighScore(score: Int) = if (score > highestScore) {
        score
    } else highestScore
}