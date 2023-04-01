import controllers.PlayerAPI
import models.Game
import models.Player
import utils.ScannerInput
import kotlin.system.exitProcess

val players = PlayerAPI()

fun main(args: Array<String>) {
    do {
        when (displayMenu()) {
            'a' -> signIn()
            'b' -> signUp()
            'c' -> listAllPlayers()
            'd' -> listAllSolvedWords()
            'e' -> showLeaderboard()
            'f' -> exitProcess(0)
            else -> displayMenu()
        }
    } while (true)
}

private fun displayMenu(): Char {

    return ScannerInput.readNextChar(
        """
        >|-------------------------------------|
        >| a) Sign in and play                 |
        >| b) Sign up                          |                            
        >| c) List all players                 |            
        >| d) List all solved words            |
        >| e) Show Leaderboard                 |
        >| f) Exit                             |
        >|-------------------------------------|
        >==>> """.trimMargin(">")
    )
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
    val numOfPlayers = ScannerInput.readNextInt("How many players would you like to play with?: ")
    for (i in 0 until numOfPlayers) {
        println("Player ${i+1} login: ")
        var username = ScannerInput.readNextLine("Please enter your username: ")
        var password = ScannerInput.readNextLine("Please enter a password: ")
        while (!players.login(username, password)) {
            System.err.println("Player ${i+1} username or password is incorrect!")
            username = ScannerInput.readNextLine("Please enter your username: ")
            password = ScannerInput.readNextLine("Please enter a password: ")
        }
        println("Player ${i+1} has been logged in")
    }
}