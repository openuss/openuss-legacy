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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fckfaces.util.Util;

/**
 * Servlet that lets us extend the FCKeditor with own plugins without putting 
 * them into the classpath.
 * 
 * @author Christian Beer
 *
 */
public class FCKServlet extends org.fckfaces.util.Servlet {

	private static final long serialVersionUID = -945294405900760483L;
	private static final String WIKI_PREFIX = "/FCKeditorWiki/";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		// search the resource in classloader
        String uri = request.getRequestURI();
        String path = uri.substring(uri.indexOf(Util.FCK_FACES_RESOURCE_PREFIX)+Util.FCK_FACES_RESOURCE_PREFIX.length()+1);
        
        File file = new File(getServletContext().getRealPath(WIKI_PREFIX + path));
        
        if (!file.exists()) {
        	super.doGet(request, response);
        } else {
        	if (uri.endsWith(".jsf")) {
	        	response.setContentType("text/html;");
	        } else {
	            response.setHeader("Cache-Control", "public");
	            response.setHeader("Last-Modified", calcModify());
	        }
	        if (uri.endsWith(".css")) {
	        	response.setContentType("text/css;");
	        } else if (uri.endsWith(".js")) {
	        	response.setContentType("text/javascript;");
	        } else if (uri.endsWith(".gif")) {
	        	response.setContentType("image/gif;");
	        }
	        response.setContentLength((int) file.length());
	        
	        // resource found, copying on output stream
	        OutputStream out = response.getOutputStream();
	        byte[] buffer = new byte[4096];
	        InputStream bis = new FileInputStream(file);
	        int read = 0;
	        while ((read = bis.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        bis.close();
	        out.flush();
	        out.close();
        }
    }
	
	private static final String calcModify() {
		Date mod = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(mod);
	}

}
