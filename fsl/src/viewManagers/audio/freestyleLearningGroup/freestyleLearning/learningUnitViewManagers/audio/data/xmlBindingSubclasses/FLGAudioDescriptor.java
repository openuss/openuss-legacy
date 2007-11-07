package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses;

import java.util.List;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewXMLDocument;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.AudioDescriptor;

/**
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioDescriptor extends AudioDescriptor implements FSLLearningUnitViewXMLDocument {
    public List getLearningUnitViewElements() {
        return getAudio();
    }
}
