
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


public class UserColorDescriptor
    extends MarshallableObject
    implements Element
{

    private String _Red;
    private String _Green;
    private String _Blue;

    public String getRed() {
        return _Red;
    }

    public void setRed(String _Red) {
        this._Red = _Red;
        if (_Red == null) {
            invalidate();
        }
    }

    public String getGreen() {
        return _Green;
    }

    public void setGreen(String _Green) {
        this._Green = _Green;
        if (_Green == null) {
            invalidate();
        }
    }

    public String getBlue() {
        return _Blue;
    }

    public void setBlue(String _Blue) {
        this._Blue = _Blue;
        if (_Blue == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Red == null) {
            throw new MissingAttributeException("red");
        }
        if (_Green == null) {
            throw new MissingAttributeException("green");
        }
        if (_Blue == null) {
            throw new MissingAttributeException("blue");
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
        w.start("userColorDescriptor");
        w.attribute("red", _Red.toString());
        w.attribute("green", _Green.toString());
        w.attribute("blue", _Blue.toString());
        w.end("userColorDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("userColorDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("red")) {
                if (_Red!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Red = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("green")) {
                if (_Green!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Green = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("blue")) {
                if (_Blue!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Blue = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("userColorDescriptor");
    }

    public static UserColorDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static UserColorDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static UserColorDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((UserColorDescriptor) d.unmarshal(xs, (UserColorDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof UserColorDescriptor)) {
            return false;
        }
        UserColorDescriptor tob = ((UserColorDescriptor) ob);
        if (_Red!= null) {
            if (tob._Red == null) {
                return false;
            }
            if (!_Red.equals(tob._Red)) {
                return false;
            }
        } else {
            if (tob._Red!= null) {
                return false;
            }
        }
        if (_Green!= null) {
            if (tob._Green == null) {
                return false;
            }
            if (!_Green.equals(tob._Green)) {
                return false;
            }
        } else {
            if (tob._Green!= null) {
                return false;
            }
        }
        if (_Blue!= null) {
            if (tob._Blue == null) {
                return false;
            }
            if (!_Blue.equals(tob._Blue)) {
                return false;
            }
        } else {
            if (tob._Blue!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Red!= null)?_Red.hashCode(): 0));
        h = ((127 *h)+((_Green!= null)?_Green.hashCode(): 0));
        h = ((127 *h)+((_Blue!= null)?_Blue.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<userColorDescriptor");
        if (_Red!= null) {
            sb.append(" red=");
            sb.append(_Red.toString());
        }
        if (_Green!= null) {
            sb.append(" green=");
            sb.append(_Green.toString());
        }
        if (_Blue!= null) {
            sb.append(" blue=");
            sb.append(_Blue.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("userColorDescriptor", (UserColorDescriptor.class));
        d.register("userDescriptor", (UserDescriptor.class));
        d.register("userFontDescriptor", (UserFontDescriptor.class));
        d.register("userFrameDescriptor", (UserFrameDescriptor.class));
        d.register("usersDescriptor", (UsersDescriptor.class));
        d.freezeElementNameMap();
        return d;
    }

}
