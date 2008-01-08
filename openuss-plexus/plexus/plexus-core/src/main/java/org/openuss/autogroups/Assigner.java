package org.openuss.autogroups;

import java.util.ArrayList;
import java.util.Random;

public class Assigner {
	public ArrayList<Group> assign(ArrayList<Group> groups, ArrayList<Person> persons){
		//check validity of parameters
		if (groups == null||groups.size()==0){
			return null;
		}
		if (persons == null||persons.size()==0){
			return null;
		}		
		//determine max number of wishes 
		int maxWish = 0;
		for (Person person : persons){
			if (person.getGroupWishes().size()>maxWish){
				maxWish = person.getGroupWishes().size();				
				if (person.getGroupWishes().size()==0){
					return null;
				}
			}			
		}		
		
		//initial assignment --> all users get their wish group
		initGroups(persons);
		
		//randomize memberlists of all groups (to make assignment independent of time of application)
		for (Group group:groups){
			randomizeGroupMembers(group);
		}
		
		//loop all groups
		for (Group group : groups){
			//if group too full
			while (group.tooFull()){
				shrinkGroup(group);
			}
		}	
			
		return groups; 
	}
	
	private void randomizeGroupMembers(Group group){
		Random random = new Random();
		ArrayList<Person> persons = group.getMembers();
		int memberCount = group.getMemberCount();
		for (int i = 1; i < memberCount*2; i++){
				switchPersons(persons, random.nextInt(memberCount), random.nextInt(memberCount));
		}
		group.setMembers(persons);
	}	
	

	private void switchPersons(ArrayList<Person> persons, int person1,
			int person2) {
		Person p1 = persons.get(person1);
		Person p2 = persons.get(person2);
		persons.set(person2, p1);
		persons.set(person1, p2);
	}

	private void shrinkGroup(Group group){
		ArrayList<Person> members = group.getMembers();
		for (Person person : members){
			ArrayList<Group> wishes = (ArrayList<Group>) person.getGroupWishes();
			for (Group groupWish : wishes){
				if (!groupWish.isFull()){
					move(group, groupWish, person);
					return;
				}
			}
		}
		shrinkGroupHard(group);
	}
	
	private void shrinkGroupHard(Group group){
		ArrayList<Person> members = group.getMembers();
		Person person = members.get(0);
		for (Group groupWish : person.getGroupWishes()){
			if (!groupWish.equals(group)) {
				move(group, groupWish, person);
				return;
			}
		}
	}
	
	private void initGroups(ArrayList<Person> persons) {
		Group wishGroup;
		ArrayList<Person> members;
		for (Person person : persons){
			wishGroup = person.getGroupWishes().get(0);
			members = wishGroup.getMembers();
			if (members == null){
				members = new ArrayList<Person>();
			}
			members.add(person);
			wishGroup.setMembers(members);			
		}
	}
	
	public void move(Group sourceGroup, Group targetGroup, Person person){
		ArrayList<Person> sourceMembers =  sourceGroup.getMembers();
		sourceMembers.remove(person);
		ArrayList<Person> targetMembers =  targetGroup.getMembers();
		targetMembers.add(person);
	}
		
	public Group getOpenGroup(ArrayList<Group> groups){
		for (Group group: groups){
			if (!group.isFull()) return group;
		}
		return null;
	}
}
