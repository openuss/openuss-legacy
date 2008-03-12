package org.openuss.framework.jsfcontrols.tags;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * Renders its childs if globalMessages were set.
 * 
 * @author Johannes Puester
 * 
 */
public final class HasGlobalMessagesHandler extends TagHandler {

	/**
	 * Constructor.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public HasGlobalMessagesHandler(final TagConfig config) {
		super(config);
	}

	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,	ELException {
		if (ctx.getFacesContext().getMessages(null).hasNext()) {
			nextHandler.apply(ctx, parent);
		}
	}
}
