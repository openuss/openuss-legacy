package org.openuss.framework.jsfcontrols.tags;



import java.util.Date;

import com.sun.facelets.tag.TagConfig;

/**
 * Is the current field a date.
 */
public final class IsDateHandler extends IsTypeHandler {

    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsDateHandler(final TagConfig config) {
        super(config);
    }

    /**
     * @param type type
     *
     * @return true if this is a boolean.
     */
    @Override
	protected boolean isType(final Class<?> type) {
        /* If the type is a string, process the body of the tag.
         */
    	if (Date.class.isAssignableFrom(type)) {
            return true;
        }
        return false;
    }
}
