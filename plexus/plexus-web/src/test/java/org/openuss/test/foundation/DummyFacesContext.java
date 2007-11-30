package org.openuss.test.foundation;

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

/**
 * This class implements FacesContext without any functionality.
 * Required for unit testing in plexus-web.  
 */
public class DummyFacesContext extends FacesContext {
	@Override
	public void addMessage(String arg0, FacesMessage arg1) {
		;
	}

	@Override
	public Application getApplication() {
		return null;
	}

	@Override
	public Iterator getClientIdsWithMessages() {
		return null;
	}

	@Override
	public ExternalContext getExternalContext() {
		return null;
	}

	@Override
	public Severity getMaximumSeverity() {
		return null;
	}

	@Override
	public Iterator getMessages() {
		return null;
	}

	@Override
	public Iterator getMessages(String arg0) {
		return null;
	}

	@Override
	public RenderKit getRenderKit() {
		return null;
	}

	@Override
	public boolean getRenderResponse() {
		return false;
	}

	@Override
	public boolean getResponseComplete() {
		return false;
	}

	@Override
	public ResponseStream getResponseStream() {
		return null;
	}

	@Override
	public ResponseWriter getResponseWriter() {
		return null;
	}

	@Override
	public UIViewRoot getViewRoot() {
		return null;
	}

	@Override
	public void release() {
	
	}

	@Override
	public void renderResponse() {
		
	}

	@Override
	public void responseComplete() {
		
	}

	@Override
	public void setResponseStream(ResponseStream arg0) {
		
	}

	@Override
	public void setResponseWriter(ResponseWriter arg0) {
		
	}

	@Override
	public void setViewRoot(UIViewRoot arg0) {
		
	}
}
