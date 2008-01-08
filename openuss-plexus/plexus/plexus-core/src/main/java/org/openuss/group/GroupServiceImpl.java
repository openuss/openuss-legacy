// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openuss.autogroups.Assigner;
import org.openuss.autogroups.Group;
import org.openuss.autogroups.Person;
import org.openuss.security.User;


/*
 * @see org.openuss.group.GroupService
 */
public class GroupServiceImpl
    extends org.openuss.group.GroupServiceBase
{

    /**
     * @see org.openuss.group.GroupService#getGroups(java.lang.Long)
     */
    protected java.util.List handleGetGroups(java.lang.Long domainId)
        throws java.lang.Exception
    {
    	GroupContainer groupContainer = getGroupContainer(domainId);
    	Set<WorkingGroup> groups = groupContainer.getGroups();
    	List<WorkingGroupInfo> groupInfos = new ArrayList<WorkingGroupInfo>();
    	for (WorkingGroup workingGroup : groups){
    		groupInfos.add(getWorkingGroupDao().toWorkingGroupInfo(workingGroup));
    	}
    	return groupInfos;
    }

    /**
     * @see org.openuss.group.GroupService#getAdminInfo(java.lang.Long)
     */
    protected org.openuss.group.GroupAdminInformationInfo handleGetAdminInfo(java.lang.Long domainId)
        throws java.lang.Exception
    {
    	GroupContainer groupContainer = getGroupContainer(domainId);
    	return getGroupAdminInformationDao().toGroupAdminInformationInfo(groupContainer.getAdminInfo());
    }

    /**
     * @see org.openuss.group.GroupService#getMembers(org.openuss.group.WorkingGroupInfo)
     */
    protected java.util.List handleGetMembers(org.openuss.group.WorkingGroupInfo groupInfo)
        throws java.lang.Exception
    {
    	if (groupInfo==null|groupInfo.getId()==null){
    		return new ArrayList<GroupMemberInfo>();
    	}
    	WorkingGroup group = getWorkingGroupDao().findById(groupInfo.getId());
    	List<GroupMemberInfo> groupMembers = new ArrayList<GroupMemberInfo>();
    	if (group.getMembers()!=null){
    		for (GroupMember gm : group.getMembers()){
    			groupMembers.add(getGroupMemberDao().toGroupMemberInfo(gm));
    		}
    	}
    	return groupMembers;
    }

    /**
     * @see org.openuss.group.GroupService#getAspirants(org.openuss.group.WorkingGroupInfo)
     */
    protected java.util.List handleGetAspirants(org.openuss.group.WorkingGroupInfo groupInfo)
        throws java.lang.Exception
    {
    	WorkingGroup group = getWorkingGroupDao().findById(groupInfo.getId());
    	List<GroupMemberInfo> groupAspirants = new ArrayList<GroupMemberInfo>();
    	for (GroupWish gw : group.getAspirants()){
    		groupAspirants.add(getGroupMemberDao().toGroupMemberInfo(gw.getGroupMember()));
    	}
    	return groupAspirants;
    }

    /**
     * @see org.openuss.group.GroupService#getCustomParameters(org.openuss.group.GroupAdminInformationInfo)
     */
    protected java.util.List handleGetCustomParameters(org.openuss.group.GroupAdminInformationInfo groupAdminInfo)
        throws java.lang.Exception
    {
    	List<CustomInfoInfo> custom = new ArrayList<CustomInfoInfo>();
    	for(CustomInfo ci : getGroupAdminInformationDao().findById(groupAdminInfo.getId()).getCustomInfo()){
    		custom.add(getCustomInfoDao().toCustomInfoInfo(ci));
    	}
    	return custom;
    }

    /**
     * @see org.openuss.group.GroupService#getCustomMemberInfo(org.openuss.group.GroupMemberInfo)
     */
    protected java.util.List handleGetCustomMemberInfo(org.openuss.group.GroupMemberInfo groupMemberInfo)
        throws java.lang.Exception
    {
    	GroupMember member = getGroupMemberDao().findById(groupMemberInfo.getId());    	
    	List<CustomInfoValueInfo> custom = new ArrayList<CustomInfoValueInfo>(); 
    	for (CustomInfoValue ci:member.getCustomValues()){
    		custom.add(getCustomInfoValueDao().toCustomInfoValueInfo(ci));
    	}
    	return custom;
    }

	@Override
	protected void handleAddCustomInfo(String customName, Long domainId)
			throws Exception {
		CustomInfo customInfo = CustomInfo.Factory.newInstance();
		customInfo.setCustomName(customName);		
		getCustomInfoDao().create(customInfo);
		
		GroupContainer groupContainer = getGroupContainer(domainId);
		GroupAdminInformation adminInfo = groupContainer.getAdminInfo();
		Set<CustomInfo> custom = adminInfo.getCustomInfo();
		if (custom!=null){
			custom.add(customInfo);
		} else if (custom==null){
			custom = new HashSet<CustomInfo>();
			custom.add(customInfo);
		}
		adminInfo.setCustomInfo(custom);
		getGroupAdminInformationDao().update(adminInfo);		
	}

	@Override
	protected void handleAddGroup(WorkingGroupInfo group, Long domainId)
			throws Exception {
		GroupContainer groupContainer = getGroupContainer(domainId);
		WorkingGroup groupEntity = getWorkingGroupDao().workingGroupInfoToEntity(group);
		getWorkingGroupDao().create(groupEntity);
		Set<WorkingGroup> groups = groupContainer.getGroups();
		groups.add(groupEntity);
		groupContainer.setGroups(groups);
		getGroupContainerDao().update(groupContainer);
	}

	@Override
	protected void handleAspire(List groupWishes) throws Exception {
		User current = getSecurityService().getUserObject((getSecurityService().getCurrentUser()));
		GroupMember member = getGroupMemberDao().findByUser(current);
		if (member == null){
			member = GroupMember.Factory.newInstance();
			member.setUser(current);
			getGroupMemberDao().create(member);
		}
		if (member.getGroupWish()!=null && member.getGroupWish().size()>0){
			Set<GroupWish> wishesToDelete = member.getGroupWish();
			member.setGroupWish(null);
			getGroupWishDao().remove(wishesToDelete);
			getGroupMemberDao().update(member);
			
		}
		List<GroupWishInfo> wishes = (List<GroupWishInfo>) groupWishes;
		List<GroupWish> wishObjects = new ArrayList<GroupWish>();
		for (GroupWishInfo wish: wishes){
			GroupWish groupWishObject = GroupWish.Factory.newInstance();
			groupWishObject.setGroup(getWorkingGroupDao().findById(wish.getGroupId()));
			groupWishObject.setWeight(wish.getWeight());
			groupWishObject.setGroupMember(member);
			wishObjects.add(groupWishObject);
		}
		getGroupWishDao().create(wishObjects);
		getGroupMemberDao().update(member);
	}

	@Override
	protected void handleAssignAspirants(Long domainId) throws Exception {
		//load entities
		GroupContainer groupContainer = getGroupContainer(domainId);
		Set<GroupMember> memberList = getGroupAspirants(groupContainer);

		//convert entities
		List<Group> groups = workingGroup2Group(groupContainer.getGroups());
		List<Person> persons = groupMember2Person(memberList, groups);
		
		//assign
		Assigner assigner = new Assigner();	
		assigner.assign((ArrayList<Group>)groups, (ArrayList<Person>)persons);
		
		//reconvert entities
		for (Group group:groups){			
			List<Person> members = group.getMembers();
			if (members!=null){
				WorkingGroup workingGroup = getWorkingGroupById(groupContainer, group.getId());
				Set<GroupMember> groupMembers = new HashSet<GroupMember>();
				for (Person person:members){
					GroupMember groupMember = getGroupMemberByUserId(memberList, person.getId());
					groupMember.setGroup(workingGroup);
					getGroupMemberDao().update(groupMember);
					groupMembers.add(groupMember);
				}
				workingGroup.setMembers(groupMembers);
				//save assigning
				getWorkingGroupDao().update(workingGroup);
			}
		}
	}
	
	private GroupMember getGroupMemberByUserId(Set<GroupMember> memberList, Long id){
		for (GroupMember member:memberList){
			if (member.getUser().getId().compareTo(id)==0) {
				return member;
			}			
		}
		return null;
	}
	
	private WorkingGroup getWorkingGroupById(GroupContainer groupContainer, Long id){
		Set<WorkingGroup> groups = groupContainer.getGroups();
		for (WorkingGroup group:groups){
			if (group.getId().compareTo(id)==0){
				return group;				
			}
		}
		return null;
	}
	
	private Set<GroupMember> getGroupAspirants(GroupContainer container){
		Set<WorkingGroup> groups = container.getGroups();
		Set<GroupMember> members = new HashSet<GroupMember>();
		for (WorkingGroup group:groups){
			Set<GroupWish> aspirants = group.getAspirants();
			for (GroupWish wish : aspirants){
				members.add(wish.getGroupMember());
			}
		}
		return members;
	}
	private List<Group> workingGroup2Group(Set<WorkingGroup> workingGroups){
		List<Group> groups = new ArrayList<Group>(); 
		for (WorkingGroup workingGroup: workingGroups){
			Group group = new Group();			
			group.setSize(workingGroup.getMemberCount());
			group.setId(workingGroup.getId());
			groups.add(group);
		}
		return groups;
	}
	
	private List<Person> groupMember2Person (Set<GroupMember> groupMembers, List<Group> groups){
		List<Person> persons = new ArrayList<Person>();
		for  (GroupMember member: groupMembers){
			Person person = new Person();
			List<Group> wishes = new ArrayList<Group>();
			for (GroupWish wish: member.getGroupWish()){
				wishes.add(getGroupById(wish.getGroup().getId(), groups));
			}
			person.setGroupWishes(wishes);
			person.setId(member.getUser().getId());
			persons.add(person);
		}
		return persons;
	}
	
	private Group getGroupById(Long id, List<Group> groups){
		for (Group group: groups){
			if (group.getId().compareTo(id)==0) {
				return group;
			}
		}
		return null;
	}

	@Override
	protected void handleChangeGroup(WorkingGroupInfo group) throws Exception {
		getWorkingGroupDao().update(getWorkingGroupDao().workingGroupInfoToEntity(group));		
	}

	@Override
	protected void handleClearGroups(Long domainId) throws Exception {
		GroupContainer gc = getGroupContainer(domainId);
		for (WorkingGroup group : gc.getGroups()){
			group.setMembers(null);
			getWorkingGroupDao().update(group);
		}	
		Set<GroupMember> members = getGroupAspirants(gc);
		for (GroupMember member:members){
			member.setGroup(null);
			getGroupMemberDao().update(member);			
		}
		
	}

	@Override
	protected void handleDeleteGroup(WorkingGroupInfo group) throws Exception {
		WorkingGroup groupObject = getWorkingGroupDao().load(group.getId());
		getGroupWishDao().remove(groupObject.getAspirants());
		groupObject.setMembers(null);
		getWorkingGroupDao().update(groupObject);
		getWorkingGroupDao().remove(group.getId());		
	}

	@Override
	protected void handleMoveMember(GroupMemberInfo groupMember,
			WorkingGroupInfo sourceGroup, WorkingGroupInfo targetGroup)
			throws Exception {
		WorkingGroup source = getWorkingGroupDao().findById(sourceGroup.getId());
		WorkingGroup target = getWorkingGroupDao().findById(targetGroup.getId());
		Set<GroupMember> members = source.getMembers();
		GroupMember changer = getGroupMemberDao().findById(groupMember.getId());
		members.remove(changer);
		source.setMembers(members);
		
		Set<GroupMember> targetMembers = target.getMembers();
		targetMembers.add(changer);
		target.setMembers(targetMembers);
		
		changer.setGroup(target);
		
		getWorkingGroupDao().update(target);
		getWorkingGroupDao().update(source);
		getGroupMemberDao().update(changer);
		
		
		
	}

	@Override
	protected void handleSetAdminInfo(GroupAdminInformationInfo adminInfo,
			Long domainId) throws Exception {
    	if (adminInfo.getId()!=null){
    		getGroupAdminInformationDao().groupAdminInformationInfoToEntity(adminInfo);
    	}
		GroupContainer groupContainer = getGroupContainer(domainId);
		GroupAdminInformation ga = getGroupAdminInformationDao().groupAdminInformationInfoToEntity(adminInfo);
		getGroupAdminInformationDao().create(ga);
		groupContainer.setAdminInfo(ga);
		
		
	}

	private GroupContainer getGroupContainer(Long domainId) {
		GroupContainer groupContainer = getGroupContainerDao().findByDomainId(domainId);
		if (groupContainer==null){
			GroupAdminInformation adminInfo = GroupAdminInformation.Factory.newInstance();
			adminInfo.setCustomInfo(null);
			adminInfo.setDeadline(new Date(System.currentTimeMillis()));
			adminInfo.setPrefCount(3);
			adminInfo.setSemester(false);
			adminInfo.setStudies(false);
			adminInfo.setMatNr(false);
			getGroupAdminInformationDao().create(adminInfo);
			
			groupContainer = GroupContainer.Factory.newInstance();
			groupContainer.setDomainId(domainId);
			groupContainer.setAdminInfo(adminInfo);
			getGroupContainerDao().create(groupContainer);
			
		}
		return groupContainer;
	}

	@Override
	protected void handleInitGroup(Long domainId)
	throws Exception {
		getGroupContainer(domainId);
	}
	
	@Override
	protected void handleDeleteCustomInfo(CustomInfoInfo customInfo)
			throws Exception {
		getCustomInfoDao().remove(customInfo.getId());		
	}

	@Override
	protected void handleSaveCustomValues(User user, List customValues)
			throws Exception {
		CustomInfoValueInfo civi;
		for (Object o : customValues){
			civi = (CustomInfoValueInfo) o;
			CustomInfoValue civ = getCustomInfoValueDao().customInfoValueInfoToEntity(civi);
			civ.setCustomInfo(getCustomInfoDao().load(civi.getCustomNameId()));
			civ.setUser(getGroupMemberDao().findByUser(user));
			getCustomInfoValueDao().create(civ);
		}
	}

	@Override
	protected WorkingGroupInfo handleGetGroup(Long groupId) throws Exception {
		return getWorkingGroupDao().toWorkingGroupInfo(getWorkingGroupDao().load(groupId));
	}

	@Override
	protected GroupMemberInfo handleGetGroupMember(User user) throws Exception {
		GroupMember groupMember = getGroupMemberDao().findByUser(getSecurityService().getUserObject(getSecurityService().getCurrentUser()));
		if (groupMember == null){
			return new GroupMemberInfo();
		}
		GroupMemberInfo gmi = new GroupMemberInfo();
		gmi = getGroupMemberDao().toGroupMemberInfo(groupMember);
		gmi.setAspired(groupMember.getGroupWish()!=null&&groupMember.getGroupWish().size()!=0);
		return gmi;
	}

	@Override
	protected List handleGetFilteredAspirants(WorkingGroupInfo group)
			throws Exception {
    	WorkingGroup workingGroup = getWorkingGroupDao().findById(group.getId());
    	List<GroupMemberInfo> groupAspirants = new ArrayList<GroupMemberInfo>();
    	for (GroupWish gw : workingGroup.getAspirants()){
    		if (gw.getWeight().intValue()==0){
    			groupAspirants.add(getGroupMemberDao().toGroupMemberInfo(gw.getGroupMember()));
    		}
    	}
    	return groupAspirants;	}

}