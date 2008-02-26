/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.elementsStructurePanel;

import java.awt.*;
import java.util.Date;

import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.*;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.util.*;

public class FLGCheckUpElementsStructurePanel extends
		FSLAbstractLearningUnitViewElementsStructurePanel {
	private FLGCheckUpSpecificElementDialogPane learningUnitViewNewAndModifyElementDialogViewSpecificPane;

	private FLGInternationalization internationalization;

	public void init(FSLLearningUnitViewManager learningUnitViewManager,
			FSLLearningUnitEventGenerator learningUnitEventGenerator,
			boolean editMode) {
		super.init(learningUnitViewManager, learningUnitEventGenerator,
				editMode);
		internationalization = new FLGInternationalization(
				"freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.elementsStructurePanel.internationalization",
				getClass().getClassLoader());
		learningUnitViewNewAndModifyElementDialogViewSpecificPane = new FLGCheckUpSpecificElementDialogPane();
		learningUnitViewManager
				.addLearningUnitViewListener(new FLGCheckUpElementStructurePanel_Adapter());
		structureTree = new FLGCheckUpElementsStructureTree();
		structureTree.init(learningUnitViewManager,
				learningUnitViewElementsManager, learningUnitEventGenerator,
				editMode);
		setTreeNodeIconProvider(new FSLAbstractLearningUnitViewElementsStructurePanel_DefaultTreeNodeIconProvider());
		createDefaultEditToolBar();
	}

	public FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane getLearningUnitViewNewAndModifyElementDialogViewSpecificPane() {
		return learningUnitViewNewAndModifyElementDialogViewSpecificPane;
	}

	public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
		FLGCheckUpElement checkUpElement = (FLGCheckUpElement) element;
		checkUpElement.setLastModificationDate(String.valueOf(new Date()
				.getTime()));
		if (checkUpElement.getFolder()) {
			checkUpElement.setType(FLGCheckUpElement.ELEMENT_TYPE_FOLDER);
		} else {
			checkUpElement
					.setType(learningUnitViewNewAndModifyElementDialogViewSpecificPane
							.getSelectedElementType());
		}
	}

	public FSLLearningUnitViewElement createLearningUnitViewElement(String id,
			String parentId, String title, boolean folder) {
		FLGCheckUpElement newElement = new FLGCheckUpElement();
		newElement.setId(id);
		newElement.setParentId(parentId);
		newElement.setTitle(title);
		newElement.setFolder(folder);
		newElement
				.setLastModificationDate(String.valueOf(new Date().getTime()));
		if (newElement.getFolder()) {
			newElement.setType(FLGCheckUpElement.ELEMENT_TYPE_FOLDER);
		} else {
			newElement
					.setType(learningUnitViewNewAndModifyElementDialogViewSpecificPane
							.getSelectedElementType());
		}
		return newElement;
	}

	class FLGCheckUpSpecificElementDialogPane extends JPanel implements
			FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane {
		private JComboBox comboBox_elementType;

		public FLGCheckUpSpecificElementDialogPane() {
			setLayout(new BorderLayout());
			comboBox_elementType = new JComboBox();
			comboBox_elementType.addItem(internationalization
					.getString("text.question"));
			comboBox_elementType.addItem(internationalization
					.getString("text.multipleChoiceAlternative"));
			comboBox_elementType.addItem(internationalization
					.getString("text.relatorAlternative"));
			comboBox_elementType.addItem(internationalization
					.getString("text.gapTextAlternative")); 
			add(comboBox_elementType);
		}

		public String verifyInput(
				FSLLearningUnitViewElement editedLearningUnitViewElement,
				FSLLearningUnitViewElement referenceLearningUnitViewElement,
				boolean asFolder, int insertPosition) {
			return null;
		}

        public boolean overwriteDefaultEntries() {
        	return false;
        }
        
		public void setInputFieldsDefaults(
				FSLLearningUnitViewElement learningUnitViewElement) {
			if (learningUnitViewElement != null) {
				if (!learningUnitViewElement.getFolder()) {
					if (learningUnitViewElement.getType().equals(
							FLGCheckUpElement.ELEMENT_TYPE_QUESTION)) {
						comboBox_elementType.setSelectedIndex(0);
					}
					if (learningUnitViewElement.getType().equals(
							FLGCheckUpElement.ELEMENT_TYPE_MULTIPLECHOICE)) {
						comboBox_elementType.setSelectedIndex(1);
					}
					if (learningUnitViewElement.getType().equals(
							FLGCheckUpElement.ELEMENT_TYPE_RELATOR)) {
						comboBox_elementType.setSelectedIndex(2);
					}
					if (learningUnitViewElement.getType().equals(
							FLGCheckUpElement.ELEMENT_TYPE_GAPTEXT)) {
						comboBox_elementType.setSelectedIndex(3);
					}
				}
			}
		}

		public void setEnabled(boolean enabled) {
			if (!enabled) {
				comboBox_elementType.setEnabled(false);
				setInputFieldsDefaults(null);
			} else {
				comboBox_elementType.setEnabled(true);
				setInputFieldsDefaults(null);
			}
		}

		String getSelectedElementType() {
			switch (comboBox_elementType.getSelectedIndex()) {
			case 0:
				return FLGCheckUpElement.ELEMENT_TYPE_QUESTION;
			case 1:
				return FLGCheckUpElement.ELEMENT_TYPE_MULTIPLECHOICE;
			case 2:
				return FLGCheckUpElement.ELEMENT_TYPE_RELATOR;
			case 3:
				return FLGCheckUpElement.ELEMENT_TYPE_GAPTEXT;
			}
			return null;
		}
	}

	class FLGCheckUpElementStructurePanel_Adapter extends
			FSLLearningUnitViewAdapter {
		public void learningUnitViewSpecificEventOccurred(
				FSLLearningUnitViewEvent event) {
			FLGCheckUpEvent checkUpEvent = (FLGCheckUpEvent) event;
			if (checkUpEvent.getEventSpecificType() == FLGCheckUpEvent.CHECKUP_SWITCH_EXAM_MODE) {
				if (checkUpEvent.getExaminationModeSelected()) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							activateBlank();
						}
					});
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							activateStandard();
						}
					});
				}
			}
		}
	}
}