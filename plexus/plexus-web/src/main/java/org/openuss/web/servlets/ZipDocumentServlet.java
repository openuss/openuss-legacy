package org.openuss.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.repository.RepositoryService;
import org.openuss.web.Constants;
import org.openuss.web.documents.ZipFilePacker;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet to retrieve a given file from the plexus-repository
 * @author Ingo Dueppe
 */
public class ZipDocumentServlet extends HttpServlet {

	private static final long serialVersionUID = -4782713711269751191L;

	private static final Logger logger = Logger.getLogger(ZipDocumentServlet.class);
	
	private transient RepositoryService repositoryService;

	@Override
	public void init() throws ServletException {
		logger.info("Initialise OpenUSS-Plexus ZipDocumentServlet");
		super.init(); 
		
		final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		repositoryService = (RepositoryService) wac.getBean("repositoryService", RepositoryService.class);
	}
	
	@Override
	protected void doHead(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doHeader...");
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		List<FileInfo> files = (List<FileInfo>) request.getSession().getAttribute(Constants.DOCUMENTS_SELECTED_FILEENTRIES);
		String fileName = (String) request.getSession().getAttribute(Constants.ZIP_FILE_NAME);
		if(fileName == null){
			fileName = "documents";
		}
		request.getSession().removeAttribute(Constants.DOCUMENTS_SELECTED_FILEENTRIES);
		
		if (files == null) {
			sendFileNotFound(response);
		} else {
//			logger.debug("selected "+files.size()+" files.");
//			for (FileInfo file : files) {
//				logger.debug("obtaining input stream for "+file.getName());
//				file.setInputStream(repositoryService.loadContent(file.getId()));
//			}
			
			ZipFilePacker packer = new ZipFilePacker(files, repositoryService);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".zip\"");
			packer.writeZip(response.getOutputStream());
		}
	}


	private void sendFileNotFound(final HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
