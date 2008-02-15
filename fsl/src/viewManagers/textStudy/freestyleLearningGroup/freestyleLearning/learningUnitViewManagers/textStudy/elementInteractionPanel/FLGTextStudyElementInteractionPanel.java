/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.elementInteractionPanel;

import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLAbstractLearningUnitViewElementInteractionPanel;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGTextStudyElementInteractionPanel extends FSLAbstractLearningUnitViewElementInteractionPanel {
    private FLGInternationalization internationalization;

    public FLGTextStudyElementInteractionPanel() {
        setSplitModeAllowed(true);
        setFullScreenModeAllowed(true);
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.elementInteractionPanel.internationalization",
            getClass().getClassLoader());
    }

    protected void buildIndependentUI() {
        super.buildIndependentUI();
        super.buildDefaultNavigationButtons(internationalization.getString("button.previousPage.toolTipText"),
            internationalization.getString("button.nextPage.toolTipText"));
    }

    
}