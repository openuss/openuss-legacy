package org.openuss.docmanagement;

public class DocRights{
	public final static int READ_ALL 		= 1 << 1;
	public final static int READ_ASSIST 	= 1 << 2;
	public final static int READ_OWNER 		= 1 << 3;
	
	public final static int EDIT_ALL 		= 1 << 4;
	public final static int EDIT_ASSIST 	= 1 << 5;
	public final static int EDIT_OWNER 		= 1 << 6;
	
}