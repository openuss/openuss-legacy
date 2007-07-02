package org.openuss.framework.web.jsf.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a page
 * @author Ingo Dueppe
 */
public class Page {

	private final String viewId;
	
	private List<Parameter> parameters = new ArrayList<Parameter>();
	private List<SecurityConstraint> securityConstraints = new ArrayList<SecurityConstraint>();
	
	public Page(String viewId) {
		this.viewId = viewId;
	}

	public String getViewId() {
		return viewId;
	}
	
	public List<Parameter> getParameters() {
		return  Collections.unmodifiableList(parameters);
	}
	
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}
	
	public List<SecurityConstraint> getSecurityConstraints() {
		return Collections.unmodifiableList(securityConstraints);
	}
	
	public void addSecurityConstraint(SecurityConstraint securityConstraint) {
		securityConstraints.add(securityConstraint);
	}
	
}
