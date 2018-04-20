/*
 * Copyright (c) 2005, 2007 by L. Paul Chew.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import java.awt.*
import java.awt.event.*
import javax.swing.*

/**
 * The Delaunay applet.
 *
 * Creates and displays a Delaunay DelaunayTriangle (DT) or a Voronoi Diagram
 * (VoD). Has a main program so it is an application as well as an applet.
 *
 * @author Paul Chew
 *
 * Created July 2005. Derived from an earlier, messier version.
 *
 * Modified December 2007. Updated some of the DelaunayTriangle methods. Added the
 * "Colorful" checkbox. Reorganized the interface between DelaunayMain and
 * DelaunayPanel. Added code to find a Voronoi cell.
 */
class DelaunayMain : javax.swing.JApplet(), Runnable, ActionListener, MouseListener, MouseMotionListener {
	private val clearButton = JButton("清除")
	private val showCircleCheckBox = JCheckBox("显示外接圆")
	private val delaunayPanel = DelaunayPanel(this)
	private val coordText = JLabel("(0,0)")

	/**
	 * As recommended, the actual use of Swing components takes place in the
	 * event-dispatching thread.
	 */
	override fun init() {
		try {
			SwingUtilities.invokeAndWait(this)
		} catch (e: Exception) {
			System.err.println("Initialization failure")
		}
	}

	override fun run() {
		layout = BorderLayout()

		val buttonPanel = JPanel()
		buttonPanel.add(clearButton)
		buttonPanel.add(showCircleCheckBox)
		this.add(buttonPanel, "North")

		val statusPanel = JPanel()
		statusPanel.add(coordText)
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

	/**
	 * If entering a mouse-entry switch then redraw the picture.
	 */
	override fun mouseEntered(e: MouseEvent) {}

	/**
	 * If exiting a mouse-entry switch then redraw the picture.
	 */
	override fun mouseExited(e: MouseEvent) {}

	/**
	 * If mouse has been pressed inside the delaunayPanel then add a new site.
	 */
	override fun mousePressed(e: MouseEvent) {
		if (e.source !== delaunayPanel) return
		val point = Pnt(e.x, e.y)
		delaunayPanel.addPointToGraph(point)
		delaunayPanel.repaint()
	}

	/**
	 * Not used, but needed for MouseListener.
	 */
	override fun mouseReleased(e: MouseEvent) {}

	override fun mouseClicked(e: MouseEvent) {}


	override fun mouseMoved(e: MouseEvent) {
		coordText.text = "( ${e.x} , ${e.y} )"
	}

	override fun mouseDragged(e: MouseEvent) {}

	companion object {

		private const val title = "三角网"

		/**
		 * Main program (used when run as application instead of applet).
		 */
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