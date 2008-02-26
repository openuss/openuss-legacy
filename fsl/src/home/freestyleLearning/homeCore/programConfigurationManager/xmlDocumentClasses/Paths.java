
package freestyleLearning.homeCore.programConfigurationManager.xmlDocumentClasses;

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


public class Paths
    extends MarshallableObject
    implements Element
{

    private String _FullPathToLearningUnitsDirectory;
    private String _FullPathToUsersDirectory;
    private String _FullPathToLearningUnitViewManagersDirectory;
    private String _OpenUssServerName;
    private String _AutomaticLoginUsername;

    public String getFullPathToLearningUnitsDirectory() {
        return _FullPathToLearningUnitsDirectory;
    }

    public void setFullPathToLearningUnitsDirectory(String _FullPathToLearningUnitsDirectory) {
        this._FullPathToLearningUnitsDirectory = _FullPathToLearningUnitsDirectory;
        if (_FullPathToLearningUnitsDirectory == null) {
            invalidate();
        }
    }

    public String getFullPathToUsersDirectory() {
        return _FullPathToUsersDirectory;
    }

    public void setFullPathToUsersDirectory(String _FullPathToUsersDirectory) {
        this._FullPathToUsersDirectory = _FullPathToUsersDirectory;
        if (_FullPathToUsersDirectory == null) {
            invalidate();
        }
    }

    public String getFullPathToLearningUnitViewManagersDirectory() {
        return _FullPathToLearningUnitViewManagersDirectory;
    }

    public void setFullPathToLearningUnitViewManagersDirectory(String _FullPathToLearningUnitViewManagersDirectory) {
        this._FullPathToLearningUnitViewManagersDirectory = _FullPathToLearningUnitViewManagersDirectory;
        if (_FullPathToLearningUnitViewManagersDirectory == null) {
            invalidate();
        }
    }

    public String getOpenUssServerName() {
        return _OpenUssServerName;
    }

    public void setOpenUssServerName(String _OpenUssServerName) {
        this._OpenUssServerName = _OpenUssServerName;
        if (_OpenUssServerName == null) {
            invalidate();
        }
    }

    public String getAutomaticLoginUsername() {
        return _AutomaticLoginUsername;
    }

    public void setAutomaticLoginUsername(String _AutomaticLoginUsername) {
        this._AutomaticLoginUsername = _AutomaticLoginUsername;
        if (_AutomaticLoginUsername == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_FullPathToLearningUnitsDirectory == null) {
            throw new MissingAttributeException("fullPathToLearningUnitsDirectory");
        }
        if (_FullPathToUsersDirectory == null) {
            throw new MissingAttributeException("fullPathToUsersDirectory");
        }
        if (_FullPathToLearningUnitViewManagersDirectory == null) {
            throw new MissingAttributeException("fullPathToLearningUnitViewManagersDirectory");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("paths");
        w.attribute("fullPathToLearningUnitsDirectory", _FullPathToLearningUnitsDirectory.toString());
        w.attribute("fullPathToUsersDirectory", _FullPathToUsersDirectory.toString());
        w.attribute("fullPathToLearningUnitViewManagersDirectory", _FullPathToLearningUnitViewManagersDirectory.toString());
        if (_OpenUssServerName!= null) {
            w.attribute("openUssServerName", _OpenUssServerName.toString());
        }
        if (_AutomaticLoginUsername!= null) {
            w.attribute("automaticLoginUsername", _AutomaticLoginUsername.toString());
        }
        w.end("paths");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("paths");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("fullPathToLearningUnitsDirectory")) {
                if (_FullPathToLearningUnitsDirectory!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _FullPathToLearningUnitsDirectory = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("fullPathToUsersDirectory")) {
                if (_FullPathToUsersDirectory!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _FullPathToUsersDirectory = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("fullPathToLearningUnitViewManagersDirectory")) {
                if (_FullPathToLearningUnitViewManagersDirectory!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _FullPathToLearningUnitViewManagersDirectory = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("openUssServerName")) {
                if (_OpenUssServerName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _OpenUssServerName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("automaticLoginUsername")) {
                if (_AutomaticLoginUsername!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _AutomaticLoginUsername = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("paths");
    }

    public static Paths unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static Paths unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static Paths unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((Paths) d.unmarshal(xs, (Paths.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Paths)) {
            return false;
        }
        Paths tob = ((Paths) ob);
        if (_FullPathToLearningUnitsDirectory!= null) {
            if (tob._FullPathToLearningUnitsDirectory == null) {
                return false;
            }
            if (!_FullPathToLearningUnitsDirectory.equals(tob._FullPathToLearningUnitsDirectory)) {
                return false;
            }
        } else {
            if (tob._FullPathToLearningUnitsDirectory!= null) {
                return false;
            }
        }
        if (_FullPathToUsersDirectory!= null) {
            if (tob._FullPathToUsersDirectory == null) {
                return false;
            }
            if (!_FullPathToUsersDirectory.equals(tob._FullPathToUsersDirectory)) {
                return false;
            }
        } else {
            if (tob._FullPathToUsersDirectory!= null) {
                return false;
            }
        }
        if (_FullPathToLearningUnitViewManagersDirectory!= null) {
            if (tob._FullPathToLearningUnitViewManagersDirectory == null) {
                return false;
            }
            if (!_FullPathToLearningUnitViewManagersDirectory.equals(tob._FullPathToLearningUnitViewManagersDirectory)) {
                return false;
            }
        } else {
            if (tob._FullPathToLearningUnitViewManagersDirectory!= null) {
                return false;
            }
        }
        if (_OpenUssServerName!= null) {
            if (tob._OpenUssServerName == null) {
                return false;
            }
            if (!_OpenUssServerName.equals(tob._OpenUssServerName)) {
                return false;
            }
        } else {
            if (tob._OpenUssServerName!= null) {
                return false;
            }
        }
        if (_AutomaticLoginUsername!= null) {
            if (tob._AutomaticLoginUsername == null) {
                return false;
            }
            if (!_AutomaticLoginUsername.equals(tob._AutomaticLoginUsername)) {
                return false;
            }
        } else {
            if (tob._AutomaticLoginUsername!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_FullPathToLearningUnitsDirectory!= null)?_FullPathToLearningUnitsDirectory.hashCode(): 0));
        h = ((127 *h)+((_FullPathToUsersDirectory!= null)?_FullPathToUsersDirectory.hashCode(): 0));
        h = ((127 *h)+((_FullPathToLearningUnitViewManagersDirectory!= null)?_FullPathToLearningUnitViewManagersDirectory.hashCode(): 0));
        h = ((127 *h)+((_OpenUssServerName!= null)?_OpenUssServerName.hashCode(): 0));
        h = ((127 *h)+((_AutomaticLoginUsername!= null)?_AutomaticLoginUsername.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<paths");
        if (_FullPathToLearningUnitsDirectory!= null) {
            sb.append(" fullPathToLearningUnitsDirectory=");
            sb.append(_FullPathToLearningUnitsDirectory.toString());
        }
        if (_FullPathToUsersDirectory!= null) {
            sb.append(" fullPathToUsersDirectory=");
            sb.append(_FullPathToUsersDirectory.toString());
        }
        if (_FullPathToLearningUnitViewManagersDirectory!= null) {
            sb.append(" fullPathToLearningUnitViewManagersDirectory=");
            sb.append(_FullPathToLearningUnitViewManagersDirectory.toString());
        }
        if (_OpenUssServerName!= null) {
            sb.append(" openUssServerName=");
            sb.append(_OpenUssServerName.toString());
        }
        if (_AutomaticLoginUsername!= null) {
            sb.append(" automaticLoginUsername=");
            sb.append(_AutomaticLoginUsername.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("paths", (Paths.class));
        d.register("programConfiguration", (ProgramConfiguration.class));
        d.freezeElementNameMap();
        return d;
    }

}
