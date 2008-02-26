package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.data.xmlBinding;

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

public class NotesDescriptor extends MarshallableRootElement implements RootElement {
    private List _Notes = PredicatedLists.createInvalidating(this, new NotesPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Notes = new NotesPredicate();

    public List getNotes() {
        return _Notes;
    }

    public void deleteNotes() {
        _Notes = null;
        invalidate();
    }

    public void emptyNotes() {
        _Notes = PredicatedLists.createInvalidating(this, pred_Notes, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
    }

    public void validate(Validator v) throws StructureValidationException {
        for (Iterator i = _Notes.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("notesDescriptor");
        if (_Notes.size() > 0) {
            for (Iterator i = _Notes.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("notesDescriptor");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("notesDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_Notes, new ArrayList());
            while (xs.atStart("note")) {
                l.add(((Note)u.unmarshal()));
            }
            _Notes = PredicatedLists.createInvalidating(this, pred_Notes, l);
        }
        xs.takeEnd("notesDescriptor");
    }

    public static NotesDescriptor unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static NotesDescriptor unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static NotesDescriptor unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((NotesDescriptor)d.unmarshal(xs, (NotesDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof NotesDescriptor)) {
            return false;
        }
        NotesDescriptor tob = ((NotesDescriptor)ob);
        if (_Notes != null) {
            if (tob._Notes == null) {
                return false;
            }
            if (!_Notes.equals(tob._Notes)) {
                return false;
            }
        }
        else {
            if (tob._Notes != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_Notes != null) ? _Notes.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<notesDescriptor");
        if (_Notes != null) {
            sb.append(" note=");
            sb.append(_Notes.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Note.newDispatcher();
    }

    private static class NotesPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof Note)) {
                throw new InvalidContentObjectException(ob, (Note.class));
            }
        }
    }
}
