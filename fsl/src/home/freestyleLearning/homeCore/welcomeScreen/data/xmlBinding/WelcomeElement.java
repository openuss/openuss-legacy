
package freestyleLearning.homeCore.welcomeScreen.data.xmlBinding;

import freestyleLearning.homeCore.welcomeScreen.data.xmlBinding.WelcomeElementLink;
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


public class WelcomeElement
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private boolean _Folder;
    private boolean has_Folder;
    private String _Id;
    private String _ParentId;
    private String _Title;
    private String _Type;
    private String _HtmlFileName;
    private List _WelcomeElementLinks = PredicatedLists.createInvalidating(this, new WelcomeElementLinksPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_WelcomeElementLinks = new WelcomeElementLinksPredicate();

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

    public String getHtmlFileName() {
        return _HtmlFileName;
    }

    public void setHtmlFileName(String _HtmlFileName) {
        this._HtmlFileName = _HtmlFileName;
        if (_HtmlFileName == null) {
            invalidate();
        }
    }

    public List getWelcomeElementLinks() {
        return _WelcomeElementLinks;
    }

    public void deleteWelcomeElementLinks() {
        _WelcomeElementLinks = null;
        invalidate();
    }

    public void emptyWelcomeElementLinks() {
        _WelcomeElementLinks = PredicatedLists.createInvalidating(this, pred_WelcomeElementLinks, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (!has_Folder) {
            throw new MissingAttributeException("folder");
        }
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
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _WelcomeElementLinks.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("welcomeElement");
        w.attribute("folder", printBoolean(getFolder()));
        w.attribute("id", _Id.toString());
        w.attribute("parentId", _ParentId.toString());
        w.attribute("title", _Title.toString());
        w.attribute("type", _Type.toString());
        if (_HtmlFileName!= null) {
            w.attribute("htmlFileName", _HtmlFileName.toString());
        }
        if (_WelcomeElementLinks.size()> 0) {
            for (Iterator i = _WelcomeElementLinks.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("welcomeElement");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("welcomeElement");
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
            if (an.equals("htmlFileName")) {
                if (_HtmlFileName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _HtmlFileName = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_WelcomeElementLinks, new ArrayList());
            while (xs.atStart("welcomeElementLink")) {
                l.add(((WelcomeElementLink) u.unmarshal()));
            }
            _WelcomeElementLinks = PredicatedLists.createInvalidating(this, pred_WelcomeElementLinks, l);
        }
        xs.takeEnd("welcomeElement");
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

    public static WelcomeElement unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static WelcomeElement unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static WelcomeElement unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((WelcomeElement) d.unmarshal(xs, (WelcomeElement.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof WelcomeElement)) {
            return false;
        }
        WelcomeElement tob = ((WelcomeElement) ob);
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
        if (_HtmlFileName!= null) {
            if (tob._HtmlFileName == null) {
                return false;
            }
            if (!_HtmlFileName.equals(tob._HtmlFileName)) {
                return false;
            }
        } else {
            if (tob._HtmlFileName!= null) {
                return false;
            }
        }
        if (_WelcomeElementLinks!= null) {
            if (tob._WelcomeElementLinks == null) {
                return false;
            }
            if (!_WelcomeElementLinks.equals(tob._WelcomeElementLinks)) {
                return false;
            }
        } else {
            if (tob._WelcomeElementLinks!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((31 *h)+(_Folder? 137 : 139));
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_ParentId!= null)?_ParentId.hashCode(): 0));
        h = ((127 *h)+((_Title!= null)?_Title.hashCode(): 0));
        h = ((127 *h)+((_Type!= null)?_Type.hashCode(): 0));
        h = ((127 *h)+((_HtmlFileName!= null)?_HtmlFileName.hashCode(): 0));
        h = ((127 *h)+((_WelcomeElementLinks!= null)?_WelcomeElementLinks.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<welcomeElement");
        if (has_Folder) {
            sb.append(" folder=");
            sb.append(printBoolean(_Folder));
        }
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
        if (_HtmlFileName!= null) {
            sb.append(" htmlFileName=");
            sb.append(_HtmlFileName.toString());
        }
        if (_WelcomeElementLinks!= null) {
            sb.append(" welcomeElementLink=");
            sb.append(_WelcomeElementLinks.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("welcomeElement", (WelcomeElement.class));
        d.register("welcomeElementLink", (WelcomeElementLink.class));
        d.register("welcomeElementLinkTarget", (WelcomeElementLinkTarget.class));
        d.register("welcomeScreenDescriptor", (WelcomeScreenDescriptor.class));
        d.freezeElementNameMap();
        return d;
    }


    private static class WelcomeElementLinksPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof WelcomeElementLink)) {
                throw new InvalidContentObjectException(ob, (WelcomeElementLink.class));
            }
        }

    }

}
