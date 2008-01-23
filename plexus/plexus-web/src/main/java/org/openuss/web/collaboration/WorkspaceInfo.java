package org.openuss.web.collaboration;

import java.io.Serializable;

import org.openuss.foundation.DomainObject;

/** FIXME: remove this class and replace through generated! */
public class WorkspaceInfo implements Serializable, DomainObject {
	
	private static final long serialVersionUID = 4104318624281216495L;

	private Long id;
	
	private String name;

	public WorkspaceInfo() {
		super();
	}
	public WorkspaceInfo(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
