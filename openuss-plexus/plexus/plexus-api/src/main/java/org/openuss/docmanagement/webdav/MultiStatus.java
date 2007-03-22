package org.openuss.docmanagement.webdav;

import java.util.List;

import org.dom4j.Document;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public interface MultiStatus {
	public List<MultiStatusResponse> getResponses();
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public void addResponse(MultiStatusResponse response);

	public void toXml(Document document);
}
