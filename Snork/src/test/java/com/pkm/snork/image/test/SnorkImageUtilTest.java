package com.pkm.snork.image.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.pkm.snork.image.SnorkImageUtil;

public class SnorkImageUtilTest {
	private final String CMYK_1 = "Huntington06102016_CMYK.jpg";
	
	private final String RGB_1 = "Huntington06102016_RGB.jpg";

	@Test
	public void testConvertToRGB() {

		Logger logger = Logger.getLogger(this.getClass());
		logger.info("Testing!!");
		
		File cmykFile = new File(getClass().getClassLoader().getResource(CMYK_1).getFile());

		assertNotNull(cmykFile);

		File rgbFile = new File(cmykFile.getParent(), RGB_1);

		SnorkImageUtil snork = new SnorkImageUtil();
		
		try {
			snork.convertToRGB(cmykFile, rgbFile);
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
		
		assertNotNull(rgbFile);
	}

}
