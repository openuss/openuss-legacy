package freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses;

import freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding.LearningUnitViewManagerDescriptor;

public class FSLLearningUnitViewManagerDescriptor extends LearningUnitViewManagerDescriptor implements Comparable {
    // Comparable Interface implementation, compare id´s lexicographically
    public int compareTo(Object o) throws ClassCastException {
        if (!(o instanceof FSLLearningUnitViewManagerDescriptor)) throw new ClassCastException("Incompatible Types");
        FSLLearningUnitViewManagerDescriptor object = (FSLLearningUnitViewManagerDescriptor)o;
        if (!object.getId().equals(this.getId())) throw new ClassCastException("incompatible ID ´s");
        return this.getId().compareTo(object.getId());
    }
}
