import controllers.PlayerAPI
import controllers.WordAPI
import models.Game
import models.Player
import utils.ScannerInput
import kotlin.system.exitProcess

val players = PlayerAPI()
val words = WordAPI()

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

fun addWord() {

}

private fun displayMenu(): Char {

    return ScannerInput.readNextChar(
        """
        >|Players in this session :            |
        >|${players.getPlayersPlaying().size}                                  |
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

fun play() {
    var word =
        words
            .getRandomWord(ScannerInput.readNextInt("What difficulty would you like the word to be? 1 (easy) - 5 (hard)"))
    if (word == null) {
        do {
            word =
                words
                    .getRandomWord(ScannerInput.readNextInt("There is no words of that difficulty! Enter a new difficulty or add a word of that difficulty! : "))
        } while (word == null)
    }
    val game = Game(word)
    do {
        for (loggedInPlayer in players.getPlayersPlaying()) {
            println(game.printGameScreen())
            game.makeGuess(ScannerInput.readNextChar("${loggedInPlayer.name} make your guess: "),loggedInPlayer)
        }
    }while (!game.gameOver)

}

fun showLeaderboard() {
    TODO("Not yet implemented")
}

fun listAllSolvedWords() {
    TODO("Not yet implemented")
}

fun listAllPlayers() {
    TODO("Not yet implemented")
}

fun signUp() {
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

fun signIn() {
    var username = ScannerInput.readNextLine("Please enter your username: ")
    var password = ScannerInput.readNextLine("Please enter a password: ")
    if (players.login(username, password)) {
        println("Hi $username you have been logged in!")
    } else println("Error username of password was incorrect!")
}

