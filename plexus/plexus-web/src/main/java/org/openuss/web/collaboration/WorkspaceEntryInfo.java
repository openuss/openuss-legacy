package org.openuss.web.collaboration;

import java.io.Serializable;
import java.util.Date;

import org.openuss.foundation.DomainObject;

public class WorkspaceEntryInfo implements Serializable, DomainObject {
	private Long id;
	
	private String name;
	private String fileName;
	private String description;
	private String extension;
	private Date created;
	private Date modified;
	private boolean folder;
	
	public WorkspaceEntryInfo() {
		// TODO Auto-generated constructor stub
	}
	public WorkspaceEntryInfo(Long id, String name, String fileName,
			String description, String extension, Date created, Date modified,
			boolean folder) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.description = description;
		this.extension = extension;
		this.created = created;
		this.modified = modified;
		this.folder = folder;
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
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public boolean isFolder() {
		return folder;
	}
	public void setFolder(boolean folder) {
		this.folder = folder;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSizeAsString() {
		return "1234 kb";
	}
	
}
