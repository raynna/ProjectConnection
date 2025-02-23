import game.AccountManager
import java.io.BufferedReader
import java.io.BufferedWriter

class LoginEncoder(
    /**@author Raynna
     * @date 23/02-2025
     * Handle the incomming packets from client to server
     * Examples: Login Requests and CreateAccount Requests
     */

    private val accountManager: AccountManager,
    private val input: BufferedReader,
    private val output: BufferedWriter
) {

    fun processRequest(request: String) {
        val parts = request.split(" ")
        when (parts[0]) {
            "CREATE_ACCOUNT" -> {
                val username = parts[1]
                val password = parts[2]
                val email = parts[3]
                val response = when {
                    username.isEmpty() || password.isEmpty() || email.isEmpty() -> "FIELDS_NOT_FILLED"
                    accountManager.loadAccount(username) != null -> "ACCOUNT_EXISTS"
                    else -> {
                        accountManager.createAccount(username, password, email)
                        "ACCOUNT_CREATED"
                    }
                }
                output.write("$response\n")
            }
            "LOGIN" -> {
                val username = parts[1]
                val password = parts[2]
                val response = when {
                    username.isEmpty() -> "USERNAME_REQUIRED"
                    password.isEmpty() -> "PASSWORD_REQUIRED"
                    accountManager.loadAccount(username) == null -> "ACCOUNT_NOT_FOUND"
                    accountManager.loadAccount(username)?.password != password -> "WRONG_PASSWORD"
                    else -> "LOGIN_SUCCESS"
                }
                    output.write("$response\n")
            }
        }
    }
}
