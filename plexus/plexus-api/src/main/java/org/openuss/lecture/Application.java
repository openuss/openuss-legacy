package org.openuss.lecture;

import java.util.Date;

import org.openuss.foundation.DomainObject;
import org.openuss.security.User;

/**
 * Application for an official institute of the department.
 * 
 * @author Ingo Dueppe
 */
public interface Application extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Date getApplicationDate();

	public void setApplicationDate(Date applicationDate);

	public Date getConfirmationDate();

	public void setConfirmationDate(Date confirmationDate);

	public String getDescription();

	public void setDescription(String description);

	public boolean isConfirmed();

	public void setConfirmed(boolean confirmed);

	public Department getDepartment();

	public void setDepartment(Department department);

	public Institute getInstitute();

	public void setInstitute(Institute institute);

	public User getApplyingUser();

	public void setApplyingUser(User applyingUser);

	public org.openuss.security.User getConfirmingUser();

	public void setConfirmingUser(User confirmingUser);

	public abstract void add(Department department);

	public abstract void add(Institute institute);

	public abstract void remove(Department department);

	public abstract void remove(Institute institute);

}