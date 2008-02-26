
package freestyleLearning.homeCore.usersManager.data.xmlBinding;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.NoValueException;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class UserFrameDescriptor
    extends MarshallableObject
    implements Element
{

    private boolean _FrameMaximized;
    private boolean has_FrameMaximized;
    private int _FrameWidth;
    private boolean has_FrameWidth;
    private int _FrameHeight;
    private boolean has_FrameHeight;
    private int _FrameLocationX;
    private boolean has_FrameLocationX;
    private int _FrameLocationY;
    private boolean has_FrameLocationY;

    public boolean getFrameMaximized() {
        if (has_FrameMaximized) {
            return _FrameMaximized;
        }
        throw new NoValueException("frameMaximized");
    }

    public void setFrameMaximized(boolean _FrameMaximized) {
        this._FrameMaximized = _FrameMaximized;
        has_FrameMaximized = true;
        invalidate();
    }

    public boolean hasFrameMaximized() {
        return has_FrameMaximized;
    }

    public void deleteFrameMaximized() {
        has_FrameMaximized = false;
        invalidate();
    }

    public int getFrameWidth() {
        if (has_FrameWidth) {
            return _FrameWidth;
        }
        throw new NoValueException("frameWidth");
    }

    public void setFrameWidth(int _FrameWidth) {
        this._FrameWidth = _FrameWidth;
        has_FrameWidth = true;
        invalidate();
    }

    public boolean hasFrameWidth() {
        return has_FrameWidth;
    }

    public void deleteFrameWidth() {
        has_FrameWidth = false;
        invalidate();
    }

    public int getFrameHeight() {
        if (has_FrameHeight) {
            return _FrameHeight;
        }
        throw new NoValueException("frameHeight");
    }

    public void setFrameHeight(int _FrameHeight) {
        this._FrameHeight = _FrameHeight;
        has_FrameHeight = true;
        invalidate();
    }

    public boolean hasFrameHeight() {
        return has_FrameHeight;
    }

    public void deleteFrameHeight() {
        has_FrameHeight = false;
        invalidate();
    }

    public int getFrameLocationX() {
        if (has_FrameLocationX) {
            return _FrameLocationX;
        }
        throw new NoValueException("frameLocationX");
    }

    public void setFrameLocationX(int _FrameLocationX) {
        this._FrameLocationX = _FrameLocationX;
        has_FrameLocationX = true;
        invalidate();
    }

    public boolean hasFrameLocationX() {
        return has_FrameLocationX;
    }

    public void deleteFrameLocationX() {
        has_FrameLocationX = false;
        invalidate();
    }

    public int getFrameLocationY() {
        if (has_FrameLocationY) {
            return _FrameLocationY;
        }
        throw new NoValueException("frameLocationY");
    }

    public void setFrameLocationY(int _FrameLocationY) {
        this._FrameLocationY = _FrameLocationY;
        has_FrameLocationY = true;
        invalidate();
    }

    public boolean hasFrameLocationY() {
        return has_FrameLocationY;
    }

    public void deleteFrameLocationY() {
        has_FrameLocationY = false;
        invalidate();
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (!has_FrameMaximized) {
            throw new MissingAttributeException("frameMaximized");
        }
        if (!has_FrameWidth) {
            throw new MissingAttributeException("frameWidth");
        }
        if (!has_FrameHeight) {
            throw new MissingAttributeException("frameHeight");
        }
        if (!has_FrameLocationX) {
            throw new MissingAttributeException("frameLocationX");
        }
        if (!has_FrameLocationY) {
            throw new MissingAttributeException("frameLocationY");
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
        w.start("userFrameDescriptor");
        w.attribute("frameMaximized", printBoolean(getFrameMaximized()));
        w.attribute("frameWidth", Integer.toString(getFrameWidth()));
        w.attribute("frameHeight", Integer.toString(getFrameHeight()));
        w.attribute("frameLocationX", Integer.toString(getFrameLocationX()));
        w.attribute("frameLocationY", Integer.toString(getFrameLocationY()));
        w.end("userFrameDescriptor");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("userFrameDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("frameMaximized")) {
                if (has_FrameMaximized) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _FrameMaximized = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_FrameMaximized = true;
                continue;
            }
            if (an.equals("frameWidth")) {
                if (has_FrameWidth) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _FrameWidth = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_FrameWidth = true;
                continue;
            }
            if (an.equals("frameHeight")) {
                if (has_FrameHeight) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _FrameHeight = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_FrameHeight = true;
                continue;
            }
            if (an.equals("frameLocationX")) {
                if (has_FrameLocationX) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _FrameLocationX = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_FrameLocationX = true;
                continue;
            }
            if (an.equals("frameLocationY")) {
                if (has_FrameLocationY) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _FrameLocationY = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_FrameLocationY = true;
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("userFrameDescriptor");
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

    public static UserFrameDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static UserFrameDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static UserFrameDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((UserFrameDescriptor) d.unmarshal(xs, (UserFrameDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof UserFrameDescriptor)) {
            return false;
        }
        UserFrameDescriptor tob = ((UserFrameDescriptor) ob);
        if (has_FrameMaximized) {
            if (!tob.has_FrameMaximized) {
                return false;
            }
            if (_FrameMaximized!= tob._FrameMaximized) {
                return false;
            }
        } else {
            if (tob.has_FrameMaximized) {
                return false;
            }
        }
        if (has_FrameWidth) {
            if (!tob.has_FrameWidth) {
                return false;
            }
            if (_FrameWidth!= tob._FrameWidth) {
                return false;
            }
        } else {
            if (tob.has_FrameWidth) {
                return false;
            }
        }
        if (has_FrameHeight) {
            if (!tob.has_FrameHeight) {
                return false;
            }
            if (_FrameHeight!= tob._FrameHeight) {
                return false;
            }
        } else {
            if (tob.has_FrameHeight) {
                return false;
            }
        }
        if (has_FrameLocationX) {
            if (!tob.has_FrameLocationX) {
                return false;
            }
            if (_FrameLocationX!= tob._FrameLocationX) {
                return false;
            }
        } else {
            if (tob.has_FrameLocationX) {
                return false;
            }
        }
        if (has_FrameLocationY) {
            if (!tob.has_FrameLocationY) {
                return false;
            }
            if (_FrameLocationY!= tob._FrameLocationY) {
                return false;
            }
        } else {
            if (tob.has_FrameLocationY) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((31 *h)+(_FrameMaximized? 137 : 139));
        h = ((31 *h)+ _FrameWidth);
        h = ((31 *h)+ _FrameHeight);
        h = ((31 *h)+ _FrameLocationX);
        h = ((31 *h)+ _FrameLocationY);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<userFrameDescriptor");
        if (has_FrameMaximized) {
            sb.append(" frameMaximized=");
            sb.append(printBoolean(_FrameMaximized));
        }
        if (has_FrameWidth) {
            sb.append(" frameWidth=");
            sb.append(Integer.toString(_FrameWidth));
        }
        if (has_FrameHeight) {
            sb.append(" frameHeight=");
            sb.append(Integer.toString(_FrameHeight));
        }
        if (has_FrameLocationX) {
            sb.append(" frameLocationX=");
            sb.append(Integer.toString(_FrameLocationX));
        }
        if (has_FrameLocationY) {
            sb.append(" frameLocationY=");
            sb.append(Integer.toString(_FrameLocationY));
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return UserColorDescriptor.newDispatcher();
    }

}
