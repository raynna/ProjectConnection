import game.AccountManager
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

object GameServer {

    /**@author Raynna
     * @date 23/02-2025
     * Open the server connection and handle incomming packets from client
     */

    private val accountManager = AccountManager()

    const val PORT = 8080

    private fun start() {
        val serverSocket = ServerSocket(PORT).apply { reuseAddress = true }
        println("[SERVER] Started on port $PORT")

        while (true) {
            val clientSocket = serverSocket.accept()
            println("[SERVER] Client connected: ${clientSocket.inetAddress}")
            handleClient(clientSocket)
        }
    }

    private fun handleClient(clientSocket: Socket) {
        try {
            val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val output = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))

            val request = input.readLine()
            println("[SERVER] Received request: $request")

            val loginEncoder = LoginEncoder(accountManager, input, output)
            loginEncoder.processRequest(request)

            output.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clientSocket.close()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        start()
    }
}
