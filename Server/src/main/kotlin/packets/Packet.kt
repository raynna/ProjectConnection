package packets

abstract class Packet {
    abstract val packet: Packets
    abstract val packetId: Int
    abstract val size: Int
    abstract val name: String

    open fun isValidRequest(parts: List<String>): Boolean {
        println("[isValidRequest] Checking parts: $parts")

        if (parts.isEmpty()) {
            println("[isValidRequest] Invalid request: no parts received")
            return false
        }

        val packetIdFromClient = parts[0].toIntOrNull()
        if (packetIdFromClient == null || packetIdFromClient != packetId) {
            println("[isValidRequest] Invalid packetId: '${parts[0]}' (expected: $packetId)")
            return false
        }

        return true // Allow further processing
    }




    abstract fun processRequest(parts: List<String>, dependencies: Map<String, Any>): String
}