import javax.swing.*
import java.awt.*

class LoginScreen {

    /**@author Raynna
     * @date 23/02-2025
     * Creating the Loginscreen, which is send on launch of client
     * All the fields are sent through packets later to server as requests
     */
    private lateinit var frame: JFrame
    private lateinit var usernameField: JTextField
    private lateinit var passwordField: JPasswordField
    private lateinit var loginButton: JButton
    private lateinit var createAccountButton: JButton
    private lateinit var errorLabel: JLabel
    private lateinit var messageLabel: JLabel

    init {
        createLoginScreen()
    }

    private var onLoginListener: ((String, String) -> Unit)? = null

    fun setOnLoginListener(listener: (String, String) -> Unit) {
        onLoginListener = listener
    }

    private fun createLoginScreen() {
        frame = JFrame("Login")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = BorderLayout()

        val usernameLabel = JLabel("Username:")
        usernameField = JTextField(20)

        val passwordLabel = JLabel("Password:")
        passwordField = JPasswordField(20)

        loginButton = JButton("Login").apply {
            addActionListener { onLoginButtonClick() }
        }

        createAccountButton = JButton("Create Account").apply {
            addActionListener { showCreateAccountPopup() }
        }

        errorLabel = JLabel().apply {
            foreground = Color.RED
            isVisible = false
        }

        messageLabel = JLabel().apply {
            foreground = Color.GREEN
            isVisible = false
        }

        val panel = JPanel().apply {
            layout = GridLayout(4, 2)
            add(usernameLabel)
            add(usernameField)
            add(passwordLabel)
            add(passwordField)
            add(loginButton)
            add(createAccountButton)
            add(errorLabel)
        }

        frame.add(panel, BorderLayout.CENTER)
        frame.pack()
        frame.setLocationRelativeTo(null)
    }

    private fun onLoginButtonClick() {
        val username = usernameField.text
        val password = String(passwordField.password)

        onLoginListener?.invoke(username, password)
    }


    private fun showCreateAccountPopup() {
        val createAccountDialog = CreateAccountScreen(frame)
        createAccountDialog.isVisible = true
    }

    fun show() {
        frame.isVisible = true
    }

    fun showError(message: String) {
        errorLabel.text = message
        errorLabel.isVisible = true
    }

    fun showMessage(message: String) {
        messageLabel.text = message
        messageLabel.isVisible = true
        errorLabel.isVisible = false
    }

    fun close() {
        frame.isVisible = false
    }
}
