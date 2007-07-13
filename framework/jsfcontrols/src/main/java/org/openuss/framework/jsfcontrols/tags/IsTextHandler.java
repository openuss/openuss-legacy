package org.openuss.framework.jsfcontrols.tags;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.sun.facelets.tag.TagConfig;

/**
 * Is the current field a string.
 */
public final class IsTextHandler extends IsTypeHandler {

    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsTextHandler(final TagConfig config) {
        super(config);
    }

    /**
     *
     *
     * @param type type
     *
     * @return true if this is a boolean.
     */
    @Override
	protected boolean isType(final Class<?> type) {
        /* If the type is a string, process the body of the tag.
         */
        if (type == String.class
        		|| type == Integer.class
        		|| type == BigDecimal.class
        		|| type == BigInteger.class
        		|| type == Character.class
        		|| type == Long.class
        		|| type == Short.class
        		|| type == Byte.class
        		|| type == Float.class
        		|| type == Double.class
        		|| (type.isPrimitive() && !type.getName().equals("boolean"))
        		) {
            return true;
        } else {
        	return false;
        }
    }
}
