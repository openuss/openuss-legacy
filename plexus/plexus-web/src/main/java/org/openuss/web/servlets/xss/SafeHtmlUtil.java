package org.openuss.web.servlets.xss;

import jregex.Pattern;
import jregex.REFlags;
import jregex.Replacer;

public class SafeHtmlUtil {
	
	public static String sanitize(String raw) {
		if (raw==null || raw.length()==0)
			return raw;

		return HTMLEntityEncode(canonicalize(raw));
	}

	private static Pattern scriptPattern = new Pattern("script", REFlags.IGNORE_CASE);
	private static Replacer scriptReplacer = scriptPattern.replacer("&#x73;cript");

	public static String HTMLEntityEncode(String input) 	{
		String next = scriptReplacer.replace(input);
		return next;
		
//		We need to enable xhtml return code (here should be a positive list  
//		
//		StringBuffer sb = new StringBuffer();
//		for ( int i = 0; i < next.length(); ++i ) {
//			char ch = next.charAt( i );
//			if (ch=='<') {
//				sb.append("&lt;");
//			} else if (ch=='>') {
//				sb.append("&gt;");
//			} else {
//				sb.append(ch);
//			}
//		}
//
//		return sb.toString();
	}


	// "Simplifies input to its simplest form to make encoding tricks more difficult"
	// though it didn't do seem to do anything to hex or HTML encoded characters... *shrug* maybe for unicode?
	public static String canonicalize( String input ) {
		String canonical = sun.text.Normalizer.normalize( input, java.text.Normalizer.Form.NFD, 0 );
		return canonical;
	}
}
