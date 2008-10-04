package org.openuss.web.migration;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.migration.CentralUserData;

/**
 * Stores information about a centrally authenticated user. 
 * @author Peter Schuh
 *
 */
@Bean(name = "centralUserData", scope = Scope.SESSION)
@View
public class CentralUserDataBean extends BaseBean implements CentralUserData {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String authenticationDomainName="";
	private Long authenticationDomainId;	
	private boolean userAgreementAccepted = false;

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
	
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#getUsername()
	 */
	public String getUsername() {
		return username;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#setUsername(java.lang.String)
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#getFirstName()
	 */
	public String getFirstName() {
		return firstName;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#setFirstName(java.lang.String)
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#getLastName()
	 */
	public String getLastName() {
		return lastName;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#setLastName(java.lang.String)
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#getEmail()
	 */
	public String getEmail() {
		return email;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#getAuthenticationDomainId()
	 */
	public Long getAuthenticationDomainId() {
		return authenticationDomainId;
	}
	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#setAuthenticationDomainId(java.lang.Long)
	 */
	public void setAuthenticationDomainId(Long authenticationDomainId) {
		this.authenticationDomainId = authenticationDomainId;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#getAuthenticationDomainName()
	 */
	public String getAuthenticationDomainName() {
		return authenticationDomainName;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.migration.CentralUserData#setAuthenticationDomainName(java.lang.String)
	 */
	public void setAuthenticationDomainName(String authenticationDomainName) {
		this.authenticationDomainName = authenticationDomainName;
	}	
}
