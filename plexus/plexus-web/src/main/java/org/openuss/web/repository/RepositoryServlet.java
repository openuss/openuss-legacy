package org.openuss.web.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.repository.RepositoryFile;
import org.openuss.repository.RepositoryService;
import org.openuss.web.Constants;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet to retrieve a given file from the plexus-repository
 * @author Ingo Dueppe
 */
public class RepositoryServlet extends HttpServlet {

	private static final long serialVersionUID = 0L;

	private static final int BUFFER_SIZE = 8192;

	private static final Logger logger = Logger.getLogger(RepositoryServlet.class);
	
	private transient RepositoryService repository;

	@Override
	public void init() throws ServletException {
		logger.info("Initialise OpenUSS-Plexus RepositoryServlet");
		super.init(); 
		
		final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		repository = (RepositoryService) wac.getBean("repositoryService", RepositoryService.class);
	}
	
	
	@Override
	protected void doHead(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final RepositoryFile file = lookupFile(request);
		if (file == null) {
			sendFileNotFound(response);
		} else {
			sendFileHeader(response, file);
		}
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final RepositoryFile file = lookupFile(request);
		if (file == null) {
			sendFileNotFound(response);
		} else {
			sendFileHeader(response, file);
			sendFileContent(response, file);
		}
	}


	private void sendFileContent(final HttpServletResponse response, final RepositoryFile file) throws IOException {
		// send content
		if (logger.isDebugEnabled()) {
			logger.debug("sending content of file "+file.getFileName()+" size "+file.getFileSize());
		}
		final ServletOutputStream output = response.getOutputStream();
		drain(file.getInputStream(), output);
		output.close();
	}

	private void sendFileHeader(final HttpServletResponse response, final RepositoryFile file) {
		// send header 
		if (logger.isDebugEnabled()) {
			logger.debug("sending header of file "+file.getFileName()+" size "+file.getFileSize());
		}
		response.setContentType(file.getContentType());
//		response.setContentType("application/octet-stream");
		response.setContentLength(file.getFileSize().intValue());
	}

	private void sendFileNotFound(final HttpServletResponse response) {
		// TODO Show file not found error
	}

	/**
	 * 
	 * @param request
	 * @return RepositoryFile or Null
	 */
	private RepositoryFile lookupFile(final HttpServletRequest request) {
		final String fileId = request.getParameter(Constants.REPOSITORY_FILE_ID);
		// fetch file from repository
		RepositoryFile file = RepositoryFile.Factory.newInstance();
		file.setId(Long.parseLong(fileId));
		file = repository.getFile(file);
		if (file == null) {
			logger.debug("file not found with id "+fileId);
		}
		return file;
	}
	
	/**
	 * Copies bytes from input to output stream
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	private void drain(final InputStream input, final OutputStream output) throws IOException {
		int read = 0;
		final byte[] buffer = new byte[BUFFER_SIZE];

		while ((read = input.read(buffer, 0, BUFFER_SIZE)) != -1) {
			output.write(buffer, 0, read);
		}
	}
}
