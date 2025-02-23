package game

import com.google.gson.Gson
import java.io.File

class AccountManager(private val accountsDirectory: File = File("data/accounts")) {

    /**@author Raynna
     * @date 23/02-2025
     * Manage loading and creation of accounts
     */

    init {
        if (!accountsDirectory.exists()) {
            accountsDirectory.mkdir()
        }
    }

    fun createAccount(username: String, password: String, email: String): Boolean {
        val accountFile = File(accountsDirectory, "$username.json")
        if (accountFile.exists()) {
            return false
        }

        val account = Account(username, password, email)
        saveAccountToFile(accountFile, account)
        return true
    }

    private fun saveAccountToFile(accountFile: File, account: Account) {
        val json = Gson().toJson(account)
        accountFile.writeText(json)
    }

    fun loadAccount(username: String): Account? {
        val accountFile = File(accountsDirectory, "$username.json")
        if (accountFile.exists()) {
            val json = accountFile.readText()
            return Gson().fromJson(json, Account::class.java)
        }
        return null
    }
}
