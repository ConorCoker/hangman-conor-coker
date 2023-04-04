package models

import controllers.PlayerAPI
import controllers.WordAPI


class Game(private val words: WordAPI, private val players: PlayerAPI, difficulty: Int) {

    private var underscores = ArrayList<Char>()
    private var turnsLeft = 6
    private var gameOver = false
    private var gameOverListener: GameOverListener? = null
    private lateinit var word: Word
    private var playersInGame = ArrayList<Player>()
    private var incorrectGuesses = ArrayList<Char>()

    init {
        findPlayersPlaying()
        getWord(difficulty)
        setupGame()
    }

    private fun setupGame() {

        for (letter in word.word) {
            underscores.add('_')
        }
    }

    private fun getWord(difficulty: Int) {
        word = words.getRandomWord(difficulty)
    }

    private fun findPlayersPlaying() {
        players.getPlayers().forEach {
            it.loggedIn
            playersInGame.add(it)
        }
    }

    fun getPlayersPlaying() = playersInGame

    fun isGameOver() = gameOver

    fun printGameScreen() = """
        >|              HANGMAN                |
        >|-------------------------------------|            
        >|${incorrectGuesses}                                  
        >|${renderMan()}                                    
        >|${underscores.joinToString(separator = " ") { it.toString() }}                   
        >|-------------------------------------|
        >==>> """.trimMargin(">")

    fun makeGuess(guess: Char, playerName: String) {
        if (turnsLeft > 1) {
            if (word.word.contains(guess, true)) {
                if (underscores.contains(guess)) {
                    for (i in underscores.indexOf(guess) until underscores.size) {
                        if (word.word[i] == guess && underscores[i] == '_') {
                            underscores[i] = guess
                            break
                        }
                    }
                } else {
                    underscores[word.word.indexOf(guess)] = guess
                }
                players.increasePlayerScore(playerName)
            } else {
                turnsLeft--
                incorrectGuesses.add(guess)
            }
        } else {
            gameOver = true
            gameOverListener?.onGameOver()
        }
    }

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

            else -> """
            _________
            |         |
            |         O
            |        /|\
            |        / \
            |     GAME OVER
        """.trimIndent()
        }

    //testing methods

    fun getUnderscores() = underscores

    fun getCurrentScore(name: String) = players.getPlayerByName(name)!!.currentScore

    fun getGameWord() = word

    fun getIncorrectGuesses() = incorrectGuesses

}





