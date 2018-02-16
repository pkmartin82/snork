package com.pkm.snork.rest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pkm.snork.SnorkRestClientException;

public abstract class RestClient {

	protected final static String APP_JSON = "application/json";

	private final static String HTTP_RESPONSE_FORMAT = "HTTP {0} Response Code: {1}{2}";
	private final static String HTTP_RESPONSE_FULL_FORMAT = ", Response: {0}";

	protected Logger logger;

	/**
	 * Default Constructor
	 */
	public RestClient() {
		logger = Logger.getLogger(this.getClass());
	}

	/**
	 * Performs an HTTP GET on a URL and returns a JSON String
	 * 
	 * @return
	 */
	protected String httpGetString(String url, String mediaType) throws SnorkRestClientException {

		// Validate the input URL
		validateUrl(url);

		CloseableHttpClient httpClient = null;
		String httpGetResponseString = null;

		try {

			// Create an HTTP Client
			httpClient = createHttpClient();

			HttpGet getRequest = new HttpGet(url);

			getRequest.addHeader("accept", mediaType);

			logger.info("Sending HTTP-GET Request: " + getRequest.toString());

			CloseableHttpResponse response = httpClient.execute(getRequest);

			// Save the HTTP Response Output
			httpGetResponseString = parseAndValidateResponse("GET", response);

		} catch (IOException e) {
			throw new SnorkRestClientException(e);
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ioe) {
					throw new SnorkRestClientException(ioe);
				}
			}
		}

		return (httpGetResponseString);
	}

	/**
	 * Performs an HTTP POST on a URL and returns a JSON String
	 * 
	 * @return
	 */
	protected String httpPostString(String url, String mediaType, String stringToPost) throws SnorkRestClientException {

		// Validate the input URL
		validateUrl(url);

		CloseableHttpClient httpClient = null;
		String httpPostResponseString = null;

		try {

			// Create an HTTP Client
			httpClient = createHttpClient();

			// Create an HTTP Post Request
			HttpPost postRequest = new HttpPost(url);

			// Insert the string to post into the POST Request
			StringEntity input = new StringEntity(stringToPost);
			input.setContentType(mediaType);
			postRequest.setEntity(input);
			try {
				postRequest.addHeader(new BasicScheme().authenticate(this.getCredentialProvider().getCredentials(AuthScope.ANY), (HttpRequest) postRequest));
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.info("Sending HTTP-POST Request: " + postRequest.toString());

			HttpClientContext context = this.getHttpClientContext();

			CloseableHttpResponse response;

			if (context != null) {
				response = httpClient.execute(postRequest, context);
			} else {
				response = httpClient.execute(postRequest);
			}

			// Save the HTTP Response Output
			httpPostResponseString = parseAndValidateResponse("POST", response);

		} catch (IOException e) {
			throw new SnorkRestClientException(e);
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ioe) {
					throw new SnorkRestClientException(ioe);
				}
			}
		}

		return (httpPostResponseString);
	}

	/**
	 * Performs an HTTP PUT on a URL and returns a JSON String
	 * 
	 * @return
	 */
	protected String httpPutString(String url, String mediaType, String stringToPut) throws SnorkRestClientException {

		// Validate the input URL
		validateUrl(url);

		CloseableHttpClient httpClient = null;
		String httpPutResponseString = null;

		try {

			// Create an HTTP Client
			httpClient = createHttpClient();

			// Create an HTTP Put Request
			HttpPut putRequest = new HttpPut(url);

			// Insert the string to put into the PUT Request
			StringEntity input = new StringEntity(stringToPut);
			input.setContentType(mediaType);
			putRequest.setEntity(input);

			logger.info("Sending HTTP-PUT Request: " + putRequest.toString());

			CloseableHttpResponse response = httpClient.execute(putRequest);

			// Save the HTTP Response Output
			httpPutResponseString = parseAndValidateResponse("PUT", response);

		} catch (IOException e) {
			throw new SnorkRestClientException(e);
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ioe) {
					throw new SnorkRestClientException(ioe);
				}
			}
		}

		return (httpPutResponseString);
	}

	/**
	 * Performs an HTTP DELETE on a URL and returns a JSON String
	 * 
	 * @return
	 */
	protected String httpDeleteString(String url, String mediaType, String stringToDelete)
			throws SnorkRestClientException {

		// Validate the input URL
		validateUrl(url);

		CloseableHttpClient httpClient = null;
		String httpDeleteResponseString = null;

		try {

			// Create an HTTP Client
			httpClient = createHttpClient();

			// Create an HTTP DELETE Request
			HttpDelete deleteRequest = new HttpDelete(url);

			logger.info("Sending HTTP-DELETE Request: " + deleteRequest.toString());

			CloseableHttpResponse response = httpClient.execute(deleteRequest);

			// Save the HTTP Response Output
			httpDeleteResponseString = parseAndValidateResponse("DELETE", response);

		} catch (IOException e) {
			throw new SnorkRestClientException(e);
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ioe) {
					throw new SnorkRestClientException(ioe);
				}
			}
		}

		return (httpDeleteResponseString);
	}

	/**
	 * Creates a CloseableHttpClient
	 * 
	 * @return a CloseableHttpClient
	 */
	protected CloseableHttpClient createHttpClient() {
		CredentialsProvider creds = this.getCredentialProvider();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(creds).build();

		return (httpClient);
	}

	/**
	 * Retrieves a Credentials Provider object specific to this client
	 */
	protected abstract CredentialsProvider getCredentialProvider();

	/**
	 * Retrieves a Http Client Context specific to this client
	 */
	protected abstract HttpClientContext getHttpClientContext();

	/**
	 * Returns a Gson object that has been configured to handle the data our rest
	 * client is returning
	 * 
	 * @return Gson
	 */
	protected Gson getGson() {

		// Creates the json object which will manage the information
		// received
		GsonBuilder gsonBuilder = new GsonBuilder();

		// Register an adapter to manage the date types as long values
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});

		// Set DateFormat to an 'easily digestible' format that can be
		// deserialized by a application server
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		Gson gSon = gsonBuilder.create();

		return (gSon);
	}

	/**
	 * Validate that the input URL is in fact a valid URL, or throw an
	 * HeroRestClientException
	 * 
	 * @param url
	 * @throws SnorkRestClientException
	 */
	private void validateUrl(String url) throws SnorkRestClientException {
		try {

			// create a URL object based on the input URL string
			@SuppressWarnings("unused")
			URL validatedUrl = new URL(url);
		} catch (MalformedURLException mue) {
			throw new SnorkRestClientException(new IllegalArgumentException(mue));
		}
	}

	/**
	 * Validate that the input response status code is a successful HTTP response,
	 * or throw an HeroRestClientException
	 * 
	 * @param httpOperation
	 * 
	 * @param responseStatusCode
	 * @throws SnorkRestClientException
	 */
	private String parseAndValidateResponse(String httpOperation, HttpResponse response)
			throws SnorkRestClientException {

		// Log the response information
		logger.debug((response != null ? response.toString() : null));

		// Get the response entity in the form of a String
		HttpEntity entity = response.getEntity();
		String responseEntity;
		try {
			responseEntity = (entity != null ? EntityUtils.toString(entity) : null);
		} catch (ParseException | IOException e) {
			throw new SnorkRestClientException(e);
		}

		// Log the HTTP Response
		logger.info("Received HttpResponse: " + responseEntity);

		// Get the HTTP Status Code
		int responseStatusCode = response.getStatusLine().getStatusCode();

		// Determine if HTTP Response is success or failure
		if ((responseStatusCode < 200) || (responseStatusCode >= 400)) {
			logger.warn(MessageFormat.format(HTTP_RESPONSE_FORMAT, httpOperation, responseStatusCode,
					MessageFormat.format(HTTP_RESPONSE_FULL_FORMAT, responseEntity)));
			throw new SnorkRestClientException("Failed : HTTP error code : " + responseStatusCode);
		} else {
			logger.debug(MessageFormat.format(HTTP_RESPONSE_FORMAT, httpOperation, responseStatusCode, ""));
		}

		return (responseEntity);
	}
}