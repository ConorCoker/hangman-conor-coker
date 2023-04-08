package models

import com.sun.org.apache.xpath.internal.operations.Bool
import controllers.PlayerAPI
import controllers.WordAPI
import utils.Utils


class Game(private vararg val players: Player, private val word: Word?) {

    private var underscores = ArrayList<Char>()
    private var incorrectGuesses = ArrayList<Char>()
    private var turnsLeft = 6
    private var gameOver = false
    private var gameOverListener: GameOverListener? = null
    private var playersAndScores = HashMap<Player, Int>()

    init {
        setupGame()
    }

    private fun setupGame() {

        if (word != null) {
            for (letter in word.word) {
                underscores.add('_')
            }
        } else gameOver = true; gameOverListener?.onGameOver(-999)

        for (player in players) {
            playersAndScores[player] = 0
        }
    }

    fun printGameScreen() = """
        >|              HANGMAN                |
        >|-------------------------------------|            
        >|${incorrectGuesses}   $playersAndScores                               
        >|${renderMan()}                                    
        >|${underscores.joinToString(separator = " ") { it.toString() }}                   
        >|-------------------------------------|
        >==>> """.trimMargin(">")

    fun makeGuess(guess: Char, playerName: String) {
        if (turnsLeft > 1) {
            if (word!!.word.contains(guess, true)) {
                if (Utils.charListContainsIgnoreCase(underscores,guess)) {
                    for (i in Utils.indexOfCharIgnoreCase(underscores,guess) until underscores.size) {
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
        } else {
            updatePlayerStats(0)
            gameOver = true
            gameOverListener?.onGameOver(0)
        }
    }


    private fun updatePlayerStats(code: Int) {
        playersAndScores.keys.forEach { playerHashMap ->
            players.find { it.name == playerHashMap.name }!!.updateStats(code, playersAndScores[playerHashMap]!!)
        }
    }


    fun isGameOver() = gameOver

    fun isSolved() = !underscores.any { it == '_' }

    fun setGameOverListener(listener: GameOverListener) {
        this.gameOverListener = listener
    }


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


    //testing methods

    fun getUnderscores() = underscores

    fun getGameWord() = word

    fun getIncorrectGuesses() = incorrectGuesses

    fun numOfRemainingGuesses() = turnsLeft

    fun getScores() = playersAndScores


}





