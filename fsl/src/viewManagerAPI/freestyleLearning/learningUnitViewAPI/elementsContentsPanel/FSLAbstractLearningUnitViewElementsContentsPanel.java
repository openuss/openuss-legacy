/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsContentsPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLLearningUnitViewElementInteractionButton;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGSingleLayout;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGInternationalization;

abstract public class FSLAbstractLearningUnitViewElementsContentsPanel extends JPanel implements
    FSLLearningUnitViewElementsContentsPanel {
	protected String activeLearningUnitViewElementId;
	protected String secondaryActiveLearningUnitViewElementId;
	protected boolean viewIsActive;
	protected boolean activeElementContentPanelIsTop;
	protected JSplitPane splitPane;
	protected JPanel centerPanel;
	protected JPanel northPanel;
	protected JPanel westPanel;
	protected JPanel topContentPanelContainer;
	protected JPanel bottomContentPanelContainer;
        protected JFrame fullScreenWindow;
        protected int activeElementContentPanelIndex;
        protected boolean editMode;
        protected boolean fullScreenMode;
        protected FSLLearningUnitViewManager learningUnitViewManager;
        protected FSLLearningUnitViewElementsManager learningUnitViewElementsManager;
        protected FSLLearningUnitViewElementInteractionButton fullScreenModeButton;
        protected FLGInternationalization internationalization;

        public void init(FSLLearningUnitViewManager learningUnitViewManager,
            FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
                this.learningUnitViewManager = learningUnitViewManager;
                learningUnitViewManager.addLearningUnitViewListener(new FSLLearningUnitViewElementsContentsPanel_LearningUnitViewAdapter());
                activeElementContentPanelIsTop = true;
                learningUnitEventGenerator.addLearningUnitListener(new FSLLearningUnitViewElementsContentsPanel_LearningUnitAdapter());
                activeElementContentPanelIndex = 0;
                fullScreenMode = false;
                buildIndependentUI();
        }

        public FSLAbstractLearningUnitViewElementsContentsPanel() {
            internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.elementsContentsPanel.internationalization",
                getClass().getClassLoader());
        }

        public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
            this.learningUnitViewElementsManager = learningUnitViewElementsManager;
            activeLearningUnitViewElementId = null;
            secondaryActiveLearningUnitViewElementId = null;
            if (viewIsActive) buildDependentUI(true, false);
        }

        public boolean isModified() {
            boolean modified = false;
            if (editMode && getActiveElementContentPanel() != null) {
                modified = getActiveElementContentPanel().isModifiedByUserInput();
            }
            return modified;
        }

        public void updateUI() {
            super.updateUI();
        }

        public void saveUserChanges() {
            if (editMode && getActiveElementContentPanel() != null) {
                if (getActiveElementContentPanel().isModifiedByUserInput()) {
                    FSLLearningUnitViewElement learningUnitViewElement =
                        learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, true);
                    learningUnitViewElement.setLastModificationDate(new Date().toString());
                    getActiveElementContentPanel().saveUserChanges();
                    FSLLearningUnitViewEvent newEvent =
                        FSLLearningUnitViewEvent.createElementsModifiedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                        		new String[] { activeLearningUnitViewElementId });
                    learningUnitViewManager.fireLearningUnitViewEvent(newEvent);
                    learningUnitViewElement.setModified(true);
                    learningUnitViewElementsManager.setModified(true);
                }
            }
        }

        protected void buildIndependentUI() {
            setLayout(new BorderLayout());
            setOpaque(false);
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT) {
                public void updateUI() {
                    super.updateUI();
                    setDividerSize(2);
                }
            };
            splitPane.setDividerSize(2);
            splitPane.setOpaque(false);
            splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.setOpaque(false);
            add(centerPanel, BorderLayout.CENTER);
            northPanel = new JPanel(new BorderLayout());
            northPanel.setOpaque(false);
            add(northPanel, BorderLayout.NORTH);
            westPanel = new JPanel(new BorderLayout());
            westPanel.setOpaque(false);
            add(westPanel, BorderLayout.WEST);
            topContentPanelContainer = new JPanel(new BorderLayout());
            topContentPanelContainer.setOpaque(false);
            bottomContentPanelContainer = new JPanel(new BorderLayout());
            bottomContentPanelContainer.setOpaque(false);
            fullScreenModeButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonQuitFullScreenMode.gif"));
            fullScreenModeButton.setToolTipText(internationalization.getString("button.fullScreen.toolTipText"));
            fullScreenModeButton.setEnabled(true);
            fullScreenModeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setFullScreenMode(false);
                    }
                });
        }

        private void setFullScreenMode(boolean fullScreenRequested) {
            FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                activeLearningUnitViewElementId, fullScreenRequested, false);
            learningUnitViewManager.fireLearningUnitViewEvent(event);
        }

        private Image loadImage(String imageFileName) {
            return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/elementsContentsPanel/images/" +
                imageFileName));
        }

        protected void buildDependentUI(boolean buildCenterPanel, boolean elementsSwitchedOnly) {
            topContentPanelContainer.removeAll();
            bottomContentPanelContainer.removeAll();
            if (getActiveElementContentPanel() != null) {
                if (activeElementContentPanelIsTop) topContentPanelContainer.add(getActiveElementContentPanel());
                else
                    bottomContentPanelContainer.add(getActiveElementContentPanel());
            }
            if (getSecondaryActiveElementContentPanel() != null) {
                if (!activeElementContentPanelIsTop) topContentPanelContainer.add(getSecondaryActiveElementContentPanel());
                else
                    bottomContentPanelContainer.add(getSecondaryActiveElementContentPanel());
            }
            topContentPanelContainer.revalidate();
            topContentPanelContainer.repaint();
            bottomContentPanelContainer.revalidate();
            bottomContentPanelContainer.repaint();
            if (buildCenterPanel) {
                centerPanel.removeAll();
                if (secondaryActiveLearningUnitViewElementId != null) {
                    if (activeElementContentPanelIsTop) {
                        splitPane.setTopComponent(createBorderPanel(new Color(50, 50, 122), topContentPanelContainer));
                        splitPane.setBottomComponent(createBorderPanel(new Color(143, 142, 158), bottomContentPanelContainer));
                    }
                    else {
                        splitPane.setTopComponent(createBorderPanel(new Color(143, 142, 158), topContentPanelContainer));
                        splitPane.setBottomComponent(createBorderPanel(new Color(50, 50, 122), bottomContentPanelContainer));
                    }
                    splitPane.setDividerLocation(getHeight() / 2);
                    // WORKAROUND #3
                    // sometimes the setDividerLocation does not work - it sets the dividerPosition to 0
                    // To ensure that the correct location is set it again (!) after the SplitPane has been painted
                    SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                splitPane.setDividerLocation(getHeight() / 2);
                            }
                        });
                    centerPanel.add(splitPane, BorderLayout.CENTER);
                }
                else {
                    centerPanel.add(topContentPanelContainer, BorderLayout.CENTER);
                }
                centerPanel.revalidate();
                centerPanel.repaint();
            }
            if (elementsSwitchedOnly) {
                if (activeElementContentPanelIsTop) {
                    ((JComponent)splitPane.getTopComponent()).setBorder(BorderFactory.createLineBorder(
                        new Color(50, 50, 122), 5));
                    ((JComponent)splitPane.getBottomComponent()).setBorder(BorderFactory.createLineBorder(
                        new Color(143, 142, 158), 5));
                }
                else {
                    ((JComponent)splitPane.getBottomComponent()).setBorder(BorderFactory.createLineBorder(
                        new Color(50, 50, 122), 5));
                    ((JComponent)splitPane.getTopComponent()).setBorder(BorderFactory.createLineBorder(
                        new Color(143, 142, 158), 5));
                }
            }
            northPanel.removeAll();
            if (getActiveElementContentPanel() != null) northPanel.add(getActiveElementContentPanel().getEditToolBar());
            northPanel.revalidate();
            northPanel.repaint();
            westPanel.removeAll();
            if (getActiveElementContentPanel() != null) westPanel.add(getActiveElementContentPanel().getLeftEditToolBar());
            westPanel.revalidate();
            westPanel.repaint();
        }

        private JPanel createBorderPanel(Color borderColor, JComponent component) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createLineBorder(borderColor, 5));
            panel.add(component);
            return panel;
        }

        // Jan: New method
        //  public FSLAbstractLearningUnitViewElementContentPanel
        //  getFirstElementContentPanel(String learningUnitViewElementId) {
        //    return getElementContentPanel(0, learningUnitViewElementId);
        //  }
        //
        public Class getActiveElementContentPanelClass() {
            return getActiveElementContentPanel() != null ? getActiveElementContentPanel().getClass() : null;
        }

        public FSLAbstractLearningUnitViewElementContentPanel getMemoryElementContentPanel() {
            Class panelClass = getActiveElementContentPanelClass();
            try {
                if (panelClass != null)
                    return (FSLAbstractLearningUnitViewElementContentPanel)panelClass.newInstance();
            }
            catch (IllegalAccessException ex) { }
            catch (InstantiationException ex) { }
            return null;
        }

        // Jan finished.
        protected FSLAbstractLearningUnitViewElementContentPanel getActiveElementContentPanel() {
            if (learningUnitViewElementsManager != null && activeLearningUnitViewElementId != null)
                return getElementContentPanel(activeElementContentPanelIndex, activeLearningUnitViewElementId);
            return null;
        }

        protected FSLAbstractLearningUnitViewElementContentPanel getSecondaryActiveElementContentPanel() {
            if (learningUnitViewElementsManager != null && secondaryActiveLearningUnitViewElementId != null)
                return getElementContentPanel(1 - activeElementContentPanelIndex, secondaryActiveLearningUnitViewElementId);
            else
                return null;
        }

        public java.awt.Component getPrintableComponent() {
            FSLAbstractLearningUnitViewElementContentPanel activeElementContentPanel =
                getElementContentPanel(activeElementContentPanelIndex,
                learningUnitViewManager.getActiveLearningUnitViewElementId());
            if (activeElementContentPanel != null) return activeElementContentPanel.getPrintableComponent();
            else return null;
        }

        public java.awt.Component getPrintableComponent(String elementId) {
            FSLAbstractLearningUnitViewElementContentPanel elementContentPanel = getElementContentPanel(0, elementId);
            if (elementContentPanel != null) return elementContentPanel.getPrintableComponent();
            else return null;
        }

        abstract protected FSLAbstractLearningUnitViewElementContentPanel
            getElementContentPanel(int index, String learningUnitViewElementId);

        class FSLLearningUnitViewElementsContentsPanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
            public void learningUnitUserViewChanged(FSLLearningUnitEvent event) {
                if (viewIsActive) buildDependentUI(true, false);
            }

            public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
                saveUserChanges();
                editMode = event.isEditMode();
                if (editMode) setBorder(BorderFactory.createLineBorder((Color)UIManager.get("FSLColorRed"), 5));
                else
                    setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }
        }


        class FSLLearningUnitViewElementsContentsPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
            public void learningUnitViewActivated(FSLLearningUnitViewEvent event) {
                if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                    viewIsActive = true;
                    //buildDependentUI(true, false);
                    topContentPanelContainer.removeAll();
                    bottomContentPanelContainer.removeAll();
                }
            }

            public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
                if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                    viewIsActive = false;
                    saveUserChanges();
                }
            }

            public void learningUnitViewElementsModified(FSLLearningUnitViewEvent event) {
                if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                    if (getActiveElementContentPanel() != null)
                        getActiveElementContentPanel().setLearningUnitViewElementId(activeLearningUnitViewElementId, true);
                    if (getSecondaryActiveElementContentPanel() != null)
                        getSecondaryActiveElementContentPanel().setLearningUnitViewElementId(secondaryActiveLearningUnitViewElementId, true);
                    buildDependentUI(true, false);
                }
            }

            public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
                if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                    boolean buildCenterPanel = false;
                    boolean elementsSwitchedOnly = false;
                    boolean secondaryActiveLearningUnitViewElementChanged = false;
                    if (!event.isElementsSwitchedOnly()) {
                        saveUserChanges();
                        if ((learningUnitViewElementsManager != null) &&
                            (!learningUnitViewElementsManager.learningUnitViewElementsIdsAreEqual(secondaryActiveLearningUnitViewElementId,
                            event.getSecondaryActiveLearningUnitViewElementId()))) {
                                buildCenterPanel = true;
                        }
                        else {
                            if ((activeLearningUnitViewElementId == null) ^
                                (event.getActiveLearningUnitViewElementId() == null)) {
                                    buildCenterPanel = true;
                            }
                        }
                        activeLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
                        secondaryActiveLearningUnitViewElementId = event.getSecondaryActiveLearningUnitViewElementId();
                        if (getActiveElementContentPanel() != null)
                            getActiveElementContentPanel().setLearningUnitViewElementId(activeLearningUnitViewElementId, false);
                        if (getSecondaryActiveElementContentPanel() != null)
                            getSecondaryActiveElementContentPanel().setLearningUnitViewElementId(secondaryActiveLearningUnitViewElementId, false);
                    }
                    else {
                        elementsSwitchedOnly = true;
                        activeElementContentPanelIndex = 1 - activeElementContentPanelIndex;
                        activeElementContentPanelIsTop = !activeElementContentPanelIsTop;
                        activeLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
                        secondaryActiveLearningUnitViewElementId = event.getSecondaryActiveLearningUnitViewElementId();
                    }
                    if (secondaryActiveLearningUnitViewElementId == null) activeElementContentPanelIsTop = true;
                    if (getActiveElementContentPanel() != null)
                        getActiveElementContentPanel().setActiveLearningUnitViewElementPanel(true);
                    if (getSecondaryActiveElementContentPanel() != null)
                        getSecondaryActiveElementContentPanel().setActiveLearningUnitViewElementPanel(false);
                    buildDependentUI(buildCenterPanel, elementsSwitchedOnly);
                }
            }

            public void learningUnitViewFullScreenModeSelected(FSLLearningUnitViewEvent event) {
                if (event.getActiveLearningUnitViewElementId() != null) {
                    if (event.isFullScreenModeRequested()) {
                        fullScreenWindow = new JFrame();
                        // the fullscreen-window is set, so that the cdi-panel can get a reference on it.
                        FLGUIUtilities.setFullScreenWindow(fullScreenWindow);
                        freestyleLearningGroup.independent.gui.FLGUIUtilities.setFullScreenFrame(fullScreenWindow);
                        fullScreenWindow.setUndecorated(true);
                        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
                        fullScreenWindow.setLocation(0, 0);
                        fullScreenWindow.setSize(screenDim.width, screenDim.height);
                        fullScreenWindow.getContentPane().setLayout(new BorderLayout());
                        JPanel contentPanel = new JPanel(new BorderLayout());
                        contentPanel.setOpaque(false);
                        contentPanel.add(getActiveElementContentPanel());
                        fullScreenWindow.getContentPane().setBackground((Color)UIManager.get("FSLMainFrameColor1"));
                        fullScreenWindow.getContentPane().add(contentPanel);
                        fullScreenWindow.setVisible(true);
                        JPanel fullScreenComponentPanel = new JPanel(new FLGSingleLayout());
                        fullScreenComponentPanel.setBackground((Color)UIManager.get("FSLMainFrameColor1"));
                        fullScreenComponentPanel.setBorder(BorderFactory.createEtchedBorder());
                        fullScreenComponentPanel.add(fullScreenModeButton);                      
                        JDialog dialog = new JDialog(fullScreenWindow, "", false);
                        dialog.getContentPane().setLayout(new BorderLayout());
                        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        dialog.addWindowListener(
                            new WindowAdapter() {
                                public void windowClosing(WindowEvent e) {
                                    exitFullScreenMode();
                                }
                            });
                        dialog.setLocation((int)(screenDim.getWidth() - 50), (int)(screenDim.getHeight() - 50));
                        dialog.setUndecorated(true);
                        dialog.getContentPane().add(fullScreenComponentPanel, BorderLayout.CENTER);
                        dialog.setSize(50,50);
                        fullScreenMode = true;
                        dialog.setVisible(true);
                    } else {
                        exitFullScreenMode();
                    }
                }
            }

            private void exitFullScreenMode() {
                if (fullScreenMode) {
                	fullScreenMode = false;
                	fullScreenWindow.setVisible(false);
                    fullScreenWindow.dispose();
                    FSLLearningUnitViewEvent viewActivatedEvent =
                        FSLLearningUnitViewEvent.createViewActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(), false);
                    learningUnitViewManager.fireLearningUnitViewEvent(viewActivatedEvent);
                    FSLLearningUnitViewEvent elementActivatedEvent =
                        FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                        activeLearningUnitViewElementId, null, false, true);
                    learningUnitViewManager.fireLearningUnitViewEvent(elementActivatedEvent);
                }
            }
        }
}
