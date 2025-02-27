package ui

import javax.swing.*
import java.awt.*

class GameScreen {

    /**@author Raynna
     * @date 23/02-2025
     * Create the main gamescreen
     */

    private lateinit var frame: JFrame

    fun show() {
        frame = JFrame("Game Screen")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = BorderLayout()

        val label = JLabel("Welcome to the Game!", JLabel.CENTER)
        label.font = Font("Arial", Font.BOLD, 24)

        frame.add(label, BorderLayout.CENTER)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}
