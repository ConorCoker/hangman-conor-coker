package models


class Game(private val gameWord: Word) {

    private var turnsLeft = gameWord.maxGuesses
    private var underscores = ArrayList<Char>()
    var gameOver = false


    init {
        setupGame()

    }

    private fun setupGame() {

        for (letter in gameWord.word) {
            underscores.add('_')
        }


    }

    fun printGameScreen() = """
        >|              HANGMAN                |
        >|-------------------------------------|            
        >|                                    
        >|${renderMan()}                                    
        >|${underscores.joinToString(separator = "") { it.toString() }}                   
        >|-------------------------------------|
        >==>> """.trimMargin(">")

    fun makeGuess(guess: Char,player:Player){
        if (turnsLeft > 0) {
            if (gameWord.word.contains(guess)) {
                player.currentScore++
                underscores[gameWord.word.indexOf(guess)] = guess
            } else turnsLeft--
        } else gameOver = true

    }


    private fun renderMan() =
        when ((gameWord.maxGuesses - turnsLeft) * 100 / gameWord.maxGuesses) {
            in 0..24 -> """
            _________
            |         |
            |         
            |         
            |        
            |
        """.trimIndent()

            in 25..49 -> """
            _________
            |         |
            |         O
            |         |
            |        
            |
        """.trimIndent()

            in 50..74 -> """
            _________
            |         |
            |         O
            |        /|\
            |         
            |
        """.trimIndent()

            in 75..99 -> """
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
            |
        """.trimIndent()
        }
}





