package org.openuss.framework.jsfcontrols.tags;

import java.util.TimeZone;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import org.openuss.framework.jsfcontrols.converter.TimestampConverter;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.jsf.ComponentSupport;
import com.sun.facelets.tag.jsf.ConvertHandler;
import com.sun.facelets.tag.jsf.ConverterConfig;

/**
 * Register a TimestampConverter instance on the UIComponent associated with the
 * closest parent UIComponent custom action. <p/> See <a target="_new"
 * href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/tlddocs/f/convertDateTime.html">tag
 * documentation</a>.
 * 
 * @author Jacob Hookom
 * @version $Id: ConvertTimestampHandler.java,v 1.1 2006/11/12 22:06:26 idueppe Exp $
 */
public class ConvertTimestampHandler extends ConvertHandler {
    private final TagAttribute dateStyle;

    private final TagAttribute locale;

    private final TagAttribute pattern;

    private final TagAttribute timeStyle;

    private final TagAttribute timeZone;

    private final TagAttribute type;

    /**
     * @param config
     */
    public ConvertTimestampHandler(ConverterConfig config) {
        super(config);
        this.dateStyle = this.getAttribute("dateStyle");
        this.locale = this.getAttribute("locale");
        this.pattern = this.getAttribute("pattern");
        this.timeStyle = this.getAttribute("timeStyle");
        this.timeZone = this.getAttribute("timeZone");
        this.type = this.getAttribute("type");
    }

    /**
     * Returns a new TimestampConverter
     * 
     * @see DateTimeConverter
     * @see com.sun.facelets.tag.jsf.ConvertHandler#createConverter(com.sun.facelets.FaceletContext)
     */
    protected Converter createConverter(FaceletContext ctx)
            throws FacesException, ELException, FaceletException {
        Converter converter = ctx.getFacesContext().getApplication().createConverter(TimestampConverter.CONVERTER_ID);
        return converter;

    }

    /**
     * Implements tag spec, see taglib documentation.
     * 
     * @see com.sun.facelets.tag.ObjectHandler#setAttributes(com.sun.facelets.FaceletContext,
     *      java.lang.Object)
     */
    protected void setAttributes(FaceletContext ctx, Object obj) {
        DateTimeConverter c = (DateTimeConverter) obj;
        if (this.locale != null) {
            c.setLocale(ComponentSupport.getLocale(ctx, this.locale));
        }
        if (this.pattern != null) {
            c.setPattern(this.pattern.getValue(ctx));
        } else {
            if (this.type != null) {
                c.setType(this.type.getValue(ctx));
            }
            if (this.dateStyle != null) {
                c.setDateStyle(this.dateStyle.getValue(ctx));
            }
            if (this.timeStyle != null) {
                c.setTimeStyle(this.timeStyle.getValue(ctx));
            }
        }
        
        if (this.timeZone != null) {
            Object t = this.timeZone.getObject(ctx);
            if (t instanceof TimeZone) {
                c.setTimeZone((TimeZone) t);
            } else if (t instanceof String) {
                TimeZone tz = TimeZone.getTimeZone((String) t);
                c.setTimeZone(tz);
            } else {
                throw new TagAttributeException(
                        this.tag,
                        this.timeZone,
                        "Illegal TimeZone, must evaluate to either a java.util.TimeZone or String, is type: "
                                + t);
            }
        }
    }

    @SuppressWarnings("unchecked")
	protected MetaRuleset createMetaRuleset(Class type) {
        return super.createMetaRuleset(type).ignoreAll();
    }
}
