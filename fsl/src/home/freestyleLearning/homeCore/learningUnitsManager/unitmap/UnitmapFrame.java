/**
 * UnitmapFrame is the main UI.
 * It interacts with <code>UnitmapGraph</code> to display the Unitmap.
 * It registers i.a. at the <code>GraphSelectionListener</code> to update
 * the UI and at <code>FSLLearningUnitsManager</code> to update 
 * <code>UnitmapGraph</code>.
 * It also offers support for undo/redo operations.
 * 
 * The UI is based on the JGraph example application GraphEdX published
 * under GNU LPGL.
 * 
 * @see com.jgraph.example.GraphEdX
 * @author Jens Sieberg, 31.07. - 11.09.2006 
 */

package freestyleLearning.homeCore.learningUnitsManager.unitmap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;

import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;

import freestyleLearning.homeCore.learningUnitsManager.unitmap.jgraph.JGraphFoldingManager;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLLearningUnitViewElementInteractionButton;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearningGroup.independent.gui.FLGAbstractImageButton;
import freestyleLearningGroup.independent.gui.FLGEffectPanel;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class UnitmapFrame extends JFrame implements GraphSelectionListener, KeyListener {
	
	// JGraph instance
	protected UnitmapGraph graph;

	// Undo Manager
	protected GraphUndoManager undoManager;

	protected JGraphFoldingManager foldingManager;
	
	// received from Unitmap
	FLGInternationalization internationalization;
	
	// showing a learning progress to a selected learning unit
	UnitmapProgressFrame progressFrame;


	AbstractButton saveButton, refreshButton, checkConsistencyButton, undoButton, 
	   			   redoButton, removeButton, zoomStdButton, zoomInButton, zoomOutButton,
	   			   toFrontButton, toBackButton, toProgressViewButton;
	
	FLGAbstractImageButton toggleConnectModeButton, toggleMarkingModeButton;
	java.awt.Image connectModeOnButtonImage, connectModeOffButtonImage,
	               markingModeOnButtonImage, markingModeOffButtonImage;

	// state of markingModeButton
	private boolean markingModeEnabled = false;
	
	JPanel bottomPanel, authorButtonPanel, learnerButtonPanel;
	
	//presents information about selected learning unit
	protected JTable learningUnitInfoTable;

	// Status Bar
	protected StatusBarGraphListener statusBar;
	


	/**
	 * builds the main user interface.
	 * 
	 * @param graph
	 * @param internationalization
	 */
	public UnitmapFrame(UnitmapGraph graph, FLGInternationalization internationalization) {
		this.internationalization = internationalization;
		
		this.setTitle(internationalization.getString("unitmap.title"));

		// Set Default Size
                int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.4);
		int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.4);
		this.setSize(width, height);  
                this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - width)/2, 
                    (Toolkit.getDefaultToolkit().getScreenSize().height - height)/2);
		// Fetch URL to Icon Resource
		URL jgraphUrl = Unitmap.class.getClassLoader().getResource(
				"freestyleLearning/homeCore/learningUnitsManager/unitmap/images/unitmap_icon.gif");
		// If Valid URL
		if (jgraphUrl != null) {
			// Load Icon
			ImageIcon jgraphIcon = new ImageIcon(jgraphUrl);
			// Use in Window
			this.setIconImage(jgraphIcon.getImage());
		}
		
		
		// Construct the Graph
		this.graph = graph;
		
		// Use a Custom Marquee Handler
		this.graph.setMarqueeHandler(createMarqueeHandler());
		
		// Construct Command History
		//
		undoManager = createGraphUndoManager();
			
		populateContentPane();
		
		graph.loadUnitmapData();
		
		installLearningUnitListeners();
		installGraphListeners(this.graph);
		
		// prepare an instance of progressFrame
		progressFrame = new UnitmapProgressFrame(graph.getModel(), this, internationalization);
	}

	/**
	 * creates a GraphUndoManager which also Updates the ToolBar.
	 * @return  GraphUndoManager
	 */
	public GraphUndoManager createGraphUndoManager()
	{
		return new GraphUndoManager() {
			// Override Superclass
			public void undoableEditHappened(UndoableEditEvent e) {
				// First Invoke Superclass
				super.undoableEditHappened(e);
				// Then Update Undo/Redo Buttons
				updateHistoryButtons();
			}
		};		
	}
	
	
	/**
	 * builds the UI.
	 * 
	 * It consists of one standard toolbar (<code>createButtonPanel()</code>),
	 * a user specific toolbar (<code> createAuthorButtonPanel(),
	 * <code>createLearnerButtonPanel</code>) and a UnimtapGraph and 
	 * JTable in the center.
	 * 
	 * If you want to make changes to the entire UI, this method
	 * needs to be overwritten.
	 */
	protected void populateContentPane() {
		JPanel topPanel = new javax.swing.JPanel();
		bottomPanel = new javax.swing.JPanel();

		FLGEffectPanel graphPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor4", true);
		FLGEffectPanel infoPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor4", true);
		
		//Use Border Layout
		getContentPane().setLayout(new BorderLayout());
		
		//Add tool bar
		topPanel.setLayout(new BorderLayout());
		topPanel.add(createButtonPanel(), java.awt.BorderLayout.CENTER);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		//Add Graph Panel to left

		JScrollPane leftScrollPane = new javax.swing.JScrollPane(graph);
		leftScrollPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(6, 6, 6, 6)));
		leftScrollPane.setBackground((Color)UIManager.get("FSLMainFrameColor4"));
		
		//Add Info Panel to right
		learningUnitInfoTable = new JTable(new LearningUnitTableModel(graph, internationalization));		

		JScrollPane rightScrollPane = new javax.swing.JScrollPane(learningUnitInfoTable);		
		rightScrollPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(6, 6, 6, 6)));
		rightScrollPane.setBackground((Color)UIManager.get("FSLMainFrameColor4"));
		
		JSplitPane editorSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftScrollPane, rightScrollPane);
		editorSplitPane.setDividerSize(2);
		// if included setDividerSize must not be included
		// editorSplitPane.setOneTouchExpandable(true);
		editorSplitPane.setDividerLocation((int)this.getSize().getWidth()*6/7);
		editorSplitPane.setResizeWeight(0.6);

		getContentPane().add(editorSplitPane, BorderLayout.CENTER);
		
		
		//Create AuthorPanel
		authorButtonPanel = createAuthorButtonPanel();
		
		//Create LearnerPanel
		learnerButtonPanel = createLearnerButtonPanel();
		
		bottomPanel.setLayout(new BorderLayout());
		
		// add the context dependent author/learner panel

		bottomPanel.add(authorButtonPanel, BorderLayout.NORTH);
		bottomPanel.add(learnerButtonPanel, BorderLayout.CENTER);

		boolean isAuthor = graph.getLearningUnitsManager().getUserRoleIsAuthor();
		authorButtonPanel.setVisible(isAuthor);
		graph.setPortsVisible(isAuthor);
		learnerButtonPanel.setVisible(!isAuthor);
		
		
		graph.setIsAuthor(isAuthor);		
		
		//Add Status Bar
		//statusBar = createStatusBar();
		//bottomPanel.add(statusBar, BorderLayout.SOUTH);
		
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}


	/**
	 * creates a toolbar with buttons that apply to learner and author.
	 * 
	 * Overwrite this method if you want to supply more or less
	 * functionality for both author and learner.
	 * 
	 * @return a standard toolbar.
	 */
	protected FLGEffectPanel createButtonPanel()
	{
		FLGEffectPanel buttonPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor1", "FSLMainFrameColor4", true);
		buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
		
		//saveButtonImage
		java.awt.Image saveButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/save.gif"));
		saveButton = new FSLLearningUnitViewElementInteractionButton(saveButtonImage);
		saveButton.setToolTipText(internationalization.getString("unitmap.button.save"));
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				uninstallGraphListeners(graph);
				//TODO internationalization 
				if(!graph.saveUnitmapData())
					FLGOptionPane.showMessageDialog(internationalization.getString("dialog.save.errorMessage"), 
													internationalization.getString("dialog.save.title"),
													FLGOptionPane.ERROR_MESSAGE);
				else
				{
					undoManager.discardAllEdits();
					updateHistoryButtons();
				}
				installGraphListeners(graph);				
			}
		});
		saveButton.setEnabled(false);
		buttonPanel.add(saveButton);
		
		//refreshButton
		java.awt.Image refreshButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/refresh.gif"));
		refreshButton = new FSLLearningUnitViewElementInteractionButton(refreshButtonImage);
		refreshButton.setToolTipText(internationalization.getString("unitmap.button.refresh"));
		refreshButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {	
				int returnValue = FLGOptionPane.YES_OPTION;
				if (isUnsaved())
				{
					returnValue = FLGOptionPane.showConfirmDialog(internationalization.getString("dialog.refresh.message"),
			    				  								  internationalization.getString("dialog.refresh.title"), 
			    				  							      FLGOptionPane.YES_NO_OPTION, 
			    				  							      FLGOptionPane.WARNING_MESSAGE);					
				}
				
				if (returnValue == FLGOptionPane.YES_OPTION)
				{
					uninstallGraphListeners(graph);
					graph.loadUnitmapData();
					installGraphListeners(graph);
				}
			}
		});
		buttonPanel.add(refreshButton);		
		
		//undoButton
		java.awt.Image undoButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/undo.gif"));
		undoButton = new FSLLearningUnitViewElementInteractionButton(undoButtonImage);
		undoButton.setToolTipText(internationalization.getString("unitmap.button.undo"));
		undoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				undo();
			}
		});
		undoButton.setEnabled(false);
		buttonPanel.add(undoButton);
		
		//redoButton
		java.awt.Image redoButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/redo.gif"));
		redoButton = new FSLLearningUnitViewElementInteractionButton(redoButtonImage);
		redoButton.setToolTipText(internationalization.getString("unitmap.button.redo"));
		redoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				redo();
			}
		});
		redoButton.setEnabled(false);
		buttonPanel.add(redoButton);		

		//zoomStdButton
		java.awt.Image zoomStdButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/zoomStd.gif"));
		zoomStdButton = new FSLLearningUnitViewElementInteractionButton(zoomStdButtonImage);
		zoomStdButton.setToolTipText(internationalization.getString("unitmap.button.zoomStd"));
		zoomStdButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				graph.setScale(1.0);
			}
		});
		buttonPanel.add(zoomStdButton);		

		//zoomIn
		java.awt.Image zoomInButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/zoomIn.gif"));
		zoomInButton = new FSLLearningUnitViewElementInteractionButton(zoomInButtonImage);
		zoomInButton.setToolTipText(internationalization.getString("unitmap.button.zoomIn"));
		zoomInButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				graph.setScale(2 * graph.getScale());
			}
		});		
		buttonPanel.add(zoomInButton);		

		//zoomOut
		java.awt.Image zoomOutButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/zoomOut.gif"));
		zoomOutButton = new FSLLearningUnitViewElementInteractionButton(zoomOutButtonImage);
		zoomOutButton.setToolTipText(internationalization.getString("unitmap.button.zoomOut"));
		zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				graph.setScale(graph.getScale() / 2);
			}
		});			
		buttonPanel.add(zoomOutButton);			

		//toFrontButton malen
		java.awt.Image toFrontButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/toFront.gif"));
		toFrontButton = new FSLLearningUnitViewElementInteractionButton(toFrontButtonImage);
		toFrontButton.setToolTipText(internationalization.getString("unitmap.button.toFront"));
		toFrontButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (!graph.isSelectionEmpty())
					toFront(graph.getSelectionCells());
			}
		});
		toFrontButton.setEnabled(false);
		buttonPanel.add(toFrontButton);		
		
		//toBackButton
		java.awt.Image toBackButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/toBack.gif"));
		toBackButton = new FSLLearningUnitViewElementInteractionButton(toBackButtonImage);
		toBackButton.setToolTipText(internationalization.getString("unitmap.button.toBack"));
		toBackButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (!graph.isSelectionEmpty())
					toBack(graph.getSelectionCells());
			}
		});
		toBackButton.setEnabled(false);
		buttonPanel.add(toBackButton);		
		
		return buttonPanel;
	}
	
	/**
	 * creates a toolbar with buttons that apply only to the author.
	 * 
	 * Overwrite this method if you want to supply more or less
	 * functionality only accessible by the author.
	 * 
	 * @return an author toolbar.
	 */
	protected FLGEffectPanel createAuthorButtonPanel() {
		FLGEffectPanel authorButtonPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor1", "FSLMainFrameColor4", true);
		authorButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		//toggleConnectModeButton
		connectModeOnButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/connectModeOff.gif"));
		connectModeOffButtonImage =
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/connectModeOn.gif"));
		toggleConnectModeButton = new FSLLearningUnitViewElementInteractionButton(connectModeOnButtonImage);
		toggleConnectModeButton.setToolTipText(internationalization.getString("unitmap.button.connectModeOn"));
		toggleConnectModeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				graph.setPortsVisible(!graph.isPortsVisible());
				toggleConnectMode(graph.isPortsVisible());
			}
		});		
		authorButtonPanel.add(toggleConnectModeButton);
		
		//removeButton 
		java.awt.Image removeButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/remove.gif"));
		removeButton = new FSLLearningUnitViewElementInteractionButton(removeButtonImage);
		removeButton.setToolTipText(internationalization.getString("unitmap.button.remove"));
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				removeEdge();
			} 
		});
		removeButton.setEnabled(false);
		authorButtonPanel.add(removeButton);
		
		//checkConsistencyButton
		java.awt.Image checkConsistencyButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/checkConsistency.gif"));
		checkConsistencyButton = new FSLLearningUnitViewElementInteractionButton(checkConsistencyButtonImage);
		checkConsistencyButton.setToolTipText(internationalization.getString("unitmap.button.checkConsistency"));
		checkConsistencyButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (graph.checkInconsitency())
				{
					//TODO maybe open dialog window here to inform user
				}
				else
				{	
					//TODO maybe open dialog window here to inform user					
				}
			}
		});			
		authorButtonPanel.add(checkConsistencyButton);

		
		return authorButtonPanel;
	}
	
	private void toggleConnectMode(boolean portsVisible) {
		java.awt.Image imageToDisplay;
		String stringToDisplay;
		if (portsVisible)
		{
			imageToDisplay = connectModeOnButtonImage;
			stringToDisplay = internationalization.getString("unitmap.button.connectModeOn");
		}
		else
		{
			imageToDisplay = connectModeOffButtonImage;
			stringToDisplay = internationalization.getString("unitmap.button.connectModeOff");
		}
		toggleConnectModeButton.setImage(imageToDisplay);
		toggleConnectModeButton.setToolTipText(stringToDisplay);
	}
	
	
	/**
	 * creates a toolbar with buttons that apply only to the learner.
	 * 
	 * Overwrite this method if you want to supply more or less
	 * functionality only accessible by the learner.
	 * 
	 * @return a learner toolbar.
	 * 	 */
	protected FLGEffectPanel createLearnerButtonPanel() {
		FLGEffectPanel learnerButtonPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor1", "FSLMainFrameColor4", true);
		learnerButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		//toggleMarkingModeButton
		markingModeOnButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/markingModeOn.gif"));
		markingModeOffButtonImage =
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/markingModeOff.gif"));
		toggleMarkingModeButton = new FSLLearningUnitViewElementInteractionButton(markingModeOnButtonImage);		
		toggleMarkingModeButton.setToolTipText(internationalization.getString("unitmap.button.MarkingModeOn"));
		toggleMarkingModeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				markingModeEnabled = !markingModeEnabled;
				
				java.awt.Image imageToDisplay = markingModeOffButtonImage;
				String stringToDisplay = internationalization.getString("unitmap.button.markingModeOff");
				
				if (!markingModeEnabled)
				{
					imageToDisplay = markingModeOnButtonImage;
					stringToDisplay = internationalization.getString("unitmap.button.markingModeOn");
					graph.markAllPrerequisites(null);
					toProgressViewButton.setEnabled(false);
				}
				
				toggleMarkingModeButton.setImage(imageToDisplay);
				toggleMarkingModeButton.setToolTipText(stringToDisplay);
			}
		});		
		learnerButtonPanel.add(toggleMarkingModeButton);
		
		//toProgressViewButton
		java.awt.Image toProgressViewButtonImage = 
			FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/unitmap/images/toProgressView.gif"));
		toProgressViewButton = new FSLLearningUnitViewElementInteractionButton(toProgressViewButtonImage);		
		toProgressViewButton.setToolTipText(internationalization.getString("unitmap.button.toProgressView"));
		toProgressViewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				java.util.List currentPrerequisiteList = graph.getCurrentPrerequisiteList();
				graph.markAllPrerequisites(null);
				toProgressViewButton.setEnabled(false);
				
				progressFrame.showProgressFrame(currentPrerequisiteList);
			}
		});		
		learnerButtonPanel.add(toProgressViewButton);
		toProgressViewButton.setEnabled(false);
		
		
		return learnerButtonPanel;
	}

	//
	// Listeners
	//	
	
	/**
	 * creates a status bar.
	 * 
	 * not used at the moment
	 */
	protected StatusBarGraphListener createStatusBar() {
		return new EdStatusBar();
	}
	
	
	
	/**
	 * registers at <code>FSLLearningUnitsManager</code> to be informed 
	 * about changes in the installed learning units. It also updates
	 * the <code>UnitmapGraph</code> if the learning progress status 
	 * of a learning unit has changed. 
	 * 
	 * At the time of development there were only available
	 * events for created and removed learning units, but not for
	 * changes e.g. in the learning units tree structure. So this
	 * method needs to be overwritten if there are more events
	 * to be handled. 
	 */
	protected void installLearningUnitListeners() {
		graph.getLearningUnitsManager().addLearningUnitListener(
				new FSLLearningUnitAdapter() {

					public void learningUnitCreated(FSLLearningUnitEvent event) {
						System.err.println("Learning unit created. Unitmap updates...");
						uninstallGraphListeners(graph);
						graph.loadUnitmapData();
						installGraphListeners(graph);
						System.err.println("... finished.");
					}

					public void learningUnitRemoved(FSLLearningUnitEvent event) {
						System.err.println("Learning unit removed. Unitmap updates...");
						uninstallGraphListeners(graph);
						graph.loadUnitmapData();
						installGraphListeners(graph);
						System.err.println("... finished");
					}

					public void learningUnitProgressStatusChanged(FSLLearningUnitEvent event) {
						System.err.println("Progress status changed. Unitmap updates... ");
						
						if (!graph.isAuthor())
						{
							graph.restoreGraphDefaults();
							if (progressFrame.isVisible())
							{
								progressFrame.calculateOverallProgressStatus();
								progressFrame.getGraph().getGraphLayoutCache().reload();
								progressFrame.repaint();
							}
						}
						System.err.println("... finished.");
					}	
				});
	}

	// Hook for subclassers
	protected void installGraphListeners(JGraph graph) {
		// Add Listeners to Graph
		//
		// Register UndoManager with the Model
		graph.getModel().addUndoableEditListener(undoManager);
		// Update ToolBar based on Selection Changes
		graph.getSelectionModel().addGraphSelectionListener(this);
		
		graph.getSelectionModel().addGraphSelectionListener(
				(LearningUnitTableModel)learningUnitInfoTable.getModel());
		
		// Listen for Delete Keystroke when the Graph has Focus
		graph.addKeyListener(this);
		graph.getModel().addGraphModelListener(statusBar);
		
		//Adds redirector for group collapse/expand
		foldingManager = new JGraphFoldingManager();
		graph.addMouseListener(foldingManager);
	}

	// Hook for subclassers
	protected void uninstallGraphListeners(JGraph graph) {
		graph.getModel().removeUndoableEditListener(undoManager);
		graph.getSelectionModel().removeGraphSelectionListener(this);
		graph.removeKeyListener(this);
		graph.getModel().removeGraphModelListener(statusBar);
		graph.removeMouseListener(foldingManager);
	}

	/**
	 * called by <code>Unitmap</code> to inform the UI about the current
	 * user-role.
	 * 
	 * @param isAuthor
	 */
	public void userRoleChanged(boolean isAuthor)
	{
		if (!this.isVisible() && progressFrame.isVisible())
			progressFrame.showUnitmapFrame();
		
		graph.setIsAuthor(isAuthor);

		learnerButtonPanel.setVisible(!isAuthor);
		authorButtonPanel.setVisible(isAuthor);
		
		graph.setPortsVisible(isAuthor);
		toggleConnectMode(graph.isPortsVisible());
	}

	// From GraphSelectionListener Interface
	public void valueChanged(GraphSelectionEvent e) {
		// Update Button States based on Current Selection
		boolean enabled = !graph.isSelectionEmpty();
		toFrontButton.setEnabled(enabled);
		toBackButton.setEnabled(enabled);
		
		//Remove is just available for edges
		removeButton.setEnabled(enabled);
		if (enabled)
			removeButton.setEnabled( graph.getModel().isEdge(e.getCell()) );

		if (markingModeEnabled && learnerButtonPanel.isVisible())
		{
			graph.markAllPrerequisites(graph.getSelectionCells());
			toProgressViewButton.setEnabled(graph.getCurrentPrerequisiteList().size() > 0);
		}
	}	

	//
	// KeyListener for Delete KeyStroke
	//
	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		// Listen for Delete Key Press
		if (e.getKeyCode() == KeyEvent.VK_DELETE)
			// Execute Remove Action on Delete Key Press
			removeEdge();
		
	}
	
	/**
	 * brings the Specified Cells to Front
	 */
	public void toFront(Object[] c) {
		graph.getGraphLayoutCache().toFront(c);
	}

	 
	/**
	 * sends the Specified Cells to Back
	 */
	public void toBack(Object[] c) {
		graph.getGraphLayoutCache().toBack(c);
	}

	/**
	 * Undo the last Change to the Model or the View
	 */
	public void undo() {
		try {
			undoManager.undo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateHistoryButtons();
		}
	}

	/**
	 * Redo the last Change to the Model or the View
	 */
	public void redo() {
		try {
			undoManager.redo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateHistoryButtons();
		}
	}
	
	/**
	 * 	Remove Edge.
	 *  
	 *  Removing Edges is restricted to the author.
	 */
	private void removeEdge() {
		if (!graph.isSelectionEmpty() && graph.isAuthor()) {
			Object[] cells = graph.getSelectionCells();
			if (cells.length == 1)
				graph.removeEdge(cells[0]);
		}
	}

	/**
	 *  Update Undo/Redo Button State based on Undo Manager
	 */
	protected void updateHistoryButtons() {
		// The View Argument Defines the Context
		undoButton.setEnabled(undoManager.canUndo(graph.getGraphLayoutCache()));
		saveButton.setEnabled(undoButton.isEnabled());
		redoButton.setEnabled(undoManager.canRedo(graph.getGraphLayoutCache()));
	}


	//
	// Custom MarqueeHandler

	// Hook for subclassers
	protected BasicMarqueeHandler createMarqueeHandler() {
		return new MyMarqueeHandler();
	}	
	



	
	/** 
	 * creates a PopupMenu.
	 * 
	 * Currently it only offers an option to delete a selected edge
	 * in author-mode.
	 * 
	 * It maybe extended to allow loading of a learning unit or
	 * to open a window to edit the properties of a learning unit. 
	 * 
	 * @param pt where the popupmenu should show up
	 * @param cell to which the popupmenu belongs.
	 * @return a popupmenu
	 */
	public JPopupMenu createPopupMenu(final Point pt, final Object cell) {
		JPopupMenu menu = new JPopupMenu();

		// Remove
		if (!graph.isSelectionEmpty() && graph.isAuthor()) {
			//menu.addSeparator();
			menu.add(new AbstractAction(internationalization.getString("unitmap.popupmenu.remove")) {
				public void actionPerformed(ActionEvent e) {
					removeEdge();
				}
			});
		}

		return menu;
	}

	/**
	 * 
	 * @return a String representing the version of this application
	 */
	protected String getVersion() {
		return JGraph.VERSION;
	}

	
	/**
	 * @return if there are changes which need to be saved.
	 */
	public boolean isUnsaved() {
		return saveButton.isEnabled();
	}
	

	/**
	 * @return if the progress frame is visible.
	 */
	public boolean isProgressFrameVisible()
	{
		return progressFrame.isVisible();
	}
	
	/**
	 * @return Returns the graph.
	 */
	public UnitmapGraph getGraph() {
		return graph;
	}

	/**
	 * @param graph The graph to set.
	 */
	public void setGraph(UnitmapGraph graph) {
		this.graph = graph;
	}


	public class StatusBarGraphListener extends JPanel implements GraphModelListener {

		/**
		 * Graph Model change event
		 */
		public void graphChanged(GraphModelEvent e) {
			updateStatusBar();
		}

		protected void updateStatusBar(){
			
		}
	}

	/*** needs some additional work ***/
	public class EdStatusBar extends StatusBarGraphListener {
		/**
		 * 
		 */
		protected JLabel leftSideStatus;

		/**
		 * contains the scale for the current graph
		 */
		protected JLabel rightSideStatus;

		/**
		 * Constructor for GPStatusBar.
		 * 
		 */
		public EdStatusBar() {
			super();
			// Add this as graph model change listener
			setLayout(new BorderLayout());
			leftSideStatus = new JLabel(getVersion());
			rightSideStatus = new JLabel("0/0Mb");
			leftSideStatus.setBorder(BorderFactory.createLoweredBevelBorder());
			rightSideStatus.setBorder(BorderFactory.createLoweredBevelBorder());
			add(leftSideStatus, BorderLayout.CENTER);
			add(rightSideStatus, BorderLayout.EAST);
		}

		protected void updateStatusBar() {
			Runtime runtime = Runtime.getRuntime();
			int freeMemory = (int) (runtime.freeMemory() / 1024);
			int totalMemory = (int) (runtime.totalMemory() / 1024);
			int usedMemory = (totalMemory - freeMemory);
			String str = (usedMemory / 1024) + "/" + (totalMemory / 1024)
					+ "Mb";
			rightSideStatus.setText(str);
		}

		/**
		 * @return Returns the leftSideStatus.
		 */
		public JLabel getLeftSideStatus() {
			return leftSideStatus;
		}

		/**
		 * @param leftSideStatus
		 *            The leftSideStatus to set.
		 */
		public void setLeftSideStatus(JLabel leftSideStatus) {
			this.leftSideStatus = leftSideStatus;
		}

		/**
		 * @return Returns the rightSideStatus.
		 */
		public JLabel getRightSideStatus() {
			return rightSideStatus;
		}

		/**
		 * @param rightSideStatus
		 *            The rightSideStatus to set.
		 */
		public void setRightSideStatus(JLabel rightSideStatus) {
			this.rightSideStatus = rightSideStatus;
		}
	}

	// MarqueeHandler that Connects Vertices and Displays PopupMenus
	public class MyMarqueeHandler extends BasicMarqueeHandler {

		// Holds the Start and the Current Point
		protected Point2D start, current;

		// Holds the First and the Current Port
		protected PortView port, firstPort;

		// Override to Gain Control (for PopupMenu and ConnectMode)
		public boolean isForceMarqueeEvent(MouseEvent e) {
			if (e.isShiftDown())
				return false;
			// If Right Mouse Button we want to Display the PopupMenu
			if (SwingUtilities.isRightMouseButton(e))
				// Return Immediately
				return true;
			// Find and Remember Port
			port = getSourcePortAt(e.getPoint());
			// If Port Found and in ConnectMode (=Ports Visible)
			if (port != null && graph.isPortsVisible())
				return true;
			// Else Call Superclass
			return super.isForceMarqueeEvent(e);
		}

		// Display PopupMenu or Remember Start Location and First Port
		public void mousePressed(final MouseEvent e) {
			// If Right Mouse Button
			if (SwingUtilities.isRightMouseButton(e)) {
				// Find Cell in Model Coordinates
				Object cell = graph.getFirstCellForLocation(e.getX(), e.getY());
				// Create PopupMenu for the Cell
				JPopupMenu menu = createPopupMenu(e.getPoint(), cell);
				// Display PopupMenu
				menu.show(graph, e.getX(), e.getY());
				// Else if in ConnectMode and Remembered Port is Valid
			} else if (port != null && graph.isPortsVisible()) {
				// Remember Start Location
				start = graph.toScreen(port.getLocation());
				// Remember First Port
				firstPort = port;
			} else {
				// Call Superclass
				super.mousePressed(e);
			}
		}

		// Find Port under Mouse and Repaint Connector
		public void mouseDragged(MouseEvent e) {
			// If remembered Start Point is Valid
			if (start != null) {
				// Fetch Graphics from Graph
				Graphics g = graph.getGraphics();
				// Reset Remembered Port
				PortView newPort = getTargetPortAt(e.getPoint());
				// Do not flicker (repaint only on real changes)
				if (newPort == null || newPort != port) {
					// Xor-Paint the old Connector (Hide old Connector)
					paintConnector(Color.black, graph.getBackground(), g);
					// If Port was found then Point to Port Location
					port = newPort;
					if (port != null)
						current = graph.toScreen(port.getLocation());
					// Else If no Port was found then Point to Mouse Location
					else
						current = graph.snap(e.getPoint());
					// Xor-Paint the new Connector
					paintConnector(graph.getBackground(), Color.black, g);
				}
			}
			// Call Superclass
			super.mouseDragged(e);
		}

		public PortView getSourcePortAt(Point2D point) {
			// Disable jumping
			graph.setJumpToDefaultPort(false);
			PortView result;
			try {
				// Find a Port View in Model Coordinates and Remember
				result = graph.getPortViewAt(point.getX(), point.getY());
			} finally {
				graph.setJumpToDefaultPort(true);
			}
			return result;
		}

		// Find a Cell at point and Return its first Port as a PortView
		protected PortView getTargetPortAt(Point2D point) {
			// Find a Port View in Model Coordinates and Remember
			return graph.getPortViewAt(point.getX(), point.getY());
		}

		// Connect the First Port and the Current Port in the Graph or Repaint
		public void mouseReleased(MouseEvent e) {
			// If Valid Event, Current and First Port
			if (e != null && port != null && firstPort != null
					&& firstPort != port) {
				// Then Establish Connection
				graph.connect((Port) firstPort.getCell(), (Port) port.getCell());
				// in case connect fails repaint() needs to be called here
				graph.repaint();
				
				e.consume();
			// Else Repaint the Graph
			} else
				graph.repaint();
			// Reset Global Vars
			firstPort = port = null;
			start = current = null;
			// Call Superclass
			super.mouseReleased(e);
		}

		// Show Special Cursor if Over Port
		public void mouseMoved(MouseEvent e) {
			// Check Mode and Find Port
			if (e != null && getSourcePortAt(e.getPoint()) != null
					&& graph.isPortsVisible()) {
				// Set Cusor on Graph (Automatically Reset)
				graph.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// Consume Event
				// Note: This is to signal the BasicGraphUI's
				// MouseHandle to stop further event processing.
				e.consume();
			} else
				// Call Superclass
				super.mouseMoved(e);
		}

		// Use Xor-Mode on Graphics to Paint Connector
		protected void paintConnector(Color fg, Color bg, Graphics g) {
			// Set Foreground
			g.setColor(fg);
			// Set Xor-Mode Color
			g.setXORMode(bg);
			// Highlight the Current Port
			paintPort(graph.getGraphics());
			// If Valid First Port, Start and Current Point
			if (firstPort != null && start != null && current != null)
				// Then Draw A Line From Start to Current Point
				g.drawLine((int) start.getX(), (int) start.getY(),
						(int) current.getX(), (int) current.getY());
		}

		// Use the Preview Flag to Draw a Highlighted Port
		protected void paintPort(Graphics g) {
			// If Current Port is Valid
			if (port != null) {
				// If Not Floating Port...
				boolean o = (GraphConstants.getOffset(port.getAllAttributes()) != null);
				// ...Then use Parent's Bounds
				Rectangle2D r = (o) ? port.getBounds() : port.getParentView()
						.getBounds();
				// Scale from Model to Screen
				r = graph.toScreen((Rectangle2D) r.clone());
				// Add Space For the Highlight Border
				r.setFrame(r.getX() - 3, r.getY() - 3, r.getWidth() + 6, r
						.getHeight() + 6);
				// Paint Port in Preview (=Highlight) Mode
				graph.getUI().paintCell(g, port, r, true);
			}
		}

	} // End of Editor.MyMarqueeHandler	
	
	// This will change the source of the actionevent to graph.
	public class EventRedirector extends AbstractAction {

		protected Action action;

		// Construct the "Wrapper" Action
		public EventRedirector(Action a) {
			super("", (ImageIcon) a.getValue(Action.SMALL_ICON));
			this.action = a;
		}

		// Redirect the Actionevent
		public void actionPerformed(ActionEvent e) {
			e = new ActionEvent(graph, e.getID(), e.getActionCommand(), e
					.getModifiers());
			action.actionPerformed(e);
		}
	}	
}
