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
	
	public String getExtension(){
		String extension = name.substring(name.lastIndexOf(".")+1);
		extension = extension.toLowerCase();
		if (extension.equals("avi")) return "avi";
		if (extension.equals("bmp")) return "bmp";
		if (extension.equals("cs")) return "cs";
		if (extension.equals("dll")) return "dll";
		if (extension.equals("doc")) return "doc";
		if (extension.equals("exe")) return "exe";
		if (extension.equals("gif")) return "gif";
		if (extension.equals("jpg")) return "jpg";
		if (extension.equals("mdb")) return "mdb";
		if (extension.equals("mov")) return "mov";
		if (extension.equals("mp3")) return "mp3";
		if (extension.equals("mpg")) return "mpg";
		if (extension.equals("others")) return "others";
		if (extension.equals("pdf")) return "pdf";
		if (extension.equals("ppt")) return "ppt";
		if (extension.equals("ra")) return "ra";
		if (extension.equals("ram")) return "ram";
		if (extension.equals("rar")) return "rar";
		if (extension.equals("tar")) return "tar";
		if (extension.equals("txt")) return "txt";
		if (extension.equals("vsd")) return "vsd";
		if (extension.equals("xls")) return "xls";
		if (extension.equals("xml")) return "xml";
		if (extension.equals("zip")) return "zip";
		return "default";
	}
	
	public boolean isRead(){
		boolean userHasViewedFile = false;
		if ((this.viewed!=null)&&(viewer!=null)) {
			for (int i = 0; i<this.viewed.length; i++){
				if (this.viewed[i].equals(viewer)) return true;
			}
		}
		return userHasViewedFile;
	}

	public String getSize() {
		if (length>1048576)
			return (new Long(length/1048576)).toString()+" MB";
		if (length>1024) 
			return (new Long(length/1024)).toString()+" KB";
		return (new Long(length)).toString()+" B";
	}	
	
}