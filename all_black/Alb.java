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
             * ���Ӳ�����
             * ��ȡͼƬ�����Ƴɰ�͸��,�޸�����
             */
            try {
              ImageIcon imageIcon = new ImageIcon(os);
              BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(),imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
              Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
              g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
              //ѭ��ÿһ�����ص㣬�ı����ص��Alphaֵ
              //int alpha = 0;
              for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                  int pixel = bufferedImage.getRGB(j2, j1); //��øõ�rgbֵ

                  int[]   rgb = new int[3];   //����һ��������

                  int ala = (100) << 24;

                  pixel = (ala) | (pixel & 0x00ffffff);

//                  
                  bufferedImage.setRGB(j2, j1, pixel);
                }
              }
              g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

              //����ͼƬΪPNG????
              ImageIO.write(bufferedImage, "png",  new File("E:\\xiao.png"));
            }
            catch (Exception e) {
              e.printStackTrace();
            }
     }
	
	
     public static void main(String[] args) throws Exception {
        Image image = null;
        File sourceimage = new File("D:\\eclipse-workspace\\wdwqre\\src\\wdwqre\\bicycle.bmp");  //source.gifͼƬҪ��HelloJava.javaͬ��һĿ¼��
		image = ImageIO.read(sourceimage);
    	int x = 0;
        Alb rc = new Alb();
        rc.setAlpha(image);
        //rc.setAlpha("E:\\wtf.jpg");
     }

}