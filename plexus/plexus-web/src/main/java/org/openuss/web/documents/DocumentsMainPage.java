package org.openuss.web.documents; 

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.enrollment.AbstractEnrollmentPage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$documents", scope = Scope.REQUEST)
@View
public class DocumentsMainPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(DocumentsMainPage.class);
	
	private DocumentDataProvider data = new DocumentDataProvider();
	
	//needed
	private String path;

	private class DocumentDataProvider extends AbstractPagedTable<DocumentTableEntry> {

		private DataPage<DocumentTableEntry> page; 
		
		@Override 
		public DataPage<DocumentTableEntry> getDataPage(int startRow, int pageSize) {		
			ArrayList<DocumentTableEntry> al = new ArrayList<DocumentTableEntry>();			
			DocumentTableEntry dte1 = new DocumentTableEntry("Digitale Goodies", "folder", "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false);
			DocumentTableEntry dte2 = new DocumentTableEntry("Normative Entscheidungstheorie.pdf", "pdf", "20 kb", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), true);
			DocumentTableEntry dte3 = new DocumentTableEntry("picture.jpg", "jpg", "175 kb", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false);
			al.add(dte1); al.add(dte2); al.add(dte3);
			page = new DataPage<DocumentTableEntry>(al.size(),0,al);
			return page;
		}
	}
	
	public String download(){
		logger.debug("document download");
		return Constants.SUCCESS;
	}
	
	public String delete(){
		logger.debug("document deleted");
		return Constants.SUCCESS;
	}

	public DocumentDataProvider getData() {
		return data;
	}


	public void setData(DocumentDataProvider data) {
		this.data = data;
	}

	public String getPath() {
		//return path;
		return "> KLR > Veranstaltung > Folien";
	}

	public void setPath(String path) {
		this.path = path;
	}

	
}