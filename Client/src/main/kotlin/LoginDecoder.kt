class LoginDecoder {

    /**@author Raynna
     * @date 23/02-2025
     * Handle the packets comming from servers response to client
     */

    fun decodeLoginResponse(response: String, isLogin: Boolean): Pair<Boolean, String> {
        if (!isLogin) {
            return when (response) {
                "FIELDS_NOT_FILLED" -> Pair(false, "You need to fill all of the fields.")
                "ACCOUNT_CREATED" -> Pair(true, "Account has been created")
                "ACCOUNT_EXISTS" -> Pair(false, "Account already exists")
                else -> Pair(false, "Unknown error")
            }
        }
        return when (response) {
            "LOGIN_SUCCESS" -> Pair(true, "") // Login successful
            "ACCOUNT_NOT_FOUND" -> Pair(false, "Account does not exist")
            "WRONG_PASSWORD" -> Pair(false, "Incorrect password")
            "ACCOUNT_BANNED" -> Pair(false, "Your account is banned")
            "USERNAME_REQUIRED" -> Pair(false, "You need to enter a username")
            "PASSWORD_REQUIRED" -> Pair(false, "You need to enter a password")
            else -> Pair(false, "Unknown error")
        }
    }
}
