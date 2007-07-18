package org.openuss.framework.jsfcontrols.tags;

import java.io.IOException;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.apache.log4j.Logger;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.TagValueExpression;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * Maps expression binding to value so other tags can access it.
 * 
 * @author Ingo Dueppe
 */
public final class SetEntityBindingHandler extends TagHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SetEntityBindingHandler.class);

	private final TagAttribute var;
	private final TagAttribute entity;
	private final TagAttribute property;

	/**
	 * Constructor. Set up the attributes for this tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public SetEntityBindingHandler(final TagConfig config) {
		super(config);

		this.var = this.getRequiredAttribute("var");
		this.entity = this.getRequiredAttribute("entity");
		this.property = this.getRequiredAttribute("property");
	}

	/**
	 * Apply.
	 * 
	 * @param faceletsContext
	 *            faceletsContext
	 * @param parent
	 *            parent
	 * 
	 * @throws IOException
	 *             IOException
	 */
	public void apply(final FaceletContext faceletsContext, final UIComponent parent) {
		// Get the name of the new value.
		final String varName = this.var.getValue(faceletsContext);
		final String entityName = (String) entity.getValueExpression(faceletsContext, String.class).getValue(faceletsContext);
		final String propertyName = (String) property.getValueExpression(faceletsContext, String.class).getValue(faceletsContext);
		try {

			final String propertyExpression = "#{" + entityName + "." + propertyName + "}";

			if (logger.isTraceEnabled())
				logger.trace("property expression =" + propertyExpression);

			// Create value expression to the entity property.
			final ValueExpression propertyValueExpression = 
				getValueExpression(propertyExpression, faceletsContext,	Object.class);


			// Put the value binding into the FaceletsContext where
			// we can retrieve it from other components.
			faceletsContext.getVariableMapper().setVariable(varName, propertyValueExpression);

			// Cache the type so we don't have to look it up in each tag.
			Class<?> type = propertyValueExpression.getType(faceletsContext);
			faceletsContext.setAttribute(varName + "Type", type);
		} catch (Exception ex) {
			logger.error(ex);
			faceletsContext.setAttribute(varName, "["+entityName+" "+propertyName+"]");
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
