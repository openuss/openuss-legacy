package org.openuss.web.braincontest; 

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;
@Bean(name = "views$secured$braincontest$braincontestnew", scope = Scope.REQUEST)
@View
public class BrainContestNewPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestNewPage.class);
	
	@Property(value = "#{braincontest_contest}")
	private BrainContestInfo brainContest;
	
	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;

	@Property(value = "#{braincontest_attachment}")
	private FileInfo attachment;
		
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() {
		if (!isPostBack()) {
			if ( brainContest != null && brainContest.getId() != null) {
				brainContest = brainContestService.getContest(brainContest);
			}
		} 
		if (brainContest!=null){
			if (brainContest.getDomainIdentifier()==null) brainContest.setDomainIdentifier(enrollment.getId());
			List<FileInfo> attachments = brainContestService.getAttachments(brainContest);			
			if (attachments!=null) setAttachment(attachments.get(0));
		}
		if (this.brainContest==null) this.brainContest = new BrainContestInfo(); 
	}	
	
	public String save() throws BrainContestApplicationException{
		if (this.brainContest.getId()==null) brainContestService.createContest(brainContest);
		else if (this.brainContest.getId()!=null) brainContestService.saveContest(brainContest);
		return Constants.BRAINCONTEST_MAIN;
	}
	
	public String removeAttachment(){
		logger.debug("braincontest attachment removed");
		return Constants.SUCCESS;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}

	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

	public FileInfo getAttachment() {
		return attachment;
	}

	public void setAttachment(FileInfo attachment) {
		this.attachment = attachment;
	}

}