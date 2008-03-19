package org.openuss.web.error;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;

/**
 * Exception Helper Class 
 * @author Ingo Dueppe
 */
@Bean(name="errorController",scope=Scope.REQUEST)
@View
public class ErrorViewController {

	private static final Logger logger = Logger.getLogger(ErrorViewController.class);
	
	@Property(value="#{requestScope['javax.servlet.error.exception']}")
	private Exception exception;
	
	public String getStackTrace() {
		StringWriter out = new StringWriter();
		try {
			if (exception != null) {
				exception.printStackTrace(new PrintWriter(out));
			}
			String str = out.toString().replaceAll("<", "&lt;");
			str = str.replaceAll("\n", "<br/>");
			return str;
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return "could not create exception stacktrace";
		}
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	

}
