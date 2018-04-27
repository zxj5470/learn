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

		val buttonPanel = JPanel().apply {
			add(clearButton)
			add(showCircleCheckBox)
		}

		val statusPanel = JPanel().apply {
			layout = BorderLayout()
			add(coordText, "East")
		}

		// Build the delaunay panel
		delaunayPanel.background = DelaunayPanel.tianyiBlue
		this.apply{
			layout = BorderLayout()
			add(buttonPanel, "North")
			add(delaunayPanel, "Center")
			add(statusPanel, "South")
		}

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
		private const val delaunayTitle = "三角网"
		@JvmStatic
		fun main(args: Array<String>) {
			useSystemUI()
			val app = DelaunayMain()
			app.init()
			JFrame().apply {
				setSize(700, 500)
				title = delaunayTitle
				layout = BorderLayout()
				defaultCloseOperation = JFrame.EXIT_ON_CLOSE
				add(app, "Center")
				isVisible = true
			}
		}
	}

	val showCircle
		get() = showCircleCheckBox.isSelected
}