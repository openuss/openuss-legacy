package org.openuss.framework.jsfcontrols.tags;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.TagValueExpression;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;


/**
 * Maps a bean and a property to a value binding.
 * 
 * @author Ingo Dueppe
 */
public final class SetValueBindingHandler extends TagHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SetValueBindingHandler.class);

    /**   The name of the new variable that this tag defines. */
    private final TagAttribute var;

    /**   The actual bean binding expression. */
    private final TagAttribute bean;

    /**   The actual bean binding expression. */
    private final TagAttribute property;
    
    /**
     * Constructor. Set up the attributes for this tag.
     *
     * @param config TagConfig
     */
    public SetValueBindingHandler(final TagConfig config) {
        super(config);
        /* Define var and valueBinding attributes. */
        this.var = this.getRequiredAttribute("var");
        this.bean = this.getRequiredAttribute("bean");
        this.property = this.getRequiredAttribute("property");
    }

    /**
     * Apply.
     *
     * @param faceletsContext faceletsContext
     * @param parent parent
     *
     * @throws IOException IOException
     */
    public void apply(final FaceletContext faceletsContext, final UIComponent parent) {
    	// get the name of the new value
    	final String varName = this.var.getValue(faceletsContext);
    	final String beanName = (String) this.bean.getValueExpression(faceletsContext, String.class).getValue(faceletsContext);
    	final String propertyName = (String) this.property.getValueExpression(faceletsContext, String.class).getValue(faceletsContext);
    	
    	try {
    		final String propertyExpression = "#{" + beanName + "."+propertyName + "}";
    		
    		if (logger.isDebugEnabled()) {
    			logger.debug("property expression "+varName+" = "+propertyExpression);
    		}
    		
    		final ValueExpression propertyValueExpression = getValueExpression(propertyExpression, faceletsContext, Object.class);
    		
    		faceletsContext.getVariableMapper().setVariable(varName, propertyValueExpression);
    	} catch (Exception e) {
    		logger.error(e);
    		faceletsContext.setAttribute(varName, "["+beanName+" "+propertyName+"]");
    	}
    }

	private ValueExpression getValueExpression(String expression, FaceletContext ctx, Class<?> type) {
		try {
			final ExpressionFactory expressionFactory = ctx.getExpressionFactory();
			final ValueExpression valueExpression = expressionFactory.createValueExpression(ctx, expression, type);
			return new TagValueExpression(var, valueExpression);
		} catch (Exception e) {
			throw new TagAttributeException(var, e);
		}
	}
    
}
