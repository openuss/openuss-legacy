package org.openuss.search;

import org.openuss.lecture.Faculty;

public interface LectureIndexer {

	public static final String IDENTIFIER = "IDENTIFIER";
	public static final String DOMAINTYPE = "DOMAINTYPE";
	public static final String MODIFIED = "MODIFIED";
	public static final String CONTENT = "CONTENT";

	public abstract void addFaculty(final Faculty faculty);

	public abstract void updateFaculty(final Faculty faculty);

	public abstract void deleteFaculty(final Faculty faculty);

}