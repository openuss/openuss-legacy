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

import org.apache.commons.io.IOUtils;
import org.fckfaces.util.Util;

/**
 * Servlet that lets us extend the FCKeditor with own plugins without putting 
 * them into the classpath.
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
public class FCKServlet extends org.fckfaces.util.Servlet {

	private static final long serialVersionUID = -945294405900760483L;
	private static final long lastModified = System.currentTimeMillis();
	
	private static final String WIKI_PREFIX = "/FCKEditorWiki/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// search the resource in classloader
        String uri = request.getRequestURI();
        String path = uri.substring(uri.indexOf(Util.FCK_FACES_RESOURCE_PREFIX)+Util.FCK_FACES_RESOURCE_PREFIX.length()+1);
        
        File file = new File(getServletContext().getRealPath(WIKI_PREFIX + path));
        
        if (!file.exists()) {
        	super.doGet(request, response);
        } else {
        	sendFile(uri, file, response);
        }
    }
	
	private void sendFile(final String uri, final File file, final HttpServletResponse response) throws IOException {
		setHeaders(uri, file, response);
        
        // resource found, copying on output stream
        OutputStream out = response.getOutputStream();
        InputStream bis = new FileInputStream(file);
        try {
	        byte[] buffer = new byte[4096];
	        int read = bis.read(buffer);
	        while (read != -1) {
	            out.write(buffer, 0, read);
	            read = bis.read(buffer);
	        }
        } finally {
        	IOUtils.closeQuietly(bis);
        	IOUtils.closeQuietly(out);
        }
	}

	private void setHeaders(final String uri, final File file,	final HttpServletResponse response) {
    	if (uri.endsWith(".jsf") || uri.endsWith(".html")) {
        	response.setContentType("text/html;charset=UTF-8");
        } else {
            response.setHeader("Cache-Control", "public");
            response.setHeader("Last-Modified", calcModify());
        }
        
        if (uri.endsWith(".css")) {
        	response.setContentType("text/css;charset=UTF-8");
        } else if (uri.endsWith(".js")) {
        	response.setContentType("text/javascript;charset=UTF-8");
        } else if (uri.endsWith(".gif")) {
        	response.setContentType("image/gif;");
        } else if (uri.endsWith(".xml")) {
        	response.setContentType("text/xml;charset=UTF-8");
        }
        response.setContentLength((int) file.length());
	}
	
	@Override
	protected long getLastModified(HttpServletRequest req) {
		return lastModified;
	}

	private static final String calcModify() {
		Date mod = new Date(lastModified);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(mod);
	}

}
