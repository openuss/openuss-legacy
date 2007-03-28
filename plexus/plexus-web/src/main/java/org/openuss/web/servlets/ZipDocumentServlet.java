package org.openuss.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;
import org.openuss.web.documents.ZipFilePacker;

/**
 * Servlet to retrieve a given file from the plexus-repository
 * @author Ingo Dueppe
 */
public class ZipDocumentServlet extends HttpServlet {

	private static final long serialVersionUID = 0L;

	private static final Logger logger = Logger.getLogger(ZipDocumentServlet.class);
	
	@Override
	public void init() throws ServletException {
		logger.info("Initialise OpenUSS-Plexus ZipDocumentServlet");
		super.init(); 
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
		request.getSession().removeAttribute(Constants.DOCUMENTS_SELECTED_FILEENTRIES);
		if (files == null) {
			sendFileNotFound(response);
		} else {
			logger.debug("selected "+files.size()+" files.");
			ZipFilePacker packer = new ZipFilePacker(files);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=\"documents.zip\"");
			packer.writeZip(response.getOutputStream());
		}
	}


//	private void sendFileContent(final HttpServletResponse response, final RepositoryFile file) throws IOException {
//		// send content
//		if (logger.isDebugEnabled()) {
//			logger.debug("sending content of file "+file.getFileName()+" size "+file.getFileSize());
//		}
//		final ServletOutputStream output = response.getOutputStream();
//		drain(file.getInputStream(), output);
//		output.close();
//	}
//
//	private void sendFileHeader(final HttpServletResponse response, final RepositoryFile file) {
//		// send header 
//		if (logger.isDebugEnabled()) {
//			logger.debug("sending header of file "+file.getFileName()+" size "+file.getFileSize());
//		}
//		response.setContentType("application/octet-stream");
//	}

	private void sendFileNotFound(final HttpServletResponse response) {
		// TODO Show file not found error
	}
}
