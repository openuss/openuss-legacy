package org.openuss.docmanagement;

import java.sql.Timestamp;



public class FileImpl extends ResourceImpl implements File{
	
	public long length;
	
	public Timestamp lastModification;
	
	public int version;
	
	public Timestamp distributionTime;
	
	public File predecessor;

	public String mimeType;
	
	public String owner;
	
	public String[] viewed;
	
	public String viewer;
	
	public FileImpl(){		
	}
	
	public FileImpl(Timestamp distributionTime, String id, Timestamp lastModification, long length, String message, String mimeType, String name, String path, File predecessor, int version, int visibility, String owner, String[] viewed, String viewer){
		this.setDistributionTime(distributionTime);
		this.setId(id);
		this.setLastModification(lastModification);
		this.setLength(length);
		this.setMessage(message);
		this.setMimeType(mimeType);
		this.setName(name);
		this.setPath(path);
		this.setPredecessor(predecessor);
		this.setVersion(version);
		this.setVisibility(visibility);
		this.owner = owner;
		this.viewed = viewed;
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getDistributionTime()
	 */
	public Timestamp getDistributionTime() {
		return distributionTime;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setDistributionTime(java.sql.Timestamp)
	 */
	public void setDistributionTime(Timestamp distributionTime) {
		this.distributionTime = distributionTime;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getLastModification()
	 */
	public Timestamp getLastModification() {
		return lastModification;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setLastModification(java.sql.Timestamp)
	 */
	public void setLastModification(Timestamp lastModification) {
		this.lastModification = lastModification;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getSize()
	 */
	public long getLength() {
		return length;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setSize(long)
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getVersion()
	 */
	public int getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setVersion(int)
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	public File getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(File predecessor) {
		this.predecessor = predecessor;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String[] getViewed() {
		return this.viewed;
	}

	public void setViewed(String[] viewed) {
		this.viewed = viewed;
	}

	public String getViewer() {
		return viewer;
	}

	public void setViewer(String viewer) {
		this.viewer = viewer;
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