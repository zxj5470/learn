import java.awt.*
import java.awt.event.*
import javax.swing.*

class DelaunayMain : javax.swing.JApplet(), Runnable, ActionListener, MouseListener, MouseMotionListener {
	private val clearButton = JButton("清除")
	private val showCircleCheckBox = JCheckBox("显示外接圆")
	private val delaunayPanel = DelaunayPanel(this)
	private val coordText = JLabel("(0,0)")

	override fun init() {
		SwingUtilities.invokeAndWait(this)
	}

	override fun run() {
		layout = BorderLayout()

		val buttonPanel = JPanel()
		buttonPanel.add(clearButton)
		buttonPanel.add(showCircleCheckBox)
		this.add(buttonPanel, "North")

		val statusPanel = JPanel()
		statusPanel.add(coordText, "East")
		this.add(statusPanel, "South")

		// Build the delaunay panel
		delaunayPanel.background = DelaunayPanel.tianyiBlue
		this.add(delaunayPanel, "Center")

		clearButton.addActionListener(this)
		showCircleCheckBox.addActionListener(this)
		delaunayPanel.addMouseListener(this)
		delaunayPanel.addMouseMotionListener(this)
	}

	/**
	 * A button has been pressed; redraw the picture.
	 */
	override fun actionPerformed(e: ActionEvent) {
		when (e.source) {
			clearButton -> delaunayPanel.clear()
		}
		delaunayPanel.repaint()
	}

	override fun mouseEntered(e: MouseEvent) {}
	override fun mouseExited(e: MouseEvent) {}
	override fun mousePressed(e: MouseEvent) {
		if (e.source !== delaunayPanel) return
		val point = Pnt(e.x, e.y)
		delaunayPanel.addPointToGraph(point)
		delaunayPanel.repaint()
	}

	override fun mouseReleased(e: MouseEvent) {}
	override fun mouseClicked(e: MouseEvent) {}
	override fun mouseDragged(e: MouseEvent) {}
	override fun mouseMoved(e: MouseEvent) {
		coordText.text = "( ${e.x} , ${e.y} )"
	}

	companion object {
		private const val title = "三角网"
		@JvmStatic
		fun main(args: Array<String>) {
			try {
				// set system default style
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
			} catch (e: Exception) {
				e.printStackTrace()
			}
			val app = DelaunayMain()    // Create applet
			app.init()                           // Applet initialization
			val frame = JFrame()           // Create window
			frame.setSize(700, 500)               // Set window size
			frame.title = title           // Set window title
			frame.layout = BorderLayout()   // Specify layout manager
			frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
			frame.add(app, "Center")           // Place applet into window
			frame.isVisible = true                // Show the window
		}
	}
	val showCircle
		get() = showCircleCheckBox.isSelected
}