
package freestyleLearning.homeCore.welcomeScreen.data.xmlBinding;

import freestyleLearning.homeCore.welcomeScreen.data.xmlBinding.WelcomeElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class WelcomeScreenDescriptor
    extends MarshallableRootElement
    implements RootElement
{

    private List _WelcomeElements = PredicatedLists.createInvalidating(this, new WelcomeElementsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_WelcomeElements = new WelcomeElementsPredicate();

    public List getWelcomeElements() {
        return _WelcomeElements;
    }

    public void deleteWelcomeElements() {
        _WelcomeElements = null;
        invalidate();
    }

    public void emptyWelcomeElements() {
        _WelcomeElements = PredicatedLists.createInvalidating(this, pred_WelcomeElements, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _WelcomeElements.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("welcomeScreenDescriptor");
        if (_WelcomeElements.size()> 0) {
            for (Iterator i = _WelcomeElements.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("welcomeScreenDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("welcomeScreenDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_WelcomeElements, new ArrayList());
            while (xs.atStart("welcomeElement")) {
                l.add(((WelcomeElement) u.unmarshal()));
            }
            _WelcomeElements = PredicatedLists.createInvalidating(this, pred_WelcomeElements, l);
        }
        xs.takeEnd("welcomeScreenDescriptor");
    }

    public static WelcomeScreenDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static WelcomeScreenDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static WelcomeScreenDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((WelcomeScreenDescriptor) d.unmarshal(xs, (WelcomeScreenDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof WelcomeScreenDescriptor)) {
            return false;
        }
        WelcomeScreenDescriptor tob = ((WelcomeScreenDescriptor) ob);
        if (_WelcomeElements!= null) {
            if (tob._WelcomeElements == null) {
                return false;
            }
            if (!_WelcomeElements.equals(tob._WelcomeElements)) {
                return false;
            }
        } else {
            if (tob._WelcomeElements!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_WelcomeElements!= null)?_WelcomeElements.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<welcomeScreenDescriptor");
        if (_WelcomeElements!= null) {
            sb.append(" welcomeElement=");
            sb.append(_WelcomeElements.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return WelcomeElement.newDispatcher();
    }


    private static class WelcomeElementsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof WelcomeElement)) {
                throw new InvalidContentObjectException(ob, (WelcomeElement.class));
            }
        }

    }

}
