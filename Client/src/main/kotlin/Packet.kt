
abstract class Packet {
    abstract val packet: Packets
    abstract val successMessage: String?
    abstract val errorMessages: Map<String, String>
    abstract val data: List<String>

    private fun getErrorMessage(code: String): String {
        return errorMessages[code] ?: "Unknown error"
    }

    fun decodeResponse(response: String): Pair<Boolean, String> {
        return when (response) {
            successMessage -> Pair(true, successMessage ?: "")
            else -> Pair(false, getErrorMessage(response))
        }
    }
}