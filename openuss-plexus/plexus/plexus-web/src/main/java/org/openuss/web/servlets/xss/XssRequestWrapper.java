package org.openuss.web.servlets.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

/**
 * XssRequestWrapper helps against the most xss attacks that are listed on <a href="http://ha.ckers.org/xss.html">ha.ckers.org</a><br/>   
 * 
 * Thanks to the author on <a href="http://mc4j.org/confluence/display/stripes/XSS+filter">Stripes Wiki</a>
 * @author Ingo Dueppe
 *
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

	private static final Logger logger = Logger.getLogger(XssRequestWrapper.class);

	private Map<String, String[]> sanitized;
	private Map<String, String[]> orig;
	
	@SuppressWarnings("unchecked")
	public XssRequestWrapper(HttpServletRequest req) {
		super(req);
		orig = req.getParameterMap();	
		sanitized = getParameterMap();
		if (logger.isDebugEnabled()) {
			snzLogger();
		}
	}		

	@Override
	public String getParameter(String name) {		
		String[] vals = getParameterMap().get(name); 
		if (vals != null && vals.length > 0) { 
			return vals[0];
		} else {        
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> getParameterMap() {	
		if (sanitized==null)
			sanitized = sanitizeParamMap(orig);
		return sanitized;			
	}

	@Override
	public String[] getParameterValues(String name) {	
		return getParameterMap().get(name);
	}

	private  Map<String, String[]> sanitizeParamMap(Map<String, String[]> raw) {		
		Map<String, String[]> res = new HashMap<String, String[]>();
		if (raw==null) {
			return res;
		}
	
		for (String key : (Set<String>) raw.keySet()) {			
			String[] rawVals = raw.get(key);
			String[] snzVals = new String[rawVals.length];
			for (int i=0; i < rawVals.length; i++) 
			{
				snzVals[i] = new HtmlInputFilter().filter(rawVals[i]); 
			}
			res.put(key, snzVals);
		}			
		return res;
	}

	@SuppressWarnings("unchecked")
	private void snzLogger() {
		for (String key : (Set<String>) orig.keySet()) {
			String[] rawVals = orig.get(key);
			String[] snzVals = sanitized.get(key);
			if (rawVals !=null && rawVals.length>0)	{
				for (int i=0; i < rawVals.length; i++) {
					if (rawVals[i].equals(snzVals[i]))	{														
						logger.debug("Sanitization. Param seems safe: " + key + "[" + i + "]=" + snzVals[i]);
					} else {
						logger.debug("Sanitization. Param modified: " + key + "[" + i + "]=" + snzVals[i]);
					}
				}		
			}
		}
	}
}
