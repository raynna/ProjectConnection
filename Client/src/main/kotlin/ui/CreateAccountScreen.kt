package ui

import javax.swing.*
import java.awt.*

class CreateAccountScreen {

    private lateinit var frame: JFrame
    private lateinit var usernameField: JTextField
    private lateinit var passwordField: JPasswordField
    private lateinit var emailField: JTextField
    private lateinit var createButton: JButton
    private lateinit var backButton: JButton
    private lateinit var errorLabel: JLabel
    private lateinit var messageLabel: JLabel

    private var onCreateAccountListener: ((String, String, String) -> Unit)? = null
    private var onBackListener: (() -> Unit)? = null

    init {
        createAccountScreen()
    }

    fun setOnCreateAccountListener(listener: (String, String, String) -> Unit) {
        onCreateAccountListener = listener
    }

    fun setOnBackListener(listener: () -> Unit) {
        onBackListener = listener
    }

    private fun createAccountScreen() {
        frame = JFrame("Create Account")
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.layout = BorderLayout()

        val usernameLabel = JLabel("Username:")
        usernameField = JTextField(20)

        val passwordLabel = JLabel("Password:")
        passwordField = JPasswordField(20)

        val emailLabel = JLabel("Email:")
        emailField = JTextField(20)

        createButton = JButton("Create Account").apply {
            addActionListener { onCreateButtonClick() }
        }

        backButton = JButton("Back").apply {
            addActionListener { onBackButtonClick() }
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
            layout = GridLayout(5, 2)
            add(usernameLabel)
            add(usernameField)
            add(passwordLabel)
            add(passwordField)
            add(emailLabel)
            add(emailField)
            add(createButton)
            add(backButton)
            add(errorLabel)
        }

        frame.add(panel, BorderLayout.CENTER)
        frame.pack()
        frame.setLocationRelativeTo(null)
    }

    private fun onCreateButtonClick() {
        val username = usernameField.text
        val password = String(passwordField.password)
        val email = emailField.text

        onCreateAccountListener?.invoke(username, password, email)
    }

    private fun onBackButtonClick() {
        frame.dispose()
        onBackListener?.invoke()
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
        frame.dispose()
    }
}
