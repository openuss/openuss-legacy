package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.User;

public class InstituteDaoMock extends AbstractMockDao<Institute> implements InstituteDao {

	public Institute create(String name, String shortcut, String ownername, String address, String postcode, String city, String telephone, String telefax, String website, String locale, String description, String email, String theme, boolean enabled) {
		return null;
	}

	public Object create(int transform, String name, String shortcut, String ownername, String address, String postcode, String city, String telephone, String telefax, String website, String locale, String description, String email, String theme, boolean enabled) {
		return null;
	}

	public Institute create(boolean enabled, String name, User owner, String ownername, String shortcut) {
		return null;
	}

	public Object create(int transform, boolean enabled, String name, User owner, String ownername, String shortcut) {
		return null;
	}

	public void instituteDetailsToEntity(InstituteDetails sourceVO, Institute targetEntity, boolean copyIfNull) {
	}

	public Institute instituteDetailsToEntity(InstituteDetails instituteDetails) {
		return null;
	}

	public void instituteDetailsToEntityCollection(Collection instances) {
	}

	public void instituteSecurityToEntity(InstituteSecurity sourceVO, Institute targetEntity, boolean copyIfNull) {
	}

	public Institute instituteSecurityToEntity(InstituteSecurity instituteSecurity) {
		return null;
	}

	public void instituteSecurityToEntityCollection(Collection instances) {
	}

	public Institute findByShortcut(String shortcut) {
		return null;
	}

	public Institute findByShortcut(String queryString, String shortcut) {
		return null;
	}

	public Object findByShortcut(int transform, String shortcut) {
		return null;
	}

	public Object findByShortcut(int transform, String queryString, String shortcut) {
		return null;
	}

	public List loadAllEnabled() {
		return null;
	}

	public List loadAllEnabled(String queryString) {
		return null;
	}

	public List loadAllEnabled(int transform) {
		return null;
	}

	public List loadAllEnabled(int transform, String queryString) {
		return null;
	}

	public void toInstituteDetails(Institute sourceEntity, InstituteDetails targetVO) {
	}

	public InstituteDetails toInstituteDetails(Institute entity) {
		return null;
	}

	public void toInstituteDetailsCollection(Collection entities) {
	}

	public void toInstituteSecurity(Institute sourceEntity, InstituteSecurity targetVO) {
	}

	public InstituteSecurity toInstituteSecurity(Institute entity) {
		return null;
	}

	public void toInstituteSecurityCollection(Collection entities) {
	}

	public Long getInstituteCount() {
		return null;
	}

	public Long getInstituteCount(String queryString) {
		return null;
	}

	public Object getInstituteCount(int transform) {
		return null;
	}

	public Object getInstituteCount(int transform, String queryString) {
		return null;
	}

	
}
