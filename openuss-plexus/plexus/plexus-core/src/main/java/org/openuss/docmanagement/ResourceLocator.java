package org.openuss.docmanagement;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ResourceLocator {
	private final String prefix;
	private final String resourcePath;
	private final String href;
	
	public ResourceLocator(String prefix, String href) {
		this.prefix = prefix;
		this.href = href;
		// TODO href zerlegen
		this.resourcePath = "";
	}
	
	/**
	 * Returns the href representation of the resource.
	 * @param isFolder 
	 * @return href representation of the resource.
	 */
	public String getHref(boolean isFolder) {
		// append trailing slash for folders, but NOT for root
		String suffix = "";
		if (isFolder && !isRootLocation()) {
			suffix = "/";
		}
		return href + suffix;
	}
	
	/**
	 * @return
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * @return
	 */
	public String getResourcePath() {
		return resourcePath;
	}
	
	/**
	 * @return
	 */
	public boolean isRootLocation() {
		return getResourcePath().equals("/");
	}
}
