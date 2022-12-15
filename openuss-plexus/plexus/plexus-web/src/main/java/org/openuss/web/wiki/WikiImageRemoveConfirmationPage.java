package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * Backing Bean for wikiremoveimage.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiimageremoveconfirmation", scope = Scope.REQUEST)
@View
public class WikiImageRemoveConfirmationPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiImageRemoveConfirmationPage.class);
	
	@Property(value="#{"+Constants.WIKI_IMAGE+"}")
	private FolderEntryInfo image;

	@Prerender
	public void prerender() {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			final BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n(Constants.WIKI_IMAGE_REMOVE_HEADER));
			newCrumb.setHint(i18n(Constants.WIKI_IMAGE_REMOVE_HEADER));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception exception) {
			LOGGER.error(exception);
		}
	}
	
	/**
	 * Removes a specific image and return the Wiki Choose Image Page.
	 * @return Wiki Choose Image Page.
	 */
	public String removeImage() {
		try {
			FolderEntryInfo imageFile = (FolderEntryInfo) getBean(Constants.WIKI_IMAGE);
			getWikiService().deleteImage(imageFile.getId());
			setBean(Constants.WIKI_IMAGE, null);
			addMessage(i18n(Constants.WIKI_IMAGE_REMOVE_SUCCEEDED));
			return Constants.WIKI_CHOOSE_IMAGE_PAGE;
		} catch (Exception e) {
			LOGGER.error("Remove image failed", e);
			addMessage(i18n(Constants.WIKI_IMAGE_CANNOT_BE_REMOVED));
			return Constants.WIKI_CHOOSE_IMAGE_PAGE;
		}
	}

	public FolderEntryInfo getImage() {
		return image;
	}

	public void setImage(FolderEntryInfo image) {
		this.image = image;
	}
}