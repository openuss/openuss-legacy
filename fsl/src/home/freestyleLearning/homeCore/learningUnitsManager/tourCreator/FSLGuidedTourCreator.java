/*
 * FSLGuidedTourCreator.java
 *
 * Created on September 10, 2004, 2:06 PM
 */

package freestyleLearning.homeCore.learningUnitsManager.tourCreator;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.xml.bind.Dispatcher;

import freestyleLearning.homeCore.learningUnitsManager.*;
import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding.*;
import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBindingSubclasses.*;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.gui.documents.*;
import freestyleLearningGroup.independent.tourCreator.*;
import freestyleLearningGroup.independent.util.*;

/**
 *
 * @author  Mirko Wahn
 */
public class FSLGuidedTourCreator extends FLGStandardTourCreator implements FSLTourCreator {
    private FLGInternationalization internationalization;
    private boolean updatingComponents;
    protected final String TOUR_CREATOR_DATA_FILENAME = "tours.xml";
    protected final String TOUR_CREATOR_DIRECTORY_NAME = "tourCreator";
    protected FSLLearningUnitViewElementInteractionButton fslCaptureButton;
    protected FSLLearningUnitsManager learningUnitsManager;
    protected String fileSeparator;
    protected File tourCreatorDirectory;
    protected File tourCreatorDescriptorFile;
    protected FSLTourDescriptor descriptor;
    protected FSLTour currentFSLTour;

    /** 
     * Creates a new instance of FSLGuidedTourCreator 
     * @param <code>learningUnitsManager</code> LearningUnitsManager to communicate with
     * @param <code>learningUnitsOriginalDataDirectory</code> original learning unit data directory
     */
    public FSLGuidedTourCreator(FSLLearningUnitsManager learningUnitsManager, File learningUnitsOriginalDataDirectory) {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.tourCreator.internationalization",
                FSLLearningUnitsManager.class.getClassLoader());
        fileSeparator = System.getProperty("file.separator");
        this.learningUnitsManager = learningUnitsManager;
        this.tourCreatorDirectory = new File(learningUnitsOriginalDataDirectory + fileSeparator + "tours");
        // temporary disabled
        if (!tourCreatorDirectory.exists()) tourCreatorDirectory.mkdirs();
        learningUnitsManager.addLearningUnitListener(new FSLGuidedTourCreator_LearningUnitListener());
        fslCaptureButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonCapture.gif"));
        fslCaptureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performCaptureAction(e);
            }
        });
        tourCreatorDescriptorFile = new File(tourCreatorDirectory.getAbsolutePath() + fileSeparator + TOUR_CREATOR_DATA_FILENAME);
        loadTours();     
        updateComponents();
        if (m_toursVector.size() > 0) setCurrentTour((FLGStandardTour)m_toursVector.get(0));
    }    
    
    /** 
     *  Updates tour selection gui items
     *  and activate first tour in list
     */
    protected void updateComponents() {
        updatingComponents = true;
        // Fill tour selection combo box
        cb_tours.removeAllItems();
        for (int i = 0; i < m_toursVector.size(); i++) {
            if (((FLGStandardTour)(m_toursVector.get(i))).getTourIcon() != null) {
                cb_tours.addItem(new JLabel(((FLGStandardTour)(m_toursVector.get(i))).getTourName(), 
                    ((FLGStandardTour)(m_toursVector.get(i))).getTourIcon(), JLabel.HORIZONTAL));
            }
            else {
                 cb_tours.addItem(new JLabel(((FLGStandardTour)m_toursVector.get(i)).getTourName(), 
                    new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif")), JLabel.HORIZONTAL));
            }
        }
        updatingComponents = false;
    }
    
    /**
     * Performs reaction to selection from tour selection combo box
     * @param <code>ActionEvent</code>ActionEvent originated from combo box selection
     */
    public void performTourSelectionAction(ActionEvent e) {
        if (!updatingComponents) {
            setCurrentTour((FLGStandardTour)m_toursVector.get(cb_tours.getSelectedIndex()));
        }
    }
    
   /**
     * Performs reaction to NEW tour selection from menu
     * @param <code>ActionEvent</code>ActionEvent originated from menu selection
     */
    public void m_tourMenuItemNewActionPerformed(ActionEvent event) {
        //creating the new tour
        saveCurrentTour();
        newTour();
        updateComponents();
        cb_tours.setSelectedIndex(cb_tours.getItemCount() - 1);
    }
    
   /**
     * Performs reaction to SAVE tour selection from menu
     * @param <code>ActionEvent</code>ActionEvent originated from menu selection
     */
    public void m_tourMenuItemSaveActionPerformed(ActionEvent event) {
        SaveGuidedTourDialog dialog = new SaveGuidedTourDialog();
        int value = FLGOptionPane.showConfirmDialog(this, dialog, internationalization.getString("dialog.saveTour.title"), FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE, 
            new String[] { internationalization.getString("dialog.button.save"), internationalization.getString("dialog.button.cancel") });
        if (value == FLGOptionPane.APPROVE_OPTION) {
            m_currentTour.setTourName(dialog.getTourName());
            m_currentTour.setTourIcon(dialog.getTourIcon());
            int currentTourIndex = cb_tours.getSelectedIndex();
            String newIconFileName = tourCreatorDirectory.getAbsolutePath() + fileSeparator + m_currentTour.getTourName() + "_tourIcon.jpg";
            BufferedImage bi = FLGUIUtilities.createBufferedImage(dialog.getTourIcon().getImage(), null);
            FLGUIUtilities.saveImage(bi, newIconFileName);  
            saveCurrentTour();
            updateComponents();
            mb_tourModified = false;
            updateJFrameTitle();
            cb_tours.setSelectedIndex(currentTourIndex);
        }
    }

   /**
     * Performs reaction to DELETE selection from menu
     * @param <code>ActionEvent</code>ActionEvent originated from menu selection
     */
    public void m_tourMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        if (FLGOptionPane.showConfirmDialog(
            internationalization.getString("dialog.confirmDeleteTour.message"), 
            internationalization.getString("dialog.confirmDeleteTour.title"), 
            FLGOptionPane.YES_NO_OPTION, FLGOptionPane.WARNING_MESSAGE) == FLGOptionPane.YES_OPTION) {
                // delete tour from descriptor and directory
                int selectedTourIndex = cb_tours.getSelectedIndex();
                descriptor.getTours().remove(selectedTourIndex);
                m_toursVector.remove(selectedTourIndex);
                if (m_toursVector.size() == 0) {
                    newTour();
                }
                updateComponents();
                saveTours();
                setCurrentTour((FLGStandardTour)m_toursVector.get(0));
                show();
        }
    }
    
    /**
     * Read tour descriptor file from tourCreator directory
     * @return Descriptor object for one tour
     */
    protected FSLTourDescriptor loadTourDescriptor() {
        FSLTourDescriptor descriptor = new FSLTourDescriptor();
        if (tourCreatorDescriptorFile.exists()) {
            Dispatcher dispatcher = TourDescriptor.newDispatcher();
            dispatcher.register(TourDescriptor.class, FSLTourDescriptor.class);
            dispatcher.register(Tour.class, FSLTour.class);
            dispatcher.register(TourElement.class, FSLTourElement.class);
            dispatcher.register(TourElementLink.class, FSLTourElementLink.class);
            dispatcher.register(TourElementLinkTarget.class, FSLTourElementLinkTarget.class);
            FileInputStream descriptorFileInputStream;
            try {
                descriptorFileInputStream = new FileInputStream(tourCreatorDescriptorFile);
                descriptor = (FSLTourDescriptor)dispatcher.unmarshal(descriptorFileInputStream);
                descriptorFileInputStream.close();
            }
            catch (Exception e) {
                FLGOptionPane.showMessageDialog("Error loading descriptor: " + e, 
                    "Exeption occurred", FLGOptionPane.ERROR_MESSAGE);
            }
        }
        return descriptor;
    }

    /**
     * Overwrites method inherited from superclass
     * Read descriptor and fill gui components with tours data
     */
    protected void loadTours() {
        m_toursVector = new Vector();
        descriptor = loadTourDescriptor();
        if (descriptor.getTours().size() == 0) {
            FLGStandardTour tour = newTour();
        }
        else {
            for (int i = 0; i < descriptor.getTours().size(); i++) {
                FLGStandardTour tour = loadTour(i);
                m_toursVector.add(tour);
            }
        }
    }

    /**
     * Create FLGStandardTour from XML data
     * @return current FLGStandardTour
     */
    protected FLGStandardTour loadTour(int descriptorEntry) {
        java.util.List tourList = descriptor.getTours();
        FLGStandardTour tour = new FLGStandardTour();
        if (tourList.size() > 0) {
            FSLTour fslTour = (FSLTour)tourList.get(descriptorEntry);
            String tourIcon_absolutePath = tourCreatorDirectory.getAbsolutePath() + fileSeparator + fslTour.getTourIcon();
            tour.setTourName(fslTour.getTourName());
            if (fslTour.getTourIcon() != null) {
                tour.setTourIcon(new ImageIcon(tourIcon_absolutePath));
            }
            else {
                tour.setTourIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif")));
            }
            for (int i = 0; i < fslTour.getTourElements().size(); i++) {
                FSLTourElement xmlElement = (FSLTourElement)fslTour.getTourElements().get(i);
                FSLTourElementLink link = (FSLTourElementLink)xmlElement.getLearningUnitViewElementLinks().get(0);
                FSLTourElementLinkTarget linkTarget = (FSLTourElementLinkTarget)link.getLearningUnitViewElementLinkTargets().get(0);
                FLGTourElement tourElement = new FLGStandardTourElement();
                // Element information object
                FLG2TourCreatorStandardElementInformation information = new FLG2TourCreatorStandardElementInformation();
                information.setTargetLearningUnitId(linkTarget.getTargetLearningUnitId());
                information.setTargetLearningUnitViewManagerId(linkTarget.getTargetLearningUnitViewManagerId());
                information.setTargetLearningUnitViewElementId(linkTarget.getTargetLearningUnitViewElementId());
                String imageAbsolutePath = tourCreatorDirectory.getAbsolutePath() + fileSeparator + xmlElement.getImageFileName();
                BufferedImage image = FLGUIUtilities.createBufferedImage(new ImageIcon(imageAbsolutePath).getImage(), null);
                information.setElementImage(image);
                tourElement.setFLG2TourCreatorElementInformation(information);
                tourElement.setDescription(xmlElement.getDescription());
                tourElement.setElementName(xmlElement.getElementName());
                tourElement.setImage(image);
                tour.addElement(tourElement);
            }
        }
        return tour;
    }

    /**
     * Overrides inherited method
     * Make changes to current tour persistent.
     */
    public void saveCurrentTour() {
        updateCurrentTourDescriptor(cb_tours.getSelectedIndex());
        saveTours();
    }
    
    /**
     * Overrides inherited method
     * Saves all tours to xml descriptor file
     */
    public void saveTours() {
        saveTourDescriptor(descriptor, new File(tourCreatorDirectory.getAbsolutePath(), TOUR_CREATOR_DATA_FILENAME));
        removeUnnecessaryExternalElementFiles();
    }
    
    /**
     * Overrides inherited method
     * Creates new empty tour
     */
    protected FSLGuidedTour newTour() {
        // new Creator tour
        FSLGuidedTour newTour = new FSLGuidedTour();
        newTour.setTourName(internationalization.getString("label.newTour.name"));
        newTour.setTourIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif")));
        m_toursVector.add(newTour);
        setCurrentTour(newTour);
        // descriptor entries
        FSLTour tour = new FSLTour();
        tour.setTourName(newTour.getTourName());
        tour.setId(createTourId());
        descriptor.getTours().add(tour);
        return newTour;
    }

    protected boolean saveTourDescriptor(FSLTourDescriptor descriptor, File descriptorFile) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(descriptorFile);
            descriptor.validate();
            descriptor.marshal(fileOutputStream);
            fileOutputStream.close();
            return true;
        }
        catch(Exception e) {
            FLGOptionPane.showMessageDialog("Error saving descriptor: " + e, 
                "Exeption occurred", FLGOptionPane.ERROR_MESSAGE);
            try {
                fileOutputStream.close();
            }
            catch(Exception ex) {
                System.out.println(ex);
            }
        }
        return false;
    }


    private void removeUnnecessaryExternalElementFiles() {
        String[] existingFileNames = tourCreatorDirectory.list();
        java.util.List filesToPreserveList = new ArrayList();
        int noTours = descriptor.getTours().size();
        // loop tours and find file names to preserve
        filesToPreserveList.add(TOUR_CREATOR_DATA_FILENAME);
        for (int i = 0; i < noTours; i++) {
            FSLTour tour = (FSLTour)descriptor.getTours().get(i);
            filesToPreserveList.add(tour.getTourIcon());
            for (int j = 0; j < tour.getTourElements().size(); j++) {
                FSLTourElement element = (FSLTourElement)tour.getTourElements().get(j);
                filesToPreserveList.add(element.getImageFileName());
             }
        }
        String[] fileNamesToPreserve = (String[])filesToPreserveList.toArray(new String[] { });
        
        // compare directory content with files to preserve
        for (int i = 0; i < existingFileNames.length; i++) {
            if (!filesToPreserveList.contains(existingFileNames[i])) {
                (new File(tourCreatorDirectory + "/" + existingFileNames[i])).delete();
            }
        }
    }

    protected void updateCurrentTourDescriptor(int index) {        
        FSLTour tour;
        if ((index >= 0) && (descriptor.getTours().size() > index)) {
            tour = (FSLTour)(descriptor.getTours().get(index));
        }
        else {
            tour = new FSLTour();
            tour.setId(createTourId());
            descriptor.getTours().add(tour);
        }
        tour.emptyTourElements();        
        tour.setTourIcon(m_currentTour.getTourName() + "_tourIcon.jpg");
        tour.setTourName(m_currentTour.getTourName());
        for (int i = 0; i < jList1.getModel().getSize(); i++) {
            FLGTourElement tourElement = (FLGTourElement)m_currentTour.getElementAt(i);
            FLG2TourCreatorElementInformation linkInformation = tourElement.getFLG2TourCreatorElementInformation();
            FSLTourElementLink link = new FSLTourElementLink();
            link.setId("tourLink" + i);
            link.addNewLearningUnitViewElementLinkTarget(linkInformation.getTargetLearningUnitId(),
                linkInformation.getTargetLearningUnitViewManagerId(),
                linkInformation.getTargetLearningUnitViewElementId());
            FSLTourElement xmlTourElement = new FSLTourElement();
            xmlTourElement.setId(createTourElementId(linkInformation, tour));
            xmlTourElement.setElementName(tourElement.getElementName());
            xmlTourElement.setDescription(tourElement.getDescription());
            xmlTourElement.getLearningUnitViewElementLinks().add(link);
            Image image = tourElement.getImage();
            // workaround to prevent saving black images from currently edited items
            showImage(image);
            BufferedImage bufferedImage = FLGUIUtilities.createBufferedImage(image, null);
            String imageName = createImageName(m_currentTour.getTourName() + "_" + xmlTourElement.getId() + "_", "tourImage");
            FLGUIUtilities.saveImage(bufferedImage, tourCreatorDirectory.getAbsolutePath() + fileSeparator + imageName);
            xmlTourElement.setImageFileName(imageName);
            xmlTourElement.setDisplayTime(tourElement.getDisplayTime());
            tour.getTourElements().add(xmlTourElement);
        }
    }
    
    public void showImage(Image image) {
        JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
//        frame.setVisible(true);
        frame.dispose();        
    }
    
    /** 
     * Overrides inherited methods
     * returns button to be placed in FSLElementInteractionPanel
     */
    public FSLLearningUnitViewElementInteractionButton getCaptureButton() {
        return fslCaptureButton;
    }

    private String createTourElementId(FLG2TourCreatorElementInformation linkInformation, FSLTour tour) {
        String id = linkInformation.getTargetLearningUnitId() + "-" + linkInformation.getTargetLearningUnitViewManagerId()
                        + "-" + linkInformation.getTargetLearningUnitViewElementId();
        if (elementIdExists(id)) {
            id = id + (new java.util.Date()).getTime();
        }
        return id;
    }

    private String createTourId() {
        return "FSLTour_" + (new java.util.Date()).getTime();
    }

    private boolean elementIdExists(String id) {
        java.util.List tours = descriptor.getTours();
        for (int ix_tours = 0; ix_tours < tours.size(); ix_tours++) {
            FSLTour tour = (FSLTour)descriptor.getTours().get(ix_tours);
            if (tour.getTourElements() != null) {
                for (int i = 0; i < tour.getTourElements().size(); i++) {
                    FSLTourElement tourElement = (FSLTourElement)tour.getTourElements().get(i);
                    if (tourElement.getId().equalsIgnoreCase(id)) return true;
                }
            }
        }
        return false;
    }

    private boolean tourIdExists(String id, FSLTour tour) {
        for (int i = 0; i < descriptor.getTours().size(); i++) {
            FSLTour existingTour = (FSLTour)(descriptor.getTours().get(i));
            if (existingTour.getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    private String createImageName(String baseName, String imagePrefix) {
        String name = tourCreatorDirectory.getAbsolutePath() + fileSeparator + baseName + imagePrefix;
        int i = 1;
        while (new File(name + i + ".jpg").exists()) {
            i++;
        }
        return (baseName + imagePrefix + i + ".jpg");
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/tourCreator/images/" + imageFileName));
    }
    
    private class SaveGuidedTourDialog extends JPanel implements FLGDialogInputVerifier {
        private JTextField tf_tourName;
        private ImageIcon tourIcon;
        private String tourIconName;
        private JButton button_changeIcon;
        private JLabel label_icon;
        private JFileChooser fileChooser;
        
        public SaveGuidedTourDialog() {
            tourIcon = m_currentTour.getTourIcon();
            if (tourIcon == null) tourIcon = new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif"));
            label_icon = new JLabel(tourIcon);
            tf_tourName = new JTextField(20);
            tf_tourName.setDocument(new FLGNameDocument(true));
            tf_tourName.setText(m_currentTour.getTourName());
            tf_tourName.selectAll();
            button_changeIcon = new JButton(internationalization.getString("button.changeTourIcon.text"));
            button_changeIcon.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   changeIcon();
               }
            });
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File file) {
                    if (file.isDirectory()) return true;
                    if (getExtension(file).equalsIgnoreCase(".jpg") 
                        || getExtension(file).equalsIgnoreCase(".gif")) return true;
                    return false;
                }
                
                public String getDescription() {
                    return internationalization.getString("fileFilter.description.label");
                }
                
                private String getExtension(File file) {
                    int ix = file.getAbsolutePath().lastIndexOf('.');
                    if (ix > 0) return file.getAbsolutePath().substring(ix);
                    return "";
                }
            });
            
            setLayout(new FLGColumnLayout());
            add(new JLabel(internationalization.getString("label.tourName.text")), FLGColumnLayout.LEFT);
            add(tf_tourName, FLGColumnLayout.LEFTEND);
            add(new JLabel(internationalization.getString("label.tourLabel.text")), FLGColumnLayout.LEFT);
            add(label_icon, FLGColumnLayout.LEFTEND);
            add(new JLabel(" "), FLGColumnLayout.LEFT);
            add(button_changeIcon, FLGColumnLayout.LEFTEND);
        }
        
        
        private void changeIcon() {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Image newImage = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()).getImage();
                ImageObserver observer = new ImageObserver() {
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                };
                int height = newImage.getHeight(observer);
                int width  = newImage.getWidth(observer);
                double scaleFactor = Math.min(64./width, 64./height);
                tourIcon = new ImageIcon(newImage.getScaledInstance((int)(width*scaleFactor), (int)(height*scaleFactor), Image.SCALE_SMOOTH));
                label_icon.setIcon(tourIcon);
                repaint();
            }
        }
        
        public String getTourName() {
            return tf_tourName.getText();
        }
        
        public ImageIcon getTourIcon() {
            return tourIcon;
        }
        
        public String getTourIconName() {
            return tourIconName;
        }
     
        public String verifyInput() {
            return null;
        }
    }

    private class FSLGuidedTourCreator_LearningUnitListener extends FSLLearningUnitAdapter {
        private void updateLearningUnitDirectory() {
            if (learningUnitsManager.getActiveLearningUnitId() != null) {
                tourCreatorDirectory = new File(learningUnitsManager.getLearningUnitPath() + fileSeparator + TOUR_CREATOR_DIRECTORY_NAME);
                if (!tourCreatorDirectory.exists()) tourCreatorDirectory.mkdirs();
            }
        }

        public void learningUnitActivated(FSLLearningUnitEvent event) {
            // updateLearningUnitDirectory();
        }

        public void learningUnitUserViewChanged(FSLLearningUnitEvent event) {

        }

        public void learningUnitsUserDirectoryChanged(FSLLearningUnitEvent event) {
        }
    }
}
