class CreateAccountPacket(
    private val username: String,
    private val password: String,
    private val email: String
) : Packet() {
    override val packet: Packets = Packets.CREATE_ACCOUNT_PACKET
    override val successMessage: String = "ACCOUNT_CREATED"
    override val errorMessages: Map<String, String> = mapOf(
        "FIELDS_NOT_FILLED" to "You need to fill all of the fields.",
        "ACCOUNT_EXISTS" to "Account already exists"
    )
    override val data: List<String> = listOf(username, password, email) // Initialize data
}