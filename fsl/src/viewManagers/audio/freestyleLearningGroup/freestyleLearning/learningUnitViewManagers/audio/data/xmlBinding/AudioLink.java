package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding;

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

/**
 * Class for adding a link (intern or extern) to the xml-file-structure.
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class AudioLink extends MarshallableObject implements Element {
    private String _Id;
    private List _AudioLinkTargets = PredicatedLists.createInvalidating(this, new AudioLinkTargetsPredicate(),
        new ArrayList());
    private PredicatedLists.Predicate pred_AudioLinkTargets = new AudioLinkTargetsPredicate();

    public String getId() {
        return _Id;
    }

    public void setId(String _Id) {
        this._Id = _Id;
        if (_Id == null) {
            invalidate();
        }
    }

    public List getAudioLinkTargets() {
        return _AudioLinkTargets;
    }

    public void deleteAudioLinkTargets() {
        _AudioLinkTargets = null;
        invalidate();
    }

    public void emptyAudioLinkTargets() {
        _AudioLinkTargets = PredicatedLists.createInvalidating(this, pred_AudioLinkTargets, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        for (Iterator i = _AudioLinkTargets.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("audioLink");
        w.attribute("id", _Id.toString());
        if (_AudioLinkTargets.size() > 0) {
            for (Iterator i = _AudioLinkTargets.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("audioLink");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("audioLink");
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
            List l = PredicatedLists.create(this, pred_AudioLinkTargets, new ArrayList());
            while (xs.atStart("audioLinkTarget")) {
                l.add(((AudioLinkTarget)u.unmarshal()));
            }
            _AudioLinkTargets = PredicatedLists.createInvalidating(this, pred_AudioLinkTargets, l);
        }
        xs.takeEnd("audioLink");
    }

    public static AudioLink unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static AudioLink unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static AudioLink unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((AudioLink)d.unmarshal(xs, (AudioLink.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof AudioLink)) {
            return false;
        }
        AudioLink tob = ((AudioLink)ob);
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
        if (_AudioLinkTargets != null) {
            if (tob._AudioLinkTargets == null) {
                return false;
            }
            if (!_AudioLinkTargets.equals(tob._AudioLinkTargets)) {
                return false;
            }
        }
        else {
            if (tob._AudioLinkTargets != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
        h = ((127 * h) + ((_AudioLinkTargets != null) ? _AudioLinkTargets.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<audioLink");
        if (_Id != null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_AudioLinkTargets != null) {
            sb.append(" audioLinkTarget=");
            sb.append(_AudioLinkTargets.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Audio.newDispatcher();
    }

    private static class AudioLinkTargetsPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof AudioLinkTarget)) {
                throw new InvalidContentObjectException(ob, (AudioLinkTarget.class));
            }
        }
    }
}
