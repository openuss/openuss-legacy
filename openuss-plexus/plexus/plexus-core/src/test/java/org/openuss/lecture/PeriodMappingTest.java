package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Test Mapping Finding
 * @author Ingo Dueppe
 */
public class PeriodMappingTest extends TestCase {
	
	private Period createPeriod(String name, Date startDate, Date endDate, boolean defaultPeriod) {
		Period period = new PeriodImpl();
		period.setName(name);
		period.setStartdate(startDate);
		period.setEnddate(endDate);
		period.setDefaultPeriod(defaultPeriod);
		return period;
	}
	
	public void testGenerateMapping() {
		Date d070301 = createDate(2007, 3, 1);
		Date d070315 = createDate(2007, 3, 15);
		Date d070401 = createDate(2007, 4, 1);
		Date d070415 = createDate(2007, 4, 15);
		Date d070901 = createDate(2007, 9, 01);
		Date d070915 = createDate(2007, 9, 15);
		Date d070930 = createDate(2007, 9, 30);
		Date d071001 = createDate(2007, 10, 1);
		Date d071015 = createDate(2007, 10, 15);
		Date d071101 = createDate(2007, 11, 1);
		Date d080331 = createDate(2008, 3, 31);
		Date d080401 = createDate(2008, 4, 1);
		Date d200101 = createDate(1920, 1, 1);
		Date d190101 = createDate(2019, 1, 1);
		
		Date min = createDate(1985, 01, 01);
		Date max = createDate(2010, 12, 31);
		
		
		// To Period
		Period defaultPeriod = createPeriod("default", min, max, true);
		Period ss07 = createPeriod("ss07", d070401, d070930, false);
		Period ws07 = createPeriod("ws07", d071001, d080401, false);
		Period sep07 = createPeriod("sep07", d070901, d070930, false);
		
		List<Period> toPeriod = new ArrayList<Period>();
		toPeriod.add(defaultPeriod);
		toPeriod.add(ss07);
		toPeriod.add(ws07);
		toPeriod.add(sep07);
		
		
		// From Period
		List<Period> fromPeriod = new ArrayList<Period>();
		Period pOne = createPeriod("P", d070301, d070315, false);
		Period pTwo = createPeriod("P", d070301, d070415, false);
		Period pThree = createPeriod("P", d070401, d070930, false);
		Period pFour = createPeriod("P", d070415, d070915, false);
		Period pFive = createPeriod("P", d070915, d070930, false);
		Period pSix = createPeriod("P", d070915, d071001, false);
		Period pSeven = createPeriod("P", d071001, d071015, false);
		Period pEight = createPeriod("P", d071015, d071101, false);
		Period pNine = createPeriod("P", d071001,d080331, false);
		Period pTen = createPeriod("P", d200101,d190101, false);
		
		fromPeriod.add(pOne);
		fromPeriod.add(pTwo);
		fromPeriod.add(pThree);
		fromPeriod.add(pFour);
		fromPeriod.add(pFive);
		fromPeriod.add(pSix);
		fromPeriod.add(pSeven);
		fromPeriod.add(pEight);
		fromPeriod.add(pNine);
		fromPeriod.add(pTen);
		
		Map<Period,Period> maps = PeriodMapping.generate(fromPeriod, toPeriod);
		
		assertEquals(defaultPeriod, maps.get(pOne));
		assertEquals(defaultPeriod, maps.get(pTwo));
		assertEquals(ss07, maps.get(pThree));
		assertEquals(ss07, maps.get(pFour));
		assertEquals(sep07, maps.get(pFive));
		assertEquals(defaultPeriod, maps.get(pSix));
		assertEquals(ws07, maps.get(pSeven));
		assertEquals(ws07, maps.get(pEight));
		assertEquals(ws07, maps.get(pNine));
		assertEquals(defaultPeriod, maps.get(pTen));
	}
	
	private Date createDate(int year, int month, int day) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(year, month, day);
		return gc.getTime();
	}

}
