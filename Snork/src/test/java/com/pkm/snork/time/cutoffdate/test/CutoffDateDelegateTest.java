package com.pkm.snork.time.cutoffdate.test;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import com.pkm.snork.time.cutoffdate.CutoffDateDelegate;
import com.pkm.snork.time.cutoffdate.CutoffDateDelegateUtil;
import com.pkm.snork.time.cutoffdate.CutoffDateRequest;

public class CutoffDateDelegateTest {

	private final static int hour8pm = 20;
	private final static int hourBefore8pm = 19;
	private final static int hourAfter8pm = 21;
	private final static int minute = 55;
	private final static int fallBackMonth = 11;
	private final static int fallBackDay = 6;
	private final static int februaryMonth = 2;
	private final static int firstOfMonth = 1;
	private final static int leapDay = 29;
	private final static int marchMonth = 3;
	private final static int normalMonth = 6;
	private final static int normalDay = 16;
	private final static int springForwardMonth = 3;
	private final static int springForwardDay = 13;
	private final static int year = 2016;

	private	final static String FALL_BACK = "Fall Back";
	private	final static String LEAP_DAY = "Leap Day";
	private final static String DAY_AFTER_LEAP_DAY = "Day After Leap Day";
	private	final static String NORMAL_DAY = "Normal Day";
	private	final static String SPRING_FORWARD = "Spring Forward";

	private final static DateTimeFormatter jodaFormat = DateTimeFormat.forPattern("hh:mmaa_MM/dd/yyyy");

	@Before
	public void setUp() throws Exception {

		try {

			// Create mocks
			CutoffDateDelegateUtil mockEcdUtil = Mockito.mock(CutoffDateDelegateUtil.class);

			// Set up mocks
			PowerMockito.spy(CutoffDateDelegate.class);
			PowerMockito.doReturn(mockEcdUtil).when(CutoffDateDelegate.class, "createCutoffDateDelegateUtil");
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}

	@Test
	public void testNormalDayBefore8pm() throws Exception {

		testBefore8pm(NORMAL_DAY, year, normalMonth, normalDay);
	}

	@Test
	public void testNormalDayAfter8pm() throws Exception {

		testAfter8pm(NORMAL_DAY, year, normalMonth, normalDay);
	}

	@Test
	public void testSpringForwardBefore8pm() throws Exception {

		testBefore8pm(SPRING_FORWARD, year, springForwardMonth, springForwardDay);
	}

	@Test
	public void testSpringForwardAfter8pm() throws Exception {

		testAfter8pm(SPRING_FORWARD, year, springForwardMonth, springForwardDay);
	}

	@Test
	public void testFallBackBefore8pm() throws Exception {

		testBefore8pm(FALL_BACK, year, fallBackMonth, fallBackDay);
	}

	@Test
	public void testFallBackAfter8pm() throws Exception {

		testAfter8pm(FALL_BACK, year, fallBackMonth, fallBackDay);
	}

	@Test
	public void testLeapDayBefore8pm() throws Exception {

		testBefore8pm(LEAP_DAY, year, februaryMonth, leapDay);
	}
	
	@Test
	public void testLeapDayAfter8pm() throws Exception {

		testAfter8pm(LEAP_DAY, year, februaryMonth, leapDay);
	}

	@Test
	public void testDayAfterLeapDayBefore8pm() throws Exception {

		testBefore8pm(DAY_AFTER_LEAP_DAY, year, marchMonth, firstOfMonth);
	}
	
	@Test
	public void testDayAfterLeapDayAfter8pm() throws Exception {

		testAfter8pm(DAY_AFTER_LEAP_DAY, year, marchMonth, firstOfMonth);
	}

	/**
	 * Test for a day before 8pm
	 * 
	 * @param scenario
	 * @param year
	 * @param month
	 * @param day
	 * @throws Exception
	 */
	private void testBefore8pm(String scenario, int year, int month, int day) throws Exception {
		scenario = scenario + " before 8pm";

		DateTime testDateTime = new DateTime(year, month, day, hourBefore8pm, minute);

		CutoffDateRequest ecr = testGo(scenario, testDateTime);

		// Assert that the ECR's date is the prior day at 8pm
		DateTime expectedDateTime = testDateTime.minusDays(1).withTime(hour8pm, 0, 0, 0);

		Assert.assertEquals(getFailureMessage(scenario, expectedDateTime, ecr.getCutoffTime()), expectedDateTime.getMillis(), ecr
				.getCutoffTime().getMillis());
	}

	/**
	 * Test for a day after 8pm 
	 * 
	 * @param scenario
	 * @param year
	 * @param month
	 * @param day
	 * @throws Exception
	 */
	private void testAfter8pm(String scenario, int year, int month, int day) throws Exception {
		scenario = scenario + " after 8pm";

		DateTime testDateTime = new DateTime(year, month, day, hourAfter8pm, minute);

		CutoffDateRequest ecr = testGo(scenario, testDateTime);

		// Assert that the ECR's date is the input day at 8pm
		DateTime expectedDateTime = testDateTime.withTime(hour8pm, 0,  0,  0);

		Assert.assertEquals(getFailureMessage(scenario, expectedDateTime, ecr.getCutoffTime()), expectedDateTime.getMillis(), ecr
				.getCutoffTime().getMillis());
	}

	/**
	 * Actually does up the test
	 * 
	 * @param scenario
	 * @param testDateTime
	 * @return
	 * @throws Exception
	 */
	private CutoffDateRequest testGo(String scenario, DateTime testDateTime) throws Exception {

		DateTimeUtils.setCurrentMillisFixed(testDateTime.getMillis());
		
		CutoffDateRequest ecr = new CutoffDateRequest();

		// Old method
		//CutoffDateDelegate.createEFTNachaFile(ecr);

		// New method
		CutoffDateDelegate.eftCapture(ecr);
		
		System.out.println(scenario + " EFT Capture cut off time: " + jodaFormat.print(ecr.getCutoffTime()));

		return (ecr);
	}

	/**
	 * Returns a formatted failure message
	 * 
	 * @param scenario
	 * @param expectedDateTime
	 * @param actualDateTime
	 * @return
	 */
	private String getFailureMessage(String scenario, DateTime expectedDateTime, DateTime actualDate) {
		StringBuilder sb = new StringBuilder("Failure: [scenario=");

		sb.append(scenario).append(", expected=").append(jodaFormat.print(expectedDateTime)).append(", actual=")
				.append(jodaFormat.print(actualDate)).append("]");

		return sb.toString();
	}
}
