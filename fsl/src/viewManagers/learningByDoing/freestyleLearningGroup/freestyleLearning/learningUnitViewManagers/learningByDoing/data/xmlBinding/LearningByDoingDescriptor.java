
package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.data.xmlBinding;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.data.xmlBinding.ViewElement;
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


public class LearningByDoingDescriptor
    extends MarshallableRootElement
    implements RootElement
{

    private List _LearningUnitViewElements = PredicatedLists.createInvalidating(this, new LearningUnitViewElementsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_LearningUnitViewElements = new LearningUnitViewElementsPredicate();

    public List getLearningUnitViewElements() {
        return _LearningUnitViewElements;
    }

    public void deleteLearningUnitViewElements() {
        _LearningUnitViewElements = null;
        invalidate();
    }

    public void emptyLearningUnitViewElements() {
        _LearningUnitViewElements = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElements, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _LearningUnitViewElements.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("learningByDoingDescriptor");
        if (_LearningUnitViewElements.size()> 0) {
            for (Iterator i = _LearningUnitViewElements.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("learningByDoingDescriptor");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("learningByDoingDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_LearningUnitViewElements, new ArrayList());
            while (xs.atStart("viewElement")) {
                l.add(((ViewElement) u.unmarshal()));
            }
            _LearningUnitViewElements = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElements, l);
        }
        xs.takeEnd("learningByDoingDescriptor");
    }

    public static LearningByDoingDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static LearningByDoingDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static LearningByDoingDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((LearningByDoingDescriptor) d.unmarshal(xs, (LearningByDoingDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof LearningByDoingDescriptor)) {
            return false;
        }
        LearningByDoingDescriptor tob = ((LearningByDoingDescriptor) ob);
        if (_LearningUnitViewElements!= null) {
            if (tob._LearningUnitViewElements == null) {
                return false;
            }
            if (!_LearningUnitViewElements.equals(tob._LearningUnitViewElements)) {
                return false;
            }
        } else {
            if (tob._LearningUnitViewElements!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_LearningUnitViewElements!= null)?_LearningUnitViewElements.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<learningByDoingDescriptor");
        if (_LearningUnitViewElements!= null) {
            sb.append(" viewElement=");
            sb.append(_LearningUnitViewElements.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("learningByDoingDescriptor", (LearningByDoingDescriptor.class));
        d.register("viewElement", (ViewElement.class));
        d.register("viewElementLink", (ViewElementLink.class));
        d.register("viewElementLinkTarget", (ViewElementLinkTarget.class));
        d.freezeElementNameMap();
        return d;
    }


    private static class LearningUnitViewElementsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof ViewElement)) {
                throw new InvalidContentObjectException(ob, (ViewElement.class));
            }
        }

    }

}
