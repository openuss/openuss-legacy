// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.User;

/**
 * @see org.openuss.lecture.Institute
 * @author Ingo Dueppe, Ron Haus
 */
public class InstituteDaoImpl extends InstituteDaoBase {

	/**
	 * @see org.openuss.lecture.InstituteDao#toInstituteInfo(org.openuss.lecture.Institute,
	 *      org.openuss.lecture.InstituteInfo)
	 */
	public void toInstituteInfo(Institute sourceEntity, InstituteInfo targetVO) {
		super.toInstituteInfo(sourceEntity, targetVO);
		
		if (sourceEntity.getDepartment() != null) {
			targetVO.setDepartmentId(sourceEntity.getDepartment().getId());
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object from the object store. If no such
	 * entity object exists in the object store, a new, blank entity is created
	 */
	private Institute loadInstituteFromInstituteInfo(InstituteInfo instituteInfo) {

		Institute institute = Institute.Factory.newInstance();
		if (instituteInfo.getId() != null) {
			institute = this.load(instituteInfo.getId());
		}
		return institute;
	}

	/**
	 * @see org.openuss.lecture.InstituteDao#instituteInfoToEntity(org.openuss.lecture.InstituteInfo)
	 */
	public Institute instituteInfoToEntity(InstituteInfo instituteInfo) {
		Institute entity = this.loadInstituteFromInstituteInfo(instituteInfo);
		instituteInfoToEntity(instituteInfo, entity, true);
		if (instituteInfo.getDepartmentId() != null) {
			if (entity.getDepartment() == null || !instituteInfo.getDepartmentId().equals(entity.getDepartment().getId())) 
				entity.setDepartment(getDepartmentDao().load(instituteInfo.getDepartmentId()));
		}
		return entity;
	}

	public Institute instituteSecurityToEntity(InstituteSecurity instituteSecurity) {
		logger.error("instituteSecurityToEntity is not supported!");
		return null;
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	public void toInstituteSecurity(Institute institute, InstituteSecurity target) {
		Map<Long, InstituteMember> members = new HashMap<Long, InstituteMember>();
		List<InstituteGroup> groups = new ArrayList<InstituteGroup>();
		// analyse groups
		traverseGroups(institute, members, groups);
		// analyse members that are not in any group yet.
		traverseAdditionalMembers(institute, members);
		// add all found groups and members to institutesecurity
		target.setGroups(groups);
		target.setMembers(new ArrayList(members.values()));
	}

	private void traverseAdditionalMembers(Institute institute, Map<Long, InstituteMember> members) {
		for (Authority authority : institute.getMembership().getMembers()) {
			if (authority instanceof User && !members.containsKey(authority.getId())) {
				InstituteMember member = new InstituteMember();
				member.setGroups(new ArrayList<InstituteGroup>());
				toInstituteMember((User) authority, member, institute);
				members.put(member.getId(), member);
			}
		}
	}

	@SuppressWarnings( { "unchecked" })
	private void traverseGroups(Institute institute, Map<Long, InstituteMember> members, Collection groups) {
		for (Group group : institute.getMembership().getGroups()) {
			InstituteGroup instituteGroup = groupToInstituteGroup(group);
			groups.add(instituteGroup);

			for (Authority authority : group.getMembers()) {
				if (authority instanceof User) {
					InstituteMember member = null;
					if (!members.containsKey(authority.getId())) {
						member = new InstituteMember();
						member.setGroups(new ArrayList<InstituteGroup>());
						toInstituteMember((User) authority, member, institute);
						members.put(member.getId(), member);
					} else {
						member = members.get(authority.getId());
					}
					member.getGroups().add(instituteGroup);
					if (!instituteGroup.getMembers().contains(member)){
						instituteGroup.getMembers().add(member);
					}
				} else if (authority instanceof Group) {
					logger.error("group members are not supported yet and will be ignored!");
				}
			}
		}
	}

	private InstituteGroup groupToInstituteGroup(Group group) {
		InstituteGroup instituteGroup = new InstituteGroup();
		instituteGroup.setMembers(new ArrayList<InstituteMember>());
		toInstituteGroup(group, instituteGroup);
		return instituteGroup;
	}

	private void toInstituteGroup(Group group, InstituteGroup fg) {
		fg.setId(group.getId());
		fg.setLabel(group.getLabel());
		fg.setName(group.getName());
	}

	private void toInstituteMember(User user, InstituteMember member, Institute institute) {
		member.setId(user.getId());
		member.setUsername(user.getUsername());
		member.setFirstName(user.getFirstName());
		member.setLastName(user.getLastName());
	}

}