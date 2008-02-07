/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementsStructurePanel;

import java.util.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.*;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

/**
 * FLGMediaPoolElementsStructurePanel.
 * Class for managing Media Pool Structure Tree Elements.
 * @author Freestyle Learning Group
 */
public class FLGMediaPoolElementsStructurePanel extends FSLAbstractLearningUnitViewElementsStructurePanel {
    private FLGMediaPoolSpecificPanel learningUnitViewNewAndModifyElementDialogViewSpecificPane;
    private FLGInternationalization internationalization;
    private String[] mediaTypes = new String[6];
    private ArrayList nodesToDelete;
    
    /**
     * Inits FLGMediaPoolElementsStructurePanel.
     * @param <code>FSLLearningUnitViewManager</code> learningUnitViewManager
     * @param <code>FSLLearningUnitEventGenerator</code> learningUnitEventGenerator
     * @param <code>boolean</code> editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
    FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        nodesToDelete = new ArrayList();
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementsStructurePanel.internationalization",
        getClass().getClassLoader());
        mediaTypes[FLGMediaFileChooser.PICTURE] = FLGMediaPoolElement.FILE_TYPE_PICTURE;
        mediaTypes[FLGMediaFileChooser.VIDEO] = FLGMediaPoolElement.FILE_TYPE_VIDEO;
        mediaTypes[FLGMediaFileChooser.AUDIO] = FLGMediaPoolElement.FILE_TYPE_AUDIO;
        mediaTypes[FLGMediaFileChooser.PDF] = FLGMediaPoolElement.FILE_TYPE_PDF;
        mediaTypes[FLGMediaFileChooser.PPT] = FLGMediaPoolElement.FILE_TYPE_PPT;
        mediaTypes[FLGMediaFileChooser.EXTERNAL] = FLGMediaPoolElement.FILE_TYPE_EXTERNAL;
        learningUnitViewNewAndModifyElementDialogViewSpecificPane = new FLGMediaPoolSpecificPanel();
    }
    
    /**
     * Creates DefaultEditToolBar.
     */
    protected void createDefaultEditToolBar() {
        editToolBar = new FSLLearningUnitViewElementsStructureEditToolBar();
        editToolBar.init(learningUnitViewManager, learningUnitEventGenerator, this, editMode,
        FSLLearningUnitViewElementsStructureEditToolBar.DEFAULT_EDIT_TOOLBAR_SORTABLE_EXPORT);
        buildIndependentUI();
    }
    
    /**
     * Returns pane for inserting or modifing structure tree elements.
     * @return <code>FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane</code> learningUnitViewNewAndModifyElementDialogViewSpecificPane
     */
    public FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane getLearningUnitViewNewAndModifyElementDialogViewSpecificPane() {
        return learningUnitViewNewAndModifyElementDialogViewSpecificPane;
    }
    
    /**
     * Modifies LearningUnitViewElement.
     * @param <code>FSLLearningUnitViewElement</code> element
     */
    public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
        FLGMediaPoolElement mediaPoolElement = (FLGMediaPoolElement)element;
        mediaPoolElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        String mediaPoolElementID = mediaPoolElement.getId();
        if (!mediaPoolElement.getFolder()) {
            mediaPoolElement.setType(selectedMediaType);
            
            if(selectedMediaType.equals("ppt")) {
            	File sourceMediaFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFile();
            	File destinationMediaFile = null;
            	if(learningUnitViewNewAndModifyElementDialogViewSpecificPane.getFinalizePPTFlag()) {
            		// convert ppt in pps file
                    String sourceMediaFileExtension = ".pps";
                    destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(selectedMediaType,
                    		sourceMediaFileExtension, mediaPoolElementID);
            	} else {
            		// convert pps in ppt file
                    String sourceMediaFileExtension = ".ppt";
                    destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(selectedMediaType,
                    		sourceMediaFileExtension, mediaPoolElementID);
            	}
                if (sourceMediaFile != null) {
                    FLGUIUtilities.startLongLastingOperation();
                    FLGFileUtility.copyFile(sourceMediaFile, destinationMediaFile);
                    mediaPoolElement.setMediaFileName(destinationMediaFile.getName());
                    FLGUIUtilities.stopLongLastingOperation();
                }
            } else{
	            File sourceMediaFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFile();
	            String sourceMediaFileExtension = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFileExtension();
	            File destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(selectedMediaType,
	            		sourceMediaFileExtension, mediaPoolElementID);
	            if (sourceMediaFile != null && !sourceMediaFile.getName().equals(mediaPoolElement.getMediaFileName())) {
	                FLGUIUtilities.startLongLastingOperation();
	                FLGFileUtility.copyFile(sourceMediaFile, destinationMediaFile);
	                mediaPoolElement.setMediaFileName(destinationMediaFile.getName());
	                FLGUIUtilities.stopLongLastingOperation();
	            }
            }
        } else {
            mediaPoolElement.setType("folder");
            mediaPoolElement.setMediaFileName("");
        }
    }
    
    /**
     * Creates LearningUnitViewElement.
     * @param <code>String</code> id
     * @param <code>String</code> parentId
     * @param <code>String</code> title
     * @param <code>boolean</code> folder
     * @return <code>FSLLearningUnitViewElement</code> newElement
     */
    public FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, boolean folder) {
        final FLGMediaPoolElement newElement = new FLGMediaPoolElement();
        newElement.setId(id);
        newElement.setParentId(parentId);
        newElement.setFolder(folder);
        newElement.setTitle(title);
        newElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (folder) {
            newElement.setMediaFileName("");
            newElement.setType("folder");
        }
        else {
            if (!folderImport) {
                newElement.setType(selectedMediaType);
                if(selectedMediaType.equals("ppt")) {
                	if(learningUnitViewNewAndModifyElementDialogViewSpecificPane.getFinalizePPTFlag()) {
                		// convert ppt in pps file
                		final File sourceMediaFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFile();
                		String sourceMediaFileExtension = ".pps";
                        final File destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(selectedMediaType,
                                sourceMediaFileExtension, id);
                        FLGUIUtilities.startLongLastingOperation();
                        FLGFileUtility.copyFile(sourceMediaFile, destinationMediaFile);
                        newElement.setMediaFileName(destinationMediaFile.getName());
                        FLGUIUtilities.stopLongLastingOperation();
                	} else {
                		// convert pps in ppt file
                		final File sourceMediaFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFile();
                		String sourceMediaFileExtension = ".ppt";
                        final File destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(selectedMediaType,
                                sourceMediaFileExtension, id);
                        FLGUIUtilities.startLongLastingOperation();
                        FLGFileUtility.copyFile(sourceMediaFile, destinationMediaFile);
                        newElement.setMediaFileName(destinationMediaFile.getName());
                        FLGUIUtilities.stopLongLastingOperation();
                	}
                } else {
                	final File sourceMediaFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFile();
                    String sourceMediaFileExtension = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getMediaFileExtension();
                    final File destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(selectedMediaType,
                    		sourceMediaFileExtension, id);
                    FLGUIUtilities.startLongLastingOperation();
                    FLGFileUtility.copyFile(sourceMediaFile, destinationMediaFile);
                    newElement.setMediaFileName(destinationMediaFile.getName());
                    FLGUIUtilities.stopLongLastingOperation();
                }
            }
        }
        return newElement;
    }
    
    class FLGMediaPoolSpecificPanel extends JPanel implements FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane {
        private JLabel fileSelectLabel;
        private JButton fileSelectButton;
        private JTextField fileSelectTextField;
        private JComboBox combo_mediaType;
        private JCheckBox checkbox_finalizePPT;
        private File mediaFile;
        private File lastSelectedDir;
        private String mediaFileExtension;
        private final int PICTURE = FLGMediaFileChooser.PICTURE;
        private final int AUDIO = FLGMediaFileChooser.AUDIO;
        private final int VIDEO = FLGMediaFileChooser.VIDEO;
        private final int PDF = FLGMediaFileChooser.PDF;
        private final int PPT = FLGMediaFileChooser.PPT;
        private final int EXTERNAL = FLGMediaFileChooser.EXTERNAL;
        private final int DEFAULT_TYPE = PICTURE;
        
        public FLGMediaPoolSpecificPanel() {
            combo_mediaType = new JComboBox();
            for (int i = 0; i < mediaTypes.length; i++) {
                combo_mediaType.addItem(internationalization.getString("label.mediaType." + mediaTypes[i]));
            }
            fileSelectLabel = new JLabel(internationalization.getString("label.filename.text"));
            fileSelectTextField = new JTextField("", 20);
            fileSelectButton = new JButton(internationalization.getString("button.label.browse"));
            fileSelectButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showFileChooserDialog();
                }
            });
            setLayout(new FLGColumnLayout(5, 5));
            add(fileSelectLabel, FLGColumnLayout.LEFT);
            add(fileSelectTextField, FLGColumnLayout.LEFT);
            add(fileSelectButton, FLGColumnLayout.LEFTEND);
            checkbox_finalizePPT = new JCheckBox(internationalization.getString("checkBox.finalizePPT"));
            checkbox_finalizePPT.setEnabled(false);
            combo_mediaType.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectedMediaType = mediaTypes[combo_mediaType.getSelectedIndex()];
                    if(selectedMediaType.equals("ppt")) {
                    	// activate finalize ppt checkbox
                    	checkbox_finalizePPT.setEnabled(true);
                    } else {
                    	checkbox_finalizePPT.setEnabled(false);
                    }
                }
            });
            add(new JLabel(" "), FLGColumnLayout.LEFT);
            add(combo_mediaType, FLGColumnLayout.LEFTEND);
            add(new JLabel(" "), FLGColumnLayout.LEFT);
            add(checkbox_finalizePPT, FLGColumnLayout.LEFTEND);
            combo_mediaType.setSelectedIndex(DEFAULT_TYPE);
            selectedMediaType = mediaTypes[DEFAULT_TYPE];
        }
        
        /**
         * Enables Dialog components.
         * @param <code>boolean</code> enabled
         */
        public void setEnabled(boolean enabled) {
            fileSelectTextField.setEnabled(enabled);
            fileSelectButton.setEnabled(enabled);
            combo_mediaType.setEnabled(enabled);
        }
        
        public boolean overwriteDefaultEntries() {
        	return false;
        }
        
        public String verifyInput(FSLLearningUnitViewElement editedLearningUnitViewElement,
        		FSLLearningUnitViewElement referenceLearningUnitViewElement, boolean asFolder, int insertPosition) {
            if (!asFolder) {
                File fileToVerify = new File(fileSelectTextField.getText());
                if (fileToVerify.exists()) return null;
                else
                    return internationalization.getString("label.filename.text") + ": " +
                    internationalization.getString("text.missingValue");
            }
            else
                return null;
        }
        
        public void setInputFieldsDefaults(FSLLearningUnitViewElement learningUnitViewElement) {
            if (learningUnitViewElement != null) {
                if (!learningUnitViewElement.getFolder()) {
                    String id = ((FLGMediaPoolElement)learningUnitViewElement).getId();
                    fileSelectTextField.setText("" + learningUnitViewElementsManager.resolveRelativeFileName(((FLGMediaPoolElement)learningUnitViewElement).getMediaFileName(),
                    learningUnitViewElement));
                    String mediaType = learningUnitViewElement.getType();
                    for (int i = 0; i < mediaTypes.length; i++) {
                        if (mediaTypes[i].equals(mediaType)) {
                            combo_mediaType.setSelectedIndex(i);
                            if(mediaType.equals("ppt")) {
                            	String mediaFileName = ((FLGMediaPoolElement)learningUnitViewElement).getMediaFileName();
                            	StringBuffer mediaFileNameBuffer = new StringBuffer(mediaFileName);
                            	StringBuffer mediaFileExtension = new StringBuffer();
                                for (int j = 3; j >= 0; j--) {
                                	mediaFileExtension.append(mediaFileNameBuffer.charAt(mediaFileName.length()-j-1));
                                }
                                if(mediaFileExtension.toString().equals(".pps")) {
                                  	// activate finalize ppt checkbox
                                	checkbox_finalizePPT.setSelected(true);
                                } else {	
                                	checkbox_finalizePPT.setSelected(false);
                                }
                            }
                            break;
                        } else{
                        	checkbox_finalizePPT.setEnabled(false);
                        	checkbox_finalizePPT.setSelected(false);
                        }
                    }
                }
                else
                    setEnabled(false);
            }
            else
                fileSelectTextField.setText("");
        }
        
        public String getMediaFileExtension() {
            return mediaFileExtension;
        }
        
        public File getMediaFile() {
            return new File(fileSelectTextField.getText());
        }
        
        public boolean getFinalizePPTFlag() {
        	return checkbox_finalizePPT.isSelected();
        }
        
        public void showFileChooserDialog() {
            FLGMediaFileChooser fileChooser = null;
            if (editToolBar.folderImportIsSelected()) {
                fileChooser = new FLGMediaFileChooser(FLGMediaFileChooser.FOLDER);
            } else {
                fileChooser = new FLGMediaFileChooser(combo_mediaType.getSelectedIndex());
            }
            if (fileChooser.showDialog(lastSelectedDir)) {
                mediaFile = fileChooser.getSelectedFile();
                lastSelectedDir = mediaFile.getParentFile();
                if (mediaFile.exists()) {
                    fileSelectTextField.setText(mediaFile.getAbsolutePath());
                    String fileExtension =  FLGFileUtility.getExtension(mediaFile.getName());
                    StringBuffer title = new StringBuffer(mediaFile.getName());
                    int length = title.length();
                    for (int i=0; i<=fileExtension.length(); i++) {
                        title.deleteCharAt((length - 1) - i * 1);
                    }
                    if (editToolBar.folderImportIsSelected()) {
                        folderImport = true;
                        String folderTitle = mediaFile.getName();
                        if(editToolBar.getLearningUnitViewElementTitle() != null && editToolBar.getLearningUnitViewElementTitle().equals("")) {
                        	editToolBar.setLearningUnitViewElementTitle(folderTitle);
                        }
                        // get files
                        DefaultTreeModel treeModel = FLGFileUtility.loadDirectoryContent(mediaFile);
                        // filter files
                        TreeNode root = (TreeNode) treeModel.getRoot();
                        nodesToDelete.clear();
                        importedFolderFilesStructure = deleteNodes(treeModel, filterFiles(treeModel, root));
                    } else {
                        folderImport=false;
                        editToolBar.setLearningUnitViewElementTitle(title.toString());
                        mediaFileExtension = fileChooser.getFileExtension();
                    }
                    // repaint interaction panel
                    learningUnitViewManager.fireLearningUnitViewEvent(
                        FLGMediaPoolViewEvent.createViewSpecificEvent(FSLLearningUnitViewEvent.SCALE_MODE_CHANGED, learningUnitViewManager.getActiveLearningUnitViewElementId()));
                }
            }
        }
        
        private DefaultTreeModel deleteNodes(DefaultTreeModel treeModel, java.util.List nodeList) {
            for (int i=0; i<nodeList.size(); i++) {
                // get node and remove it
                MutableTreeNode nodeToDelete = (MutableTreeNode) nodeList.get(i);
                treeModel.removeNodeFromParent(nodeToDelete);
            }
            return treeModel;
        }
        
        private java.util.List filterFiles(DefaultTreeModel treeModel, TreeNode nodeToCheck) {
            for (int i=0; i<treeModel.getChildCount(nodeToCheck);i++) {
                TreeNode child = (TreeNode)treeModel.getChild(nodeToCheck,i);
                File fileToCheck = (File) ((DefaultMutableTreeNode)child).getUserObject();
                if (fileToCheck.isDirectory()) {
                    // get children and filter them
                    filterFiles(treeModel, child);
                } else {
                    // check file extension
                    String fileExtension = FLGFileUtility.getExtension(fileToCheck.getName());
                    // pictures
                    if (selectedMediaType.equals(FLGMediaPoolElement.FILE_TYPE_PICTURE)) {
                        if (!fileExtension.equals("gif")
                        && !fileExtension.equals("jpg")
                        && !fileExtension.equals("png")) {
                            nodesToDelete.add((MutableTreeNode)child);
                        }
                    }
                    // audio files
                    if (selectedMediaType.equals(FLGMediaPoolElement.FILE_TYPE_AUDIO)) {
                        if (!fileExtension.equals("mp2")
                        && !fileExtension.equals("mp3")
                        && !fileExtension.equals("wav")
                        && !fileExtension.equals("au")
                        && !fileExtension.equals("midi")) {
                            nodesToDelete.add((MutableTreeNode)child);
                        }
                    }
                    // pdf files
                    if (selectedMediaType.equals(FLGMediaPoolElement.FILE_TYPE_PDF)) {
                        if (!fileExtension.equals("pdf")) {
                            nodesToDelete.add((MutableTreeNode)child);
                        }
                    }
                    // powerpoint files
                    if (selectedMediaType.equals(FLGMediaPoolElement.FILE_TYPE_PPT)) {
                        if (!fileExtension.equals("ppt")) {
                            nodesToDelete.add((MutableTreeNode)child);
                        }
                    }
                    // video files
                    if (selectedMediaType.equals(FLGMediaPoolElement.FILE_TYPE_VIDEO)) {
                        if (!fileExtension.equals("mov")) {
                            nodesToDelete.add((MutableTreeNode)child);
                        }
                    }
                    // external files
                    if (selectedMediaType.equals(FLGMediaPoolElement.FILE_TYPE_EXTERNAL)) {
                        // nothing to delete, maybe later...
                    }
                }
            }
            return nodesToDelete;
        }
        
        public void showFileChooserDialog2() {
            FLGMediaFileChooser fileChooser = new FLGMediaFileChooser(combo_mediaType.getSelectedIndex());
            if (fileChooser.showDialog(lastSelectedDir)) {
                mediaFile = fileChooser.getSelectedFile();
                lastSelectedDir = mediaFile.getParentFile();
                if (mediaFile.exists()) {
                    fileSelectTextField.setText(mediaFile.getAbsolutePath());
                    String fileExtension =  FLGFileUtility.getExtension(mediaFile.getName());
                    StringBuffer title = new StringBuffer(mediaFile.getName());
                    int length = title.length();
                    for (int i=0; i<=fileExtension.length(); i++) {
                        title.deleteCharAt((length-1)-i*1);
                    }
                    editToolBar.setLearningUnitViewElementTitle(title.toString());
                    mediaFileExtension = fileChooser.getFileExtension();
                    // repaint interaction panel
                    learningUnitViewManager.fireLearningUnitViewEvent(FLGMediaPoolViewEvent.createViewSpecificEvent(
                        FSLLearningUnitViewEvent.SCALE_MODE_CHANGED, learningUnitViewManager.getActiveLearningUnitViewElementId()));
                }
            }
        }
    }
}
