/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.slideShow.statusPanel;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearning.learningUnitViewAPI.statusPanel.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.slideShow.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.slideShow.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.util.*;

public class FLGSlideShowStatusPanel extends FSLAbstractLearningUnitViewStatusPanel {
    private FLGInternationalization internationalization;
    private boolean isPlayingSlideShow;
    private int maxSlideIndex;
    private int currentSlideIndex;

    /**
     * Inits Slide Show Status Panel.
     * @param <code>FSLLearningUnitViewManager</code> learningUnitViewManager
     * @param <code>FSLLearningUnitEventGenerator</code> learningUnitEventGenerator
     * @param <code>boolean</code> editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.slideShow.statusPanel.internationalization",
                getClass().getClassLoader());
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitViewManager.addLearningUnitViewListener(new FLGSlideShowStatusPanel_LearningUnitViewAdapter());
    }

    /**
     * Returns amount of children to structure tree element.
     * @param <code>String</code> elementId
     * @return <code>int</code> amount of children
     */
    private int getAmountOfChildrenToElement(String elementId, int amountOfChildren ) {
    	String[] elementIds = learningUnitViewElementsManager.getChildrenIdsOfLearningUnitViewElement(elementId);
    	for (int i=0; i<elementIds.length; i++) {
    		if (learningUnitViewElementsManager.getChildrenIdsOfLearningUnitViewElement(elementIds[i]).length>0) {
    			// get children and add them
    			amountOfChildren = getAmountOfChildrenToElement(elementIds[i],amountOfChildren);
    		} else {
    			// is child
    			amountOfChildren++;
    		}
    	}
    	return amountOfChildren;
    }
    
    /**
     * Builds dependent UI.
     */
    public void buildDependentUI() {
        if (activeLearningUnitViewElementId == null) {
            setText(internationalization.getString("text.welcome"));
    	} else {
        	String text = "";
            if (isPlayingSlideShow) {
            	if(maxSlideIndex == 0) {
            		FLGSlideShowElement activeElement = (FLGSlideShowElement) learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
	                text += activeElement.getTitle();
            	} else {
            		FLGSlideShowElement activeElement = (FLGSlideShowElement) learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
            		if(!activeElement.getFolder()) {
            			text = internationalization.getString("text.slide") + " " + (currentSlideIndex) + " " +
            			internationalization.getString("text.of") + " " + (maxSlideIndex);
            		}
            	}
            } else {
            	if(learningUnitViewElementsManager.getChildrenIdsOfLearningUnitViewElement(activeLearningUnitViewElementId).length > 0) {
            		text = internationalization.getString("text.slideShow") + " ";
            		if(learningUnitViewElementsManager.getChildrenIdsOfLearningUnitViewElement(activeLearningUnitViewElementId).length == 1) {
            			text += getAmountOfChildrenToElement(activeLearningUnitViewElementId,0) + " " + internationalization.getString("text.subElement");
            		} else {
            			text += getAmountOfChildrenToElement(activeLearningUnitViewElementId,0) + " " + internationalization.getString("text.subElements");
            		}
            	} else {
            		FLGSlideShowElement activeElement = (FLGSlideShowElement) learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
	                text += activeElement.getTitle();
            	}
            }
            FLGSlideShowElement slideShowElement = (FLGSlideShowElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewManager.getActiveLearningUnitViewElementId(), false);
            if (slideShowElement != null) {
                if (slideShowElement.hasWaitForAudioEnd() && slideShowElement.getWaitForAudioEnd()) {
                    //text += " - " + internationalization.getString("text.playing.endOfAudio");
                }
                else if (slideShowElement.hasDelayTime()) {
                    //text += " - " + internationalization.getString("text.playing") + " " + slideShowElement.getDelayTime() + "ms";
                }
            }
            setText(text);
        }
    }

    class FLGSlideShowStatusPanel_LearningUnitViewAdapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent e) {
            FLGSlideShowEvent slideShowEvent = (FLGSlideShowEvent)e;
            int slideShowEventType = slideShowEvent.getEventSpecificType();
            if (slideShowEventType == FLGSlideShowEvent.SLIDE_SHOW_SLIDE_DISPLAYED) {
                isPlayingSlideShow = true;
                maxSlideIndex = slideShowEvent.getMaxSlideIndex();
                currentSlideIndex = slideShowEvent.getCurrentSlideIndex();
                buildDependentUI();
            }
            if (slideShowEventType == FLGSlideShowEvent.SLIDE_SHOW_STOPPED) {
                isPlayingSlideShow = false;
                buildDependentUI();
            }
        }
    }
}
