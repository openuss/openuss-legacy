package org.openuss.web.security.converter;

import junit.framework.TestCase;

import org.openuss.lecture.InstituteGroup;
import org.openuss.security.SecurityConstants;
import org.openuss.web.lecture.InstituteGroupConverter;

public class DeleteDomainNameFromUsernameConverterTest extends TestCase {

	public void testAsObjectWithDelimiterContained() {
		String username = SecurityConstants.USERNAME_DOMAIN_DELIMITER+"exampledomainname"+SecurityConstants.USERNAME_DOMAIN_DELIMITER+"tester";
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = (String) converter.getAsObject(null, null, username);
		assertTrue(clearedUsername.equals("tester"));		
	}
	
	public void testAsStringWithDelimiterContained() {
		String username = SecurityConstants.USERNAME_DOMAIN_DELIMITER+"exampledomainname"+SecurityConstants.USERNAME_DOMAIN_DELIMITER+"tester";
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = converter.getAsString(null, null, username);
		assertTrue(clearedUsername.equals("tester"));
	}
	
	public void testAsObjectWithoutDelimiterContained() {
		String username = "tester";
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = (String) converter.getAsObject(null, null, username);
		assertTrue(clearedUsername.equals(username));		
	}
	
	public void testAsStringWithoutDelimiterContained() {
		String username = "tester";
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = converter.getAsString(null, null, username);
		assertTrue(clearedUsername.equals(username));
	}
	
	public void testAsObjectWithNullValue() {
		String username = null;
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = (String) converter.getAsObject(null, null, username);
		assertNull(clearedUsername);		
	}
	
	public void testAsStringWithNullValue() {
		String username = null;
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = converter.getAsString(null, null, username);
		assertNull(clearedUsername);
	}
	
	public void testAsObjectWithEmptyValue() {
		String username = "";
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = (String) converter.getAsObject(null, null, username);
		assertTrue(clearedUsername.equals(username));		
	}
	
	public void testAsStringWithEmptyValue() {
		String username = "";
		DeleteDomainNameFromUsernameConverter converter = new DeleteDomainNameFromUsernameConverter();
		String clearedUsername = converter.getAsString(null, null, username);
		assertTrue(clearedUsername.equals(username));
	}
}
