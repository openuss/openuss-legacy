package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.data.xmlBinding;

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
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class NoteLink extends MarshallableObject implements Element {
    private String _Id;
    private List _NoteLinkTargets = PredicatedLists.createInvalidating(this, new NoteLinkTargetsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_NoteLinkTargets = new NoteLinkTargetsPredicate();

    public String getId() {
        return _Id;
    }

    public void setId(String _Id) {
        this._Id = _Id;
        if (_Id == null) {
            invalidate();
        }
    }

    public List getNoteLinkTargets() {
        return _NoteLinkTargets;
    }

    public void deleteNoteLinkTargets() {
        _NoteLinkTargets = null;
        invalidate();
    }

    public void emptyNoteLinkTargets() {
        _NoteLinkTargets = PredicatedLists.createInvalidating(this, pred_NoteLinkTargets, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        for (Iterator i = _NoteLinkTargets.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("noteLink");
        w.attribute("id", _Id.toString());
        if (_NoteLinkTargets.size() > 0) {
            for (Iterator i = _NoteLinkTargets.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("noteLink");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("noteLink");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id != null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_NoteLinkTargets, new ArrayList());
            while (xs.atStart("noteLinkTarget")) {
                l.add(((NoteLinkTarget)u.unmarshal()));
            }
            _NoteLinkTargets = PredicatedLists.createInvalidating(this, pred_NoteLinkTargets, l);
        }
        xs.takeEnd("noteLink");
    }

    public static NoteLink unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static NoteLink unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static NoteLink unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((NoteLink)d.unmarshal(xs, (NoteLink.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof NoteLink)) {
            return false;
        }
        NoteLink tob = ((NoteLink)ob);
        if (_Id != null) {
            if (tob._Id == null) {
                return false;
            }
            if (!_Id.equals(tob._Id)) {
                return false;
            }
        }
        else {
            if (tob._Id != null) {
                return false;
            }
        }
        if (_NoteLinkTargets != null) {
            if (tob._NoteLinkTargets == null) {
                return false;
            }
            if (!_NoteLinkTargets.equals(tob._NoteLinkTargets)) {
                return false;
            }
        }
        else {
            if (tob._NoteLinkTargets != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
        h = ((127 * h) + ((_NoteLinkTargets != null) ? _NoteLinkTargets.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<noteLink");
        if (_Id != null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_NoteLinkTargets != null) {
            sb.append(" noteLinkTarget=");
            sb.append(_NoteLinkTargets.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Note.newDispatcher();
    }

    private static class NoteLinkTargetsPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof NoteLinkTarget)) {
                throw new InvalidContentObjectException(ob, (NoteLinkTarget.class));
            }
        }
    }
}
