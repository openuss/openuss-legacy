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
public class InstituteDaoImpl extends org.openuss.lecture.InstituteDaoBase {

	public Institute instituteInfoToEntity(InstituteInfo instituteInfo) {
		Institute institute = load(instituteInfo.getId());
		if (institute == null) {
			institute = Institute.Factory.newInstance();
		}
		instituteInfoToEntity(instituteInfo, institute, false);
		return institute;
	}

	public Institute instituteSecurityToEntity(InstituteSecurity instituteSecurity) {
		logger.error("instituteSecurityToEntity is not supported!");
		return null;
	}

	@Override
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
		for (Authority authority: institute.getMembership().getMembers()) {
			if (authority instanceof User && !members.containsKey(authority.getId())) {
				InstituteMember member = new InstituteMember();
				member.setGroups(new ArrayList<InstituteGroup>());
				toInstituteMember((User)authority, member, institute);
				members.put(member.getId(), member);
			}
		}
	}

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
						toInstituteMember((User)authority, member, institute);
						members.put(member.getId(), member);
					} else {
						member = members.get(authority.getId());
					}
					member.getGroups().add(instituteGroup);
					instituteGroup.getMembers().add(member);
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