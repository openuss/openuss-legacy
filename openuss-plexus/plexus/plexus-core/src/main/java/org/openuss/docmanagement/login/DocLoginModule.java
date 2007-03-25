package org.openuss.docmanagement.login;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.jackrabbit.core.security.CredentialsCallback;
import org.apache.jackrabbit.core.security.SecurityConstants;
import org.apache.jackrabbit.core.security.UserPrincipal;
import org.apache.log4j.Logger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A <code>SimpleLoginModule</code> ...
 */
public class DocLoginModule implements LoginModule {

	public static final Logger logger = Logger.getLogger(DocLoginModule.class);
	
    /**
     * Name of the anonymous user id option in the LoginModule configuration
     */
    private static final String OPT_ANONYMOUS = "anonymousId";

    /**
     * Name of the default user id option in the LoginModule configuration
     */
    private static final String OPT_DEFAULT = "defaultUserId";

    /**
     * The default user id for anonymous login
     */
    private static final String DEFAULT_ANONYMOUS_ID = "anonymous";

    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;

    // configurable options
    //private boolean someOpt = false;

    // local authentication state:
    // the principals, i.e. the authenticated identities
    private final Set principals = new HashSet();

    /**
     * Id of an anonymous user login
     */
    private String anonymousUserId = DEFAULT_ANONYMOUS_ID;

    /**
     * The default user id. Only used when not <code>null</code>.
     */
    private String defaultUserId = null;

    /**
     * Constructor
     */
    public DocLoginModule() {
    }

    /**
     * Returns the anonymous user id.
     *
     * @return anonymous user id
     */
    public String getAnonymousId() {
        return anonymousUserId;
    }

    /**
     * Sets the anonymous user id.
     *
     * @param anonymousId anonymous user id
     */
    public void setAnonymousId(String anonymousId) {
        anonymousUserId = anonymousId;
    }

    /**
     * Returns the default user id.
     *
     * @return default user id
     */
    public String getDefaultUserId() {
        return defaultUserId;
    }

    /**
     * Sets the default user id to be used when no login credentials
     * are presented.
     *
     * @param defaultUserId default user id
     */
    public void setDefaultUserId(String defaultUserId) {
        this.defaultUserId = defaultUserId;
    }

    //----------------------------------------------------------< LoginModule >
    /**
     * {@inheritDoc}
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map sharedState, Map options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;

        // initialize any configured options
        //someOpt = "true".equalsIgnoreCase((String)options.get("someOpt"));
        String userId = (String) options.get(OPT_ANONYMOUS);
        if (userId != null) {
            anonymousUserId = userId;
        }
        if (options.containsKey(OPT_DEFAULT)) {
            defaultUserId = (String) options.get(OPT_DEFAULT);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public boolean login() throws LoginException {
        // prompt for a user name and password
        if (callbackHandler == null) {
            throw new LoginException("no CallbackHandler available");
        }

        boolean authenticated = false;
        principals.clear();
        try {
            // Get credentials using a JAAS callback
            CredentialsCallback ccb = new CredentialsCallback();
            callbackHandler.handle(new Callback[] { ccb });
            Credentials creds = ccb.getCredentials();
            // Use the credentials to set up principals
            if (creds != null) {
                if (creds instanceof SimpleCredentials) {
                    SimpleCredentials sc = (SimpleCredentials) creds;
                    // authenticate

                    Object attr = sc.getAttribute(SecurityConstants.IMPERSONATOR_ATTRIBUTE);
                    authenticated = false;
                    if (attr != null && attr instanceof Subject) {
                    } else {
                        // username and password have to match the value specified in 
                    	// RepositoryAccess.java to work properly
                    	if (sc.getUserID().equals("username")) {
                        	logger.debug("user recognized");
                        	if((new String(sc.getPassword())).equals("password")) {
                        		logger.debug("password right");
                        		principals.add(new UserPrincipal(sc.getUserID()));
                        	}
                        		authenticated = true; 
                        	}
                        }
                    }
                }

        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException uce) {
            throw new LoginException(uce.getCallback().toString() + " not available");
        }

        if (authenticated) {
            return !principals.isEmpty();
        } else {
            // authentication failed: clean out state
            principals.clear();
            throw new FailedLoginException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public boolean commit() throws LoginException {
        if (principals.isEmpty()) {
            return false;
        } else {
            // add a principals (authenticated identities) to the Subject
            subject.getPrincipals().addAll(principals);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean abort() throws LoginException {
        if (principals.isEmpty()) {
            return false;
        } else {
            logout();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean logout() throws LoginException {
        subject.getPrincipals().removeAll(principals);
        principals.clear();
        return true;
    }
}
