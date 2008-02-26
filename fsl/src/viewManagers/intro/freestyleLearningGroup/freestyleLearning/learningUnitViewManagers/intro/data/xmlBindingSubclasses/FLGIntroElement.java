/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBindingSubclasses;

import java.util.Vector;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBinding.ViewElement;

public class FLGIntroElement extends ViewElement implements FSLLearningUnitViewElement {
    private boolean modified;

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public FSLLearningUnitViewElement deepCopy() {
        FLGIntroElement copy = new FLGIntroElement();
        FSLLearningUnitViewElementsManager.copyLearningUnitViewElement(this, copy);
        copy.setVideoFileName(getVideoFileName());
        return copy;
    }

    public String[] getLearningUnitViewElementExternalFilesRelativePaths(FSLLearningUnitViewElementsManager
        learningUnitViewElementsManager) {
            if (!getFolder()) {
                Vector paths = new Vector();
                paths.add(getVideoFileName());
                // Keine weiteren mit dem Video verbundenen Dateien
                return (String[]) paths.toArray(
                    new String[] { });
            }
            else
                return null;
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
        FLGIntroElementLink learningUnitViewElementLink = new FLGIntroElementLink();
        learningUnitViewElementLink.emptyLearningUnitViewElementLinkTargets();
        return FSLLearningUnitViewElementsManager.addLearningUnitViewElementLink(learningUnitViewElementLink, this);
    }
}
