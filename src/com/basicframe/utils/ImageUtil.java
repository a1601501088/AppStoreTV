package com.basicframe.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;



/**
 * <p>Description: 图片处理</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class ImageUtil {
	
	private static final boolean USE_TRANSFORM = false;
	private static FileInputStream fis;
	
	/**
	 * 缩略图
	 * @param srcImgPath
	 * @param tow
	 * @param toh
	 * @param maxtow
	 * @param maxtoh
	 * @return
	 */
	public static boolean makeThumb(String srcImgPath, String destPath, int tow, int toh, int maxtow, int maxtoh) {
		boolean flag = false;
		File srcFile = new File(srcImgPath);
		if (!srcFile.exists()) {
			return flag;
		}
		File destFile = new File(destPath);
		if (tow < 60) {
			tow = 60;
		}
		if (toh < 60) {
			toh = 60;
		}
		boolean make_max = false;
		if (maxtow >= 300 && maxtoh >= 300) {
			make_max = true;
		}
		String srcImgType = CommonUtil.getImageType(srcFile);
		if ("gif".equals(srcImgType)) {
			make_max = false; 
		}
		try {
			BufferedImage srcImg = ImageIO.read(srcFile);
			float src_w = srcImg.getWidth();
			float src_h = srcImg.getHeight();
			if (src_w <= maxtow && src_h <= maxtoh){
				make_max = false;
			}
			float thumb_ratio = tow / toh; 
			float src_ratio = src_w / src_h; 
			if (thumb_ratio <= src_ratio) {
				toh = (int) (tow / src_ratio);
				maxtoh = (int) (maxtow * (src_h / src_w));
			} else {
				tow = (int) (toh * src_ratio);
				maxtow = (int) (maxtoh * (src_w / src_h));
			}
			if (src_w > tow || src_h > toh) {
				double x_ratio = (double) tow / src_w; 
				double y_ratio = (double) toh / src_h; 
				AffineTransform tx = new AffineTransform();
				tx.setToScale(x_ratio, y_ratio);
				BufferedImage thumbImg = new BufferedImage(tow, toh, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g2d = thumbImg.createGraphics();
				if (USE_TRANSFORM) {
					g2d.drawImage(srcImg, tx, null);
				} else {
					Image scaleImg = getScaledInstance(srcImg, tow, toh);
					g2d.drawImage(scaleImg, 0, 0, null);
				}
				g2d.dispose();
				ImageIO.write(thumbImg, "jpeg", destFile);
				if (make_max) {
					BufferedImage maxImg = new BufferedImage(maxtow, maxtoh, BufferedImage.TYPE_3BYTE_BGR);
					g2d = maxImg.createGraphics();
					if (USE_TRANSFORM) {
						g2d.drawImage(srcImg, tx, null);
					} else {
						Image scaleImg = getScaledInstance(srcImg, maxtow, maxtoh);
						g2d.drawImage(scaleImg, 0, 0, null);
					}
					g2d.dispose();
					ImageIO.write(maxImg, "jpeg", srcFile);
				}
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private static Image getScaledInstance(BufferedImage srcImage, int imageWidth, int imageHeight) {
		ImageFilter filter = new java.awt.image.AreaAveragingScaleFilter(imageWidth, imageHeight);
		ImageProducer prod = new FilteredImageSource(srcImage.getSource(), filter);
		Image newImage = Toolkit.getDefaultToolkit().createImage(prod);
		ImageIcon imageIcon = new ImageIcon(newImage);
		Image scaleImg = imageIcon.getImage();
		return scaleImg;
	}
	
	/**
	 * 添加图片水印
	 * 
	 * @param request
	 * 			request请求
	 * @param srcImg
	 * 			图片地址
	 */
	public static void makeWaterMark(HttpServletRequest request, String srcImg) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			File srcFile = new File(path + srcImg);
			String srcImgType = CommonUtil.getImageType(srcFile);
			if (srcImgType.equals("gif")) {
				byte[] bytes = new byte[1024];
				fis = new FileInputStream(path + srcImg);
				fis.read(bytes);
				String srcContent = new String(bytes, "UTF-8");
				if (srcContent.indexOf("XMP") != -1) {
					return;
				}
			}
			//水印图片
			String waterMark = path + "images/watermark.png";
			Image water = ImageIO.read(new File(waterMark));
			Image src = ImageIO.read(srcFile); 
			int water_w = water.getWidth(null);
			int water_h = water.getHeight(null);
			int src_w = src.getWidth(null);
			int src_h = src.getHeight(null);
			if ((src_w < water_w + 150) || (src_h < water_h + 150)) {
				return;
			}
			//水印位置(1:顶端居左,2:顶端居右,3:底端居左,4:底端居右)
			int waterMarkPos = 4;
			int x = 0, y = 0;
			switch (waterMarkPos) {
				case 1:
					x = 0;
					y = 0;
					break;
				case 2:
					x = src_w - water_w;
					y = 0;
					break;
				case 3:
					x = 0;
					y = src_h - water_h;
					break;
				case 4:
					x = src_w - water_w;
					y = src_h - water_h;
					break;
				default:
					x = CommonUtil.rand(0, src_w - water_w);
					y = CommonUtil.rand(0, src_h - water_h);
			}
			BufferedImage image = new BufferedImage(src_w, src_h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(src, 0, 0, src_w, src_h, null); 
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.7f)); 
			g2d.drawImage(water, x, y, water_w, water_h, null); 
			g2d.dispose();
			ImageIO.write(image, srcImgType, srcFile); 
		} catch (IOException e) {
			return;
		}
	}
	
	
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 图像切割（改）
     * 
     * @param srcImageFile
     *            源图像地址
     * @param x
     *            目标切片起点x坐标
     * @param y
     *            目标切片起点y坐标
     * @param destWidth
     *            目标切片宽度
     * @param destHeight
     *            目标切片高度
     */
    public static boolean abscut(String srcImageFile, int x, int y, int destWidth, int destHeight) {
    	boolean flag = false;
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                flag = ImageIO.write(tag, "JPEG", new File(srcImageFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    /**
     * 图片复制
     * @param srcImgPath
     * @param destPath
     * @return
     */
    public static boolean copy(String srcImgPath, String destPath) {
    	boolean flag = false;
    	File srcFile = new File(srcImgPath);
		if (!srcFile.exists()) {
			return flag;
		}
		File destFile = new File(destPath);
		try {
			BufferedImage srcImg;
			srcImg = ImageIO.read(srcFile);
			int src_w = srcImg.getWidth();
			int src_h = srcImg.getHeight();
			BufferedImage img = new BufferedImage(src_w, src_h, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = img.getGraphics();
            g.drawImage(srcImg, 0, 0, null); // 绘制缩小后的图
            g.dispose();
			ImageIO.write(img, "jpeg", destFile);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
    }
	
	
}