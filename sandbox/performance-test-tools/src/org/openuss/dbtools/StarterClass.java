package org.openuss.dbtools;

public class StarterClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DataCreator dc = new DataCreator();
		try {
			//Vorsicht, dauert sehr lange :-)
			dc.createBasicData(1000, 50, 3, 4, 2);
			dc.createForumData(5, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
