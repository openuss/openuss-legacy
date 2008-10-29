package org.openuss.web.application;

import org.apache.log4j.Logger;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.view.ViewControllerMapper;

/**
 * @author Ingo Dueppe
 */
public class PropertyViewControllerMapper implements ViewControllerMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PropertyViewControllerMapper.class);

	private Properties views;

	public Properties getViews() {
		return views;
	}

	public void setViews(Properties views) {
		this.views = views;
	}
	
	public String mapViewId(String viewId) {
		if (StringUtils.isEmpty(viewId)) {
			return null;
		}
		
		// remove "/views/" prefix
		if (viewId.startsWith("/views/")) {
			viewId = viewId.substring(7);
		}
		
		int slash = viewId.lastIndexOf('/');
        int period = viewId.lastIndexOf('.');
        if ((period >= 0) && (period > slash)) {
            viewId = viewId.substring(0, period);
        }
        
        if (logger.isDebugEnabled()) {
        	logger.debug("look up controller for view "+viewId);
        }
		
		return StringUtils.trim(views.getProperty(viewId));
	}
}
