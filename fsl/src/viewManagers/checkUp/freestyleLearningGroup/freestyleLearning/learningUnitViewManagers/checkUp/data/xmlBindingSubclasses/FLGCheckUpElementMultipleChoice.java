/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBindingSubclasses;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.MultipleChoice;

public class FLGCheckUpElementMultipleChoice extends MultipleChoice {
	public FLGCheckUpElementMultipleChoice deepCopy() {
		FLGCheckUpElementMultipleChoice copy = new FLGCheckUpElementMultipleChoice();
		copy.setQuestionHtmlFileName(getQuestionHtmlFileName());
		copy.emptyMultipleChoiceAnswers();
		copy.emptyRightAnswersIds();
		for (int i = 0; i < this.getMultipleChoiceAnswers().size(); i++) {
			FLGCheckUpElementMultipleChoiceAnswer answer = (FLGCheckUpElementMultipleChoiceAnswer) getMultipleChoiceAnswers()
					.get(i);
			copy.getMultipleChoiceAnswers().add(answer.deepCopy());
		}
		for (int i = 0; i < this.getRightAnswersIds().size(); i++) {
			copy.getRightAnswersIds().add(getRightAnswersIds().get(i));
		}
		return copy;
	}

	public FLGCheckUpElementMultipleChoiceAnswer addNewMultipleChoiceAnswer() {
		FLGCheckUpElementMultipleChoiceAnswer multipleChoiceAnswer = new FLGCheckUpElementMultipleChoiceAnswer();
		if (getMultipleChoiceAnswers().isEmpty()) {
			multipleChoiceAnswer.setId("m1");
		} else {
			int maxMultipleChoiceAnswerIdValue = 0;
			for (int i = 0; i < getMultipleChoiceAnswers().size(); i++) {
				String multipleChoiceAnswerId = ((FLGCheckUpElementMultipleChoiceAnswer) getMultipleChoiceAnswers()
						.get(i)).getId();
				int multipleChoiceAnswerIdValue = Integer
						.parseInt(multipleChoiceAnswerId.substring(1));
				maxMultipleChoiceAnswerIdValue = Math.max(
						maxMultipleChoiceAnswerIdValue,
						multipleChoiceAnswerIdValue);
			}
			multipleChoiceAnswer.setId("m"
					+ (maxMultipleChoiceAnswerIdValue + 1));
		}
		getMultipleChoiceAnswers().add(multipleChoiceAnswer);
		return multipleChoiceAnswer;
	}

	public FLGCheckUpElementMultipleChoiceAnswer getMultipleChoiceAnswer(
			String multipleChoiceAnswerId) {
		for (int i = 0; i < getMultipleChoiceAnswers().size(); i++) {
			FLGCheckUpElementMultipleChoiceAnswer multipleChoiceAnswer = (FLGCheckUpElementMultipleChoiceAnswer) getMultipleChoiceAnswers()
					.get(i);
			if (multipleChoiceAnswer.getId().equals(multipleChoiceAnswerId))
				return multipleChoiceAnswer;
		}
		return null;
	}
}
