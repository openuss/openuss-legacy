package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.IdentifiableElement;
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

public class EvaluationDescriptor extends MarshallableObject implements
		Element, IdentifiableElement {
	private String _Id;

	private String _NumberOfRuns;

	private String _MultipleChoiceQuota;

	private String _GapTextQuota;

	private String _RelatorQuota;

	public String getId() {
		return _Id;
	}

	public void setId(String _Id) {
		this._Id = _Id;
		if (_Id == null) {
			invalidate();
		}
	}

	public String id() {
		return _Id.toString();
	}

	public String getNumberOfRuns() {
		return _NumberOfRuns;
	}

	public void setNumberOfRuns(String _NumberOfRuns) {
		this._NumberOfRuns = _NumberOfRuns;
		if (_NumberOfRuns == null) {
			invalidate();
		}
	}

	public String getMultipleChoiceQuota() {
		return _MultipleChoiceQuota;
	}

	public void setMultipleChoiceQuota(String _MultipleChoiceQuota) {
		this._MultipleChoiceQuota = _MultipleChoiceQuota;
		if (_MultipleChoiceQuota == null) {
			invalidate();
		}
	}

	public String getGapTextQuota() {
		return _GapTextQuota;
	}

	public void setGapTextQuota(String _GapTextQuota) {
		this._GapTextQuota = _GapTextQuota;
		if (_GapTextQuota == null) {
			invalidate();
		}
	}

	public String getRelatorQuota() {
		return _RelatorQuota;
	}

	public void setRelatorQuota(String _RelatorQuota) {
		this._RelatorQuota = _RelatorQuota;
		if (_RelatorQuota == null) {
			invalidate();
		}
	}

	public void validateThis() throws LocalValidationException {
		if (_Id == null) {
			throw new MissingAttributeException("id");
		}
		if (_NumberOfRuns == null) {
			throw new MissingAttributeException("numberOfRuns");
		}
		if (_MultipleChoiceQuota == null) {
			throw new MissingAttributeException("multipleChoiceQuota");
		}
		if (_GapTextQuota == null) {
			throw new MissingAttributeException("gapTextQuota");
		}
		if (_RelatorQuota == null) {
			throw new MissingAttributeException("relatorQuota");
		}
	}

	public void validate(Validator v) throws StructureValidationException {
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("evaluationDescriptor");
		w.attribute("id", _Id.toString());
		w.attribute("numberOfRuns", _NumberOfRuns.toString());
		w.attribute("multipleChoiceQuota", _MultipleChoiceQuota.toString());
		w.attribute("gapTextQuota", _GapTextQuota.toString());
		w.attribute("relatorQuota", _RelatorQuota.toString());
		w.end("evaluationDescriptor");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("evaluationDescriptor");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("id")) {
				if (_Id != null) {
					throw new DuplicateAttributeException(an);
				}
				_Id = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("numberOfRuns")) {
				if (_NumberOfRuns != null) {
					throw new DuplicateAttributeException(an);
				}
				_NumberOfRuns = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("multipleChoiceQuota")) {
				if (_MultipleChoiceQuota != null) {
					throw new DuplicateAttributeException(an);
				}
				_MultipleChoiceQuota = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("gapTextQuota")) {
				if (_GapTextQuota != null) {
					throw new DuplicateAttributeException(an);
				}
				_GapTextQuota = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("relatorQuota")) {
				if (_RelatorQuota != null) {
					throw new DuplicateAttributeException(an);
				}
				_RelatorQuota = xs.takeAttributeValue();
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		xs.takeEnd("evaluationDescriptor");
	}

	public static EvaluationDescriptor unmarshal(InputStream in)
			throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static EvaluationDescriptor unmarshal(XMLScanner xs)
			throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static EvaluationDescriptor unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((EvaluationDescriptor) d.unmarshal(xs,
				(EvaluationDescriptor.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof EvaluationDescriptor)) {
			return false;
		}
		EvaluationDescriptor tob = ((EvaluationDescriptor) ob);
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
		if (_NumberOfRuns != null) {
			if (tob._NumberOfRuns == null) {
				return false;
			}
			if (!_NumberOfRuns.equals(tob._NumberOfRuns)) {
				return false;
			}
		} else {
			if (tob._NumberOfRuns != null) {
				return false;
			}
		}
		if (_MultipleChoiceQuota != null) {
			if (tob._MultipleChoiceQuota == null) {
				return false;
			}
			if (!_MultipleChoiceQuota.equals(tob._MultipleChoiceQuota)) {
				return false;
			}
		} else {
			if (tob._MultipleChoiceQuota != null) {
				return false;
			}
		}
		if (_GapTextQuota != null) {
			if (tob._GapTextQuota == null) {
				return false;
			}
			if (!_GapTextQuota.equals(tob._GapTextQuota)) {
				return false;
			}
		} else {
			if (tob._GapTextQuota != null) {
				return false;
			}
		}
		if (_RelatorQuota != null) {
			if (tob._RelatorQuota == null) {
				return false;
			}
			if (!_RelatorQuota.equals(tob._RelatorQuota)) {
				return false;
			}
		} else {
			if (tob._RelatorQuota != null) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
		h = ((127 * h) + ((_NumberOfRuns != null) ? _NumberOfRuns.hashCode()
				: 0));
		h = ((127 * h) + ((_MultipleChoiceQuota != null) ? _MultipleChoiceQuota
				.hashCode() : 0));
		h = ((127 * h) + ((_GapTextQuota != null) ? _GapTextQuota.hashCode()
				: 0));
		h = ((127 * h) + ((_RelatorQuota != null) ? _RelatorQuota.hashCode()
				: 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<evaluationDescriptor");
		if (_Id != null) {
			sb.append(" id=");
			sb.append(_Id.toString());
		}
		if (_NumberOfRuns != null) {
			sb.append(" numberOfRuns=");
			sb.append(_NumberOfRuns.toString());
		}
		if (_MultipleChoiceQuota != null) {
			sb.append(" multipleChoiceQuota=");
			sb.append(_MultipleChoiceQuota.toString());
		}
		if (_GapTextQuota != null) {
			sb.append(" gapTextQuota=");
			sb.append(_GapTextQuota.toString());
		}
		if (_RelatorQuota != null) {
			sb.append(" relatorQuota=");
			sb.append(_RelatorQuota.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		Dispatcher d = new Dispatcher();
		d.register("evaluationDescriptor", (EvaluationDescriptor.class));
		d.register("evaluationsDescriptor", (EvaluationsDescriptor.class));
		d.freezeElementNameMap();
		return d;
	}
}
