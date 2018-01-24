package com.pkm.snork.server.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pkm.snork.server.ServerChecker;


public class ServerCheckerTest {

	private ServerChecker checker;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		checker = new ServerChecker();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		List<String> serverNames = getServerNames();

		for (String serverName : serverNames) {
			assertEquals(true, checker.doesServerExist(serverName));
		}
	}
	
	private List<String> getServerNames() {

		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();

		try {
			result = IOUtils.toString(classLoader.getResourceAsStream("servers.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String [] serverNameArray = result.split("\\r?\\n");

		List<String> serverNames = Arrays.asList(serverNameArray); 
		return (serverNames);
	}

}
