package org.openuss.lecture;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;

/**
 * @see org.openuss.lecture.Course
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class CourseImpl extends CourseBase implements Course, Lifecycle {

	private static final Logger logger = Logger.getLogger(CourseImpl.class);

	private static final long serialVersionUID = -5670314763540215921L;
	
	public CourseImpl() {
		//default configuration
		setAccessType(AccessType.CLOSED);
		setNewsletter(true);
		setDocuments(true);
		setDiscussion(true);
	}

	private String shortenInMiddle(String str, int maxLength){
		if (str==null){
			return null;
		}
		if (str.length()<maxLength){
			return str;
		}
		int halfLength = maxLength / 2;
		String beginning = "";
		String ending = "";
		ending = str.substring(str.length()-halfLength);
		beginning = str.substring(0, str.length()-halfLength);
		return StringUtils.abbreviate(beginning, maxLength-halfLength)+ending;
	}
	
	/**
	 * Generates a guid shortcut on persisting if no shortcut is already set.
	 */
	private void generateShortcut() {
		logger.debug("auto-generate shortcut for course");
		this.setShortcut(shortenInMiddle(getCourseType().getShortcut()+"-"+getPeriod().getName(), 30));

	}

	@Override
	public Boolean isPasswordCorrect(String password) {
		return StringUtils.equalsIgnoreCase(getPassword(), password);
	}

	@Override
	public String getName() {
		return this.getCourseType().getName();
	}

	public boolean onDelete(Session session) throws CallbackException {
		return false;
	}

	public void onLoad(Session session, Serializable id) {
	}

	public boolean onSave(Session session) throws CallbackException {
		if (StringUtils.isBlank(getShortcut())) {
			generateShortcut();
		}
		return false;
	}

	public boolean onUpdate(Session session) throws CallbackException {
		return false;
	}
	
	@Override
	public Boolean isActive() {
		return this.getPeriod().isActive();
	}

}