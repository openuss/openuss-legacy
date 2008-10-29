package org.openuss.security.acegi.shibboleth;

import org.springframework.util.Assert;

/**
 * @author Peter Schuh
 *
 */
public class PlexusShibbolethAuthenticationProcessingFilter extends ShibbolethAuthenticationProcessingFilter {
		
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(getDefaultDomainName(), "defaultDomainName must be specified");
		Assert.notNull(getDefaultDomainId(), "defaultDomainId must be specified");
	}

}
