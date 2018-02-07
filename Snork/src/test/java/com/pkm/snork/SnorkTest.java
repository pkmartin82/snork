package com.pkm.snork;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.pkm.snork.Snork;

public class SnorkTest {

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

	@Test
	public void testCheckNumber() {

		System.err.println(">>>" + name.getMethodName());
		
		Snork snork = new Snork();
		
		String s = "22243157320657";

		Long l = Long.valueOf(s);

		Integer i = l.intValue();

		System.out.println(s);
		System.out.println(l);
		System.out.println(i);
		

		Integer parsedInteger = snork.parseCheckNumber(s);
		
		System.out.println(parsedInteger);
	}

	@Test
	public void testCheckNumberWithSpaces() {

		System.err.println(">>>" + name.getMethodName());

		Snork snork = new Snork();
		
		String s = "1157320657                                    ";

		System.out.println(s);
		
		Integer parsedInteger = snork.parseCheckNumber(s);
		
		System.out.println(parsedInteger);
	}
	
	@Test
	public void testCheckNumberOnlySpaces() {

		System.err.println(">>>" + name.getMethodName());

		Snork snork = new Snork();
		
		String s = "                              ";

		System.out.println(s);
		
		Integer parsedInteger = snork.parseCheckNumber(s);
		
		System.out.println(parsedInteger);
	}

	@Test
	public void testBlankCheckNumber() {

		System.err.println(">>>" + name.getMethodName());

		Snork snork = new Snork();
		
		String s = "";

		System.out.println(s);
		
		Integer parsedInteger = snork.parseCheckNumber(s);
		
		System.out.println(parsedInteger);
	}

	@Test(expected=NullPointerException.class)
	public void testNullCheckNumber() {

		System.err.println(">>>" + name.getMethodName());

		Snork snork = new Snork();
		
		String s = null;

		System.out.println(s);
		
		Integer parsedInteger = snork.parseCheckNumber(s);
		
		System.out.println(parsedInteger);
	}

	@Test
	public void testCheckNumberLotsOfPreceedingZeroes() {

		System.err.println(">>>" + name.getMethodName());

		Snork snork = new Snork();
		
		String s = "00000000000000000000000000000000000000000000000000001234";

		System.out.println(s);
		
		Integer parsedInteger = snork.parseCheckNumber(s);
		
		System.out.println(parsedInteger);
	}
}
