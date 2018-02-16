package com.pkm.snork.jira;

import java.lang.reflect.Type;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pkm.snork.SnorkRestClientException;
import com.pkm.snork.jira.dto.Fields;
import com.pkm.snork.jira.dto.IssueType;
import com.pkm.snork.jira.dto.Project;

public class SnorkJiraClientTest {

	public final static int ITEM_REQUEST_ISSUE_TYPE = 9;
	public final static String ITEM_REQUEST_PROJECT = "10901";

	protected Logger logger;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		logger = Logger.getLogger(this.getClass());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddToJira() {

		JiraIssueDto itemRequest = this.makeJiraItemRequestDto();

		SnorkJiraClient sjc = new SnorkJiraClient();

		Gson gson = getGson();

		String jsonItemRequest = gson.toJson(itemRequest);

		try {
			sjc.addToJira(jsonItemRequest);
		} catch (SnorkRestClientException e) {

			logger.error(String.format("Caught Exception: %s", e.toString()), e);
		}

	}

	@Test
	public void testJiraIssueDto() {
		JiraIssueDto issue = new JiraIssueDto();
		IssueType itemRequestType = new IssueType(ITEM_REQUEST_ISSUE_TYPE);
		Project project = new Project(ITEM_REQUEST_PROJECT);

		Fields fields = new Fields();
		fields.setDescription("What up!");
		fields.setIssuetype(itemRequestType);
		fields.setProject(project);
		fields.setSummary("This Jira Item Request was created automatically!");

		issue.setFields(fields);

		Gson gson = getGson();

		String jsonIssue = gson.toJson(issue);

		logger.info(jsonIssue);

	}

	private JiraIssueDto makeJiraItemRequestDto() {
		JiraIssueDto issue = new JiraIssueDto();

		IssueType itemRequestType = new IssueType(ITEM_REQUEST_ISSUE_TYPE);
		Project project = new Project(ITEM_REQUEST_PROJECT);

		Fields fields = new Fields();
		fields.setDescription("What up!");
		fields.setIssuetype(itemRequestType);
		fields.setProject(project);
		fields.setSummary("This Jira Item Request was created automatically!");

		issue.setFields(fields);

		return (issue);
	}

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
}
