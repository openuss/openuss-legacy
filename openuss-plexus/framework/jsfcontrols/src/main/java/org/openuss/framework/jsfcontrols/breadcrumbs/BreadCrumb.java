package org.openuss.framework.jsfcontrols.breadcrumbs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.openuss.framework.web.jsf.util.ConversationUtil;

/**
 * 
 * @author Sebastian Roekens
 * @author Ingo Dueppe
 * 
 */
public class BreadCrumb implements Serializable{

	/** logger */
	private static final long serialVersionUID = 7288616777708885608L;
	
	private String link;
	private String name;
	private String hint;
	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	public BreadCrumb() {
		this(null, null, null);
	}
	public BreadCrumb(String link, String name, String hint) {
		super();
		this.link = link;
		this.name = name;
		this.hint = hint;
	}
	public BreadCrumb(String name, String hint) {
		this(null, name, hint);
	}
	public BreadCrumb(String name) {
		this(null, name, null);
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getLink() {
		return ConversationUtil.encodeParameters(link, parameters);
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}
	
	public void removeParameter(String name) {
		parameters.remove(name);
	}

}