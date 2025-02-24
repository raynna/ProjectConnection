package client

import CreateAccountPacket
import LoginPacket
import Packet
import java.io.BufferedReader
import java.io.BufferedWriter

class ClientPacketHandler(
    private val input: BufferedReader,
    private val output: BufferedWriter
) {

    private fun sendPacket(packet: Packet): Pair<Boolean, String> {

        output.write("${packet.packet.id} ${packet.data.joinToString(" ")}\n")
        output.flush()
        println("[sendPacket] client sent output: PacketId: ${packet.packet.id}, PacketName: ${packet.packet.name} ${packet.data.joinToString(" ")}")
        val response = input.readLine() ?: "ERROR"
        println("[sendPacket] client recieved input: $response")
        return packet.decodeResponse(response)
    }

    fun sendLoginRequest(username: String, password: String): Pair<Boolean, String> {
        println("[sendLoginRequest] username: $username password: $password")
        val packet = LoginPacket(username, password) // Pass username and password
        return sendPacket(packet)
    }

    fun sendCreateAccountRequest(username: String, password: String, email: String): Pair<Boolean, String> {
        val packet = CreateAccountPacket(username, password, email) // Pass username, password, and email
        return sendPacket(packet)
    }
}