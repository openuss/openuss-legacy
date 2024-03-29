/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.learningUnitsManager;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitsActivator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGMainFrameToolBarButton;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGInternationalization;

class FSLLearningUnitsNavigationHistoryManager extends FSLLearningUnitViewAdapter {
    private FLGMainFrameToolBarButton button_nextLearningUnitViewElement;
    private FLGMainFrameToolBarButton button_previousLearningUnitViewElement;
    private FSLLearningUnitViewsManager learningUnitViewsManager;
    private Vector historyElements;
    private int currentHistoryElementIndex;
    private String currentLearningUnitId;
    private FLGInternationalization internationalization;
    private FSLLearningUnitsActivator learningUnitsActivator;

    public void init(FSLLearningUnitsActivator learningUnitsActivator, FSLLearningUnitViewsManager learningUnitViewsManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator) {
            this.learningUnitsActivator = learningUnitsActivator;
            this.learningUnitViewsManager = learningUnitViewsManager;
            learningUnitEventGenerator.addLearningUnitListener(
                new FSLLearningUnitsNavigationHistoryManager_LearningUnitAdapter());
            historyElements = new Vector();
            currentHistoryElementIndex = -1;
            internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.internationalization",
                getClass().getClassLoader());
            buildIndependentUI();
            buildDependentUI();
    }

    public FLGMainFrameToolBarButton[] getMainFrameToolBarButtons() {
        return new FLGMainFrameToolBarButton[] {
            button_previousLearningUnitViewElement, button_nextLearningUnitViewElement
        };
    }

    public void learningUnitViewElementsSelected(FSLLearningUnitViewEvent event) {
    }

    public void learningUnitViewElementsMoved(FSLLearningUnitViewEvent event) {
    }

    public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
        if (event.getActiveLearningUnitViewElementId() != null) {
            FSLLearningUnitsNavigationHistoryElement historyElement =
                new FSLLearningUnitsNavigationHistoryElement(currentLearningUnitId, event.getLearningUnitViewManagerId(),
                event.getActiveLearningUnitViewElementId(), event.getSecondaryActiveLearningUnitViewElementId());
            if (!historyElement.equals(getCurrentHistoryElement())) {
                addElementToHistory(historyElement);
            }
            buildDependentUI();
        }
    }

    public void learningUnitViewElementsCreated(FSLLearningUnitViewEvent event) {
    }
    
    public void learningUnitViewElementsModified(FSLLearningUnitViewEvent event) {
    }
    
    public void learningUnitViewElementsRemoved(FSLLearningUnitViewEvent event) {
        clearHistory();
    }

    private void clearHistory() {
        historyElements = new Vector();
        currentHistoryElementIndex = -1;
        buildDependentUI();
    }

    private void buildIndependentUI() {
        button_previousLearningUnitViewElement = new FLGMainFrameToolBarButton(loadImage("images/previousElement.gif"));
        button_previousLearningUnitViewElement.setToolTipText(internationalization.getString("button.previousElement.toolTipText"));
        button_nextLearningUnitViewElement = new FLGMainFrameToolBarButton(loadImage("images/nextElement.gif"));
        button_nextLearningUnitViewElement.setToolTipText(internationalization.getString("button.nextElement.toolTipText"));
        button_previousLearningUnitViewElement.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Thread() {
                        public void run() {
                           moveToPreviousLearningUnitViewElement();
                        }
                    }.start();
                }
            });
        button_nextLearningUnitViewElement.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Thread() {
                        public void run() {
                           moveToNextLearningUnitViewElement();
                        }
                    }.start();
                }
            });
    }

    private void buildDependentUI() {
        button_previousLearningUnitViewElement.setEnabled(currentHistoryElementIndex > 0);
        button_nextLearningUnitViewElement.setEnabled(currentHistoryElementIndex < historyElements.size() - 1);
    }

    private FSLLearningUnitsNavigationHistoryElement getCurrentHistoryElement() {
        if (currentHistoryElementIndex >= 0)
            return (FSLLearningUnitsNavigationHistoryElement)historyElements.get(currentHistoryElementIndex);
        else
            return null;
    }

    private void moveToPreviousLearningUnitViewElement() {
        if (currentHistoryElementIndex >= 0) {
            currentHistoryElementIndex -= 1;
            if (!moveToLearningUnitViewElement(getCurrentHistoryElement())) {
                currentHistoryElementIndex += 1;
            }
            buildDependentUI();
        }
    }

    private void moveToNextLearningUnitViewElement() {
        if (currentHistoryElementIndex < historyElements.size()) {
            currentHistoryElementIndex += 1;
            if (!moveToLearningUnitViewElement(getCurrentHistoryElement())) {
                currentHistoryElementIndex -= 1;
            }
            buildDependentUI();
        }
    }

    /**
     * Invoked after modifying the current history element index
     * @return <code>true</code> if history element has successfully been activated
     */
    private boolean moveToLearningUnitViewElement(FSLLearningUnitsNavigationHistoryElement historyElement) {
        boolean moved = false;
        if (learningUnitsActivator.setActiveLearningUnit(historyElement.getLearningUnitId())) {
            FSLLearningUnitViewManager learningUnitViewManager =
                learningUnitViewsManager.getLearningUnitViewManager(historyElement.getLearningUnitViewManagerId());
            learningUnitViewsManager.setActiveLearningUnitViewManager(learningUnitViewManager, false);
            moved = learningUnitViewManager.setActiveLearningUnitViewElementId(historyElement.getActiveLearningUnitViewElementId(),
                historyElement.getSecondaryActiveLearningUnitViewElementId());
        }
        return moved;
    }

    private void addElementToHistory(FSLLearningUnitsNavigationHistoryElement historyElement) {
        currentHistoryElementIndex++;
        while (historyElements.size() > currentHistoryElementIndex)
            historyElements.removeElementAt(historyElements.size() - 1);
        FLGUIUtilities.trace(this, "add element " + currentHistoryElementIndex + ", " +
            historyElement.getActiveLearningUnitViewElementId());
        historyElements.add(historyElement);
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource(imageFileName));
    }

    private class FSLLearningUnitsNavigationHistoryElement {
        private String learningUnitId;
        private String learningUnitViewManagerId;
        private String activeLearningUnitViewElementId;
        private String secondaryActiveLearningUnitViewElementId;

        FSLLearningUnitsNavigationHistoryElement(String learningUnitId, String learningUnitViewManagerId,
            String activeLearningUnitViewElementId, String secondaryActiveLearningUnitViewElementId) {
                this.learningUnitId = learningUnitId;
                this.learningUnitViewManagerId = learningUnitViewManagerId;
                this.activeLearningUnitViewElementId = activeLearningUnitViewElementId;
                this.secondaryActiveLearningUnitViewElementId = secondaryActiveLearningUnitViewElementId;
        }

        String getLearningUnitId() {
            return learningUnitId;
        }

        String getLearningUnitViewManagerId() {
            return learningUnitViewManagerId;
        }

        String getActiveLearningUnitViewElementId() {
            return activeLearningUnitViewElementId;
        }

        String getSecondaryActiveLearningUnitViewElementId() {
            return secondaryActiveLearningUnitViewElementId;
        }

        boolean equals(FSLLearningUnitsNavigationHistoryElement historyElement) {
            if (historyElement == null) return false;
            if ((learningUnitViewManagerId == historyElement.getLearningUnitViewManagerId()) &&
                (activeLearningUnitViewElementId == historyElement.getActiveLearningUnitViewElementId()) &&
                (secondaryActiveLearningUnitViewElementId == historyElement.getSecondaryActiveLearningUnitViewElementId()))
                    return true;
            else {
                return false;
            }
        }
    }


    class FSLLearningUnitsNavigationHistoryManager_LearningUnitAdapter extends FSLLearningUnitAdapter {
        public void learningUnitUserViewChanged(FSLLearningUnitEvent event) {
            clearHistory();
        }

        public void learningUnitsUserDirectoryChanged(FSLLearningUnitEvent event) {
            clearHistory();
        }

        public void learningUnitActivated(FSLLearningUnitEvent event) {
            currentLearningUnitId = event.getLearningUnitId();
        }

        public void learningUnitInvalidated(FSLLearningUnitEvent event) {
            clearHistory();
        }
    }
}
