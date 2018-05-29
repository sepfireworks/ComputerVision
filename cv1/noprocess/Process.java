package noprocess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Process {
	/**
	 * Define Process Class, includes all the processing algorithm of this project
	 */
	// integrate two images into a hybrid image
	public BufferedImage Merge(int p1,int p2)
	{
		// Load parameters for image1
		Parameters plist = new Parameters();
		double[] para1 = plist.Para(p1);
		double sigma1 = para1[0];int r1 = (int)para1[1];
		// Load parameters for image2
		double[] para2 = plist.Para(p2);
		double sigma2 = para2[0];int r2 = (int)para2[1];
		// Generate templates for image1 & image2
		int n1 = 2*r1 + 1;
		int n2 = 2*r2 + 1;
		double kn01[][] = new double[n1][n1];
		double kn02[][] = new double[n2][n2];
		Template kernel = new Template();
		kn01 = kernel.Tpl(r1,sigma1);kn02 = kernel.Tpl(r2,sigma2);
		// Load the original data from image1 & image2
		BufferedImage input1 = ReadImg(p1);BufferedImage input2 = ReadImg(p2);
		// Start to Merge
		BufferedImage IMX = null;
		try {
		  // Define variables
		  int height = input1.getHeight();
	      int width = input1.getWidth();
	      int pixels1[] = new int[height*width];int outpixels1[] = new int[height*width];
	      int pixels2[] = new int[height*width];int outpixelsX[] = new int[height*width];
          IMX = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
          // Get the pixels values from image1
          for (int row = input1.getMinY(); row < height ; row++) 
          {
              for (int col = input1.getMinX(); col < width; col++) 
              {
              	pixels1[row*width + col] = input1.getRGB(col,row);
              }
          }
          // Get the pixels values from image2
          for (int row = input1.getMinY(); row < height ; row++) 
          {
              for (int col = input1.getMinX(); col < width; col++) 
              {
              	pixels2[row*width + col] = input2.getRGB(col,row);
              }
          }
          // Implement the Gaussian Filter to image1
          for (int row = input1.getMinY(); row < height ; row++) {
            for (int col = input1.getMinX(); col < width; col++) {
          	  int alpha = (255) << 24;
            	  double sumR = 0,sumG = 0,sumB = 0;
            	  int R,G,B;
          	  for(int i = -r1; i <= r1; i++)
          	  {
          		  int roffset = row + i;
          		  roffset = (roffset < 0) ? 0 :(roffset>=height ? height-1 : roffset);
					  for(int j = -r1; j <= r1; j++)
					  {
						int coffset = col+j;
						coffset = (coffset < 0) ? 0 :(coffset>=width ? width-1 : coffset);
						int[] rgb = new int[3];
						// separate rgb values from 3 channels
						rgb[0] = (pixels1[roffset*width+coffset] & 0xff0000) >> 16;
	                  	rgb[1] = (pixels1[roffset*width+coffset] & 0xff00) >> 8;
	              	  	rgb[2] = (pixels1[roffset*width+coffset] & 0xff);
	              	  	// Template Convolution Process
	              	  	sumR  = sumR + rgb[0]*kn01[r1+i][r1+j];
						sumG  = sumG + rgb[1]*kn01[r1+i][r1+j];
						sumB  = sumB + rgb[2]*kn01[r1+i][r1+j];
					  }
				  }
          	  	  R = Jud.judge(sumR); 
				  G = Jud.judge(sumG); 
				  B = Jud.judge(sumB);
				  // store new pixels values (low-pass image)
				  outpixels1[row*width + col] = (alpha) | (R<<16) | (G<<8) | (B);
            }
          }
          // Implement the Gaussian Filter to image2, subtract with original image2, then be merged with image1
          for (int row = input2.getMinY(); row < height ; row++) {
            for (int col = input2.getMinX(); col < width; col++) {
          	  int alpha = (255) << 24;
            	  double sumR = 0,sumG = 0,sumB = 0;
            	  double R,G,B;int Ri,Gi,Bi;
          	  for(int i = -r2; i <= r2; i++)
          	  {
          		  int roffset = row + i;
          		  roffset = (roffset < 0) ? 0 :(roffset>=height ? height-1 : roffset);
					  for(int j = -r2; j <= r2; j++)
					  {
						int coffset = col+j;
						coffset = (coffset < 0) ? 0 :(coffset>=width ? width-1 : coffset);
						int[] rgb = new int[3];
						// separate rgb values from 3 color channels
						rgb[0] = (pixels2[roffset*width+coffset] & 0xff0000) >> 16;
	                  	rgb[1] = (pixels2[roffset*width+coffset] & 0xff00) >> 8;
	              	  	rgb[2] = (pixels2[roffset*width+coffset] & 0xff);
	              	  	// Template Convolution Process
	              	  	sumR  = sumR + (double)rgb[0]*kn02[r2+i][r2+j];
						sumG  = sumG + (double)rgb[1]*kn02[r2+i][r2+j];
						sumB  = sumB + (double)rgb[2]*kn02[r2+i][r2+j];
					  }
			  }
          	  // Merging Process
          	  R = (double)((outpixels1[row*width + col]& 0xff0000)>>16)+(double)((pixels2[row*width+col]& 0xff0000)>> 16) - sumR; 
			  G = (double)((outpixels1[row*width + col]& 0xff00)>>8)+(double)((pixels2[row*width+col]& 0xff00)>>8) - sumG; 
			  B = (double)((outpixels1[row*width + col]& 0xff)>>0)+(double)((pixels2[row*width+col]& 0xff)>>0) - sumB;
			  Ri = Jud.judge(R);Gi = Jud.judge(G);Bi = Jud.judge(B);
			  // store new pixels values (Hybrid Image)
			  outpixelsX[row*width + col] = (alpha) | (Ri<<16) | (Gi<<8) | (Bi);
            }
          }
          // Reset the value of every pixel to produce the Hybrid Image
          for (int row = input1.getMinY(); row < height ; row++) 
          {
              for (int col = input1.getMinX(); col < width; col++) 
              {
              	IMX.setRGB(col, row, outpixelsX[row*width + col]);
              }
          }
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
        return IMX;
	}
	
	// Execute the low-pass filtering
	public BufferedImage Gaussian(int p1)
	{
		// Load parameters for image1
		Parameters plist = new Parameters();
		double[] para1 = plist.Para(p1);
		double sigma1 = para1[0];int r1 = (int)para1[1];
		// Generate templates for image1
		int n1 = 2*r1 + 1;
		double kn01[][] = new double[n1][n1];
		Template kernel = new Template();
		kn01 = kernel.Tpl(r1,sigma1);
		// Load the original data from image1
		BufferedImage input1 = ReadImg(p1);
		// Start to Filter
		BufferedImage IM1 = null;
		try {
		  // Define variables
		  int height = input1.getHeight();
	      int width = input1.getWidth();
	      int pixels1[] = new int[height*width];int outpixels1[] = new int[height*width];
          IM1 = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
          // Get the pixels values from image1
          for (int row = input1.getMinY(); row < height ; row++) 
          {
              for (int col = input1.getMinX(); col < width; col++) 
              {
              	pixels1[row*width + col] = input1.getRGB(col,row);
              }
          }
          // Implement the Gaussian Filter to image1
          for (int row = input1.getMinY(); row < height ; row++) {
            for (int col = input1.getMinX(); col < width; col++) {
          	  int alpha = (255) << 24;
            	  double sumR = 0,sumG = 0,sumB = 0;//, sumA = 0;
            	  int R,G,B;//, sumA = 0;
          	  for(int i = -r1; i <= r1; i++)
          	  {
          		  int roffset = row + i;
          		  roffset = (roffset < 0) ? 0 :(roffset>=height ? height-1 : roffset);
					  for(int j = -r1; j <= r1; j++)
					  {
						int coffset = col+j;
						coffset = (coffset < 0) ? 0 :(coffset>=width ? width-1 : coffset);
						int[] rgb = new int[3]; 
						// separate rgb values from 3 color channels
						rgb[0] = (pixels1[roffset*width+coffset] & 0xff0000) >> 16;
	                  	rgb[1] = (pixels1[roffset*width+coffset] & 0xff00) >> 8;
	              	  	rgb[2] = (pixels1[roffset*width+coffset] & 0xff);
	              	  	// Template Convolution Process
	              	  	sumR  = sumR + rgb[0]*kn01[r1+i][r1+j];
						sumG  = sumG + rgb[1]*kn01[r1+i][r1+j];
						sumB  = sumB + rgb[2]*kn01[r1+i][r1+j];
					  }
				  }
          	  	  R = Jud.judge(sumR); 
				  G = Jud.judge(sumG); 
				  B = Jud.judge(sumB);
				  // store new pixels values (low-pass image)
				  outpixels1[row*width + col] = (alpha) | (R<<16) | (G<<8) | (B);
            }
          }
          // Reset the value of every pixel to produce the Low-passing Image
          for (int row = input1.getMinY(); row < height ; row++) 
          {
              for (int col = input1.getMinX(); col < width; col++) 
              {
              	IM1.setRGB(col, row, outpixels1[row*width + col]);
              }
          }
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
		System.out.println("******NOW END******");
        return IM1;
	}
	
	// Execute the high-pass filtering
	public BufferedImage antiGaus(int p2)
	{
		// Load parameters for image2
		Parameters plist = new Parameters();
		double[] para2 = plist.Para(p2);
		double sigma2 = para2[0];int r2 = (int)para2[1];
		// Generate templates for image2
		int n2 = 2*r2 + 1;
		double kn02[][] = new double[n2][n2];
		Template kernel = new Template();
		kn02 = kernel.Tpl(r2,sigma2);
		// Load the original data from image2
		BufferedImage input2 = ReadImg(p2);
		// Start to Filter and Subtract
		BufferedImage IM2 = null;
		try {
		  // Define variables
		  int height = input2.getHeight();
	      int width = input2.getWidth();
	      int pixels2[] = new int[height*width];int outpixelsX[] = new int[height*width];
          IM2 = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
          // Get the pixels values from image1
          for (int row = input2.getMinY(); row < height ; row++) 
          {
              for (int col = input2.getMinX(); col < width; col++) 
              {
              	pixels2[row*width + col] = input2.getRGB(col,row);
              }
          }
          // Implement the Gaussian Filter to image2 and then Subtract with the original image
          for (int row = input2.getMinY(); row < height ; row++) {
            for (int col = input2.getMinX(); col < width; col++) {
          	  int alpha = (255) << 24;
            	  double sumR = 0,sumG = 0,sumB = 0;
            	  double R,G,B;int Ri,Gi,Bi;
          	  for(int i = -r2; i <= r2; i++)
          	  {
          		  int roffset = row + i;
          		  roffset = (roffset < 0) ? 0 :(roffset>=height ? height-1 : roffset);
					  for(int j = -r2; j <= r2; j++)
					  {
						int coffset = col+j;
						coffset = (coffset < 0) ? 0 :(coffset>=width ? width-1 : coffset);
						int[] rgb = new int[3]; 
						// separate rgb values from 3 color channels
						rgb[0] = (pixels2[roffset*width+coffset] & 0xff0000) >> 16;
	                  	rgb[1] = (pixels2[roffset*width+coffset] & 0xff00) >> 8;
	              	  	rgb[2] = (pixels2[roffset*width+coffset] & 0xff);
	              	  	// Template Convolution Process
	              	  	sumR  = sumR + (double)rgb[0]*kn02[r2+i][r2+j];
						sumG  = sumG + (double)rgb[1]*kn02[r2+i][r2+j];
						sumB  = sumB + (double)rgb[2]*kn02[r2+i][r2+j];
					  }
			  }
          	  // Subtracting Process
          	  R = (double)((pixels2[row*width+col]& 0xff0000)>> 16) - sumR; 
			  G = (double)((pixels2[row*width+col]& 0xff00)>>8) - sumG;
			  B = (double)((pixels2[row*width+col]& 0xff)>>0) - sumB;
			  Ri = Jud.judge(R);Gi = Jud.judge(G);Bi = Jud.judge(B);
			  // store new pixels values (High-pass image)
			  outpixelsX[row*width + col] = (alpha) | (Ri<<16) | (Gi<<8) | (Bi);
            }
          }
          // Reset the value of every pixel to produce the High-passing Image
          for (int row = input2.getMinY(); row < height ; row++) 
          {
              for (int col = input2.getMinX(); col < width; col++) 
              {
              	IM2.setRGB(col, row, outpixelsX[row*width + col]);
              }
          }
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
        return IM2;
	}
	
	// Generate a sequence of Hybrid Image thumbnails 
	public BufferedImage Sqn(int p1, int p2, int k)
	{
		// Compute the coefficient of shrinking, ks
		double ks = Math.pow(0.5,k);double ks0 = Math.pow(2,k);
		// Input the Hybrid Image
		BufferedImage input = Merge(p1,p2);
		// Start Shrinking
		BufferedImage Sequence0 = null;
		try {
		  int height = input.getHeight();int height0 = (int)(ks*height);
	      int width = input.getWidth();int width0 = (int)(ks*width);
	      int pixels[] = new int[height*width];int outpixels[] = new int[height*width];
	      Sequence0 = new BufferedImage(width0,height0, BufferedImage.TYPE_INT_ARGB);
	      // Get the pixels values from the Hybrid Image
          for (int row = input.getMinY(); row < height ; row++) 
          {
              for (int col = input.getMinX(); col < width; col++) 
              {
              	pixels[row*width + col] = input.getRGB(col,row);
              }
          }
          // Set up a connection between the new image & original image
          for (int row = 0; row < height0 ; row++) {
            for (int col = 0; col < width0; col++) {
            	int pos = (int)(ks0*row*width + ks0*col);
				outpixels[row*width0 + col] = pixels[pos];
            }
          }
          //  Reset the value of every pixel to produce the thumbnail
          for (int row = 0; row < height0 ; row++) 
          {
              for (int col = 0; col < width0; col++) 
              {
            	  Sequence0.setRGB(col, row, outpixels[row*width0 + col]);
              }
          }
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
		System.out.println("******NOW END******");
        return Sequence0;
	}
	
	//Loads an image file according to the input from Paths.java
	public BufferedImage ReadImg(int p0) {
		String p;
		Paths onePath = new Paths();
		p = onePath.path(p0);
		java.net.URL imgURL = this.getClass().getResource(p); // get the relative path
		BufferedImage ResultImg = null;
		try {
			ImageIcon imageIcon = new ImageIcon(imgURL);
	        ResultImg = new BufferedImage(imageIcon.getIconWidth(),imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2D = (Graphics2D) ResultImg.getGraphics();
	        g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
			}
		catch (Exception e) {
            e.printStackTrace();
          }
		return ResultImg;
	}
}