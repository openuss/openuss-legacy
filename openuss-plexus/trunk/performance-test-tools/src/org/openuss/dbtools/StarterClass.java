package org.openuss.dbtools;

public class StarterClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DataCreator dc = new DataCreator();
		try {
			dc.createBasicData(20, 10, 3, 3, 2);
			dc.createForumData(2, 5);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
