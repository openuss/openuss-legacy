package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding;

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

/**
 * This class is used to write data to a xml-file, to save the structure of elements (here: audio) in the Audio-view.
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class AudioDescriptor extends MarshallableRootElement implements RootElement {
    private List _Audio = PredicatedLists.createInvalidating(this, new AudioPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Audio = new AudioPredicate();

    public List getAudio() {
        return _Audio;
    }

    public void deleteAudio() {
        _Audio = null;
        invalidate();
    }

    public void emptyAudio() {
        _Audio = PredicatedLists.createInvalidating(this, pred_Audio, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
    }

    public void validate(Validator v) throws StructureValidationException {
        for (Iterator i = _Audio.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("audioDescriptor");
        if (_Audio.size() > 0) {
            for (Iterator i = _Audio.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("audioDescriptor");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("audioDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_Audio, new ArrayList());
            while (xs.atStart("audio")) {
                l.add(((Audio)u.unmarshal()));
            }
            _Audio = PredicatedLists.createInvalidating(this, pred_Audio, l);
        }
        xs.takeEnd("audioDescriptor");
    }

    public static AudioDescriptor unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static AudioDescriptor unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static AudioDescriptor unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((AudioDescriptor)d.unmarshal(xs, (AudioDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof AudioDescriptor)) {
            return false;
        }
        AudioDescriptor tob = ((AudioDescriptor)ob);
        if (_Audio != null) {
            if (tob._Audio == null) {
                return false;
            }
            if (!_Audio.equals(tob._Audio)) {
                return false;
            }
        }
        else {
            if (tob._Audio != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_Audio != null) ? _Audio.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<audioDescriptor");
        if (_Audio != null) {
            sb.append(" audio=");
            sb.append(_Audio.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Audio.newDispatcher();
    }

    private static class AudioPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof Audio)) {
                throw new InvalidContentObjectException(ob, (Audio.class));
            }
        }
    }
}
