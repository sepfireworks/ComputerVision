package all_black;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Alb {

	private void setAlpha(Image os) {
            /**
             * 增加测试项
             * 读取图片，绘制成半透明,修改像素
             */
            try {
              ImageIcon imageIcon = new ImageIcon(os);
              BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(),imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
              Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
              g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
              //循环每一个像素点，改变像素点的Alpha值
              //int alpha = 0;
              for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                  int pixel = bufferedImage.getRGB(j2, j1); //获得该点rgb值

                  int[]   rgb = new int[3];   //创建一个新数组

                  int ala = (100) << 24;

                  pixel = (ala) | (pixel & 0x00ffffff);

//                  
                  bufferedImage.setRGB(j2, j1, pixel);
                }
              }
              g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

              //生成图片为PNG????
              ImageIO.write(bufferedImage, "png",  new File("E:\\xiao.png"));
            }
            catch (Exception e) {
              e.printStackTrace();
            }
     }
	
	
     public static void main(String[] args) throws Exception {
        Image image = null;
        File sourceimage = new File("D:\\eclipse-workspace\\wdwqre\\src\\wdwqre\\bicycle.bmp");  //source.gif图片要与HelloJava.java同在一目录下
		image = ImageIO.read(sourceimage);
    	int x = 0;
        Alb rc = new Alb();
        rc.setAlpha(image);
        //rc.setAlpha("E:\\wtf.jpg");
     }

}