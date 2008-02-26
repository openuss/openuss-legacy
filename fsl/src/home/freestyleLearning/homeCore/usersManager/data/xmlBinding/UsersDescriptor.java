
package freestyleLearning.homeCore.usersManager.data.xmlBinding;

import freestyleLearning.homeCore.usersManager.data.xmlBinding.UserDescriptor;
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


public class UsersDescriptor
    extends MarshallableRootElement
    implements RootElement
{

    private List _UsersDescriptors = PredicatedLists.createInvalidating(this, new UsersDescriptorsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_UsersDescriptors = new UsersDescriptorsPredicate();

    public List getUsersDescriptors() {
        return _UsersDescriptors;
    }

    public void deleteUsersDescriptors() {
        _UsersDescriptors = null;
        invalidate();
    }

    public void emptyUsersDescriptors() {
        _UsersDescriptors = PredicatedLists.createInvalidating(this, pred_UsersDescriptors, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _UsersDescriptors.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("usersDescriptor");
        if (_UsersDescriptors.size()> 0) {
            for (Iterator i = _UsersDescriptors.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("usersDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("usersDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_UsersDescriptors, new ArrayList());
            while (xs.atStart("userDescriptor")) {
                l.add(((UserDescriptor) u.unmarshal()));
            }
            _UsersDescriptors = PredicatedLists.createInvalidating(this, pred_UsersDescriptors, l);
        }
        xs.takeEnd("usersDescriptor");
    }

    public static UsersDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static UsersDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static UsersDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((UsersDescriptor) d.unmarshal(xs, (UsersDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof UsersDescriptor)) {
            return false;
        }
        UsersDescriptor tob = ((UsersDescriptor) ob);
        if (_UsersDescriptors!= null) {
            if (tob._UsersDescriptors == null) {
                return false;
            }
            if (!_UsersDescriptors.equals(tob._UsersDescriptors)) {
                return false;
            }
        } else {
            if (tob._UsersDescriptors!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_UsersDescriptors!= null)?_UsersDescriptors.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<usersDescriptor");
        if (_UsersDescriptors!= null) {
            sb.append(" userDescriptor=");
            sb.append(_UsersDescriptors.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return UserColorDescriptor.newDispatcher();
    }


    private static class UsersDescriptorsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof UserDescriptor)) {
                throw new InvalidContentObjectException(ob, (UserDescriptor.class));
            }
        }

    }

}
