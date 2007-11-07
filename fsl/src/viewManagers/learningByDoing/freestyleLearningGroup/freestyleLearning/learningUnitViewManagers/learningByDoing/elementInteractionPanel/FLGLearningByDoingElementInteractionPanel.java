/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.elementInteractionPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FLGLearningByDoingElementInteractionPanel extends FSLAbstractLearningUnitViewElementInteractionPanel {
    private FSLLearningUnitViewElementInteractionButton playButton;
    private FLGInternationalization internationalization;

    public FLGLearningByDoingElementInteractionPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.elementInteractionPanel.internationalization",
            getClass().getClassLoader());
        playButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPlay.gif"));
        playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
        playButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    play();
                }
            });
    }

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitViewManager.addLearningUnitViewListener(new FLGInteractionPanel_Adapter());
    }

    private void play() {
        // Execute Program
        FLGLearningByDoingElement learningByDoingElement =
            (FLGLearningByDoingElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
        String executableFileName = learningByDoingElement.getExecutableFileName();
        File executableFile = learningUnitViewElementsManager.resolveRelativeFileName(learningByDoingElement.getExecutableFileName(),
            learningByDoingElement);
        String executableFilePath = executableFile.getAbsolutePath();
        String parameters = learningByDoingElement.getProgramParameters();
        if (learningByDoingElement.hasAutoChangeWorkingDirectory() && learningByDoingElement.getAutoChangeWorkingDirectory()) {
           int lastFileSeparatorIndex = executableFilePath.lastIndexOf(System.getProperty("file.separator"));
           if (lastFileSeparatorIndex < 0) lastFileSeparatorIndex = executableFilePath.lastIndexOf("/");
           String prefix = "user.dir=";
           parameters = prefix + executableFilePath.substring(0, lastFileSeparatorIndex) + " " + parameters;
        }
        FLGPlatformSpecifics.startExternalApplication(executableFilePath, parameters);
    }

    protected void buildDependentUI() {
        super.buildDependentUI();
        if (learningUnitViewElementsManager != null) {
            FLGLearningByDoingElement learningByDoingElement =
                (FLGLearningByDoingElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
            if (learningByDoingElement != null) {
                if (learningByDoingElement.getFolder()) {
                    playButton.setEnabled(false);
                }
                else {
                    playButton.setEnabled(true);
                }
            }
        }
        else {
            playButton.setEnabled(false);
        }
    }

    protected void insertViewSpecificInteractionComponents() {
        add(playButton);
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
    }

    class FLGInteractionPanel_Adapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                FLGLearningByDoingElement learningUnitViewElement =
                    (FLGLearningByDoingElement)learningUnitViewElementsManager.getLearningUnitViewElement(event.getActiveLearningUnitViewElementId(), false);
                if (learningUnitViewElement != null && !learningUnitViewElement.getFolder()) {
                    playButton.setEnabled(true);
                }
                else
                    playButton.setEnabled(false);
            }
        }
    }
}
