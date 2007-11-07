package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class EvaluationsDescriptor extends MarshallableRootElement implements
		RootElement {
	private List _EvaluationsDescriptors = PredicatedLists.createInvalidating(
			this, new EvaluationsDescriptorsPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_EvaluationsDescriptors = new EvaluationsDescriptorsPredicate();

	public List getEvaluationsDescriptors() {
		return _EvaluationsDescriptors;
	}

	public void deleteEvaluationsDescriptors() {
		_EvaluationsDescriptors = null;
		invalidate();
	}

	public void emptyEvaluationsDescriptors() {
		_EvaluationsDescriptors = PredicatedLists.createInvalidating(this,
				pred_EvaluationsDescriptors, new ArrayList());
	}

	public void validateThis() throws LocalValidationException {
	}

	public void validate(Validator v) throws StructureValidationException {
		for (Iterator i = _EvaluationsDescriptors.iterator(); i.hasNext();) {
			v.validate(((ValidatableObject) i.next()));
		}
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("evaluationsDescriptor");
		if (_EvaluationsDescriptors.size() > 0) {
			for (Iterator i = _EvaluationsDescriptors.iterator(); i.hasNext();) {
				m.marshal(((MarshallableObject) i.next()));
			}
		}
		w.end("evaluationsDescriptor");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("evaluationsDescriptor");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			throw new InvalidAttributeException(an);
		}
		{
			List l = PredicatedLists.create(this, pred_EvaluationsDescriptors,
					new ArrayList());
			while (xs.atStart("evaluationDescriptor")) {
				l.add(((EvaluationDescriptor) u.unmarshal()));
			}
			_EvaluationsDescriptors = PredicatedLists.createInvalidating(this,
					pred_EvaluationsDescriptors, l);
		}
		xs.takeEnd("evaluationsDescriptor");
	}

	public static EvaluationsDescriptor unmarshal(InputStream in)
			throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static EvaluationsDescriptor unmarshal(XMLScanner xs)
			throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static EvaluationsDescriptor unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((EvaluationsDescriptor) d.unmarshal(xs,
				(EvaluationsDescriptor.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof EvaluationsDescriptor)) {
			return false;
		}
		EvaluationsDescriptor tob = ((EvaluationsDescriptor) ob);
		if (_EvaluationsDescriptors != null) {
			if (tob._EvaluationsDescriptors == null) {
				return false;
			}
			if (!_EvaluationsDescriptors.equals(tob._EvaluationsDescriptors)) {
				return false;
			}
		} else {
			if (tob._EvaluationsDescriptors != null) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_EvaluationsDescriptors != null) ? _EvaluationsDescriptors
				.hashCode()
				: 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<evaluationsDescriptor");
		if (_EvaluationsDescriptors != null) {
			sb.append(" evaluationDescriptor=");
			sb.append(_EvaluationsDescriptors.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return EvaluationDescriptor.newDispatcher();
	}

	private static class EvaluationsDescriptorsPredicate implements
			PredicatedLists.Predicate {
		public void check(Object ob) {
			if (!(ob instanceof EvaluationDescriptor)) {
				throw new InvalidContentObjectException(ob,
						(EvaluationDescriptor.class));
			}
		}
	}
}
