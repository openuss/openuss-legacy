/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementInteractionPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

/**
 * FLGMediaPoolElementInteractionPanel.
 * Manager Class for Media Pool Interaction Panel.
 * @author Freestyle Learning Group
 */
public class FLGMediaPoolElementInteractionPanel extends FSLAbstractLearningUnitViewElementInteractionPanel {
    private FSLLearningUnitViewElementInteractionButton addFileButton;
    private FSLLearningUnitViewElementInteractionButton removeFileButton;
    private FSLLearningUnitViewElementInteractionButton playButton;
    private FSLLearningUnitViewElementInteractionButton pauseButton;
    private FSLLearningUnitViewElementInteractionButton stopButton;
    private FSLLearningUnitViewElementInteractionButton exportButton;
    private FSLLearningUnitViewElementInteractionButton pageScalePlusButton;
    private FSLLearningUnitViewElementInteractionButton pageScaleMinusButton;
    private FSLLearningUnitViewElementInteractionButton pageScalePageWidthButton;
    private FSLLearningUnitViewElementInteractionButton pageScalePageHeightButton;
    private FSLLearningUnitViewElementInteractionButton pageScalePage100Button;
    private FSLLearningUnitViewElementInteractionButton nextPageButton;
    private FSLLearningUnitViewElementInteractionButton prevPageButton;
    private boolean playing;
    private FLGInternationalization internationalization;
    private String activatedLearningUnitViewElementId;
    
    /**
     * Constructor.
     * Sets Internationalization.
     */
    public FLGMediaPoolElementInteractionPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementInteractionPanel.internationalization",
        getClass().getClassLoader());
        exportButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonExport.gif"));
        exportButton.setToolTipText(internationalization.getString("button.toolip.export"));
    }
    
    /**
     * Inits Media Pool Interaction Panel.
     * @param FSLLearningUnitViewManager learningUnitViewManager,
     * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
     * @param boolean editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            playing = false;
            learningUnitViewManager.addLearningUnitViewListener(new FLGMediaPoolElementInteractionPanel_Adapter());
            addFileButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonAddFile.gif"));
            addFileButton.setToolTipText(internationalization.getString("button.tooltip.removeFile"));
            removeFileButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonRemoveFile.gif"));
            removeFileButton.setToolTipText(internationalization.getString("button.tooltip.addFile"));
            playButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPlay.gif"));
            playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
            pauseButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPause.gif"));
            pauseButton.setToolTipText(internationalization.getString("button.tooltip.pause"));
            stopButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonStop.gif"));
            stopButton.setToolTipText(internationalization.getString("button.tooltip.stop"));

            pageScalePlusButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonScalePlus.gif"));
            pageScalePlusButton.setToolTipText(internationalization.getString("button.tooltip.pageScalePlus"));
            pageScaleMinusButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonScaleMinus.gif"));
            pageScaleMinusButton.setToolTipText(internationalization.getString("button.tooltip.pageScaleMinus"));
            pageScalePageWidthButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonScalePageWidth.gif"));
            pageScalePageWidthButton.setToolTipText(internationalization.getString("button.tooltip.pageWidth"));
            pageScalePageHeightButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonScalePageHeight.gif"));
            pageScalePageHeightButton.setToolTipText(internationalization.getString("button.tooltip.pageHeight"));
            pageScalePage100Button = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonScalePage100.gif"));
            pageScalePage100Button.setToolTipText(internationalization.getString("button.tooltip.page100"));
            nextPageButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonNextPage.gif"));
            nextPageButton.setToolTipText(internationalization.getString("button.tooltip.nextPage"));
            prevPageButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPreviousPage.gif"));
            prevPageButton.setToolTipText(internationalization.getString("button.tooltip.prevPage"));

            playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    play();
                }
            });
            pauseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pause();
                }
            });
            stopButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });
            exportButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    export();
                }
            });
            pageScalePlusButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pageScalePlus();
                }
            });
            pageScaleMinusButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pageScaleMinus();
                }
            });
            pageScalePageWidthButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pageScalePageWidth();
                }
            });
            pageScalePageHeightButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pageScalePageHeight();
                }
            });
            pageScalePage100Button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pageScalePage100();
                }
            });
            nextPageButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nextPage();
                }
            });
            prevPageButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    prevPage();
                }
            });
    }
    
    private void pageScalePlus() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_SCALE_INCREASED, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void pageScaleMinus() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_SCALE_DECREASED, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void pageScalePageWidth() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_SCALE_PAGE_WIDTH, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void pageScalePageHeight() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_SCALE_PAGE_HEIGHT, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void pageScalePage100() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_SCALE_PAGE_100, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
     }
    
    private void nextPage() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_NEXT_PAGE, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void prevPage() {
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_PREVIOUS_PAGE, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void export() {
        ((FLGMediaPoolManager) learningUnitViewManager).exportLearningUnitViewElement(activeLearningUnitViewElementId);
    }
    
    private void play() {
        if (playing) {
            stop();
        }
        FLGMediaPoolElement mediaPoolElement = (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(activatedLearningUnitViewElementId, false);
        if (mediaPoolElement.getType().equals("audio") || mediaPoolElement.getType().equals("video")) {
            playing = true;
            FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(FLGMediaPoolViewEvent.MEDIA_PLAY_BUTTON_PRESSED, 
            		learningUnitViewManager.getActiveLearningUnitViewElementId());
            learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
        }
        else {
            // start application
            String executableFileName = mediaPoolElement.getMediaFileName();
            File executableFile = learningUnitViewElementsManager.resolveRelativeFileName(executableFileName, mediaPoolElement);
            String executableFilePath = executableFile.getAbsolutePath();
            FLGPlatformSpecifics.startExternalApplication(executableFilePath);
        }
        rebuildButtonStatus(mediaPoolElement, false);
    }
    
    private void pause() {
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_PAUSE_BUTTON_PRESSED, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void stop() {
        playing = false;
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_STOP_BUTTON_PRESSED, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
    }
    
    private void rebuildButtonStatus(FSLLearningUnitViewElement learningUnitViewElement, boolean isFullScreenModeChanged) {
        if (learningUnitViewElement.getType().equals("video") || learningUnitViewElement.getType().equals("audio")) {
            setSplitModeAllowed(false);
            setFullScreenModeAllowed(learningUnitViewElement.getType().equals("video"));
            setScaleModeAllowed(true);
            buildDependentUI();
            playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
            add(playButton);
            add(stopButton);
            if (!isFullScreenModeChanged) {
                playButton.setEnabled(true);
                stopButton.setEnabled(playing);
            }
            return;
        }
        else if (learningUnitViewElement.getType().equals("picture")) {
            setScaleModeAllowed(true);
            setSplitModeAllowed(true);
            setFullScreenModeAllowed(true);
            buildDependentUI();
            return;
        }
        else if (learningUnitViewElement.getType().equals("pdf")) {
            setFullScreenModeAllowed(false);
            setSplitModeAllowed(false);
            setScaleModeAllowed(false);
            add(playButton);
            addSeparator();
            add(prevPageButton);
            add(nextPageButton);
            add(pageScalePlusButton);
            add(pageScaleMinusButton);
            addSeparator();
            add(pageScalePageWidthButton);
            add(pageScalePageHeightButton);
            add(pageScalePage100Button);
            buildDependentUI();
            return;
        }
        setFullScreenModeAllowed(false);
        setSplitModeAllowed(false);
        setScaleModeAllowed(false);
        buildDependentUI();
        addSeparator();
        playButton.setToolTipText(internationalization.getString("button.tooltip.execute"));
        add(playButton);
    }
    
    /**
     * Inserts view specific interaction components:
     * - Button for export.
     */
    protected void insertViewSpecificInteractionComponents() {
        if (activatedLearningUnitViewElementId!=null) {
            add(exportButton);
        }
    }
    
    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
    }
    
    class FLGMediaPoolElementInteractionPanel_Adapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
//            stop();
        }
        
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent e) {
            FLGMediaPoolViewEvent mediaPoolViewEvent = (FLGMediaPoolViewEvent)e;
            int mediaPoolViewEventType = mediaPoolViewEvent.getEventSpecificType();
            if (mediaPoolViewEventType == FLGMediaPoolViewEvent.MEDIA_PLAY_BUTTON_PRESSED) {
                playing = true;
            }
            if (mediaPoolViewEventType == FLGMediaPoolViewEvent.MEDIA_STOP_BUTTON_PRESSED) {
                playing = false;
            }
            if (mediaPoolViewEventType == FLGMediaPoolViewEvent.MEDIA_END_OF_MEDIA_REACHED) {
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
            }
        }
        
        public void learningUnitViewScaleModeChanged(FSLLearningUnitViewEvent event) {
            // repaint button panel, because user pressed scale button
            FLGMediaPoolElement element =
                (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(
                    learningUnitViewManager.getActiveLearningUnitViewElementId(), false);
            if (element != null && element.hasScaleToFit()) {
                setScaleToFit(element.getScaleToFit());
            }
            if (element != null && !element.getFolder()) {
                rebuildButtonStatus(element, true);
            }
        }
        
        public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
            // set scale to fit button
            FLGMediaPoolElement element = (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(
            learningUnitViewManager.getActiveLearningUnitViewElementId(), false);
            if (element != null && element.hasScaleToFit()) {
                setScaleToFit(element.getScaleToFit());
            }
            if (!event.getActiveLearningUnitViewElementId().equals(activatedLearningUnitViewElementId)) {
//                stop();
            }
            activatedLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
            FLGMediaPoolElement learningUnitViewElement = (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(event.getActiveLearningUnitViewElementId(), false);
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                if (learningUnitViewElement != null && !learningUnitViewElement.getFolder()) {
                    rebuildButtonStatus(learningUnitViewElement, event.isFullScreenModeChanged());
                }
            }
        }
    }
}
