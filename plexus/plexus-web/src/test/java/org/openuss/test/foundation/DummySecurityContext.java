package org.openuss.test.foundation;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.openuss.security.User;

/**
 * A trivial security context which automatically "authenticates" the user. 
 */
public class DummySecurityContext implements SecurityContext {
	private static final long serialVersionUID = -3426913277471941380L;
	/**
	 * The "current" user
	 */
	protected User currentUser;
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * Factory which allows to create a context already linked to a user.
	 * 
	 * @param currentUser The user who should be recognized as authenticated
	 * @return A new {@link DummySecurityContext} whose getCurrentUser() method returns currentUser.
	 */
	public static DummySecurityContext create(User currentUser) {
		DummySecurityContext res = new DummySecurityContext();
		res.setCurrentUser(currentUser);
		return res;
	}

	public Authentication getAuthentication() {
		return new Authentication() {

			/**
			 * If you're ever stupid enough to serialize this test class, the following value ensures this will never break. 
			 */
			private static final long serialVersionUID = -5297848740663190649L;

			public GrantedAuthority[] getAuthorities() {
				return null;
			}

			public Object getCredentials() {
				return null;
			}

			public Object getDetails() {
				return null;
			}

			public Object getPrincipal() {
				return null;
			}

			public boolean isAuthenticated() {
				return true;
			}

			public void setAuthenticated(boolean arg0)
					throws IllegalArgumentException {
				
			}

			public String getName() {
				return getCurrentUser().getName();
			}
					
		};
	}

	public void setAuthentication(Authentication arg0) {
		
	}
}