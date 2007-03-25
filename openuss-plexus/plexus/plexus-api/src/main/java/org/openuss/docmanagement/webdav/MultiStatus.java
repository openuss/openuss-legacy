package org.openuss.docmanagement.webdav;

import java.util.List;

import org.dom4j.Document;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public interface MultiStatus {
	public void addResponse(MultiStatusResponse response);
	
	public PropertyResponse createPropertyResponse(String href, String description);
	
	public StatusResponse createStatusResponse(String href, int statusCode, String description);
	
	public String getDescription();
	
	public List<MultiStatusResponse> getResponses();
	
	public void setDescription(String description);

	public void toXml(Document document);
}
