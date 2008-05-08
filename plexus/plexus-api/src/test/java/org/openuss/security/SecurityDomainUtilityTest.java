package org.openuss.security;

import junit.framework.TestCase;

public class SecurityDomainUtilityTest extends TestCase {

	public void testToUsername() {
		assertEquals("domain\\username", SecurityDomainUtility.toUsername("domain", "username"));
	}

	public void testExtractDomain() {
		assertEquals("domain",SecurityDomainUtility.extractDomain("domain\\username"));
		assertEquals("", SecurityDomainUtility.extractDomain("domain$username"));
		assertEquals("", SecurityDomainUtility.extractDomain("username"));
	}

	public void testExtractUsername() {
		assertEquals("username",SecurityDomainUtility.extractUsername("domain\\username"));
		assertEquals("domain$username", SecurityDomainUtility.extractUsername("domain$username"));
		assertEquals("", SecurityDomainUtility.extractUsername("domain\\"));
		assertEquals("username", SecurityDomainUtility.extractUsername("username"));
	}

}
