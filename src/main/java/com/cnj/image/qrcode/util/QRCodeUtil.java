package com.cnj.image.qrcode.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具包。 依赖javase 2.2，core2.2
 * write方法做bufferedImage的生成、写入操作并返回File</br>
 * gen方法只会做bufferedImage的生成操作并返回bufferedImage
 * 
 * @author czz
 */
public  class QRCodeUtil {
	
	  /**
		 * 根据参数生成二维码图片。
		 * 
		 * @param imgCharactCode
		 *            字符编码, 默认为:UTF-8.
		 * @param imgWidth
		 *            图片宽度, 默认为: 300px
		 * @param imgHeight
		 *            图片高度, 默认为: 300px
		 * @param qrContent
		 *            二维码内容
		 * @param logoFile 
		 * 			  二维码logo图片文件对象
		 * 
		 * @return 二维码图片的imgage缓冲
		 */
	    public static BufferedImage genQRCodeImg(String imgCharactCode, int imgWidth, int imgHeight, String qrContent){
	    	if (qrContent == null || "".equals(qrContent)) {
				return null;
			}

			BitMatrix bitMatrix = null;
	    	// 定义二维码参数的哈希映射表
			HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			// 编码方式，支持中文
			imgCharactCode = (imgCharactCode == null || "".equals(imgCharactCode) ? "UTF-8" : imgCharactCode);
			hints.put(EncodeHintType.CHARACTER_SET, imgCharactCode);
			// 容错等级
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			// 二维码边距
			hints.put(EncodeHintType.MARGIN, 1);
			
			return genQRCodeImg(hints,imgWidth, imgHeight, qrContent);
	    	
	    }
	    /**
	     * 生成二维码图片的imgage缓冲
	     * 
	     * @param hints 
	     * 			定义二维码参数的哈希映射表
		 * @param imgWidth
		 *            图片宽度, 默认为: 300px
		 * @param imgHeight
		 *            图片高度, 默认为: 300px
		 * @param qrContent
		 *            二维码内容
		 * @param logoFile 
		 * 			  二维码logo图片文件对象
	     * @return 二维码图片的imgage缓冲
	     */
		public static BufferedImage genQRCodeImg(HashMap<EncodeHintType, Object> hints, int imgWidth, int imgHeight,
				 String qrContent) {
			BitMatrix bitMatrix;
			BufferedImage bimg=null;
			try {
				// 生成点阵
				imgWidth = (imgWidth <= 0 ? 300 : imgWidth); // 默认为300px
				imgHeight = (imgHeight <= 0 ? 300 : imgHeight); // 默认为300px
				bitMatrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, imgWidth, imgHeight, hints);
				bimg=MatrixToImageWriter.toBufferedImage(bitMatrix);
			} catch (WriterException e) {
				e.printStackTrace();
			}
			return bimg;
		}
		/**
		 * 为二维码添加logo
		 * 
		 * @param qrcodeBI 
		 * 			图片缓冲
		 * @param logoFile 
		 * 			logo文件
		 * @return 带logo的二维码图片缓冲
		 */
		public static BufferedImage  mergeQRCodeWithLogo(BufferedImage qrcodeBI,File logoFile){
			
			BufferedImage logo;
			BufferedImage combined = null;
			try {
				logo = ImageIO.read(logoFile);
				int imgHeight=qrcodeBI.getHeight();
				int imgWidth=qrcodeBI.getWidth();
				int deltaHeight = imgHeight - logo.getHeight();
				int deltaWidth = imgWidth - logo.getWidth();
				combined = new BufferedImage(imgHeight, imgWidth, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = (Graphics2D) combined.getGraphics();
				g.drawImage(qrcodeBI, 0, 0, null);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g.drawImage(logo, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return combined;
		}
		/**
		 * 根据参数生成带logo的二维码图片。
		 * 
		 * @param imgCharactCode
		 *            字符编码, 默认为:UTF-8.
		 * @param imgWidth
		 *            图片宽度, 默认为: 300px
		 * @param imgHeight
		 *            图片高度, 默认为: 300px
		 * @param qrContent
		 *            二维码内容
		 * @param logoFile 
		 * 			  二维码logo图片文件对象
		 * 
		 * @return 带logo的二维码图片的imgage缓冲
		 */
		public static BufferedImage genQRCodeWithLogo(String imgCharactCode, int imgWidth, int imgHeight, String qrContent,File logoFile){
			BufferedImage qrcode=genQRCodeImg(imgCharactCode, imgWidth, imgHeight, qrContent);
			return mergeQRCodeWithLogo(qrcode, logoFile);
		}
		/**
		 * 根据参数生成带logo的二维码图片。
		 * 
		 * @param imgCharactCode
		 *            字符编码, 默认为:UTF-8.
		 * @param imgWidth
		 *            图片宽度, 默认为: 300px
		 * @param imgHeight
		 *            图片高度, 默认为: 300px
		 * @param strImgFileFoler
		 *            图片存储目录
		 * @param imgFileName
		 *            图片名称(如：qrcode.png)
		 * @param qrContent
		 *            二维码内容
		 * @param logoFile 
		 * 			  二维码logo图片imgage缓冲
		 * 
		 * @return 带logo的二维码图片的image缓冲对象
		 */
		public static BufferedImage writeQRCodeWithLogo(String imgCharactCode, int imgWidth, int imgHeight, String strImgFileFoler,
				String imgFileName, String qrContent,File logoFile){
			BufferedImage qrcode=genQRCodeWithLogo(imgCharactCode, imgWidth, imgHeight, qrContent, logoFile);
			writeQrCodeImage(qrcode,strImgFileFoler,imgFileName);
			return qrcode;
		}
		/**
		 * 根据参数生成带logo的二维码image缓冲对象
		 * 
		 * @param imgCharactCode
		 *            字符编码, 默认为:UTF-8.
		 * @param imgWidth
		 *            图片宽度, 默认为: 300px
		 * @param imgHeight
		 *            图片高度, 默认为: 300px
		 * @param strImgFileFoler
		 *            图片存储目录
		 * @param imgFileName
		 *            图片名称(如：qrcode.png)
		 * @param qrContent
		 *            二维码内容
		 * 
		 * @return 带logo的二维码图片的image缓冲对象
		 */
		public static BufferedImage writeQrCodeImage(String imgCharactCode, int imgWidth, int imgHeight, String strImgFileFoler,
				String imgFileName, String qrContent){
			BufferedImage qrcode=genQRCodeImg(imgCharactCode, imgWidth, imgHeight, qrContent);
			writeQrCodeImage(qrcode,strImgFileFoler,imgFileName);
			return qrcode;
		}
		/**
		 * 
		 * @param qrcode
		 * 			二维码图片的imgage缓冲
		 * @param bitMatrix
		 * 		
		 * @param strImgFileFoler
		 * 			 图片存储目录
		 * @param imgFileName
		 * 			  图片名称(如：qrcode.png)
		 * @return 二维码图片的imgage缓冲
		 */
		public static File writeQrCodeImage(BufferedImage qrcode,String strImgFileFoler,String imgFileName){
			// 创建目录
			File fileImgFoler = new File(strImgFileFoler);
			if (!fileImgFoler.exists()) {
				fileImgFoler.mkdir();
			}
			// 图片
			String strImgFullName = fileImgFoler.getPath() + "/" + imgFileName;
			File imgFullFile = new File(strImgFullName);
			
			String imgFormat = imgFileName.substring(imgFileName.lastIndexOf(".") + 1);
			// 输出文件
			try {
				ImageIO.write(qrcode, imgFormat, imgFullFile);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				fileImgFoler=null;
			}
			return imgFullFile;
		}
		public static void main(String[] args) {
			BufferedImage img= writeQRCodeWithLogo("utf-8",800, 800, "D:/", "t1.jpg", "123",new File("D:/projectcsei/AWS-Enterprise5.1/webserver/webapps/csei/tbs_img/csei_logo.gif"));
			BufferedImage img2=writeQrCodeImage("utf-8",800, 800, "D:/", "t2.jpg", "123");
		}
}






