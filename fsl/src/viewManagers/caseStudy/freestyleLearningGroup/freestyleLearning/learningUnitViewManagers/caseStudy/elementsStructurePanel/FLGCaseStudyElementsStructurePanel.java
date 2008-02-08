/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.caseStudy.elementsStructurePanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.FSLAbstractLearningUnitViewElementsStructurePanel;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs.FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.caseStudy.data.xmlBindingSubclasses.FLGCaseStudyElement;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGSelectableFileChooser;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGFileUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGCaseStudyElementsStructurePanel extends FSLAbstractLearningUnitViewElementsStructurePanel {
    private ViewSpecificPanel learningUnitViewNewAndModifyElementDialogViewSpecificPane;
    private FLGInternationalization internationalization;

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.caseStudy.elementsStructurePanel.internationalization",
                getClass().getClassLoader());
            learningUnitViewNewAndModifyElementDialogViewSpecificPane = new ViewSpecificPanel();
    }

    public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
        FLGCaseStudyElement caseStudyElement = (FLGCaseStudyElement)element;
        caseStudyElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (!element.getFolder()) {
            caseStudyElement.setType(learningUnitViewNewAndModifyElementDialogViewSpecificPane.getViewElementType());
            if (caseStudyElement.getType().equals(FLGCaseStudyElement.ELEMENT_TYPE_PDF)) {
                // PDF-File
                caseStudyElement.setHtmlFileName(null);
                File sourceExecutableFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getExecutableFile();
                String sourceExecutableFileName = sourceExecutableFile.getName();
                if (!sourceExecutableFileName.equals(caseStudyElement.getDocumentFileName())) {
                    FLGUIUtilities.startLongLastingOperation();
                    File destinationDirectory = learningUnitViewElementsManager.getLearningUnitViewDataDirectory();
                    FLGFileUtility.copyFile(sourceExecutableFile,
                        new File(destinationDirectory.getPath() + "//" + sourceExecutableFile.getName()));
                    caseStudyElement.setDocumentFileName(sourceExecutableFileName);
                    FLGUIUtilities.stopLongLastingOperation();
                }
            }
            else {
                // non-PDF
                caseStudyElement.setDocumentFileName(null);
            }
        }
        else {
            caseStudyElement.setType(FLGCaseStudyElement.ELEMENT_TYPE_FOLDER);
        }
    }

    public FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, boolean folder) {
        FLGCaseStudyElement newElement = new FLGCaseStudyElement();
        newElement.setId(id);
        newElement.setParentId(parentId);
        newElement.setTitle(title);
        newElement.setFolder(folder);
        newElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (!newElement.getFolder()) {
            newElement.setType(learningUnitViewNewAndModifyElementDialogViewSpecificPane.getViewElementType());
            if (newElement.getType().equals(FLGCaseStudyElement.ELEMENT_TYPE_PDF)) {
                File sourceExecutableFile = learningUnitViewNewAndModifyElementDialogViewSpecificPane.getExecutableFile();
                String sourceExecutableFileName = sourceExecutableFile.getName();
                if (!sourceExecutableFileName.equals(newElement.getDocumentFileName())) {
                    FLGUIUtilities.startLongLastingOperation();
                    File destinationDirectory = learningUnitViewElementsManager.getLearningUnitViewDataDirectory();
                    FLGFileUtility.copyFile(sourceExecutableFile,
                        new File(destinationDirectory.getPath() + "//" + sourceExecutableFile.getName()));
                    newElement.setDocumentFileName(sourceExecutableFileName);
                    FLGUIUtilities.stopLongLastingOperation();
                }
            }
        }
        else
            newElement.setType(FLGCaseStudyElement.ELEMENT_TYPE_FOLDER);
        return newElement;
    }

    public FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane
        getLearningUnitViewNewAndModifyElementDialogViewSpecificPane() {
            return learningUnitViewNewAndModifyElementDialogViewSpecificPane;
    }

    class ViewSpecificPanel extends JPanel implements FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane {
        private JLabel fileSelectLabel;
        private JButton fileSelectButton;
        private JTextField fileSelectTextField;
        private File executableFile;
        private File lastSelectedDir;
        private String executableFileExtension;
        private JComboBox typeSelectionComboBox;
        private final String CASE = internationalization.getString("comboBox.item.case");
        private final String PDF = internationalization.getString("comboBox.item.pdf");
        private String viewElementType;

        public ViewSpecificPanel() {
            fileSelectLabel = new JLabel(internationalization.getString("label.filename.text"));
            fileSelectTextField = new JTextField("", 20);
            fileSelectButton = new JButton(internationalization.getString("button.label.browse"));
            fileSelectButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showFileChooserDialog();
                    }
                });
            typeSelectionComboBox = new JComboBox();
            typeSelectionComboBox.addItem(CASE);
            typeSelectionComboBox.addItem(PDF);
            typeSelectionComboBox.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setViewElementType(typeSelectionComboBox.getSelectedItem().toString());
                    }
                });
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder(internationalization.getString("border.label.text")));
            JPanel componentPanel = new JPanel(new FLGColumnLayout(5, 5));
            componentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            componentPanel.add(typeSelectionComboBox, FLGColumnLayout.LEFTEND);
            componentPanel.add(fileSelectLabel, FLGColumnLayout.LEFTEND);
            componentPanel.add(fileSelectTextField, FLGColumnLayout.LEFT);
            componentPanel.add(fileSelectButton, FLGColumnLayout.LEFTEND);
            componentPanel.add(new JLabel(" "), FLGColumnLayout.LEFT);
            add(componentPanel, BorderLayout.CENTER);
            setViewElementType(CASE);
        }
        
        public boolean overwriteDefaultEntries() {
        	return false;
        }
     
        public String getViewElementType() {
            if (viewElementType.equals(PDF)) return FLGCaseStudyElement.ELEMENT_TYPE_PDF;
            else
                return FLGCaseStudyElement.ELEMENT_TYPE_CASE;
        }

        private void setViewElementType(String type) {
            viewElementType = type;
            enableFileSelectionComponents(type.equals(PDF));
        }

        private void enableFileSelectionComponents(boolean enabled) {
            fileSelectLabel.setEnabled(enabled);
            fileSelectButton.setEnabled(enabled);
            fileSelectTextField.setEnabled(enabled);
            if (!enabled) fileSelectTextField.setText("");
        }

        public void setEnabled(boolean enabled) {
            enableFileSelectionComponents(enabled && viewElementType.equals(PDF));
            typeSelectionComboBox.setEnabled(enabled);
        }

        public String verifyInput(FSLLearningUnitViewElement editedLearningUnitViewElement,
            FSLLearningUnitViewElement referenceLearningUnitViewElement, boolean asFolder, int insertPosition) {
                if (getViewElementType().equals(FLGCaseStudyElement.ELEMENT_TYPE_PDF)) {
                    File fileToVerify = new File(fileSelectTextField.getText());
                    if (fileToVerify != null && fileToVerify.exists()) return null;
                    return internationalization.getString("label.filename.text") + ": " +
                        internationalization.getString("text.missingValue");
                }
                return null;
        }

        public void setInputFieldsDefaults(FSLLearningUnitViewElement learningUnitViewElement) {
            if (learningUnitViewElement != null) {
                if (!learningUnitViewElement.getFolder()) {
                    FLGCaseStudyElement caseStudyElement = (FLGCaseStudyElement)learningUnitViewElement;
                    if (caseStudyElement.getType().equals(PDF)) {
                        String id = caseStudyElement.getId();
                        fileSelectTextField.setText("" + learningUnitViewElementsManager.resolveRelativeFileName(caseStudyElement.getDocumentFileName(),
                            learningUnitViewElement));
                    }
                }
                else
                    setEnabled(false);
            }
            else
                setEnabled(false);
        }

        public String getExecutableFileExtension() {
            return "." + executableFileExtension;
        }

        public File getExecutableFile() {
            return new File(fileSelectTextField.getText());
        }

        public void showFileChooserDialog() {
            FLGSelectableFileChooser fileChooser = new FLGSelectableFileChooser(internationalization.getString("label.fileType.text"), ".pdf");
            if (fileChooser.showDialog(lastSelectedDir)) {
                executableFile = fileChooser.getSelectedFile();
                lastSelectedDir = executableFile.getParentFile();
                if (executableFile.exists()) {
                    fileSelectTextField.setText(executableFile.getAbsolutePath());
                    executableFileExtension = fileChooser.getFileExtension();
                }
            }
        }
    }
}