
package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.data.xmlBinding;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.data.xmlBinding.ViewElementLink;
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
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class ViewElement
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private String _Id;
    private String _ParentId;
    private String _Title;
    private String _Type;
    private String _LastModificationDate;
    private String _ExecutableFileName;
    private String _ProgramParameters;
    private List _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, new LearningUnitViewElementLinksPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_LearningUnitViewElementLinks = new LearningUnitViewElementLinksPredicate();
    private boolean _Folder;
    private boolean has_Folder;
    private boolean _AutoChangeWorkingDirectory;
    private boolean has_AutoChangeWorkingDirectory;
    private boolean _DirectoryContentIncluded;
    private boolean has_DirectoryContentIncluded;

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

    public String getParentId() {
        return _ParentId;
    }

    public void setParentId(String _ParentId) {
        this._ParentId = _ParentId;
        if (_ParentId == null) {
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

    public String getType() {
        return _Type;
    }

    public void setType(String _Type) {
        this._Type = _Type;
        if (_Type == null) {
            invalidate();
        }
    }

    public String getLastModificationDate() {
        return _LastModificationDate;
    }

    public void setLastModificationDate(String _LastModificationDate) {
        this._LastModificationDate = _LastModificationDate;
        if (_LastModificationDate == null) {
            invalidate();
        }
    }

    public String getExecutableFileName() {
        return _ExecutableFileName;
    }

    public void setExecutableFileName(String _ExecutableFileName) {
        this._ExecutableFileName = _ExecutableFileName;
        if (_ExecutableFileName == null) {
            invalidate();
        }
    }

    public String getProgramParameters() {
        return _ProgramParameters;
    }

    public void setProgramParameters(String _ProgramParameters) {
        this._ProgramParameters = _ProgramParameters;
        if (_ProgramParameters == null) {
            invalidate();
        }
    }

    public List getLearningUnitViewElementLinks() {
        return _LearningUnitViewElementLinks;
    }

    public void deleteLearningUnitViewElementLinks() {
        _LearningUnitViewElementLinks = null;
        invalidate();
    }

    public void emptyLearningUnitViewElementLinks() {
        _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElementLinks, new ArrayList());
    }

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

    public boolean getAutoChangeWorkingDirectory() {
        if (has_AutoChangeWorkingDirectory) {
            return _AutoChangeWorkingDirectory;
        }
        throw new NoValueException("autoChangeWorkingDirectory");
    }

    public void setAutoChangeWorkingDirectory(boolean _AutoChangeWorkingDirectory) {
        this._AutoChangeWorkingDirectory = _AutoChangeWorkingDirectory;
        has_AutoChangeWorkingDirectory = true;
        invalidate();
    }

    public boolean hasAutoChangeWorkingDirectory() {
        return has_AutoChangeWorkingDirectory;
    }

    public void deleteAutoChangeWorkingDirectory() {
        has_AutoChangeWorkingDirectory = false;
        invalidate();
    }

    public boolean getDirectoryContentIncluded() {
        if (has_DirectoryContentIncluded) {
            return _DirectoryContentIncluded;
        }
        throw new NoValueException("directoryContentIncluded");
    }

    public void setDirectoryContentIncluded(boolean _DirectoryContentIncluded) {
        this._DirectoryContentIncluded = _DirectoryContentIncluded;
        has_DirectoryContentIncluded = true;
        invalidate();
    }

    public boolean hasDirectoryContentIncluded() {
        return has_DirectoryContentIncluded;
    }

    public void deleteDirectoryContentIncluded() {
        has_DirectoryContentIncluded = false;
        invalidate();
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_ParentId == null) {
            throw new MissingAttributeException("parentId");
        }
        if (_Title == null) {
            throw new MissingAttributeException("title");
        }
        if (_Type == null) {
            throw new MissingAttributeException("type");
        }
        if (_ExecutableFileName == null) {
            throw new MissingAttributeException("executableFileName");
        }
        if (!has_Folder) {
            throw new MissingAttributeException("folder");
        }
        if (!has_DirectoryContentIncluded) {
            throw new MissingAttributeException("directoryContentIncluded");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _LearningUnitViewElementLinks.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("viewElement");
        w.attribute("id", _Id.toString());
        w.attribute("parentId", _ParentId.toString());
        w.attribute("title", _Title.toString());
        w.attribute("type", _Type.toString());
        if (_LastModificationDate!= null) {
            w.attribute("lastModificationDate", _LastModificationDate.toString());
        }
        w.attribute("executableFileName", _ExecutableFileName.toString());
        if (_ProgramParameters!= null) {
            w.attribute("programParameters", _ProgramParameters.toString());
        }
        w.attribute("folder", printBoolean(getFolder()));
        if (has_AutoChangeWorkingDirectory) {
            w.attribute("autoChangeWorkingDirectory", printBoolean(getAutoChangeWorkingDirectory()));
        }
        w.attribute("directoryContentIncluded", printBoolean(getDirectoryContentIncluded()));
        if (_LearningUnitViewElementLinks.size()> 0) {
            for (Iterator i = _LearningUnitViewElementLinks.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("viewElement");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("viewElement");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("parentId")) {
                if (_ParentId!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ParentId = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("title")) {
                if (_Title!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Title = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("type")) {
                if (_Type!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Type = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("lastModificationDate")) {
                if (_LastModificationDate!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _LastModificationDate = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("executableFileName")) {
                if (_ExecutableFileName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ExecutableFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("programParameters")) {
                if (_ProgramParameters!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ProgramParameters = xs.takeAttributeValue();
                continue;
            }
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
            if (an.equals("autoChangeWorkingDirectory")) {
                if (has_AutoChangeWorkingDirectory) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _AutoChangeWorkingDirectory = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_AutoChangeWorkingDirectory = true;
                continue;
            }
            if (an.equals("directoryContentIncluded")) {
                if (has_DirectoryContentIncluded) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _DirectoryContentIncluded = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_DirectoryContentIncluded = true;
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_LearningUnitViewElementLinks, new ArrayList());
            while (xs.atStart("viewElementLink")) {
                l.add(((ViewElementLink) u.unmarshal()));
            }
            _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElementLinks, l);
        }
        xs.takeEnd("viewElement");
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

    public static ViewElement unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static ViewElement unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static ViewElement unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((ViewElement) d.unmarshal(xs, (ViewElement.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof ViewElement)) {
            return false;
        }
        ViewElement tob = ((ViewElement) ob);
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
        if (_ParentId!= null) {
            if (tob._ParentId == null) {
                return false;
            }
            if (!_ParentId.equals(tob._ParentId)) {
                return false;
            }
        } else {
            if (tob._ParentId!= null) {
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
        if (_Type!= null) {
            if (tob._Type == null) {
                return false;
            }
            if (!_Type.equals(tob._Type)) {
                return false;
            }
        } else {
            if (tob._Type!= null) {
                return false;
            }
        }
        if (_LastModificationDate!= null) {
            if (tob._LastModificationDate == null) {
                return false;
            }
            if (!_LastModificationDate.equals(tob._LastModificationDate)) {
                return false;
            }
        } else {
            if (tob._LastModificationDate!= null) {
                return false;
            }
        }
        if (_ExecutableFileName!= null) {
            if (tob._ExecutableFileName == null) {
                return false;
            }
            if (!_ExecutableFileName.equals(tob._ExecutableFileName)) {
                return false;
            }
        } else {
            if (tob._ExecutableFileName!= null) {
                return false;
            }
        }
        if (_ProgramParameters!= null) {
            if (tob._ProgramParameters == null) {
                return false;
            }
            if (!_ProgramParameters.equals(tob._ProgramParameters)) {
                return false;
            }
        } else {
            if (tob._ProgramParameters!= null) {
                return false;
            }
        }
        if (_LearningUnitViewElementLinks!= null) {
            if (tob._LearningUnitViewElementLinks == null) {
                return false;
            }
            if (!_LearningUnitViewElementLinks.equals(tob._LearningUnitViewElementLinks)) {
                return false;
            }
        } else {
            if (tob._LearningUnitViewElementLinks!= null) {
                return false;
            }
        }
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
        if (has_AutoChangeWorkingDirectory) {
            if (!tob.has_AutoChangeWorkingDirectory) {
                return false;
            }
            if (_AutoChangeWorkingDirectory!= tob._AutoChangeWorkingDirectory) {
                return false;
            }
        } else {
            if (tob.has_AutoChangeWorkingDirectory) {
                return false;
            }
        }
        if (has_DirectoryContentIncluded) {
            if (!tob.has_DirectoryContentIncluded) {
                return false;
            }
            if (_DirectoryContentIncluded!= tob._DirectoryContentIncluded) {
                return false;
            }
        } else {
            if (tob.has_DirectoryContentIncluded) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_ParentId!= null)?_ParentId.hashCode(): 0));
        h = ((127 *h)+((_Title!= null)?_Title.hashCode(): 0));
        h = ((127 *h)+((_Type!= null)?_Type.hashCode(): 0));
        h = ((127 *h)+((_LastModificationDate!= null)?_LastModificationDate.hashCode(): 0));
        h = ((127 *h)+((_ExecutableFileName!= null)?_ExecutableFileName.hashCode(): 0));
        h = ((127 *h)+((_ProgramParameters!= null)?_ProgramParameters.hashCode(): 0));
        h = ((127 *h)+((_LearningUnitViewElementLinks!= null)?_LearningUnitViewElementLinks.hashCode(): 0));
        h = ((31 *h)+(_Folder? 137 : 139));
        h = ((31 *h)+(_AutoChangeWorkingDirectory? 137 : 139));
        h = ((31 *h)+(_DirectoryContentIncluded? 137 : 139));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<viewElement");
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_ParentId!= null) {
            sb.append(" parentId=");
            sb.append(_ParentId.toString());
        }
        if (_Title!= null) {
            sb.append(" title=");
            sb.append(_Title.toString());
        }
        if (_Type!= null) {
            sb.append(" type=");
            sb.append(_Type.toString());
        }
        if (_LastModificationDate!= null) {
            sb.append(" lastModificationDate=");
            sb.append(_LastModificationDate.toString());
        }
        if (_ExecutableFileName!= null) {
            sb.append(" executableFileName=");
            sb.append(_ExecutableFileName.toString());
        }
        if (_ProgramParameters!= null) {
            sb.append(" programParameters=");
            sb.append(_ProgramParameters.toString());
        }
        if (_LearningUnitViewElementLinks!= null) {
            sb.append(" viewElementLink=");
            sb.append(_LearningUnitViewElementLinks.toString());
        }
        if (has_Folder) {
            sb.append(" folder=");
            sb.append(printBoolean(_Folder));
        }
        if (has_AutoChangeWorkingDirectory) {
            sb.append(" autoChangeWorkingDirectory=");
            sb.append(printBoolean(_AutoChangeWorkingDirectory));
        }
        if (has_DirectoryContentIncluded) {
            sb.append(" directoryContentIncluded=");
            sb.append(printBoolean(_DirectoryContentIncluded));
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return LearningByDoingDescriptor.newDispatcher();
    }


    private static class LearningUnitViewElementLinksPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof ViewElementLink)) {
                throw new InvalidContentObjectException(ob, (ViewElementLink.class));
            }
        }

    }

}
