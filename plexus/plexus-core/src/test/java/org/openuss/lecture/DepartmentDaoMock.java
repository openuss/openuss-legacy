package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.Membership;

public class DepartmentDaoMock extends AbstractMockDao<Department> implements DepartmentDao {

	public Department create(boolean defaultDepartment, DepartmentType departmentType, boolean enabled,
			Membership membership, String name, String ownerName, String shortcut) {
		return null;
	}

	public Department create(DepartmentType departmentType, boolean defaultDepartment, String shortcut, String name,
			String description, String ownerName, String address, String postcode, String city, String country,
			String telephone, String telefax, String website, String email, String locale, String theme, Long imageId,
			boolean enabled) {
		return null;
	}

	public Object create(int transform, boolean defaultDepartment, DepartmentType departmentType, boolean enabled,
			Membership membership, String name, String ownerName, String shortcut) {
		return null;
	}

	public Object create(int transform, DepartmentType departmentType, boolean defaultDepartment, String shortcut,
			String name, String description, String ownerName, String address, String postcode, String city,
			String country, String telephone, String telefax, String website, String email, String locale,
			String theme, Long imageId, boolean enabled) {
		return null;
	}

	public void departmentInfoToEntity(DepartmentInfo sourceVO, Department targetEntity, boolean copyIfNull) {
		
	}

	public Department departmentInfoToEntity(DepartmentInfo departmentInfo) {
		return null;
	}

	public void departmentInfoToEntityCollection(Collection instances) {
		
	}

	public List findByEnabled(boolean enabled) {
		return null;
	}

	public List findByEnabled(int transform, boolean enabled) {
		return null;
	}

	public List findByEnabled(int transform, String queryString, boolean enabled) {
		return null;
	}

	public List findByEnabled(String queryString, boolean enabled) {
		return null;
	}

	public Object findByShortcut(int transform, String queryString, String shortcut) {
		return null;
	}

	public Object findByShortcut(int transform, String shortcut) {
		return null;
	}

	public Department findByShortcut(String queryString, String shortcut) {
		return null;
	}

	public Department findByShortcut(String shortcut) {
		return null;
	}

	public List findByType(DepartmentType type) {
		return null;
	}

	public List findByType(int transform, DepartmentType type) {
		return null;
	}

	public List findByType(int transform, String queryString, DepartmentType type) {
		return null;
	}

	public List findByType(String queryString, DepartmentType type) {
		return null;
	}

	public List findByUniversity(int transform, String queryString, University university) {
		return null;
	}

	public List findByUniversity(int transform, University university) {
		return null;
	}

	public List findByUniversity(String queryString, University university) {
		return null;
	}

	public List findByUniversity(University university) {
		return null;
	}

	public Object findByUniversityAndDefault(int transform, String queryString, University university,
			boolean defaultDepartment) {
		return null;
	}

	public Object findByUniversityAndDefault(int transform, University university, boolean defaultDepartment) {
		return null;
	}

	public Department findByUniversityAndDefault(String queryString, University university, boolean defaultDepartment) {
		return null;
	}

	public Department findByUniversityAndDefault(University university, boolean defaultDepartment) {
		return null;
	}

	public List findByUniversityAndEnabled(int transform, String queryString, University university, boolean enabled) {
		return null;
	}

	public List findByUniversityAndEnabled(int transform, University university, boolean enabled) {
		return null;
	}

	public List findByUniversityAndEnabled(String queryString, University university, boolean enabled) {
		return null;
	}

	public List findByUniversityAndEnabled(University university, boolean enabled) {
		return null;
	}

	public List findByUniversityAndType(int transform, String queryString, University university,
			DepartmentType departmentType) {
		return null;
	}

	public List findByUniversityAndType(int transform, University university, DepartmentType departmentType) {
		return null;
	}

	public List findByUniversityAndType(String queryString, University university, DepartmentType departmentType) {
		return null;
	}

	public List findByUniversityAndType(University university, DepartmentType departmentType) {
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(int transform, String queryString, University university,
			DepartmentType departmentType, boolean enabled) {
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(int transform, University university, DepartmentType departmentType,
			boolean enabled) {
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(String queryString, University university,
			DepartmentType departmentType, boolean enabled) {
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(University university, DepartmentType departmentType, boolean enabled) {
		return null;
	}

	public void toDepartmentInfo(Department sourceEntity, DepartmentInfo targetVO) {
		
	}

	public DepartmentInfo toDepartmentInfo(Department entity) {
		return null;
	}

	public void toDepartmentInfoCollection(Collection entities) {
		
	}

	public Department create(DepartmentType departmentType, boolean defaultDepartment, String name, String shortName,
			String shortcut, String description, String ownerName, String address, String postcode, String city,
			String country, String telephone, String telefax, String website, String email, String locale,
			String theme, Long imageId, boolean enabled) {
		return null;
	}

	public Object create(int transform, DepartmentType departmentType, boolean defaultDepartment, String name,
			String shortName, String shortcut, String description, String ownerName, String address, String postcode,
			String city, String country, String telephone, String telefax, String website, String email, String locale,
			String theme, Long imageId, boolean enabled) {
		return null;
	}

	
}
