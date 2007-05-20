package org.openuss.dbtools;

import java.text.DecimalFormat;

public class StarterClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long time1 = System.currentTimeMillis();
		long time2 = System.currentTimeMillis();

		try {
			// Vorsicht, kann sehr lange dauern :-)
			DataCreator dc = new DataCreator();
			dc.createBasicData(100, 20, 2, 3, 2);
			dc.createForumData(2, 3);
			dc.createDocuments();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			time2 = System.currentTimeMillis();
			long time3 = time2 - time1;

			DecimalFormat format = new DecimalFormat("00");

			long hour = (time3 % 86400000) / 3600000;
			long min = (time3 % 3600000) / 60000;
			long sec = (time3 % 60000) / 1000;

			System.out.println("************************");
			System.out.println("Total Time: " + format.format(hour) + "h " + format.format(min) + "m "
					+ format.format(sec) + "s");
			System.out.println("************************");
		}

	}

}
