/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementInteractionPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

abstract public class FSLAbstractLearningUnitViewElementInteractionPanel extends JPanel implements
    FSLLearningUnitViewElementInteractionPanel {
        public final static int STANDARD_SEPARATOR_WIDTH = 10;
        private boolean viewIsActive;
        private boolean splitMode;
        private boolean splitModeAllowed;
        private boolean fullScreenModeAllowed;
        private boolean scaleToScreenAllowed;
        private boolean scaleToFit;
        protected FSLLearningUnitViewElementInteractionButton scaleToggleButton;
        protected FSLLearningUnitViewElementInteractionButton fullScreenModeButton;
        protected FSLLearningUnitViewElementInteractionButton splitModeButton;
        protected FSLLearningUnitViewElementInteractionButton splitModeSwitchButton;
        protected FSLLearningUnitViewElementInteractionButton nextElementButton;
        protected FSLLearningUnitViewElementInteractionButton previousElementButton;
        protected boolean editMode;
        protected boolean fullScreenSelected;
        protected FSLLearningUnitViewManager learningUnitViewManager;
        protected FSLLearningUnitViewElementsManager learningUnitViewElementsManager;
        protected String activeLearningUnitViewElementId;
        protected String secondaryActiveLearningUnitViewElementId;
        protected FLGInternationalization internationalization;

        public FSLAbstractLearningUnitViewElementInteractionPanel() {
            internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.elementInteractionPanel.internationalization",
                getClass().getClassLoader());
            setSplitModeAllowed(false);
            setFullScreenModeAllowed(false);
            setScaleModeAllowed(false);
        }

        public void init(FSLLearningUnitViewManager learningUnitViewManager,
            FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
                this.learningUnitViewManager = learningUnitViewManager;
                learningUnitEventGenerator.addLearningUnitListener(
                    new FSLLearningUnitViewElementInteractionPanel_LearningUnitAdapter());
                this.editMode = editMode;
                learningUnitViewManager.addLearningUnitViewListener(
                    new FSLLearningUnitViewElementInteractionPanel_LearningUnitViewVetoableAdapter());
                learningUnitViewManager.addLearningUnitViewListener(
                    new FSLLearningUnitViewElementInteractionPanel_LearningUnitViewAdapter());
                buildIndependentUI();
                buildDependentUI();
        }

        protected void addSeparator(int width) {
            add(javax.swing.Box.createHorizontalStrut(width));
        }

        protected void addSeparator() {
            addSeparator(STANDARD_SEPARATOR_WIDTH);
        }

        protected void setScaleToFit(boolean scaleToFit) {
            this.scaleToFit = scaleToFit;
        }

        public void setSplitModeAllowed(boolean splitModeAllowed) {
            this.splitModeAllowed = splitModeAllowed;
        }

        public void setFullScreenModeAllowed(boolean fullScreenModeAllowed) {
            this.fullScreenModeAllowed = fullScreenModeAllowed;
        }

        protected void setScaleModeAllowed(boolean scaleToScreenAllowed) {
            this.scaleToScreenAllowed = scaleToScreenAllowed;
        }

        public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
            this.learningUnitViewElementsManager = learningUnitViewElementsManager;
            activeLearningUnitViewElementId = null;
            secondaryActiveLearningUnitViewElementId = null;
            splitMode = false;
            buildDependentUI();
        }

        protected void buildDefaultNavigationButtons(String previousElementButtonToolTipText,
            String nextElementButtonToolTipText) {
                nextElementButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonNextElement.gif"));
                nextElementButton.setToolTipText(nextElementButtonToolTipText);
                previousElementButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPreviousElement.gif"));
                previousElementButton.setToolTipText(previousElementButtonToolTipText);
                nextElementButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            nextPage();
                        }
                    });
                previousElementButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            previousPage();
                        }
                    });
        }

        protected void buildIndependentUI() {
            setLayout(new FLGLeftToRightLayout(5));
            setOpaque(false);
            splitModeButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonSplitMode.gif"));
            splitModeSwitchButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonSplitModeSwitched.gif"));
            splitModeSwitchButton.setToolTipText(internationalization.getString("button.splitModeSwitch.toolTipText"));
            splitModeSwitchButton.setEnabled(false);
            splitModeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        toggleSplitMode();
                    }
                });
            splitModeSwitchButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        switchElements();
                    }
                });
            fullScreenModeButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonFullScreenMode.gif"));
            fullScreenModeButton.setToolTipText(internationalization.getString("button.fullScreen.toolTipText"));
            fullScreenModeButton.setEnabled(false);
            fullScreenModeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setFullScreenMode(!fullScreenSelected);
                    }
                });
            scaleToggleButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonToggleScale.gif"));
            scaleToggleButton.setToolTipText(internationalization.getString("button.toggleScale.toolTipText"));
            scaleToFit = false;
            scaleToggleButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        toggleScale();
                    }
                });
        }

        protected void buildDependentUI() {
            removeAll();

	            if (splitModeAllowed) {
	                add(splitModeButton);
	                add(splitModeSwitchButton);
	                splitModeSwitchButton.setEnabled(splitMode || secondaryActiveLearningUnitViewElementId != null);
	                splitModeButton.setEnabled(activeLearningUnitViewElementId != null ||
	                    secondaryActiveLearningUnitViewElementId != null);
	                if (splitMode)
	                    splitModeButton.setToolTipText(internationalization.getString("button.turnSplitModeOff.toolTipText"));
	                else
	                    splitModeButton.setToolTipText(internationalization.getString("button.turnSplitModeOn.toolTipText"));
	            }
	            if (previousElementButton != null) add(previousElementButton);
	            if (nextElementButton != null) add(nextElementButton);
	            if (learningUnitViewElementsManager != null) {
	                if (nextElementButton != null)
	                    nextElementButton.setEnabled(learningUnitViewElementsManager.getNextElementIdInDepthFirstOrder(activeLearningUnitViewElementId)
	                        != null);
	                if (previousElementButton != null)
	                    previousElementButton.setEnabled(learningUnitViewElementsManager.getPreviousElementIdInDepthFirstOrder(activeLearningUnitViewElementId)
	                        != null);
	            }
	            else {
	                if (nextElementButton != null) nextElementButton.setEnabled(false);
	                if (previousElementButton != null) previousElementButton.setEnabled(false);
	            }
	            if (learningUnitViewElementsManager != null) {
	                FSLLearningUnitViewElement learningUnitViewElement = learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
	                if (learningUnitViewElement != null && !learningUnitViewElement.getFolder() && fullScreenModeAllowed) {
	                    add(fullScreenModeButton);
	                    fullScreenModeButton.setEnabled(activeLearningUnitViewElementId != null);
	                }
	            }
	            if (scaleToScreenAllowed) {
	                add(scaleToggleButton);
	                scaleToggleButton.setEnabled(activeLearningUnitViewElementId != null);
	                if (!scaleToFit) {
	                	scaleToggleButton.setToolTipText(internationalization.getString("button.toggleScale.undoScale.toolTipText"));
	                	scaleToggleButton.setImage(loadImage("buttonToggleScale.gif"));
	                } else {
	                	scaleToggleButton.setToolTipText(internationalization.getString("button.toggleScale.scale.toolTipText"));
	                    scaleToggleButton.setImage(loadImage("buttonToggleNoScale.gif"));
	                }
	            }
	            insertViewSpecificInteractionComponents();
	            revalidate();
	            repaint();
        }

        // overwrite to insert view specific buttons
        protected void insertViewSpecificInteractionComponents() {
        }

        private void toggleScale() {
            if (scaleToFit) {
                scaleToggleButton.setImage(loadImage("buttonToggleScale.gif"));
            }
            else {
                scaleToggleButton.setImage(loadImage("buttonToggleNoScale.gif"));
            }
            scaleToFit = !scaleToFit;
            FSLLearningUnitViewEvent learningUnitViewEvent =
                FSLLearningUnitViewEvent.createScaleModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(), scaleToFit);
            learningUnitViewManager.fireLearningUnitViewEvent(learningUnitViewEvent);
        }

        private void toggleSplitMode() {
            splitMode = !splitMode;
            if (splitMode) {
                secondaryActiveLearningUnitViewElementId = activeLearningUnitViewElementId;
            }
            else {
                secondaryActiveLearningUnitViewElementId = null;
            }
            splitModeSwitchButton.setEnabled(splitMode);
            FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
            learningUnitViewManager.fireLearningUnitViewEvent(event);
        }

        protected void setFullScreenMode(boolean fullScreenRequested) {
            this.fullScreenSelected = fullScreenRequested;
            FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                activeLearningUnitViewElementId, fullScreenRequested, false);
            learningUnitViewManager.fireLearningUnitViewEvent(event);
        }

        private void switchElements() {
            String elementId = activeLearningUnitViewElementId;
            activeLearningUnitViewElementId = secondaryActiveLearningUnitViewElementId;
            secondaryActiveLearningUnitViewElementId = elementId;
            FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, true);
            learningUnitViewManager.fireLearningUnitViewEvent(event);
        }

        private void nextPage() {
            String nextLearningUnitViewElementId =
                learningUnitViewElementsManager.getNextElementIdInDepthFirstOrder(activeLearningUnitViewElementId);
            activateLearningUnitViewElement(nextLearningUnitViewElementId);
        }

        private void previousPage() {
            String previousLearningUnitViewElementId =
                learningUnitViewElementsManager.getPreviousElementIdInDepthFirstOrder(activeLearningUnitViewElementId);
            activateLearningUnitViewElement(previousLearningUnitViewElementId);
        }

        private void activateLearningUnitViewElement(String learningUnitViewElementId) {
            FSLLearningUnitViewVetoableEvent vetoableEvent =
                FSLLearningUnitViewVetoableEvent.createElementActivatingEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                learningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
            learningUnitViewManager.fireLearningUnitViewEvent(vetoableEvent);
            if (!vetoableEvent.isVeto()) {
                FSLLearningUnitViewEvent nonVetoableEvent =
                    FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                    learningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
                learningUnitViewManager.fireLearningUnitViewEvent(nonVetoableEvent);
            }
        }

        private Image loadImage(String imageFileName) {
            return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/elementInteractionPanel/images/" +
                imageFileName));
        }

        class FSLLearningUnitViewElementInteractionPanel_LearningUnitViewAdapter extends FSLLearningUnitViewAdapter {
            public void learningUnitViewFullScreenModeSelected(FSLLearningUnitViewEvent event) {
                fullScreenSelected = event.isFullScreenModeRequested();
            }
        }


        class FSLLearningUnitViewElementInteractionPanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
            public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
                editMode = event.isEditMode();
            }
        }


        class FSLLearningUnitViewElementInteractionPanel_LearningUnitViewVetoableAdapter extends
            FSLLearningUnitViewVetoableAdapter {
                public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
                    if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                        activeLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
                        secondaryActiveLearningUnitViewElementId = event.getSecondaryActiveLearningUnitViewElementId();
                        splitMode = secondaryActiveLearningUnitViewElementId != null;
                        buildDependentUI();
                    }
                }

                public void learningUnitViewActivated(FSLLearningUnitViewEvent event) {
                    viewIsActive = true;
                    buildDependentUI();
                }

                public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
                    viewIsActive = true;
                }
        }
}