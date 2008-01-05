// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: ValueObject.vsl in openuss/tools/andromda/templates.
//
package org.openuss.web.feeds;

import java.io.Writer;
import java.util.Date;


/**
 * 
 * @author Ingo D�ppe
 * @author Sebastian Roeckens
 */
public class FeedWrapper  {

	private static final long serialVersionUID = 3926487462738394282L;

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(final FeedWrapper otherBean) {
		this.setWriter(otherBean.getWriter());
		this.setLastModified(otherBean.getLastModified());
	}

	private java.io.Writer writer;

	/**
	 * 
	 */
	public java.io.Writer getWriter() {
		return this.writer;
	}

	public void setWriter(final Writer writer) {
		this.writer = writer;
	}

	private java.util.Date lastModified;

	/**
	 * 
	 */
	public java.util.Date getLastModified() {
		return lastModified != null? new Date(lastModified.getTime()):null;
	}

	public void setLastModified(final Date lastModified) {
		this.lastModified = null;
		if (lastModified != null) {
			this.lastModified = new Date(lastModified.getTime());
		}
	}

}