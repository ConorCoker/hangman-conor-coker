package models

/**
 * Class representing a player in the game.
 *
 * @property name The name of the player.
 * @property password The password of the player.
 */
class Player(
    var name: String,
    var password: String,
) {
    var loggedIn = false
    var wins: Int = 0
    var losses: Int = 0
    var gamesPlayed: Int = 0
    var highestScore: Int = 0

    /**
     * Updates the stats of the player after the game is over.
     *
     * @param code The code indicating if the game was won or lost.
     * @param score The score earned by the player in the game.
     */
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

    /**
     * Finds the highest score between the current highest score and the new score.
     *
     * @param score The new score to compare.
     * @return The highest score between the current highest score and the new score.
     */
    private fun findHighScore(score: Int) = if (score > highestScore) {
        score
    } else highestScore
}
