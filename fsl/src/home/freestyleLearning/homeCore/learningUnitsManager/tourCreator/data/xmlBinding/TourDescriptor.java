
package freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding;

import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding.Tour;
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


public class TourDescriptor
    extends MarshallableRootElement
    implements RootElement
{

    private List _Tours = PredicatedLists.createInvalidating(this, new ToursPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Tours = new ToursPredicate();

    public List getTours() {
        return _Tours;
    }

    public void deleteTours() {
        _Tours = null;
        invalidate();
    }

    public void emptyTours() {
        _Tours = PredicatedLists.createInvalidating(this, pred_Tours, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _Tours.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("tourDescriptor");
        if (_Tours.size()> 0) {
            for (Iterator i = _Tours.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("tourDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("tourDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_Tours, new ArrayList());
            while (xs.atStart("tour")) {
                l.add(((Tour) u.unmarshal()));
            }
            _Tours = PredicatedLists.createInvalidating(this, pred_Tours, l);
        }
        xs.takeEnd("tourDescriptor");
    }

    public static TourDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static TourDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static TourDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((TourDescriptor) d.unmarshal(xs, (TourDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof TourDescriptor)) {
            return false;
        }
        TourDescriptor tob = ((TourDescriptor) ob);
        if (_Tours!= null) {
            if (tob._Tours == null) {
                return false;
            }
            if (!_Tours.equals(tob._Tours)) {
                return false;
            }
        } else {
            if (tob._Tours!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Tours!= null)?_Tours.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<tourDescriptor");
        if (_Tours!= null) {
            sb.append(" tour=");
            sb.append(_Tours.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Tour.newDispatcher();
    }


    private static class ToursPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof Tour)) {
                throw new InvalidContentObjectException(ob, (Tour.class));
            }
        }

    }

}
