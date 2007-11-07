package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class RelatorStartPoint extends MarshallableObject implements Element {

	private String _Id;

	private String _HtmlFileName;

	public String getId() {
		return _Id;
	}

	public void setId(String _Id) {
		this._Id = _Id;
		if (_Id == null) {
			invalidate();
		}
	}

	public String getHtmlFileName() {
		return _HtmlFileName;
	}

	public void setHtmlFileName(String _HtmlFileName) {
		this._HtmlFileName = _HtmlFileName;
		if (_HtmlFileName == null) {
			invalidate();
		}
	}

	public void validateThis() throws LocalValidationException {
		if (_Id == null) {
			throw new MissingAttributeException("id");
		}
	}

	public void validate(Validator v) throws StructureValidationException {
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("relatorStartPoint");
		w.attribute("id", _Id.toString());
		if (_HtmlFileName != null) {
			w.attribute("htmlFileName", _HtmlFileName.toString());
		}
		w.end("relatorStartPoint");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("relatorStartPoint");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("id")) {
				if (_Id != null) {
					throw new DuplicateAttributeException(an);
				}
				_Id = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("htmlFileName")) {
				if (_HtmlFileName != null) {
					throw new DuplicateAttributeException(an);
				}
				_HtmlFileName = xs.takeAttributeValue();
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		xs.takeEnd("relatorStartPoint");
	}

	public static RelatorStartPoint unmarshal(InputStream in)
			throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static RelatorStartPoint unmarshal(XMLScanner xs)
			throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static RelatorStartPoint unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((RelatorStartPoint) d.unmarshal(xs, (RelatorStartPoint.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof RelatorStartPoint)) {
			return false;
		}
		RelatorStartPoint tob = ((RelatorStartPoint) ob);
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
		if (_HtmlFileName != null) {
			if (tob._HtmlFileName == null) {
				return false;
			}
			if (!_HtmlFileName.equals(tob._HtmlFileName)) {
				return false;
			}
		} else {
			if (tob._HtmlFileName != null) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
		h = ((127 * h) + ((_HtmlFileName != null) ? _HtmlFileName.hashCode()
				: 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<relatorStartPoint");
		if (_Id != null) {
			sb.append(" id=");
			sb.append(_Id.toString());
		}
		if (_HtmlFileName != null) {
			sb.append(" htmlFileName=");
			sb.append(_HtmlFileName.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return CheckUpDescriptor.newDispatcher();
	}

}
