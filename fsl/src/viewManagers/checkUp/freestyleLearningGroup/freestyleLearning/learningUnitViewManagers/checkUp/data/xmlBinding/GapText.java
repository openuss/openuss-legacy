package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding.GapTextGap;
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

public class GapText extends MarshallableObject implements Element {

	private String _HtmlFormularFileName;

	private List _GapTextGaps = PredicatedLists.createInvalidating(this,
			new GapTextGapsPredicate(), new ArrayList());

	private PredicatedLists.Predicate pred_GapTextGaps = new GapTextGapsPredicate();

	public String getHtmlFormularFileName() {
		return _HtmlFormularFileName;
	}

	public void setHtmlFormularFileName(String _HtmlFormularFileName) {
		this._HtmlFormularFileName = _HtmlFormularFileName;
		if (_HtmlFormularFileName == null) {
			invalidate();
		}
	}

	public List getGapTextGaps() {
		return _GapTextGaps;
	}

	public void deleteGapTextGaps() {
		_GapTextGaps = null;
		invalidate();
	}

	public void emptyGapTextGaps() {
		_GapTextGaps = PredicatedLists.createInvalidating(this,
				pred_GapTextGaps, new ArrayList());
	}

	public void validateThis() throws LocalValidationException {
	}

	public void validate(Validator v) throws StructureValidationException {
		for (Iterator i = _GapTextGaps.iterator(); i.hasNext();) {
			v.validate(((ValidatableObject) i.next()));
		}
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("gapText");
		if (_HtmlFormularFileName != null) {
			w.attribute("htmlFormularFileName", _HtmlFormularFileName
					.toString());
		}
		if (_GapTextGaps.size() > 0) {
			for (Iterator i = _GapTextGaps.iterator(); i.hasNext();) {
				m.marshal(((MarshallableObject) i.next()));
			}
		}
		w.end("gapText");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("gapText");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("htmlFormularFileName")) {
				if (_HtmlFormularFileName != null) {
					throw new DuplicateAttributeException(an);
				}
				_HtmlFormularFileName = xs.takeAttributeValue();
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		{
			List l = PredicatedLists.create(this, pred_GapTextGaps,
					new ArrayList());
			while (xs.atStart("gapTextGap")) {
				l.add(((GapTextGap) u.unmarshal()));
			}
			_GapTextGaps = PredicatedLists.createInvalidating(this,
					pred_GapTextGaps, l);
		}
		xs.takeEnd("gapText");
	}

	public static GapText unmarshal(InputStream in) throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static GapText unmarshal(XMLScanner xs) throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static GapText unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((GapText) d.unmarshal(xs, (GapText.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof GapText)) {
			return false;
		}
		GapText tob = ((GapText) ob);
		if (_HtmlFormularFileName != null) {
			if (tob._HtmlFormularFileName == null) {
				return false;
			}
			if (!_HtmlFormularFileName.equals(tob._HtmlFormularFileName)) {
				return false;
			}
		} else {
			if (tob._HtmlFormularFileName != null) {
				return false;
			}
		}
		if (_GapTextGaps != null) {
			if (tob._GapTextGaps == null) {
				return false;
			}
			if (!_GapTextGaps.equals(tob._GapTextGaps)) {
				return false;
			}
		} else {
			if (tob._GapTextGaps != null) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_HtmlFormularFileName != null) ? _HtmlFormularFileName
				.hashCode()
				: 0));
		h = ((127 * h) + ((_GapTextGaps != null) ? _GapTextGaps.hashCode() : 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<gapText");
		if (_HtmlFormularFileName != null) {
			sb.append(" htmlFormularFileName=");
			sb.append(_HtmlFormularFileName.toString());
		}
		if (_GapTextGaps != null) {
			sb.append(" gapTextGap=");
			sb.append(_GapTextGaps.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return CheckUpDescriptor.newDispatcher();
	}

	private static class GapTextGapsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof GapTextGap)) {
				throw new InvalidContentObjectException(ob, (GapTextGap.class));
			}
		}

	}

}
