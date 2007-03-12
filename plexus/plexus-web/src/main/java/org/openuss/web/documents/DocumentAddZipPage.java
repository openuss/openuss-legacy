package org.openuss.web.documents;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$addzip", scope = Scope.REQUEST)
@View
public class DocumentAddZipPage{
	private Date releaseDate;
		
	private static final Logger logger = Logger.getLogger(DocumentAddZipPage.class);
	

	public String unzip(){
		logger.debug("new document saved");
		return Constants.SUCCESS;
	}


	public Date getReleaseDate() {
		return releaseDate;
	}


	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}