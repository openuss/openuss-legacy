/*
 * Copyright 2005 Jenia org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Extended for OpenUSS.
 */
package org.openuss.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.fckfaces.util.Util;
import org.openuss.web.Constants;

/**
 * @author srecinto
 * @author Christian Beer
 *
 */
public class FCKServlet extends org.fckfaces.util.Servlet {

	private static final long serialVersionUID = -945294405900760483L;

	private static final String WIKI_FCKEDITOR_PATH = "/FCKEditorWiki/";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		if (isWiki(request.getSession())) {
			// search the resource in classloader
	        ClassLoader cl = this.getClass().getClassLoader();
	        String uri = request.getRequestURI();
	        String path = uri.substring(uri.indexOf(Util.FCK_FACES_RESOURCE_PREFIX)+Util.FCK_FACES_RESOURCE_PREFIX.length()+1);
	        
	        // FIXME instead of forward do the same as in orig. servlet >> caching!!!
	        this.getServletContext().getRequestDispatcher(WIKI_FCKEDITOR_PATH + path).forward(request,response);
		} else {
			super.doGet(request, response);
		}
    }
	
	protected boolean isWiki(HttpSession session) {
		MutableBoolean isWiki = (MutableBoolean)session.getAttribute(Constants.WIKI_IS_ACTIVE);
		return isWiki.booleanValue();
	}

}
