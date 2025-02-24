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
            println("[processRequest] INVALID_REQUEST parts is empty")
            sendResponse("INVALID_REQUEST")
            return
        }

        val packetId = parts[0].toIntOrNull()
        if (packetId == null) {
            println("[processRequest] INVALID_PACKET_ID ${parts[0]}")
            sendResponse("INVALID_PACKET_ID")
            return
        }

        val packet = packets[packetId]
        if (packet == null) {
            println("[processRequest] PACKET_NOT_IMPLEMENTED $packetId")
            sendResponse("PACKET_NOT_IMPLEMENTED")
            return
        }

        if (!packet.isValidRequest(parts)) {
            println("[processRequest] invalid parts $parts")
            sendResponse("INVALID_REQUEST")
            return
        }

        try {
            println("[processRequest] PacketId: ${packet.packetId} PacketName: ${packet.name} Parts: $parts")
            val response = packet.processRequest(parts, dependencies)
            sendResponse(response)
        } catch (e: Exception) {
            sendResponse("ERROR: ${e.message}")
        }
    }

    private fun sendResponse(response: String) {
        output.write("$response\n")
        output.flush()
    }
}