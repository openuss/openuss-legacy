package org.openuss.web.search;

import java.io.Serializable;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.search.DomainResult;

/**
 * Search Result Container 
 * 
 * @author Ingo Dueppe
 */
@Bean(name="search_results", scope=Scope.SESSION)
public class SearchResults implements Serializable{

	private static final long serialVersionUID = 2703663123055519715L;

	private List<DomainResult> hits;
	
	private boolean fuzzy = true;
	
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

	public boolean isFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}
	
}
