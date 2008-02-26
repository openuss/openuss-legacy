
package freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding;

import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding.TourElementLink;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ConversionException;
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
import javax.xml.bind.NoValueException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.PredicatedLists.Predicate;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class TourElement
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private String _Id;
    private String _ElementName;
    private String _Description;
    private String _ImageFileName;
    private List _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, new LearningUnitViewElementLinksPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_LearningUnitViewElementLinks = new LearningUnitViewElementLinksPredicate();
    private int _DisplayTime;
    private boolean has_DisplayTime;

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

    public String getElementName() {
        return _ElementName;
    }

    public void setElementName(String _ElementName) {
        this._ElementName = _ElementName;
        if (_ElementName == null) {
            invalidate();
        }
    }

    public String getDescription() {
        return _Description;
    }

    public void setDescription(String _Description) {
        this._Description = _Description;
        if (_Description == null) {
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

    public List getLearningUnitViewElementLinks() {
        return _LearningUnitViewElementLinks;
    }

    public void deleteLearningUnitViewElementLinks() {
        _LearningUnitViewElementLinks = null;
        invalidate();
    }

    public void emptyLearningUnitViewElementLinks() {
        _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElementLinks, new ArrayList());
    }

    public int getDisplayTime() {
        if (has_DisplayTime) {
            return _DisplayTime;
        }
        throw new NoValueException("displayTime");
    }

    public void setDisplayTime(int _DisplayTime) {
        this._DisplayTime = _DisplayTime;
        has_DisplayTime = true;
        invalidate();
    }

    public boolean hasDisplayTime() {
        return has_DisplayTime;
    }

    public void deleteDisplayTime() {
        has_DisplayTime = false;
        invalidate();
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_ElementName == null) {
            throw new MissingAttributeException("elementName");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _LearningUnitViewElementLinks.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("tourElement");
        w.attribute("id", _Id.toString());
        w.attribute("elementName", _ElementName.toString());
        if (_Description!= null) {
            w.attribute("description", _Description.toString());
        }
        if (_ImageFileName!= null) {
            w.attribute("imageFileName", _ImageFileName.toString());
        }
        if (has_DisplayTime) {
            w.attribute("displayTime", Integer.toString(getDisplayTime()));
        }
        if (_LearningUnitViewElementLinks.size()> 0) {
            for (Iterator i = _LearningUnitViewElementLinks.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("tourElement");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("tourElement");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("elementName")) {
                if (_ElementName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ElementName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("description")) {
                if (_Description!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Description = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("imageFileName")) {
                if (_ImageFileName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ImageFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("displayTime")) {
                if (has_DisplayTime) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _DisplayTime = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_DisplayTime = true;
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_LearningUnitViewElementLinks, new ArrayList());
            while (xs.atStart("tourElementLink")) {
                l.add(((TourElementLink) u.unmarshal()));
            }
            _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElementLinks, l);
        }
        xs.takeEnd("tourElement");
    }

    public static TourElement unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static TourElement unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static TourElement unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((TourElement) d.unmarshal(xs, (TourElement.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof TourElement)) {
            return false;
        }
        TourElement tob = ((TourElement) ob);
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
        if (_ElementName!= null) {
            if (tob._ElementName == null) {
                return false;
            }
            if (!_ElementName.equals(tob._ElementName)) {
                return false;
            }
        } else {
            if (tob._ElementName!= null) {
                return false;
            }
        }
        if (_Description!= null) {
            if (tob._Description == null) {
                return false;
            }
            if (!_Description.equals(tob._Description)) {
                return false;
            }
        } else {
            if (tob._Description!= null) {
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
        if (_LearningUnitViewElementLinks!= null) {
            if (tob._LearningUnitViewElementLinks == null) {
                return false;
            }
            if (!_LearningUnitViewElementLinks.equals(tob._LearningUnitViewElementLinks)) {
                return false;
            }
        } else {
            if (tob._LearningUnitViewElementLinks!= null) {
                return false;
            }
        }
        if (has_DisplayTime) {
            if (!tob.has_DisplayTime) {
                return false;
            }
            if (_DisplayTime!= tob._DisplayTime) {
                return false;
            }
        } else {
            if (tob.has_DisplayTime) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_ElementName!= null)?_ElementName.hashCode(): 0));
        h = ((127 *h)+((_Description!= null)?_Description.hashCode(): 0));
        h = ((127 *h)+((_ImageFileName!= null)?_ImageFileName.hashCode(): 0));
        h = ((127 *h)+((_LearningUnitViewElementLinks!= null)?_LearningUnitViewElementLinks.hashCode(): 0));
        h = ((31 *h)+ _DisplayTime);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<tourElement");
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_ElementName!= null) {
            sb.append(" elementName=");
            sb.append(_ElementName.toString());
        }
        if (_Description!= null) {
            sb.append(" description=");
            sb.append(_Description.toString());
        }
        if (_ImageFileName!= null) {
            sb.append(" imageFileName=");
            sb.append(_ImageFileName.toString());
        }
        if (_LearningUnitViewElementLinks!= null) {
            sb.append(" tourElementLink=");
            sb.append(_LearningUnitViewElementLinks.toString());
        }
        if (has_DisplayTime) {
            sb.append(" displayTime=");
            sb.append(Integer.toString(_DisplayTime));
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Tour.newDispatcher();
    }


    private static class LearningUnitViewElementLinksPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof TourElementLink)) {
                throw new InvalidContentObjectException(ob, (TourElementLink.class));
            }
        }

    }

}
