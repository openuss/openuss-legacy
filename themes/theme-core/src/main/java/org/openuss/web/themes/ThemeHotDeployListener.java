package org.openuss.web.themes;

import static org.openuss.framework.utilities.StringPool.SPACE;
import static org.openuss.framework.utilities.StringPool.UNDERLINE;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openuss.framework.deploy.HotDeployEvent;
import org.openuss.framework.deploy.HotDeployException;
import org.openuss.framework.deploy.HotDeployListener;

/**
 * Listen to theme deploy events. If a theme war is deployed it parse the
 * /WEB-INF/openuss-theme.xml and creates a corresponding Theme object and
 * register this at the themeManager.
 * 
 * @author Ingo Dueppe
 * 
 */
public class ThemeHotDeployListener implements HotDeployListener {

	private static final Logger logger = Logger.getLogger(ThemeHotDeployListener.class);

	private ThemeManager themeManager;

	public ThemeManager getThemeManager() {
		return themeManager;
	}

	public void setThemeManager(final ThemeManager themeManager) {
		this.themeManager = themeManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokeDeploy(final HotDeployEvent event) throws HotDeployException {
		String servletContextName = "N/A";
		try {
			ServletContext context = event.getServletContext();
			servletContextName = StringUtils.replace(context.getServletContextName(), SPACE, UNDERLINE);

			InputStream stream = context.getResourceAsStream("/WEB-INF/openuss-theme.xml");
			if (stream != null) {
				logger.debug("Invoking theme deploy for " + servletContextName);
				Theme theme = buildTheme(stream);
				theme.setServletContext(context);
				theme.setClassLoader(event.getContextClassLoader());
				themeManager.registerTheme(theme);
			}

		} catch (Exception e) {
			logger.error(e);
			throw new HotDeployException("Error unregistering themes for " + servletContextName, e);
		}
	}

	private Theme buildTheme(final InputStream stream) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		saxReader.setMergeAdjacentText(true);
		// @todo should be switch on!
		saxReader.setValidation(false);
		Document doc = saxReader.read(stream);
		Element themeElement = doc.getRootElement();

		Theme theme = new ThemeBean();

		theme.setId(themeElement.attributeValue("id"));
		theme.setName(themeElement.attributeValue("name"));

		List<Element> elements = doc.getRootElement().elements();

		for (Element element : elements) {
			if (StringUtils.equals(Theme.IMAGE_ROOTPATH, element.getName()))
				theme.setImagesPath(element.getTextTrim());
			if (StringUtils.equals(Theme.LAYOUT, element.getName()))
				theme.setLayout(element.getTextTrim());
			if (StringUtils.equals(Theme.STYLESHEET, element.getName()))
				theme.setStylesheet(element.getTextTrim());
			if (StringUtils.equals(Theme.DIALOG, element.getName()))
				theme.setDialog(element.getTextTrim());
		}
		return theme;
	}

	public void invokeUndeploy(final HotDeployEvent event) throws HotDeployException {
		String servletContextName = "N/A";
		try {
			ServletContext context = event.getServletContext();
			servletContextName = StringUtils.replace(context.getServletContextName(), SPACE, UNDERLINE);

			InputStream stream = context.getResourceAsStream("/WEB-INF/openuss-theme.xml");
			if (stream != null) {
				logger.debug("Invoking theme undeploy for " + servletContextName);
				Theme theme = new ThemeBean();
				theme.setServletContext(context);
				theme.setClassLoader(event.getContextClassLoader());
				themeManager.unregisterTheme(theme);
			}

		} catch (Exception e) {
			logger.error(e);
			throw new HotDeployException("Error registering themes for " + servletContextName, e);
		}

	}

}
