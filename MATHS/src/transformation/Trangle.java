package transformation;

import java.applet.Applet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Trangle extends Window {

	int height=400;
	int width=500;
	public int T_X=0;
	public int T_Y=0;
	public boolean rot=false;
	public double rx=60;
	public double ry=60;
	public double rx1=40;
	public double ry1=40;
	int cbsx;
	int cbsy;
	int cbex;
	int cbey;
	boolean transformed=false;
	public Trangle(Frame owner) {
		super(owner);
		this.setLocation(300, 200);
		this.setSize(500,400);
		this.setVisible(true);
		//this.setBackground(new Color(0,0,0,0));
		this.addMouseListener(new MHandler());
		
	
		// TODO Auto-generated constructor stub
	}
	public void paint(Graphics g)
	{
	
		if(!transformed)
		{
			Transform_To(width/2,height/2);
			transformed=true;
		}
		g.setColor(new Color(255,255,255));
		g.drawRect(0, 0, width, height);
	    drawFrame(g);
	    drawline(g);
	}
	public void drawline(Graphics g)
	{
		g.setColor(new Color(0,0,255));
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		
		g.drawLine(getx(-100), gety(0), getx(100),gety(0));
		g.drawLine(getx(0), gety(100), getx(0), gety(-100));
		//System.out.println("x : "+rx+" y :"+ry+" "+rx1+" "+ry1);
		System.out.println("x : "+getx(rx)+" y :"+gety(ry)+" "+getx(rx1)+" "+gety(ry1));
		g.drawLine((int)getx(rx),(int)gety(ry),(int)getx(rx1),(int)gety(ry1));
		
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rotate(10);
				repaint();
				
	}
	public void rotate(int angle)
	{
		double dangle=angle*(Math.PI/180);
		System.out.println("\nangle : "+dangle+" "+Math.cos(dangle));
		rx=(rx*Math.cos(dangle)-ry*Math.sin(dangle));
		ry=(rx*Math.sin(dangle)+ry*Math.cos(dangle));
		rx1=(rx1*Math.cos(dangle)-ry1*Math.sin(dangle));
		ry1=(rx1*Math.sin(dangle)+ry1*Math.cos(dangle));
		
	}
	public void drawFrame(Graphics g)
	{
		//System.out.println("Drawing frame");
		Color color=new Color(80,255,80);
		int cbw=40;
		int cbh=height/23;
		int mbw=40;
		int mbh=height/23;
		//draws frame around window.
		g.setColor(color);
		g.fillRect(0, 0, width, height/18);
		g.fillRect(0, 0, width/60, height);
		g.fillRect(width-width/60, 0, width/60, height);
		g.fillRect(0, height-height/50, width, height/50);
		color=new Color(255,80,80);
		g.setColor(color);
		g.fillRect(width-cbw-width/60, 0, cbw,cbh);
		
		cbsx=width-cbw-width/60;
		cbsy=0;
		cbex=width-width/60;
		cbey=cbh;
		/*g.setColor(new Color(80,80,255));
		g.fillRect(width-(cbw+mbw), 0, mbw,mbh);
		*/
		
	}
	public static void main(String args[])
	{
	    Trangle t=new Trangle(null);  
	}
	
	/*0
	 Transformations Methods.
	 */
	public void Transform_To(int x,int y)
	{
		T_X=x;
		T_Y=y;
		
		/*rx=getx(rx);
		ry=gety(ry);
		rx1=getx(rx1);
		ry1=gety(ry1); */
	}
	public int getx(int x)
	{
		
		return x+T_X;
	}
	public int gety(int y)
	{
		return (T_Y-y);
	}
	

	public double getx(double x)
	{
		
		return x+T_X;
	}
	public double gety(double y)
	{
		return (T_Y-y);
	}
	
	class MHandler implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
			int x=e.getX();
			int y=e.getY();
			
			if((x>cbsx && y>cbsy) && (x<cbex && y<cbey) )
			{
				System.exit(0);
			}
		
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
