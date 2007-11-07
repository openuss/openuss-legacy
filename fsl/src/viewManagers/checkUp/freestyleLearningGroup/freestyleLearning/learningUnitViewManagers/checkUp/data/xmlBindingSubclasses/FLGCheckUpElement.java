/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBindingSubclasses;

import java.util.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.ViewElement;
import freestyleLearningGroup.independent.gui.FLGHtmlUtilities;
import freestyleLearningGroup.independent.util.FLGUtilities;

public class FLGCheckUpElement extends ViewElement implements
		FSLLearningUnitViewElement {
	public static String ELEMENT_TYPE_FOLDER = "folder";

	public static String ELEMENT_TYPE_QUESTION = "question";

	public static String ELEMENT_TYPE_MULTIPLECHOICE = "multipleChoice";

	public static String ELEMENT_TYPE_RELATOR = "relator";

	public static String ELEMENT_TYPE_GAPTEXT = "gapText";

	private boolean modified;

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public FSLLearningUnitViewElement deepCopy() {
		FLGCheckUpElement copy = new FLGCheckUpElement();
		FSLLearningUnitViewElementsManager.copyLearningUnitViewElement(this,
				copy);
		if (getGapText() != null) {
			copy.setGapText(getGapText().deepCopy());
		}
		if (getMultipleChoice() != null) {
			copy.setMultipleChoice(getMultipleChoice().deepCopy());
		}
		if (getRelator() != null) {
			copy.setRelator(getRelator().deepCopy());
		}
		return copy;
	}

	public String[] getLearningUnitViewElementExternalFilesRelativePaths(
			FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
		if (getGapText() != null
				&& getGapText().getHtmlFormularFileName() != null)
			return FLGHtmlUtilities.getAllRelativeFileNamesToHtmlFile(
					getGapText().getHtmlFormularFileName(),
					learningUnitViewElementsManager.resolveRelativeFileName(
							getGapText().getHtmlFormularFileName(), this));
		if (getMultipleChoice() != null) {
			Vector relativePaths = new Vector();
			String htmlFileName;
			htmlFileName = getMultipleChoice().getQuestionHtmlFileName();
			if (htmlFileName != null) {
				relativePaths.addAll(FLGUtilities
						.createVectorFromArray(FLGHtmlUtilities
								.getAllRelativeFileNamesToHtmlFile(
										htmlFileName,
										learningUnitViewElementsManager
												.resolveRelativeFileName(
														htmlFileName, this))));
			}
			for (int i = 0; i < getMultipleChoice().getMultipleChoiceAnswers()
					.size(); i++) {
				htmlFileName = ((FLGCheckUpElementMultipleChoiceAnswer) getMultipleChoice()
						.getMultipleChoiceAnswers().get(i)).getHtmlFileName();
				if (htmlFileName != null) {
					relativePaths
							.addAll(FLGUtilities
									.createVectorFromArray(FLGHtmlUtilities
											.getAllRelativeFileNamesToHtmlFile(
													htmlFileName,
													learningUnitViewElementsManager
															.resolveRelativeFileName(
																	htmlFileName,
																	this))));
				}
			}
			return (String[]) relativePaths.toArray(new String[] {});
		}
		if (getRelator() != null) {
			Vector relativePaths = new Vector();
			String htmlFileName;
			htmlFileName = getRelator().getQuestionHtmlFileName();
			if (htmlFileName != null) {
				relativePaths.addAll(FLGUtilities
						.createVectorFromArray(FLGHtmlUtilities
								.getAllRelativeFileNamesToHtmlFile(
										htmlFileName,
										learningUnitViewElementsManager
												.resolveRelativeFileName(
														htmlFileName, this))));
			}
			for (int i = 0; i < getRelator().getRelatorStartPoints().size(); i++) {
				htmlFileName = ((FLGCheckUpElementRelatorStartPoint) getRelator()
						.getRelatorStartPoints().get(i)).getHtmlFileName();
				if (htmlFileName != null) {
					relativePaths
							.addAll(FLGUtilities
									.createVectorFromArray(FLGHtmlUtilities
											.getAllRelativeFileNamesToHtmlFile(
													htmlFileName,
													learningUnitViewElementsManager
															.resolveRelativeFileName(
																	htmlFileName,
																	this))));
				}
			}
			for (int i = 0; i < getRelator().getRelatorEndPoints().size(); i++) {
				htmlFileName = ((FLGCheckUpElementRelatorEndPoint) getRelator()
						.getRelatorEndPoints().get(i)).getHtmlFileName();
				if (htmlFileName != null) {
					relativePaths
							.addAll(FLGUtilities
									.createVectorFromArray(FLGHtmlUtilities
											.getAllRelativeFileNamesToHtmlFile(
													htmlFileName,
													learningUnitViewElementsManager
															.resolveRelativeFileName(
																	htmlFileName,
																	this))));
				}
			}
			return (String[]) relativePaths.toArray(new String[] {});
		}
		return null;
	}

	public FSLLearningUnitViewElementLink getLearningUnitViewElementLink(
			String learningUnitViewElementLinkId) {
		for (int i = 0; i < getLearningUnitViewElementLinks().size(); i++) {
			FSLLearningUnitViewElementLink learningUnitViewElementLink = (FSLLearningUnitViewElementLink) getLearningUnitViewElementLinks()
					.get(i);
			if (learningUnitViewElementLink.getId().equals(
					learningUnitViewElementLinkId))
				return learningUnitViewElementLink;
		}
		return null;
	}

	public FSLLearningUnitViewElementLink addNewLearningUnitViewElementLink() {
		FLGCheckUpElementLink learningUnitViewElementLink = new FLGCheckUpElementLink();
		learningUnitViewElementLink.emptyLearningUnitViewElementLinkTargets();
		return FSLLearningUnitViewElementsManager
				.addLearningUnitViewElementLink(learningUnitViewElementLink,
						this);
	}

	public FLGCheckUpElementGapText getGapText() {
		if (getGapTexts().isEmpty())
			return null;
		return (FLGCheckUpElementGapText) getGapTexts().get(0);
	}

	public FLGCheckUpElementMultipleChoice getMultipleChoice() {
		if (getMultipleChoices().isEmpty())
			return null;
		return (FLGCheckUpElementMultipleChoice) getMultipleChoices().get(0);
	}

	public FLGCheckUpElementRelator getRelator() {
		if (getRelators().isEmpty())
			return null;
		return (FLGCheckUpElementRelator) getRelators().get(0);
	}

	public void setGapText(FLGCheckUpElementGapText gapText) {
		emptyGapTexts();
		getGapTexts().add(gapText);
	}

	public void setMultipleChoice(FLGCheckUpElementMultipleChoice multipleChoice) {
		emptyMultipleChoices();
		getMultipleChoices().add(multipleChoice);
	}

	public void setRelator(FLGCheckUpElementRelator relator) {
		emptyRelators();
		getRelators().add(relator);
	}

	public void setType(String type) {
		if (!type.equals(getType())) {
			emptyLearningUnitViewElementLinks();
			if (type.equals(ELEMENT_TYPE_FOLDER)) {
				emptyGapTexts();
				emptyMultipleChoices();
				emptyRelators();
			}
			if (type.equals(ELEMENT_TYPE_GAPTEXT)) {
				emptyMultipleChoices();
				emptyRelators();
				if (getGapText() == null) {
					FLGCheckUpElementGapText gapText = new FLGCheckUpElementGapText();
					gapText.emptyGapTextGaps();
					setGapText(gapText);
				}
			}
			if (type.equals(ELEMENT_TYPE_MULTIPLECHOICE)) {
				emptyGapTexts();
				emptyRelators();
				if (getMultipleChoice() == null) {
					FLGCheckUpElementMultipleChoice multipleChoice = new FLGCheckUpElementMultipleChoice();
					multipleChoice.emptyMultipleChoiceAnswers();
					multipleChoice.emptyRightAnswersIds();
					setMultipleChoice(multipleChoice);
				}
			}
			if (type.equals(ELEMENT_TYPE_RELATOR)) {
				emptyGapTexts();
				emptyMultipleChoices();
				if (getRelator() == null) {
					FLGCheckUpElementRelator relator = new FLGCheckUpElementRelator();
					relator.emptyRelatorStartPoints();
					relator.emptyRelatorEndPoints();
					relator.emptyRelatorRelations();
					setRelator(relator);
				}
			}
			super.setType(type);
		}
	}
}
