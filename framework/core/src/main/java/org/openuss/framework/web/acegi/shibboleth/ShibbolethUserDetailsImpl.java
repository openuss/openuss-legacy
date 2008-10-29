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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.acegisecurity.GrantedAuthority;


/**
* A UserDetails implementation which is used internally by the Shibboleth services. It also contains the user's
* distinguished name and a set of attributes that have been retrieved from the Shibboleth Identity Provider.<p>An instance may be
* created when user information is retrieved during authentication.</p>
*  <p>An instance of this class will be used by the <tt>ShibbolethAuthenticationProcessingFilter</tt> to construct the final
* user details object that it returns.</p>
*
* Inspired by <code>org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl</code>
* @author Peter Schuh
* @version $Id$
*/
public class ShibbolethUserDetailsImpl implements ShibbolethUserDetails {
   //~ Static fields/initializers =====================================================================================

   private static final long serialVersionUID = 1L;
   private static final List<GrantedAuthority> NO_AUTHORITIES = new ArrayList<GrantedAuthority>();

   //~ Constants for Attribute IDs where to store user details ========================================================
   
   public static final String USERNAME_KEY = "ACEGI_SHIBBOLETH_USERNAME_KEY";
   public static final String FIRSTNAME_KEY = "ACEGI_SHIBBOLETH_FIRSTNAME_KEY";
   public static final String LASTNAME_KEY = "ACEGI_SHIBBOLETH_LASTNAME_KEY";
   public static final String EMAIL_KEY = "ACEGI_SHIBBOLETH_EMAIL_KEY";
   public static final String AUTHENTICATIONDOMAINID_KEY = "ACEGI_SHIBBOLETH_AUTHENTICATIONDOMAINID_KEY";
   public static final String AUTHENTICATIONDOMAINNAME_KEY = "ACEGI_SHIBBOLETH_AUTHENTICATIONDOMAINNAME_KEY";

   //~ Instance fields ================================================================================================

   private Attributes attributes = new BasicAttributes();
   private String password;
   private String username;
   private List<GrantedAuthority> authorities = NO_AUTHORITIES;
   private boolean accountNonExpired = true;
   private boolean accountNonLocked = true;
   private boolean credentialsNonExpired = true;
   private boolean enabled = true;

   //~ Constructors ===================================================================================================

   public ShibbolethUserDetailsImpl() {}

   //~ Methods ========================================================================================================

   public Attributes getAttributes() {
       return attributes;
   }

   public GrantedAuthority[] getAuthorities() {
       return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[0]);
   }

   public String getPassword() {
       return password;
   }

   public String getUsername() {
       return username;
   }

   public boolean isAccountNonExpired() {
       return accountNonExpired;
   }

   public boolean isAccountNonLocked() {
       return accountNonLocked;
   }

   public boolean isCredentialsNonExpired() {
       return credentialsNonExpired;
   }

   public boolean isEnabled() {
       return enabled;
   }

   public void addAuthority(GrantedAuthority a) {
       authorities.add(a);
   }

   public void setAccountNonExpired(boolean accountNonExpired) {
	   this.accountNonExpired = accountNonExpired;	   
   }
   
   public void setAccountNonLocked(boolean accountNonLocked) {
	   this.accountNonLocked = accountNonLocked;
   }
   
   public void setAttributes(Attributes attributes) {
	   this.attributes = attributes;
   }
   
   public void setAuthorities(GrantedAuthority[] authorities) {
	   this.authorities = new ArrayList<GrantedAuthority>(Arrays.asList(authorities));
   }
   
   public void setCredentialsNonExpired(boolean credentialsNonExpired) {
	   this.credentialsNonExpired = credentialsNonExpired;
   }
   
   public void setEnabled(boolean enabled) {
	   this.enabled = enabled;
   }
   
   public void setPassword(String password) {
	   this.password = password;
   }
   
   public void setUsername(String username) {
	   this.username = username;
   }
}