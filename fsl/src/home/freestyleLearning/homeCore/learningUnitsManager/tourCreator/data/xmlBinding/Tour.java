
package freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding;

import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding.TourElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.IdentifiableElement;
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


public class Tour
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private String _Id;
    private String _TourName;
    private String _TourIcon;
    private List _TourElements = PredicatedLists.createInvalidating(this, new TourElementsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_TourElements = new TourElementsPredicate();

    public String getId() {
        return _Id;
    }

    public void setId(String _Id) {
        this._Id = _Id;
        if (_Id == null) {
            invalidate();
        }
    }

    public String id() {
        return _Id.toString();
    }

    public String getTourName() {
        return _TourName;
    }

    public void setTourName(String _TourName) {
        this._TourName = _TourName;
        if (_TourName == null) {
            invalidate();
        }
    }

    public String getTourIcon() {
        return _TourIcon;
    }

    public void setTourIcon(String _TourIcon) {
        this._TourIcon = _TourIcon;
        if (_TourIcon == null) {
            invalidate();
        }
    }

    public List getTourElements() {
        return _TourElements;
    }

    public void deleteTourElements() {
        _TourElements = null;
        invalidate();
    }

    public void emptyTourElements() {
        _TourElements = PredicatedLists.createInvalidating(this, pred_TourElements, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_TourName == null) {
            throw new MissingAttributeException("tourName");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _TourElements.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("tour");
        w.attribute("id", _Id.toString());
        w.attribute("tourName", _TourName.toString());
        if (_TourIcon!= null) {
            w.attribute("tourIcon", _TourIcon.toString());
        }
        if (_TourElements.size()> 0) {
            for (Iterator i = _TourElements.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("tour");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("tour");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("tourName")) {
                if (_TourName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _TourName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("tourIcon")) {
                if (_TourIcon!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _TourIcon = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_TourElements, new ArrayList());
            while (xs.atStart("tourElement")) {
                l.add(((TourElement) u.unmarshal()));
            }
            _TourElements = PredicatedLists.createInvalidating(this, pred_TourElements, l);
        }
        xs.takeEnd("tour");
    }

    public static Tour unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static Tour unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static Tour unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((Tour) d.unmarshal(xs, (Tour.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Tour)) {
            return false;
        }
        Tour tob = ((Tour) ob);
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
        if (_TourName!= null) {
            if (tob._TourName == null) {
                return false;
            }
            if (!_TourName.equals(tob._TourName)) {
                return false;
            }
        } else {
            if (tob._TourName!= null) {
                return false;
            }
        }
        if (_TourIcon!= null) {
            if (tob._TourIcon == null) {
                return false;
            }
            if (!_TourIcon.equals(tob._TourIcon)) {
                return false;
            }
        } else {
            if (tob._TourIcon!= null) {
                return false;
            }
        }
        if (_TourElements!= null) {
            if (tob._TourElements == null) {
                return false;
            }
            if (!_TourElements.equals(tob._TourElements)) {
                return false;
            }
        } else {
            if (tob._TourElements!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_TourName!= null)?_TourName.hashCode(): 0));
        h = ((127 *h)+((_TourIcon!= null)?_TourIcon.hashCode(): 0));
        h = ((127 *h)+((_TourElements!= null)?_TourElements.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<tour");
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_TourName!= null) {
            sb.append(" tourName=");
            sb.append(_TourName.toString());
        }
        if (_TourIcon!= null) {
            sb.append(" tourIcon=");
            sb.append(_TourIcon.toString());
        }
        if (_TourElements!= null) {
            sb.append(" tourElement=");
            sb.append(_TourElements.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("tour", (Tour.class));
        d.register("tourDescriptor", (TourDescriptor.class));
        d.register("tourElement", (TourElement.class));
        d.register("tourElementLink", (TourElementLink.class));
        d.register("tourElementLinkTarget", (TourElementLinkTarget.class));
        d.freezeElementNameMap();
        return d;
    }


    private static class TourElementsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof TourElement)) {
                throw new InvalidContentObjectException(ob, (TourElement.class));
            }
        }

    }

}
