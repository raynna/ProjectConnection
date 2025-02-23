import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

object GameClient {

    /**@author Raynna
     * @date 23/02-2025
     * Launching the program with a loginscreen, with button to create accounts.
     */

    private const val SERVER_ADDRESS = "localhost"
    private const val SERVER_PORT = 8080

    private val loginDecoder = LoginDecoder()

    @JvmStatic
    fun main(args: Array<String>) {
        val loginScreen = LoginScreen()
        loginScreen.setOnLoginListener { username, password ->
            handleLogin(username, password, loginScreen)
        }
        loginScreen.show()
    }

    private fun handleLogin(username: String, password: String, loginScreen: LoginScreen) {
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
            val response = sendLoginRequest(username, password)
            loginDecoder.decodeLoginResponse(response, isLogin = true)
        } catch (e: Exception) {
            Pair(false, "Error connecting to server")
        }
    }

    fun createAccount(username: String, password: String, email: String): Pair<Boolean, String> {
        return try {
            val response = sendCreateAccountRequest(username, password, email)
            loginDecoder.decodeLoginResponse(response, isLogin = false)
        } catch (e: Exception) {
            Pair(false, "Error connecting to server")
        }
    }


    private fun sendLoginRequest(username: String, password: String): String {
        return try {
            val socket = Socket(SERVER_ADDRESS, SERVER_PORT)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val output = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

            output.write("LOGIN $username $password\n")
            output.flush()

            val response = input.readLine()
            socket.close()

            response
        } catch (e: Exception) {
            "ERROR"
        }
    }

    private fun sendCreateAccountRequest(username: String, password: String, email: String): String {
        return try {
            val socket = Socket(SERVER_ADDRESS, SERVER_PORT)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val output = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

            output.write("CREATE_ACCOUNT $username $password $email\n")
            output.flush()

            val response = input.readLine()
            socket.close()
            response
        } catch (e: Exception) {
            "ERROR"
        }
    }

}
