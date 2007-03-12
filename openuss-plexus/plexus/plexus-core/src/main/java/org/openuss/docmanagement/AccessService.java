package org.openuss.docmanagement;

public class AccessService{
	public static final int CREATE = 1 << 1;
	public static final int RETRIEVE = 1 << 2;
	public static final int UPDATE = 1 << 3;
	public static final int DELETE = 1 << 4;
	
	public boolean actionAllowed(int action, String id){
		return true;
	}
}