package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.RelatorEndPoint;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.RelatorRelation;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.RelatorStartPoint;
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
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Relator extends MarshallableObject implements Element {

	private String _QuestionHtmlFileName;

	private List _RelatorStartPoints = PredicatedLists.createInvalidating(this,
			new RelatorStartPointsPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_RelatorStartPoints = new RelatorStartPointsPredicate();

	private List _RelatorEndPoints = PredicatedLists.createInvalidating(this,
			new RelatorEndPointsPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_RelatorEndPoints = new RelatorEndPointsPredicate();

	private List _RelatorRelations = PredicatedLists.createInvalidating(this,
			new RelatorRelationsPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_RelatorRelations = new RelatorRelationsPredicate();

	public String getQuestionHtmlFileName() {
		return _QuestionHtmlFileName;
	}

	public void setQuestionHtmlFileName(String _QuestionHtmlFileName) {
		this._QuestionHtmlFileName = _QuestionHtmlFileName;
		if (_QuestionHtmlFileName == null) {
			invalidate();
		}
	}

	public List getRelatorStartPoints() {
		return _RelatorStartPoints;
	}

	public void deleteRelatorStartPoints() {
		_RelatorStartPoints = null;
		invalidate();
	}

	public void emptyRelatorStartPoints() {
		_RelatorStartPoints = PredicatedLists.createInvalidating(this,
				pred_RelatorStartPoints, new ArrayList());
	}

	public List getRelatorEndPoints() {
		return _RelatorEndPoints;
	}

	public void deleteRelatorEndPoints() {
		_RelatorEndPoints = null;
		invalidate();
	}

	public void emptyRelatorEndPoints() {
		_RelatorEndPoints = PredicatedLists.createInvalidating(this,
				pred_RelatorEndPoints, new ArrayList());
	}

	public List getRelatorRelations() {
		return _RelatorRelations;
	}

	public void deleteRelatorRelations() {
		_RelatorRelations = null;
		invalidate();
	}

	public void emptyRelatorRelations() {
		_RelatorRelations = PredicatedLists.createInvalidating(this,
				pred_RelatorRelations, new ArrayList());
	}

	public void validateThis() throws LocalValidationException {
	}

	public void validate(Validator v) throws StructureValidationException {
		for (Iterator i = _RelatorStartPoints.iterator(); i.hasNext();) {
			v.validate(((ValidatableObject) i.next()));
		}
		for (Iterator i = _RelatorEndPoints.iterator(); i.hasNext();) {
			v.validate(((ValidatableObject) i.next()));
		}
		for (Iterator i = _RelatorRelations.iterator(); i.hasNext();) {
			v.validate(((ValidatableObject) i.next()));
		}
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("relator");
		if (_QuestionHtmlFileName != null) {
			w.attribute("questionHtmlFileName", _QuestionHtmlFileName
					.toString());
		}
		if (_RelatorStartPoints.size() > 0) {
			for (Iterator i = _RelatorStartPoints.iterator(); i.hasNext();) {
				m.marshal(((MarshallableObject) i.next()));
			}
		}
		if (_RelatorEndPoints.size() > 0) {
			for (Iterator i = _RelatorEndPoints.iterator(); i.hasNext();) {
				m.marshal(((MarshallableObject) i.next()));
			}
		}
		if (_RelatorRelations.size() > 0) {
			for (Iterator i = _RelatorRelations.iterator(); i.hasNext();) {
				m.marshal(((MarshallableObject) i.next()));
			}
		}
		w.end("relator");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("relator");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("questionHtmlFileName")) {
				if (_QuestionHtmlFileName != null) {
					throw new DuplicateAttributeException(an);
				}
				_QuestionHtmlFileName = xs.takeAttributeValue();
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		{
			List l = PredicatedLists.create(this, pred_RelatorStartPoints,
					new ArrayList());
			while (xs.atStart("relatorStartPoint")) {
				l.add(((RelatorStartPoint) u.unmarshal()));
			}
			_RelatorStartPoints = PredicatedLists.createInvalidating(this,
					pred_RelatorStartPoints, l);
		}
		{
			List l = PredicatedLists.create(this, pred_RelatorEndPoints,
					new ArrayList());
			while (xs.atStart("relatorEndPoint")) {
				l.add(((RelatorEndPoint) u.unmarshal()));
			}
			_RelatorEndPoints = PredicatedLists.createInvalidating(this,
					pred_RelatorEndPoints, l);
		}
		{
			List l = PredicatedLists.create(this, pred_RelatorRelations,
					new ArrayList());
			while (xs.atStart("relatorRelation")) {
				l.add(((RelatorRelation) u.unmarshal()));
			}
			_RelatorRelations = PredicatedLists.createInvalidating(this,
					pred_RelatorRelations, l);
		}
		xs.takeEnd("relator");
	}

	public static Relator unmarshal(InputStream in) throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static Relator unmarshal(XMLScanner xs) throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static Relator unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((Relator) d.unmarshal(xs, (Relator.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof Relator)) {
			return false;
		}
		Relator tob = ((Relator) ob);
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
		if (_RelatorStartPoints != null) {
			if (tob._RelatorStartPoints == null) {
				return false;
			}
			if (!_RelatorStartPoints.equals(tob._RelatorStartPoints)) {
				return false;
			}
		} else {
			if (tob._RelatorStartPoints != null) {
				return false;
			}
		}
		if (_RelatorEndPoints != null) {
			if (tob._RelatorEndPoints == null) {
				return false;
			}
			if (!_RelatorEndPoints.equals(tob._RelatorEndPoints)) {
				return false;
			}
		} else {
			if (tob._RelatorEndPoints != null) {
				return false;
			}
		}
		if (_RelatorRelations != null) {
			if (tob._RelatorRelations == null) {
				return false;
			}
			if (!_RelatorRelations.equals(tob._RelatorRelations)) {
				return false;
			}
		} else {
			if (tob._RelatorRelations != null) {
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
		h = ((127 * h) + ((_RelatorStartPoints != null) ? _RelatorStartPoints
				.hashCode() : 0));
		h = ((127 * h) + ((_RelatorEndPoints != null) ? _RelatorEndPoints
				.hashCode() : 0));
		h = ((127 * h) + ((_RelatorRelations != null) ? _RelatorRelations
				.hashCode() : 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<relator");
		if (_QuestionHtmlFileName != null) {
			sb.append(" questionHtmlFileName=");
			sb.append(_QuestionHtmlFileName.toString());
		}
		if (_RelatorStartPoints != null) {
			sb.append(" relatorStartPoint=");
			sb.append(_RelatorStartPoints.toString());
		}
		if (_RelatorEndPoints != null) {
			sb.append(" relatorEndPoint=");
			sb.append(_RelatorEndPoints.toString());
		}
		if (_RelatorRelations != null) {
			sb.append(" relatorRelation=");
			sb.append(_RelatorRelations.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return CheckUpDescriptor.newDispatcher();
	}

	private static class RelatorStartPointsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof RelatorStartPoint)) {
				throw new InvalidContentObjectException(ob,
						(RelatorStartPoint.class));
			}
		}

	}

	private static class RelatorEndPointsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof RelatorEndPoint)) {
				throw new InvalidContentObjectException(ob,
						(RelatorEndPoint.class));
			}
		}

	}

	private static class RelatorRelationsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof RelatorRelation)) {
				throw new InvalidContentObjectException(ob,
						(RelatorRelation.class));
			}
		}

	}

}
