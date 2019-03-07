package com.pkm.snork.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UrlEncoderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		String baseUrl = "https://bc3.uat.afford.com/ECM/PayNow?PaymentData=";
		String paymentData = "{\"StoreID\":\"243\",\"Person\":{\"FirstName\":\"Intell\",\"MiddleInitial\":\"X\",\"LastName\":\"Igent\"},\"Billing\":{\"Person\":{\"FirstName\":\"Intell\",\"MiddleInitial\":\"I\",\"LastName\":\"Gent\"},\"Address1\":\"171%20Service%20Rd\",\"Address2\":\"Suite%20200\",\"City\":\"Warwick\", \"State\":\"RI\",\"Zipcode\":\"02886\",\"Country\":\"USA\",\"Phone\":\"4019213700\",\"Email\":\"intelligentlink.com\"},\"TransInfo\":[{\"Value\":\"J09999999\",\"Code\":\"STUID\"}],\"Item\":[{\"Price\":200.46,\"Code\":\"FUDGE\"}],\"AllowItemChanges\":\"false\",\"PaymentMethod\":\"BankAccount\",\"ConfirmationUrlUsage\":\"PayNowSuccess\",\"ReferrerTransactionId\":\"9234\"}";
		

		//  The simple URL
		String simpleUrl = baseUrl + paymentData;
		System.out.println("SimpleURL\t\t| " + simpleUrl);

		//  The entire URL encoded
		String allEncodedUrl = null;
		try {
			allEncodedUrl = URLEncoder.encode(simpleUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Caught Exception while encoding all the URL: " + e.toString());
		}
		System.out.println("AllEncodedURL\t\t| " + allEncodedUrl);
		
		//  The simple URL with an encoded query string only
		String queryStringEncodedUrl = null;
		try {
			queryStringEncodedUrl = baseUrl + URLEncoder.encode(paymentData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Caught Exception while encoding queryString: " + e.toString());
		}
		System.out.println("QueryStringURL\t\t| " + queryStringEncodedUrl);
		assertNotEquals(queryStringEncodedUrl, allEncodedUrl);

	}

}
