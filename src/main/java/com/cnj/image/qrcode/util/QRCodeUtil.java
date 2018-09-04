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
 * ��ά�빤�߰��� ����javase 2.2��core2.2
 * write������bufferedImage�����ɡ�д�����������File</br>
 * gen����ֻ����bufferedImage�����ɲ���������bufferedImage
 * 
 * @author czz
 */
public  class QRCodeUtil {
	
	  /**
		 * ���ݲ������ɶ�ά��ͼƬ��
		 * 
		 * @param imgCharactCode
		 *            �ַ�����, Ĭ��Ϊ:UTF-8.
		 * @param imgWidth
		 *            ͼƬ���, Ĭ��Ϊ: 300px
		 * @param imgHeight
		 *            ͼƬ�߶�, Ĭ��Ϊ: 300px
		 * @param qrContent
		 *            ��ά������
		 * @param logoFile 
		 * 			  ��ά��logoͼƬ�ļ�����
		 * 
		 * @return ��ά��ͼƬ��imgage����
		 */
	    public static BufferedImage genQRCodeImg(String imgCharactCode, int imgWidth, int imgHeight, String qrContent){
	    	if (qrContent == null || "".equals(qrContent)) {
				return null;
			}

			BitMatrix bitMatrix = null;
	    	// �����ά������Ĺ�ϣӳ���
			HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			// ���뷽ʽ��֧������
			imgCharactCode = (imgCharactCode == null || "".equals(imgCharactCode) ? "UTF-8" : imgCharactCode);
			hints.put(EncodeHintType.CHARACTER_SET, imgCharactCode);
			// �ݴ�ȼ�
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			// ��ά��߾�
			hints.put(EncodeHintType.MARGIN, 1);
			
			return genQRCodeImg(hints,imgWidth, imgHeight, qrContent);
	    	
	    }
	    /**
	     * ���ɶ�ά��ͼƬ��imgage����
	     * 
	     * @param hints 
	     * 			�����ά������Ĺ�ϣӳ���
		 * @param imgWidth
		 *            ͼƬ���, Ĭ��Ϊ: 300px
		 * @param imgHeight
		 *            ͼƬ�߶�, Ĭ��Ϊ: 300px
		 * @param qrContent
		 *            ��ά������
		 * @param logoFile 
		 * 			  ��ά��logoͼƬ�ļ�����
	     * @return ��ά��ͼƬ��imgage����
	     */
		public static BufferedImage genQRCodeImg(HashMap<EncodeHintType, Object> hints, int imgWidth, int imgHeight,
				 String qrContent) {
			BitMatrix bitMatrix;
			BufferedImage bimg=null;
			try {
				// ���ɵ���
				imgWidth = (imgWidth <= 0 ? 300 : imgWidth); // Ĭ��Ϊ300px
				imgHeight = (imgHeight <= 0 ? 300 : imgHeight); // Ĭ��Ϊ300px
				bitMatrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, imgWidth, imgHeight, hints);
				bimg=MatrixToImageWriter.toBufferedImage(bitMatrix);
			} catch (WriterException e) {
				e.printStackTrace();
			}
			return bimg;
		}
		/**
		 * Ϊ��ά�����logo
		 * 
		 * @param qrcodeBI 
		 * 			ͼƬ����
		 * @param logoFile 
		 * 			logo�ļ�
		 * @return ��logo�Ķ�ά��ͼƬ����
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
		 * ���ݲ������ɴ�logo�Ķ�ά��ͼƬ��
		 * 
		 * @param imgCharactCode
		 *            �ַ�����, Ĭ��Ϊ:UTF-8.
		 * @param imgWidth
		 *            ͼƬ���, Ĭ��Ϊ: 300px
		 * @param imgHeight
		 *            ͼƬ�߶�, Ĭ��Ϊ: 300px
		 * @param qrContent
		 *            ��ά������
		 * @param logoFile 
		 * 			  ��ά��logoͼƬ�ļ�����
		 * 
		 * @return ��logo�Ķ�ά��ͼƬ��imgage����
		 */
		public static BufferedImage genQRCodeWithLogo(String imgCharactCode, int imgWidth, int imgHeight, String qrContent,File logoFile){
			BufferedImage qrcode=genQRCodeImg(imgCharactCode, imgWidth, imgHeight, qrContent);
			return mergeQRCodeWithLogo(qrcode, logoFile);
		}
		/**
		 * ���ݲ������ɴ�logo�Ķ�ά��ͼƬ��
		 * 
		 * @param imgCharactCode
		 *            �ַ�����, Ĭ��Ϊ:UTF-8.
		 * @param imgWidth
		 *            ͼƬ���, Ĭ��Ϊ: 300px
		 * @param imgHeight
		 *            ͼƬ�߶�, Ĭ��Ϊ: 300px
		 * @param strImgFileFoler
		 *            ͼƬ�洢Ŀ¼
		 * @param imgFileName
		 *            ͼƬ����(�磺qrcode.png)
		 * @param qrContent
		 *            ��ά������
		 * @param logoFile 
		 * 			  ��ά��logoͼƬimgage����
		 * 
		 * @return ��logo�Ķ�ά��ͼƬ��image�������
		 */
		public static BufferedImage writeQRCodeWithLogo(String imgCharactCode, int imgWidth, int imgHeight, String strImgFileFoler,
				String imgFileName, String qrContent,File logoFile){
			BufferedImage qrcode=genQRCodeWithLogo(imgCharactCode, imgWidth, imgHeight, qrContent, logoFile);
			writeQrCodeImage(qrcode,strImgFileFoler,imgFileName);
			return qrcode;
		}
		/**
		 * ���ݲ������ɴ�logo�Ķ�ά��image�������
		 * 
		 * @param imgCharactCode
		 *            �ַ�����, Ĭ��Ϊ:UTF-8.
		 * @param imgWidth
		 *            ͼƬ���, Ĭ��Ϊ: 300px
		 * @param imgHeight
		 *            ͼƬ�߶�, Ĭ��Ϊ: 300px
		 * @param strImgFileFoler
		 *            ͼƬ�洢Ŀ¼
		 * @param imgFileName
		 *            ͼƬ����(�磺qrcode.png)
		 * @param qrContent
		 *            ��ά������
		 * 
		 * @return ��logo�Ķ�ά��ͼƬ��image�������
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
		 * 			��ά��ͼƬ��imgage����
		 * @param bitMatrix
		 * 		
		 * @param strImgFileFoler
		 * 			 ͼƬ�洢Ŀ¼
		 * @param imgFileName
		 * 			  ͼƬ����(�磺qrcode.png)
		 * @return ��ά��ͼƬ��imgage����
		 */
		public static File writeQrCodeImage(BufferedImage qrcode,String strImgFileFoler,String imgFileName){
			// ����Ŀ¼
			File fileImgFoler = new File(strImgFileFoler);
			if (!fileImgFoler.exists()) {
				fileImgFoler.mkdir();
			}
			// ͼƬ
			String strImgFullName = fileImgFoler.getPath() + "/" + imgFileName;
			File imgFullFile = new File(strImgFullName);
			
			String imgFormat = imgFileName.substring(imgFileName.lastIndexOf(".") + 1);
			// ����ļ�
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






