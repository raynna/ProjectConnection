package packets

enum class Packets(val id: Int, val successMessage: String?, val errorMessages: Map<String, String>) {
    LOGIN_PACKET(
        id = 1,
        successMessage = "Login successful",
        errorMessages = mapOf(
            "ACCOUNT_NOT_FOUND" to "Account does not exist",
            "WRONG_PASSWORD" to "Incorrect password",
            "ACCOUNT_BANNED" to "Your account is banned",
            "USERNAME_REQUIRED" to "You need to enter a username",
            "PASSWORD_REQUIRED" to "You need to enter a password"
        )
    ),
    CREATE_ACCOUNT_PACKET(
        id = 2,
        successMessage = "Account has been created",
        errorMessages = mapOf(
            "FIELDS_NOT_FILLED" to "You need to fill all of the fields.",
            "ACCOUNT_EXISTS" to "Account already exists"
        )
    );

    fun getErrorMessage(code: String): String {
        return errorMessages[code] ?: "Unknown error"
    }
}