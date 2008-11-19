package org.openuss.web.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.acl.AclManager;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet to retrieve a given file from the plexus-repository
 * 
 * @author Ingo Dueppe
 */
public class DocumentServlet extends HttpServlet {

	private static final long serialVersionUID = -8848001102897327126L;

	private static final Logger logger = Logger.getLogger(DocumentServlet.class);
	
	private static final String REQUEST_FILE = "org.openuss.web.servlets.request.file";

	private transient DocumentService documentService;
	private transient AclManager aclManager;

	@Override
	public void init() throws ServletException {
		logger.info("Initialise OpenUSS-Plexus RepositoryServlet");
		super.init();

		final WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		documentService = (DocumentService) wac.getBean("documentService", DocumentService.class);
		// TODO Should be declared by acegi security context;
		aclManager = (AclManager) wac.getBean("aclManager", AclManager.class);
		AcegiUtils.setAclManager(aclManager);
	}

	@Override
	protected void doHead(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final FileInfo file = lookupFile(request);
		if (file == null) {
			sendFileNotFound(response);
		} else {
			sendFileHeader(response, file);
		}
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException {
		final FileInfo file = lookupFile(request);
		if (file == null) {
			sendFileNotFound(response);
		} else if (!AcegiUtils.hasPermission(file, new Integer[] { LectureAclEntry.READ })) {
			throw new AccessDeniedException("User is not permitted!");
		} else {
			sendFileHeader(response, file);
			sendFileContent(response, file);
		}
	}

	private void sendFileContent(final HttpServletResponse response, final FileInfo file) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("sending content of file " + file.getFileName() + " size " + file.getFileSize());
		}
		final ServletOutputStream os = response.getOutputStream();
		final InputStream is = file.getInputStream();
		try {
			IOUtils.copyLarge(is, os);
			os.flush();
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
		logger.debug("send file content");
	}

	private void sendFileHeader(final HttpServletResponse response, final FileInfo file) {
		if (logger.isDebugEnabled()) {
			logger.debug("sending header of file " + file.getFileName() + " size " + file.getFileSize());
		}
		response.setContentType(file.getContentType());
		response.setContentLength(file.getFileSize().intValue());
	}

	@Override
	protected long getLastModified(HttpServletRequest request) {
		final FileInfo fileInfo = lookupFile(request);
		if (fileInfo != null) {
			return fileInfo.getModified().getTime();
		} else {
			return super.getLastModified(request);
		}
	}

	private void sendFileNotFound(final HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @param request
	 * @return RepositoryFile or Null
	 */
	private FileInfo lookupFile(final HttpServletRequest request) {
		FileInfo file = (FileInfo) request.getAttribute(REQUEST_FILE);
		
		if (file == null) {
			final String fileId = request.getParameter(Constants.REPOSITORY_FILE_ID);
			try {
				// fetch file from document service
				file = documentService.getFileEntry(Long.parseLong(fileId), true);
			} catch (NumberFormatException nfe) {
				// number format exception can be ignored.
			}
			if (file == null) {
				logger.debug("file not found with id " + fileId);
			} else {
				request.setAttribute(REQUEST_FILE, file);
			}
		}
		
		return file;
	}
}
