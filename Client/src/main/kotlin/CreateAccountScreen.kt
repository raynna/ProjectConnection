import javax.swing.*
import java.awt.*

class CreateAccountScreen(owner: JFrame) : JDialog(owner, "Create Account", true) {

    private lateinit var usernameField: JTextField
    private lateinit var passwordField: JPasswordField
    private lateinit var emailField: JTextField
    private lateinit var createButton: JButton
    private lateinit var errorLabel: JLabel

    init {
        createUI()
    }

    private fun createUI() {
        layout = BorderLayout()

        val usernameLabel = JLabel("Username:")
        usernameField = JTextField(20)

        val passwordLabel = JLabel("Password:")
        passwordField = JPasswordField(20)

        val emailLabel = JLabel("Email:")
        emailField = JTextField(20)

        createButton = JButton("Create Account")

        errorLabel = JLabel().apply {
            foreground = Color.RED
            isVisible = false
        }

        val panel = JPanel().apply {
            layout = GridLayout(4, 2)
            add(usernameLabel)
            add(usernameField)
            add(passwordLabel)
            add(passwordField)
            add(emailLabel)
            add(emailField)
            add(createButton)
            add(errorLabel)
        }

        add(panel, BorderLayout.CENTER)
        pack()
        setLocationRelativeTo(owner)
    }
}