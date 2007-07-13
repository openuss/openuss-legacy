package org.openuss.framework.jsfcontrols.tags;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;


/**
 * Is the current field a boolean.
 */
public abstract class IsTypeHandler extends TagHandler {
    /**   */
    private final TagAttribute id;

    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsTypeHandler(final TagConfig config) {
        super(config);
        this.id = this.getRequiredAttribute("id");
    }

    /**
     * Is the current field a boolean.
     *
     * @param faceletsContext ctx
     * @param aParent parent
     *
     * @throws IOException IOException
     */
    public void apply(final FaceletContext faceletsContext, final UIComponent aParent)
        throws IOException {
        /* Get the name of the value binding. */
        String tid = this.id.getValue(faceletsContext);
        ValueExpression ve = faceletsContext.getVariableMapper().resolveVariable(tid+"Type");
        if (ve != null) {
	        Class<?> type = (Class<?>) faceletsContext.getVariableMapper().resolveVariable(tid + "Type")
	                                            .getValue(faceletsContext);
	
	        /* If the type is a boolean, process the body of the tag. */
	        if (isType(type)) {
	            this.nextHandler.apply(faceletsContext, aParent);
	        }
        }
    }

    /**
     *
     *
     * @param type type
     *
     * @return true if this is the correct type.
     */
    protected abstract boolean isType(Class<?> type);
}
