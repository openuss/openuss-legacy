package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.media.bean.playerbean.*;
import javax.swing.*;
import javax.swing.border.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElementGridObject;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGDialogInputVerifier;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.plotter.FLGBarChart2D;
import freestyleLearningGroup.independent.plotter.FLGData2D;
import freestyleLearningGroup.independent.plotter.FLGDataGroup2D;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FLGSelectorMainPanelManager.
 * Manager Class for selector Main Panel in Selector Game Mode.
 * @author Carsten Fiedler
 */
public class FLGSelectorMainContentPanel extends FSLAbstractLearningUnitViewElementContentPanel {
    private FLGInternationalization internationalization;
    private boolean isModifiedByUserInput = false;
    private boolean playModeActivated = false;
    private boolean userRoleIsAuthor;
    private boolean multiPlayerMode = false; 
    private boolean lastMultiPlayerRun = false;
    private int playGroundWidth;
    private int playGroundHeight;
    private int gameSpeed;
    private double gameLength;
    private int correctClicksCounter;
    private int wrongClicksCounter;
    private int notClickedCounter;
    private int scaleHeight;
    private int scaleWidth;
    private String currentPlayer;
    private JPanel playGroundPanel;
    private JLabel headerLabel;
    private String activeLearningUnitViewElementId;
    private FLGSelectorElement currentSelectorElement;
    private java.util.List gridObjectList;
    private javax.swing.Timer playTimer, stopTimer;
    private Hashtable occupiedIndexHash = new Hashtable();  
    private java.applet.AudioClip wrongAudioClip;
    private java.applet.AudioClip correctAudioClip;
    private ArrayList wrongElements = new ArrayList();
    private ArrayList correctElements = new ArrayList();
    private ArrayList notClickedElements = new ArrayList();
    private ArrayList playerList = new ArrayList();
    private ArrayList multiPlayerResultList = new ArrayList();
    private MediaPlayer mediaPlayer;
    private AudioClip successClip;
    private AudioClip failureClip;
    private AudioClip evaluationAudioClip;
    
    /**
     * Inits Selector (playground) main content panel.
     * @param FSLLearningUnitViewManager learningUnitViewManager
     * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
     * @param boolean editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
    FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager,learningUnitEventGenerator,editMode);
        learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorLearningUnitViewAdapter());
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel.internationalization",
        getClass().getClassLoader());
        wrongAudioClip = java.applet.Applet.newAudioClip(
        		getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/sounds/wrongSound.wav"));
        correctAudioClip = java.applet.Applet.newAudioClip(
        		getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/sounds/correctSound.wav"));
        mediaPlayer = new MediaPlayer();
        
        // init evaluation sounds
        // success
    	successClip = java.applet.Applet.newAudioClip(
        		getClass().getClassLoader().getResource("" +
        				"freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/sounds/evalSuccess.wav"));
    	// failure
		failureClip = java.applet.Applet.newAudioClip(
        		getClass().getClassLoader().getResource(
        				"freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/sounds/evalFailure.wav"));
		// default sound
	    evaluationAudioClip = java.applet.Applet.newAudioClip(
        		getClass().getClassLoader().getResource(
        				"freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/sounds/evalSound.wav"));
    }
    
    /**
     * Gets Edit-Toolbar. Inherited method has to be overwritten,
     * but is not needed in game mode.
     * @return null
     */
    protected JComponent[] getEditToolBarComponents() {
        return null;
    }
    
    /**
     * Returns if Content Panel has been modified by user input.
     * @return boolean
     */
    public boolean isModifiedByUserInput() {
        return isModifiedByUserInput;
    }
    
    /**
     * Saves User Changes. Method has to be implemented,
     * but is not needed because user does not change settings in play mode.
     */
    public void saveUserChanges() {
    }
    
    /**
     * Builds independent UI.
     * Sets layout and panel for presenting game title.
     */
    protected void buildIndependentUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 241, 245));
        headerLabel = new JLabel();
        headerLabel.setBorder(new EmptyBorder(5,5,5,5));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setOpaque(false);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(headerLabel,BorderLayout.NORTH);
        playGroundPanel = new JPanel();
        playGroundPanel.setOpaque(false);
    }
    
    /**
     * Builds dependent UI.
     * Adds grid objects into content panel.
     */
    protected void buildDependentUI(boolean reloadIfAlreadyLoaded) {
        playGroundPanel.removeAll();
        String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
        learningUnitViewElementsManager = learningUnitViewManager.getLearningUnitViewElementsManager();
        FLGSelectorElement viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewElement(
        		activeLearningUnitViewElementId,false);
        if (viewElement!=null && playModeActivated) {
            // set game title
            headerLabel.setText(viewElement.getTitle());
            // set layout for game panel
            playGroundPanel.setLayout(new GridLayout(playGroundWidth, playGroundHeight));
            playGroundPanel.setBorder(new EmptyBorder(5,5,5,5));
            // add panels for grid objects
            for (int i=0; i<playGroundHeight; i++) {
                for (int j=0; j<playGroundWidth; j++) {
                    JPanel gridPanel = new JPanel();
                    gridPanel.setOpaque(false);
                    gridPanel.setBorder(BorderFactory.createLineBorder(FLGUIUtilities.BASE_COLOR4,1));
                    playGroundPanel.add(gridPanel);
                }
            }
            add(playGroundPanel);
        }
    }
  
    /**
     * Scales images.
     * @param imageFileId
     * @param scaleHeight
     * @param scaleWidth
     * @return Image scaledImage
     */
    private Image scaleImage(String imageFileId, int scaleHeight, int scaleWidth) {
    	Image scaledImage;
    	File f;
        String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
        FLGSelectorElement viewElement = null;
        if (userRoleIsAuthor) {
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                	activeLearningUnitViewElementId);
    		f = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() + "/" + imageFileId);
    	} else {
    		// check for user element
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
        		activeLearningUnitViewElementId);
       		// if no user element
    		if(viewElement==null) {
    			// get original element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
        		f = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() + "/" + imageFileId);
    		} else {
    			f = new File(learningUnitViewManager.getLearningUnitViewUserDirectory() + "/" + imageFileId);
    		}
    	} 
		URL u = null;
		try {
			u = f.toURL();
		} catch (Exception e) { e.printStackTrace();}
    	Image imageToScale = FLGImageUtility.loadImageAndWait(u);
    	Image newImage = imageToScale;
    	ImageObserver observer = new ImageObserver() {
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
        int height = newImage.getHeight(observer);
        int width  = newImage.getWidth(observer);
		if(imageToScale.getHeight(observer) >= imageToScale.getWidth(observer)) {
			scaledImage = imageToScale.getScaledInstance(-1, scaleHeight, Image.SCALE_SMOOTH);
		} else {
	    	scaledImage = imageToScale.getScaledInstance(scaleWidth, -1, Image.SCALE_SMOOTH);
	    }
		return scaledImage;
    }
    
    private void fireTimeoutEvent() {
    	if (multiPlayerMode) {
    		FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(lastMultiPlayerRun,
    				FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_TIMEOUT);
    		learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
    	} 
    	if (!multiPlayerMode) {
    		// fire game ended event to disable music and to modify play button and stop button
    		FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_PLAY_MODE_TIMEOUT);
    		learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
    	}
    }
    
    private void displayNextElement() {
        // get random grid object from list
        int randomListIndex = createRandomGridObjectListIndex();
        FLGSelectorElementGridObject randomGridObject = (FLGSelectorElementGridObject) gridObjectList.get(randomListIndex);
        final FLGSelectorElementGridObject finalRandomGridObject = randomGridObject;
        // check, if index is free
        // create random panel position
        int gridObjectPosition = createRandomGridObjectIndex();
   	    while (occupiedIndexHash.containsKey(Integer.toString(gridObjectPosition))) {
        	gridObjectPosition = createRandomGridObjectIndex();
   		}
        final int finalGridObjectPosition = gridObjectPosition;
        occupiedIndexHash.put(Integer.toString(gridObjectPosition),Long.toString(System.currentTimeMillis()));
        JGridPanel randomGridObjectPanel = new JGridPanel(randomGridObject,gridObjectPosition, new BorderLayout());
        randomGridObjectPanel.setOpaque(false);
        randomGridObjectPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 122),5));
        if (randomGridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT)) {
            JLabel label = new JLabel(randomGridObject.getText(),JLabel.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 18));
            randomGridObjectPanel.add(label,BorderLayout.CENTER);
            randomGridObjectPanel.addMouseListener(new GridObjectSelectionMouseAdapter());
        }
        else {
          	scaleHeight = playGroundPanel.getComponent(0).getSize().height-10;
          	scaleWidth = playGroundPanel.getComponent(0).getSize().width-10;
        	randomGridObjectPanel.add(new JLabel(new ImageIcon(
        			scaleImage(randomGridObject.getId(),scaleHeight,scaleWidth))),BorderLayout.CENTER);
            randomGridObjectPanel.addMouseListener(new GridObjectSelectionMouseAdapter());
        }        
        playGroundPanel.remove(gridObjectPosition);
        playGroundPanel.add(randomGridObjectPanel,gridObjectPosition);
        //playGroundPanel.updateUI();
        playGroundPanel.validate();
        javax.swing.Timer cleanTimer = new javax.swing.Timer(gameSpeed*3000, new ActionListener() {  
            public void actionPerformed(ActionEvent e) {
            	// check, if grid object is still displayed before deleting
                if (!occupiedIndexHash.isEmpty()) { 
                	if (occupiedIndexHash.get(Integer.toString(finalGridObjectPosition))!=null) {
                		if (System.currentTimeMillis()
                			-Long.parseLong((String) occupiedIndexHash.get(Integer.toString(finalGridObjectPosition)))>=gameSpeed*3000) {
                			occupiedIndexHash.remove(Integer.toString(finalGridObjectPosition));
                				if (playModeActivated) {
                					playGroundPanel.remove(finalGridObjectPosition);
	                				JPanel emptyPanel = new JPanel();
	                				emptyPanel.setOpaque(false);
	                				emptyPanel.setBorder(BorderFactory.createLineBorder(FLGUIUtilities.BASE_COLOR4,1));
	                				playGroundPanel.add(emptyPanel,finalGridObjectPosition);
	                				playGroundPanel.validate();
	                				if(finalRandomGridObject.getClickAllowed()) {
	                					notClickedCounter++;
	                					notClickedElements.add(finalRandomGridObject);
	                				}
                				}
                		}
                	}
                }
            }
        });
        cleanTimer.setRepeats(false);
        cleanTimer.start();
    }
    
    private void playMusic(File musicFile) {
    	try {
    		if (musicFile!=null) {
    			mediaPlayer.setMediaLocation((musicFile.toURL()).toString());
    	        mediaPlayer.setPlaybackLoop(true);
    			mediaPlayer.start();
    		}
    		
    	} catch(Exception e) {
    		System.out.println("FLGSelectorMainContentPanel: Error while playing music.");
    	}
    }
    
    /**
     * Starts game.
     */
    public void startGame() {
        // get all grid objects to selected view element
        activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
        learningUnitViewElementsManager = learningUnitViewManager.getLearningUnitViewElementsManager();
        currentSelectorElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewElement(
        		activeLearningUnitViewElementId,false);
        gridObjectList = currentSelectorElement.getLearningUnitViewElementGridObjects();
    	// load music
        File musicFile = null;
        if (currentSelectorElement.getMusicFileName()!=null && !currentSelectorElement.getMusicFileName().equals("")) {
        	musicFile = learningUnitViewElementsManager.resolveRelativeFileName(currentSelectorElement.getMusicFileName(),
        		currentSelectorElement);
        }
        if (musicFile!=null) {
         	playMusic(musicFile);
        } else {
        	// System.out.println("FLGSelectorMainContentPanel: Music is null!");
        }
        // prepare timer tasks
        if (playGroundWidth<=3) {
	        playTimer = new javax.swing.Timer(gameSpeed*500, new ActionListener() {  
	            public void actionPerformed(ActionEvent e) {
	       			displayNextElement();
	            }
	        });
    	} else {
    		playTimer = new javax.swing.Timer(gameSpeed*250, new ActionListener() {  
	            public void actionPerformed(ActionEvent e) {
	       			displayNextElement();
	            }
	        });
    	}
        stopTimer = new javax.swing.Timer(((int)(gameLength*2))*60*500, new ActionListener() {  
            public void actionPerformed(ActionEvent e) {
                fireTimeoutEvent();
            }
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
        playTimer.start();
    }
    
    private void startMultiPlayerGame() {
    	// get random player
    	currentPlayer = getRandomPlayer();
    	// open message window to present first players name
    	JPanel playerPanel = new JPanel(new BorderLayout());
    	// player name label
    	JLabel playerLabel = new JLabel((internationalization.getString("selector.mainContentPanel.multiPlayerMode_playerLabel") + " " +
    			currentPlayer + "!"),JLabel.CENTER);
    	playerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    	playerPanel.add(playerLabel, BorderLayout.NORTH);
    	// image label
    	JLabel imageLabel = new JLabel(new ImageIcon(
    			FLGImageUtility.loadImageAndWait(
    					(getClass().getClassLoader().getResource("" +
    					"freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/images/zielflagge.gif")))
    	));
    	imageLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        playerPanel.add(imageLabel,BorderLayout.CENTER);
    	// ready steady label		
    	JLabel goodLuckLabel = new JLabel(
    			internationalization.getString("selector.mainContentPanel.multiPlayerMode_goodLuck"),JLabel.CENTER);
    	goodLuckLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    	playerPanel.add(goodLuckLabel, BorderLayout.SOUTH);
    	// open option pane
    	String[] buttonLabel = {internationalization.getString("selector.mainContentPanel.multiPlayerMode_dialogStartButton")};
    	new FLGOptionPane(null,playerPanel,internationalization.getString("selector.mainContentPanel.multiPlayerMode_dialogTitle"),
    			FLGOptionPane.OK_OPTION,FLGOptionPane.PLAIN_MESSAGE,buttonLabel).setVisible(true);
    	FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(
    			FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_UPADTE_STATUS_PANEL,playGroundWidth, playGroundHeight, gameSpeed, gameLength);
		learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
    	buildDependentUI(true);
        startGame();
    }
    
    /**
     * Stops game, opens result panel.
     */
    public void stopGame() {
    	if (mediaPlayer.getMediaLocation()!=null) {
    		mediaPlayer.stop();
    		mediaPlayer.setPlaybackLoop(false);
    	}
        playTimer.stop();
        stopTimer.stop();
        occupiedIndexHash.clear();
        playGroundPanel.removeAll();
        playGroundPanel.setOpaque(false);
        headerLabel.setText("");
       	// if full screen mode, deactive it 
       	FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
            activeLearningUnitViewElementId, false);
       	learningUnitViewManager.fireLearningUnitViewEvent(event);
    }
    
    private void showMultiPlayerResults() {
    	// open window with click results
        JPanel evaluationPanel = new JPanel(new BorderLayout());
        evaluationPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("selector.mainContentPanel.resultDialog.headerLabel") 
            	+ " " + currentSelectorElement.getTitle()));
        // create panel with player names and bar chart
        JPanel playerPanel = new JPanel(new FLGColumnLayout());
        playerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        FLGDataGroup2D data = new FLGDataGroup2D();
        for (int i=0;i<multiPlayerResultList.size();i++) {
        	MultiPlayerResult result = (MultiPlayerResult)multiPlayerResultList.get(i);
    		int points = result.getRightClicks()*3 - result.getWrongClicks()*2 - result.getMissedClicks();
    		if (points<0) {points=0;}
    		data.add(new FLGData2D(result.getPlayerName() + ": " + points + " " +
    				internationalization.getString("selector.mainContentPanel.multiPlayerResultDialog.points"),points,java.awt.Color.blue));
        }
        // add player panel into evaluation panel
        evaluationPanel.add(playerPanel,BorderLayout.NORTH);
        // add bar chart
      	FLGBarChart2D barchart = new FLGBarChart2D(data, "", 
					internationalization.getString("selector.mainContentPanel.multiPlayerResultDialog.barChartYaxis"));
		barchart.setPreferredSize(new Dimension(300,300));
		barchart.setPaintWithGradient(true);
		evaluationPanel.add(barchart,BorderLayout.CENTER);
        // play sound
	    evaluationAudioClip.play();
	    // open dialog
        FLGOptionPane.showConfirmDialog(new JScrollPane(evaluationPanel),
        		internationalization.getString("selector.mainContentPanel.multiPlayerResultDialog.headerLabel"),
        		FLGOptionPane.OK_OPTION, FLGOptionPane.PLAIN_MESSAGE);
   	 	multiPlayerResultList.clear();
    	FSLLearningUnitViewEvent sEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_TIMEOUT);
    	learningUnitViewManager.fireLearningUnitViewEvent(sEvent);
    }
    
    private void showResults() {
        // open window with click results
        JPanel evaluationPanel = new JPanel(new BorderLayout());
        evaluationPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("selector.mainContentPanel.resultDialog.headerLabel") 
            	+ " " + currentSelectorElement.getTitle()));
        // create panel with details
        JPanel resultPanel = new JPanel(new FLGColumnLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        JLabel correctClicksLabel = new JLabel(internationalization.getString("selector.mainContentPanel.resultDialog.correctClicksLabel")
        		+ " " + Integer.toString(correctClicksCounter));
        resultPanel.add(correctClicksLabel,FLGColumnLayout.CENTER);
        JLabel wrongClicksLabel = new JLabel(internationalization.getString("selector.mainContentPanel.resultDialog.wrongClicksLabel")
        		+ " " + Integer.toString(wrongClicksCounter));
        resultPanel.add(wrongClicksLabel,FLGColumnLayout.CENTER);
        JLabel notClickedLabel = new JLabel(internationalization.getString("selector.mainContentPanel.resultDialog.notClickedLabel")
        		+ " " + Integer.toString(notClickedCounter));
        resultPanel.add(notClickedLabel,FLGColumnLayout.CENTEREND);
        // create list with all correct answers
        String[] correctAnswersArray = new String[correctElements.size()];
        for (int i=0; i<correctElements.size();i++) {
        	FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) correctElements.get(i);
        	if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT)){
        	   	correctAnswersArray[i]=gridObject.getText();
        	} else {
        		correctAnswersArray[i]=gridObject.getImageFileName();
        	}
        }
        JList resultList = new JList(correctAnswersArray);
        //resultList.setVisibleRowCount(10);
        resultList.setPreferredSize(new Dimension(200,200));
        resultPanel.add(new JScrollPane(resultList),FLGColumnLayout.CENTER);
        // create list with all wrong answers
        String[] wrongAnswersArray = new String[wrongElements.size()];
        for (int i=0; i<wrongElements.size();i++) {
        	FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) wrongElements.get(i);
        	if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT)){
        		wrongAnswersArray[i]=gridObject.getText();
        	} else {
        		wrongAnswersArray[i]=gridObject.getImageFileName();
        	}
        }
        resultList = new JList(wrongAnswersArray);
        //resultList.setVisibleRowCount(10);
        resultList.setPreferredSize(new Dimension(200,200));
        resultPanel.add(new JScrollPane(resultList),FLGColumnLayout.CENTER);
        // create list with not clicked elements
        String[] forgottenAnswersArray = new String[notClickedElements.size()];
        for (int i=0; i<notClickedElements.size();i++) {
        	FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) notClickedElements.get(i);
        	if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT)){
        		forgottenAnswersArray[i]=gridObject.getText();
        	} else {
        		forgottenAnswersArray[i]=gridObject.getImageFileName();
        	}
        }
        resultList = new JList(forgottenAnswersArray);
        //resultList.setVisibleRowCount(10);
        resultList.setPreferredSize(new Dimension(200,200));
        resultPanel.add(new JScrollPane(resultList),FLGColumnLayout.CENTEREND);
        // add result panel into evaluation panel
        evaluationPanel.add(resultPanel,BorderLayout.NORTH);
        // build bar chart 
        if(correctElements.size()==0 && wrongElements.size()==0 && notClickedElements.size()==0){
        	// do not open barchart
        } else {
	        FLGDataGroup2D data = new FLGDataGroup2D();
	        data.add(new FLGData2D(internationalization.getString("selector.mainContentPanel.resultDialog.barChartCorrectAnswers"), 
					correctElements.size(), java.awt.Color.green));
	        data.add(new FLGData2D(internationalization.getString("selector.mainContentPanel.resultDialog.barChartWrongAnswers"), 
					wrongElements.size(), java.awt.Color.red));
	        data.add(new FLGData2D(internationalization.getString("selector.mainContentPanel.resultDialog.barChartNotClickedAnswers"), 
					notClickedElements.size(),java.awt.Color.blue));
			FLGBarChart2D barchart = new FLGBarChart2D(data, "", 
					internationalization.getString("selector.mainContentPanel.resultDialog.barChartYaxis"));
			barchart.setPreferredSize(new Dimension(300,300));
			barchart.setPaintWithGradient(true);
			evaluationPanel.add(barchart,BorderLayout.CENTER);
        }
        
        // play sounds
        if(correctElements.size()>wrongElements.size()+notClickedElements.size()) {
			// success
        	successClip.play();
        } 
		if (correctElements.size()==wrongElements.size() && notClickedElements.size()==0) {
			// play default sound
		    evaluationAudioClip.play();
		}
		if((correctElements.size()<wrongElements.size()) || (correctElements.size()==wrongElements.size() && notClickedElements.size()>0)) {
			// failure
			//failureClip.play();
		}
		
		// calculate sum
		int sum = correctClicksCounter*3 - wrongClicksCounter*2 - notClickedCounter;
		if (sum<0) {sum=0;}
		JLabel sumLabel = new JLabel(internationalization.getString("selector.mainContentPanel.resultDialog.sumLabel")
				+ " " + Integer.toString(sum),JLabel.CENTER);
		sumLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		evaluationPanel.add(sumLabel,BorderLayout.SOUTH);
		
		// open dialog
        FLGOptionPane.showConfirmDialog(new JScrollPane(evaluationPanel),
        		internationalization.getString("selector.mainContentPanel.resultDialog.tilte"),
        		FLGOptionPane.OK_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        
    }
    
    private void resetCounters() {
        // delete correct and wrong answer buffers...
        correctClicksCounter = 0;
        wrongClicksCounter = 0;
        notClickedCounter = 0;
        wrongElements.clear();
        correctElements.clear();
        notClickedElements.clear();
    }
    
    /**
     * Loads grid object image.
     * @param String imageFileUrl
     * @return Image grid object image
     */
    public Image loadImage(String imageFileUrl) {
        File f;
        String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
        FLGSelectorElement viewElement = null;
        if (userRoleIsAuthor) {
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                	activeLearningUnitViewElementId);
    		f = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() + "/" + imageFileUrl);
    	} else {
    		// check for user element
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
        		activeLearningUnitViewElementId);
       		// if no user element
    		if(viewElement==null) {
    			// get original element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
        		f = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() + "/" + imageFileUrl);
    		} else {
    			f = new File(learningUnitViewManager.getLearningUnitViewUserDirectory() + "/" + imageFileUrl);
    		}
    	} 
        URL u = null;
        try {
            u = f.toURL();
        } catch (Exception e) { e.printStackTrace();}
        return FLGImageUtility.loadImageAndWait(u);
    }
    
    private int createRandomGridObjectIndex() {
        Random r1 = new Random();
        int index = (int)(r1.nextDouble() * playGroundWidth * playGroundHeight);
        return index;
    }
    
    private int createRandomGridObjectListIndex() {
        Random r1 = new Random();
        int index = (int)(r1.nextDouble() * currentSelectorElement.getLearningUnitViewElementGridObjects().size());
        return index;
    }
    
    private String getRandomPlayer() {
        Random r1 = new Random();
        int index = (int)(r1.nextDouble() * playerList.size());
        String player = (String) playerList.get(index);
        // remove player in player list
        playerList.remove(index);
        return player;
    }
    
    private void storeResults() {
    	multiPlayerResultList.add(
    			new MultiPlayerResult(currentPlayer,correctClicksCounter,wrongClicksCounter,notClickedCounter));
    	playerList.remove(currentPlayer);
    }
    
    /**
     * JGridPanel.
     * Inner class to store grid object instance
     * and its index in playground panel.
     */
    public class JGridPanel extends JPanel {
        private FLGSelectorElementGridObject gridObject;
        private int playGroundPosition;
        JGridPanel(FLGSelectorElementGridObject gridObject, int playGroundPosition, LayoutManager layoutManager) {
            super(layoutManager);
            this.gridObject = gridObject;
            this.playGroundPosition = playGroundPosition;
        }
        public FLGSelectorElementGridObject getGridObject() {
            return gridObject;
        }
        public int getplayGroundPosition() {
            return playGroundPosition;
        }
    }
    
    /**
     * GridObjectSelectionMouseAdapter.
     * Class manages mouse clicks on list,
     * presents green or red rectangle for 
     * right or wrong mouse click on grid object.
     */
    public class GridObjectSelectionMouseAdapter extends MouseAdapter {
       public void mousePressed(MouseEvent e) {
            JGridPanel gridPanel = ((JGridPanel)(e.getSource()));
            FLGSelectorElementGridObject selectedGridObject = gridPanel.getGridObject();
            if (selectedGridObject.getClickAllowed()) {
                correctClicksCounter++;
                // store object id in list wiht correct answers
                correctElements.add(selectedGridObject);
             	// play sound 
                correctAudioClip.play();
                // show green rectangle
                JPanel correctClickPanel = new JPanel(new BorderLayout());
                correctClickPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,10));
                if (selectedGridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_IMAGE)) {
                	correctClickPanel.add(new JLabel(new ImageIcon(scaleImage(selectedGridObject.getId(),scaleHeight,scaleWidth))),BorderLayout.CENTER);
                } else {
                	JLabel label = new JLabel(selectedGridObject.getText(),JLabel.CENTER);
                    label.setFont(new Font("SansSerif", Font.BOLD, 18));
                    correctClickPanel.add(label,BorderLayout.CENTER);
                }
                // remove object from playground
                playGroundPanel.remove(gridPanel.getplayGroundPosition());
                playGroundPanel.add(correctClickPanel,gridPanel.getplayGroundPosition());
                //playGroundPanel.updateUI();
                playGroundPanel.validate();
                final int finalGridObjectPosition = gridPanel.getplayGroundPosition();
                javax.swing.Timer cleanTimer = new javax.swing.Timer(150, new ActionListener() {  
                    public void actionPerformed(ActionEvent e) {
                        if (!occupiedIndexHash.isEmpty()) {
                        	occupiedIndexHash.remove(Integer.toString(finalGridObjectPosition));
                        	playGroundPanel.remove(finalGridObjectPosition);
                        	JPanel emptyPanel = new JPanel();
                        	emptyPanel.setOpaque(false);
                        	emptyPanel.setBorder(BorderFactory.createLineBorder(FLGUIUtilities.BASE_COLOR4,1));
                        	playGroundPanel.add(emptyPanel,finalGridObjectPosition);
                        	//playGroundPanel.updateUI();
                        	playGroundPanel.validate();
                    	}
                    }
                });
                cleanTimer.setRepeats(false);
                cleanTimer.start();
            } else {
                wrongClicksCounter++;
                // store object id
                wrongElements.add(selectedGridObject);
                // play sound for wrong click
                wrongAudioClip.play();
                // show special effect for 1 second :-)
                JPanel wrongClickPanel = new JPanel();
                wrongClickPanel.setBorder(BorderFactory.createLineBorder(Color.RED,10));
                wrongClickPanel.add(new JLabel(new ImageIcon(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource(
                	"freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/elementsContentsPanel/images/ausrufezeichen4.gif")))));
                playGroundPanel.remove(gridPanel.getplayGroundPosition());
                playGroundPanel.add(wrongClickPanel,gridPanel.getplayGroundPosition());
                //playGroundPanel.updateUI();
                playGroundPanel.validate();
                final int finalGridObjectPosition = gridPanel.getplayGroundPosition();
                javax.swing.Timer cleanTimer = new javax.swing.Timer(500, new ActionListener() {  
                    public void actionPerformed(ActionEvent e) {
                        if (!occupiedIndexHash.isEmpty()) {
                        	occupiedIndexHash.remove(Integer.toString(finalGridObjectPosition));
                        	playGroundPanel.remove(finalGridObjectPosition);
                        	JPanel emptyPanel = new JPanel();
                        	emptyPanel.setOpaque(false);
                        	emptyPanel.setBorder(BorderFactory.createLineBorder(FLGUIUtilities.BASE_COLOR4,1));
                        	playGroundPanel.add(emptyPanel,finalGridObjectPosition);
                        	//playGroundPanel.updateUI();
                        	playGroundPanel.validate();
                    	}
                    }
                });
                cleanTimer.setRepeats(false);
                cleanTimer.start();
           }
       }
    }
    
    /**
     * FLGSelectorLearningUnitViewAdapter.
     * Inner class for learning unit view event handling.
     */
    class FLGSelectorLearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
    	public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
	    	if (mediaPlayer.getMediaLocation()!=null) {
	    		mediaPlayer.stop();
	    		mediaPlayer.setPlaybackLoop(false);
	    	}
	    	if (playTimer!=null) {
	    		playTimer.stop();
	    		stopTimer.stop();
	    	}
            // reset flags
            playModeActivated = false;
            lastMultiPlayerRun = false;
            multiPlayerMode = false;
            // reset lists
	        resetCounters();
	        if (learningUnitViewManager.getActiveLearningUnitViewElementId()!=null) {
	        	buildDependentUI(true);
	        }
    	}
    	
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            switch (selectorEvent.getEventSpecificType()) {
                case FLGSelectorEvent.SELECTOR_PLAY_MODE_ENTERED: {
                	playGroundWidth = selectorEvent.getWidth();
                    playGroundHeight = selectorEvent.getHeight();
                    gameSpeed = selectorEvent.getGameSpeed();
                    gameLength = selectorEvent.getGameLength();
                    playModeActivated = true;
               	 	multiPlayerMode = false;
                    buildDependentUI(true);
                    startGame();
                    break;
                }
                case FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_ENTERED: {
                    playGroundWidth = selectorEvent.getWidth();
                    playGroundHeight = selectorEvent.getHeight();
                    gameSpeed = selectorEvent.getGameSpeed();
                    gameLength = selectorEvent.getGameLength();
                    // get players
                	Object[] tmpArray = selectorEvent.getPlayers(); 
                    for (int i=0; i<tmpArray.length; i++){
                    	playerList.add(tmpArray[i]);
                    }
                    if (!playerList.isEmpty()) {
                    	if (playerList.size()==1) {
                    		lastMultiPlayerRun = true;
                    	} else {
                    		lastMultiPlayerRun = false;
                    	}
                        multiPlayerMode = true;
                        playModeActivated = true;
                        buildDependentUI(true);
                    	startMultiPlayerGame();
                    } else {
                    	// open option pane
                        FLGOptionPane.showConfirmDialog(internationalization.getString("selector.mainContentPanel.multiPlayerMode_noUsers.text"),
                                internationalization.getString("selector.mainContentPanel.multiPlayerMode_noUsers.title"),
                                FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
                        stopGame();
                        resetCounters();
                    }
                    break;
                }
                case FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_EXITED: {
                	if (playerList.size()==1) { lastMultiPlayerRun=true; } 
                    playModeActivated = false;
                    stopGame();
                    showResults();
                    storeResults();
                    resetCounters();
                    if (!playerList.isEmpty() && !multiPlayerResultList.isEmpty()) {
                        playModeActivated = true;
                        buildDependentUI(true);
                        if (playerList.size()==1) { lastMultiPlayerRun=true; }
                        startMultiPlayerGame();
                    } else {
                    	 playModeActivated = false;
                    	 lastMultiPlayerRun = false;
                    	 showMultiPlayerResults();
                    }
                    break;
                }
                case FLGSelectorEvent.SELECTOR_PLAY_MODE_EXITED: {
                	stopGame();
	                if (!multiPlayerMode) {
	                	showResults();
	                }
	                // reset flags
	                playModeActivated = false;
		            lastMultiPlayerRun = false;
	                multiPlayerMode = false;
		            resetCounters();
	                break;
                }
                case FLGSelectorEvent.SELECTOR_USER_ROLE_CHANGED: {
                	userRoleIsAuthor=selectorEvent.getUserRole();
                	break;
                }
            }
        }
    }
    
    /**
     * Inner class to store multi player results.
     */
    public class MultiPlayerResult {
    	private String playerName;
    	private int rightClicks;
    	private int wrongClicks;
    	private int missedClicks;
    	
    	MultiPlayerResult(String playerName, int rightClicks, int wrongClicks, int missedClicks) {
    		this.playerName = playerName;
    		this.rightClicks = rightClicks;
    		this.wrongClicks = wrongClicks;
    		this.missedClicks = missedClicks;
    	}
    	
    	public String getPlayerName() {
    		return playerName;
    	}
    	
    	public int getWrongClicks() {
    		return wrongClicks;
    	}
    	
    	public int getRightClicks() {
    		return rightClicks;
    	}
    	
    	public int getMissedClicks() {
    		return missedClicks;
    	}
    }
}