package org.openuss.web.application;

import org.apache.log4j.Logger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.view.ViewControllerMapper;

/**
 * ViewControllerMapper
 * 
 * @author Ingo Dueppe
 */
public class DelegateViewControllerMapper implements ViewControllerMapper {

	private static final Logger logger = Logger.getLogger(DelegateViewControllerMapper.class);

	private List<ViewControllerMapper> strategies;

	/**
	 * Defines the strategies of view controller mappings.
	 */
	public List<ViewControllerMapper> getStrategies() {
		return strategies;
	}

	/**
	 * Defines the strategies of view controller mappings.
	 * 
	 * @param strategies
	 */
	public void setStrategies(final List<ViewControllerMapper> strategies) {
		this.strategies = strategies;
	}

	/**
	 * {@inheritDoc}
	 */
	public String mapViewId(final String viewId) {
		if (logger.isDebugEnabled()) {
			logger.debug("look up controller for " + viewId);
		}
		if (strategies != null) {
			for (ViewControllerMapper mapper : strategies) {
				String ctrl = mapper.mapViewId(viewId);
				if (StringUtils.isNotBlank(ctrl)) {
					logger.debug("found " + ctrl);
					return ctrl;
				}
			}
		}
		return null;
	}

}
