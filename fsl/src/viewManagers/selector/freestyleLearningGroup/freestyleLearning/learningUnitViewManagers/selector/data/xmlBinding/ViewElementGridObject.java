
package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding;

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


public class ViewElementGridObject
    extends MarshallableObject
    implements Element
{

    private boolean _ClickAllowed;
    private boolean has_ClickAllowed;
    private String _Id;
    private String _Text;
    private String _ImageFileName;
    private String _Type;
    private String _BackgroundColor;

    public boolean getClickAllowed() {
        if (has_ClickAllowed) {
            return _ClickAllowed;
        }
        throw new NoValueException("clickAllowed");
    }

    public void setClickAllowed(boolean _ClickAllowed) {
        this._ClickAllowed = _ClickAllowed;
        has_ClickAllowed = true;
        invalidate();
    }

    public boolean hasClickAllowed() {
        return has_ClickAllowed;
    }

    public void deleteClickAllowed() {
        has_ClickAllowed = false;
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

    public String getText() {
        return _Text;
    }

    public void setText(String _Text) {
        this._Text = _Text;
        if (_Text == null) {
            invalidate();
        }
    }

    public String getImageFileName() {
        return _ImageFileName;
    }

    public void setImageFileName(String _ImageFileName) {
        this._ImageFileName = _ImageFileName;
        if (_ImageFileName == null) {
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

    public String getBackgroundColor() {
        return _BackgroundColor;
    }

    public void setBackgroundColor(String _BackgroundColor) {
        this._BackgroundColor = _BackgroundColor;
        if (_BackgroundColor == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (!has_ClickAllowed) {
            throw new MissingAttributeException("clickAllowed");
        }
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_Type == null) {
            throw new MissingAttributeException("type");
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
        w.start("viewElementGridObject");
        w.attribute("clickAllowed", printBoolean(getClickAllowed()));
        w.attribute("id", _Id.toString());
        if (_Text!= null) {
            w.attribute("text", _Text.toString());
        }
        if (_ImageFileName!= null) {
            w.attribute("imageFileName", _ImageFileName.toString());
        }
        w.attribute("type", _Type.toString());
        if (_BackgroundColor!= null) {
            w.attribute("backgroundColor", _BackgroundColor.toString());
        }
        w.end("viewElementGridObject");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("viewElementGridObject");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("clickAllowed")) {
                if (has_ClickAllowed) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _ClickAllowed = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_ClickAllowed = true;
                continue;
            }
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("text")) {
                if (_Text!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Text = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("imageFileName")) {
                if (_ImageFileName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ImageFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("type")) {
                if (_Type!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Type = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("backgroundColor")) {
                if (_BackgroundColor!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _BackgroundColor = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        xs.takeEnd("viewElementGridObject");
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

    public static ViewElementGridObject unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static ViewElementGridObject unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static ViewElementGridObject unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((ViewElementGridObject) d.unmarshal(xs, (ViewElementGridObject.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof ViewElementGridObject)) {
            return false;
        }
        ViewElementGridObject tob = ((ViewElementGridObject) ob);
        if (has_ClickAllowed) {
            if (!tob.has_ClickAllowed) {
                return false;
            }
            if (_ClickAllowed!= tob._ClickAllowed) {
                return false;
            }
        } else {
            if (tob.has_ClickAllowed) {
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
        if (_Text!= null) {
            if (tob._Text == null) {
                return false;
            }
            if (!_Text.equals(tob._Text)) {
                return false;
            }
        } else {
            if (tob._Text!= null) {
                return false;
            }
        }
        if (_ImageFileName!= null) {
            if (tob._ImageFileName == null) {
                return false;
            }
            if (!_ImageFileName.equals(tob._ImageFileName)) {
                return false;
            }
        } else {
            if (tob._ImageFileName!= null) {
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
        if (_BackgroundColor!= null) {
            if (tob._BackgroundColor == null) {
                return false;
            }
            if (!_BackgroundColor.equals(tob._BackgroundColor)) {
                return false;
            }
        } else {
            if (tob._BackgroundColor!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((31 *h)+(_ClickAllowed? 137 : 139));
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_Text!= null)?_Text.hashCode(): 0));
        h = ((127 *h)+((_ImageFileName!= null)?_ImageFileName.hashCode(): 0));
        h = ((127 *h)+((_Type!= null)?_Type.hashCode(): 0));
        h = ((127 *h)+((_BackgroundColor!= null)?_BackgroundColor.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<viewElementGridObject");
        if (has_ClickAllowed) {
            sb.append(" clickAllowed=");
            sb.append(printBoolean(_ClickAllowed));
        }
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_Text!= null) {
            sb.append(" text=");
            sb.append(_Text.toString());
        }
        if (_ImageFileName!= null) {
            sb.append(" imageFileName=");
            sb.append(_ImageFileName.toString());
        }
        if (_Type!= null) {
            sb.append(" type=");
            sb.append(_Type.toString());
        }
        if (_BackgroundColor!= null) {
            sb.append(" backgroundColor=");
            sb.append(_BackgroundColor.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return SelectorDescriptor.newDispatcher();
    }

}
