package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.dialogs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGDialogInputVerifier;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class is the implementation of a JDialog which shows a error-dialog.
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioFileOverwriteDialog implements FLGDialogInputVerifier {
    private FLGInternationalization internationalization;
    private JPanel dialogContentComponent;
    private JLabel fileExistsLabel;
    private JLabel fileOverwriteLabel;
    private FSLLearningUnitViewElement referenceLearningUnitViewElement;

    /** Constructor of this class. */
    public FLGAudioFileOverwriteDialog() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.dialogs.internationalization",
            getClass().getClassLoader());
        buildIndependentUI();
    }

    /**
     * This method shows a dialog with a textfield and the ok or cancel buttons.
     * @param elementsManager The UnitViewElementsManager of this view.
     * @param referenceElementId The UnitViewElementId of the actual Audio-object
     * @return true if the user klicks on OK false if the user klicks on CANCEL
     */
    public boolean showDialog(FSLLearningUnitViewElementsManager elementsManager, String referenceElementId) {
        this.referenceLearningUnitViewElement = elementsManager.getLearningUnitViewElement(referenceElementId, false);
        //buildDependentUI();
        int returnValue = FLGOptionPane.showConfirmDialog(this, dialogContentComponent,
            internationalization.getString("dialog.soundFileExists.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        if (returnValue == FLGOptionPane.OK_OPTION) {
            return true;
        }
        else
            return false;
    }

    /** The components and the layout of the Dialog are set in this method. */
    private void buildIndependentUI() {
        dialogContentComponent = new JPanel(new BorderLayout());
        dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel mainElementInputPanel = new JPanel(new FLGColumnLayout());
        dialogContentComponent.add(mainElementInputPanel, BorderLayout.NORTH);
        fileExistsLabel = new JLabel(internationalization.getString("label.file.exists"));
        fileOverwriteLabel = new JLabel(internationalization.getString("label.file.overwrite"));
        mainElementInputPanel.setLayout(new FLGColumnLayout(5, 5));
        mainElementInputPanel.add(fileExistsLabel, FLGColumnLayout.CENTEREND);
        mainElementInputPanel.add(fileOverwriteLabel, FLGColumnLayout.CENTEREND);
    }

    public String verifyInput() {
        return null;
    }
}
