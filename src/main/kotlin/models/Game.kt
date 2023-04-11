package models

import utils.Utils

/**
 * The Game class represents a game of Hangman.
 *
 * @property players A variable-length array of the players in the game.
 * @property word The word to be guessed in the game.
 * @property underscores An ArrayList of characters representing the current state of the word being guessed.
 * @property incorrectGuesses An ArrayList of characters representing the letters that have been guessed incorrectly.
 * @property turnsLeft The number of incorrect guesses remaining before the game is over.
 * @property gameOver A boolean indicating if the game is over.
 * @property gameOverListener A listener to be called when the game is over.
 * @property playersAndScores A HashMap of the players and their scores in the game.
 * @property hint A empty string to be filled with words hint if requested by user.
 * @property definition A empty string to be filled with words definition if requested by user.
 *
 * @constructor Creates a new instance of the Game class.
 *
 * @param players A variable-length array of the players in the game.
 * @param word The word to be guessed in the game.
 */
class Game(private vararg val players: Player, private val word: Word?) {

    private var hint = ""
    private var definition = ""
    private var underscores = ArrayList<Char>()
    private var incorrectGuesses = ArrayList<Char>()
    private var turnsLeft = 6
    private var gameOver = false
    private var gameOverListener: GameOverListener? = null
    private var playersAndScores = HashMap<Player, Int>()

    /**
     * Initializes the game.
     */
    init {
        setupGame()
    }

    /**
     * Sets up the game by initializing the underscores list and the scores of the players.
     */
    private fun setupGame() {

        if (word != null) {
            for (letter in word.word) {
                underscores.add('_')
            }
        } else {
            gameOver = true
            gameOverListener?.onGameOver(-999)
        }

        for (player in players) {
            playersAndScores[player] = 0
        }
    }

    /**
     * Returns a string representation of the game screen.
     *
     * @return A string representation of the game screen.
     */
    fun printGameScreen() = """
        >|              HANGMAN                 |
        >|--------------------------------------|   
        >|Guess '1' for hint, '2' for definition| 
        >|$hint 
        >|$definition                                      
        >|$incorrectGuesses   
        >| ${displayUsersAndScores()}                              
        >|${renderMan()}                                    
        >|${underscores.joinToString(separator = " ") { it.toString() }}                   
        >|--------------------------------------|
        >==>> """.trimMargin(">")

    /**
     * Makes a guess for a given player.
     *
     * @param guess The letter guessed.
     * @param playerName The name of the player guessing.
     */
    fun makeGuess(guess: Char, playerName: String) {
        if (turnsLeft > 1) {
            when (guess) {
                '1' -> hint = word!!.hint
                '2' -> definition = word!!.definition
                else -> {
                    if (word!!.word.contains(guess, true)) {
                        if (Utils.charListContainsIgnoreCase(underscores, guess)) {
                            for (i in Utils.indexOfCharIgnoreCase(underscores, guess) until underscores.size) {
                                if (word.word[i].lowercase() == guess.lowercase() && underscores[i] == '_') {
                                    underscores[i] = guess
                                    playersAndScores[players.find { it.name == playerName }!!] =
                                        playersAndScores[players.find { it.name == playerName }]!! + 1
                                    break
                                }
                            }
                        } else {
                            underscores[word.word.lowercase().indexOf(guess.lowercase())] = guess
                            playersAndScores[players.find { it.name == playerName }!!] =
                                playersAndScores[players.find { it.name == playerName }]!! + 1
                        }
                        if (isSolved()) {
                            updatePlayerStats(1)
                            gameOver = true
                            word.solved = true
                            gameOverListener?.onGameOver(1)
                        }
                    } else {
                        turnsLeft--
                        incorrectGuesses.add(guess)
                    }
                }
            }
        } else {
            updatePlayerStats(0)
            gameOver = true
            gameOverListener?.onGameOver(0)
        }
    }

    /**
     * Updates the stats of players after the game is over.
     *
     * @param code The code indicating if the game was won (1) or lost (0).
     */
    private fun updatePlayerStats(code: Int) {
        playersAndScores.keys.forEach { playerHashMap ->
            players.find { it.name == playerHashMap.name }!!.updateStats(code, playersAndScores[playerHashMap]!!)
        }
    }

    /**
     * Displays the names of players and their respective scores.
     */
    private fun displayUsersAndScores(): String {
        var str = ""
        playersAndScores.forEach { (t, u) ->
            str += "|${t.name} | $u " + "|"
        }
        return str
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    fun isGameOver() = gameOver

    /**
     * Checks if the game has been solved.
     *
     * @return True if the game has been solved, false otherwise.
     */
    fun isSolved() = !underscores.any { it == '_' }

    /**
     * Sets a game over listener.
     *
     * @param listener The listener to be set.
     */
    fun setGameOverListener(listener: GameOverListener) {
        this.gameOverListener = listener
    }

    /**
     * Renders the Hangman on the screen according to the number of remaining turns.
     *
     * @return The String representation of the Hangman.
     */
    private fun renderMan() =
        when (turnsLeft) {
            6 -> """
            _________
            |         |
            |         
            |         
            |        
            |
            """.trimIndent()

            5 -> """
            _________
            |         |
            |         O
            |         
            |        
            |
            """.trimIndent()

            4 -> """
            _________
            |         |
            |         O
            |         |
            |         
            |
            """.trimIndent()

            3 -> """
            _________
            |         |
            |         O
            |        /|
            |        
            |
            """.trimIndent()

            2 -> """
            _________
            |         |
            |         O
            |        /|\
            |        
            |
            """.trimIndent()

            1 -> """
            _________
            |         |
            |         O
            |        /|\
            |        / 
            |
            """.trimIndent()

            else -> ""
        }

    // testing methods

    /**
     * Returns the underscores currently displayed on the screen.
     *
     * @return The underscores.
     */
    fun getUnderscores() = underscores

    /**
     * Returns the word being played.
     *
     * @return The word.
     */
    fun getGameWord() = word

    /**
     * Returns the list of incorrect guesses.
     *
     * @return The incorrect guesses.
     */
    fun getIncorrectGuesses() = incorrectGuesses

    /**
     * Returns the number of remaining turns.
     *
     * @return The number of remaining turns.
     */
    fun numOfRemainingGuesses() = turnsLeft

    /**
     * Returns the current scores of the players.
     *
     * @return The scores.
     */
    fun getScores() = playersAndScores
}
