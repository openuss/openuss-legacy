package org.openuss.autogroups;

import java.util.ArrayList;
import junit.framework.TestCase;


public class AutoGroupTest extends TestCase {	
	public void testMove(){
		Assigner assigner = new Assigner();
		Group source = new Group();
		Group target = new Group();
		Person person = new Person();
		ArrayList<Person> members = new ArrayList<Person>();
		members.add(person);
		source.setMembers(members);
		target.setMembers(new ArrayList<Person>());
		
		ArrayList<Person> sourceMembers;
		ArrayList<Person> targetMembers;
		
		assigner.move(source, target, person);
		
		sourceMembers = source.getMembers();
		targetMembers = target.getMembers();
		
		assertEquals(sourceMembers.size(), 0);		
		assertEquals(targetMembers.size(), 1);		
	}
	
	public void testAssign(){
		Group group1 = new Group();
		Group group2 = new Group();
		Group group3 = new Group();
		Group group4 = new Group();
		group1.setSize(1);
		group2.setSize(1);
		group3.setSize(1);
		group4.setSize(1);
		
		Person p1 = new Person();
		Person p2 = new Person();
		Person p3 = new Person();
		Person p4 = new Person();
		
		p1.setGroupWishes(new ArrayList<Group>()); 
		p2.setGroupWishes(new ArrayList<Group>()); 
		p3.setGroupWishes(new ArrayList<Group>()); 
		p4.setGroupWishes(new ArrayList<Group>());
		
		p1.getGroupWishes().add(group1);
		p2.getGroupWishes().add(group2);
		p3.getGroupWishes().add(group3);
		p4.getGroupWishes().add(group4);
		
		Assigner assigner = new Assigner();
		
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(group1);
		groups.add(group2);
		groups.add(group3);
		groups.add(group4);
		ArrayList<Person> persons = new ArrayList<Person>();
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.add(p4);
		
		assigner.assign(groups, persons);
		
		assertEquals(groups.get(0).getMemberCount(), 1);
		assertEquals(groups.get(1).getMemberCount(), 1);
		assertEquals(groups.get(2).getMemberCount(), 1);
		assertEquals(groups.get(3).getMemberCount(), 1);
	}
	
	public void testAssignComplex(){
		Group group1 = new Group();
		Group group2 = new Group();
		Group group3 = new Group();
		Group group4 = new Group();
		group1.setSize(0);
		group2.setSize(2);
		group3.setSize(1);
		group4.setSize(1);
		
		Person p1 = new Person();
		Person p2 = new Person();
		Person p3 = new Person();
		Person p4 = new Person();
		
		p1.setGroupWishes(new ArrayList<Group>()); 
		p2.setGroupWishes(new ArrayList<Group>()); 
		p3.setGroupWishes(new ArrayList<Group>()); 
		p4.setGroupWishes(new ArrayList<Group>());
		
		p1.getGroupWishes().add(group1);
		p1.getGroupWishes().add(group2);
		p2.getGroupWishes().add(group2);
		p3.getGroupWishes().add(group3);
		p4.getGroupWishes().add(group4);
		
		Assigner assigner = new Assigner();
		
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(group1);
		groups.add(group2);
		groups.add(group3);
		groups.add(group4);
		ArrayList<Person> persons = new ArrayList<Person>();
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.add(p4);
		
		assigner.assign(groups, persons);
		
		assertEquals(groups.get(0).getMemberCount(), 0);
		assertEquals(groups.get(1).getMemberCount(), 2);
		assertEquals(groups.get(2).getMemberCount(), 1);
		assertEquals(groups.get(3).getMemberCount(), 1);
	}
	
	
}
