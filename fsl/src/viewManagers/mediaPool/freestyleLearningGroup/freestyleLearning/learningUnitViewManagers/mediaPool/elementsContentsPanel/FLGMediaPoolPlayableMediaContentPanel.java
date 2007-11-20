/*
 * FLGMediaPoolPlayableMediaContentPanel.java
 *
 * Created on 21. November 2006, 14:30
 */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementsContentsPanel;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementsContentsPanel.FLGMediaPoolAudioElementContentPanel.AudioElementContentPanel_Adapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.media.*;

abstract class FLGMediaPoolPlayableMediaContentPanel extends FLGMediaPoolAbstractElementContentPanel {  
    protected FLGMediaPlayer player;
    protected String lastLoadedLearningUnitViewElementId;
    protected boolean paused = false;
    protected boolean isPlaying = false;
    protected boolean progressSetting = false;
    protected int volumeStep = 3;
    protected FSLLearningUnitViewManager learningUnitViewManager;
    protected MediaPoolThread timeSetter;
    private double actualPlayingTime = 0.0;
    protected String currentPlayedElementId;
    
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            this.learningUnitViewManager = learningUnitViewManager;
            this.learningUnitViewManager.addLearningUnitViewListener(new PlayableMediaElementContentPanel_Adapter());
            createPlayer();
            buildDependentUI(false);
    }
    
    protected void createPlayer() {
        player = new FLGMediaPlayer();
        player.setVolume(volumeStep);
        player.addMediaListener(
            new FLGMediaListener() {
                public void endOfMediaReached() {
                    performEndOfMediaReachedAction();
                }
            });
    }
    
    protected void performEndOfMediaReachedAction() {
        FSLLearningUnitViewEvent event = FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_END_OF_MEDIA_REACHED, learningUnitViewManager.getActiveLearningUnitViewElementId());
        learningUnitViewManager.fireLearningUnitViewEvent(event);
    }
    
    protected java.awt.Component getPrintableComponent() {
        return null;
    }
    
    public boolean isModifiedByUserInput() {
        return false;
    }
    
    public void saveUserChanges() {
    	learningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
    	FLGMediaPoolElement learningUnitViewElement = (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
    	learningUnitViewElement.setLastModificationDate(String.valueOf(new Date().getTime()));
    }
    
    protected void loadMediaToCurrentElement() {
       if (learningUnitViewElementsManager != null && learningUnitViewElementId != null) {
            FLGMediaPoolElement learningUnitViewElement = (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
            if (learningUnitViewElement != null) {
                File mediaFile = learningUnitViewElementsManager.resolveRelativeFileName(((FLGMediaPoolElement)learningUnitViewElement).getMediaFileName(), learningUnitViewElement);
                try {
                    FLGUIUtilities.startLongLastingOperation();
                    player.loadMedia(mediaFile);
                    lastLoadedLearningUnitViewElementId = learningUnitViewElementId;
                }  catch (FLGMediaException me) {
                    FLGOptionPane.showMessageDialog(
                        internationalization.getString("dialog.mediaException.message1") + "\n\n" + me
                        	+ "\n\n" + internationalization.getString("dialog.mediaException.message2"), 
                        		internationalization.getString("dialog.mediaException.title"), FLGOptionPane.ERROR_MESSAGE);
                    FSLLearningUnitViewEvent endOfMediaEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
                        FLGMediaPoolViewEvent.MEDIA_END_OF_MEDIA_REACHED, learningUnitViewManager.getActiveLearningUnitViewElementId());
                    learningUnitViewManager.fireLearningUnitViewEvent(endOfMediaEvent);
                }
                finally {
                    FLGUIUtilities.stopLongLastingOperation();
                }
            }
        }
    }
    
    protected void play() {
       isPlaying = true;
       player.start();
       if (timeSetter == null || !timeSetter.running) {
           timeSetter = new MediaPoolThread();
    	   timeSetter.start();
       }
       paused = false;
    }
    
    class MediaPoolThread extends Thread {    	
    	protected boolean running;
    	
 	    public void run() {
 	    	running = true;
	    	while (player.isPlaying() && lastLoadedLearningUnitViewElementId == learningUnitViewElementId) {
	    		SwingUtilities.invokeLater(
	    				new Runnable() {
	    					public void run() {
	    						if (!paused) {
		    						actualPlayingTime = player.getActualPlayingTime();
		    						FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
		                                        FLGMediaPoolViewEvent.MEDIA_PLAYING_TIME_PROGRESS, learningUnitViewManager.getActiveLearningUnitViewElementId());
		                            ((FLGMediaPoolViewEvent)mediaPoolViewEvent).actualPlayingTimeSeconds = actualPlayingTime; 
		                            ((FLGMediaPoolViewEvent)mediaPoolViewEvent).maximumPlayingTime = player.getMaximumPlayingTime();
		                            if (!progressSetting) {
		                                learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
		                            }
	                            }
	                        }
	                    }
	    		);
	    		try {
	    			sleep(1000);
	            } catch (InterruptedException e) {
	            	progressSetting = false;
	                break;
	            }
	            progressSetting = false;
	    	}
	    	running = false;
	     }
	}
	
    
    protected void stop() {
    	
        if (isPlaying && player != null) {
            player.stop();
            isPlaying = false;
            actualPlayingTime = 0.0;
        }

      	paused = false;
        buildDependentUI(false);
    }

    protected void pause() {
    	paused = true;
		actualPlayingTime = player.getActualPlayingTime();
		player.pause();
    }
    
    class PlayableMediaElementContentPanel_Adapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent e) {
            int mediaPoolViewEventType = ((FLGMediaPoolViewEvent)e).getEventSpecificType();
            switch (mediaPoolViewEventType) {
                /*case FLGMediaPoolViewEvent.MEDIA_PLAY_BUTTON_PRESSED: {
                    // implemented by subpanels (audio, video, ...)
                }*/
                case FLGMediaPoolViewEvent.MEDIA_STOP_BUTTON_PRESSED: {
                    stop();
                    break;
                }
                case FLGMediaPoolViewEvent.MEDIA_END_OF_MEDIA_REACHED: {
                    stop();
                    break;
                }
                case FLGMediaPoolViewEvent.MEDIA_PROGRESS_STEP_SET: {
                    if (isPlaying) {
                        progressSetting = true;
                        player.setMediaTime(((FLGMediaPoolViewEvent)e).actualPlayingTimeSeconds);  
                    }
                    break;
                }
                case FLGMediaPoolViewEvent.MEDIA_PAUSE_BUTTON_PRESSED: {
                	pause();
                	break;
                }
                case FLGMediaPoolViewEvent.MEDIA_VOLUME_STEP_SET: {
                    player.setVolume(((FLGMediaPoolViewEvent)e).volumeStep); 
                    break;
                }
            }
        }
        
    	public void learningUnitViewElementActivating(FSLLearningUnitViewEvent event) {
    		activeLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
    		if(paused && event.getLearningUnitViewElementId() != currentPlayedElementId) {
    	        FSLLearningUnitViewEvent mediaPoolViewEvent = FLGMediaPoolViewEvent.createViewSpecificEvent(
    	                FLGMediaPoolViewEvent.MEDIA_STOP_BUTTON_PRESSED, learningUnitViewManager.getActiveLearningUnitViewElementId());
    	            learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
     			//stop();
    		}
    	}
    }
}

