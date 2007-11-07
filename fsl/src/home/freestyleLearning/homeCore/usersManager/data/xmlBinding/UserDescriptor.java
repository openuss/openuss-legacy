
package freestyleLearning.homeCore.usersManager.data.xmlBinding;

import freestyleLearning.homeCore.usersManager.data.xmlBinding.UserColorDescriptor;
import freestyleLearning.homeCore.usersManager.data.xmlBinding.UserFontDescriptor;
import freestyleLearning.homeCore.usersManager.data.xmlBinding.UserFrameDescriptor;
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


public class UserDescriptor
    extends MarshallableObject
    implements Element, IdentifiableElement
{

    private String _Id;
    private String _UserName;
    private String _UserPassword;
    private String _CurrentUserRole;
    private List _AllowedUserRoles = null;
    private PredicatedLists.Predicate pred_AllowedUserRoles = new AllowedUserRolesPredicate();
    private String _LearningUnitsUserDataDirectoryName;
    private List _UserColors = PredicatedLists.createInvalidating(this, new UserColorsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_UserColors = new UserColorsPredicate();
    private List _UserFonts = PredicatedLists.createInvalidating(this, new UserFontsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_UserFonts = new UserFontsPredicate();
    private List _UserFrameOptions = PredicatedLists.createInvalidating(this, new UserFrameOptionsPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_UserFrameOptions = new UserFrameOptionsPredicate();
    private boolean _AutomaticSelectionEnabled;
    private boolean has_AutomaticSelectionEnabled;
    private boolean _PlayWelcomeVideosEnabled;
    private boolean has_PlayWelcomeVideosEnabled;
    private boolean _DisplayWelcomeScreen;
    private boolean has_DisplayWelcomeScreen;
    private boolean _RememberFrameStatusEnabled;
    private boolean has_RememberFrameStatusEnabled;

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

    public String getUserName() {
        return _UserName;
    }

    public void setUserName(String _UserName) {
        this._UserName = _UserName;
        if (_UserName == null) {
            invalidate();
        }
    }

    public String getUserPassword() {
        return _UserPassword;
    }

    public void setUserPassword(String _UserPassword) {
        this._UserPassword = _UserPassword;
        if (_UserPassword == null) {
            invalidate();
        }
    }

    public String getCurrentUserRole() {
        return _CurrentUserRole;
    }

    public void setCurrentUserRole(String _CurrentUserRole) {
        this._CurrentUserRole = _CurrentUserRole;
        if (_CurrentUserRole == null) {
            invalidate();
        }
    }

    public List getAllowedUserRoles() {
        return _AllowedUserRoles;
    }

    public void deleteAllowedUserRoles() {
        _AllowedUserRoles = null;
        invalidate();
    }

    public void emptyAllowedUserRoles() {
        _AllowedUserRoles = PredicatedLists.createInvalidating(this, pred_AllowedUserRoles, new ArrayList());
    }

    public String getLearningUnitsUserDataDirectoryName() {
        return _LearningUnitsUserDataDirectoryName;
    }

    public void setLearningUnitsUserDataDirectoryName(String _LearningUnitsUserDataDirectoryName) {
        this._LearningUnitsUserDataDirectoryName = _LearningUnitsUserDataDirectoryName;
        if (_LearningUnitsUserDataDirectoryName == null) {
            invalidate();
        }
    }

    public List getUserColors() {
        return _UserColors;
    }

    public void deleteUserColors() {
        _UserColors = null;
        invalidate();
    }

    public void emptyUserColors() {
        _UserColors = PredicatedLists.createInvalidating(this, pred_UserColors, new ArrayList());
    }

    public List getUserFonts() {
        return _UserFonts;
    }

    public void deleteUserFonts() {
        _UserFonts = null;
        invalidate();
    }

    public void emptyUserFonts() {
        _UserFonts = PredicatedLists.createInvalidating(this, pred_UserFonts, new ArrayList());
    }

    public List getUserFrameOptions() {
        return _UserFrameOptions;
    }

    public void deleteUserFrameOptions() {
        _UserFrameOptions = null;
        invalidate();
    }

    public void emptyUserFrameOptions() {
        _UserFrameOptions = PredicatedLists.createInvalidating(this, pred_UserFrameOptions, new ArrayList());
    }

    public boolean getAutomaticSelectionEnabled() {
        if (has_AutomaticSelectionEnabled) {
            return _AutomaticSelectionEnabled;
        }
        throw new NoValueException("automaticSelectionEnabled");
    }

    public void setAutomaticSelectionEnabled(boolean _AutomaticSelectionEnabled) {
        this._AutomaticSelectionEnabled = _AutomaticSelectionEnabled;
        has_AutomaticSelectionEnabled = true;
        invalidate();
    }

    public boolean hasAutomaticSelectionEnabled() {
        return has_AutomaticSelectionEnabled;
    }

    public void deleteAutomaticSelectionEnabled() {
        has_AutomaticSelectionEnabled = false;
        invalidate();
    }

    public boolean getPlayWelcomeVideosEnabled() {
        if (has_PlayWelcomeVideosEnabled) {
            return _PlayWelcomeVideosEnabled;
        }
        throw new NoValueException("playWelcomeVideosEnabled");
    }

    public void setPlayWelcomeVideosEnabled(boolean _PlayWelcomeVideosEnabled) {
        this._PlayWelcomeVideosEnabled = _PlayWelcomeVideosEnabled;
        has_PlayWelcomeVideosEnabled = true;
        invalidate();
    }

    public boolean hasPlayWelcomeVideosEnabled() {
        return has_PlayWelcomeVideosEnabled;
    }

    public void deletePlayWelcomeVideosEnabled() {
        has_PlayWelcomeVideosEnabled = false;
        invalidate();
    }

    public boolean getDisplayWelcomeScreen() {
        if (has_DisplayWelcomeScreen) {
            return _DisplayWelcomeScreen;
        }
        throw new NoValueException("displayWelcomeScreen");
    }

    public void setDisplayWelcomeScreen(boolean _DisplayWelcomeScreen) {
        this._DisplayWelcomeScreen = _DisplayWelcomeScreen;
        has_DisplayWelcomeScreen = true;
        invalidate();
    }

    public boolean hasDisplayWelcomeScreen() {
        return has_DisplayWelcomeScreen;
    }

    public void deleteDisplayWelcomeScreen() {
        has_DisplayWelcomeScreen = false;
        invalidate();
    }

    public boolean getRememberFrameStatusEnabled() {
        if (has_RememberFrameStatusEnabled) {
            return _RememberFrameStatusEnabled;
        }
        throw new NoValueException("rememberFrameStatusEnabled");
    }

    public void setRememberFrameStatusEnabled(boolean _RememberFrameStatusEnabled) {
        this._RememberFrameStatusEnabled = _RememberFrameStatusEnabled;
        has_RememberFrameStatusEnabled = true;
        invalidate();
    }

    public boolean hasRememberFrameStatusEnabled() {
        return has_RememberFrameStatusEnabled;
    }

    public void deleteRememberFrameStatusEnabled() {
        has_RememberFrameStatusEnabled = false;
        invalidate();
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Id == null) {
            throw new MissingAttributeException("id");
        }
        if (_UserName == null) {
            throw new MissingAttributeException("userName");
        }
        if (_UserPassword == null) {
            throw new MissingAttributeException("userPassword");
        }
        if (_CurrentUserRole == null) {
            throw new MissingAttributeException("currentUserRole");
        }
        if (_AllowedUserRoles == null) {
            throw new MissingAttributeException("allowedUserRoles");
        }
        if (_LearningUnitsUserDataDirectoryName == null) {
            throw new MissingAttributeException("learningUnitsUserDataDirectoryName");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _UserColors.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
        for (Iterator i = _UserFonts.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
        for (Iterator i = _UserFrameOptions.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("userDescriptor");
        w.attribute("id", _Id.toString());
        w.attribute("userName", _UserName.toString());
        w.attribute("userPassword", _UserPassword.toString());
        w.attribute("currentUserRole", _CurrentUserRole.toString());
        w.attributeName("allowedUserRoles");
        for (Iterator i = _AllowedUserRoles.iterator(); i.hasNext(); ) {
            w.attributeValueToken(((String) i.next()).toString());
        }
        w.attribute("learningUnitsUserDataDirectoryName", _LearningUnitsUserDataDirectoryName.toString());
        if (has_AutomaticSelectionEnabled) {
            w.attribute("automaticSelectionEnabled", printBoolean(getAutomaticSelectionEnabled()));
        }
        if (has_PlayWelcomeVideosEnabled) {
            w.attribute("playWelcomeVideosEnabled", printBoolean(getPlayWelcomeVideosEnabled()));
        }
        if (has_DisplayWelcomeScreen) {
            w.attribute("displayWelcomeScreen", printBoolean(getDisplayWelcomeScreen()));
        }
        if (has_RememberFrameStatusEnabled) {
            w.attribute("rememberFrameStatusEnabled", printBoolean(getRememberFrameStatusEnabled()));
        }
        if (_UserColors.size()> 0) {
            for (Iterator i = _UserColors.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        if (_UserFonts.size()> 0) {
            for (Iterator i = _UserFonts.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        if (_UserFrameOptions.size()> 0) {
            for (Iterator i = _UserFrameOptions.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("userDescriptor");
    }

    private static String printBoolean(boolean f) {
        return (f?"true":"false");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("userDescriptor");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("id")) {
                if (_Id!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Id = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("userName")) {
                if (_UserName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _UserName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("userPassword")) {
                if (_UserPassword!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _UserPassword = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("currentUserRole")) {
                if (_CurrentUserRole!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _CurrentUserRole = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("allowedUserRoles")) {
                if (_AllowedUserRoles!= null) {
                    throw new DuplicateAttributeException(an);
                }
                ArrayList l = new ArrayList();
                xs.tokenizeAttributeValue();
                while (xs.atAttributeValueToken()) {
                    l.add(String.valueOf(xs.takeAttributeValueToken()));
                }
                _AllowedUserRoles = PredicatedLists.createInvalidating(this, pred_AllowedUserRoles, l);
                continue;
            }
            if (an.equals("learningUnitsUserDataDirectoryName")) {
                if (_LearningUnitsUserDataDirectoryName!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _LearningUnitsUserDataDirectoryName = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("automaticSelectionEnabled")) {
                if (has_AutomaticSelectionEnabled) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _AutomaticSelectionEnabled = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_AutomaticSelectionEnabled = true;
                continue;
            }
            if (an.equals("playWelcomeVideosEnabled")) {
                if (has_PlayWelcomeVideosEnabled) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _PlayWelcomeVideosEnabled = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_PlayWelcomeVideosEnabled = true;
                continue;
            }
            if (an.equals("displayWelcomeScreen")) {
                if (has_DisplayWelcomeScreen) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _DisplayWelcomeScreen = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_DisplayWelcomeScreen = true;
                continue;
            }
            if (an.equals("rememberFrameStatusEnabled")) {
                if (has_RememberFrameStatusEnabled) {
                    throw new DuplicateAttributeException(an);
                }
                try {
                    _RememberFrameStatusEnabled = readBoolean(xs.takeAttributeValue());
                } catch (Exception x) {
                    throw new ConversionException(an, x);
                }
                has_RememberFrameStatusEnabled = true;
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_UserColors, new ArrayList());
            while (xs.atStart("userColorDescriptor")) {
                l.add(((UserColorDescriptor) u.unmarshal()));
            }
            _UserColors = PredicatedLists.createInvalidating(this, pred_UserColors, l);
        }
        {
            List l = PredicatedLists.create(this, pred_UserFonts, new ArrayList());
            while (xs.atStart("userFontDescriptor")) {
                l.add(((UserFontDescriptor) u.unmarshal()));
            }
            _UserFonts = PredicatedLists.createInvalidating(this, pred_UserFonts, l);
        }
        {
            List l = PredicatedLists.create(this, pred_UserFrameOptions, new ArrayList());
            while (xs.atStart("userFrameDescriptor")) {
                l.add(((UserFrameDescriptor) u.unmarshal()));
            }
            _UserFrameOptions = PredicatedLists.createInvalidating(this, pred_UserFrameOptions, l);
        }
        xs.takeEnd("userDescriptor");
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

    public static UserDescriptor unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static UserDescriptor unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static UserDescriptor unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((UserDescriptor) d.unmarshal(xs, (UserDescriptor.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof UserDescriptor)) {
            return false;
        }
        UserDescriptor tob = ((UserDescriptor) ob);
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
        if (_UserName!= null) {
            if (tob._UserName == null) {
                return false;
            }
            if (!_UserName.equals(tob._UserName)) {
                return false;
            }
        } else {
            if (tob._UserName!= null) {
                return false;
            }
        }
        if (_UserPassword!= null) {
            if (tob._UserPassword == null) {
                return false;
            }
            if (!_UserPassword.equals(tob._UserPassword)) {
                return false;
            }
        } else {
            if (tob._UserPassword!= null) {
                return false;
            }
        }
        if (_CurrentUserRole!= null) {
            if (tob._CurrentUserRole == null) {
                return false;
            }
            if (!_CurrentUserRole.equals(tob._CurrentUserRole)) {
                return false;
            }
        } else {
            if (tob._CurrentUserRole!= null) {
                return false;
            }
        }
        if (_AllowedUserRoles!= null) {
            if (tob._AllowedUserRoles == null) {
                return false;
            }
            if (!_AllowedUserRoles.equals(tob._AllowedUserRoles)) {
                return false;
            }
        } else {
            if (tob._AllowedUserRoles!= null) {
                return false;
            }
        }
        if (_LearningUnitsUserDataDirectoryName!= null) {
            if (tob._LearningUnitsUserDataDirectoryName == null) {
                return false;
            }
            if (!_LearningUnitsUserDataDirectoryName.equals(tob._LearningUnitsUserDataDirectoryName)) {
                return false;
            }
        } else {
            if (tob._LearningUnitsUserDataDirectoryName!= null) {
                return false;
            }
        }
        if (_UserColors!= null) {
            if (tob._UserColors == null) {
                return false;
            }
            if (!_UserColors.equals(tob._UserColors)) {
                return false;
            }
        } else {
            if (tob._UserColors!= null) {
                return false;
            }
        }
        if (_UserFonts!= null) {
            if (tob._UserFonts == null) {
                return false;
            }
            if (!_UserFonts.equals(tob._UserFonts)) {
                return false;
            }
        } else {
            if (tob._UserFonts!= null) {
                return false;
            }
        }
        if (_UserFrameOptions!= null) {
            if (tob._UserFrameOptions == null) {
                return false;
            }
            if (!_UserFrameOptions.equals(tob._UserFrameOptions)) {
                return false;
            }
        } else {
            if (tob._UserFrameOptions!= null) {
                return false;
            }
        }
        if (has_AutomaticSelectionEnabled) {
            if (!tob.has_AutomaticSelectionEnabled) {
                return false;
            }
            if (_AutomaticSelectionEnabled!= tob._AutomaticSelectionEnabled) {
                return false;
            }
        } else {
            if (tob.has_AutomaticSelectionEnabled) {
                return false;
            }
        }
        if (has_PlayWelcomeVideosEnabled) {
            if (!tob.has_PlayWelcomeVideosEnabled) {
                return false;
            }
            if (_PlayWelcomeVideosEnabled!= tob._PlayWelcomeVideosEnabled) {
                return false;
            }
        } else {
            if (tob.has_PlayWelcomeVideosEnabled) {
                return false;
            }
        }
        if (has_DisplayWelcomeScreen) {
            if (!tob.has_DisplayWelcomeScreen) {
                return false;
            }
            if (_DisplayWelcomeScreen!= tob._DisplayWelcomeScreen) {
                return false;
            }
        } else {
            if (tob.has_DisplayWelcomeScreen) {
                return false;
            }
        }
        if (has_RememberFrameStatusEnabled) {
            if (!tob.has_RememberFrameStatusEnabled) {
                return false;
            }
            if (_RememberFrameStatusEnabled!= tob._RememberFrameStatusEnabled) {
                return false;
            }
        } else {
            if (tob.has_RememberFrameStatusEnabled) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Id!= null)?_Id.hashCode(): 0));
        h = ((127 *h)+((_UserName!= null)?_UserName.hashCode(): 0));
        h = ((127 *h)+((_UserPassword!= null)?_UserPassword.hashCode(): 0));
        h = ((127 *h)+((_CurrentUserRole!= null)?_CurrentUserRole.hashCode(): 0));
        h = ((127 *h)+((_AllowedUserRoles!= null)?_AllowedUserRoles.hashCode(): 0));
        h = ((127 *h)+((_LearningUnitsUserDataDirectoryName!= null)?_LearningUnitsUserDataDirectoryName.hashCode(): 0));
        h = ((127 *h)+((_UserColors!= null)?_UserColors.hashCode(): 0));
        h = ((127 *h)+((_UserFonts!= null)?_UserFonts.hashCode(): 0));
        h = ((127 *h)+((_UserFrameOptions!= null)?_UserFrameOptions.hashCode(): 0));
        h = ((31 *h)+(_AutomaticSelectionEnabled? 137 : 139));
        h = ((31 *h)+(_PlayWelcomeVideosEnabled? 137 : 139));
        h = ((31 *h)+(_DisplayWelcomeScreen? 137 : 139));
        h = ((31 *h)+(_RememberFrameStatusEnabled? 137 : 139));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<userDescriptor");
        if (_Id!= null) {
            sb.append(" id=");
            sb.append(_Id.toString());
        }
        if (_UserName!= null) {
            sb.append(" userName=");
            sb.append(_UserName.toString());
        }
        if (_UserPassword!= null) {
            sb.append(" userPassword=");
            sb.append(_UserPassword.toString());
        }
        if (_CurrentUserRole!= null) {
            sb.append(" currentUserRole=");
            sb.append(_CurrentUserRole.toString());
        }
        if (_AllowedUserRoles!= null) {
            sb.append(" allowedUserRoles=");
            sb.append(_AllowedUserRoles.toString());
        }
        if (_LearningUnitsUserDataDirectoryName!= null) {
            sb.append(" learningUnitsUserDataDirectoryName=");
            sb.append(_LearningUnitsUserDataDirectoryName.toString());
        }
        if (_UserColors!= null) {
            sb.append(" userColorDescriptor=");
            sb.append(_UserColors.toString());
        }
        if (_UserFonts!= null) {
            sb.append(" userFontDescriptor=");
            sb.append(_UserFonts.toString());
        }
        if (_UserFrameOptions!= null) {
            sb.append(" userFrameDescriptor=");
            sb.append(_UserFrameOptions.toString());
        }
        if (has_AutomaticSelectionEnabled) {
            sb.append(" automaticSelectionEnabled=");
            sb.append(printBoolean(_AutomaticSelectionEnabled));
        }
        if (has_PlayWelcomeVideosEnabled) {
            sb.append(" playWelcomeVideosEnabled=");
            sb.append(printBoolean(_PlayWelcomeVideosEnabled));
        }
        if (has_DisplayWelcomeScreen) {
            sb.append(" displayWelcomeScreen=");
            sb.append(printBoolean(_DisplayWelcomeScreen));
        }
        if (has_RememberFrameStatusEnabled) {
            sb.append(" rememberFrameStatusEnabled=");
            sb.append(printBoolean(_RememberFrameStatusEnabled));
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return UserColorDescriptor.newDispatcher();
    }


    private static class AllowedUserRolesPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof String)) {
                throw new InvalidContentObjectException(ob, (String.class));
            }
        }

    }


    private static class UserColorsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof UserColorDescriptor)) {
                throw new InvalidContentObjectException(ob, (UserColorDescriptor.class));
            }
        }

    }


    private static class UserFontsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof UserFontDescriptor)) {
                throw new InvalidContentObjectException(ob, (UserFontDescriptor.class));
            }
        }

    }


    private static class UserFrameOptionsPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof UserFrameDescriptor)) {
                throw new InvalidContentObjectException(ob, (UserFrameDescriptor.class));
            }
        }

    }

}
