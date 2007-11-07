
package freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.IdentifiableElement;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.NoValueException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class LearningUnitDescriptor
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private boolean _Folder;
    private boolean has_Folder;
    private String _Id;
    private String _ParentID;
    private String _Title;
    private String _EnrollmentId;
    private String _OpenUssServerName;
    private String _Version;
    private String _Path;
    private String _Authors;
    private List _LearningUnitViewManagers = null;
    private PredicatedLists.Predicate pred_LearningUnitViewManagers = new LearningUnitViewManagersPredicate();
    private List _Prerequisites = null;
    private PredicatedLists.Predicate pred_Prerequisites = new PrerequisitesPredicate();

    public boolean getFolder() {
        if (has_Folder) {
            return _Folder;
        }
        throw new NoValueException("folder");
    }

    public void setFolder(boolean _Folder) {
        this._Folder = _Folder;
        has_Folder = true;
        invalidate();
    }

    public boolean hasFolder() {
        return has_Folder;
    }

    public void deleteFolder() {
        has_Folder = false;
        invalidate();
    }

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

    public String getParentID() {
        return _ParentID;
    }

    public void setParentID(String _ParentID) {
        this._ParentID = _ParentID;
        if (_ParentID == null) {
            invalidate();
        }
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

    public String getEnrollmentId() {
        return _EnrollmentId;
    }

    public void setEnrollmentId(String _EnrollmentId) {
        this._EnrollmentId = _EnrollmentId;
        if (_EnrollmentId == null) {
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

    public String getVersion() {
        return _Version;
    }

    public void setVersion(String _Version) {
        this._Version = _Version;
        if (_Version == null) {
            invalidate();
        }
    }

    public String getPath() {
        return _Path;
    }

    public void setPath(String _Path) {
        this._Path = _Path;
        if (_Path == null) {
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

    public List getLearningUnitViewManagers() {
        return _LearningUnitViewManagers;
    }

    public void deleteLearningUnitViewManagers() {
        _LearningUnitViewManagers = null;
        invalidate();
    }

    public void emptyLearningUnitViewManagers() {
        _LearningUnitViewManagers = PredicatedLists.createInvalidating(this, pred_LearningUnitViewManagers, new ArrayList());
    }

    public List getPrerequisites() {
        return _Prerequisites;
    }

    public void deletePrerequisites() {
        _Prerequisites = null;
        invalidate();
    }

    public void emptyPrerequisites() {
        _Prerequisites = PredicatedLists.createInvalidating(this, pred_Prerequisites, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_Title == null) {
            throw new MissingAttributeException("title");
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
        w.start("learningUnitDescriptor");
        if (has_Folder) {
            w.attribute("folder", printBoolean(getFolder()));
        }
        w.attribute("id", _Id.toString());
        if (_ParentID!= null) {
            w.attribute("parentID", _ParentID.toString());
        }
        w.attribute("title", _Title.toString());
        if (_EnrollmentId!= null) {
            w.attribute("enrollmentId", _EnrollmentId.toString());
        }
        if (_OpenUssServerName!= null) {
            w.attribute("openUssServerName", _OpenUssServerName.toString());
        }
        if (_Version!= null) {
            w.attribute("version", _Version.toString());
        }
        if (_Path!= null) {
            w.attribute("path", _Path.toString());
        }
        if (_Authors!= null) {
            w.attribute("authors", _Authors.toString());
        }
        if (_LearningUnitViewManagers!= null) {
            w.attributeName("learningUnitViewManagers");
            for (Iterator i = _LearningUnitViewManagers.iterator(); i.hasNext(); ) {
                w.attributeValueToken(((String) i.next()).toString());
            }
        }
        if (_Prerequisites!= null) {
            w.attributeName("prerequisites");
            for (Iterator i = _Prerequisites.iterator(); i.hasNext(); ) {
                w.attributeValueToken(((String) i.next()).toString());
            }
        }
        w.end("learningUnitDescriptor");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("learningUnitDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("folder")) {
                if (has_Folder) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _Folder = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_Folder = true;
                continue;
            }
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("parentID")) {
                if (_ParentID!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ParentID = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("title")) {
                if (_Title!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Title = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("enrollmentId")) {
                if (_EnrollmentId!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _EnrollmentId = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("openUssServerName")) {
                if (_OpenUssServerName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _OpenUssServerName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("version")) {
                if (_Version!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Version = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("path")) {
                if (_Path!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Path = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("authors")) {
                if (_Authors!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Authors = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("learningUnitViewManagers")) {
                if (_LearningUnitViewManagers!= null) {
                    throw new DuplicateAttributeException(an);
                }
                ArrayList l = new ArrayList();
                xs.tokenizeAttributeValue();
                while (xs.atAttributeValueToken()) {
                    l.add(String.valueOf(xs.takeAttributeValueToken()));
                }
                _LearningUnitViewManagers = PredicatedLists.createInvalidating(this, pred_LearningUnitViewManagers, l);
                continue;
            }
            if (an.equals("prerequisites")) {
                if (_Prerequisites!= null) {
                    throw new DuplicateAttributeException(an);
                }
                ArrayList l = new ArrayList();
                xs.tokenizeAttributeValue();
                while (xs.atAttributeValueToken()) {
                    l.add(String.valueOf(xs.takeAttributeValueToken()));
                }
                _Prerequisites = PredicatedLists.createInvalidating(this, pred_Prerequisites, l);
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("learningUnitDescriptor");
    }

    private static boolean readBoolean(String s)
        throws ConversionException
    {
        if (s.equals("true")) {
            return true;
        }
        if (s.equals("false")) {
            return false;
        }
        throw new ConversionException(s);
    }

    public static LearningUnitDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static LearningUnitDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static LearningUnitDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((LearningUnitDescriptor) d.unmarshal(xs, (LearningUnitDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof LearningUnitDescriptor)) {
            return false;
        }
        LearningUnitDescriptor tob = ((LearningUnitDescriptor) ob);
        if (has_Folder) {
            if (!tob.has_Folder) {
                return false;
            }
            if (_Folder!= tob._Folder) {
                return false;
            }
        } else {
            if (tob.has_Folder) {
                return false;
            }
        }
        if (_Id!= null) {
            if (tob._Id == null) {
                return false;
            }
            if (!_Id.equals(tob._Id)) {
                return false;
            }
        } else {
            if (tob._Id!= null) {
                return false;
            }
        }
        if (_ParentID!= null) {
            if (tob._ParentID == null) {
                return false;
            }
            if (!_ParentID.equals(tob._ParentID)) {
                return false;
            }
        } else {
            if (tob._ParentID!= null) {
                return false;
            }
        }
        if (_Title!= null) {
            if (tob._Title == null) {
                return false;
            }
            if (!_Title.equals(tob._Title)) {
                return false;
            }
        } else {
            if (tob._Title!= null) {
                return false;
            }
        }
        if (_EnrollmentId!= null) {
            if (tob._EnrollmentId == null) {
                return false;
            }
            if (!_EnrollmentId.equals(tob._EnrollmentId)) {
                return false;
            }
        } else {
            if (tob._EnrollmentId!= null) {
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
        if (_Version!= null) {
            if (tob._Version == null) {
                return false;
            }
            if (!_Version.equals(tob._Version)) {
                return false;
            }
        } else {
            if (tob._Version!= null) {
                return false;
            }
        }
        if (_Path!= null) {
            if (tob._Path == null) {
                return false;
            }
            if (!_Path.equals(tob._Path)) {
                return false;
            }
        } else {
            if (tob._Path!= null) {
                return false;
            }
        }
        if (_Authors!= null) {
            if (tob._Authors == null) {
                return false;
            }
            if (!_Authors.equals(tob._Authors)) {
                return false;
            }
        } else {
            if (tob._Authors!= null) {
                return false;
            }
        }
        if (_LearningUnitViewManagers!= null) {
            if (tob._LearningUnitViewManagers == null) {
                return false;
            }
            if (!_LearningUnitViewManagers.equals(tob._LearningUnitViewManagers)) {
                return false;
            }
        } else {
            if (tob._LearningUnitViewManagers!= null) {
                return false;
            }
        }
        if (_Prerequisites!= null) {
            if (tob._Prerequisites == null) {
                return false;
            }
            if (!_Prerequisites.equals(tob._Prerequisites)) {
                return false;
            }
        } else {
            if (tob._Prerequisites!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((31 *h)+(_Folder? 137 : 139));
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_ParentID!= null)?_ParentID.hashCode(): 0));
        h = ((127 *h)+((_Title!= null)?_Title.hashCode(): 0));
        h = ((127 *h)+((_EnrollmentId!= null)?_EnrollmentId.hashCode(): 0));
        h = ((127 *h)+((_OpenUssServerName!= null)?_OpenUssServerName.hashCode(): 0));
        h = ((127 *h)+((_Version!= null)?_Version.hashCode(): 0));
        h = ((127 *h)+((_Path!= null)?_Path.hashCode(): 0));
        h = ((127 *h)+((_Authors!= null)?_Authors.hashCode(): 0));
        h = ((127 *h)+((_LearningUnitViewManagers!= null)?_LearningUnitViewManagers.hashCode(): 0));
        h = ((127 *h)+((_Prerequisites!= null)?_Prerequisites.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<learningUnitDescriptor");
        if (has_Folder) {
            sb.append(" folder=");
            sb.append(printBoolean(_Folder));
        }
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_ParentID!= null) {
            sb.append(" parentID=");
            sb.append(_ParentID.toString());
        }
        if (_Title!= null) {
            sb.append(" title=");
            sb.append(_Title.toString());
        }
        if (_EnrollmentId!= null) {
            sb.append(" enrollmentId=");
            sb.append(_EnrollmentId.toString());
        }
        if (_OpenUssServerName!= null) {
            sb.append(" openUssServerName=");
            sb.append(_OpenUssServerName.toString());
        }
        if (_Version!= null) {
            sb.append(" version=");
            sb.append(_Version.toString());
        }
        if (_Path!= null) {
            sb.append(" path=");
            sb.append(_Path.toString());
        }
        if (_Authors!= null) {
            sb.append(" authors=");
            sb.append(_Authors.toString());
        }
        if (_LearningUnitViewManagers!= null) {
            sb.append(" learningUnitViewManagers=");
            sb.append(_LearningUnitViewManagers.toString());
        }
        if (_Prerequisites!= null) {
            sb.append(" prerequisites=");
            sb.append(_Prerequisites.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("learningUnitDescriptor", (LearningUnitDescriptor.class));
        d.register("learningUnitsDescriptor", (LearningUnitsDescriptor.class));
        d.freezeElementNameMap();
        return d;
    }


    private static class LearningUnitViewManagersPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof String)) {
                throw new InvalidContentObjectException(ob, (String.class));
            }
        }

    }


    private static class PrerequisitesPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof String)) {
                throw new InvalidContentObjectException(ob, (String.class));
            }
        }

    }

}
