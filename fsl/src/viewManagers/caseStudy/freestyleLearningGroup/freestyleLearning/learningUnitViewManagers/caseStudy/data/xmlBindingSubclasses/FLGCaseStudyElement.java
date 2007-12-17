/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.caseStudy.data.xmlBindingSubclasses;

import java.util.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.caseStudy.data.xmlBinding.ViewElement;
import freestyleLearningGroup.independent.gui.FLGHtmlUtilities;

public class FLGCaseStudyElement extends ViewElement implements FSLLearningUnitViewElement {
    public static String ELEMENT_TYPE_FOLDER = "folder";
    public static String ELEMENT_TYPE_PDF = "pdf";
    public static String ELEMENT_TYPE_CASE = "case";
    private boolean modified;

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
    
    public FSLLearningUnitViewElement deepCopy() {
        FLGCaseStudyElement copy = new FLGCaseStudyElement();
        FSLLearningUnitViewElementsManager.copyLearningUnitViewElement(this, copy);
        copy.setDocumentFileName(getDocumentFileName());
        return copy;
    }

    public String[] getLearningUnitViewElementExternalFilesRelativePaths(FSLLearningUnitViewElementsManager
        learningUnitViewElementsManager) {
            if (!getFolder()) {
                if (getType().equals(ELEMENT_TYPE_PDF)) {
                    Vector paths = new Vector();
                    String fileName;
                    fileName = learningUnitViewElementsManager.resolveRelativeFileName(getDocumentFileName(), this).getName();
                    return new String[] { fileName };
                }
                if (getType().equals(ELEMENT_TYPE_CASE) && getHtmlFileName() != null) {
                    return FLGHtmlUtilities.getAllRelativeFileNamesToHtmlFile(getHtmlFileName(),
                        learningUnitViewElementsManager.resolveRelativeFileName(getHtmlFileName(), this));
                }
                else
                    return null;
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
        FLGCaseStudyElementLink learningUnitViewElementLink = new FLGCaseStudyElementLink();
        learningUnitViewElementLink.emptyLearningUnitViewElementLinkTargets();
        return FSLLearningUnitViewElementsManager.addLearningUnitViewElementLink(learningUnitViewElementLink, this);
    }

    public void setType(String type) {
        if (!type.equals(getType())) {
            this.emptyLearningUnitViewElementLinks();
            if (!type.equals(ELEMENT_TYPE_CASE)) {
                this.setDocumentFileName(null);
            }
            super.setType(type);
        }
    }
}