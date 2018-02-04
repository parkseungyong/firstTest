package com.joinhawaii.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.joinhawaii.common.JoinHawaiiDbFieldConstants;
import com.joinhawaii.common.JoinHawaiiMapConstants;

public class ImageUtil {
	
	private static final int imgWidth = 600;
	//private static final String IMAGE_UPLOAD_PATH = "/img/picture/";
	private static final String img_upload_path = "/img/picture/"; 
//	private static final String img_temp_path = "/img/temp/";
	private static final String slash = "/";
	private static final String dot = ".";
	private static final String def_ext = "gif";
	private static final String date_format = "yyyyMMddHHmmssSSS";
	
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
	
	
	public String uplaodImage(MultipartFile mFile, String contextPath, String dir){

		String img_uri = img_upload_path + dir + slash;
		String img_path = contextPath + img_uri;
		String new_fileName = getFileName() + dot + getFileExtension(mFile.getOriginalFilename());
		
		String tmp_pathfileName = contextPath + img_upload_path + mFile.getOriginalFilename();
		
		try {
			if (!mFile.isEmpty()){
				File dirFile = new File(img_path);
				if (!dirFile.exists()) dirFile.mkdirs();
				
				File tempFile = new File(tmp_pathfileName);
				mFile.transferTo(tempFile);
				
				getResizeImg(tempFile, img_path + new_fileName);
				tempFile.delete();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return img_uri + new_fileName;
	}
	
	private String getFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(date_format);
		return df.format(cal.getTime()) + (new Random().nextInt(99));
	}

	
	private String getFileExtension(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos < 0) return def_ext;
		else return fileName.substring(pos+1,fileName.length());
	}
}
