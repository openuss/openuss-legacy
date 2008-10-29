/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openuss.security.acegi.ldap;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.acegisecurity.ldap.InitialDirContextFactory;
import org.acegisecurity.ldap.LdapCallback;
import org.acegisecurity.ldap.LdapEntryMapper;
import org.acegisecurity.ldap.LdapTemplate;
import org.acegisecurity.ldap.LdapUtils;
import org.springframework.util.Assert;

/**
 * LDAP equivalent of the Spring JdbcTemplate class.
 * <p>
 * This is mainly intended to simplify Ldap access within Acegi Security's
 * LDAP-related services. This template is used to access Microsoft Active
 * Directory services.
 * </p>
 * 
 * @author Ben Alex
 * @author Luke Taylor
 * @author Peter Schuh
 * 
 */
public class ActiveDirectoryLdapTemplate extends LdapTemplate {
	// ~ Static fields/initializers
	// =====================================================================================

	public static final String[] NO_ATTRS = new String[0];

	// ~ Constructors
	// ===================================================================================================

	public ActiveDirectoryLdapTemplate(InitialDirContextFactory dirContextFactory) {
		super(dirContextFactory);
	}

	/**
	 * 
	 * @param dirContextFactory
	 *            the source of DirContexts
	 * @param userDn
	 *            the user name to authenticate as when obtaining new contexts
	 * @param password
	 *            the user's password
	 */
	public ActiveDirectoryLdapTemplate(InitialDirContextFactory dirContextFactory, String userDn, String password) {
		super(dirContextFactory, userDn, password);
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 * Obtains the part of a DN relative to a supplied base context.
	 * <p>
	 * If the DN is "cn=bob,ou=people,dc=acegisecurity,dc=org" and the base
	 * context name is "ou=people,dc=acegisecurity,dc=org" it would return
	 * "cn=bob". This is a substitute for the implementation in
	 * <tt>LdapUtils</tt> for Active Directory. It uses
	 * <tt>String.indexOf()</tt> instead of <tt>String.lastIndexOf()</tt>.
	 * </p>
	 * 
	 * @param fullDn
	 *            the DN
	 * @param baseCtx
	 *            the context to work out the name relative to.
	 * 
	 * @return the
	 * 
	 * @throws NamingException
	 *             any exceptions thrown by the context are propagated.
	 */
	private String getRelativeName(String fullDn, Context baseCtx) throws NamingException {
		String baseDn = baseCtx.getNameInNamespace();

		if (baseDn.length() == 0) {
			return fullDn;
		}

		if (baseDn.equals(fullDn)) {
			return "";
		}
		// within LdapUtils class lastIndexOf() is used, but does not work.
		int index = fullDn.indexOf(baseDn);

		Assert.isTrue(index > 0, "Context base DN is not contained in the full DN");

		// remove the base name and preceding comma.
		return fullDn.substring(0, index - 1);
	}

	/**
	 * Performs an LDAP compare operation of the value of an attribute for a
	 * particular directory entry.
	 * 
	 * @param dn
	 *            the entry who's attribute is to be used
	 * @param attributeName
	 *            the attribute who's value we want to compare
	 * @param value
	 *            the value to be checked against the directory value
	 * 
	 * @return true if the supplied value matches that in the directory
	 */
	public boolean compare(final String dn, final String attributeName, final Object value) {
		final String comparisonFilter = "(" + attributeName + "={0})";

		class LdapCompareCallback implements LdapCallback {
			public Object doInDirContext(DirContext ctx) throws NamingException {
				SearchControls ctls = new SearchControls();
				ctls.setReturningAttributes(NO_ATTRS);
				ctls.setSearchScope(SearchControls.OBJECT_SCOPE);

				String relativeName = getRelativeName(dn, ctx);

				NamingEnumeration results = ctx.search(relativeName, comparisonFilter, new Object[] { value }, ctls);

				return Boolean.valueOf(results.hasMore());
			}
		}

		Boolean matches = (Boolean) execute(new LdapCompareCallback());

		return matches.booleanValue();
	}

	public boolean nameExists(final String dn) {
		Boolean exists = (Boolean) execute(new LdapCallback() {
			public Object doInDirContext(DirContext ctx) throws NamingException {
				try {
					Object obj = ctx.lookup(getRelativeName(dn, ctx));
					if (obj instanceof Context) {
						LdapUtils.closeContext((Context) obj);
					}

				} catch (NameNotFoundException nnfe) {
					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		});

		return exists.booleanValue();
	}

	/**
	 * Composes an object from the attributes of the given DN.
	 * 
	 * @param dn
	 *            the directory entry which will be read
	 * @param mapper
	 *            maps the attributes to the required object
	 * @param attributesToRetrieve
	 *            the named attributes which will be retrieved from the
	 *            directory entry.
	 * 
	 * @return the object created by the mapper
	 */
	public Object retrieveEntry(final String dn, final LdapEntryMapper mapper, final String[] attributesToRetrieve) {
		return execute(new LdapCallback() {
			public Object doInDirContext(DirContext ctx) throws NamingException {
				return mapper.mapAttributes(dn, ctx.getAttributes(getRelativeName(dn, ctx), attributesToRetrieve));
			}
		});
	}

}