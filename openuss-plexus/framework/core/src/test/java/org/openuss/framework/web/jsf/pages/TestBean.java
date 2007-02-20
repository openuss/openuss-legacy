package org.openuss.framework.web.jsf.pages;

import org.apache.log4j.Logger;

/**
 * Helper test bean
 * 
 * @author Ingo Dueppe
 *
 */
public class TestBean {

	private static final Logger logger = Logger.getLogger(TestBean.class);

	private Long id = 123456789L;
	
	public TestBean() {}
	
	public TestBean(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String accessDenied() {
		logger.debug("access denied action method invoked.");
		return "error";
	}
}
