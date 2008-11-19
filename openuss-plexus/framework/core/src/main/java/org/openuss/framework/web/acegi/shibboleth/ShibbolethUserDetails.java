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

	package org.openuss.framework.web.acegi.shibboleth;
	
	import org.acegisecurity.userdetails.UserDetails;
	import javax.naming.directory.Attributes;

	/**
	 * Captures the information for a user presented by Shibboleth.
	 * Inspired by <code>org.acegisecurity.userdetails.ldap.LdapUserDetails</code>
	 * 
	 * @author Peter Schuh
	 * @version $Id$
	 */
	
	public interface ShibbolethUserDetails extends UserDetails {

	    //~ Methods ========================================================================================================

	    /**
	     * The attributes for the user's entry in the directory (or a subset of them, depending on what was
	     * retrieved from the directory)
	     *
	     * @return the user's attributes, or an empty array if none were obtained, never null.
	     */
	    Attributes getAttributes();

	}