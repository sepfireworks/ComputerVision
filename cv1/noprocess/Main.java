package noprocess;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JComponent implements ActionListener {
	/**
	 * Define Main Class, used to Create buttons and Image Objects
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image1;private BufferedImage image2; // for the Samples
	private BufferedImage image3;private BufferedImage image4; // for the Low-pass & High-pass
	private BufferedImage resultImage;// for the Hybrid Image
	// for the sequence
	private BufferedImage imagex1;private BufferedImage imagex2;private BufferedImage imagex3;private BufferedImage imagex4;
	// control variables p1,p2,t, depend on changeBtn, represent the paths and parameter values of certain sample images 
	private int p1 = 0; private int p2 = 1; private int t = 0; 
	// 5 buttons in total
	private JButton changeBtn;private JButton lpBtn;private JButton hpBtn;
	private JButton MergeBtn;private JButton SqncBtn;
	public JButton getButton1() {
		changeBtn = new JButton("Change Samples"); 
		changeBtn.addActionListener(this); 
		return changeBtn;
	}
	public JButton getButton2() {
		lpBtn = new JButton("Low-pass");
		lpBtn.addActionListener(this); 
		return lpBtn;
	}
	public JButton getButton3() {
		hpBtn = new JButton("High-pass"); 
		hpBtn.addActionListener(this); 
		return hpBtn;
	}
	public JButton getButton4() {
		MergeBtn = new JButton("Merge"); 
		MergeBtn.addActionListener(this);
		return MergeBtn;
	}
	public JButton getButton5() {
		SqncBtn = new JButton("Sequence");
		SqncBtn.addActionListener(this);
		return SqncBtn;
	}
	
	// Draw the Components, including images and words
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// print instructions
		Font inf=new Font("Arial",Font.ITALIC,20);g2d.setFont(inf);
		g2d.drawString("Author : Yuntao Yang",1400,820);
		g2d.drawString("ID: 29712068, yy4y17@soton.ac.uk",1400,860);
		Font tit=new Font("New Times Roman",Font.BOLD,30);g2d.setFont(tit);
		g2d.drawString("NOTICE",1500,540);
		Font not=new Font("Courier",Font.PLAIN,20);;g2d.setFont(not);
		g2d.drawString("1. All the results of filtering are based on",1400,590);
		g2d.drawString("the current samples, press the buttons",1400,620);
		g2d.drawString("again to refresh them.",1400,650);
		g2d.drawString("2. Don't press the \"Sequence\" button if",1400,690);
		g2d.drawString("you haven't run the \"Merge\" at least for",1400,720);
		g2d.drawString("one time, or there would be an Error.",1400,750);
		// draw images, 2 samples + 3 results + 4 thumbnails = 9 images in total
		Process reader = new Process();
		image1 = reader.ReadImg(p1);
		image2 = reader.ReadImg(p2);
		if (image1 != null) {
			g2d.drawImage(image1, 15, 30, image1.getWidth(), image1.getHeight(), null);
		}
		if(image2 != null) {
			g2d.drawImage(image2, 485, 30, image2.getWidth(), image2.getHeight(), null);
		}
		if(resultImage != null) {
			g2d.drawImage(resultImage, 970, 30, resultImage.getWidth(), resultImage.getHeight(), null);
		}
		if(image3 != null) {
			g2d.drawImage(image3, 15, 530, image3.getWidth(), image3.getHeight(), null);
		}
		if(image4 != null) {
			g2d.drawImage(image4, 485, 530, image4.getWidth(), image4.getHeight(), null);
		}
		if(imagex1 != null) {
			g2d.drawImage(imagex1, 
					975+resultImage.getWidth(), 
					30+resultImage.getHeight()-imagex1.getHeight(), 
					imagex1.getWidth(), imagex1.getHeight(), null);
		}
		if(imagex2 != null) {
			g2d.drawImage(imagex2, 
					980+resultImage.getWidth()+imagex1.getWidth(), 
					30+resultImage.getHeight()-imagex2.getHeight(), 
					imagex2.getWidth(), imagex2.getHeight(), null);
		}
		if(imagex3 != null) {
			g2d.drawImage(imagex3, 
					985+resultImage.getWidth()+imagex1.getWidth()+imagex2.getWidth(),
					30+resultImage.getHeight()-imagex3.getHeight(),
					imagex3.getWidth(), imagex3.getHeight(), null);
		}
		if(imagex4 != null) {
			g2d.drawImage(imagex4,
					990+resultImage.getWidth()+imagex1.getWidth()+imagex2.getWidth()+imagex3.getWidth(),
					30+resultImage.getHeight()-imagex4.getHeight(),
					imagex4.getWidth(), imagex4.getHeight(), null);
			}
		}
	// Change the value of p1,p2 to control the outputs
	public void change() {
		if (t < 4)
		{
			p1 = p1 + 2; p2 = p2 + 2; t = t + 1;
		}
		else
		{
			p1 = 0; p2 = 1; t = 0;
		}
	}
	//the Low-pass filtering
	public void lowpass() {
		Process Gaus = new Process();
		image3 = Gaus.Gaussian(p1);
	}
	//the High-pass filtering
	public void highpass() {
		Process AtGs = new Process();
		image4 = AtGs.antiGaus(p2);
	}
	//Create the Hybrid Image
	public void Merge() {
		Process ToMerge = new Process();
		resultImage = ToMerge.Merge(p1,p2);
	}
	//Generate the sequence of thumbnails based on Hybrid Images
	public void Seq() {
		Process ToSqn = new Process();
		imagex1 = ToSqn.Sqn(p1, p2, 1);
		imagex2 = ToSqn.Sqn(p1, p2, 2);
		imagex3 = ToSqn.Sqn(p1, p2, 3);
		imagex4 = ToSqn.Sqn(p1, p2, 4);
	}
	// Main method, set up the GUI frame
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main imp = new Main();
		frame.getContentPane().add(imp, BorderLayout.CENTER);
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		flowPanel.add(imp.getButton1());
		flowPanel.add(imp.getButton2());
		flowPanel.add(imp.getButton3());
		flowPanel.add(imp.getButton4());
		flowPanel.add(imp.getButton5());
		frame.getContentPane().add(flowPanel, BorderLayout.SOUTH);
		frame.setSize(1900, 1000);
		frame.setTitle("Gaussian Filter Demo");
		frame.setVisible(true);
	}
	// the Action Listener and Action List
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == changeBtn) {
			this.change();
			this.repaint();
		}
		if (e.getSource() == lpBtn) {
			this.lowpass();
			this.repaint();
		}
		if (e.getSource() == hpBtn) {
			this.highpass();
			this.repaint();
		}
		if (e.getSource() == MergeBtn) {
			this.Merge();
			this.repaint();
		}
		if (e.getSource() == SqncBtn) {
			this.Seq();
			this.repaint();
		}
	}

}