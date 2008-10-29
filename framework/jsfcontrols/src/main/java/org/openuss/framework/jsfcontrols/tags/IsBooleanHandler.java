package org.openuss.framework.jsfcontrols.tags;

import com.sun.facelets.tag.TagConfig;

/**
 * Is the current field a boolean.
 */
public final class IsBooleanHandler extends IsTypeHandler {

    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsBooleanHandler(final TagConfig config) {
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
        /* If the type is a boolean, process the body of the tag.
         */
        if (type == Boolean.class) {
            return true;
        } else if (type.isPrimitive() && "boolean".equals(type.getName())) {
            return true;
        }

        return false;
    }
}
