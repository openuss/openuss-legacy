package org.openuss.lecture;

import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * A University is a special type of an Organisation.
 * It's the top level institution and contains Departments and Periods.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */
public interface University extends Organisation, DomainObject {

	public UniversityType getUniversityType();

	public void setUniversityType(UniversityType universityType);

	public List<Department> getDepartments();

	public void setDepartments(List<Department> departments);

	public List<Period> getPeriods();

	public void setPeriods(List<Period> periods);

	public abstract void add(Department department);

	public abstract void remove(Department department);

	public abstract void add(Period period);

	public abstract void remove(Period period);

	public abstract List getActivePeriods();

	/**
	 * Retrieve the first period of the university with the default flag equal
	 * true.
	 */
	public abstract Period getDefaultPeriod();

}