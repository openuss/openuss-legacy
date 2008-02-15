/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.statusPanel;

import freestyleLearning.learningUnitViewAPI.statusPanel.FSLAbstractLearningUnitViewStatusPanel;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGMindmapsStatusPanel extends FSLAbstractLearningUnitViewStatusPanel {
    private FLGInternationalization internationalization;

    public FLGMindmapsStatusPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.statusPanel.internationalization",
            getClass().getClassLoader());
    }

    public void buildDependentUI() {
        if (activeLearningUnitViewElementId == null) {
            setText(internationalization.getString("text.welcome"));
        }
        else {
            String text = internationalization.getString("text.page") + " ";
            text += (learningUnitViewElementsManager.getElementPositionInDepthFirstOrder(activeLearningUnitViewElementId) + 1);
            text += " " + internationalization.getString("text.of") + " ";
            text += learningUnitViewElementsManager.size();
            setText(text);
        }
    }
}