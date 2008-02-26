package freestyleLearning.homeCore.learningUnitsManager;

import java.awt.Color;
import java.awt.Cursor;
import java.util.*;

import javax.swing.UIManager;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLinkTarget;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.independent.gui.FLGImageProgressDialog;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FSLLearningUnitLinkChecker.
 * Manager class for checking links in learning units.
 * @author Carsten Fiedler
 */
public class FSLLearningUnitLinkChecker {
	// list with DeadLink objects
	// entries with learningUnitId == null are correct targets and do not have to be deleted
	// entries with learningUnitId == value have to be deleted
	private ArrayList deadLinksList;
	private FSLLearningUnitsManager learningUnitsManager;
	private FSLLearningUnitViewsManager learningUnitViewsManager;
	private FLGInternationalization internationalization;
    private FLGImageProgressDialog linkCheckerProgressDialog;
	private boolean checkInOtherUnits = false;
	private String learningUnitToUpdateId;
	
	/**
	 * Constructor.
	 * @param <code>FSLLearningUnitsManager</code> learningUnitsManager
	 * @param <code>FSLLearningUnitViewsManager</code> learningUnitViewsManager
	 */
	public FSLLearningUnitLinkChecker(FSLLearningUnitsManager learningUnitsManager, FSLLearningUnitViewsManager learningUnitViewsManager) {
		this.learningUnitsManager = learningUnitsManager;
		this.learningUnitViewsManager = learningUnitViewsManager;
		internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.internationalization",
        		FSLLearningUnitsManager.class.getClassLoader());
		deadLinksList = new ArrayList();
	}
	
	/**
	 * Checks active learning unit for dead links.
	 * @param <code>boolean</code> checkInActivetUnit
	 * @param <code>boolean</code> checkInOtherUnits
	 * @return <code>boolean</code> true if dead links exist
	 */
	public boolean checkLinks(boolean checkInActivetUnit, boolean checkInOtherUnits) {
		boolean deadLinksExist = false;
		learningUnitToUpdateId = learningUnitsManager.getActiveLearningUnitId();
		// first, check links in active learning unit
		String[][] viewManagersIdsAndTitles = learningUnitsManager.getViewManagerIdsAndTitlesForCurrentLearningUnit();
		for (int h=0; h<viewManagersIdsAndTitles.length; h++) {
			String id = viewManagersIdsAndTitles[h][0];
			// get view manager
			FSLLearningUnitViewManager viewManager = learningUnitViewsManager.getLearningUnitViewManager(id);
		    if (viewManager!=null) {
		    	// get elements manager
		    	FSLLearningUnitViewElementsManager learningUnitViewElementsManager = viewManager.getLearningUnitViewElementsManager();
		        if (learningUnitViewElementsManager!=null) {
		        	// get elements
		        	String[] learningUnitViewElementsIds = learningUnitViewElementsManager.getAllLearningUnitViewElementIds();
		            for (int i=0; i<learningUnitViewElementsIds.length; i++) {
		            	// get links for element
		                java.util.List links = learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementsIds[i],false).getLearningUnitViewElementLinks();
		                // check links
		                for (int j=0; j<links.size(); j++) {
		                   	// get link target list for link
		                    FSLLearningUnitViewElementLink link = (FSLLearningUnitViewElementLink)links.get(j);
		                    java.util.List linkTargetList = link.getLearningUnitViewElementLinkTargets();
		                    // search link targets
		                    for (int k=0; k<linkTargetList.size(); k++) {
		                    	// get link target
		                        FSLLearningUnitViewElementLinkTarget linkTarget = (FSLLearningUnitViewElementLinkTarget) linkTargetList.get(k);
		                        // search target in active unit
		                        if(linkTarget.getTargetLearningUnitId().equals(learningUnitsManager.getActiveLearningUnitId())) {
			                    	// manager to search
		                            String managerId = linkTarget.getTargetLearningUnitViewManagerId();
		                            // get target manager
		                            FSLLearningUnitViewManager targetManager = learningUnitViewsManager.getLearningUnitViewManager(managerId);
		                            if(targetManager == null) {
		                            	// target manager does not exist (maybe deleted), delete link
		                            	deadLinksList.add(new DeadLink(learningUnitsManager.getActiveLearningUnitId(), 
			                            		viewManager, link, learningUnitViewElementsIds[i]));
		                            	
		                            	deadLinksExist = true;
		                            } else {
		                            	// search elements in view
		                                FSLLearningUnitViewElementsManager targetElementsManager = targetManager.getLearningUnitViewElementsManager();
			                            if((targetElementsManager.getLearningUnitViewElement(linkTarget.getTargetLearningUnitViewElementId(), false)) == null) {
			                              	// element does not exiat -> add dead link into list
			                               	deadLinksList.add(new DeadLink(learningUnitsManager.getActiveLearningUnitId(), 
			                            		viewManager, link, learningUnitViewElementsIds[i]));
			                               	deadLinksExist = true;
			                            }
		                            }
			                    } else {
		                          	// link in other unit, add into list for dead links -> have to be checked!
		                           	deadLinksList.add(new DeadLink(null, viewManager, link, learningUnitViewElementsIds[i]));
			                    }
		                    }
		                }
		            }
		        }
		    }
		}

		if(checkInOtherUnits) {
			System.out.println("Searching links in other units...");
			// search for link targets in other installed units
		    String[] learningUnitIds = learningUnitsManager.getInstalledLearningUnitsIds();
		    String activeLearningUnitId = learningUnitsManager.getActiveLearningUnitId();
		    for (int i=0; i<learningUnitIds.length; i++) {
		    	// do not search again in active learning unit
		    	if (!learningUnitIds[i].equals(activeLearningUnitId)) {
		    		ArrayList indexesToUpdate = checkLinksInOtherUnits(learningUnitIds[i], deadLinksList);
		    		// update null entries
		    		for (int j=0; j<indexesToUpdate.size(); j++) {
		    			// get index to update
		    			String s = (String) indexesToUpdate.get(j);
		    			int index = Integer.parseInt(s);
		    			DeadLink link = (DeadLink)deadLinksList.get(index);
		    			// set active learning unit id because it has to be updated
		    			link.setLearningUnitId(activeLearningUnitId);
		    		}
		    		if (deadLinksList.size()>0) { 
		    			deadLinksExist = true;
		    		}
		    	}
		    }
		}
	    return deadLinksExist;
	}
	
	// returns indexes of dead links in deadLinksList
	private ArrayList checkLinksInOtherUnits(String learningUnitId, ArrayList deadLinksList) {
		ArrayList indexes = new ArrayList();
		// load learning unit --> progressbar
       	try {
       		learningUnitsManager.loadLearningUnit(learningUnitId);
  		} catch(Exception e) { e.printStackTrace(); }
  		// get view managers to unit
		String[] viewManagersIds = learningUnitsManager.getLearningUnitViewManagersIdsOfLearningUnit(learningUnitId);
		// get links, targets and search elements in manager of loaded unit
		for (int j=0; j<deadLinksList.size(); j++) {
			DeadLink deadLink = (DeadLink) deadLinksList.get(j);
			String unitId = deadLink.getLearningUnitId();
			if (unitId == null) {
				// get link to check
				FSLLearningUnitViewElementLink link = deadLink.getLink();
				java.util.List targetList = link.getLearningUnitViewElementLinkTargets();
				for (int k=0; k<targetList.size(); k++) {
					// get target to check
					FSLLearningUnitViewElementLinkTarget target = (FSLLearningUnitViewElementLinkTarget) targetList.get(k);
					boolean targetExists = false;
					for (int i=0; i<viewManagersIds.length; i++) {
						FSLLearningUnitViewManager viewManager = learningUnitViewsManager.getLearningUnitViewManager(viewManagersIds[i]);
						// get elements manager
						FSLLearningUnitViewElementsManager learningUnitViewElementsManager = viewManager.getLearningUnitViewElementsManager();
						if (learningUnitViewElementsManager!=null) {
							String[] learningUnitViewElementsIds = learningUnitViewElementsManager.getAllLearningUnitViewElementIds();
							// search elements for link targets
							for (int l=0; l<learningUnitViewElementsIds.length; l++) {
								if (target.getTargetLearningUnitId().equals(learningUnitViewElementsIds[l])) {
									targetExists = true;
								}
							}
						}
					}
					if(!targetExists) {
						// add index to update
						indexes.add(Integer.toString(j));
					}
				}
			}
		}
		return indexes;
	}
	
	// TO DO: Correct Reloading.
	public void deleteDeadLinks() {
		// reload unit if not loaded
		 try {
			FLGUIUtilities.startLongLastingOperation();
			learningUnitsManager.loadLearningUnit(learningUnitToUpdateId);
			FLGUIUtilities.stopLongLastingOperation();
			System.out.println("learningUnitsManager.getActiveLearningUnitId(): " + learningUnitsManager.getActiveLearningUnitId());
		} catch(Exception e) {
			e.printStackTrace();
		}
		// ask if dead links should be deleted
	    int returnVal = FLGOptionPane.showConfirmDialog(internationalization.getString("linkChecker.optionPane.deadLinks.message"),
	    		internationalization.getString("linkChecker.optionPane.deadLinks.title"), FLGOptionPane.YES_NO_OPTION, FLGOptionPane.QUESTION_MESSAGE);
	    if (returnVal == FLGOptionPane.YES_OPTION) {
	    	// delete links in active learning unit
	        for (int i=0; i<deadLinksList.size(); i++) {
	        	DeadLink deadLink = (DeadLink) deadLinksList.get(i);
	        	if(deadLink.getLearningUnitId()!=null) {
	        		// get view target manager 
		        	FSLLearningUnitViewManager viewManager = deadLink.getViewManager();
		        	// get view element
		        	String viewElementId = deadLink.getElementToUpdateId();
		        	viewManager = learningUnitsManager.getLearningUnitViewsManager().getLearningUnitViewManager(viewManager.getLearningUnitViewManagerId());
		        	FSLLearningUnitViewElement viewElement = viewManager.getLearningUnitViewElementsManager().getLearningUnitViewElement(viewElementId, false);
		        	// remove link
		        	viewElement.getLearningUnitViewElementLinks().remove(deadLink.getLink());
		        	viewElement.setModified(true);
		        	viewManager.getLearningUnitViewElementsManager().setModified(true);
		        	// update content of view element
		        	String[] learningUnitViewElementLinksIds = new String[1];
		        	learningUnitViewElementLinksIds[0] = deadLink.getLink().getId();
		        	viewManager.fireLearningUnitViewEvent(FSLLearningUnitViewEvent.createElementLinksRemovedEvent(viewManager.getLearningUnitViewManagerId(),
		        		viewElementId, learningUnitViewElementLinksIds));
		        	viewManager.saveLearningUnitViewData();
	        	}
	        	
	        }
	    }
	    // empty dead link list
   		deadLinksList.clear();
   		learningUnitsManager.saveLearningUnit();
	}
	
	/**
	 * Deletes links from views when imported.
	 * @param <code>FSLLearningUnitViewManager</code> viewManager
	 * @param <code>boolean</code> linksInOtherUnits
	 * @param <code>boolean</code> linksInOtherViews
	 * @param <code>boolean</code> linksOnExistingElementsInOtherViews
	 */
	public void checkLinksForLearningUnitView(FSLLearningUnitViewManager viewManager, boolean linksInOtherUnits, 
			boolean linksInOtherViews, boolean linksOnExistingElementsInOtherViews) {
		if (linksInOtherUnits && linksInOtherViews) {} else {
			// open progressbar 
			linkCheckerProgressDialog = new FLGImageProgressDialog(null, 0, 200, 0, getClass().getClassLoader().getResource("freestyleLearning/homeCore/images/fsl.gif"), 
					(Color)UIManager.get("FSLColorBlue"), (Color)UIManager.get("FSLColorRed"), internationalization.getString("learningUnit.linkCheckerProgressDialog.progressbarText"));
			linkCheckerProgressDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			linkCheckerProgressDialog.setIndeterminate(true);
			// get all elements for view
			String[] viewElementsIds = viewManager.getLearningUnitViewElementsManager().getAllLearningUnitViewElementIds();
			for (int i=0; i<viewElementsIds.length; i++) {
				// get all links for element
				FSLLearningUnitViewElement viewElement = viewManager.getLearningUnitViewElementsManager().getLearningUnitViewElement(viewElementsIds[i], false);
				java.util.List linkList = viewElement.getLearningUnitViewElementLinks();
				ArrayList linksToUpdateList = new ArrayList();
				for (int j=0; j<linkList.size(); j++) {
					// get all targets to link
					FSLLearningUnitViewElementLink link = (FSLLearningUnitViewElementLink) linkList.get(j);
					java.util.List targetsList = link.getLearningUnitViewElementLinkTargets();
					for (int k=0; k<targetsList.size(); k++) {
						// get target information
						FSLLearningUnitViewElementLinkTarget target = (FSLLearningUnitViewElementLinkTarget) targetsList.get(k);
						String targetLearningUnitId = target.getTargetLearningUnitId();
						String targetViewManagerId = target.getTargetLearningUnitViewManagerId();
						String targetLearningUnitViewElementId = target.getTargetLearningUnitViewElementId();
						// linksInOtherUnits = false -> delete link in other unit
						if(!linksInOtherUnits && !targetLearningUnitId.equals(learningUnitsManager.getActiveLearningUnitId())) {
							linksToUpdateList.add(link);
			       		} 
						// linksInOtherViews = false -> delete links in other view, it does not matter its target exist
						if (!linksInOtherViews && !linksOnExistingElementsInOtherViews) {
							// delete all links in other views of same unit
							if (targetLearningUnitId.equals(learningUnitsManager.getActiveLearningUnitId())) {
								if (!targetViewManagerId.equals(viewManager.getLearningUnitViewManagerId())) {
									linksToUpdateList.add(link);
								}
				        	}
						}
						// delete links in other views of same units without targets
						if (!linksInOtherViews && linksOnExistingElementsInOtherViews) {
							if (targetLearningUnitId.equals(learningUnitsManager.getActiveLearningUnitId())) {
								if(!targetViewManagerId.equals(viewManager.getLearningUnitViewManagerId())) {
									// get view manager
									FSLLearningUnitViewManager mangerToCheck = learningUnitsManager.getLearningUnitViewsManager().getLearningUnitViewManager(viewManager.getLearningUnitViewManagerId());
									String[] elementsIds = mangerToCheck.getLearningUnitViewElementsManager().getAllLearningUnitViewElementIds();
									boolean elementExists = false;
									for (int m=0; m<elementsIds.length; m++) {
										if(elementsIds[i].equals(targetLearningUnitViewElementId)) {
											elementExists = true;
											break;
										}
									}
									if(!elementExists) { 
										// remove link
						    			linksToUpdateList.add(link);
									}
	
								}
							}
						}
					}
				}
				// update content of view element
	    		String[] learningUnitViewElementLinksIds = new String[linksToUpdateList.size()];
	    		for(int j=0; j< learningUnitViewElementLinksIds.length; j++) {
	    			FSLLearningUnitViewElementLink link = (FSLLearningUnitViewElementLink) linksToUpdateList.get(j);
	    			viewElement.getLearningUnitViewElementLinks().remove(link);
	    			learningUnitViewElementLinksIds[j] = link.getId(); 
	    		}
	    		// update view element entries
				viewElement.setModified(true);
	    		viewManager.getLearningUnitViewElementsManager().setModified(true);
	    		viewManager.fireLearningUnitViewEvent(FSLLearningUnitViewEvent.createElementLinksRemovedEvent(viewManager.getLearningUnitViewManagerId(),
	    			viewElement.getId(), learningUnitViewElementLinksIds));
	    		viewManager.saveLearningUnitViewData();
			}
			linkCheckerProgressDialog.dispose();
		}
	}
	
	class DeadLink {
		private String learningUnitId;
		private String elementToUpdateId;
		private FSLLearningUnitViewManager viewManager;
		private FSLLearningUnitViewElementLink link;
		
		DeadLink(String learningUnitId, FSLLearningUnitViewManager viewManager, FSLLearningUnitViewElementLink link, String elementToUpdateId) {
			this.learningUnitId = learningUnitId;
			this.viewManager = viewManager;
			this.link = link;
			this.elementToUpdateId = elementToUpdateId;
		}
		
		public String getLearningUnitId() {
			return learningUnitId;
		}
		
		public void setLearningUnitId(String learningUnitId) {
			this.learningUnitId = learningUnitId;
		}
		
		public FSLLearningUnitViewManager getViewManager() {
			return viewManager;
		}
		
		public FSLLearningUnitViewElementLink getLink() {
			return link;
		}
		
		public String getElementToUpdateId() {
			return elementToUpdateId;
		}
	}
}
