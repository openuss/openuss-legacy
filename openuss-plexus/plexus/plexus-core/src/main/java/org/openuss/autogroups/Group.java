package org.openuss.autogroups;

import java.util.ArrayList;

public class Group {
	
	public Long id;
	
	public int size;
	
	public ArrayList<Person> members; 

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Person> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Person> members) {
		this.members = members;
	}
	
	public int getMemberCount(){
		if (members==null) {
			return 0;
		}
		return members.size();
	}
	
	public boolean isFull(){
		return (getMemberCount()>=getSize());
	}
	
	public boolean tooFull(){
		return (getMemberCount()>getSize());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
