package org.openuss.framework.jsfcontrols.tags;

import com.sun.facelets.tag.AbstractTagLibrary;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.openuss.framework.jsfcontrols.converter.TimestampConverter;


/**
 * OpenUSS JSF Core Facelets Library
 * 
 * @author Ingo Düppe
 * @author Rick Hightower
 */
public final class JsfCoreLibrary extends AbstractTagLibrary {
    /** Namespace used to import this library in Facelets pages  */
    public static final String NAMESPACE = "http://www.openuss.org/jsf-core";

    /**  Current instance of library. */
    public static final JsfCoreLibrary INSTANCE = new JsfCoreLibrary();

    /**
     * Creates a new JstlCoreLibrary object.
     *
     */
    public JsfCoreLibrary() {
        super(NAMESPACE);

        this.addTagHandler("setValueTypeBinding", SetValueTypeBindingHandler.class);
        this.addTagHandler("setValueBinding", SetValueBindingHandler.class);
        this.addTagHandler("strAttribute", StringAttributeHandler.class);
        this.addTagHandler("isBoolean", IsBooleanHandler.class);
        this.addTagHandler("isText", IsTextHandler.class);
        this.addTagHandler("isDate", IsDateHandler.class);
        this.addTagHandler("hasGlobalMessages", HasGlobalMessagesHandler.class);
        this.addTagHandler("setEntityBinding", SetEntityBindingHandler.class);
        this.addConverter("convertTimestamp", TimestampConverter.CONVERTER_ID, ConvertTimestampHandler.class);

        try {
            Method[] methods = JsfFunctions.class.getMethods();

            for (int i = 0; i < methods.length; i++) {
                if (Modifier.isStatic(methods[i].getModifiers())) {
                    this.addFunction(methods[i].getName(), methods[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
