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

public class Note extends MarshallableObject implements Element {
    private String _HtmlFileName;
    private List _NoteLinks = PredicatedLists.createInvalidating(this, new NoteLinksPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_NoteLinks = new NoteLinksPredicate();
    private String _TargetViewManagerId;
    private String _TargetViewElementId;

    public String getHtmlFileName() {
        return _HtmlFileName;
    }

    public void setHtmlFileName(String _HtmlFileName) {
        this._HtmlFileName = _HtmlFileName;
        if (_HtmlFileName == null) {
            invalidate();
        }
    }

    public List getNoteLinks() {
        return _NoteLinks;
    }

    public void deleteNoteLinks() {
        _NoteLinks = null;
        invalidate();
    }

    public void emptyNoteLinks() {
        _NoteLinks = PredicatedLists.createInvalidating(this, pred_NoteLinks, new ArrayList());
    }

    public String getTargetViewManagerId() {
        return _TargetViewManagerId;
    }

    public void setTargetViewManagerId(String _TargetViewManagerId) {
        this._TargetViewManagerId = _TargetViewManagerId;
        if (_TargetViewManagerId == null) {
            invalidate();
        }
    }

    public String getTargetViewElementId() {
        return _TargetViewElementId;
    }

    public void setTargetViewElementId(String _TargetViewElementId) {
        this._TargetViewElementId = _TargetViewElementId;
        if (_TargetViewElementId == null) {
            invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (_TargetViewManagerId == null) {
            throw new MissingAttributeException("targetViewManagerId");
        }
        if (_TargetViewElementId == null) {
            throw new MissingAttributeException("targetViewElementId");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        for (Iterator i = _NoteLinks.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("note");
        if (_HtmlFileName != null) {
            w.attribute("htmlFileName", _HtmlFileName.toString());
        }
        w.attribute("targetViewManagerId", _TargetViewManagerId.toString());
        w.attribute("targetViewElementId", _TargetViewElementId.toString());
        if (_NoteLinks.size() > 0) {
            for (Iterator i = _NoteLinks.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("note");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("note");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("htmlFileName")) {
                if (_HtmlFileName != null) {
                    throw new DuplicateAttributeException(an);
                }
                _HtmlFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("targetViewManagerId")) {
                if (_TargetViewManagerId != null) {
                    throw new DuplicateAttributeException(an);
                }
                _TargetViewManagerId = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("targetViewElementId")) {
                if (_TargetViewElementId != null) {
                    throw new DuplicateAttributeException(an);
                }
                _TargetViewElementId = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_NoteLinks, new ArrayList());
            while (xs.atStart("noteLink")) {
                l.add(((NoteLink)u.unmarshal()));
            }
            _NoteLinks = PredicatedLists.createInvalidating(this, pred_NoteLinks, l);
        }
        xs.takeEnd("note");
    }

    public static Note unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static Note unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static Note unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((Note)d.unmarshal(xs, (Note.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Note)) {
            return false;
        }
        Note tob = ((Note)ob);
        if (_HtmlFileName != null) {
            if (tob._HtmlFileName == null) {
                return false;
            }
            if (!_HtmlFileName.equals(tob._HtmlFileName)) {
                return false;
            }
        }
        else {
            if (tob._HtmlFileName != null) {
                return false;
            }
        }
        if (_NoteLinks != null) {
            if (tob._NoteLinks == null) {
                return false;
            }
            if (!_NoteLinks.equals(tob._NoteLinks)) {
                return false;
            }
        }
        else {
            if (tob._NoteLinks != null) {
                return false;
            }
        }
        if (_TargetViewManagerId != null) {
            if (tob._TargetViewManagerId == null) {
                return false;
            }
            if (!_TargetViewManagerId.equals(tob._TargetViewManagerId)) {
                return false;
            }
        }
        else {
            if (tob._TargetViewManagerId != null) {
                return false;
            }
        }
        if (_TargetViewElementId != null) {
            if (tob._TargetViewElementId == null) {
                return false;
            }
            if (!_TargetViewElementId.equals(tob._TargetViewElementId)) {
                return false;
            }
        }
        else {
            if (tob._TargetViewElementId != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_HtmlFileName != null) ? _HtmlFileName.hashCode() : 0));
        h = ((127 * h) + ((_NoteLinks != null) ? _NoteLinks.hashCode() : 0));
        h = ((127 * h) + ((_TargetViewManagerId != null) ? _TargetViewManagerId.hashCode() : 0));
        h = ((127 * h) + ((_TargetViewElementId != null) ? _TargetViewElementId.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<note");
        if (_HtmlFileName != null) {
            sb.append(" htmlFileName=");
            sb.append(_HtmlFileName.toString());
        }
        if (_NoteLinks != null) {
            sb.append(" noteLink=");
            sb.append(_NoteLinks.toString());
        }
        if (_TargetViewManagerId != null) {
            sb.append(" targetViewManagerId=");
            sb.append(_TargetViewManagerId.toString());
        }
        if (_TargetViewElementId != null) {
            sb.append(" targetViewElementId=");
            sb.append(_TargetViewElementId.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("note", (Note.class));
        d.register("noteLink", (NoteLink.class));
        d.register("noteLinkTarget", (NoteLinkTarget.class));
        d.register("notesDescriptor", (NotesDescriptor.class));
        d.freezeElementNameMap();
        return d;
    }

    private static class NoteLinksPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof NoteLink)) {
                throw new InvalidContentObjectException(ob, (NoteLink.class));
            }
        }
    }
}
