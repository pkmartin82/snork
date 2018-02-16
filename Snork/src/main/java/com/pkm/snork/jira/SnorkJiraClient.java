package com.pkm.snork.jira;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

import com.pkm.snork.SnorkRestClientException;
import com.pkm.snork.rest.RestClient;

public class SnorkJiraClient extends RestClient {

	private String requestBaseUrl = "https://fmdjira.atlassian.net/rest/api/2/issue";

	/**
	 * Default Constructor
	 */
	public SnorkJiraClient() {
		super();
	}

	public String addToJira(String jsonString) throws SnorkRestClientException {

		logger.debug(String.format("Adding to Jira: %s ", jsonString));

		// Concatenate the Base URL with the Target URL
		String url = this.requestBaseUrl;

		// Retrieve a JSON String from the HTTP-POST
		String responseJsonString = httpPostString(url, APP_JSON, jsonString);

		logger.debug("Done adding to Jira!");

		// Return the added Hero
		return responseJsonString;
	}

	@Override
	protected CredentialsProvider getCredentialProvider() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("pmartin@afford.com", "WhyDidIHaveToChangeMyPassw0rd?");
		provider.setCredentials(AuthScope.ANY, credentials);

		return (provider);
	}

	@Override
	protected HttpClientContext getHttpClientContext() {
		HttpHost targetHost = new HttpHost("fmdjira.atlassian.net", 8443, "https");
		 
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());
		 
		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(getCredentialProvider());
		context.setAuthCache(authCache);

		//return context;
		return null;
	}
}
