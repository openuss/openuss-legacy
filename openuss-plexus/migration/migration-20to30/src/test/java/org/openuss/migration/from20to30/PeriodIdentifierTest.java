package org.openuss.migration.from20to30;

import junit.framework.TestCase;

public class PeriodIdentifierTest extends TestCase {
	
	PeriodIdentifier identifier = new PeriodIdentifier();
	
	public void testFind() {
		// Spring 2002
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Spring of 2002"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Spring 2002"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Summer term 02"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SS 2002"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SS2002"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SS 02"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SS02"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SoSe 02"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SoSe02"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Sommersemester 2002"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Sommersemester2002"));
		assertFalse(PeriodIdentifier.SS02 == identifier.find("Vorlesung SoSe 2002 03"));
		assertFalse(PeriodIdentifier.SS02 == identifier.find("Vorlesung SoSe 200203"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("SS 2002 Priv.-Doz. Dr. Faber"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Vorlesung SoSe 2002"));
		assertEquals(PeriodIdentifier.SS02 , identifier.find("Studienabschnitt 1 / Einstellungsjahrgang 2002"));
		// Fall 2002
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("WS01/02"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("01/02"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("01 / 02"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("2001 / 2002"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("xyt Wintersemester 2001 / 2002 xyz"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("xyt Wintersemester 2001/2002 xyz"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("WiSe 2001 - 2002"));
		assertEquals(PeriodIdentifier.WS01_02 , identifier.find("WiSe 2001 / 2002"));
		// Fall 2002
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("WS00/01"));
		assertEquals(PeriodIdentifier.WS00_01, identifier.find("00/01"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("00 / 01"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("2000 / 2001"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("xyt Wintersemester 2000 / 2001 xyz"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("xyt Wintersemester 2000/2001 xyz"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("WiSe 2000 - 2001"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("WiSe 2000 / 2001"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("WiSe 2000-2001"));
		assertEquals(PeriodIdentifier.WS00_01 , identifier.find("WiSe 2000/2001"));

		assertEquals(PeriodIdentifier.WS05_06 , identifier.find("Wintersemester 2005/2006"));
		assertEquals(PeriodIdentifier.WS06_07 , identifier.find("Wintersemester 2006/2007"));
		assertEquals(PeriodIdentifier.WS07_08 , identifier.find("WS 2007/2008"));
		assertEquals(PeriodIdentifier.WS07_08 , identifier.find("WS 07/08"));
		
	}

}
