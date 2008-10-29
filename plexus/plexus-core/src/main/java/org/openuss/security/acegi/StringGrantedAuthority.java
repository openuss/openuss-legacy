package org.openuss.security.acegi;

import org.acegisecurity.GrantedAuthority;
import org.apache.commons.lang.Validate;

public class StringGrantedAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = -5447868136485027973L;
	
	private String authority;
	
	public StringGrantedAuthority(String authority) {
		Validate.notNull(authority, "Parameter authority must not be null!");
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GrantedAuthority) {
			return super.equals(obj);
		}
		return authority.equals(obj);
	}

	@Override
	public int hashCode() {
		return authority.hashCode();
	}

	@Override
	public String toString() {
		return authority;
	}
}
