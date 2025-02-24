class LoginPacket(
    private val username: String,
    private val password: String
) : Packet() {
    override val packet: Packets = Packets.LOGIN_PACKET
    override val successMessage: String = "LOGIN_SUCCESS"
    override val errorMessages: Map<String, String> = mapOf(
        "ACCOUNT_NOT_FOUND" to "Account does not exist",
        "WRONG_PASSWORD" to "Incorrect password",
        "ACCOUNT_BANNED" to "Your account is banned",
        "USERNAME_REQUIRED" to "You need to enter a username",
        "PASSWORD_REQUIRED" to "You need to enter a password"
    )
    override val data: List<String> = listOf(username, password) // Initialize data
}