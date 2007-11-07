
package freestyleLearning.homeCore.programConfigurationManager.xmlDocumentClasses;

import freestyleLearning.homeCore.programConfigurationManager.xmlDocumentClasses.Paths;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class ProgramConfiguration
    extends MarshallableRootElement
    implements RootElement
{

    private Paths _Paths;

    public Paths getPaths() {
        return _Paths;
    }

    public void setPaths(Paths _Paths) {
        this._Paths = _Paths;
        if (_Paths == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Paths == null) {
            throw new MissingContentException("paths");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        v.validate(_Paths);
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("programConfiguration");
        m.marshal(_Paths);
        w.end("programConfiguration");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("programConfiguration");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        _Paths = ((Paths) u.unmarshal());
        xs.takeEnd("programConfiguration");
    }

    public static ProgramConfiguration unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static ProgramConfiguration unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static ProgramConfiguration unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((ProgramConfiguration) d.unmarshal(xs, (ProgramConfiguration.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof ProgramConfiguration)) {
            return false;
        }
        ProgramConfiguration tob = ((ProgramConfiguration) ob);
        if (_Paths!= null) {
            if (tob._Paths == null) {
                return false;
            }
            if (!_Paths.equals(tob._Paths)) {
                return false;
            }
        } else {
            if (tob._Paths!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Paths!= null)?_Paths.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<programConfiguration");
        if (_Paths!= null) {
            sb.append(" paths=");
            sb.append(_Paths.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Paths.newDispatcher();
    }

}
