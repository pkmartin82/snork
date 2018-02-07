package com.pkm.snork.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.pkm.snork.test.custom.Repeat;
import com.pkm.snork.test.custom.RepeatRule;

@SuppressWarnings("unused")
public class UrlHitter {

	private final String USER_AGENT = "Mozilla/5.0";
	private static int testCount = 0;

	@Rule
	public TestName name = new TestName();
	
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

	// HTTP GET request
	@Rule
	public RepeatRule repeatRule = new RepeatRule();
	
    @Test
    @Repeat(-1)
	public void sendGet() throws Exception {

		String url 
//			= "http://blackbaud1qa:9180/tmsportal/";
			= "https://sfs.iuat.afford.com/RCW/Home/RefreshDataCache?schoolId=34";
//		= "https://trevecca.uat.afford.com";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		System.out.println("\nSending " + ++testCount + " 'GET' request to URL : " + url);
		System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

//		System.out.println(result.toString());

	}

	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
		urlParameters.add(new BasicNameValuePair("cn", ""));
		urlParameters.add(new BasicNameValuePair("locale", ""));
		urlParameters.add(new BasicNameValuePair("caller", ""));
		urlParameters.add(new BasicNameValuePair("num", "12345"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());

	}

}