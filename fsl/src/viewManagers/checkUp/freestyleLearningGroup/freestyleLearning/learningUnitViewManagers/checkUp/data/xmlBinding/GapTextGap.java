package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.NoValueException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class GapTextGap extends MarshallableObject implements Element {

	private String _Id;

	private List _GapTextSolutions = PredicatedLists.createInvalidating(this,
			new GapTextSolutionsPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_GapTextSolutions = new GapTextSolutionsPredicate();

	private boolean _IgnoreCase;

	private boolean has_IgnoreCase;

	public String getId() {
		return _Id;
	}

	public void setId(String _Id) {
		this._Id = _Id;
		if (_Id == null) {
			invalidate();
		}
	}

	public List getGapTextSolutions() {
		return _GapTextSolutions;
	}

	public void deleteGapTextSolutions() {
		_GapTextSolutions = null;
		invalidate();
	}

	public void emptyGapTextSolutions() {
		_GapTextSolutions = PredicatedLists.createInvalidating(this,
				pred_GapTextSolutions, new ArrayList());
	}

	public boolean getIgnoreCase() {
		if (has_IgnoreCase) {
			return _IgnoreCase;
		}
		throw new NoValueException("ignoreCase");
	}

	public void setIgnoreCase(boolean _IgnoreCase) {
		this._IgnoreCase = _IgnoreCase;
		has_IgnoreCase = true;
		invalidate();
	}

	public boolean hasIgnoreCase() {
		return has_IgnoreCase;
	}

	public void deleteIgnoreCase() {
		has_IgnoreCase = false;
		invalidate();
	}

	public void validateThis() throws LocalValidationException {
		if (_Id == null) {
			throw new MissingAttributeException("id");
		}
		if (!has_IgnoreCase) {
			throw new MissingAttributeException("ignoreCase");
		}
	}

	public void validate(Validator v) throws StructureValidationException {
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("gapTextGap");
		w.attribute("id", _Id.toString());
		w.attribute("ignoreCase", printBoolean(getIgnoreCase()));
		for (Iterator i = _GapTextSolutions.iterator(); i.hasNext();) {
			w.leaf("gapTextSolution", ((String) i.next()).toString());
		}
		w.end("gapTextGap");
	}

	private static String printBoolean(boolean f) {
		return (f ? "true" : "false");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("gapTextGap");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("id")) {
				if (_Id != null) {
					throw new DuplicateAttributeException(an);
				}
				_Id = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("ignoreCase")) {
				if (has_IgnoreCase) {
					throw new DuplicateAttributeException(an);
				}
				try {
					_IgnoreCase = readBoolean(xs.takeAttributeValue());
				} catch (Exception x) {
					throw new ConversionException(an, x);
				}
				has_IgnoreCase = true;
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		{
			List l = new ArrayList();
			while (xs.atStart()) {
				if (xs.atStart("gapTextSolution")) {
					xs.takeStart("gapTextSolution");
					String s;
					if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
						s = xs.takeChars(XMLScanner.WS_COLLAPSE);
					} else {
						s = "";
					}
					String uf;
					try {
						uf = String.valueOf(s);
					} catch (Exception x) {
						throw new ConversionException("gapTextSolution", x);
					}
					l.add(uf);
					xs.takeEnd("gapTextSolution");
				} else {
					break;
				}
			}
			_GapTextSolutions = PredicatedLists.createInvalidating(this,
					pred_GapTextSolutions, l);
		}
		xs.takeEnd("gapTextGap");
	}

	private static boolean readBoolean(String s) throws ConversionException {
		if (s.equals("true")) {
			return true;
		}
		if (s.equals("false")) {
			return false;
		}
		throw new ConversionException(s);
	}

	public static GapTextGap unmarshal(InputStream in)
			throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static GapTextGap unmarshal(XMLScanner xs) throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static GapTextGap unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((GapTextGap) d.unmarshal(xs, (GapTextGap.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof GapTextGap)) {
			return false;
		}
		GapTextGap tob = ((GapTextGap) ob);
		if (_Id != null) {
			if (tob._Id == null) {
				return false;
			}
			if (!_Id.equals(tob._Id)) {
				return false;
			}
		} else {
			if (tob._Id != null) {
				return false;
			}
		}
		if (_GapTextSolutions != null) {
			if (tob._GapTextSolutions == null) {
				return false;
			}
			if (!_GapTextSolutions.equals(tob._GapTextSolutions)) {
				return false;
			}
		} else {
			if (tob._GapTextSolutions != null) {
				return false;
			}
		}
		if (has_IgnoreCase) {
			if (!tob.has_IgnoreCase) {
				return false;
			}
			if (_IgnoreCase != tob._IgnoreCase) {
				return false;
			}
		} else {
			if (tob.has_IgnoreCase) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
		h = ((127 * h) + ((_GapTextSolutions != null) ? _GapTextSolutions
				.hashCode() : 0));
		h = ((31 * h) + (_IgnoreCase ? 137 : 139));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<gapTextGap");
		if (_Id != null) {
			sb.append(" id=");
			sb.append(_Id.toString());
		}
		if (_GapTextSolutions != null) {
			sb.append(" gapTextSolution=");
			sb.append(_GapTextSolutions.toString());
		}
		if (has_IgnoreCase) {
			sb.append(" ignoreCase=");
			sb.append(printBoolean(_IgnoreCase));
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return CheckUpDescriptor.newDispatcher();
	}

	private static class GapTextSolutionsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof String)) {
				throw new InvalidContentObjectException(ob, (String.class));
			}
		}

	}

}
