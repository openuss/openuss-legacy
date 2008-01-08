package org.openuss.autogroups;

import java.util.List;

public class Person {
	
	public Long id;
	
	public List<Group> groupWishes;

	public List<Group> getGroupWishes() {
		return groupWishes;
	}

	public void setGroupWishes(List<Group> groupWishes) {
		this.groupWishes = groupWishes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
