package freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding;

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
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class LearningUnitViewManagersDescriptor extends MarshallableRootElement implements RootElement {
    private List _LearningUnitViewManagersDescriptors =
        PredicatedLists.createInvalidating(this, new LearningUnitViewManagersDescriptorsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_LearningUnitViewManagersDescriptors =
        new LearningUnitViewManagersDescriptorsPredicate();

    public List getLearningUnitViewManagersDescriptors() {
        return _LearningUnitViewManagersDescriptors;
    }

    public void deleteLearningUnitViewManagersDescriptors() {
        _LearningUnitViewManagersDescriptors = null;
        invalidate();
    }

    public void emptyLearningUnitViewManagersDescriptors() {
        _LearningUnitViewManagersDescriptors = PredicatedLists.createInvalidating(this, pred_LearningUnitViewManagersDescriptors,
            new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
    }

    public void validate(Validator v) throws StructureValidationException {
        for (Iterator i = _LearningUnitViewManagersDescriptors.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject)i.next()));
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("learningUnitViewManagersDescriptor");
        if (_LearningUnitViewManagersDescriptors.size() > 0) {
            for (Iterator i = _LearningUnitViewManagersDescriptors.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject)i.next()));
            }
        }
        w.end("learningUnitViewManagersDescriptor");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("learningUnitViewManagersDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_LearningUnitViewManagersDescriptors, new ArrayList());
            while (xs.atStart("learningUnitViewManagerDescriptor")) {
                l.add(((LearningUnitViewManagerDescriptor)u.unmarshal()));
            }
            _LearningUnitViewManagersDescriptors = PredicatedLists.createInvalidating(this,
                pred_LearningUnitViewManagersDescriptors, l);
        }
        xs.takeEnd("learningUnitViewManagersDescriptor");
    }

    public static LearningUnitViewManagersDescriptor unmarshal(InputStream in) throws UnmarshalException {
        return unmarshal(XMLScanner.open(in));
    }

    public static LearningUnitViewManagersDescriptor unmarshal(XMLScanner xs) throws UnmarshalException {
        return unmarshal(xs, newDispatcher());
    }

    public static LearningUnitViewManagersDescriptor unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return ((LearningUnitViewManagersDescriptor)d.unmarshal(xs, (LearningUnitViewManagersDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof LearningUnitViewManagersDescriptor)) {
            return false;
        }
        LearningUnitViewManagersDescriptor tob = ((LearningUnitViewManagersDescriptor)ob);
        if (_LearningUnitViewManagersDescriptors != null) {
            if (tob._LearningUnitViewManagersDescriptors == null) {
                return false;
            }
            if (!_LearningUnitViewManagersDescriptors.equals(tob._LearningUnitViewManagersDescriptors)) {
                return false;
            }
        }
        else {
            if (tob._LearningUnitViewManagersDescriptors != null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 * h) + ((_LearningUnitViewManagersDescriptors != null) ?
            _LearningUnitViewManagersDescriptors.hashCode() : 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<learningUnitViewManagersDescriptor");
        if (_LearningUnitViewManagersDescriptors != null) {
            sb.append(" learningUnitViewManagerDescriptor=");
            sb.append(_LearningUnitViewManagersDescriptors.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return LearningUnitViewManagerDescriptor.newDispatcher();
    }

    private static class LearningUnitViewManagersDescriptorsPredicate implements PredicatedLists.Predicate {
        public void check(Object ob) {
            if (!(ob instanceof LearningUnitViewManagerDescriptor)) {
                throw new InvalidContentObjectException(ob, (LearningUnitViewManagerDescriptor.class));
            }
        }
    }
}
