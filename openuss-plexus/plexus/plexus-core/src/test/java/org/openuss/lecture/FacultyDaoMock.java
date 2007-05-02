package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.User;

public class FacultyDaoMock extends AbstractMockDao<Faculty> implements FacultyDao {

	public Faculty create(String name, String shortcut, String ownername, String address, String postcode, String city, String telephone, String telefax, String website, String locale, String description, String email, String theme, boolean enabled) {
		return null;
	}

	public Object create(int transform, String name, String shortcut, String ownername, String address, String postcode, String city, String telephone, String telefax, String website, String locale, String description, String email, String theme, boolean enabled) {
		return null;
	}

	public Faculty create(boolean enabled, String name, User owner, String ownername, String shortcut) {
		return null;
	}

	public Object create(int transform, boolean enabled, String name, User owner, String ownername, String shortcut) {
		return null;
	}

	public void facultyDetailsToEntity(FacultyDetails sourceVO, Faculty targetEntity, boolean copyIfNull) {
	}

	public Faculty facultyDetailsToEntity(FacultyDetails facultyDetails) {
		return null;
	}

	public void facultyDetailsToEntityCollection(Collection instances) {
	}

	public void facultySecurityToEntity(FacultySecurity sourceVO, Faculty targetEntity, boolean copyIfNull) {
	}

	public Faculty facultySecurityToEntity(FacultySecurity facultySecurity) {
		return null;
	}

	public void facultySecurityToEntityCollection(Collection instances) {
	}

	public Faculty findByShortcut(String shortcut) {
		return null;
	}

	public Faculty findByShortcut(String queryString, String shortcut) {
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

	public void toFacultyDetails(Faculty sourceEntity, FacultyDetails targetVO) {
	}

	public FacultyDetails toFacultyDetails(Faculty entity) {
		return null;
	}

	public void toFacultyDetailsCollection(Collection entities) {
	}

	public void toFacultySecurity(Faculty sourceEntity, FacultySecurity targetVO) {
	}

	public FacultySecurity toFacultySecurity(Faculty entity) {
		return null;
	}

	public void toFacultySecurityCollection(Collection entities) {
	}


}
