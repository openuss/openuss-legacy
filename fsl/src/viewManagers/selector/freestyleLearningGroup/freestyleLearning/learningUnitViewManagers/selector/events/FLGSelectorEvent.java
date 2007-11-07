package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

/**
 * FLGSelectorEvent.
 * @author Carsten Fiedler
 */
public class FLGSelectorEvent extends FSLLearningUnitViewEvent {
	 public static final int SELECTOR_PLAY_MODE_ENTERED = 1;
	 public static final int SELECTOR_PLAY_MODE_EXITED = 2;
	 public static final int SELECTOR_PLAY_MODE_TIMEOUT = 3;
	 public static final int SELECTOR_RESET_STATUS_PANEL = 4;
	 public static final int SELECTOR_USER_ROLE_CHANGED = 5;
	 public static final int SELECTOR_REPAINT_CONTENT_PANEL = 6;
	 public static final int SELECTOR_MULTI_PLAYER_MODE_ENTERED = 7;
	 public static final int SELECTOR_MULTI_PLAYER_RUN_TIMEOUT = 8;
	 public static final int SELECTOR_MULTI_PLAYER_MODE_TIMEOUT = 9;
	 public static final int SELECTOR_MULTI_PLAYER_RUN_EXITED = 10;
	 public static final int SELECTOR_MULTI_PLAYER_RUN_UPADTE_STATUS_PANEL = 11;
	 private int eventSpecificType = -1;
	 private int width = 0;
	 private int heigth = 0;
	 private int gameSpeed = 0;
	 private double gameLength = 0.0;
	 private Object[] players;
	 private boolean userRoleIsAuthor = false;
	 private boolean lastMultiPlayerRun = false;
	 
	 /**
	  * Returns user role.
	  * If true, user is author.
	  * @return boolean userRoleIsAuthor
	  */
	 public boolean getUserRole() {
		 return userRoleIsAuthor;
	 }
	 
	 /** 
	  * Returns playground width.
	  * @return int width
	  */
	 public int getWidth() {
		return width; 
	 }
	 
	 /**
	  * Returns playground height.
	  * @return int height
	  */
	 public int getHeight() {
		 return heigth;
	 }
	 
	 /**
	  * Returns game speed.
	  * @return int gameSpeed
	  */
	 public int getGameSpeed() {
		 return gameSpeed;
	 }
	 
	 /**
	  * Returns game length.
	  * @return double gameLength
	  */
	 public double getGameLength() {
		 return gameLength;
	 }
	 
	 /**
	  * Returns event specific type.
	  * @return int eventSpecificType
	  */
	 public int getEventSpecificType() {
		 return eventSpecificType;
	 }
	 
	 /**
	  * Returns users.
	  * @return Object users
	  */
	 public Object[] getPlayers() {
		 return players;
	 }
	 
	 /**
	  * Returns if last multi player run has been finished.
	  * @return boolean lastMultiPlayerRun
	  */
	 public boolean getLastMultiPlayerRun() {
		 return lastMultiPlayerRun;
	 }
	 
	 /**
	  * Creates a specific view event.
	  * @param int eventSpecificType
	  * @param int width
	  * @param int heigth
	  * @param int gameSpeed
	  * @param double gameLength
	  * @return FSLLearningUnitViewEvent event
	  */
	 public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType,
				int width, int heigth, int gameSpeed, double gameLength) {
	        FLGSelectorEvent event = new FLGSelectorEvent();
	        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
	        event.width = width;
	        event.heigth = heigth;
	        event.gameLength = gameLength;
	        event.gameSpeed = gameSpeed;
	        event.eventSpecificType = eventSpecificType;
	        return event;
	 }
	 
	 /**
	  * Creates a specific view event.
	  * @param int eventSpecificType
	  * @param int width
	  * @param int heigth
	  * @param int gameSpeed
	  * @param double gameLength
	  * @param Object[] Users
	  * @return FSLLearningUnitViewEvent event
	  */
	 public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType,
				int width, int heigth, int gameSpeed, double gameLength, Object[] players) {
	        FLGSelectorEvent event = new FLGSelectorEvent();
	        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
	        event.width = width;
	        event.heigth = heigth;
	        event.gameLength = gameLength;
	        event.gameSpeed = gameSpeed;
	        event.players = players;
	        event.eventSpecificType = eventSpecificType;
	        return event;
	 }

	 /**
	  * Creates a specific view event.
	  * @param int eventSpecificType
	  * @return FSLLearningUnitViewEvent event
	  */
	 public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType) {
	        FLGSelectorEvent event = new FLGSelectorEvent();
	        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
	        event.eventSpecificType = eventSpecificType;
	        return event;
	 }
	 
	 
	 /**
	  * Creates a specific view event.
	  * @param boolean lastMultiPlayerRun
	  * @param int eventSpecificType
	  * @return FSLLearningUnitViewEvent event
	  */
	 public static FSLLearningUnitViewEvent createViewSpecificEvent(boolean lastMultiPlayerRun, int eventSpecificType) {
	        FLGSelectorEvent event = new FLGSelectorEvent();
	        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
	        event.eventSpecificType = eventSpecificType;
	        event.lastMultiPlayerRun = lastMultiPlayerRun;
	        return event;
	 }
	 
	 /**
	  * Creates a specific view event.
	  * @param int eventSpecificType
	  * @param booleanuserRoleIsAuthor
	  * @return FSLLearningUnitViewEvent event
	  */
	 public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType, boolean userRoleIsAuthor) {
	        FLGSelectorEvent event = new FLGSelectorEvent();
	        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
	        event.eventSpecificType = eventSpecificType;
	        event.userRoleIsAuthor = userRoleIsAuthor;
	        return event;
	 }
}
