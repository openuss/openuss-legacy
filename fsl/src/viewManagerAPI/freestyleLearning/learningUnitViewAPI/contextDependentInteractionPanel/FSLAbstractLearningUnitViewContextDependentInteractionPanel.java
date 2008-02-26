package freestyleLearning.learningUnitViewAPI.contextDependentInteractionPanel;

import java.awt.Image;

import javax.swing.JPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class is the abstract implementation for a contextDependentInteractionPanel. This class has to be extended
 * by a CDI-Panel of a view.
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public abstract class FSLAbstractLearningUnitViewContextDependentInteractionPanel extends JPanel implements
    FSLLearningUnitViewContextDependentInteractionPanel {
        public final static int STANDARD_SEPARATOR_WIDTH = 10;
        protected boolean editMode;
        protected boolean fullScreenSelected;
        protected FSLLearningUnitViewManager learningUnitViewManager;
        protected FSLLearningUnitViewElementsManager learningUnitViewElementsManager;
        protected String activeLearningUnitViewElementId;
        protected String secondaryActiveLearningUnitViewElementId;
        private FLGInternationalization internationalization;

        /** Constructor of this class. Only the internationalization is loaded. */
        public FSLAbstractLearningUnitViewContextDependentInteractionPanel() {
            internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.contextDependentInteractionPanel.internationalization",
                getClass().getClassLoader());
        }

        /**
         * Initializes this class.
         * @param learningUnitViewManager the learning unit view manager of this class.
         * @param learningUnitEventGenerator the learning unit events generator.
         * @param editMode the edit mode
         */
        public void init(FSLLearningUnitViewManager learningUnitViewManager,
            FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
                this.learningUnitViewManager = learningUnitViewManager;
                buildDependentUI();
        }

        /** Class for building the UI. */
        protected void buildDependentUI() {
        }

        /** Class for loading images. Uses FLGImageUtility-class for this. */
        private Image loadImage(String imageFileName) {
            return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/elementInteractionPanel/images/" +
                imageFileName));
        }
}
