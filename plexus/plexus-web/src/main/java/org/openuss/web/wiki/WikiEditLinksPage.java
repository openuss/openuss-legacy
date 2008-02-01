package org.openuss.web.wiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;


@Bean(name = "views$secured$wiki$wikieditlinks", scope = Scope.REQUEST)
@View
public class WikiEditLinksPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiEditLinksPage.class);
	
	private boolean createNewSite;
	
	private String selected = Constants.WIKI_STARTSITE_NAME;
	private List<SelectItem> wikiSites = null;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		List<WikiSiteInfo> sites = wikiService.findWikiSitesByCourse(this.courseInfo.getId());
		this.wikiSites = new ArrayList<SelectItem>(sites.size());
		for (WikiSiteInfo site : sites) {
			this.wikiSites.add(new SelectItem(Long.toString(site.getId()), site.getName()));
		}
	}

	public boolean isCreateNewSite() {
		return createNewSite;
	}
	public void setCreateNewSite(boolean createNewSite) {
		this.createNewSite = createNewSite;
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