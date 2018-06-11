package desktop;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

public class WallPaperWindow extends JWindow{

	File imgFile=null;
	WallPaperWindow()
	{
		Dimension windowSize= Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(windowSize);
		this.setLocation(0,0);
		this.setBackground(new Color(0,0,0,20));
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g;
		if(imgFile!=null)
			paintImage(g2d);
		
	}
	public void paintImage(Graphics2D g)
	{
		try {
			
			BufferedImage img=ImageIO.read(imgFile);
			g.drawImage(img,0,0,null);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showWallpaper(String imgPath)
	{
		imgFile=new File(imgPath);
		if(!imgFile.exists())
		{
			return ;
		}
		
		this.repaint();
		
	}
	public static void main(String[] args) {
		WallPaperWindow wall=new WallPaperWindow();
		wall.showWallpaper("D:\\Users\\akdongar\\Downloads\\Website-Design-Background.png");
	}
}
