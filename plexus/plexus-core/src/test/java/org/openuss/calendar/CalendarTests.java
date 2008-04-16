package org.openuss.calendar;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CalendarTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.calendar");
		//$JUnit-BEGIN$
		// TODO - CALENDAR import Tests from Diplom-Branch --> problems
//		suite.addTestSuite(AppointmentDaoTest.class);
		suite.addTestSuite(AppointmentTypeDaoTest.class);
		suite.addTestSuite(CalendarDaoTest.class);
		// TODO - CALENDAR import Tests from Diplom-Branch --> problems
//		suite.addTestSuite(CalendarServiceIntegrationTest.class);
		// TODO - CALENDAR import Tests from Diplom-Branch --> problems
//		suite.addTestSuite(SerialAppointmentDaoTest.class);
		return suite;
	}


}
