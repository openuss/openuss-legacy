package org.openuss.web.docmanagement;

import org.openuss.docmanagement.FileImpl;

public class FileTableEntry extends FileImpl{
	public boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}