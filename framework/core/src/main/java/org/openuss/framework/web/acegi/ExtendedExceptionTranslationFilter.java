package org.openuss.framework.web.acegi;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.InsufficientAuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.AccessDeniedHandler;
import org.acegisecurity.ui.AccessDeniedHandlerImpl;
import org.acegisecurity.ui.AuthenticationEntryPoint;
import org.acegisecurity.ui.ExceptionTranslationFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ExtendedExceptionTranslationFilter extends ExceptionTranslationFilter {

	protected static final Log logger = LogFactory.getLog(ExtendedExceptionTranslationFilter.class);
	protected AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
    protected AuthenticationEntryPoint migrationEntryPoint = null;
	   
    protected void handleException(ServletRequest request, ServletResponse response, FilterChain chain,
            AcegiSecurityException exception) throws IOException, ServletException {
            if (exception instanceof AuthenticationException) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Authentication exception occurred; redirecting to authentication entry point", exception);
                }

                sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
            } else if (exception instanceof AccessDeniedException) {
                if (getAuthenticationTrustResolver().isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Access is denied (user is anonymous); redirecting to authentication entry point",
                            exception);
                    }

                    sendStartAuthentication(request, response, chain,
                        new InsufficientAuthenticationException("Full authentication is required to access this resource"));
                } else {
                	// SendStartMigration, if migration is enabled and necessary.
                	if (isMigrationEnabled() && hasToMigrate(request)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Access is denied (user must migrate); redirecting to migration entry point",
                                exception);
                        }

                        sendStartMigration(request, response, chain,
                            new InsufficientAuthenticationException("User must migrate before accessing this resource"));
                	} else {
                		if (logger.isDebugEnabled()) {
                			logger.debug("Access is denied (user is not anonymous); delegating to AccessDeniedHandler",
                					exception);
                		}
                		
                		getAccessDeniedHandler().handle(request, response, (AccessDeniedException) exception);            		
                	}
                }
            }
        }

    protected boolean isMigrationEnabled() {
    	return (migrationEntryPoint!=null);
    }
    
    protected abstract boolean hasToMigrate(ServletRequest request);
        
    protected void sendStartMigration(ServletRequest request, ServletResponse response, FilterChain chain,
        AuthenticationException reason) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        SavedRequest savedRequest = new SavedRequest(httpRequest, getPortResolver());

        if (logger.isDebugEnabled()) {
            logger.debug("Migration entry point being called; SavedRequest added to Session: " + savedRequest);
        }

        if (isCreateSessionAllowed()) {
            // Store the HTTP request itself. Used by AbstractProcessingFilter
            // for redirection after successful authentication (SEC-29)
            httpRequest.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY, savedRequest);
        }
        
       	getMigrationEntryPoint().commence(httpRequest, (HttpServletResponse) response, reason);
       
    }

	public AccessDeniedHandler getAccessDeniedHandler() {
		return accessDeniedHandler;
	}

	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		this.accessDeniedHandler = accessDeniedHandler;
	}

	public AuthenticationEntryPoint getMigrationEntryPoint() {
		return migrationEntryPoint;
	}

	public void setMigrationEntryPoint(AuthenticationEntryPoint migrationEntryPoint) {
		this.migrationEntryPoint = migrationEntryPoint;
	}

}
