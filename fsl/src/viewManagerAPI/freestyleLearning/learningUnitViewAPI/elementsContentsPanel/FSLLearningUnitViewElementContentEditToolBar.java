/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsContentsPanel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import freestyleLearningGroup.independent.gui.FLGEffectPanel;
import freestyleLearningGroup.independent.gui.FLGLeftToRightLayout;

public class FSLLearningUnitViewElementContentEditToolBar extends FLGEffectPanel {
    public void init(JComponent[] editToolBarComponents) {
        setEffect("FSLMainFrameColor3", true);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        setLayout(new FLGLeftToRightLayout(0));
        if (editToolBarComponents != null) {
            for (int i = 0; i < editToolBarComponents.length; i++) add(editToolBarComponents[i]);
        }
    }
}