package org.openuss.framework.web.jsf.pages;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a page
 * @author Ingo Dueppe
 */
public class Page {

	private final String viewId;
	
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	public Page(String viewId) {
		this.viewId = viewId;
	}

	public String getViewId() {
		return viewId;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}
	
}
