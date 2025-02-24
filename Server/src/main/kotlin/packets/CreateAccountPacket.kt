package packets

import game.AccountManager

class CreateAccountPacket : Packet() {
    override val packet: Packets = Packets.CREATE_ACCOUNT_PACKET
    override val packetId: Int = Packets.CREATE_ACCOUNT_PACKET.id
    override val size: Int = 4 // CREATE_ACCOUNT <username> <password> <email>
    override val name: String = "CREATE_ACCOUNT_PACKET"

    override fun processRequest(parts: List<String>, dependencies: Map<String, Any>): String {
        val username = parts.getOrNull(1) ?: ""
        val password = parts.getOrNull(2) ?: ""
        val email = parts.getOrNull(3) ?: ""
        if (username.isEmpty()) {
            return "USERNAME_REQUIRED"
        }
        if (password.isEmpty()) {
            return "PASSWORD_REQUIRED"
        }
        if (email.isEmpty()) {
            return "EMAIL_REQUIRED"
        }
        if (parts.size < size) {
            return "INVALID_PACKETSIZE"
        }

        val accountManager = dependencies["accountManager"] as? AccountManager
            ?: throw IllegalStateException("AccountManager dependency is missing")

        return when {
            accountManager.loadAccount(username) != null -> "ACCOUNT_EXISTS"
            else -> {
                accountManager.createAccount(username, password, email)
                "ACCOUNT_CREATED"
            }
        }
    }
}