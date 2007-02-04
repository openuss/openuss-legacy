package org.openuss.web.lecture;

import junit.framework.TestCase;

import org.openuss.lecture.FacultyGroup;

public class FacultyGroupConverterTest extends TestCase {
	
	public void testAsObject() {
		FacultyGroupConverter converter = new FacultyGroupConverter();
		FacultyGroup group = (FacultyGroup) converter.getAsObject(null, null, "12345:GROUP_NAME");
		assertTrue(group.getId() == 12345L);
		assertEquals("GROUP_NAME", group.getName());
	}
	
	public void testAsString() {
		FacultyGroupConverter converter = new FacultyGroupConverter();
		FacultyGroup group = new FacultyGroup();
		group.setId(12345L);
		group.setName("GROUP_NAME");
		String str = converter.getAsString(null,null,group);
		assertEquals("12345:GROUP_NAME",str);
	}

}
