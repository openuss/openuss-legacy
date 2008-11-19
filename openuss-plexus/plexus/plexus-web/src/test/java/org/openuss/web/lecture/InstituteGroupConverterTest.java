package org.openuss.web.lecture;

import junit.framework.TestCase;

import org.openuss.lecture.InstituteGroup;

public class InstituteGroupConverterTest extends TestCase {
	
	public void testAsObject() {
		final InstituteGroupConverter converter = new InstituteGroupConverter();
		InstituteGroup group = (InstituteGroup) converter.getAsObject(null, null, "12345:GROUP_NAME");
		assertTrue(group.getId() == 12345L);
		assertEquals("GROUP_NAME", group.getName());
	}
	
	public void testAsString() {
		InstituteGroupConverter converter = new InstituteGroupConverter();
		InstituteGroup group = new InstituteGroup();
		group.setId(12345L);
		group.setName("GROUP_NAME");
		String str = converter.getAsString(null,null,group);
		assertEquals("12345:GROUP_NAME",str);
	}

}
