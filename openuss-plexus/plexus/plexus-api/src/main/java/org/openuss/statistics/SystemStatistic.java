package org.openuss.statistics;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface SystemStatistic extends DomainObject {

	public Long getUsers();

	public void setUsers(Long users);

	public Long getUniversities();

	public void setUniversities(Long universities);

	public Long getDepartments();

	public void setDepartments(Long departments);

	public Long getInstitutes();

	public void setInstitutes(Long institutes);

	public Long getCourses();

	public void setCourses(Long courses);

	public Long getDocuments();

	public void setDocuments(Long documents);

	public Long getPosts();

	public void setPosts(Long posts);

	public Date getCreateTime();

	public void setCreateTime(Date createTime);

	public Long getId();

	public void setId(Long id);

}