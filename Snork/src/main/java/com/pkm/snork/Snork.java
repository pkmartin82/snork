package com.pkm.snork;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Snork {

	private final static String MASKED_PW = "********";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<Integer, String> employees = new HashMap<Integer, String>();
		
		for (int i = 0; i < 10; i++) {
			employees.put(i, "Employee[" + i + "]");
		}

		
		System.out.println(employees.toString().replace("Employee[8]", "sam was right"));
	}

	
	public Integer parseCheckNumber( String lockboxFileDetailRecordString ) {
		String checkNumberAsString = lockboxFileDetailRecordString.trim(); 
		Integer checkNumberAsInteger = 0;

		//  if the check number is null or empty-String...
		if (StringUtils.isEmpty(checkNumberAsString)) {

			//  ...set it to 0
			checkNumberAsInteger = 0;
		} else {

			//  ...else get the checkNumber as a Long...
			Long checkNumberAsLong = Long.valueOf(checkNumberAsString);

			//  ...and if it is greater than the largest Integer...
			if (checkNumberAsLong > Integer.MAX_VALUE) {

				checkNumberAsString = StringUtils.substring(checkNumberAsString, -9);

				checkNumberAsInteger = Integer.valueOf(checkNumberAsString);

//				//  ...set it to 0
//				checkNumberAsInteger = 0;
			} else {

				//  ...else set it to the Integer-value
				checkNumberAsInteger = checkNumberAsLong.intValue();
			}
		}		
		return checkNumberAsInteger;
	}
	
	private void test() {
		
	}
}
