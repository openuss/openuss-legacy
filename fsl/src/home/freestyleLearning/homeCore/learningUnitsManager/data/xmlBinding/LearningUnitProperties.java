package freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class LearningUnitProperties extends MarshallableRootElement implements RootElement {
    private EditStatus _EditStatus;

    public EditStatus getEditStatus() {
        return _EditStatus;
    }

    public void setEditStatus(EditStatus _EditStatus) {
        this._EditStatus = _EditStatus;
        if (_EditStatus == null) {
            invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (_EditStatus == null) {
            throw new MissingContentException("editStatus");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        v.validate(_EditStatus);
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("learningUnitProperties");
        m.marshal(_EditStatus);
        w.end("learningUnitProperties");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("learningUnitProperties");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        _EditStatus = ((EditStatus)u.unmarshal());
        xs.takeEnd("learningUnitProperties");
    }

    public static LearningUnitProperties unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static LearningUnitProperties unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static LearningUnitProperties unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((LearningUnitProperties)d.unmarshal(xs, (LearningUnitProperties.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof LearningUnitProperties)) {
            return false;
        }
        LearningUnitProperties tob = ((LearningUnitProperties)ob);
        if (_EditStatus != null) {
            if (tob._EditStatus == null) {
                return false;
            }
            if (!_EditStatus.equals(tob._EditStatus)) {
                return false;
            }
        }
        else {
            if (tob._EditStatus != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_EditStatus != null) ? _EditStatus.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<learningUnitProperties");
        if (_EditStatus != null) {
            sb.append(" editStatus=");
            sb.append(_EditStatus.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return EditStatus.newDispatcher();
    }
}
