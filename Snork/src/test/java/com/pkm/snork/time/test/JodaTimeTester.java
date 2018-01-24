package com.pkm.snork.time.test;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JodaTimeTester {

	private static Logger logger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = Logger.getLogger(JodaTimeTester.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		logger.warn("what up!");
	}

	@After
	public void tearDown() throws Exception {
		logger.debug("peace!");
	}

	@Test
	public void testDayDiff() {

		DateTimeZone dtZone = DateTimeZone.forID("America/New_York");
		
		//  DateTime dt = DateTime(YEAR, MONTH, DAY, HOUR, MIN, SEC);
		DateTime dt1 = new DateTime(2017, 3, 9, 8, 14, 25, dtZone);

		DateTime dt2 = DateTime.now();
		
		int daysBetween = Days.daysBetween(dt1.toLocalDate(), dt2.toLocalDate()).getDays(); 
		
		logger.info(String.format("Days Between [%s] and [%s] = %d", dt1.toString(), dt2.toString(), daysBetween));
	}

	@Test
	public void testTimeZoneDiff() {
		DateTimeZone edtZone = DateTimeZone.forID("America/New_York");
		DateTimeZone bstZone = DateTimeZone.forID("Europe/London");
		
		
		DateTime bstDt = new DateTime(2017, 7, 13, 17, 00, 00, bstZone);

		DateTime converted = bstDt.toDateTime(edtZone);
		
		logger.info(String.format("%s in BST is the same as %s in EDT", bstDt, converted));

		Exception e = new Exception(new MalformedURLException("blah!"));
		logger.error("Exception=" + e.toString() + "");
		
	}
	
	

}
