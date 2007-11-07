package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.MultipleChoiceAnswer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class MultipleChoice extends MarshallableObject implements Element {

	private String _QuestionHtmlFileName;

	private List _RightAnswersIds = null;

	private PredicatedLists.Predicate pred_RightAnswersIds = new RightAnswersIdsPredicate();

	private List _MultipleChoiceAnswers = PredicatedLists.createInvalidating(
			this, new MultipleChoiceAnswersPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_MultipleChoiceAnswers = new MultipleChoiceAnswersPredicate();

	public String getQuestionHtmlFileName() {
		return _QuestionHtmlFileName;
	}

	public void setQuestionHtmlFileName(String _QuestionHtmlFileName) {
		this._QuestionHtmlFileName = _QuestionHtmlFileName;
		if (_QuestionHtmlFileName == null) {
			invalidate();
		}
	}

	public List getRightAnswersIds() {
		return _RightAnswersIds;
	}

	public void deleteRightAnswersIds() {
		_RightAnswersIds = null;
		invalidate();
	}

	public void emptyRightAnswersIds() {
		_RightAnswersIds = PredicatedLists.createInvalidating(this,
				pred_RightAnswersIds, new ArrayList());
	}

	public List getMultipleChoiceAnswers() {
		return _MultipleChoiceAnswers;
	}

	public void deleteMultipleChoiceAnswers() {
		_MultipleChoiceAnswers = null;
		invalidate();
	}

	public void emptyMultipleChoiceAnswers() {
		_MultipleChoiceAnswers = PredicatedLists.createInvalidating(this,
				pred_MultipleChoiceAnswers, new ArrayList());
	}

	public void validateThis() throws LocalValidationException {
		if (_RightAnswersIds == null) {
			throw new MissingAttributeException("rightAnswersIds");
		}
	}

	public void validate(Validator v) throws StructureValidationException {
		for (Iterator i = _MultipleChoiceAnswers.iterator(); i.hasNext();) {
			v.validate(((ValidatableObject) i.next()));
		}
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("multipleChoice");
		if (_QuestionHtmlFileName != null) {
			w.attribute("questionHtmlFileName", _QuestionHtmlFileName
					.toString());
		}
		w.attributeName("rightAnswersIds");
		for (Iterator i = _RightAnswersIds.iterator(); i.hasNext();) {
			w.attributeValueToken(((String) i.next()).toString());
		}
		if (_MultipleChoiceAnswers.size() > 0) {
			for (Iterator i = _MultipleChoiceAnswers.iterator(); i.hasNext();) {
				m.marshal(((MarshallableObject) i.next()));
			}
		}
		w.end("multipleChoice");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("multipleChoice");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("questionHtmlFileName")) {
				if (_QuestionHtmlFileName != null) {
					throw new DuplicateAttributeException(an);
				}
				_QuestionHtmlFileName = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("rightAnswersIds")) {
				if (_RightAnswersIds != null) {
					throw new DuplicateAttributeException(an);
				}
				ArrayList l = new ArrayList();
				xs.tokenizeAttributeValue();
				while (xs.atAttributeValueToken()) {
					l.add(String.valueOf(xs.takeAttributeValueToken()));
				}
				_RightAnswersIds = PredicatedLists.createInvalidating(this,
						pred_RightAnswersIds, l);
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		{
			List l = PredicatedLists.create(this, pred_MultipleChoiceAnswers,
					new ArrayList());
			while (xs.atStart("multipleChoiceAnswer")) {
				l.add(((MultipleChoiceAnswer) u.unmarshal()));
			}
			_MultipleChoiceAnswers = PredicatedLists.createInvalidating(this,
					pred_MultipleChoiceAnswers, l);
		}
		xs.takeEnd("multipleChoice");
	}

	public static MultipleChoice unmarshal(InputStream in)
			throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static MultipleChoice unmarshal(XMLScanner xs)
			throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static MultipleChoice unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((MultipleChoice) d.unmarshal(xs, (MultipleChoice.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof MultipleChoice)) {
			return false;
		}
		MultipleChoice tob = ((MultipleChoice) ob);
		if (_QuestionHtmlFileName != null) {
			if (tob._QuestionHtmlFileName == null) {
				return false;
			}
			if (!_QuestionHtmlFileName.equals(tob._QuestionHtmlFileName)) {
				return false;
			}
		} else {
			if (tob._QuestionHtmlFileName != null) {
				return false;
			}
		}
		if (_RightAnswersIds != null) {
			if (tob._RightAnswersIds == null) {
				return false;
			}
			if (!_RightAnswersIds.equals(tob._RightAnswersIds)) {
				return false;
			}
		} else {
			if (tob._RightAnswersIds != null) {
				return false;
			}
		}
		if (_MultipleChoiceAnswers != null) {
			if (tob._MultipleChoiceAnswers == null) {
				return false;
			}
			if (!_MultipleChoiceAnswers.equals(tob._MultipleChoiceAnswers)) {
				return false;
			}
		} else {
			if (tob._MultipleChoiceAnswers != null) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_QuestionHtmlFileName != null) ? _QuestionHtmlFileName
				.hashCode()
				: 0));
		h = ((127 * h) + ((_RightAnswersIds != null) ? _RightAnswersIds
				.hashCode() : 0));
		h = ((127 * h) + ((_MultipleChoiceAnswers != null) ? _MultipleChoiceAnswers
				.hashCode()
				: 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<multipleChoice");
		if (_QuestionHtmlFileName != null) {
			sb.append(" questionHtmlFileName=");
			sb.append(_QuestionHtmlFileName.toString());
		}
		if (_RightAnswersIds != null) {
			sb.append(" rightAnswersIds=");
			sb.append(_RightAnswersIds.toString());
		}
		if (_MultipleChoiceAnswers != null) {
			sb.append(" multipleChoiceAnswer=");
			sb.append(_MultipleChoiceAnswers.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return CheckUpDescriptor.newDispatcher();
	}

	private static class RightAnswersIdsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof String)) {
				throw new InvalidContentObjectException(ob, (String.class));
			}
		}

	}

	private static class MultipleChoiceAnswersPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof MultipleChoiceAnswer)) {
				throw new InvalidContentObjectException(ob,
						(MultipleChoiceAnswer.class));
			}
		}

	}

}
