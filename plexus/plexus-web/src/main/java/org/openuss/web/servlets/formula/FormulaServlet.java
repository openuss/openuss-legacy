package org.openuss.web.servlets.formula;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.acl.AclManager;
import org.apache.log4j.Logger;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.PostInfo;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet to retrieve a given formula
 * 
 * @author Ingo Dueppe
 */
public class FormulaServlet extends HttpServlet {

	private static final long serialVersionUID = -8848001102897327126L;

	private static final Logger logger = Logger.getLogger(FormulaServlet.class);

	private transient DiscussionService discussionService;
	private transient AclManager aclManager;

	@Override
	public void init() throws ServletException {
		logger.info("Initialise OpenUSS-Plexus RepositoryServlet");
		super.init();

		final WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		discussionService = (DiscussionService) wac.getBean("discussionService", DiscussionService.class);
		// FIXME Should be declared by acegi security context;
		aclManager = (AclManager) wac.getBean("aclManager", AclManager.class);
		AcegiUtils.setAclManager(aclManager);
	}

	@Override
	protected void doHead(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		long id = 0;
		PostInfo postInfo = new PostInfo();
		postInfo.setId(id);
		postInfo = discussionService.getPost(postInfo);
		
		PrintWriter out = response.getWriter();
		
		out.print(postInfo.getFormula());
		out.flush();
		
	}

}
