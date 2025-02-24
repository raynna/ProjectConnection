package packets

import java.io.BufferedReader
import java.io.BufferedWriter

class PacketHandler(
    private val input: BufferedReader,
    private val output: BufferedWriter,
    private val dependencies: Map<String, Any>
) {
    private val packets = mapOf(
        Packets.LOGIN_PACKET.id to LoginPacket(),
        Packets.CREATE_ACCOUNT_PACKET.id to CreateAccountPacket()
    )

    fun processRequest(request: String) {
        val parts = request.trim().split(" ").filter { it.isNotEmpty() }
        if (parts.isEmpty()) {
            sendResponse("PACKET_ARGUMENTS_EMPTY")
            return
        }

        val packetId = parts[0].toIntOrNull()
        if (packetId == null) {
            sendResponse("PACKET_ID_MISSING")
        }

        val packet = packets[packetId]
        if (packet == null) {
            sendResponse("PACKET_NOT_IMPLEMENTED")
        }

        if (packet != null) {
            if (!packet.isValidRequest(parts)) {
                sendResponse("INVALID_REQUEST")
                return
            }
        }

        try {
            val response = packet?.processRequest(parts, dependencies)
            if (response != null) {
                sendResponse(response)
            }
        } catch (e: Exception) {
            sendResponse("ERROR: ${e.message}")
        }
    }

    private fun sendResponse(response: String) {
        output.write("$response\n")
        output.flush()
    }
}