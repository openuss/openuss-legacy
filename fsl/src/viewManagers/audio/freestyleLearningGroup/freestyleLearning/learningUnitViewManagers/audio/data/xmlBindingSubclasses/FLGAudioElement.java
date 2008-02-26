package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses;

import java.util.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.Audio;
import freestyleLearningGroup.independent.gui.FLGHtmlUtilities;

/**
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioElement extends Audio implements FSLLearningUnitViewElement {
    public static final String ELEMENT_TYPE_AUDIO_ELEMENT = "audio";
    public static final String ELEMENT_TYPE_FOLDER = "folder";
    private boolean modified;
    private String id;
    private String parentId;
    private String title;
    private boolean isFolder;
    private String[] array;

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public FSLLearningUnitViewElement deepCopy() {
        FLGAudioElement copy = new FLGAudioElement();
        FSLLearningUnitViewElementsManager.copyLearningUnitViewElement(this, copy);
        copy.setHtmlFileName(getHtmlFileName());
        copy.setSoundFileName(getSoundFileName());
        return copy;
    }

    /**
     * Returns the filenames (only filenames - not filepath) from files which should not be deleted after exiting the
     * program or saving the content.
     * @param learningUnitViewElementsManager The ElementsManagerClass of the view
     * @return an array of filenames (Strings)
     */
    public String[] getLearningUnitViewElementExternalFilesRelativePaths(FSLLearningUnitViewElementsManager
        learningUnitViewElementsManager) {
            //String-arrays for the html-file, the soundfile and the return array.
            array = new String[2];
            String[] htmlFile = new String[1];
            String[] soundFile = new String[1];
            //if the element is not a folder and his html-filename is not null,
            //than the html-filename is set.
            if (!getFolder() && getHtmlFileName() != null) {
                htmlFile = FLGHtmlUtilities.getAllRelativeFileNamesToHtmlFile(getHtmlFileName(),
                    learningUnitViewElementsManager.resolveRelativeFileName(getHtmlFileName(), this));
            }
            //if the element is not a folder and his soundfilename is not null,
            //than the soundfilename is set.
            if (!getFolder() && getSoundFileName() != null) {
                soundFile[0] = getPureSoundFileName();
            }
            //there are 4 possible cases in this method:
            //first case: no htmlfile and no soundfile has to be returned
            if (htmlFile[0] == null && soundFile[0] == null) {
                return null;
            }
            //second case: only the soundfilename has to be returned
            else if (htmlFile[0] == null && soundFile[0] != null) {
                return soundFile;
            }
            //third case: only the html-filename has to be returned
            else if (htmlFile[0] != null && soundFile[0] == null) {
                return htmlFile;
            }
            //fourth case: both of the filenames has to be returned
            else {
                array[0] = htmlFile[0];
                array[1] = soundFile[0];
                return array;
            }
    }

    public FSLLearningUnitViewElementLink getLearningUnitViewElementLink(String learningUnitViewElementLinkId) {
        for (int i = 0; i < getLearningUnitViewElementLinks().size(); i++) {
            FSLLearningUnitViewElementLink learningUnitViewElementLink =
                (FSLLearningUnitViewElementLink)getLearningUnitViewElementLinks().get(i);
            if (learningUnitViewElementLink.getId().equals(learningUnitViewElementLinkId))
                return learningUnitViewElementLink;
        }
        return null;
    }

    public FSLLearningUnitViewElementLink addNewLearningUnitViewElementLink() {
        FLGAudioElementLink learningUnitViewElementLink = new FLGAudioElementLink();
        learningUnitViewElementLink.emptyAudioLinkTargets();
        return FSLLearningUnitViewElementsManager.addLearningUnitViewElementLink(learningUnitViewElementLink, this);
    }

    public List getLearningUnitViewElementLinks() {
        return getAudioLinks();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return ELEMENT_TYPE_AUDIO_ELEMENT;
    }

    public void setType(String type) { }

    public boolean getFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }
}
