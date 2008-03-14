package org.openuss.web.seminarpool;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.web.Constants;

/**
 *
 * @author PS-Seminarplaceallocation
 * 
 */

@Bean(name = "views$secured$seminarpool$main", scope = Scope.REQUEST)
@View 
public class SeminarpoolMainPage extends AbstractSeminarpoolPage {

	private static final Logger logger = Logger.getLogger(SeminarpoolMainPage.class);

	private String password;
	
	private List<UserInfo> userInfoList = new ArrayList<UserInfo>();

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if ( seminarpoolInfo != null && seminarpoolInfo.getId() != null ) {
			userInfoList = getSeminarpoolAdministrationService().getAllSeminarpoolAdmins(seminarpoolInfo.getId());
		}
		//Check phase
		if(seminarpoolInfo != null && seminarpoolInfo.getSeminarpoolStatus() == SeminarpoolStatus.PREPARATIONPHASE){
			if(seminarpoolInfo.getRegistrationStartTime().getTime()< System.currentTimeMillis() && System.currentTimeMillis() < seminarpoolInfo.getRegistrationEndTime().getTime()){
				seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.REGISTRATIONPHASE);
				this.seminarpoolAdministrationService.updateSeminarpool(seminarpoolInfo);
			}
		}
		else if(seminarpoolInfo != null && seminarpoolInfo.getSeminarpoolStatus() == SeminarpoolStatus.REGISTRATIONPHASE && System.currentTimeMillis() > seminarpoolInfo.getRegistrationEndTime().getTime()){
	
				seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE);
				this.seminarpoolAdministrationService.updateSeminarpool(seminarpoolInfo);
		}
	}
	

	
	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, seminarpoolInfo.getPassword())) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}
	
	public String applyWithPassword(){
		if (! seminarpoolUserRegistrationService.applyUserByPassword(password, user.getId(), seminarpoolInfo.getId()) ){
			addError(i18n(Constants.SEMINARPOOL_MAIN_PASSWORD_ERROR));
		}
		return "";
	}

	/**
	 * Returns true, if seminarpool is boookmarked, otherwise false
	 * @return bookmarked-flag
	 */
	public Boolean getBookmarked() {
		try {
			return desktopService2.isSeminarpoolBookmarked(desktopInfo.getId(), seminarpoolInfo.getId());
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
	
	/**
	 * Bookmarks the selected seminarpool on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutSeminarpool() {
		try {
			desktopService2.linkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
			addMessage(i18n("desktop_command_add_seminarpool_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}
	
	/**
	 * removes the Bookmark for the selected seminarpool on the MyUni Page.
	 * @return Outcome
	 */
	public String removeSeminarpoolShortcut() {
		try {
			desktopService2.unlinkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
			addMessage(i18n("desktop_command_remove_seminarpool_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}



	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

}