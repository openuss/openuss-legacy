package org.openuss.wiki;

import java.util.Collection;

import org.openuss.foundation.DomainObject;

/**
 * A WikiSite is related to a course and has a lot of WikiSiteVersions.
 * It has a name and can be marked as deleted.
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface WikiSite extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Long getDomainId();

	public void setDomainId(Long domainId);

	public String getName();

	public void setName(String name);

	public Boolean getDeleted();

	public void setDeleted(Boolean deleted);

	public Boolean getReadOnly();

	public void setReadOnly(Boolean readOnly);

	public Collection<WikiSiteVersion> getWikiSiteVersions();

	public void setWikiSiteVersions(Collection<WikiSiteVersion> wikiSiteVersions);

}