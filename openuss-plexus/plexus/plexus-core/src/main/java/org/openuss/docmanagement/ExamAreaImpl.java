package org.openuss.docmanagement;

import java.sql.Timestamp;

public class ExamAreaImpl extends FolderImpl implements ExamArea{
	
	public Timestamp deadline;
	
	public ExamAreaImpl(){
		super();
	}
	
	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}
	
	
}