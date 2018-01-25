package com.pkm.snork.image;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class SnorkImageUtil {

	public void convertToRGB(File originalImageFile, File newImageFile) throws IOException {

		// Find a suitable ImageReader
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("JPEG");
		ImageReader reader = null;

		while (readers.hasNext()) {
			reader = readers.next();
			if (reader.canReadRaster()) {
				break;
			}
		}

		// Stream the image file (the original CMYK image)
		ImageInputStream input = ImageIO.createImageInputStream(originalImageFile);
		reader.setInput(input);

		// Read the image raster
		Raster raster = reader.readRaster(0, null);

		// Create a new RGB image
		BufferedImage bi = new BufferedImage(raster.getWidth(), raster.getHeight(), 
				//BufferedImage.TYPE_4BYTE_ABGR  //Turned White into Gray
				BufferedImage.TYPE_4BYTE_ABGR_PRE
				);

		// Fill the new image with the old raster
		bi.getRaster().setRect(raster);

		ImageIO.write(bi, "JPG", newImageFile);

		System.out.println("New Image Object Created");
	}
}
