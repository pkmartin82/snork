package com.pkm.snork.capture.cutoffdate;

import java.util.Date;

import org.joda.time.DateTime;

public class CutoffDateDelegate {

	public CutoffDateDelegate() {
		// TODO Auto-generated constructor stub
	}

	public static void eftCapture(CutoffDateRequest request) throws Exception {
		populateCutoffDate(request);
		CutoffDateDelegateUtil util = createCutoffDateDelegateUtil();
		util.captureEftTransactions(request);
	}

	public static void createEFTNachaFile(CutoffDateRequest request) throws Exception {
		populateCutoffDate_Old(request);
		CutoffDateDelegateUtil util = createCutoffDateDelegateUtil();
		util.createEFTNachaFile(request);
	}

	private static CutoffDateDelegateUtil createCutoffDateDelegateUtil() {
		return (new CutoffDateDelegateUtil());
	}
	private static void populateCutoffDate(CutoffDateRequest request) {

		if (request.getCutoffTime() == null) {

			DateTime today = DateTime.now().withTimeAtStartOfDay();

			DateTime today8pm = today.withHourOfDay(20);
			DateTime yesterday8pm = today8pm.minusDays(1);

			Date cutoffTime = null;

			// if it is before 8pm...
			if (DateTime.now().isBefore(today8pm)) {

				// ...cutoffTime = yesterday at 8pm
				cutoffTime = yesterday8pm.toGregorianCalendar().getTime();
			} else {

				// ...else it is after 8pm, so cutoffTime = today at 8pm
				cutoffTime = today8pm.toGregorianCalendar().getTime();
			}

			request.setCutoffTime(cutoffTime);
		}
	}

	private static void populateCutoffDate_Old(CutoffDateRequest request) {

		/*
		 * if(request.getEftBusinessId() == null) { throw new
		 * IllegalArgumentException("businessID cannot be null"); }
		 */

		if (request.getCutoffTime() == null) {

			DateTime todayDate = new DateTime().withTimeAtStartOfDay();

			DateTime todayEightPM = todayDate.plusHours(20);
			DateTime yestEightPM = todayEightPM.minusDays(1);

			Date cutoffTime = null;
			DateTime currentTime = new DateTime().toDateTime();
			// currentTime = new
			// DateTime().toDateMidnight().toDateTime().plusHours(21);

			if (currentTime.isBefore(todayEightPM.toGregorianCalendar().getTime().getTime())) {
				cutoffTime = yestEightPM.toGregorianCalendar().getTime();
			} else {
				cutoffTime = todayEightPM.toGregorianCalendar().getTime();
			}

			request.setCutoffTime(cutoffTime);
		}
	}
}
