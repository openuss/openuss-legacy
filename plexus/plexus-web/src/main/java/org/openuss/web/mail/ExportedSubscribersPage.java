package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;

@Bean(name = "views$secured$mailinglist$exportedsubscribers", scope = Scope.REQUEST)
@View
public class ExportedSubscribersPage extends AbstractMailingListPage{

	
		private String subscribersSemicolon;
		
		private String subscribersComma;
	
		@SuppressWarnings("unchecked")
		@Prerender
		public void prerender() throws Exception {	
			super.prerender();
			mailingList = getEnrollmentMailingListService().getMailingList(enrollmentInfo);
			setSessionBean(Constants.MAILINGLIST_MAILINGLIST, mailingList);
			setSubscribersSemicolon(getEnrollmentMailingListService().exportSubscribers(enrollmentInfo));
			setSubscribersComma(getSubscribersSemicolon().replace(';', ','));
		}

		public String getSubscribersSemicolon() {
			return subscribersSemicolon;
		}

		public void setSubscribersSemicolon(String subscribers1) {
			this.subscribersSemicolon = subscribers1;
		}

		public String getSubscribersComma() {
			return subscribersComma;
		}

		public void setSubscribersComma(String subscribers2) {
			this.subscribersComma = subscribers2;
		}	
		
		
		
}