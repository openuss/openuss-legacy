package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import freestyleLearning.homeCore.learningUnitsManager.learningUnitsStructureTree.FSLLearningUnitsStructureTreePanel;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElementGridObject;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.FLGSelectorGridObjectPropertiesDialog;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.FLGSelectorNewGridObjectDialog;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel.FLGSelectorElementsContentsPanel.FLGSelectorContentsPanel_LearningUnitAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.data.xmlBindingSubclasses.FLGTextStudyElement;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGImageProgressDialog;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGLeftToRightLayout;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.media.FLGMediaListener;
import freestyleLearningGroup.independent.media.FLGMediaPlayer;
import freestyleLearningGroup.independent.util.FLGFileUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FLGSelectorConfigurationContentPanel.
 * Manager Class for Selector Configuration Content Panel.
 * @author Carsten Fiedler
 */
public class FLGSelectorConfigurationContentPanel extends FSLAbstractLearningUnitViewElementContentPanel {
    private FLGInternationalization internationalization;
    private FLGMediaPlayer mediaPlayer;
    private FLGSelectorElementGridObject currentGridObject;
    private FLGImageProgressDialog progressDialog = null;
    private boolean isModifiedByUserInput = false;
    private boolean userRoleIsAuthor;
    private boolean selected=false;
    private boolean insertingSeveralImages = false;
    private JList gridObjectsList;
    private DefaultListModel listModel;
    private JCheckBox isAllowedCheckBox;
    private JPanel mainPanel;
    private JPanel imagePanel;
    private java.util.List persistentGridObjectsList;
    private JButton musicStopButton;
    private JButton musicPlayButton;
    private JButton musicRemoveButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton musicButton;
    private JButton loadImageDirectoryButton;
    private JLabel musicFileNameLabel;
    private Image newImage;
    private JMenuItem editElementItem;
    private JMenuItem removeElementItem;
    private JPopupMenu popup;
    private PopupActionListener popupListener;
    private Object[] selectedGridObjects=null;
    
    /**
     * Constructor.
     * Sets internationalization.
     */
    public FLGSelectorConfigurationContentPanel() {
    	internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel.internationalization",
        getClass().getClassLoader());
    }
    
    /**
     * Inits Selector Configuration Content Panel.
     * @param <code>FSLLearningUnitViewManager</code> learningUnitViewManager
     * @param <code>FSLLearningUnitEventGenerator</code> learningUnitEventGenerator
     * @param <code>boolean</code> editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
    		FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        listModel = new DefaultListModel();
        gridObjectsList = new JList(listModel);
        popupListener = new PopupActionListener();
        // init popupmenu
        initPopupMenu();
        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        mediaPlayer = new FLGMediaPlayer();
        learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorLearningUnitViewAdapter());
    }
    
    /**
     * Saves User Changes.
     */
    public void saveUserChanges() {
    	FLGSelectorElement learningUnitViewElement = (FLGSelectorElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewManager.getActiveLearningUnitViewElementId(), true);
        learningUnitViewElement.setLastModificationDate(String.valueOf(new Date().getTime()));
    }
    
    /**
     * Builds independent UI.
     */
    protected void buildIndependentUI() {
        setLayout(new BorderLayout());
        
        // build main configuration panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 241, 245));
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));
        // panel for element overview and music button panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("selector.configurationContentPanel.listLabel")));
        leftPanel.setOpaque(false);
        // panel for element configuration
        JPanel rightPanel = new JPanel(new FLGColumnLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("selector.configurationContentPanel.gridObjectPropertiesLabel")));
        rightPanel.setOpaque(false);
        
        // add grid object list
        //gridObjectsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gridObjectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        gridObjectsList.setCellRenderer(new GridObjectListRenderer());
        gridObjectsList.setBorder(new EmptyBorder(5,5,5,5));
        gridObjectsList.addMouseListener(
        new MouseAdapter() {
            // show popup menu and set selection path in tree
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (!listModel.isEmpty()) {
                        if (gridObjectsList.getSelectedValues().length==1) {
                        	//editElementItem.setEnabled(true);
	                        //removeElementItem.setEnabled(true);
                        	initPopupMenu();
	                        popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	                        if ((gridObjectsList.getSelectedIndex())==-1) {
	                            gridObjectsList.setSelectedIndex(0);
	                        }
                        } else {
                        	//editElementItem.setEnabled(false);
                        	//removeElementItem.setEnabled(true);
                        	initPopupMenu();
	                        popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	                        if ((gridObjectsList.getSelectedIndex())==-1) {
	                            gridObjectsList.setSelectedIndex(0);
	                        }
                        }
                    } else {
                        // list is empty, deactivate edit and delete menu item
                        //editElementItem.setEnabled(false);
                        //removeElementItem.setEnabled(false);
                    	initPopupMenu();
                    }
                }
            }
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (!listModel.isEmpty()) {
                        if (gridObjectsList.getSelectedValues().length==1) {
                        	initPopupMenu();
                        	//editElementItem.setEnabled(true);
	                        //removeElementItem.setEnabled(true);
	                        popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	                        if ((gridObjectsList.getSelectedIndex())==-1) {
	                            gridObjectsList.setSelectedIndex(0);
	                        }
                        } else {
                        	System.out.println("false");
                        	//editElementItem.setEnabled(false);
	                        //removeElementItem.setEnabled(true);
                        	initPopupMenu();
	                        popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	                        if ((gridObjectsList.getSelectedIndex())==-1) {
	                            gridObjectsList.setSelectedIndex(0);
	                        }
                        }
                    } else {
                        // list is empty, deactivate edit and delete menu item
                        //editElementItem.setEnabled(false);
                        //removeElementItem.setEnabled(false);
                    	initPopupMenu();
                    }
                }
            }
        }
        );
        
        JScrollPane listScrollPane = new JScrollPane(gridObjectsList);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        
        // build properties panel
        JPanel propertiesPanel = new JPanel(new FLGColumnLayout());
        propertiesPanel.setOpaque(false);
        
        // element is clickable checkbox
        isAllowedCheckBox = new JCheckBox(internationalization.getString("selector.configurationContentPanel.checkBox.elementAllowed"));
        isAllowedCheckBox.setEnabled(false);
        isAllowedCheckBox.setOpaque(false);
        isAllowedCheckBox.setBorder(new EmptyBorder(5,5,5,5));
        
        // action listener for checkbox
        isAllowedCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ) {
            	if (selectedGridObjects.length==1) {
	                if (currentGridObject.getClickAllowed()) {
	                    // modify descriptor value
	                    currentGridObject.setClickAllowed(false);
	                } else {
	                    // modify descriptor value
	                    currentGridObject.setClickAllowed(true);
	                }
            	} else {
            		for (int i=0; i< selectedGridObjects.length; i++) {
            			FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) selectedGridObjects[i];
            			gridObject.setClickAllowed(isAllowedCheckBox.isSelected());
            		}
            	}
                isModifiedByUserInput=true;
            }
        });
        
        // insert checkbox
        propertiesPanel.add(isAllowedCheckBox,FLGColumnLayout.LEFTEND);
        
        // huild panel for new, edit, delete and laod image directory button
        JPanel buttonPanel = new JPanel(new FLGColumnLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(5,5,5,5));
        JButton newButton = new JButton(
        		internationalization.getString("selector.configurationContentPanel.popup.insertElement"));
        newButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.popup.insertElement"));
        newButton.setPreferredSize(new Dimension(135,30));
        editButton = new JButton(
        		internationalization.getString("selector.configurationContentPanel.popup.editElement"));
        editButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.popup.editElement"));
        editButton.setPreferredSize(new Dimension(135,30));
        deleteButton = new JButton(
        		internationalization.getString("selector.configurationContentPanel.popup.deleteElement"));
        deleteButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.popup.deleteElement"));
        deleteButton.setPreferredSize(new Dimension(135,30));
        loadImageDirectoryButton = new JButton(
        		internationalization.getString("selector.configurationContentPanel.popup.insertElements"));
        loadImageDirectoryButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.popup.insertElements"));
        loadImageDirectoryButton.setPreferredSize(new Dimension(135,30));
        
        // add buttons into panel
        buttonPanel.add(newButton,FLGColumnLayout.LEFTEND);
        buttonPanel.add(editButton,FLGColumnLayout.LEFTEND);
        buttonPanel.add(deleteButton,FLGColumnLayout.LEFTEND);
        buttonPanel.add(loadImageDirectoryButton,FLGColumnLayout.LEFTEND);
        propertiesPanel.add(buttonPanel,FLGColumnLayout.LEFTEND);
        
        // build preview panel for images
        JPanel previewPanel = new JPanel(new FLGColumnLayout());
        previewPanel.setBorder(new EmptyBorder(5,5,5,5));
        previewPanel.setOpaque(false);
        // add label above preview panel
        JLabel previewPanelLabel = new JLabel(internationalization.getString("selector.configurationContentPanel.previewPanelLabel"));
        previewPanelLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        previewPanelLabel.setBorder(new EmptyBorder(5,5,5,5));
        previewPanel.add(previewPanelLabel,FLGColumnLayout.LEFTEND);
        // add preview panel
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(170,170));
        imagePanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel previewLabel = new JLabel(internationalization.getString("selector.configurationPanel.previewPaneltext"),JLabel.CENTER);
        previewLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        imagePanel.add(previewLabel,BorderLayout.CENTER);
        imagePanel.setOpaque(false);
        previewPanel.add(imagePanel,FLGColumnLayout.LEFTEND);
        propertiesPanel.add(previewPanel,FLGColumnLayout.LEFTEND);
        
        rightPanel.add(propertiesPanel,FLGColumnLayout.LEFTEND);
        
        mainPanel.add(leftPanel,BorderLayout.CENTER);
        mainPanel.add(rightPanel,BorderLayout.EAST);
        
        // build music selection panel
        JPanel musicPanel = new JPanel(new FLGColumnLayout());
        musicPanel.setBorder(BorderFactory.createTitledBorder(
        internationalization.getString("selector.configurationContentPanel.musicPanel.header")));
        musicPanel.setOpaque(false);
        // add panel for preseting selected music file name
        musicFileNameLabel = new JLabel();
        musicFileNameLabel.setPreferredSize(new Dimension(400,25));
        musicFileNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        musicFileNameLabel.setOpaque(false);
        musicPanel.add(musicFileNameLabel,FLGColumnLayout.LEFTEND);
        // add music selction button
        JPanel buttonsPanel = new JPanel(new FLGLeftToRightLayout(5));
        musicButton = new JButton(internationalization.getString("selector.configurationContentPanel.musicButton"));
        musicButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.musicButton"));
        musicButton.setPreferredSize(new Dimension(125,25));
        buttonsPanel.add(musicButton);
        musicRemoveButton = new JButton(internationalization.getString("selector.configurationContentPanel.musicRemoveButton"));
        musicRemoveButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.musicRemoveButton"));
        musicRemoveButton.setPreferredSize(new Dimension(125,25));
        musicRemoveButton.setEnabled(false);
        buttonsPanel.add(musicRemoveButton);
        // add music play button
        musicPlayButton = new JButton(internationalization.getString("selector.configurationContentPanel.musicPlayButton"));
        musicPlayButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.musicPlayButton"));
        musicPlayButton.setPreferredSize(new Dimension(125,25));
        musicPlayButton.setEnabled(false);
        buttonsPanel.add(musicPlayButton);
        // add music stop button
        musicStopButton = new JButton(internationalization.getString("selector.configurationContentPanel.musicStopButton"));
        musicStopButton.setToolTipText(internationalization.getString("selector.configurationContentPanel.musicStopButton"));
        musicStopButton.setEnabled(false);
        musicStopButton.setPreferredSize(new Dimension(125,25));
        buttonsPanel.add(musicStopButton);
        musicPanel.add(buttonsPanel,FLGColumnLayout.LEFTEND);
        mainPanel.add(musicPanel,BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setOpaque(false);
        add(new JScrollPane(mainPanel));
        
        /****** add action listeners for buttons ******/
        
        /** newButton **/
        newButton.addActionListener(popupListener);
        
        /** editButton **/
        editButton.addActionListener(popupListener);
        
        /** deleteButton **/
        deleteButton.addActionListener(popupListener);
        
        /** button for loading a whole image directory **/
        loadImageDirectoryButton.addActionListener(popupListener);
        
        /** musicButton for adding music file **/
        musicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileDialog = new JFileChooser();
                String[] fileExtensions = { ".mp3", ".mp2" ,".wav",".mid"};
                fileDialog.setFileFilter(new FLGUIUtilities.FLGFileFilter(fileExtensions,
                	internationalization.getString("selector.configurationContentPanel.musicFileChooser.fileTypes")));
                fileDialog.setDialogTitle(internationalization.getString("selector.dialogs.musicFileChooser.tilte"));
                java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                fileDialog.setLocation((int)(screenDim.getWidth() - fileDialog.getWidth()) / 2,
                (int)(screenDim.getHeight() - fileDialog.getHeight()) / 2);
                if (fileDialog.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
                    final File selectedMusicFile = fileDialog.getSelectedFile();
                    FLGUIUtilities.startLongLastingOperation();
                    Thread copyThread = new Thread() {
                        public void run() {
                            musicPlayButton.setEnabled(false);
                            musicRemoveButton.setEnabled(false);
                            musicButton.setEnabled(false);
                        	progressDialog = new FLGImageProgressDialog("title",0,1000,0,
                                    getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/images/fsl.gif"),
                                    (Color)UIManager.get("FSLColorBlue"),(Color)UIManager.get("FSLColorRed"),
                                    internationalization.getString("selector.configurationContentPanel.loadMusicProgrssBar"));
                            progressDialog.setBarValue(500);
                            copyMusicFile(selectedMusicFile);
                      		progressDialog.setBarValue(1000);
                            musicPlayButton.setEnabled(true);
                            musicRemoveButton.setEnabled(true);
                            musicButton.setEnabled(true);
                            progressDialog.dispose();
                        }
                    };
                    copyThread.start();
                    FLGUIUtilities.stopLongLastingOperation();
                }
            }
        });
        
        // musicRemoveButton for removing music file
        musicRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
                FLGSelectorElement viewElement = null;
                if (userRoleIsAuthor) {
            		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                        	activeLearningUnitViewElementId);
            	} else {
            		// check for user element
            		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
                		activeLearningUnitViewElementId);
            		// if no user element
            		if(viewElement==null) {
            			// get original element
                		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                            	activeLearningUnitViewElementId);
            		}
            	}
                removeMusicFile(viewElement);
                musicRemoveButton.setEnabled(false);
                musicPlayButton.setEnabled(false);
            }
        });
        
        /** button for playing music **/
        musicPlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                musicStopButton.setEnabled(true);
                musicPlayButton.setEnabled(false);
                musicButton.setEnabled(false);
                musicRemoveButton.setEnabled(false);
                try {
                	String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
                	FLGSelectorElement viewElement = null;
                	if (userRoleIsAuthor) {
                		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                            	activeLearningUnitViewElementId);
                		mediaPlayer.loadMedia(new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() + "/" +
                                viewElement.getMusicFileName()));
                	} else {
                		// check for user element
                		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
                    		activeLearningUnitViewElementId);
                		// if no user element
                		if(viewElement==null) {
                			// get original element
                    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                                	activeLearningUnitViewElementId);
                    		mediaPlayer.loadMedia(new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() + "/" +
                                    viewElement.getMusicFileName()));
                		} else {
                			mediaPlayer.loadMedia(new File(learningUnitViewManager.getLearningUnitViewUserDirectory() + "/" +
                                viewElement.getMusicFileName()));
                		}
                	}
                	// play music
                   	mediaPlayer.start();
                    mediaPlayer.addMediaListener(
                    new FLGMediaListener() {
                    	public void endOfMediaReached() {
                    		musicPlayButton.setEnabled(true);
                            musicStopButton.setEnabled(false);
                        }
                    });
                } catch(Exception mediaExp) {
                        System.out.println("Error while playing media file.");
                }
            }
        });
        
        // button to stop running music
        musicStopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                musicStopButton.setEnabled(false);
                musicPlayButton.setEnabled(true);
                musicButton.setEnabled(true);
                musicRemoveButton.setEnabled(true);
                String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
                FLGSelectorElement viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewElement(
                		activeLearningUnitViewElementId,false);
                if (viewElement!=null) {
                    try {
                        mediaPlayer.stop();
                    } catch(Exception mediaExp) {
                        System.out.println("Error while stop playing the media file.");
                    }
                }
            }
        });
    }
    
    private void initPopupMenu() {
        popup = new JPopupMenu();
        // add element
        /**
        insertElementItem = new JMenuItem(internationalization.getString("selector.configurationContentPanel.popup.insertElement"));
        insertElementItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
        insertElementItem.addActionListener(popupListener);
        insertElementItem.setEnabled(true);
        popup.add(insertElementItem);
        **/
        // edit element
        editElementItem = new JMenuItem(internationalization.getString("selector.configurationContentPanel.popup.editElement"));
        editElementItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
        editElementItem.addActionListener(popupListener);
        editElementItem = new JMenuItem(internationalization.getString("selector.configurationContentPanel.popup.editElement"));
        editElementItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
        editElementItem.addActionListener(popupListener);
        if (selectedGridObjects!=null) {
	        if (selectedGridObjects.length==1) {
	        	editElementItem.setEnabled(true);
	        }
	     else {
	        	
		    editElementItem.setEnabled(false);
	    }}
        popup.add(editElementItem);
	    // delete selected element
        removeElementItem = new JMenuItem(internationalization.getString("selector.configurationContentPanel.popup.deleteElement"));
        removeElementItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
        removeElementItem.addActionListener(popupListener);
        removeElementItem.setEnabled(true);
        popup.add(removeElementItem);
        //popup.addSeparator();
        // add elements
        /**
        insertElementsItem = new JMenuItem(internationalization.getString("selector.configurationContentPanel.popup.insertElements"));
        insertElementsItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
        insertElementsItem.addActionListener(popupListener);
        insertElementsItem.setEnabled(true);
        popup.add(insertElementsItem);
        **/
        popup.setOpaque(true);
    }
    
    private void removeMusicFile(FLGSelectorElement viewElement) {
        if (viewElement!=null) {
            if (viewElement.getMusicFileName()!=null && !(viewElement.getMusicFileName().equals(""))) {
                // delete descriptor entry
                viewElement.setMusicFileName(null);
                // update panel
                musicFileNameLabel.setText("");
                // disbale music play button
                musicPlayButton.setEnabled(false);
                musicRemoveButton.setEnabled(false);
                isModifiedByUserInput=true;
            }
        }
    }
    
    private void copyMusicFile(File selectedMusicFile) {
        // if old file exits, remove it
        String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
       	FLGSelectorElement viewElement = null;
    	if (userRoleIsAuthor) {
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                	activeLearningUnitViewElementId);
    	} else {
    		// check for user element
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
        		activeLearningUnitViewElementId);
    		// if no user element
    		if(viewElement==null) {
    			// get original element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
    		}
    		if (learningUnitViewElementsManager.isOriginalElementsModified()) {
    			viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
                    	activeLearningUnitViewElementId);
    		}
    	}
        if (viewElement!=null) {
            // get file type
            String musicFileName = selectedMusicFile.getName();
            StringBuffer fileType = new StringBuffer();
            for (int i=3;i>0;i--) {
                fileType.append(musicFileName.charAt(musicFileName.length()-i));
            }
            // create new file for elements external data
            String id = learningUnitViewManager.getActiveLearningUnitViewElementId();
            File destinationFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(
            "music", "." + fileType.toString(), id);
            // copy new music file into selector folder and delete tmpFile
            FLGFileUtility.copyFile(selectedMusicFile, destinationFile);
            // update descriptor and save
            viewElement.setMusicFileName(destinationFile.getName());
            isModifiedByUserInput=true;
            // update label
            musicFileNameLabel.setText(internationalization.getString("selector.configurationContentPanel.musicPanel.label"));
            musicPlayButton.setEnabled(false);
        }
    }
    
    /**
     * Deletes a grid object from list.
     * If image is selected, the corresponding file will be deleted.
     * @param <code>String</code> gridObjectId
     * @param <code>int</code> selectedIndex
     */
    public void deleteElement(String gridObjectId, int selectedIndex) {
        // delete image panel
        imagePanel.removeAll();
        imagePanel.repaint();
        imagePanel.updateUI();
        // get active view element
        
        String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
        FLGSelectorElement viewElement = null;
    	
        if (userRoleIsAuthor) {
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                	activeLearningUnitViewElementId);
    	} else {
    		// check for user element
    		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
        		activeLearningUnitViewElementId);
    		// if no user element
    		if(viewElement==null) {
    			// get original element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
    		}
    	}
        // get grid objects to active view element
        persistentGridObjectsList = viewElement.getLearningUnitViewElementGridObjects();
        if (persistentGridObjectsList!=null) {
            for (int i=0; i<persistentGridObjectsList.size(); i++) {
                FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) persistentGridObjectsList.get(i);
                if (gridObjectId.equals(gridObject.getId())) {
                    persistentGridObjectsList.remove(gridObject);
                }
            }
        }
    }
    
    /**
     * Creates a new grid object and sets it as current active grid object.
     * @param <code>String</code> name
     * @param <code>Image</code> scaledImage
     * @param <code>File</code> newImageFile
     */
    public void insertNewImageElement(String name, Image scaledImage, File imageFile) {
        // set check boxes
        isAllowedCheckBox.setSelected(true);
        isAllowedCheckBox.setEnabled(true);
        if (learningUnitViewManager!=null && learningUnitViewElementsManager!=null) {
            // get active view element
            String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            FLGSelectorElement viewElement = null;
            if (userRoleIsAuthor) {
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
        	} else {
        		// check for user element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
            		activeLearningUnitViewElementId);
        		// if no user element
        		if(viewElement==null) {
        			// get original element
            		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                        	activeLearningUnitViewElementId);
        		}
        	}
            if (viewElement!=null) {
                // get grid object list to active view element
                persistentGridObjectsList = viewElement.getLearningUnitViewElementGridObjects();
                // get file type from grid object image file
                String s = imageFile.getName();
                StringBuffer fileType = new StringBuffer();
                for (int i=3;i>0;i--) {
                    fileType.append(s.charAt(s.length()-i));
                }
                // create new file for elements externalData
                File destinationFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(
                FLGSelectorElementGridObject.ELEMENT_TYPE_IMAGE, "." + fileType.toString(),
                activeLearningUnitViewElementId);
                // create temporary source file in author/user directory to store scaled images
                File sourceFile;
                if (userRoleIsAuthor) {
                    sourceFile = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory()
                    + "/" + imageFile.getName());
                } else {
                    sourceFile = new File(learningUnitViewManager.getLearningUnitViewUserDirectory()
                    + "/" + imageFile.getName());
                }
                try {
                	FLGUIUtilities.saveImage(FLGUIUtilities.createBufferedImage(scaledImage),sourceFile.getAbsolutePath());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                FLGFileUtility.copyFile(sourceFile, destinationFile);
                sourceFile.delete();
                // create new grid object
                FLGSelectorElementGridObject newImageObject = new FLGSelectorElementGridObject();
                newImageObject.setId(destinationFile.getName());
                newImageObject.setType(FLGSelectorElementGridObject.ELEMENT_TYPE_IMAGE);
                newImageObject.setImageFileName(name);
                // store new grid object in view element descriptor
                currentGridObject = newImageObject;
                currentGridObject.setClickAllowed(true);
                isAllowedCheckBox.setSelected(true);
                persistentGridObjectsList.add(currentGridObject);
                isModifiedByUserInput=true;
                viewElement.setModified(true);
            }
        }
    }
    
    /**
     * Inserts a new Grid Object, a new Text Element.
     * @param <code>String</code> newText
     */
    public void insertNewTextElement(String newText) {
        boolean textElementAlreadyExits=false;
        if (learningUnitViewManager!=null && learningUnitViewElementsManager!=null) {
            // get active view element
            String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            FLGSelectorElement viewElement = null;
            if (userRoleIsAuthor) {
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
        	} else {
        		// check for user element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
            		activeLearningUnitViewElementId);
        		// if no user element
        		if(viewElement==null) {
        			// get original element
            		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                        	activeLearningUnitViewElementId);
        		}
        	}
            if (viewElement!=null) {
                persistentGridObjectsList = viewElement.getLearningUnitViewElementGridObjects();
                if (persistentGridObjectsList!=null) {
                    // check, if text already exits
                    if ( persistentGridObjectsList!=null) {
                        for (int i = 0; i < persistentGridObjectsList.size(); i++) {
                            FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) persistentGridObjectsList.get(i);
                            if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT)) {
                                if (gridObject.getText().equals(newText)) {
                                    // open message window
                                    JPanel messagePanel = new JPanel();
                                    messagePanel.add(new JLabel(internationalization.getString("selector.configurationContentPanel.textElementAlreadyExitsMessage")));
                                    int returnValue = FLGOptionPane.showConfirmDialog(messagePanel,
                                    internationalization.getString("selector.configurationContentPanel.textElementAlreadyExitsOptionPaneTitle"),
                                    FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
                                    // set boolean
                                    textElementAlreadyExits = true;
                                    break;
                                }
                            }
                        }
                        // if text does not exits, insert new grid object
                        if (!textElementAlreadyExits) {
                            // if image file name does not exit insert new grid object
                            FLGSelectorElementGridObject newtextObject = new FLGSelectorElementGridObject();
                            newtextObject.setId(newText);
                            newtextObject.setText(newText);
                            newtextObject.setType(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT);
                            // store new grid object in descriptor
                            currentGridObject = newtextObject;
                            currentGridObject.setClickAllowed(true);
                            isAllowedCheckBox.setSelected(true);
                            isAllowedCheckBox.setEnabled(true);
                            persistentGridObjectsList.add(currentGridObject);
                            isModifiedByUserInput=true;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Modifies an existing text element.
     * @param <code>String</code> newText
     * @param <code>int</code> selectedIndex
     * @param <code>boolean</code> clickedAllowed
     */
    public void modifyTextElement(String newText, int selectedIndex, boolean clickedAllowed) {
        boolean textElementAlreadyExits=false;
        if (learningUnitViewManager!=null && learningUnitViewElementsManager!=null) {
            // get active view element
            String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            FLGSelectorElement viewElement = null;
            if (userRoleIsAuthor) {
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
        	} else {
        		// check for user element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
            		activeLearningUnitViewElementId);
        		// if no user element
        		if(viewElement==null) {
        			// get original element
            		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                        	activeLearningUnitViewElementId);
        		}
        	}
            if (viewElement!=null) {
                persistentGridObjectsList = viewElement.getLearningUnitViewElementGridObjects();
                if (persistentGridObjectsList!=null) {
                    // check, if text already exits
                    if ( persistentGridObjectsList!=null) {
                        for (int i = 0; i < persistentGridObjectsList.size(); i++) {
                            FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) persistentGridObjectsList.get(i);
                            if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT)) {
                                if (gridObject.getText().equals(newText)) {
                                    // open message window
                                    JPanel messagePanel = new JPanel();
                                    messagePanel.add(new JLabel(internationalization.getString("selector.configurationContentPanel.textElementAlreadyExitsMessage")));
                                    int returnValue = FLGOptionPane.showConfirmDialog(messagePanel,
                                    internationalization.getString("selector.configurationContentPanel.textElementAlreadyExitsOptionPaneTitle"),
                                    FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
                                    // set boolean
                                    textElementAlreadyExits = true;
                                    break;
                                }
                            }
                        }
                        // if text does not exits, insert new grid object
                        if (!textElementAlreadyExits) {
                            // delete old entries
                            deleteElement(currentGridObject.getId(),selectedIndex);
                            listModel.removeElement(currentGridObject);
                            // create new grid object
                            FLGSelectorElementGridObject newtextObject = new FLGSelectorElementGridObject();
                            newtextObject.setId(newText);
                            newtextObject.setText(newText);
                            newtextObject.setType(FLGSelectorElementGridObject.ELEMENT_TYPE_TEXT);
                            // store new grid object in descriptor
                            currentGridObject = newtextObject;
                            currentGridObject.setClickAllowed(clickedAllowed);
                            if (clickedAllowed) {
                                isAllowedCheckBox.setSelected(true);
                            } else {
                                isAllowedCheckBox.setSelected(false);
                            }
                            persistentGridObjectsList.add(currentGridObject);
                            isModifiedByUserInput=true;
                            // update jlist
                            listModel.addElement(currentGridObject);
                            gridObjectsList.setSelectedIndex(listModel.size()-1);
                            
                        }
                    }
                }
            }
        }
    }
    
    public void modifyImageElement(String name, Image newImage, File imageFile, int selectedIndex, boolean clickedAllowed) {
        // delete old element
        deleteElement(currentGridObject.getId(),selectedIndex);
        listModel.removeElement(currentGridObject);
        insertNewImageElement(name, newImage, imageFile);
        if (clickedAllowed) {
            isAllowedCheckBox.setSelected(true);
            isAllowedCheckBox.setEnabled(true);
        } else {
            isAllowedCheckBox.setSelected(false);
            isAllowedCheckBox.setEnabled(false);
        }
        // update jlist
        listModel.addElement(currentGridObject);
        gridObjectsList.setSelectedIndex(listModel.size()-1);
    }
    
    /**
     * Stops FLGMediaPlayer if is still playing.
     */
    public void deactiveMediaPlayer(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
    
    /**
     * Builds dependent UI.
     * Loads grid objects from selector descriptor into grid object lists.
     */
    protected void buildDependentUI(boolean reloadIfAlreadyLoaded) {
    	isModifiedByUserInput=false;
    	selected=false;
        deactiveMediaPlayer();
        // clear image panel
        imagePanel.removeAll();
        imagePanel.repaint();
        imagePanel.updateUI();
        musicFileNameLabel.setText("");
        musicPlayButton.setEnabled(false);
        musicStopButton.setEnabled(false);
        musicRemoveButton.setEnabled(false);
        // re-init check boxes
        isAllowedCheckBox.setSelected(false);
        isAllowedCheckBox.setEnabled(false);
        // build grid objects list
        listModel.removeAllElements();
        listModel.clear();
        gridObjectsList.removeAll();
        gridObjectsList.removeAll();
        gridObjectsList.updateUI();
      
        if (learningUnitViewManager!=null) {
            String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            learningUnitViewElementsManager = learningUnitViewManager.getLearningUnitViewElementsManager();
            FLGSelectorElement viewElement = null;
            if (userRoleIsAuthor) {
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
        	} else {
        		// check for user element
        		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
            		activeLearningUnitViewElementId);
        		// if no user element
        		if(viewElement==null) {
        			// get original element
            		viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                        activeLearningUnitViewElementId);
        		}
        	}
            if (viewElement!=null) {
                final java.util.List gridObjectList = viewElement.getLearningUnitViewElementGridObjects();
                // delete entries in list model
                if (gridObjectList!=null) {
                    // set music file name
                    if (viewElement.getMusicFileName()!=null && !(viewElement.getMusicFileName().equals(""))) {
                        musicFileNameLabel.setText(internationalization.getString("selector.configurationContentPanel.musicPanel.label"));
                        musicPlayButton.setEnabled(true);
                        musicRemoveButton.setEnabled(true);
                        // load music
                        
                    }
                    if (gridObjectList.size() >= 1) {
                        FLGUIUtilities.startLongLastingOperation();
                    	// init progress dialog
                        /**
                        progressDialog = new FLGImageProgressDialog("title",0,(gridObjectList.size())*10,0,
                        	getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/images/fsl.gif"),
                        	(Color)UIManager.get("FSLColorBlue"),(Color)UIManager.get("FSLColorRed"),"Loading selector elements...");
                        **/
                        //Thread loader = new Thread() {
                          //  public void run() {
                                // load grid objects
                                for (int i=0; i<gridObjectList.size();i++) {
                                    final FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) gridObjectList.get(i);
                                    final int barStep = i;
                            //        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                              //          public void run() {
                                        	//if(!listModel.contains(gridObject))  {
                                        		listModel.addElement(gridObject);
                                        		//progressDialog.setBarValue((barStep+1)*10);
                                        	//}
                                  //      }
                                   // });
                                    
                                }
                          //  }
                        //};
                       // loader.start();
                        //progressDialog.dispose();
                        FLGUIUtilities.stopLongLastingOperation();
                    }
                }
            }
        }
   	}
 
    /**
     * Gets Edit-Toolbar. Inherited method has to be overwritten.
     * Selector View does not support an Edit-Toolbar.
     * It uses a configuration content panel.
     * @return null
     */
    protected JComponent[] getEditToolBarComponents() {
        return null;
    }
    
    /**
     * Returns true if Content Panel has been modified by user input.
     * @return <code>boolean</code>
     */
    public boolean isModifiedByUserInput() {
        return isModifiedByUserInput;
    }
    
    /**
     * Loads image to specified URL.
     * @param <code>String</code> image url
     * @return <code>Image</code> image
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
    			// user element
    			f = new File(learningUnitViewManager.getLearningUnitViewUserDirectory() + "/" + imageFileUrl);
    		}
    	}
        URL u = null;
        try {
            u = f.toURL();
        } catch (Exception e) { e.printStackTrace();}
        return FLGImageUtility.loadImageAndWait(u);
    }
    
    private void loadGridObjectPreview() {
	    	imagePanel.removeAll();
	    	if (gridObjectsList.getSelectedIndices().length==1) {
	    		editButton.setEnabled(true);
		    	FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) gridObjectsList.getSelectedValue();
			    	if (gridObject!=null) {
			            currentGridObject=gridObject;
			            isAllowedCheckBox.setEnabled(true);
			            if (currentGridObject.getClickAllowed()) {
			                isAllowedCheckBox.setSelected(true);
			            } else {
			                isAllowedCheckBox.setSelected(false);
			            }
			            if (currentGridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_IMAGE)) {
			                if (currentGridObject.getClickAllowed()) {
			                    isAllowedCheckBox.setSelected(true);
			                } else {
			                    isAllowedCheckBox.setSelected(false);
			                }
			                imagePanel.add(new JLabel(
			                		new ImageIcon(
			                				scaleImage(loadImage(currentGridObject.getId()),170,170))),BorderLayout.CENTER);
			            } else {
			                JLabel previewLabel = new JLabel(currentGridObject.getText(),JLabel.CENTER);
			                previewLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
			                imagePanel.add(previewLabel,BorderLayout.CENTER);
			            }
			        }
	    	}
	    	if (gridObjectsList.getSelectedIndices().length>1) {
	    		editButton.setEnabled(false);
	    	}
	        imagePanel.repaint();
	        imagePanel.updateUI();
	        if (gridObjectsList.isSelectionEmpty()) {
		        isAllowedCheckBox.setSelected(false);
		        isAllowedCheckBox.setEnabled(false);
		    }
	        
    }
    
    private Image loadImage(File imageFileName) {
        URL imageUrl = null;
        try {
            imageUrl = imageFileName.toURL();
        } catch (Exception e) { e.printStackTrace();}
        return FLGImageUtility.loadImageAndWait(imageUrl);
    }
    
    private Image scaleImage(Image imageToScale, int w, int h) {
        Image newImage = imageToScale;
        ImageObserver observer = new ImageObserver() {
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
        int height = newImage.getHeight(observer);
        int width  = newImage.getWidth(observer);
        if(imageToScale.getHeight(observer) >= imageToScale.getWidth(observer)) {
            newImage = imageToScale.getScaledInstance(-1, h, Image.SCALE_SMOOTH);
        } else {
            newImage = imageToScale.getScaledInstance(w, -1, Image.SCALE_SMOOTH);
        }
        return newImage;
    }
    
    /**
     * GridObjectListRenderer.
     * Class renders grid object list.
     */
    public class GridObjectListRenderer extends JLabel implements ListCellRenderer {
        
        public GridObjectListRenderer() {
            setOpaque(true);
            setIconTextGap(10);
        }
        
        public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject)value;
            if (gridObject!=null) {
                setFont(new Font("SansSerif", Font.PLAIN, 14));
                if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_IMAGE)) {
                    setText(gridObject.getImageFileName());
                    
                    setIcon(new ImageIcon(scaleImage(loadImage(gridObject.getId()))));
                    
                } else {
                    setText(gridObject.getId());
                    setIcon(new ImageIcon(scaleImage(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().
                    getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/images/text.gif")))));
                }
            }
            if(isSelected) {
            	if(!insertingSeveralImages) {
	                selected=true;
	                // enable pop up menu
	                //editElementItem.setEnabled(true);
	                //removeElementItem.setEnabled(true);
	                initPopupMenu();
	                setBackground(new Color(50, 50, 122));
	                setForeground(Color.white);
	                // get all selected grid objects
	                selectedGridObjects = gridObjectsList.getSelectedValues();
	                if (selectedGridObjects.length==0) { 
	                	isAllowedCheckBox.setSelected(false);
	                	isAllowedCheckBox.setEnabled(false);
	                }
	                loadGridObjectPreview();
            	}
            } else {
            	loadGridObjectPreview();
                setBackground(Color.white);
                setForeground(Color.black);
            }
            setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
            return this;
        }
        
        private Image loadImage(String imageFileId) {
            File f = null;
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
            return FLGImageUtility.loadImageAndWait(u);
        }
        
        private Image scaleImage(Image imagetoScale) {
            Image scaledImage;
            scaledImage = imagetoScale.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return scaledImage;
        }
    }
    
    /**
     * FLGSelectorLearningUnitViewAdapter.
     * Inner class for learning unit view event handling.
     */
    class FLGSelectorLearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_USER_ROLE_CHANGED) {
            	userRoleIsAuthor=selectorEvent.getUserRole();
            	if (active) buildDependentUI(true);
             }
        }
    }
    
    /**
     * Inner class for invoking popup actions.
     */
    class PopupActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // add element
            if (ae.getActionCommand().equals(internationalization.getString("selector.configurationContentPanel.popup.insertElement"))) {
                // open dialog for adding a new element
                FLGSelectorNewGridObjectDialog newObjectDialog = new FLGSelectorNewGridObjectDialog();
                newObjectDialog.showDialog();
                if (newObjectDialog.insertNewElement()) {
                    if (newObjectDialog.imageSelected()) {
                        Image newImage = newObjectDialog.getScaledImage();
                        String newText = newObjectDialog.getText();
                        File imageFile = newObjectDialog.getImageFile();
                        if(imageFile!=null) {
                            insertNewImageElement(newText,newImage,imageFile);
                        } else {
                            // option error pane
                            FLGOptionPane.showConfirmDialog(internationalization.getString("selector.dialogs.noImageInserted.text"),
                            internationalization.getString("selector.dialogs.noElementSelected.title"),
                            FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
                            isModifiedByUserInput=false;
                        }
                    } else {
                        String newText = newObjectDialog.getText();
                        insertNewTextElement(newText);
                    }
                    if (isModifiedByUserInput) {
                        // update jlist
                        listModel.addElement(currentGridObject);
                        gridObjectsList.setSelectedIndex(listModel.size()-1);
                    }
                }
            }
            // edit element
            if (ae.getActionCommand().equals(internationalization.getString("selector.configurationContentPanel.popup.editElement"))) {
                currentGridObject = (FLGSelectorElementGridObject) gridObjectsList.getSelectedValue();
                int selectedIndex = gridObjectsList.getSelectedIndex();
                if (currentGridObject!=null) {
                    FLGSelectorGridObjectPropertiesDialog propertiesDialog = new FLGSelectorGridObjectPropertiesDialog();
                    // text element
                    if (currentGridObject.getType().equals(FLGSelectorElement.ELEMENT_TYPE_TEXT)) {
                        propertiesDialog.setTextElementTitle(currentGridObject.getText());
                        propertiesDialog.showDialog();
                        if (propertiesDialog.insertNewElement()) {
                            modifyTextElement(propertiesDialog.getText(),selectedIndex,currentGridObject.getClickAllowed());
                        }
                        // image element
                    } else {
                        // get image file to grid object
                        File imageFile;
                        File newImageFile;
                        if (userRoleIsAuthor) {
                            imageFile = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() +
                            "/" + currentGridObject.getId());
                            newImageFile = new File(learningUnitViewManager.getLearningUnitViewOriginalDataDirectory() +
                            "/tmp_" + currentGridObject.getId());
                        } else {
                            imageFile = new File(learningUnitViewManager.getLearningUnitViewUserDirectory() +
                            "/" + currentGridObject.getId());
                            newImageFile = new File(learningUnitViewManager.getLearningUnitViewUserDirectory() +
                            "/tmp_" + currentGridObject.getId());
                        }
                        FLGFileUtility.copyFile(imageFile, newImageFile);
                        propertiesDialog.setImageFile(newImageFile);
                        propertiesDialog.setImageElementTitle(currentGridObject.getImageFileName());
                        propertiesDialog.showDialog();
                        // get user decision
                        if (propertiesDialog.insertNewElement()) {
                            String text = propertiesDialog.getText();
                            imageFile = propertiesDialog.getImageFile();
                            if (imageFile!=null) {
                                newImage = loadImage(imageFile);
                                ImageObserver observer = new ImageObserver() {
                                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                                        return false;
                                    }
                                };
                                int height = newImage.getHeight(observer);
                                int width  = newImage.getWidth(observer);
                                newImage = propertiesDialog.getScaledImage();
                                modifyImageElement(text,newImage,imageFile,selectedIndex,currentGridObject.getClickAllowed());
                            } else {
                                // option error pane
                                FLGOptionPane.showConfirmDialog(internationalization.getString("selector.dialogs.noImageInserted.text"),
                                internationalization.getString("selector.dialogs.noElementSelected.title"),
                                FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
                            }
                        }
                        newImageFile.delete();
                    }
                } else {
                    // open message window: no element selected
                    int returnValue = FLGOptionPane.showConfirmDialog(internationalization.getString("selector.dialogs.noElementSelected.text"),
                    internationalization.getString("selector.dialogs.noElementSelected.title"),
                    FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
                }
            }
            // remove element
            if (ae.getActionCommand().equals(internationalization.getString("selector.configurationContentPanel.popup.deleteElement"))) {
            	if (gridObjectsList.getSelectedIndices().length>1) {
            		selectedGridObjects = gridObjectsList.getSelectedValues();
            		int[] selectedIndeces = gridObjectsList.getSelectedIndices();
            		// open option pane, if user really wants to delete
                    int returnValue = FLGOptionPane.showConfirmDialog(internationalization.getString("selector.dialogs.deleteElementsDialog.text"),
                    internationalization.getString("selector.dialogs.deleteDialog.title"),
                    FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.QUESTION_MESSAGE);
                    if (returnValue==FLGOptionPane.OK_OPTION) {
	            		for (int i=0; i<selectedGridObjects.length; i++) {
	    	                currentGridObject = (FLGSelectorElementGridObject) selectedGridObjects[i];
	    	                deleteElement(currentGridObject.getId(),selectedIndeces[i]);
	    	            }
	                    // update jlist
	            		for (int i=0; i<selectedGridObjects.length; i++) {
	            			currentGridObject = (FLGSelectorElementGridObject) selectedGridObjects[i];
	            			listModel.removeElement(currentGridObject);
	            			isModifiedByUserInput=true;
	            		}
                    }
            	} else {
	                currentGridObject = (FLGSelectorElementGridObject) gridObjectsList.getSelectedValue();
	                int selectedIndex = gridObjectsList.getSelectedIndex();
	                if (currentGridObject!=null) {
	                    // open option pane, if user really wants to delete
	                    int returnValue = FLGOptionPane.showConfirmDialog(internationalization.getString("selector.dialogs.deleteDialog.text"),
	                    internationalization.getString("selector.dialogs.deleteDialog.title"),
	                    FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.QUESTION_MESSAGE);
	                    if (returnValue==FLGOptionPane.OK_OPTION) {
	                        deleteElement(currentGridObject.getId(),selectedIndex);
	                        // update jlist
	                        listModel.remove(selectedIndex);
	                        isModifiedByUserInput=true;
	                    }
	                } else {
	                    // open message window: no element selected
	                    int returnValue = FLGOptionPane.showConfirmDialog(internationalization.getString("selector.dialogs.noElementSelected.text"),
	                    internationalization.getString("selector.dialogs.noElementSelected.title"),
	                    FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
	                }
            	}
            }
            // insert several images
            if (ae.getActionCommand().equals(internationalization.getString("selector.configurationContentPanel.popup.insertElements"))) {
                // open message window with scaling options
                final int width=250;
                final int height=250;
                // open file chooser
                JFileChooser fileDialog = new JFileChooser();
                fileDialog.setMultiSelectionEnabled(true);
                fileDialog.setDialogTitle(internationalization.getString("selector.configurationContentPanel.loadImageDirectoryFileChooserTitle="));
                java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                fileDialog.setLocation((int)(screenDim.getWidth() - fileDialog.getWidth()) / 2,
                (int)(screenDim.getHeight() - fileDialog.getHeight()) / 2);
                String[] fileExtensions = { ".png" ,".jpg", ".gif"};
                fileDialog.setFileFilter(new FLGUIUtilities.FLGFileFilter(fileExtensions,
                internationalization.getString("selector.configurationContentPanel.fileDialog.fileFilter")));
                // open dialog
                if (fileDialog.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
                	insertingSeveralImages=true;
                    final File[] imageFiles = fileDialog.getSelectedFiles();
                    FLGUIUtilities.startLongLastingOperation();
                    Thread loader = new Thread() {
                        public void run() {
                            progressDialog = new FLGImageProgressDialog("title",0,(imageFiles.length)*10,0,
                                getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/images/fsl.gif"),
                                (Color)UIManager.get("FSLColorBlue"),(Color)UIManager.get("FSLColorRed"),
                                internationalization.getString("selector.configurationContentPanel.loadAndScaleImagesProgrssBar"));
                        	// scale images files and
                        	// create new  grid objects for images in descriptor
                        	for (int i=0;i<imageFiles.length;i++) {
                        		Image scaledImage = scaleImage(loadImage(imageFiles[i]),width,height);
                        		// scaled image needs height value, so draw it in temporary panel
                        		new JPanel().add(new JLabel(new ImageIcon(scaledImage)));
                        		try {
                        			insertNewImageElement(imageFiles[i].getName(),scaledImage,imageFiles[i]);
                        			// update jlist
                        			final int barStep=i+1;
                        			javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        				public void run() {
                                        	 listModel.addElement(currentGridObject);
                                       		 progressDialog.setBarValue(barStep*10);
                        				}
                        			 });
                        		} catch (Exception exp) { exp.printStackTrace(); }
                        	}
                        	// close progress bar
                            progressDialog.dispose();
                        }
                    };
                    loader.start();
                   	FLGUIUtilities.stopLongLastingOperation();
                    insertingSeveralImages=false;
                }
            }
        }
    }
}
