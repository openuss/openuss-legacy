package org.openuss.web.wiki;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

/**
 * Wiki Import Information Bean
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name="wiki_import_info", scope=Scope.REQUEST)
public class WikiImportInfo {

	private Long courseId;
	private String type;
	
	public WikiImportInfo() {
		super();
	}
	
	public WikiImportInfo(Long courseId, String type) {
		this.courseId = courseId;
		this.type = type;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
