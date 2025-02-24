package packets

import game.AccountManager

class LoginPacket : Packet() {
    override val packet: Packets = Packets.LOGIN_PACKET
    override val packetId: Int = Packets.LOGIN_PACKET.id
    override val size: Int = 3 // LOGIN <username> <password>
    override val name: String = "LOGIN_PACKET"

    override fun processRequest(parts: List<String>, dependencies: Map<String, Any>): String {
        println("[LoginPacket] size = $size, expected 3?")
        val username = parts.getOrNull(1) ?: ""
        val password = parts.getOrNull(2) ?: ""
        if (username.isEmpty()) {
            return "USERNAME_REQUIRED"
        }
        if (password.isEmpty()) {
            return "PASSWORD_REQUIRED"
        }
        if (parts.size < size) {
            return "INVALID_PACKETSIZE"
        }


        val accountManager = dependencies["accountManager"] as? AccountManager
            ?: throw IllegalStateException("AccountManager dependency missing.")

        return when {
            accountManager.loadAccount(username) == null -> "ACCOUNT_NOT_FOUND"
            accountManager.loadAccount(username)?.password != password -> "WRONG_PASSWORD"
            else -> "LOGIN_SUCCESS"
        }
    }
}