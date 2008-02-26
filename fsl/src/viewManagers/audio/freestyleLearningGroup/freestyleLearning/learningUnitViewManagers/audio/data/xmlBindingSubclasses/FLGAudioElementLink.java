package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses;

import java.util.List;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLinkTarget;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.AudioLink;

/**
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioElementLink extends AudioLink implements FSLLearningUnitViewElementLink {
    public FSLLearningUnitViewElementLinkTarget
        addNewLearningUnitViewElementLinkTarget(String learningUnitId, String learningUnitViewManagerId,
        String learningUnitViewElementId) {
            FLGAudioElementLinkTarget learningUnitViewElementLinkTarget = new FLGAudioElementLinkTarget();
            return FSLLearningUnitViewElementsManager.addLearningUnitViewElementLinkTarget(this,
                learningUnitViewElementLinkTarget, learningUnitId, learningUnitViewManagerId, learningUnitViewElementId);
    }

    public List getLearningUnitViewElementLinkTargets() {
        return getAudioLinkTargets();
    }
}
