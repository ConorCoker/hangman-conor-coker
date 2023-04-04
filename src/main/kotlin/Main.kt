import controllers.PlayerAPI
import controllers.WordAPI
import models.Game
import models.GameOverListener
import models.Player
import utils.ScannerInput
import kotlin.system.exitProcess

val players = PlayerAPI()
val words = WordAPI()
val loggedIn = ArrayList<String>()

fun main(args: Array<String>) {
    do {
        when (displayMenu()) {
            'a' -> signIn()
            'b' -> play()
            'c' -> signUp()
            'd' -> addWord()
            'e' -> listAllSolvedWords()
            'f' -> listAllPlayers()
            'g' -> showLeaderboard()
            'h' -> exitProcess(0)
            else -> displayMenu()
        }
    } while (true)
}

private fun addWord() {

}

private fun displayMenu(): Char {

    return ScannerInput.readNextChar(
        """
        >|Players in this session :            
        >|${players.listLoggedInPlayers()}                                     
        >|-------------------------------------|
        >| a) Sign in                          |
        >| b) Play                             |
        >| c) Sign up                          |
        >| d) Add a word                       |                            
        >| e) List all players                 |            
        >| f) List all solved words            |
        >| g) Show Leaderboard                 |
        >| h) Exit                             |
        >|-------------------------------------|
        >==>> """.trimMargin(">")
    )
}

private fun play() {
    val game = Game(words, players, ScannerInput.readNextInt("Please enter a difficulty (1-5): "))
    do {
        for (player in game.getPlayersPlaying()) {
            println(game.printGameScreen())
            game.makeGuess(ScannerInput.readNextChar("${player.name} make your guess: "), player.name)
        }
    } while (!game.isGameOver())

    game.setGameOverListener(object : GameOverListener {
        override fun onGameOver() {
            println("Game over")

        }
    })
}


private fun showLeaderboard() {
    TODO("Not yet implemented")
}

private fun listAllSolvedWords() {
    TODO("Not yet implemented")
}

private fun listAllPlayers() {
    TODO("Not yet implemented")
}

private fun signUp() {
    if (players.addPlayer(
            Player(
                ScannerInput.readNextLine("Please enter a username: "),
                ScannerInput.readNextLine("Please enter a password: ")
            )
        )
    ) {
        println("You have successfully signed up!")
    } else System.err.println("That username is already taken!")

}

private fun signIn() {
    val username = ScannerInput.readNextLine("Please enter your username: ")
    if (players.login(username, ScannerInput.readNextLine("Please enter your password: "))) {
        println("Hi $username you have been logged in!")
        loggedIn.add(username)
    } else System.err.println("Error username of password was incorrect!")
}

