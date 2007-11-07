package freestyleLearningGroup.independent.util;

import java.util.EventListener;

/** The listener interface for receiving copy events (starting or finishing the copy process of a file). */
public interface FLGFileCopyListener extends EventListener {
    /** Called when a file copy starts. */
    public void copyStarted(FLGFileCopyEvent e);

    /** Called when a file copy ends. */
    public void copyEnded(FLGFileCopyEvent e);
}
