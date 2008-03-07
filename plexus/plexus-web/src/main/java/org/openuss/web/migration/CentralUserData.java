package org.openuss.web.migration;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Stores information about a centrally authenticated user. 
 * @author Peter Schuh
 *
 */
@Bean(name = "centralUserData", scope = Scope.SESSION)
@View
public class CentralUserData extends BaseBean {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String authenticationDomainName="";
	private Long authenticationDomainId;	
	private boolean userAgreementAccepted = false;

	private static final Logger logger = Logger.getLogger(CentralUserData.class);

	/**
	 * Validator to check wether the user has accepted the user agreement or not.
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateAcception(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput)toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_useragreement_must_be_accepted"), null);
		}
	}

	public boolean isUserAgreementAccepted() {
		return userAgreementAccepted;
	}

	public void setUserAgreementAccepted(boolean userAgreementAccepted) {
		this.userAgreementAccepted = userAgreementAccepted;
	}	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getAuthenticationDomainId() {
		return authenticationDomainId;
	}
	public void setAuthenticationDomainId(Long authenticationDomainId) {
		this.authenticationDomainId = authenticationDomainId;
	}

	public String getAuthenticationDomainName() {
		return authenticationDomainName;
	}

	public void setAuthenticationDomainName(String authenticationDomainName) {
		this.authenticationDomainName = authenticationDomainName;
	}	
}
