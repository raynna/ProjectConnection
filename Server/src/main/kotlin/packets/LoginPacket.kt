package packets

import game.AccountManager

class LoginPacket : Packet() {
    override val packet: Packets = Packets.LOGIN_PACKET
    override val packetId: Int = Packets.LOGIN_PACKET.id
    override val size: Int = 3 // LOGIN <username> <password>
    override val name: String = "LOGIN_PACKET"

    override fun processRequest(parts: List<String>, dependencies: Map<String, Any>): String {
        // Ensure the request has the correct number of parts
        println("[LoginPacket] size = $size, expected 3?")
        if (parts.size < size) {
            return "INVALID_REQUEST"
        }

        val username = parts.getOrNull(1) ?: "" // Avoids crash if username is missing
        val password = parts.getOrNull(2) ?: "" // Avoids crash if password is missing

        val accountManager = dependencies["accountManager"] as? AccountManager
            ?: throw IllegalStateException("AccountManager dependency missing.")

        return when {
            username.isEmpty() -> "USERNAME_REQUIRED"
            password.isEmpty() -> "PASSWORD_REQUIRED"
            accountManager.loadAccount(username) == null -> "ACCOUNT_NOT_FOUND"
            accountManager.loadAccount(username)?.password != password -> "WRONG_PASSWORD"
            else -> "LOGIN_SUCCESS"
        }
    }
}