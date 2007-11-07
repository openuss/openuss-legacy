package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGDialogInputVerifier;
import freestyleLearningGroup.independent.gui.FLGMediaFileChooser;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class is the implementation of a JDialog which lets the user choose a mediafile from the harddrive (or
 * from the network, cd's...).
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioChooseFileDialog implements FLGDialogInputVerifier {
    private FLGInternationalization internationalization;
    private JPanel dialogContentComponent;
    private FLGMediaFileChooser fileChooser;
    private JLabel fileSelectLabel;
    private JTextField fileSelectTextField;
    private JButton fileSelectButton;
    private File fileName;
    private FSLLearningUnitViewElement referenceLearningUnitViewElement;

    /** Constructor of this class. */
    public FLGAudioChooseFileDialog() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.dialogs.internationalization",
            getClass().getClassLoader());
        buildIndependentUI();
    }

    /**
     * This method shows a dialog with a textfield and a browse-button.
     * @param elementsManager The UnitViewElementsManager of this view.
     * @param referenceElementId The UnitViewElementId of the actual Audio-object
     * @param soundFile The absolute path of the soundfile (if it is set)
     * @return true if the user klicks on OK false if the user klicks on CANCEL
     */
    public boolean showDialog(FSLLearningUnitViewElementsManager elementsManager,
        String referenceElementId, String soundFile) {
            //if a soundfile was set earlier, than it is shown in the textfield
            if (soundFile != null) {
                fileSelectTextField.setText(soundFile);
            }
            this.referenceLearningUnitViewElement = elementsManager.getLearningUnitViewElement(referenceElementId, false);
            int returnValue = FLGOptionPane.showConfirmDialog(this, dialogContentComponent, internationalization.getString("dialog.SoundFile.title"),
                FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
            if (returnValue == FLGOptionPane.CANCEL_OPTION) {
                return false;
            }
            return true;
    }

    /**
     * Returns the name of the mediafile.
     * @return the name of the mediafile.
     */
    public File getMediaFileName() {
        return fileName;
    }

    /** The components and the layout of the Dialog are set in this method. */
    private void buildIndependentUI() {
        dialogContentComponent = new JPanel(new BorderLayout());
        dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel mainElementInputPanel = new JPanel(new FLGColumnLayout());
        dialogContentComponent.add(mainElementInputPanel, BorderLayout.NORTH);
        fileSelectLabel = new JLabel(internationalization.getString("label.filename.text"));
        fileSelectTextField = new JTextField("", 20);
        fileSelectTextField.setEditable(false);
        fileSelectButton = new JButton(internationalization.getString("button.label.browse"));
        //browse-button needs an actionListener
        fileSelectButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showFileChooserDialog();
                }
            });
        mainElementInputPanel.setLayout(new FLGColumnLayout(5, 5));
        //add the components to the mainElementInputPanel
        mainElementInputPanel.add(fileSelectLabel, FLGColumnLayout.LEFT);
        mainElementInputPanel.add(fileSelectTextField, FLGColumnLayout.LEFT);
        mainElementInputPanel.add(fileSelectButton, FLGColumnLayout.LEFTEND);
        mainElementInputPanel.add(new JLabel(" "), FLGColumnLayout.LEFT);
    }

    /** This method shows the FileChooserDialog to search for a mediafile on the harddisk (or somewhere else) */
    public void showFileChooserDialog() {
        fileChooser = new FLGMediaFileChooser(1);
        fileChooser.showDialog();
        fileName = fileChooser.getSelectedFile();
        //if no file is chosen, no filename is set in the textfield.
        if (fileName != null) {
            //test if the extension equals mp2, mp3 or wav
            if (testExtension(fileName))
                //if it is true, then the filename is taken and showed in the textfield
                    fileSelectTextField.setText(fileName + "");
        }
    }

    /**
     * Method to test, if the right extension is given here: mp2, mp3 or wav are accepted
     * @return true if the extension is allowed, false if it is something else
     */
    public boolean testExtension(File fileName) {
        String s = fileName + "";
        s = s.substring(s.length() - 3, s.length());
        if (s.equals("wav") | s.equals("mp2") | s.equals("mp3"))
            return true;
        else
            return false;
    }

    public String verifyInput() {
        return null;
    }
}
