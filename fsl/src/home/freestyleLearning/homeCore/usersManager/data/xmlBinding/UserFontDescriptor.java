
package freestyleLearning.homeCore.usersManager.data.xmlBinding;

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


public class UserFontDescriptor
    extends MarshallableObject
    implements Element
{

    private String _StructureTreeFontSize;
    private String _ElementsContentsPanelFontSize;

    public String getStructureTreeFontSize() {
        return _StructureTreeFontSize;
    }

    public void setStructureTreeFontSize(String _StructureTreeFontSize) {
        this._StructureTreeFontSize = _StructureTreeFontSize;
        if (_StructureTreeFontSize == null) {
            invalidate();
        }
    }

    public String getElementsContentsPanelFontSize() {
        return _ElementsContentsPanelFontSize;
    }

    public void setElementsContentsPanelFontSize(String _ElementsContentsPanelFontSize) {
        this._ElementsContentsPanelFontSize = _ElementsContentsPanelFontSize;
        if (_ElementsContentsPanelFontSize == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_StructureTreeFontSize == null) {
            throw new MissingAttributeException("structureTreeFontSize");
        }
        if (_ElementsContentsPanelFontSize == null) {
            throw new MissingAttributeException("elementsContentsPanelFontSize");
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
        w.start("userFontDescriptor");
        w.attribute("structureTreeFontSize", _StructureTreeFontSize.toString());
        w.attribute("elementsContentsPanelFontSize", _ElementsContentsPanelFontSize.toString());
        w.end("userFontDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("userFontDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("structureTreeFontSize")) {
                if (_StructureTreeFontSize!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _StructureTreeFontSize = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("elementsContentsPanelFontSize")) {
                if (_ElementsContentsPanelFontSize!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ElementsContentsPanelFontSize = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("userFontDescriptor");
    }

    public static UserFontDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static UserFontDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static UserFontDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((UserFontDescriptor) d.unmarshal(xs, (UserFontDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof UserFontDescriptor)) {
            return false;
        }
        UserFontDescriptor tob = ((UserFontDescriptor) ob);
        if (_StructureTreeFontSize!= null) {
            if (tob._StructureTreeFontSize == null) {
                return false;
            }
            if (!_StructureTreeFontSize.equals(tob._StructureTreeFontSize)) {
                return false;
            }
        } else {
            if (tob._StructureTreeFontSize!= null) {
                return false;
            }
        }
        if (_ElementsContentsPanelFontSize!= null) {
            if (tob._ElementsContentsPanelFontSize == null) {
                return false;
            }
            if (!_ElementsContentsPanelFontSize.equals(tob._ElementsContentsPanelFontSize)) {
                return false;
            }
        } else {
            if (tob._ElementsContentsPanelFontSize!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_StructureTreeFontSize!= null)?_StructureTreeFontSize.hashCode(): 0));
        h = ((127 *h)+((_ElementsContentsPanelFontSize!= null)?_ElementsContentsPanelFontSize.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<userFontDescriptor");
        if (_StructureTreeFontSize!= null) {
            sb.append(" structureTreeFontSize=");
            sb.append(_StructureTreeFontSize.toString());
        }
        if (_ElementsContentsPanelFontSize!= null) {
            sb.append(" elementsContentsPanelFontSize=");
            sb.append(_ElementsContentsPanelFontSize.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return UserColorDescriptor.newDispatcher();
    }

}
