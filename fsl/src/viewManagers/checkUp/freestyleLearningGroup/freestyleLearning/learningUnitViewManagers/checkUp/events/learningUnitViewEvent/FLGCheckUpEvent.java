package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.events.learningUnitViewEvent;

import java.util.Hashtable;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

public class FLGCheckUpEvent extends FSLLearningUnitViewEvent {
	public static final int CHECKUP_PLAY_MODE_ENTERED = 1;

	public static final int CHECKUP_PLAY_MODE_EXITED = 2;

	public static final int CHECKUP_NEXT_QUESTION_SELECTED = 3;

	public static final int CHECKUP_CHECK_ANSWER_REQUESTED = 4;

	public static final int CHECKUP_ANSWER_CHECKED = 5;

	public static final int CHECKUP_RESOLVE_MODE_ENTERED = 6;

	public static final int CHECKUP_RESOLVE_MODE_EXITED = 7;

	public static final int CHECKUP_EVALUATE_MODE_ENTERED = 8;

	public static final int CHECKUP_EVALUATE_MODE_EXITED = 9;

	public static final int CHECKUP_UPDATE_LAST_RUN_RESULTS = 10;

	public static final int CHECKUP_RESET_EVALUATION_HISTORY = 11;

	public static final int CHECKUP_EVALUATION_HISTORY_SET = 12;

	public static final int CHECKUP_LOAD_USER_DATA = 13;

	public static final int CHECKUP_SAVE_USER_DATA = 14;

	public static final int CHECKUP_SWITCH_EXAM_MODE = 15;

	public static final int CHECKUP_FIRST_ACTIVATED = 16;

	private int eventSpecificType = -1;

	private String activatedLearningUnitViewElementId = null;

	private String topLevelElementId = null;

	private int correctAnswers = 0;

	private int maxNoAnswers = 0;

	private int noQuestions = 0;

	private int currentQuestionNo = 0;

	private double quota = 0.;

	private boolean saveAnswersForEvaluation = false;

	private Hashtable answerQuotas;

	private boolean playVideo;

	private boolean examinationModeSelected = false;

	private boolean assistantMediaExisting = false;

	public boolean isAssistantMediaExisting() {
		return assistantMediaExisting;
	}

	public void setAssistantMediaExisting(boolean assistantMediaExisting) {
		this.assistantMediaExisting = assistantMediaExisting;
	}

	public int getEventSpecificType() {
		return eventSpecificType;
	}

	public String getActivatedLearningUnitViewElementId() {
		return activatedLearningUnitViewElementId;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public int getMaxNoAnswers() {
		return maxNoAnswers;
	}

	public double getAnswerQuota() {
		return quota;
	}

	public Hashtable getAnswerQuotas() {
		return answerQuotas;
	}

	public int getNoQuestions() {
		return noQuestions;
	}

	public int getCurrentQuestionNo() {
		return currentQuestionNo;
	}

	public String getTopLevelElementId() {
		return topLevelElementId;
	}

	public boolean getSaveAnswersForEvaluation() {
		return saveAnswersForEvaluation;
	}

	public boolean getPlayVideo() {
		return playVideo;
	}

	public boolean getExaminationModeSelected() {
		return examinationModeSelected;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, int noQuestions,
			boolean examinationModeSelected) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.noQuestions = noQuestions;
		event.examinationModeSelected = examinationModeSelected;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, boolean saveAnswers) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.saveAnswersForEvaluation = saveAnswers;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, String activatedLearningUnitViewElementId) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.activatedLearningUnitViewElementId = activatedLearningUnitViewElementId;
		event.playVideo = false;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, String activatedLearningUnitViewElementId,
			int currentQuestionNo) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.activatedLearningUnitViewElementId = activatedLearningUnitViewElementId;
		event.currentQuestionNo = currentQuestionNo;
		event.playVideo = false;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, String activatedLearningUnitViewElementId,
			boolean examinationModeSelected) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.activatedLearningUnitViewElementId = activatedLearningUnitViewElementId;
		event.examinationModeSelected = examinationModeSelected;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, String activatedLearningUnitViewElementId,
			boolean examinationModeSelected, boolean playVideo) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.activatedLearningUnitViewElementId = activatedLearningUnitViewElementId;
		event.examinationModeSelected = examinationModeSelected;
		event.playVideo = playVideo;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, String activatedLearningUnitViewElementId,
			double quota, boolean playVideo) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.activatedLearningUnitViewElementId = activatedLearningUnitViewElementId;
		event.quota = quota;
		event.playVideo = playVideo;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, String activatedLearningUnitViewElementId,
			int correctAnswers, int maxNoAnswers, boolean playVideo) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.activatedLearningUnitViewElementId = activatedLearningUnitViewElementId;
		event.correctAnswers = correctAnswers;
		event.maxNoAnswers = maxNoAnswers;
		event.playVideo = playVideo;
		return event;
	}

	public static FSLLearningUnitViewEvent createViewSpecificEvent(
			int eventSpecificType, Hashtable answerQuotas,
			String topLevelElementId) {
		FLGCheckUpEvent event = new FLGCheckUpEvent();
		event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
		event.eventSpecificType = eventSpecificType;
		event.answerQuotas = answerQuotas;
		event.topLevelElementId = topLevelElementId;
		return event;
	}

	public String toString() {
		return "FLGCheckUpEvent[type=" + eventSpecificType
				+ "\n\tactivatedLearningUnitViewElementId = "
				+ activatedLearningUnitViewElementId
				+ "\n\ttopLevelElementId = " + topLevelElementId
				+ "\n\tcorrectAnswers = " + correctAnswers
				+ "\n\tmaxNoAnswers = " + maxNoAnswers + "\n\tnoQuestions = "
				+ noQuestions + "\n\tquota = " + quota
				+ "\n\tcurrentQuestionNo = " + currentQuestionNo
				+ "\n\tsaveAnswersForEvaluation = " + saveAnswersForEvaluation
				+ "\n\tanswerQuotas = " + answerQuotas;
	}
}
