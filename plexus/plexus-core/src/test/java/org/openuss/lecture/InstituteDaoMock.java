package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.Membership;

public class InstituteDaoMock extends AbstractMockDao<Institute> implements InstituteDao {

	public Institute create(boolean enabled, Membership membership, String name, String ownerName, String shortcut) {
		return null;
	}

	public Object create(int transform, boolean enabled, Membership membership, String name, String ownerName,
			String shortcut) {
		return null;
	}

	public Object create(int transform, String shortcut, String name, String description, String ownerName,
			String address, String postcode, String city, String country, String telephone, String telefax,
			String website, String email, String locale, String theme, Long imageId, boolean enabled) {
		return null;
	}

	public Institute create(String shortcut, String name, String description, String ownerName, String address,
			String postcode, String city, String country, String telephone, String telefax, String website,
			String email, String locale, String theme, Long imageId, boolean enabled) {
		return null;
	}

	public List findByDepartmentAndEnabled(Department department, Boolean enabled) {
		return null;
	}

	public List findByDepartmentAndEnabled(int transform, Department department, Boolean enabled) {
		return null;
	}

	public List findByDepartmentAndEnabled(int transform, String queryString, Department department, Boolean enabled) {
		return null;
	}

	public List findByDepartmentAndEnabled(String queryString, Department department, Boolean enabled) {
		return null;
	}

	public List findByEnabled(Boolean enabled) {
		return null;
	}

	public List findByEnabled(int transform, Boolean enabled) {
		return null;
	}

	public List findByEnabled(int transform, String queryString, Boolean enabled) {
		return null;
	}

	public List findByEnabled(String queryString, Boolean enabled) {
		return null;
	}

	public List findByShortcut(int transform, String queryString, String shortcut) {
		return null;
	}

	public List findByShortcut(int transform, String shortcut) {
		return null;
	}

	public List findByShortcut(String queryString, String shortcut) {
		return null;
	}

	public List findByShortcut(String shortcut) {
		return null;
	}

	public void instituteInfoToEntity(InstituteInfo sourceVO, Institute targetEntity, boolean copyIfNull) {
		
	}

	public Institute instituteInfoToEntity(InstituteInfo instituteInfo) {
		return null;
	}

	public void instituteInfoToEntityCollection(Collection instances) {
		
	}

	public void instituteSecurityToEntity(InstituteSecurity sourceVO, Institute targetEntity, boolean copyIfNull) {
		
	}

	public Institute instituteSecurityToEntity(InstituteSecurity instituteSecurity) {
		return null;
	}

	public void instituteSecurityToEntityCollection(Collection instances) {
		
	}

	public void toInstituteInfo(Institute sourceEntity, InstituteInfo targetVO) {
		
	}

	public InstituteInfo toInstituteInfo(Institute entity) {
		return null;
	}

	public void toInstituteInfoCollection(Collection entities) {
		
	}

	public void toInstituteSecurity(Institute sourceEntity, InstituteSecurity targetVO) {
		
	}

	public InstituteSecurity toInstituteSecurity(Institute entity) {
		return null;
	}

	public void toInstituteSecurityCollection(Collection entities) {
		
	}

	public Institute create(String name, String shortName, String shortcut, String description, String ownerName,
			String address, String postcode, String city, String country, String telephone, String telefax,
			String website, String email, String locale, String theme, Long imageId, boolean enabled) {
		return null;
	}

	public Object create(int transform, String name, String shortName, String shortcut, String description,
			String ownerName, String address, String postcode, String city, String country, String telephone,
			String telefax, String website, String email, String locale, String theme, Long imageId, boolean enabled) {
		return null;
	}



}
