package packets

class CreateAccountPacket(
    private val username: String,
    private val password: String,
    private val email: String
) : Packet() {
    override val packet: Packets = Packets.CREATE_ACCOUNT_PACKET
    override val successMessage: String = "ACCOUNT_CREATED"
    override val errorMessages: Map<String, String> = mapOf(
        "USERNAME_REQUIRED" to "You need to enter a username",
        "PASSWORD_REQUIRED" to "You need to enter a password",
        "EMAIL_REQUIRED" to "You need to enter an email",
        "ACCOUNT_EXISTS" to "Account already exists"
    )
    override val data: List<String> = listOf(username, password, email)
}