package packets

import game.AccountManager

class CreateAccountPacket : Packet() {
    override val packet: Packets = Packets.CREATE_ACCOUNT_PACKET
    override val packetId: Int = Packets.CREATE_ACCOUNT_PACKET.id
    override val size: Int = 4 // CREATE_ACCOUNT <username> <password> <email>
    override val name: String = "CREATE_ACCOUNT_PACKET"

    override fun processRequest(parts: List<String>, dependencies: Map<String, Any>): String {
        val username = parts[1]
        val password = parts[2]
        val email = parts[3]

        val accountManager = dependencies["accountManager"] as? AccountManager
            ?: throw IllegalStateException("AccountManager dependency is missing")

        return when {
            username.isEmpty() || password.isEmpty() || email.isEmpty() -> "FIELDS_NOT_FILLED"
            accountManager.loadAccount(username) != null -> "ACCOUNT_EXISTS"
            else -> {
                accountManager.createAccount(username, password, email)
                "ACCOUNT_CREATED"
            }
        }
    }
}