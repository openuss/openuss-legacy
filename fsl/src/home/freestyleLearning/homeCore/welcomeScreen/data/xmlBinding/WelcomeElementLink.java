
package freestyleLearning.homeCore.welcomeScreen.data.xmlBinding;

import freestyleLearning.homeCore.welcomeScreen.data.xmlBinding.WelcomeElementLinkTarget;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class WelcomeElementLink
    extends MarshallableObject
    implements Element
{

    private String _Id;
    private List _WelcomeElementLinkTargets = PredicatedLists.createInvalidating(this, new WelcomeElementLinkTargetsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_WelcomeElementLinkTargets = new WelcomeElementLinkTargetsPredicate();

    public String getId() {
        return _Id;
    }

    public void setId(String _Id) {
        this._Id = _Id;
        if (_Id == null) {
            invalidate();
        }
    }

    public List getWelcomeElementLinkTargets() {
        return _WelcomeElementLinkTargets;
    }

    public void deleteWelcomeElementLinkTargets() {
        _WelcomeElementLinkTargets = null;
        invalidate();
    }

    public void emptyWelcomeElementLinkTargets() {
        _WelcomeElementLinkTargets = PredicatedLists.createInvalidating(this, pred_WelcomeElementLinkTargets, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _WelcomeElementLinkTargets.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("welcomeElementLink");
        w.attribute("id", _Id.toString());
        if (_WelcomeElementLinkTargets.size()> 0) {
            for (Iterator i = _WelcomeElementLinkTargets.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("welcomeElementLink");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("welcomeElementLink");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_WelcomeElementLinkTargets, new ArrayList());
            while (xs.atStart("welcomeElementLinkTarget")) {
                l.add(((WelcomeElementLinkTarget) u.unmarshal()));
            }
            _WelcomeElementLinkTargets = PredicatedLists.createInvalidating(this, pred_WelcomeElementLinkTargets, l);
        }
        xs.takeEnd("welcomeElementLink");
    }

    public static WelcomeElementLink unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static WelcomeElementLink unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static WelcomeElementLink unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((WelcomeElementLink) d.unmarshal(xs, (WelcomeElementLink.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof WelcomeElementLink)) {
            return false;
        }
        WelcomeElementLink tob = ((WelcomeElementLink) ob);
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
        if (_WelcomeElementLinkTargets!= null) {
            if (tob._WelcomeElementLinkTargets == null) {
                return false;
            }
            if (!_WelcomeElementLinkTargets.equals(tob._WelcomeElementLinkTargets)) {
                return false;
            }
        } else {
            if (tob._WelcomeElementLinkTargets!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_WelcomeElementLinkTargets!= null)?_WelcomeElementLinkTargets.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<welcomeElementLink");
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_WelcomeElementLinkTargets!= null) {
            sb.append(" welcomeElementLinkTarget=");
            sb.append(_WelcomeElementLinkTargets.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return WelcomeElement.newDispatcher();
    }


    private static class WelcomeElementLinkTargetsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof WelcomeElementLinkTarget)) {
                throw new InvalidContentObjectException(ob, (WelcomeElementLinkTarget.class));
            }
        }

    }

}
