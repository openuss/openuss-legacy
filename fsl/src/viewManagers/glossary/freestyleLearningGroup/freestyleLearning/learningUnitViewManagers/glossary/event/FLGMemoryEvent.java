package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.event;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

public class FLGMemoryEvent extends FSLLearningUnitViewEvent {

  public static final int MEMORY_TOGGLE = 0;
  public static final int MEMORY_START = 1;
  public static final int MEMORY_UNCOVER = 2;
  public static final int MEMORY_MOVE_UP = 3;
  public static final int MEMORY_MOVE_NOT = 4;
  public static final int MEMORY_STOP = 5;
  public static final int MEMORY_NEXT = 6;
  public static final int MEMORY_STARTED = 7;
  public static final int MEMORY_COVERED = 8;
  public static final int MEMORY_ENTERED = 9;
  public static final int MEMORY_LEFT = 10;
  public static final int MEMORY_STOPPED = 11;
  public static final int MEMORY_UNCOVERED = 12;
  public static final int MEMORY_MOVE_DOWN = 13;
  public static final int MEMORY_SESSION_FINISHED = 15;

  protected int m_eventSpecificType;
  protected String m_activeLearningUnitViewElementId;

  public FLGMemoryEvent(int eventSpecificType) {
    this(eventSpecificType,
         null);
  }

  public FLGMemoryEvent(int eventSpecificType,
                        String activeLearningUnitViewElementId) {
    eventType = VIEW_SPECIFIC_EVENT_OCCURRED;
    m_eventSpecificType = eventSpecificType;
    m_activeLearningUnitViewElementId = activeLearningUnitViewElementId;
  }

  public int getEventSpecificType() {
    return m_eventSpecificType;
  }

  public String getActiveLearningUnitViewElement() {
    return m_activeLearningUnitViewElementId;
  }
}