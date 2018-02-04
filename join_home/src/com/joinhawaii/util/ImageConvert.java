package com.joinhawaii.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageConvert {
	
	private final static int imgWidth = 600;
	
	/**
	 * ?ù¥ÎØ∏Ï?? ?Üí?ù¥*?è≠ Î≥?Í≤? ?óÜ?ù¥ ??ÑÎ¶¨?ã∞Îß? ?ÇÆÏ∂? ?ö©?üâ?ùÑ Ï§ÑÏûÑ
	 * @param orgFile
	 * @param retFileNm
	 * @return
	 */
	public File getCompressImg(File orgFile, String retFileNm){
		File retImgFile = new File(retFileNm);
		
		try{
			
			InputStream is = new FileInputStream(orgFile);
			OutputStream os = new FileOutputStream(retImgFile);
			float quality = 0.3f;
			
			BufferedImage bufImg = ImageIO.read(is);
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
			
			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			
			writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();
			
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
			
			writer.write(null, new IIOImage(bufImg, null, null), param);
			
			is.close();
			os.close();
			ios.close();
			writer.dispose();
			 
		} catch (Exception e) {
			e.printStackTrace();
			retImgFile = orgFile;
		} 
		
		return retImgFile;
	}
	
	/**
	 * ?ù¥ÎØ∏Ï??Í∞? ?Å∞ Í≤ΩÏö∞ 600?úºÎ°? ?è≠?ùÑ Ï§ÑÏù¥Í≥? ?èô?ùº ÎπÑÏú®Î°? ?Üí?ù¥?èÑ Ï§ÑÏûÑ.
	 * @param orgFile
	 * @param retFileNm
	 * @return
	 */
	public File getResizeImg(File orgFile, String retFileNm){
		return getResizeImg(orgFile, retFileNm, imgWidth);
	}
	
	
	/**
	 * Ïß??†ï?ïú ?ù¥ÎØ∏Ï?? ?è≠ ?Ç¨?ù¥Ïß?Î≥¥Îã§ ?Å∞ Í≤ΩÏö∞ ?ï¥?ãπ ?è≠Í≥? ?Üí?ù¥Î•? ?èô?ùº?ïú ÎπÑÏú®Î°? Ï§ÑÏûÑ. 
	 * @param orgFile
	 * @param retFileNm
	 * @param imgWidthSize
	 * @return
	 */
	public File getResizeImg(File orgFile, String retFileNm, int imgWidthSize){
		File retImgFile = new File(retFileNm);
		String fileExt = retFileNm.substring(retFileNm.lastIndexOf('.')+1);
		
		FileOutputStream fos = null;
		try{
			BufferedImage orgImg = ImageIO.read(orgFile);
			
			int orgWidth = orgImg.getWidth();
			int orgHeight = orgImg.getHeight();
			
			int destHeight = 0;
			int destWidth = 0;
			
			if (orgWidth > imgWidthSize){
				destWidth = imgWidthSize;
				destHeight = (int)((float)orgHeight * ((float)imgWidthSize/(float)orgWidth));
			} else {
				destHeight = orgHeight;
				destWidth = orgWidth;
			}
			BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = destImg.createGraphics();
			graphic.drawImage(orgImg, 0, 0, destWidth, destHeight, null);
			
			fos = new FileOutputStream(retImgFile);
			ImageIO.write(destImg, fileExt, fos);
			
		} catch (Exception e){
			e.printStackTrace();
			retImgFile = orgFile;
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return retImgFile;
	}
}
