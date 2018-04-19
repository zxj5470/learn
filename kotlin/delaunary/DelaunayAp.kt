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
 * The Delauany applet.
 *
 * Creates and displays a Delaunay Triangulation (DT) or a Voronoi Diagram
 * (VoD). Has a main program so it is an application as well as an applet.
 *
 * @author Paul Chew
 *
 * Created July 2005. Derived from an earlier, messier version.
 *
 * Modified December 2007. Updated some of the Triangulation methods. Added the
 * "Colorful" checkbox. Reorganized the interface between DelaunayAp and
 * DelaunaryPanel. Added code to find a Voronoi cell.
 */
class DelaunayAp : javax.swing.JApplet(), Runnable, ActionListener, MouseListener {

	private val debug = false             // Used for debugging
	private var currentSwitch: Component? = null    // Entry-switch that mouse is in
	private val voronoiButton = JRadioButton("Voronoi Diagram")
	private val delaunayButton = JRadioButton("Delaunay Triangulation")
	private val clearButton = JButton("Clear")
	private val colorfulBox = JCheckBox("More Colorful")
	private val delaunayPanel = DelaunaryPanel(this)
	private val circleSwitch = JLabel("Show Empty Circles")
	private val delaunaySwitch = JLabel("Show Delaunay Edges")
	private val voronoiSwitch = JLabel("Show Voronoi Edges")

	/**
	 * @return true iff the "colorful" box is selected
	 */
	val isColorful: Boolean
		get() = colorfulBox.isSelected

	/**
	 * @return true iff doing Voronoi diagram.
	 */
	val isVoronoi: Boolean
		get() = voronoiButton.isSelected

	/**
	 * Initialize the applet.
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

	/**
	 * Set up the applet's GUI.
	 * As recommended, the init method executes this in the event-dispatching
	 * thread.
	 */
	override fun run() {
		layout = BorderLayout()

		// Add the button controls
		val group = ButtonGroup()
		group.add(voronoiButton)
		group.add(delaunayButton)
		val buttonPanel = JPanel()
		buttonPanel.add(voronoiButton)
		buttonPanel.add(delaunayButton)
		buttonPanel.add(clearButton)
		buttonPanel.add(JLabel("          "))      // Spacing
		buttonPanel.add(colorfulBox)
		this.add(buttonPanel, "North")

		// Add the mouse-entry switches
		val switchPanel = JPanel()
		switchPanel.add(circleSwitch)
		switchPanel.add(Label("     "))            // Spacing
		switchPanel.add(delaunaySwitch)
		switchPanel.add(Label("     "))            // Spacing
		switchPanel.add(voronoiSwitch)
		this.add(switchPanel, "South")

		// Build the delaunay panel
		delaunayPanel.background = Color.gray
		this.add(delaunayPanel, "Center")

		// Register the listeners
		voronoiButton.addActionListener(this)
		delaunayButton.addActionListener(this)
		clearButton.addActionListener(this)
		colorfulBox.addActionListener(this)
		delaunayPanel.addMouseListener(this)
		circleSwitch.addMouseListener(this)
		delaunaySwitch.addMouseListener(this)
		voronoiSwitch.addMouseListener(this)

		// Initialize the radio buttons
		voronoiButton.doClick()
	}

	/**
	 * A button has been pressed; redraw the picture.
	 */
	override fun actionPerformed(e: ActionEvent) {
		if (debug)
			println((e.source as AbstractButton).text)
		if (e.source === clearButton) delaunayPanel.clear()
		delaunayPanel.repaint()
	}

	/**
	 * If entering a mouse-entry switch then redraw the picture.
	 */
	override fun mouseEntered(e: MouseEvent) {
		currentSwitch = e.component
		if (currentSwitch is JLabel)
			delaunayPanel.repaint()
		else
			currentSwitch = null
	}

	/**
	 * If exiting a mouse-entry switch then redraw the picture.
	 */
	override fun mouseExited(e: MouseEvent) {
		currentSwitch = null
		if (e.component is JLabel) delaunayPanel.repaint()
	}

	/**
	 * If mouse has been pressed inside the delaunayPanel then add a new site.
	 */
	override fun mousePressed(e: MouseEvent) {
		if (e.source !== delaunayPanel) return
		val point = Pnt(e.x, e.y)
		if (debug) println("Click " + point)
		delaunayPanel.addSite(point)
		delaunayPanel.repaint()
	}

	/**
	 * Not used, but needed for MouseListener.
	 */
	override fun mouseReleased(e: MouseEvent) {}

	override fun mouseClicked(e: MouseEvent) {}

	/**
	 * @return true iff within circle switch
	 */
	fun showingCircles(): Boolean {
		return currentSwitch === circleSwitch
	}

	/**
	 * @return true iff within delaunay switch
	 */
	fun showingDelaunay(): Boolean {
		return currentSwitch === delaunaySwitch
	}

	/**
	 * @return true iff within voronoi switch
	 */
	fun showingVoronoi(): Boolean {
		return currentSwitch === voronoiSwitch
	}

	companion object {

		private val windowTitle = "Voronoi/Delaunay Window"

		/**
		 * Main program (used when run as application instead of applet).
		 */
		@JvmStatic
		fun main(args: Array<String>) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
			} catch (e: Exception) {
				e.printStackTrace()
			}

			val applet = DelaunayAp()    // Create applet
			applet.init()                           // Applet initialization
			val dWindow = JFrame()           // Create window
			dWindow.setSize(700, 500)               // Set window size
			dWindow.title = windowTitle           // Set window title
			dWindow.layout = BorderLayout()   // Specify layout manager
			dWindow.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
			// Specify closing behavior
			dWindow.add(applet, "Center")           // Place applet into window
			dWindow.isVisible = true                // Show the window
		}
	}
}