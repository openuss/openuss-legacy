package org.openuss.lecture;

import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe  
 */
public interface Department extends Organisation, DomainObject {

	public DepartmentType getDepartmentType();

	public void setDepartmentType(DepartmentType departmentType);

	public boolean isDefaultDepartment();

	public void setDefaultDepartment(boolean defaultDepartment);

	public University getUniversity();

	public void setUniversity(University university);

	public List<Institute> getInstitutes();

	public void setInstitutes(List<Institute> institutes);

	public List<Application> getApplications();

	public void setApplications(List<Application> applications);

	public abstract void add(Institute institute);

	public abstract void remove(Institute institute);

}