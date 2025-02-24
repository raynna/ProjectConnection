import game.AccountManager
import packets.PacketHandler
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

object GameServer {

    private val accountManager = AccountManager()

    private const val PORT = 8080

    private fun start() {
        val serverSocket = ServerSocket(PORT).apply { reuseAddress = true }
        println("[SERVER] Started on port $PORT")

        while (true) {
            val clientSocket = serverSocket.accept()
            println("[SERVER] Client connected: ${clientSocket.inetAddress}")
            Thread { processPackets(clientSocket) }.start()
        }
    }

    private fun processPackets(clientSocket: Socket) {
        try {
            val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val output = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))

            val dependencies = mapOf(
                "accountManager" to accountManager
            )

            val packetHandler = PacketHandler(input, output, dependencies)

            while (true) {
                val request = input.readLine() ?: break
                println("[SERVER] Received request: $request")

                packetHandler.processRequest(request)
                output.flush()
            }
        } catch (e: Exception) {
            println("[SERVER] Error processing client request: ${e.message}")
            e.printStackTrace()
        } finally {
            clientSocket.close()
            println("[SERVER] Client disconnected: ${clientSocket.inetAddress}")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        start()
    }
}