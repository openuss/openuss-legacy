package org.openuss.wiki;

import java.util.Date;

/**
 * a WikiSiteVersion is related to a WikiSite and to a CourseMember.
 * It stores the text of the site, the creationDate and a note
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface WikiSiteVersion extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getText();

	public void setText(String text);

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public String getNote();

	public void setNote(String note);

	public Boolean getStable();

	public void setStable(Boolean stable);

	public org.openuss.wiki.WikiSite getWikiSite();

	public void setWikiSite(org.openuss.wiki.WikiSite wikiSite);

	public org.openuss.security.User getAuthor();

	public void setAuthor(org.openuss.security.User author);

}