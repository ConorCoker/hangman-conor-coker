import utils.ScannerInput
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    when (displayMenu()) {
        'a' -> signIn()
        'b' -> signUp()
        'c' -> play()
        'd' -> listAllPlayers()
        'e' -> listAllSolvedWords()
        'f' -> showLeaderboard()
        'g' -> exitProcess(0)
        else -> displayMenu()
    }
}

private fun displayMenu(): Char {

    return ScannerInput.readNextChar(
        """
        >|-------------------------------------|
        >| a) Sign in                          |
        >| b) Sign up                          |
        >| c) Play                             |
        >| d) List all players                 |            
        >| e) List all solved words            |
        >| f) Show Leaderboard                 |
        >| g) Exit                             |
        >|-------------------------------------|
        >==>> """.trimMargin(">")
    )
}

fun play() {
    TODO("Not yet implemented")
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
    TODO("Not yet implemented")
}

fun signIn() {
    TODO("Not yet implemented")
}