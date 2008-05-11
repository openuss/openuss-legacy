package org.openuss.framework.web.jsf.pages;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openuss.framework.web.jsf.util.FacesUtils;
import org.openuss.framework.web.jsf.util.Resources;

/**
 * Pages
 * 
 * @author Ingo Dueppe
 */
public class Pages {

	private static final Logger logger = Logger.getLogger(Pages.class);

	private static final String PAGE_XML_SUFFIX = ".page.xml";
	private volatile static Pages instance;

	private Map<String, Page> pagesByViewId = Collections.synchronizedMap(new HashMap<String, Page>());
	private Map<String, List<Page>> pageStacksByViewId = Collections.synchronizedMap(new HashMap<String, List<Page>>());
	
	private static final class WildcardComparator implements Comparator<String>, Serializable {
		private static final long serialVersionUID = -4054989573023840704L;

		public int compare(String x, String y) {
			if (x.length() < y.length()) {
				return -1;
			}
			if (x.length() > y.length()) {
				return 1;
			}
			return x.compareTo(y);
		}
	}

	private SortedSet<String> wildcardViewIds = new TreeSet<String>(new WildcardComparator());

	/**
	 * Singleton constructor
	 */
	private Pages() {
		initialize();
	}

	public static Pages instance() {
		if (instance == null) {
			synchronized (Pages.class) {
				instance = new Pages();
			}
		}
		
		//	Uncomment this for debug the web layer - this enables to edit pages.xml without restart tomcat
			instance = new Pages();
		
		return instance;
	}

	/**
	 * if a /WEB-INF/pages.xml exists and loads the containing page
	 * configurations
	 */
	private void initialize() {
		InputStream stream = Resources.getResourceAsStream("/WEB-INF/pages.xml");
		if (stream == null) {
			logger.info("no /WEB-INF/pages.xml file found");
		} else {
			logger.info("reading /WEB-INF/pages.xml");
			parse(stream);
		}
	}

	/**
	 * Retrieve a Page object according to a view
	 * 
	 * @param viewId
	 *            a JSF view id
	 * @return Page
	 */
	public Page getPage(String viewId) {
		Page page = null;
		if (viewId != null) {
			page = getCachedPage(viewId);
			if (page == null) {
				page = createPage(viewId);
			}
		}
		return page;
	}

	/**
	 * Apply any page parameters passed as parameter values to the model.
	 * 
	 * @param facesContext
	 */
	public void applyRequestParameterValues(FacesContext facesContext) {
		String viewId = facesContext.getViewRoot().getViewId();
		if (logger.isDebugEnabled()) {
			logger.debug("apply request parameter values to the model for "+viewId);
		}
		Map<String, Object> requestParameters = facesContext.getExternalContext().getRequestParameterMap();
		for (Page page : getPageStack(viewId)) {
			for (Parameter parameter : page.getParameters()) {
				Object value = parameter.getValueFromRequest(facesContext, requestParameters);
				if (value != null) {
					ValueBinding valueBinding = parameter.getValueBinding();
					if (logger.isDebugEnabled()) {
						logger.debug("Apply "+valueBinding.getExpressionString()+" = "+value);
					}
					valueBinding.setValue(facesContext, value);
				}
			}
		}
	}
	
	/**
	 * Check if all security constraints are fulfilled.
	 * @param facesContext
	 */
	public void performSecurityConstraints(FacesContext facesContext) {
		String viewId = facesContext.getViewRoot().getViewId();
		if (logger.isDebugEnabled()) {
			logger.debug("check security constraints of viewid "+viewId);
		}
		for (Page page: getPageStack(viewId)) {
			for (SecurityConstraint constraint: page.getSecurityConstraints()) {
				constraint.performConstraint();
			}
		}
	}

	/**
	 * Encode page parameters into a URL
	 * 
	 * @param facesContext
	 * @param url
	 *            the base URL
	 * @param viewId
	 *            the JSF view id of the page
	 * @return the URL with parameters appended
	 */
	public String encodePageParameters(FacesContext facesContext, String url, String viewId) {
		Map<String, Object> parameters = getConvertedParameters(facesContext, viewId);
		return FacesUtils.encodeParameters(url, parameters);
	}

	/**
	 * Get the values of any page parameters by evaluating the value bindings
	 * against the model and converting to String.
	 * 
	 * @param viewId the JSF view id
	 * @return a map of page parameter name to String value
	 */
	public Map<String, Object> getConvertedParameters(FacesContext facesContext, String viewId) {
		return getConvertedParameters(facesContext, viewId, Collections.EMPTY_SET);
	}

	private Map<String, Object> getConvertedParameters(FacesContext facesContext, String viewId, Set<String> overridden) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Page page: getPageStack(viewId)) {
			for (Parameter parameter : page.getParameters()) {
				if (!overridden.contains(parameter.getName())) {
					Object value = parameter.getValueFromModel(facesContext);
					if (value != null) {
						parameters.put(parameter.getName(),value);
					}
				}
			}
		}
		return parameters;
	}

	/**
	 * Get the stack of Page objects, from least specific to most specific, that
	 * match the given view id.
	 * 
	 * @param viewId
	 *            a JSF view id
	 */
	private List<Page> getPageStack(String viewId) {
		List<Page> stack = pageStacksByViewId.get(viewId);
		if (stack == null) {
			stack = createPageStack(viewId);
			pageStacksByViewId.put(viewId, stack);
		}
		return stack;
	}

	/**
	 * Create the stack of pages that match a JSF view id
	 */
	private List<Page> createPageStack(String viewId) {
		List<Page> stack = new ArrayList<Page>(1);
		if (viewId != null) {
			for (String wildcard : wildcardViewIds) {
				if (viewId.startsWith(wildcard.substring(0, wildcard.length() - 1))) {
					stack.add(getPage(wildcard));
				}
			}
		}
		Page page = getPage(viewId);
		if (page != null) {
			stack.add(page);
		}
		return stack;
	}

	/**
	 * Create a new Page object for a JSF view id, by searching for a
	 * viewId.page.xml file.
	 */
	private Page createPage(String viewId) {
		String resourceName = replaceExtension(viewId, PAGE_XML_SUFFIX);
		InputStream stream = resourceName == null ? null : Resources.getResourceAsStream(resourceName);

		if (stream == null) {
			Page result = new Page(viewId);
			pagesByViewId.put(viewId, result);
			return result;
		} else {
			parse(stream, viewId);
			return getCachedPage(viewId);
		}
	}

	private Page getCachedPage(String viewId) {
		Page result = pagesByViewId.get(viewId);
		if (result == null) {
			// workaround for what gavin king believe is a bug in the JSF RI
			viewId = replaceExtension(viewId, getSuffix());
			if (viewId != null) {
				result = pagesByViewId.get(viewId);
			}
		}
		return result;
	}

	/**
	 * Get the root element of the document
	 */
	private Element getDocumentRoot(InputStream stream) {
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setMergeAdjacentText(true);
			// TODO should be switch on for validation!
			saxReader.setValidation(false);
			Document doc = saxReader.read(stream);
			return doc.getRootElement();
		} catch (DocumentException de) {
			throw new RuntimeException(de);
		}
	}

	/**
	 * @param stream
	 *            intputstream to a pages.xml file
	 */
	private void parse(InputStream stream) {
		Element root = getDocumentRoot(stream);
		List<Element> elements = root.elements("page");
		for (Element element : elements) {
			parsePage(element, element.attributeValue("view-id"));
		}
	}

	/**
	 * @param stream
	 *            input stream to *.page.xml file
	 * @param viewId
	 *            a JSF view id
	 */
	private void parse(InputStream stream, String viewId) {
		parsePage(getDocumentRoot(stream), viewId);
	}

	/**
	 * Parse a page element and add a Page to the map
	 * 
	 * @param element
	 * @param viewId
	 */
	private void parsePage(Element element, String viewId) {
		if (logger.isInfoEnabled()) {
			logger.info("parsing page infos for " + viewId);
		}

		if (viewId == null) {
			throw new IllegalStateException("Must specify view-id for <page/> declaration");
		}
		if (viewId.endsWith("*")) {
			wildcardViewIds.add(viewId);
		}

		Page page = new Page(viewId);
		pagesByViewId.put(viewId, page);

		parseParameters(element, page);
		parseSecurityConstraints(element, page);
	}

	private void parseSecurityConstraints(Element element, Page page) {
		List<Element> elements = element.elements("securityConstraint");
		for(Element constraintElement : elements) {
			page.addSecurityConstraint(parseSecurityConstraint(constraintElement));
		}
	}

	private SecurityConstraint parseSecurityConstraint(Element element) {
		String domainObjectExpression = element.attributeValue("domainObject");
		String permissionExpression = element.attributeValue("permissions");
		String onDeniedActionExpression = element.attributeValue("onDeniedAction");
		
		if (StringUtils.isBlank(domainObjectExpression)) {
			throw new IllegalArgumentException("DomainObject attribute must not be empty or null.");
		}
		
		if (StringUtils.isBlank(permissionExpression)) {
			throw new IllegalArgumentException("Permission attribute must not be empty or null.");
		}
		

		SecurityConstraint securityConstraint = new SecurityConstraint();
		securityConstraint.setDomainObject(FacesUtils.createValueBinding(domainObjectExpression));
		ValueBinding permission = FacesUtils.createValueBinding(permissionExpression);
		securityConstraint.setPermissions((Integer)permission.getValue(FacesContext.getCurrentInstance()));
		if (StringUtils.isNotBlank(onDeniedActionExpression)) {
			securityConstraint.setOnDeniedAction(FacesUtils.createMethodBinding(onDeniedActionExpression));
		}
		
		return securityConstraint;
	}

	/**
	 * qparam element page element
	 * @param page object
	 */
	private void parseParameters(Element element, Page page) {
		List<Element> elements = element.elements("parameter");
		for (Element parameterElement : elements) {
			page.addParameter(parseParameter(parameterElement));
		}
	}

	/**
	 * @param parameter
	 *            element
	 * @return Parameter object
	 */
	private Parameter parseParameter(Element element) {
		String valueExpression = element.attributeValue("value");
		String name = element.attributeValue("name");
		if (name == null) {
			if (valueExpression == null) {
				throw new IllegalArgumentException("must specify name or value for page <parameter/> declaration");
			}
			name = valueExpression.substring(2, valueExpression.length() - 1);
		}
		Parameter parameter = new Parameter(name);
		if (valueExpression != null) {
			parameter.setValueBinding(FacesUtils.createValueBinding(valueExpression));
		}
		parameter.setConverterId(element.attributeValue("converterId"));
		String converterExpression = element.attributeValue("converter");

		if (converterExpression != null) {
			parameter.setConverterValueBinding(FacesUtils.createValueBinding(converterExpression));
		}
		return parameter;
	}

	/**
	 * Convience method to replace the extension with the given suffix.<br/>
	 * <code>/pages/test.xhtml</code> to <code>/pages/test.page.xml</code>
	 * 
	 * @param value
	 *            the string to be changed
	 * @param suffix
	 *            suffix to be set
	 * @return the value with the new suffix
	 */
	private String replaceExtension(String value, String suffix) {
		int loc = value.lastIndexOf('.');
		return loc < 0 ? null : value.substring(0, loc) + suffix;
	}

	/**
	 * @return JSF default view suffix like .jsf or .faces
	 */
	public static String getSuffix() {
		String defaultSuffix = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(
				ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);
		return defaultSuffix == null ? ViewHandler.DEFAULT_SUFFIX : defaultSuffix;
	}

}
