import javax.swing.*
import java.awt.*
import java.io.IOException

class CreateAccountScreen(parent: JFrame) : JDialog(parent, "Create Account", true) {

    /**@author Raynna
     * @date 23/02-2025
     * Create the CreateAccount screen
     * This handles creating account and sending packet requests to server to make sure its a valid account to create.
     */

    private val usernameField = JTextField(20)
    private val passwordField = JPasswordField(20)
    private val emailField = JTextField(20)
    private val createButton = JButton("Create Account")
    private val cancelButton = JButton("Cancel")

    init {
        layout = GridLayout(5, 2)
        setSize(300, 200)
        setLocationRelativeTo(parent)

        add(JLabel("Username:"))
        add(usernameField)
        add(JLabel("Password:"))
        add(passwordField)
        add(JLabel("Email:"))
        add(emailField)
        add(createButton)
        add(cancelButton)

        createButton.addActionListener { onCreateAccount() }
        cancelButton.addActionListener { dispose() }
    }

    private fun onCreateAccount() {
        val username = usernameField.text
        val password = String(passwordField.password)
        val email = emailField.text

        try {
            val response = GameClient.createAccount(username, password, email)

            if (response.first) {
                JOptionPane.showMessageDialog(this, "Account created successfully!")
                dispose()
            } else {
                JOptionPane.showMessageDialog(this, response.second, "Error", JOptionPane.ERROR_MESSAGE)
            }
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(this, "Error connecting to the server", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}
