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
 * This class is the representation of an audio-element and sets the attributes of it, e.g. the filename of an
 * audio-file or of a html-file.
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class Audio extends MarshallableObject implements Element {
    private String _HtmlFileName;
    private List _AudioLinks = PredicatedLists.createInvalidating(this, new AudioLinksPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_AudioLinks = new AudioLinksPredicate();
    private String _TargetViewManagerId;
    private String _TargetViewElementId;
    private String _SoundFileName;
    private String lastModificationDate;
    
    public String getLastModificationDate() {
    	return lastModificationDate;
    }
    
    public void setLastModificationDate(String lastModificationDate) {
    	this.lastModificationDate = lastModificationDate;
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

    /**
     * Returns the Filename of the assigned Soundfile from this audio-Object (only Filename).
     * @return String pureSoundFileName
     */
    public String getPureSoundFileName() {
        int i = _SoundFileName.lastIndexOf("\\");
        int length = _SoundFileName.length();
        String pureSoundFileName = _SoundFileName.substring(i + 1, length);
        return pureSoundFileName;
    }

    /**
     * Returns the absolute Filename of the assigned Soundfile from this audio-Object.
     * @return String _SoundFileName
     */
    public String getSoundFileName() {
        return _SoundFileName;
    }

    /**
     * Sets the Filename of the assigned Soundfile from this audio-object.
     * @param _SoundFileName The absolute Filename of the SoundFile for this audio-object.
     */
    public void setSoundFileName(String _SoundFileName) {
        this._SoundFileName = _SoundFileName;
        if (_SoundFileName == null) {
            invalidate();
        }
    }

    public List getAudioLinks() {
        return _AudioLinks;
    }

    public void deleteAudioLinks() {
        _AudioLinks = null;
        invalidate();
    }

    public void emptyAudioLinks() {
        _AudioLinks = PredicatedLists.createInvalidating(this, pred_AudioLinks, new ArrayList());
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
        for (Iterator i = _AudioLinks.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("audio");
        if (_HtmlFileName != null) {
            w.attribute("htmlFileName", _HtmlFileName.toString());
        }
        //adds attribute to the XML-File
        if (_SoundFileName != null) {
            w.attribute("soundFileName", _SoundFileName.toString());
        }
        w.attribute("targetViewManagerId", _TargetViewManagerId.toString());
        w.attribute("targetViewElementId", _TargetViewElementId.toString());
        if (_AudioLinks.size() > 0) {
            for (Iterator i = _AudioLinks.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("audio");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("audio");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("htmlFileName")) {
                if (_HtmlFileName != null) {
                    throw new DuplicateAttributeException(an);
                }
                _HtmlFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("soundFileName")) {
                if (_SoundFileName != null) {
                    throw new DuplicateAttributeException(an);
                }
                _SoundFileName = xs.takeAttributeValue();
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
            List l = PredicatedLists.create(this, pred_AudioLinks, new ArrayList());
            while (xs.atStart("audioLink")) {
                l.add(((AudioLink)u.unmarshal()));
            }
            _AudioLinks = PredicatedLists.createInvalidating(this, pred_AudioLinks, l);
        }
        xs.takeEnd("audio");
    }

    public static Audio unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static Audio unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static Audio unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((Audio)d.unmarshal(xs, (Audio.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Audio)) {
            return false;
        }
        Audio tob = ((Audio)ob);
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
        if (_SoundFileName != null) {
            if (tob._SoundFileName == null) {
                return false;
            }
            if (!_SoundFileName.equals(tob._SoundFileName)) {
                return false;
            }
        }
        else {
            if (tob._SoundFileName != null) {
                return false;
            }
        }
        if (_AudioLinks != null) {
            if (tob._AudioLinks == null) {
                return false;
            }
            if (!_AudioLinks.equals(tob._AudioLinks)) {
                return false;
            }
        }
        else {
            if (tob._AudioLinks != null) {
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
        h = ((127 * h) + ((_SoundFileName != null) ? _HtmlFileName.hashCode() : 0));
        h = ((127 * h) + ((_AudioLinks != null) ? _AudioLinks.hashCode() : 0));
        h = ((127 * h) + ((_TargetViewManagerId != null) ? _TargetViewManagerId.hashCode() : 0));
        h = ((127 * h) + ((_TargetViewElementId != null) ? _TargetViewElementId.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<audio");
        if (_HtmlFileName != null) {
            sb.append(" htmlFileName=");
            sb.append(_HtmlFileName.toString());
        }
        if (_SoundFileName != null) {
            sb.append(" soundFileName=");
            sb.append(_SoundFileName.toString());
        }
        if (_AudioLinks != null) {
            sb.append(" audioLink=");
            sb.append(_AudioLinks.toString());
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
        d.register("audio", (Audio.class));
        d.register("audioLink", (AudioLink.class));
        d.register("audioLinkTarget", (AudioLinkTarget.class));
        d.register("audioDescriptor", (AudioDescriptor.class));
        d.freezeElementNameMap();
        return d;
    }

    private static class AudioLinksPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof AudioLink)) {
                throw new InvalidContentObjectException(ob, (AudioLink.class));
            }
        }
    }
}
