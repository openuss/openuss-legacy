package org.openuss.web.wiki;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;

/**
 * Backing Bean for wikieditlinks.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikieditlinks", scope = Scope.REQUEST)
@View
public class WikiEditLinksPage extends AbstractWikiPage {
	
	private String selected = Constants.WIKI_STARTSITE_NAME;
	
	private List<SelectItem> wikiSites;
	
	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception { 
		super.prerender();
		final List<WikiSiteInfo> sites = wikiService.findWikiSitesByDomainObject(this.courseInfo.getId());
		wikiSites = new ArrayList<SelectItem>(sites.size());
		for (WikiSiteInfo site : sites) {
			wikiSites.add(new SelectItem(site.getName(), readablePageName(site.getName()))); 
		}
		wikiSites.add(new SelectItem(Constants.WIKI_NEW_TAG, i18n(Constants.WIKI_EDIT_LINKS_NEWPAGE)));
	}

	public String getSelected() {
		return selected;
	}
	
	public void setSelected(String selected) {
		this.selected = selected;
	}

	public List<SelectItem> getWikiSites() {
		return wikiSites;
	}
	public void setWikiSites(List<SelectItem> wikiSites) {
		this.wikiSites = wikiSites;
	}

}