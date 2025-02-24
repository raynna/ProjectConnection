import client.ClientPacketHandler
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

object GameClient {

    private const val SERVER_ADDRESS = "localhost"
    private const val SERVER_PORT = 8080

    @JvmStatic
    fun main(args: Array<String>) {
        val loginScreen = LoginScreen()
        loginScreen.setOnLoginListener { username, password ->
            handleLogin(username, password, loginScreen)
        }
        loginScreen.show()
    }

    private fun handleLogin(username: String, password: String, loginScreen: LoginScreen) {
        println("[handleLogin] username: $username password $password")
        val loginResult = loginToServer(username, password)
        when (loginResult.first) {
            true -> {
                GameScreen().show()
                loginScreen.close()
            }
            false -> {
                loginScreen.showError(loginResult.second)
            }
        }
    }

    private fun loginToServer(username: String, password: String): Pair<Boolean, String> {
        return try {
            val socket = Socket(SERVER_ADDRESS, SERVER_PORT)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val output = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

            val packetHandler = ClientPacketHandler(input, output)
            val result = packetHandler.sendLoginRequest(username, password)
            socket.close()
            println("[loginToServer] username: $username password: $password")
            println("[loginToServer]: loginToServer result: $result")
            result
        } catch (e: Exception) {
            Pair(false, "Error connecting to server")
        }
    }

    private fun createAccount(username: String, password: String, email: String): Pair<Boolean, String> {
        return try {
            val socket = Socket(SERVER_ADDRESS, SERVER_PORT)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val output = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

            val packetHandler = ClientPacketHandler(input, output)
            val result = packetHandler.sendCreateAccountRequest(username, password, email)
            socket.close()

            result
        } catch (e: Exception) {
            Pair(false, "Error connecting to server")
        }
    }
}