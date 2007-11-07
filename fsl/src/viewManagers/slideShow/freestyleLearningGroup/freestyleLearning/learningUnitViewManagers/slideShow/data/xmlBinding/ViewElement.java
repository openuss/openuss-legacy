
package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.slideShow.data.xmlBinding;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.slideShow.data.xmlBinding.ViewElementLink;
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


public class ViewElement
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private String _Id;
    private String _ParentId;
    private String _Title;
    private String _Type;
    private String _LastModificationDate;
    private String _ImageFileName;
    private String _AudioFileName;
    private String _BackgroundColor;
    private List _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, new LearningUnitViewElementLinksPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_LearningUnitViewElementLinks = new LearningUnitViewElementLinksPredicate();
    private boolean _Folder;
    private boolean has_Folder;
    private boolean _WaitForAudioEnd;
    private boolean has_WaitForAudioEnd;
    private int _DelayTime;
    private boolean has_DelayTime;
    private int _Volume;
    private boolean has_Volume;
    private boolean _RepeatAudio;
    private boolean has_RepeatAudio;
    private boolean _DoAntialiazing;
    private boolean has_DoAntialiazing;

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

    public String getParentId() {
        return _ParentId;
    }

    public void setParentId(String _ParentId) {
        this._ParentId = _ParentId;
        if (_ParentId == null) {
            invalidate();
        }
    }

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String _Title) {
        this._Title = _Title;
        if (_Title == null) {
            invalidate();
        }
    }

    public String getType() {
        return _Type;
    }

    public void setType(String _Type) {
        this._Type = _Type;
        if (_Type == null) {
            invalidate();
        }
    }

    public String getLastModificationDate() {
        return _LastModificationDate;
    }

    public void setLastModificationDate(String _LastModificationDate) {
        this._LastModificationDate = _LastModificationDate;
        if (_LastModificationDate == null) {
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

    public String getAudioFileName() {
        return _AudioFileName;
    }

    public void setAudioFileName(String _AudioFileName) {
        this._AudioFileName = _AudioFileName;
        if (_AudioFileName == null) {
            invalidate();
        }
    }

    public String getBackgroundColor() {
        return _BackgroundColor;
    }

    public void setBackgroundColor(String _BackgroundColor) {
        this._BackgroundColor = _BackgroundColor;
        if (_BackgroundColor == null) {
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

    public boolean getFolder() {
        if (has_Folder) {
            return _Folder;
        }
        throw new NoValueException("folder");
    }

    public void setFolder(boolean _Folder) {
        this._Folder = _Folder;
        has_Folder = true;
        invalidate();
    }

    public boolean hasFolder() {
        return has_Folder;
    }

    public void deleteFolder() {
        has_Folder = false;
        invalidate();
    }

    public boolean getWaitForAudioEnd() {
        if (has_WaitForAudioEnd) {
            return _WaitForAudioEnd;
        }
        throw new NoValueException("waitForAudioEnd");
    }

    public void setWaitForAudioEnd(boolean _WaitForAudioEnd) {
        this._WaitForAudioEnd = _WaitForAudioEnd;
        has_WaitForAudioEnd = true;
        invalidate();
    }

    public boolean hasWaitForAudioEnd() {
        return has_WaitForAudioEnd;
    }

    public void deleteWaitForAudioEnd() {
        has_WaitForAudioEnd = false;
        invalidate();
    }

    public int getDelayTime() {
        if (has_DelayTime) {
            return _DelayTime;
        }
        throw new NoValueException("delayTime");
    }

    public void setDelayTime(int _DelayTime) {
        this._DelayTime = _DelayTime;
        has_DelayTime = true;
        invalidate();
    }

    public boolean hasDelayTime() {
        return has_DelayTime;
    }

    public void deleteDelayTime() {
        has_DelayTime = false;
        invalidate();
    }

    public int getVolume() {
        if (has_Volume) {
            return _Volume;
        }
        throw new NoValueException("volume");
    }

    public void setVolume(int _Volume) {
        this._Volume = _Volume;
        has_Volume = true;
        invalidate();
    }

    public boolean hasVolume() {
        return has_Volume;
    }

    public void deleteVolume() {
        has_Volume = false;
        invalidate();
    }

    public boolean getRepeatAudio() {
        if (has_RepeatAudio) {
            return _RepeatAudio;
        }
        throw new NoValueException("repeatAudio");
    }

    public void setRepeatAudio(boolean _RepeatAudio) {
        this._RepeatAudio = _RepeatAudio;
        has_RepeatAudio = true;
        invalidate();
    }

    public boolean hasRepeatAudio() {
        return has_RepeatAudio;
    }

    public void deleteRepeatAudio() {
        has_RepeatAudio = false;
        invalidate();
    }

    public boolean getDoAntialiazing() {
        if (has_DoAntialiazing) {
            return _DoAntialiazing;
        }
        throw new NoValueException("doAntialiazing");
    }

    public void setDoAntialiazing(boolean _DoAntialiazing) {
        this._DoAntialiazing = _DoAntialiazing;
        has_DoAntialiazing = true;
        invalidate();
    }

    public boolean hasDoAntialiazing() {
        return has_DoAntialiazing;
    }

    public void deleteDoAntialiazing() {
        has_DoAntialiazing = false;
        invalidate();
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_ParentId == null) {
            throw new MissingAttributeException("parentId");
        }
        if (_Title == null) {
            throw new MissingAttributeException("title");
        }
        if (_Type == null) {
            throw new MissingAttributeException("type");
        }
        if (!has_Folder) {
            throw new MissingAttributeException("folder");
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
        w.start("viewElement");
        w.attribute("id", _Id.toString());
        w.attribute("parentId", _ParentId.toString());
        w.attribute("title", _Title.toString());
        w.attribute("type", _Type.toString());
        if (_LastModificationDate!= null) {
            w.attribute("lastModificationDate", _LastModificationDate.toString());
        }
        if (_ImageFileName!= null) {
            w.attribute("imageFileName", _ImageFileName.toString());
        }
        if (_AudioFileName!= null) {
            w.attribute("audioFileName", _AudioFileName.toString());
        }
        if (_BackgroundColor!= null) {
            w.attribute("backgroundColor", _BackgroundColor.toString());
        }
        w.attribute("folder", printBoolean(getFolder()));
        if (has_WaitForAudioEnd) {
            w.attribute("waitForAudioEnd", printBoolean(getWaitForAudioEnd()));
        }
        if (has_DelayTime) {
            w.attribute("delayTime", Integer.toString(getDelayTime()));
        }
        if (has_Volume) {
            w.attribute("volume", Integer.toString(getVolume()));
        }
        if (has_RepeatAudio) {
            w.attribute("repeatAudio", printBoolean(getRepeatAudio()));
        }
        if (has_DoAntialiazing) {
            w.attribute("doAntialiazing", printBoolean(getDoAntialiazing()));
        }
        if (_LearningUnitViewElementLinks.size()> 0) {
            for (Iterator i = _LearningUnitViewElementLinks.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("viewElement");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("viewElement");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("parentId")) {
                if (_ParentId!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ParentId = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("title")) {
                if (_Title!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Title = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("type")) {
                if (_Type!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Type = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("lastModificationDate")) {
                if (_LastModificationDate!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _LastModificationDate = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("imageFileName")) {
                if (_ImageFileName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _ImageFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("audioFileName")) {
                if (_AudioFileName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _AudioFileName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("backgroundColor")) {
                if (_BackgroundColor!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _BackgroundColor = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("folder")) {
                if (has_Folder) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _Folder = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_Folder = true;
                continue;
            }
            if (an.equals("waitForAudioEnd")) {
                if (has_WaitForAudioEnd) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _WaitForAudioEnd = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_WaitForAudioEnd = true;
                continue;
            }
            if (an.equals("delayTime")) {
                if (has_DelayTime) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _DelayTime = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_DelayTime = true;
                continue;
            }
            if (an.equals("volume")) {
                if (has_Volume) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _Volume = Integer.parseInt(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_Volume = true;
                continue;
            }
            if (an.equals("repeatAudio")) {
                if (has_RepeatAudio) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _RepeatAudio = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_RepeatAudio = true;
                continue;
            }
            if (an.equals("doAntialiazing")) {
                if (has_DoAntialiazing) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _DoAntialiazing = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_DoAntialiazing = true;
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_LearningUnitViewElementLinks, new ArrayList());
            while (xs.atStart("viewElementLink")) {
                l.add(((ViewElementLink) u.unmarshal()));
            }
            _LearningUnitViewElementLinks = PredicatedLists.createInvalidating(this, pred_LearningUnitViewElementLinks, l);
        }
        xs.takeEnd("viewElement");
    }

    private static boolean readBoolean(String s)
        throws ConversionException
    {
        if (s.equals("true")) {
            return true;
        }
        if (s.equals("false")) {
            return false;
        }
        throw new ConversionException(s);
    }

    public static ViewElement unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static ViewElement unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static ViewElement unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((ViewElement) d.unmarshal(xs, (ViewElement.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof ViewElement)) {
            return false;
        }
        ViewElement tob = ((ViewElement) ob);
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
        if (_ParentId!= null) {
            if (tob._ParentId == null) {
                return false;
            }
            if (!_ParentId.equals(tob._ParentId)) {
                return false;
            }
        } else {
            if (tob._ParentId!= null) {
                return false;
            }
        }
        if (_Title!= null) {
            if (tob._Title == null) {
                return false;
            }
            if (!_Title.equals(tob._Title)) {
                return false;
            }
        } else {
            if (tob._Title!= null) {
                return false;
            }
        }
        if (_Type!= null) {
            if (tob._Type == null) {
                return false;
            }
            if (!_Type.equals(tob._Type)) {
                return false;
            }
        } else {
            if (tob._Type!= null) {
                return false;
            }
        }
        if (_LastModificationDate!= null) {
            if (tob._LastModificationDate == null) {
                return false;
            }
            if (!_LastModificationDate.equals(tob._LastModificationDate)) {
                return false;
            }
        } else {
            if (tob._LastModificationDate!= null) {
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
        if (_AudioFileName!= null) {
            if (tob._AudioFileName == null) {
                return false;
            }
            if (!_AudioFileName.equals(tob._AudioFileName)) {
                return false;
            }
        } else {
            if (tob._AudioFileName!= null) {
                return false;
            }
        }
        if (_BackgroundColor!= null) {
            if (tob._BackgroundColor == null) {
                return false;
            }
            if (!_BackgroundColor.equals(tob._BackgroundColor)) {
                return false;
            }
        } else {
            if (tob._BackgroundColor!= null) {
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
        if (has_Folder) {
            if (!tob.has_Folder) {
                return false;
            }
            if (_Folder!= tob._Folder) {
                return false;
            }
        } else {
            if (tob.has_Folder) {
                return false;
            }
        }
        if (has_WaitForAudioEnd) {
            if (!tob.has_WaitForAudioEnd) {
                return false;
            }
            if (_WaitForAudioEnd!= tob._WaitForAudioEnd) {
                return false;
            }
        } else {
            if (tob.has_WaitForAudioEnd) {
                return false;
            }
        }
        if (has_DelayTime) {
            if (!tob.has_DelayTime) {
                return false;
            }
            if (_DelayTime!= tob._DelayTime) {
                return false;
            }
        } else {
            if (tob.has_DelayTime) {
                return false;
            }
        }
        if (has_Volume) {
            if (!tob.has_Volume) {
                return false;
            }
            if (_Volume!= tob._Volume) {
                return false;
            }
        } else {
            if (tob.has_Volume) {
                return false;
            }
        }
        if (has_RepeatAudio) {
            if (!tob.has_RepeatAudio) {
                return false;
            }
            if (_RepeatAudio!= tob._RepeatAudio) {
                return false;
            }
        } else {
            if (tob.has_RepeatAudio) {
                return false;
            }
        }
        if (has_DoAntialiazing) {
            if (!tob.has_DoAntialiazing) {
                return false;
            }
            if (_DoAntialiazing!= tob._DoAntialiazing) {
                return false;
            }
        } else {
            if (tob.has_DoAntialiazing) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_ParentId!= null)?_ParentId.hashCode(): 0));
        h = ((127 *h)+((_Title!= null)?_Title.hashCode(): 0));
        h = ((127 *h)+((_Type!= null)?_Type.hashCode(): 0));
        h = ((127 *h)+((_LastModificationDate!= null)?_LastModificationDate.hashCode(): 0));
        h = ((127 *h)+((_ImageFileName!= null)?_ImageFileName.hashCode(): 0));
        h = ((127 *h)+((_AudioFileName!= null)?_AudioFileName.hashCode(): 0));
        h = ((127 *h)+((_BackgroundColor!= null)?_BackgroundColor.hashCode(): 0));
        h = ((127 *h)+((_LearningUnitViewElementLinks!= null)?_LearningUnitViewElementLinks.hashCode(): 0));
        h = ((31 *h)+(_Folder? 137 : 139));
        h = ((31 *h)+(_WaitForAudioEnd? 137 : 139));
        h = ((31 *h)+ _DelayTime);
        h = ((31 *h)+ _Volume);
        h = ((31 *h)+(_RepeatAudio? 137 : 139));
        h = ((31 *h)+(_DoAntialiazing? 137 : 139));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<viewElement");
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_ParentId!= null) {
            sb.append(" parentId=");
            sb.append(_ParentId.toString());
        }
        if (_Title!= null) {
            sb.append(" title=");
            sb.append(_Title.toString());
        }
        if (_Type!= null) {
            sb.append(" type=");
            sb.append(_Type.toString());
        }
        if (_LastModificationDate!= null) {
            sb.append(" lastModificationDate=");
            sb.append(_LastModificationDate.toString());
        }
        if (_ImageFileName!= null) {
            sb.append(" imageFileName=");
            sb.append(_ImageFileName.toString());
        }
        if (_AudioFileName!= null) {
            sb.append(" audioFileName=");
            sb.append(_AudioFileName.toString());
        }
        if (_BackgroundColor!= null) {
            sb.append(" backgroundColor=");
            sb.append(_BackgroundColor.toString());
        }
        if (_LearningUnitViewElementLinks!= null) {
            sb.append(" viewElementLink=");
            sb.append(_LearningUnitViewElementLinks.toString());
        }
        if (has_Folder) {
            sb.append(" folder=");
            sb.append(printBoolean(_Folder));
        }
        if (has_WaitForAudioEnd) {
            sb.append(" waitForAudioEnd=");
            sb.append(printBoolean(_WaitForAudioEnd));
        }
        if (has_DelayTime) {
            sb.append(" delayTime=");
            sb.append(Integer.toString(_DelayTime));
        }
        if (has_Volume) {
            sb.append(" volume=");
            sb.append(Integer.toString(_Volume));
        }
        if (has_RepeatAudio) {
            sb.append(" repeatAudio=");
            sb.append(printBoolean(_RepeatAudio));
        }
        if (has_DoAntialiazing) {
            sb.append(" doAntialiazing=");
            sb.append(printBoolean(_DoAntialiazing));
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return SlideShowDescriptor.newDispatcher();
    }


    private static class LearningUnitViewElementLinksPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof ViewElementLink)) {
                throw new InvalidContentObjectException(ob, (ViewElementLink.class));
            }
        }

    }

}
