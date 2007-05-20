package org.openuss.dbtools;

public class StarterClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		DataCreator dc = new DataCreator();
		
		long time1 = System.currentTimeMillis();;
		long time2 = 0L;
		
		try {
			//Vorsicht, dauert sehr lange :-)
			dc.createBasicData(200, 50, 3, 4, 2);
			dc.createForumData(2, 3);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			time2 = System.currentTimeMillis();
			long time3 = time2 - time1;
			System.out.println("Duration: "+time3+" ms");
		}

	}

}
