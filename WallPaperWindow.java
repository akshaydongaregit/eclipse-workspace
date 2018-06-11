package desktop;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

public class WallPaperWindow extends JWindow{

	File imgFile=null;
	Dimension windowSize=new Dimension(900,500);
	Point windowLocation=new Point(200,150);
	
	WallPaperWindow()
	{
		Dimension windowSize= Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(this.windowSize);
		this.setLocation(this.windowLocation);
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
			Image scaled= img.getScaledInstance((int)windowSize.getWidth(),(int)windowSize.getHeight(),BufferedImage.SCALE_SMOOTH);
			g.drawImage(scaled,0,0,null);
		
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
	
	public static void main(String[] args) throws InterruptedException {
		WallPaperWindow wall=new WallPaperWindow();
		//wall.showWallpaper("D:\\Users\\akdongar\\Downloads\\Website-Design-Background.png");
		//wall.showWallpaper("C:\\Users\\hp\\Downloads\\357727.jpg");
		wall.showWallpaper("C:\\Users\\hp\\Downloads\\kristen_stewart_2016-wallpaper-1600x900.jpg");
		//wall.showWallpaper("C:\\Users\\hp\\Downloads\\forest-beautiful-hobbit-grass-arch-street-natural-trees-tunnel-wallpaper-hd-1366x768 - 1.jpg");
		
		
		WallPaperWindow wall1=new WallPaperWindow();
		wall1.showWallpaper("C:\\Users\\hp\\Downloads\\357727.jpg");
		//wall1.showWallpaper("C:\\Users\\hp\\Downloads\\forest-beautiful-hobbit-grass-arch-street-natural-trees-tunnel-wallpaper-hd-1366x768 - 1.jpg");
		wall1.setOpacity(0.0f);
		
		/*for(float op=1.0f;op>0;op-=0.1)
		{
			Thread.sleep(200);
			wall.setOpacity(op);
			wall1.setOpacity(1.0f-op);
		}*/
		
		float op1=1.0f;
		float op2=0.0f;
		int flag=1;
		
		while(true)
		{
			//System.out.println("op1 : "+op1+" op2 : "+op2);
			Thread.sleep(200);
			wall.setOpacity(op1);
			wall1.setOpacity(op2);
			op1=flag>0?op1-.1f:op1+.1f;
			op2=flag>0?op2+.1f:op2-.1f;
			
			if(op1<.1)
				flag=-1;
			else if(op1>.9f)
				flag=1;
		}
	}
}
