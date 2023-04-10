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
            'd' -> println(players.listAllPlayers())
            'e' -> println(words.listAllSolvedWords())
            'f' -> println(players.getLeaderboard())
            'g' -> updateAccount()
            'h' -> deleteAccount()
            'i' -> exitProcess(0)
            else -> displayMenu()
        }
    } while (true)
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
        >| d) List all players                 |            
        >| e) List all solved words            |
        >| f) Show Leaderboard                 |
        >| g) Update account details           |
        >| h) Delete account                   |    
        >| i) Exit                             |
        >|-------------------------------------|
        >==>> """.trimMargin(">")
    )
}

private fun signIn() {
    val username = ScannerInput.readNextLine("Please enter your username: ")
    if (players.login(username, ScannerInput.readNextLine("Please enter your password: "))) {
        println("Hi $username you have been logged in!")
        loggedIn.add(username)
    } else System.err.println("Error username of password was incorrect!")
}

private fun play() {
    words.loadWords()
    val game = Game(
        *players.getLoggedInPlayers().toTypedArray(),
        word = words.getRandomWord(ScannerInput.readNextInt("Please enter a difficulty (1-5): "))
    )
    game.setGameOverListener(object : GameOverListener {
        override fun onGameOver(code: Int) {
            when (code) {
                1 ->
                    println(
                        """ ___________________________________
|                                   |
|                                   |
|              \O/                  |
|               |                   |
|              / \                  |
|                                   |
|        CONGRATULATIONS!           |
|                                   |
|   You have successfully guessed   |
|   the word "${game.getGameWord()!!.word}"!"""
                    )


                0 -> {
                    System.err.println(
                        """
            _________
            |         |
            |         O
            |        /|\
            |        / \ 
            |GAME OVER.. The word was ${game.getGameWord()!!.word}
        """.trimIndent()
                    )
                }

                else -> System.err.println("ERROR no words are in system!!!")
            }
        }
    })

    do {
        if (game.isGameOver()) {
            break
        }
        for (player in players.getLoggedInPlayers()) {
            println(game.printGameScreen())
            game.makeGuess(ScannerInput.readNextChar("${player.name} make your guess: "), player.name)
        }
    } while (!game.isGameOver())
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

private fun updateAccount() {
    when (players.updateAccountDetails(
        players.getPlayerByName(ScannerInput.readNextLine("Enter your current name: ")),
        ScannerInput.readNextLine("Enter your new name: "),
        ScannerInput.readNextLine("Enter your new password: ")
    )) {
        true -> println("Your account was updated successfully")
        false -> println("Something went wrong! Please check your username and password again!")
    }
}

private fun deleteAccount() {
    when (players.deleteAccount(
        ScannerInput.readNextLine("Enter the name of your account that you want to delete: "),
        ScannerInput.readNextLine("Enter your password: ")
    )) {
        1 -> println("Your account was deleted!")
        0 -> System.err.println("Your password was incorrect!")
        -1 -> System.err.println("We could not find that account!")
    }
}



