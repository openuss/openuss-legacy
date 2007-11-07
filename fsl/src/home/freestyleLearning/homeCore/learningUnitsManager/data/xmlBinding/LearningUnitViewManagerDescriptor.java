package freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding;

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

public class LearningUnitViewManagerDescriptor extends MarshallableObject implements Element, IdentifiableElement {
    private String _Id;
    private String _Title;
    private String _Version;
    private String _DirectoryName;
    private String _ClassName;
    private String _Authors;

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

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String _Title) {
        this._Title = _Title;
        if (_Title == null) {
            invalidate();
        }
    }

    public String getVersion() {
        return _Version;
    }

    public void setVersion(String _Version) {
        this._Version = _Version;
        if (_Version == null) {
            invalidate();
        }
    }

    public String getDirectoryName() {
        return _DirectoryName;
    }

    public void setDirectoryName(String _DirectoryName) {
        this._DirectoryName = _DirectoryName;
        if (_DirectoryName == null) {
            invalidate();
        }
    }

    public String getClassName() {
        return _ClassName;
    }

    public void setClassName(String _ClassName) {
        this._ClassName = _ClassName;
        if (_ClassName == null) {
            invalidate();
        }
    }

    public String getAuthors() {
        return _Authors;
    }

    public void setAuthors(String _Authors) {
        this._Authors = _Authors;
        if (_Authors == null) {
            invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_Title == null) {
            throw new MissingAttributeException("title");
        }
        if (_Version == null) {
            throw new MissingAttributeException("version");
        }
        if (_DirectoryName == null) {
            throw new MissingAttributeException("directoryName");
        }
        if (_ClassName == null) {
            throw new MissingAttributeException("className");
        }
        if (_Authors == null) {
            throw new MissingAttributeException("authors");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("learningUnitViewManagerDescriptor");
        w.attribute("id", _Id.toString());
        w.attribute("title", _Title.toString());
        w.attribute("version", _Version.toString());
        w.attribute("directoryName", _DirectoryName.toString());
        w.attribute("className", _ClassName.toString());
        w.attribute("authors", _Authors.toString());
        w.end("learningUnitViewManagerDescriptor");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("learningUnitViewManagerDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id != null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("title")) {
                if (_Title != null) {
                    throw new DuplicateAttributeException(an);
                }
                _Title = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("version")) {
                if (_Version != null) {
                    throw new DuplicateAttributeException(an);
                }
                _Version = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("directoryName")) {
                if (_DirectoryName != null) {
                    throw new DuplicateAttributeException(an);
                }
                _DirectoryName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("className")) {
                if (_ClassName != null) {
                    throw new DuplicateAttributeException(an);
                }
                _ClassName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("authors")) {
                if (_Authors != null) {
                    throw new DuplicateAttributeException(an);
                }
                _Authors = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("learningUnitViewManagerDescriptor");
    }

    public static LearningUnitViewManagerDescriptor unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static LearningUnitViewManagerDescriptor unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static LearningUnitViewManagerDescriptor unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((LearningUnitViewManagerDescriptor)d.unmarshal(xs, (LearningUnitViewManagerDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof LearningUnitViewManagerDescriptor)) {
            return false;
        }
        LearningUnitViewManagerDescriptor tob = ((LearningUnitViewManagerDescriptor)ob);
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
        if (_Title != null) {
            if (tob._Title == null) {
                return false;
            }
            if (!_Title.equals(tob._Title)) {
                return false;
            }
        }
        else {
            if (tob._Title != null) {
                return false;
            }
        }
        if (_Version != null) {
            if (tob._Version == null) {
                return false;
            }
            if (!_Version.equals(tob._Version)) {
                return false;
            }
        }
        else {
            if (tob._Version != null) {
                return false;
            }
        }
        if (_DirectoryName != null) {
            if (tob._DirectoryName == null) {
                return false;
            }
            if (!_DirectoryName.equals(tob._DirectoryName)) {
                return false;
            }
        }
        else {
            if (tob._DirectoryName != null) {
                return false;
            }
        }
        if (_ClassName != null) {
            if (tob._ClassName == null) {
                return false;
            }
            if (!_ClassName.equals(tob._ClassName)) {
                return false;
            }
        }
        else {
            if (tob._ClassName != null) {
                return false;
            }
        }
        if (_Authors != null) {
            if (tob._Authors == null) {
                return false;
            }
            if (!_Authors.equals(tob._Authors)) {
                return false;
            }
        }
        else {
            if (tob._Authors != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_Id != null) ? _Id.hashCode() : 0));
        h = ((127 * h) + ((_Title != null) ? _Title.hashCode() : 0));
        h = ((127 * h) + ((_Version != null) ? _Version.hashCode() : 0));
        h = ((127 * h) + ((_DirectoryName != null) ? _DirectoryName.hashCode() : 0));
        h = ((127 * h) + ((_ClassName != null) ? _ClassName.hashCode() : 0));
        h = ((127 * h) + ((_Authors != null) ? _Authors.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<learningUnitViewManagerDescriptor");
        if (_Id != null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_Title != null) {
            sb.append(" title=");
            sb.append(_Title.toString());
        }
        if (_Version != null) {
            sb.append(" version=");
            sb.append(_Version.toString());
        }
        if (_DirectoryName != null) {
            sb.append(" directoryName=");
            sb.append(_DirectoryName.toString());
        }
        if (_ClassName != null) {
            sb.append(" className=");
            sb.append(_ClassName.toString());
        }
        if (_Authors != null) {
            sb.append(" authors=");
            sb.append(_Authors.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("learningUnitViewManagerDescriptor", (LearningUnitViewManagerDescriptor.class));
        d.register("learningUnitViewManagersDescriptor", (LearningUnitViewManagersDescriptor.class));
        d.freezeElementNameMap();
        return d;
    }
}
