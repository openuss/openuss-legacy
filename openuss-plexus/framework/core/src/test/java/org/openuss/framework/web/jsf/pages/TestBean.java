package org.openuss.framework.web.jsf.pages;

public class TestBean {
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
}
