package org.openuss.framework.jsfcontrols.tags;

import java.io.IOException;

import javax.faces.component.UIComponent;

import org.apache.log4j.Logger;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * Maps an parameter el expression to a string attribute
 * 
 * @author Ingo Dueppe
 */
public final class StringAttributeHandler extends TagHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StringAttributeHandler.class);

	private final TagAttribute name;
	private final TagAttribute value;

	/**
	 * Constructor. Set up the attributes for this tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public StringAttributeHandler(final TagConfig config) {
		super(config);

		this.name = this.getRequiredAttribute("name");
		this.value = this.getRequiredAttribute("value");
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
		final String strName = this.name.getValue(faceletsContext);
		final String strValue = (String) value.getValueExpression(faceletsContext, String.class).getValue(
				faceletsContext);

		if (logger.isDebugEnabled()) {
			logger.debug("set attribute " + strName + " = " + strValue);
		}

		parent.getAttributes().put(strName, strValue);
	}

}
