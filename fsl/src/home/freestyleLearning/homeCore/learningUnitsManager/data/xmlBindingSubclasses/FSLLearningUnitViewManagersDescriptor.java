package freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses;

import java.util.Iterator;

import freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding.LearningUnitViewManagersDescriptor;

public class FSLLearningUnitViewManagersDescriptor extends LearningUnitViewManagersDescriptor {
    public FSLLearningUnitViewManagerDescriptor getDescriptorById(String id) {
        FSLLearningUnitViewManagerDescriptor tmpLearningUnitViewManagerDescriptor;
        Iterator iterator = this.getLearningUnitViewManagersDescriptors().iterator();
        while (iterator.hasNext()) {
            tmpLearningUnitViewManagerDescriptor = (FSLLearningUnitViewManagerDescriptor)iterator.next();
            if (tmpLearningUnitViewManagerDescriptor.getId().equals(id)) return tmpLearningUnitViewManagerDescriptor;
        }
        return null;
    }
}
