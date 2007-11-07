package freestyleLearningGroup.independent.util;

import java.io.File;
import java.util.EventObject;

/** A simple event class for events only on files. */
public class FLGFileCopyEvent extends EventObject {
    public FLGFileCopyEvent(File source) {
        super(source);
    }

    /** Returns the filename of the event source. */
    public String toString() {
        return ((File)source).getName();
    }
}
