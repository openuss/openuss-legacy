
package freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding;

import freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding.LearningUnitDescriptor;
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


public class LearningUnitsDescriptor
    extends MarshallableRootElement
    implements RootElement
{

    private List _LearningUnitsDescriptors = PredicatedLists.createInvalidating(this, new LearningUnitsDescriptorsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_LearningUnitsDescriptors = new LearningUnitsDescriptorsPredicate();

    public List getLearningUnitsDescriptors() {
        return _LearningUnitsDescriptors;
    }

    public void deleteLearningUnitsDescriptors() {
        _LearningUnitsDescriptors = null;
        invalidate();
    }

    public void emptyLearningUnitsDescriptors() {
        _LearningUnitsDescriptors = PredicatedLists.createInvalidating(this, pred_LearningUnitsDescriptors, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _LearningUnitsDescriptors.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("learningUnitsDescriptor");
        if (_LearningUnitsDescriptors.size()> 0) {
            for (Iterator i = _LearningUnitsDescriptors.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("learningUnitsDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("learningUnitsDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_LearningUnitsDescriptors, new ArrayList());
            while (xs.atStart("learningUnitDescriptor")) {
                l.add(((LearningUnitDescriptor) u.unmarshal()));
            }
            _LearningUnitsDescriptors = PredicatedLists.createInvalidating(this, pred_LearningUnitsDescriptors, l);
        }
        xs.takeEnd("learningUnitsDescriptor");
    }

    public static LearningUnitsDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static LearningUnitsDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static LearningUnitsDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((LearningUnitsDescriptor) d.unmarshal(xs, (LearningUnitsDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof LearningUnitsDescriptor)) {
            return false;
        }
        LearningUnitsDescriptor tob = ((LearningUnitsDescriptor) ob);
        if (_LearningUnitsDescriptors!= null) {
            if (tob._LearningUnitsDescriptors == null) {
                return false;
            }
            if (!_LearningUnitsDescriptors.equals(tob._LearningUnitsDescriptors)) {
                return false;
            }
        } else {
            if (tob._LearningUnitsDescriptors!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_LearningUnitsDescriptors!= null)?_LearningUnitsDescriptors.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<learningUnitsDescriptor");
        if (_LearningUnitsDescriptors!= null) {
            sb.append(" learningUnitDescriptor=");
            sb.append(_LearningUnitsDescriptors.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return LearningUnitDescriptor.newDispatcher();
    }


    private static class LearningUnitsDescriptorsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof LearningUnitDescriptor)) {
                throw new InvalidContentObjectException(ob, (LearningUnitDescriptor.class));
            }
        }

    }

}
