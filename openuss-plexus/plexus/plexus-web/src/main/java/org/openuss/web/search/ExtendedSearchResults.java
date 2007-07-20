package org.openuss.web.search;

import java.io.Serializable;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.search.DomainResult;


/**
 * Extended Search Result Container 
 * @author Kai Stettner
 */
@Bean(name="extended_search_results", scope=Scope.SESSION)
public class ExtendedSearchResults implements Serializable {
	
	private static final long serialVersionUID = 2703663293293922929L;

	private List<DomainResult> hits;
	
	private String textToSearch;

	public List<DomainResult> getHits() {
		return hits;
	}

	public void setHits(List<DomainResult> hits) {
		this.hits = hits;
	}

	public String getTextToSearch() {
		return textToSearch;
	}

	public void setTextToSearch(String textToSearch) {
		this.textToSearch = textToSearch;
	}

	public int getHitCounts() {
		return hits != null ? hits.size() : 0;
	}

}
