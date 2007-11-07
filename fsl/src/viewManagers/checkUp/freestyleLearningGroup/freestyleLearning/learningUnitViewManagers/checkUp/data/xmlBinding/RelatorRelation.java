package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.data.xmlBinding;

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
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class RelatorRelation extends MarshallableObject implements Element {

	private String _Id;

	private String _StartPointId;

	private List _EndPointsIds = null;

	private PredicatedLists.Predicate pred_EndPointsIds = new EndPointsIdsPredicate();

	public String getId() {
		return _Id;
	}

	public void setId(String _Id) {
		this._Id = _Id;
		if (_Id == null) {
			invalidate();
		}
	}

	public String getStartPointId() {
		return _StartPointId;
	}

	public void setStartPointId(String _StartPointId) {
		this._StartPointId = _StartPointId;
		if (_StartPointId == null) {
			invalidate();
		}
	}

	public List getEndPointsIds() {
		return _EndPointsIds;
	}

	public void deleteEndPointsIds() {
		_EndPointsIds = null;
		invalidate();
	}

	public void emptyEndPointsIds() {
		_EndPointsIds = PredicatedLists.createInvalidating(this,
				pred_EndPointsIds, new ArrayList());
	}

	public void validateThis() throws LocalValidationException {
		if (_Id == null) {
			throw new MissingAttributeException("id");
		}
		if (_StartPointId == null) {
			throw new MissingAttributeException("startPointId");
		}
		if (_EndPointsIds == null) {
			throw new MissingAttributeException("endPointsIds");
		}
	}

	public void validate(Validator v) throws StructureValidationException {
	}

	public void marshal(Marshaller m) throws IOException {
		XMLWriter w = m.writer();
		w.start("relatorRelation");
		w.attribute("id", _Id.toString());
		w.attribute("startPointId", _StartPointId.toString());
		w.attributeName("endPointsIds");
		for (Iterator i = _EndPointsIds.iterator(); i.hasNext();) {
			w.attributeValueToken(((String) i.next()).toString());
		}
		w.end("relatorRelation");
	}

	public void unmarshal(Unmarshaller u) throws UnmarshalException {
		XMLScanner xs = u.scanner();
		Validator v = u.validator();
		xs.takeStart("relatorRelation");
		while (xs.atAttribute()) {
			String an = xs.takeAttributeName();
			if (an.equals("id")) {
				if (_Id != null) {
					throw new DuplicateAttributeException(an);
				}
				_Id = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("startPointId")) {
				if (_StartPointId != null) {
					throw new DuplicateAttributeException(an);
				}
				_StartPointId = xs.takeAttributeValue();
				continue;
			}
			if (an.equals("endPointsIds")) {
				if (_EndPointsIds != null) {
					throw new DuplicateAttributeException(an);
				}
				ArrayList l = new ArrayList();
				xs.tokenizeAttributeValue();
				while (xs.atAttributeValueToken()) {
					l.add(String.valueOf(xs.takeAttributeValueToken()));
				}
				_EndPointsIds = PredicatedLists.createInvalidating(this,
						pred_EndPointsIds, l);
				continue;
			}
			throw new InvalidAttributeException(an);
		}
		xs.takeEnd("relatorRelation");
	}

	public static RelatorRelation unmarshal(InputStream in)
			throws UnmarshalException {
		return unmarshal(XMLScanner.open(in));
	}

	public static RelatorRelation unmarshal(XMLScanner xs)
			throws UnmarshalException {
		return unmarshal(xs, newDispatcher());
	}

	public static RelatorRelation unmarshal(XMLScanner xs, Dispatcher d)
			throws UnmarshalException {
		return ((RelatorRelation) d.unmarshal(xs, (RelatorRelation.class)));
	}

	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof RelatorRelation)) {
			return false;
		}
		RelatorRelation tob = ((RelatorRelation) ob);
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
		if (_StartPointId != null) {
			if (tob._StartPointId == null) {
				return false;
			}
			if (!_StartPointId.equals(tob._StartPointId)) {
				return false;
			}
		} else {
			if (tob._StartPointId != null) {
				return false;
			}
		}
		if (_EndPointsIds != null) {
			if (tob._EndPointsIds == null) {
				return false;
			}
			if (!_EndPointsIds.equals(tob._EndPointsIds)) {
				return false;
			}
		} else {
			if (tob._EndPointsIds != null) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int h = 0;
		h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
		h = ((127 * h) + ((_StartPointId != null) ? _StartPointId.hashCode()
				: 0));
		h = ((127 * h) + ((_EndPointsIds != null) ? _EndPointsIds.hashCode()
				: 0));
		return h;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<<relatorRelation");
		if (_Id != null) {
			sb.append(" id=");
			sb.append(_Id.toString());
		}
		if (_StartPointId != null) {
			sb.append(" startPointId=");
			sb.append(_StartPointId.toString());
		}
		if (_EndPointsIds != null) {
			sb.append(" endPointsIds=");
			sb.append(_EndPointsIds.toString());
		}
		sb.append(">>");
		return sb.toString();
	}

	public static Dispatcher newDispatcher() {
		return CheckUpDescriptor.newDispatcher();
	}

	private static class EndPointsIdsPredicate implements
			PredicatedLists.Predicate {

		public void check(Object ob) {
			if (!(ob instanceof String)) {
				throw new InvalidContentObjectException(ob, (String.class));
			}
		}

	}

}
