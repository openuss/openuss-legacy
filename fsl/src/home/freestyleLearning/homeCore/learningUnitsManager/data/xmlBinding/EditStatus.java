package freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding;

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

public class EditStatus extends MarshallableObject implements Element {
    private String _CurrentlyEditedBy;

    public String getCurrentlyEditedBy() {
        return _CurrentlyEditedBy;
    }

    public void setCurrentlyEditedBy(String _CurrentlyEditedBy) {
        this._CurrentlyEditedBy = _CurrentlyEditedBy;
        if (_CurrentlyEditedBy == null) {
            invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (_CurrentlyEditedBy == null) {
            throw new MissingAttributeException("currentlyEditedBy");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("editStatus");
        w.attribute("currentlyEditedBy", _CurrentlyEditedBy.toString());
        w.end("editStatus");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("editStatus");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("currentlyEditedBy")) {
                if (_CurrentlyEditedBy != null) {
                    throw new DuplicateAttributeException(an);
                }
                _CurrentlyEditedBy = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("editStatus");
    }

    public static EditStatus unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static EditStatus unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static EditStatus unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((EditStatus)d.unmarshal(xs, (EditStatus.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof EditStatus)) {
            return false;
        }
        EditStatus tob = ((EditStatus)ob);
        if (_CurrentlyEditedBy != null) {
            if (tob._CurrentlyEditedBy == null) {
                return false;
            }
            if (!_CurrentlyEditedBy.equals(tob._CurrentlyEditedBy)) {
                return false;
            }
        }
        else {
            if (tob._CurrentlyEditedBy != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_CurrentlyEditedBy != null) ? _CurrentlyEditedBy.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<editStatus");
        if (_CurrentlyEditedBy != null) {
            sb.append(" currentlyEditedBy=");
            sb.append(_CurrentlyEditedBy.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("editStatus", (EditStatus.class));
        d.register("learningUnitProperties", (LearningUnitProperties.class));
        d.freezeElementNameMap();
        return d;
    }
}
