package models

/**

An interface for listeners to be notified when the game is over.
 */
interface GameOverListener {

    /**

     Called when the game is over.
     @param code An integer code that indicates the game's outcome. 1 for a win, 0 for a loss, and -999 for an error.
     */
    fun onGameOver(code: Int)
}
