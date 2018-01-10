package transformation;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JFrame;

public class TD extends JFrame{

	Graphics2D g2d;
	TEnv te;
	NTEnv nte;
	public void showFrame()
	{
		
		this.setLocation(0, 0);
		this.setSize(new Dimension(1200,700));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(new Color(0,0,200));
		this.setVisible(true);
		
	}
	public void paint(Graphics g)
	{
		
		RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		renderHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		
		System.out.println("\nIn paint");
		g2d=(Graphics2D)g;
		g2d.setRenderingHints(renderHints);
		g2d.setColor(new Color(255,255,255));
		g2d.fillRect(0, 0,this.getWidth(),this.getHeight());
		
		te=new TEnv(g,300,500);
		nte=new NTEnv(g,300,500);
		
		drawCoords();
		drawTrangle();
		drawLight();
		drawcube();
	}
	public void drawcube()
	{
		g2d.setColor(new Color(0,0,255));
	
		nte.setOrigin(new int[]{800,260});
		//drawnCoords();
		nte.drawCube(80);
	}
	
	public void drawnCoords()
	{
		g2d.setColor(new Color(0,0,0));
		g2d.setStroke(new BasicStroke(4));
		g2d.fillRect(0, 0,700,700);
		g2d.setColor(new Color(0,0,255));
		nte.drawLine(0,0,0,200,0,0);
		nte.drawLine(0,0,0,0,200,0);
		nte.drawLine(0,0,0,00,0,200);
		nte.drawLine(0,0,0,-200,0,0);
		nte.drawLine(0,0,0,0,-200,0);
		nte.drawLine(0,0,0,00,0,-200);
	}
	public void drawLight()
	{
		int width=200;
		int height=40;
		
		g2d.setColor(new Color(Color.HSBtoRGB(0.6f,1.0f,0.1f)));
		g2d.fillRect(200, 40,400,100);
		g2d.setColor(new Color(Color.HSBtoRGB(0.6f,1.0f,0.2f)));
		g2d.fillRect(220,50 ,360,80);
		g2d.setColor(new Color(Color.HSBtoRGB(0.6f,1.0f,0.4f)));
		g2d.fillRect(240, 60,320,60);
		g2d.setColor(new Color(Color.HSBtoRGB(0.6f,1.0f,0.6f)));
		g2d.fillRect(260, 70,280,40);
		g2d.setColor(new Color(Color.HSBtoRGB(0.6f,1.0f,1.0f)));
		g2d.fillRect(280, 80,240,20);
		g2d.setColor(new Color(255,255,255,150));
		g2d.fillRect(300,88,200,4);
		g2d.setColor(new Color(255,255,255,255));
		g2d.fillRect(320,89,160,2);
	}
	
	
	public void drawTrangle()
	{
		System.out.println("\nDrawing trangle");
		g2d.setColor(new Color(255,0,0));
		
		int px[]=new int[]{0,0,50};
		int py[]=new int[]{-100,0,0};
		int pz[]=new int[]{0,50,0};
		te.fillPoly(px, py, pz,3);
		g2d.setColor(new Color(0,0,0));
		te.drawPoly(px, py, pz,3);
		
		g2d.setColor(new Color(255,0,0));
		px=new int[]{0,0,-45};
		py=new int[]{-100,0,26};
		pz=new int[]{0,50,10};
		te.fillPoly(px, py, pz,3);
		g2d.setColor(new Color(0,0,0));
		te.drawPoly(px, py, pz,3);
		
		g2d.setColor(new Color(255,0,0));
		px=new int[]{0,-50,-45};
		py=new int[]{-100,0,26};
		pz=new int[]{0,0,10};
		te.fillPoly(px, py, pz,3);
		g2d.setColor(new Color(0,0,0));
		te.drawPoly(px, py, pz,3);
		/*te.drawLine(0, -100, 0, 0, 0, 50);
		te.drawLine(0, -100, 0,50,0,0);
		te.drawLine(0, 0, 50,50,0,0);
		*/
		
		/*
		te.drawLine(0,-100,0,-50,0,0);
		te.drawLine(-50,0,0,0,0,50);
		
		te.drawLine(-50,0,0,50,0,0);
		te.drawLine(0,-100,0,50,0,0);
		
		*/
		
	}
	public void drawCoords()
	{
		System.out.println("\nDrawing coordinates");
		
		g2d.setColor(new Color(0,200,0));
		g2d.fillRect(0, 0,700,700);
		g2d.setColor(new Color(0,0,255));
		te.drawLine(0,0,0,200,0,0);
		te.drawLine(0,0,0,0,200,0);
		te.drawLine(0,0,0,00,0,200);
		te.drawLine(0,0,0,-200,0,0);
		te.drawLine(0,0,0,0,-200,0);
		te.drawLine(0,0,0,00,0,-200);
		/*
		Pointc pc=new Pointc();
		int[] ax=  pc.convert(new int[]{100,0,0}); //new int[]{1,0,0};
		int[] ay=pc.convert(new int[]{0,100,0}); //new int[]{0,1,0};
		int[] az=pc.convert(new int[]{0,0,00}); //new int[]{0,0,1};
		
		g2d.setStroke(new BasicStroke(2));
		
		System.out.println("\nax : "+ax[0]+" ax : "+ax[1]);
		g2d.drawLine(0,0,ax[0],ax[1]);
		g2d.drawLine(0,0,ay[0],ay[1] );
		g2d.drawLine(0,0, az[0],az[1]);
		
		//g2d.drawLine(0,0,100,100);
		 
		 */
	}
	
	/* main() function is here */
	
	public static void main(String args[])
	{
		new TD().showFrame();
		
	}
	
	/*
	 class which creates 3D Enviornment and draw given points
	
	by converting them to 2D .
	
	*/
	class TEnv
	{
		
		
		TEnv(Graphics g,int x,int y)
		{
			this.g=(Graphics2D)g;
			this.org[0]=x;
			this.org[1]=y;
		}
		protected int org[] = new int[]{100,100};
		public Graphics2D g;
		public void setOrigin(int Orig[])
		{
			if(Orig.length<2)
			{
				System.out.println("\nUnable to set Origin");
				return;
			}
			
			
			this.org[0]=Orig[0];
			this.org[1]=Orig[1];
			System.out.println("\n Origin is set to ( "+org[0]+" , "+org[1]+" )");
			return;
		}
		
	    public void drawLine(int x1,int y1,int z1,int x2,int y2,int z2)
		{
	    	
	    	Pointc pc=new Pointc();
	    	int p1[]=pc.convert(new int[]{x1,y1,z1});
			int p2[]=pc.convert(new int[]{x2,y2,z2});
			
			g.drawLine(org[0]+p1[0],org[1]+p1[1] ,org[0]+p2[0], org[1]+p2[1]);
		}
	    
	    public void drawPoly(int px[],int py[],int pz[],int np)
	    {
	    	if(px.length<np || py.length<np || pz.length<np)
	    	{
	    			System.out.println("\nInsufficient points");
	    			return;
	    	}
	    	
	    	int cpx[]=new int[np];
	    	int cpy[]=new int[np];
	    	/*
	    	 loop to convert 3D points to 2D points.
	    	 */
	    	Pointc pc=new Pointc();
	    	for(int i=0;i<np;i++)
	    	{
	    		int cp[]=pc.convert(new int[]{px[i],py[i],pz[i]});
	    		cpx[i]=org[0]+cp[0];
	    		cpy[i]=org[1]+cp[1];
	    	}
	    	
	    	g.drawPolygon(cpx,cpy,np);
	    	
	    }
	    
	    public void fillPoly(int px[],int py[],int pz[],int np)
	    {
	    	if(px.length<np || py.length<np || pz.length<np)
	    	{
	    			System.out.println("\nInsufficient points");
	    			return;
	    	}
	    	
	    	int cpx[]=new int[np];
	    	int cpy[]=new int[np];
	    	/*
	    	 loop to convert 3D points to 2D points.
	    	 */
	    	Pointc pc=new Pointc();
	    	for(int i=0;i<np;i++)
	    	{
	    		int cp[]=pc.convert(new int[]{px[i],py[i],pz[i]});
	    		cpx[i]=org[0]+cp[0];
	    		cpy[i]=org[1]+cp[1];
	    	}
	    	
	    	g.fillPolygon(cpx,cpy,np);
	    	
	    }
	    
	    public void drawCube(int l)
	    {
	    	
	    	Cube cube=new Cube(l);
	    	
	    	for(int i=0;i<8;i++)
	    	{
	    		g.setColor(new Color(255,255,255));
	    		g.fillRect(org[0]-2*l,org[1]-2*l,4*l,4*l);
	    		
	    		
	    		cube.setTheta(0);
	    	cube.xcompile();
	    	g.setColor(new Color(0,0,255,200));
	    	//draw back face no 1.
	    	this.fillPoly(cube.getf1()[0],cube.getf1()[1],cube.getf1()[2],4);
	    	
	    	g.setColor(new Color(0,0,255,255));
	    	this.fillPoly(cube.getf2()[0],cube.getf2()[1],cube.getf2()[2],4);
	    	
	    	g.setColor(new Color(0,0,255,155));
	    	this.fillPoly(cube.getf3()[0],cube.getf3()[1],cube.getf3()[2],4);
	    	
	    	g.setColor(new Color(0,0,255,100));
	    	this.fillPoly(cube.getf4()[0],cube.getf4()[1],cube.getf4()[2],4);
	    	
	    	g.setColor(new Color(0,0,255,55));
	    	this.fillPoly(cube.getf5()[0],cube.getf5()[1],cube.getf5()[2],4);
	    	
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	}
	    	/*
	    	g.setColor(new Color(0,0,255,100));
	    	this.fillPoly(cube.getf4()[0],cube.getf4()[1],cube.getf4()[2],4);
	    	
	    	g.setColor(new Color(0,0,255,55));
	    	this.fillPoly(cube.getf5()[0],cube.getf5()[1],cube.getf5()[2],4);
	    	*/
	    	/*this.fillPoly(
	    			new int[]{0,0,l,l},
	    			
	    			new int[]{0,-l,-l,0},
	    			
	    			new int[]{0,0,0,0},
	    			4);
	    	
	    	g.setColor(new Color(0,0,255,255));
	    	//draw bottom  face no 2.
	    	this.fillPoly(
	    			new int[]{0,l,l,0},
	    			
	    			new int[]{0,0,0,0},
	    			
	    			new int[]{0,0,l,l},
	    			4);
	    	
	    	g.setColor(new Color(0,0,255,155));
	    	//draw right side face no 3.
	    	this.fillPoly(
	    			
	    			
	    			new int[]{l,l,l,l},
	    			
	    			new int[]{0,-l,-l,0},
	    			
	    			new int[]{0,0,l,l},
	    			4);
	    	
	    	
	    	g.setColor(new Color(0,0,255,100));
	    	//draw left side  face no 4.
	    	this.fillPoly(
	    			new int[]{0,0,0,0},
	    			
	    			new int[]{0,-l,-l,0},
	    			
	    			new int[]{0,0,l,l},
	    			4);
	    	g.setColor(new Color(0,0,255,55));
	    	//draw front  face no 5.
	    	this.fillPoly(
	    			new int[]{0,l,l,0},
	    			
	    			new int[]{-l,-l,0,0},
	    			
	    			new int[]{l,l,l,l},
	    			4);
	    			*/
	    	
	    }
	} 
	
	class Cube
	{
		int f1[][];
		int f2[][];
		int f3[][];
		int f4[][];
		int f5[][];
		int theta;
		int cf1[][]=new int[3][4];
		int cf2[][]=new int[3][4];
		int cf3[][]=new int[3][4];
		int cf4[][]=new int[3][4];
		int cf5[][]=new int[3][4];
		
		public Cube(int l)
		{
			f1=new int[][]{ {0,0,l,l},{0,-l,-l,0},{0,0,0,0}};
			f2=new int[][]{{0,l,l,0},{0,0,0,0},{0,0,l,l}};
			f3=new int[][]{{l,l,l,l},{0,-l,-l,0},{0,0,l,l}};
			f4=new int[][]{{0,0,0,0},{0,-l,-l,0},{0,0,l,l}};
			f5=new int[][]{{0,l,l,0},{-l,-l,0,0},{l,l,l,l}};
			
		}
		
		public void reset()
		{
			cf1=f1;
			cf2=f2;
			cf3=f3;
			cf4=f4;
			cf5=f5;
		}
		public void xcompile()
		{
			//Compilation of first face
			for(int i=0;i<4;i++)
			{
				cf1[0][i]=f1[0][i];
			}
			for(int i=0;i<4;i++)
			{
				cf1[1][i]=(int) (f1[1][i]*Math.cos(theta*(Math.PI/180))-f1[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf1[2][i]=(int) (f1[1][i]*Math.sin(theta*(Math.PI/180))-f1[2][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			//Compilation of second face
			for(int i=0;i<4;i++)
			{
				cf2[0][i]=f2[0][i];
			}
			for(int i=0;i<4;i++)
			{
				cf2[1][i]=(int) (f2[1][i]*Math.cos(theta*(Math.PI/180))-f2[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf2[2][i]=(int) (f2[1][i]*Math.sin(theta*(Math.PI/180))-f2[2][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			//Compilation of third face
			for(int i=0;i<4;i++)
			{
				cf3[0][i]=f3[0][i];
			}
			for(int i=0;i<4;i++)
			{
				cf3[1][i]=(int) (f3[1][i]*Math.cos(theta*(Math.PI/180))-f3[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf3[2][i]=(int) (f3[1][i]*Math.sin(theta*(Math.PI/180))-f3[2][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			//Compilation of fourth face
			for(int i=0;i<4;i++)
			{
				cf4[0][i]=f4[0][i];
			}
			for(int i=0;i<4;i++)
			{
				cf4[1][i]=(int) (f4[1][i]*Math.cos(theta*(Math.PI/180))-f4[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf4[2][i]=(int) (f4[1][i]*Math.sin(theta*(Math.PI/180))-f4[2][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			//Compilation of fifth face
			for(int i=0;i<4;i++)
			{
				cf5[0][i]=f5[0][i];
			}
			for(int i=0;i<4;i++)
			{
				cf5[1][i]=(int) (f5[1][i]*Math.cos(theta*(Math.PI/180))-f5[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf5[2][i]=(int) (f5[1][i]*Math.sin(theta*(Math.PI/180))-f5[2][i]*Math.cos(theta*(Math.PI/180)));
			}
			
		}
		
		public void ycompile()
		{
			//Compilation of first face
			for(int i=0;i<4;i++)
			{
				cf1[0][i]=(int) (cf1[0][i]*Math.cos(theta*(Math.PI/180))+cf1[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf1[1][i]=cf1[1][i];
			}
			
			for(int i=0;i<4;i++)
			{
				cf1[2][i]=(int) (cf1[2][i]*Math.cos(theta*(Math.PI/180))-cf1[0][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			//Compilation of second face
			for(int i=0;i<4;i++)
			{
				cf2[0][i]=(int) (cf2[0][i]*Math.cos(theta*(Math.PI/180))+cf2[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf2[1][i]=cf2[1][i];
			}
			
			for(int i=0;i<4;i++)
			{
				cf2[2][i]=(int) (cf2[2][i]*Math.cos(theta*(Math.PI/180))-cf2[0][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			//Compilation of third face
			for(int i=0;i<4;i++)
			{
				cf3[0][i]=(int) (cf3[0][i]*Math.cos(theta*(Math.PI/180))+cf3[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf3[1][i]=cf3[1][i];
			}
			
			for(int i=0;i<4;i++)
			{
				cf3[2][i]=(int) (cf3[2][i]*Math.cos(theta*(Math.PI/180))-cf3[0][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			//Compilation of forth face
			for(int i=0;i<4;i++)
			{
				cf4[0][i]=(int) (cf4[0][i]*Math.cos(theta*(Math.PI/180))+cf4[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf4[1][i]=cf4[1][i];
			}
			
			for(int i=0;i<4;i++)
			{
				cf4[2][i]=(int) (cf4[2][i]*Math.cos(theta*(Math.PI/180))-cf4[0][i]*Math.sin(theta*(Math.PI/180)));
			}
			
			//Compilation of fifth face
			for(int i=0;i<4;i++)
			{
				cf5[0][i]=(int) (cf5[0][i]*Math.cos(theta*(Math.PI/180))+cf5[2][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf5[1][i]=cf5[1][i];
			}
			
			for(int i=0;i<4;i++)
			{
				cf5[2][i]=(int) (cf5[2][i]*Math.cos(theta*(Math.PI/180))-cf5[0][i]*Math.sin(theta*(Math.PI/180)));
			}
			
		}
		
		public void zcompile()
		{
			//Compilation of first face
			for(int i=0;i<4;i++)
			{
				cf1[0][i]=(int) (cf1[0][i]*Math.cos(theta*(Math.PI/180))-cf1[1][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf1[1][i]=(int) (cf1[0][i]*Math.sin(theta*(Math.PI/180))+cf1[1][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf1[2][i]=cf1[2][i];
			}
			
			//Compilation of second face
			for(int i=0;i<4;i++)
			{
				cf2[0][i]=(int) (cf2[0][i]*Math.cos(theta*(Math.PI/180))-cf2[1][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf2[1][i]=(int) (cf2[0][i]*Math.sin(theta*(Math.PI/180))+cf2[1][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf2[2][i]=cf2[2][i];
			}
			//Compilation of third face
			for(int i=0;i<4;i++)
			{
				cf3[0][i]=(int) (cf3[0][i]*Math.cos(theta*(Math.PI/180))-cf3[1][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf3[1][i]=(int) (cf3[0][i]*Math.sin(theta*(Math.PI/180))+cf3[1][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf3[2][i]=cf3[2][i];
			}
			//Compilation of forth face
			for(int i=0;i<4;i++)
			{
				cf4[0][i]=(int) (cf4[0][i]*Math.cos(theta*(Math.PI/180))-cf4[1][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf4[1][i]=(int) (cf4[0][i]*Math.sin(theta*(Math.PI/180))+cf4[1][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf4[2][i]=cf4[2][i];
			}
			//Compilation of fifth face
			for(int i=0;i<4;i++)
			{
				cf5[0][i]=(int) (cf5[0][i]*Math.cos(theta*(Math.PI/180))-cf5[1][i]*Math.sin(theta*(Math.PI/180)));
			}
			for(int i=0;i<4;i++)
			{
				cf5[1][i]=(int) (cf5[0][i]*Math.sin(theta*(Math.PI/180))+cf5[1][i]*Math.cos(theta*(Math.PI/180)));
			}
			
			for(int i=0;i<4;i++)
			{
				cf5[2][i]=cf5[2][i];
			}
		}
		
		public void setTheta(int theta)
		{
			this.theta=theta;
		}
		public int[][] getf1()
		{
			//Math.cos(45.0*(Math.PI/180));
			return cf1;
		}
		public int[][] getf2()
		{
			return cf2;
		}
		public int[][] getf3()
		{
			return cf3;
		}
		public int[][] getf4()
		{
			return cf4;
		}
		public int[][] getf5()
		{
			return cf5;
		}
	}
	
	class Pointc
	{
		public int[] convert(int p[])
		{
			if(p.length<3)
				return null;
			
			
			double x=p[0];
			double y=p[1];
			double z=p[2];
			
			x = x + z * Math.cos(45.0*(Math.PI/180));
			y = y + z * Math.sin(45.0*(Math.PI/180));
			
			System.out.println("\nx : "+x+" y : "+y);
			return new int[]{(int)x,(int)y};
		}
	}
	
	class Fpointc extends Pointc
	{
		
		public int[] convert(int p[])
		{
			if(p.length<3)
				return null;
			
			
			double x=p[0];
			double y=p[1];
			double z=p[2];
			
			x = x + z * Math.cos(45.0*(Math.PI/180));
			y = y + z * Math.sin(45.0*(Math.PI/180));
			
			System.out.println("\nx : "+x+" y : "+y);
			return new int[]{(int)x,(int)y};
		}
	}
	
	
	class NTEnv
	{
		
		
		NTEnv(Graphics g,int x,int y)
		{
			this.g=(Graphics2D)g;
			this.org[0]=x;
			this.org[1]=y;
		}
		protected int org[] = new int[]{100,100};
		public Graphics2D g;
		public void setOrigin(int Orig[])
		{
			if(Orig.length<2)
			{
				System.out.println("\nUnable to set Origin");
				return;
			}
			
			
			this.org[0]=Orig[0];
			this.org[1]=Orig[1];
			System.out.println("\n Origin is set to ( "+org[0]+" , "+org[1]+" )");
			return;
		}
		
	    public void drawLine(int x1,int y1,int z1,int x2,int y2,int z2)
		{
	    	
	    	Pointc pc=new Pointc();
	    	int p1[]=pc.convert(new int[]{x1,y1,z1});
			int p2[]=pc.convert(new int[]{x2,y2,z2});
			
			g.drawLine(org[0]+p1[0],org[1]+p1[1] ,org[0]+p2[0], org[1]+p2[1]);
		}
	    
	    public void drawPoly(int px[],int py[],int pz[],int np)
	    {
	    	if(px.length<np || py.length<np || pz.length<np)
	    	{
	    			System.out.println("\nInsufficient points");
	    			return;
	    	}
	    	
	    	int cpx[]=new int[np];
	    	int cpy[]=new int[np];
	    	/*
	    	 loop to convert 3D points to 2D points.
	    	 */
	    	NPointc pc=new NPointc();
	    	for(int i=0;i<np;i++)
	    	{
	    		int cp[]=pc.convert(new int[]{px[i],py[i],pz[i]});
	    		cpx[i]=org[0]+cp[0];
	    		cpy[i]=org[1]+cp[1];
	    	}
	    	
	    	g.drawPolygon(cpx,cpy,np);
	    	
	    }
	    
	    public void fillPoly(int px[],int py[],int pz[],int np)
	    {
	    	if(px.length<np || py.length<np || pz.length<np)
	    	{
	    			System.out.println("\nInsufficient points");
	    			return;
	    	}
	    	
	    	int cpx[]=new int[np];
	    	int cpy[]=new int[np];
	    	/*
	    	 loop to convert 3D points to 2D points.
	    	 */
	    	NPointc pc=new NPointc();
	    	for(int i=0;i<np;i++)
	    	{
	    		int cp[]=pc.convert(new int[]{px[i],py[i],pz[i]});
	    		cpx[i]=org[0]+cp[0];
	    		cpy[i]=org[1]+cp[1];
	    	}
	    	
	    	g.fillPolygon(cpx,cpy,np);
	    	
	    }
	    
	    public void drawCube(int l)
	    {
	    	
	    	Cube cube=new Cube(l);
	    	
	    	for(int i=0;i<10;i++)
	    	{
	    		g.setColor(new Color(255,255,255));
	    		g.fillRect(org[0]-3*l,org[1]-3*l,6*l,6*l);
	    		
	    		
	    		cube.setTheta(i*40);
	    	//	cube.reset();
	    		
	    		/*
	    		   // Rotation about x axis
	    		     cube.xcompile();  
	    		 */
	    		/*
	    		   // Rotation about y axis
	    		     cube.ycompile();  
	    		 */
	    		/*
	    		   // Rotation about z axis
	    		     cube.zcompile();  
	    		 */
	    		
	    		   // Rotation about x and y axis
	    		     cube.xcompile(); 
	    		     cube.ycompile();  
	    		 
	    		/*
	    		   // Rotation about x and z axis
	    		     cube.xcompile(); 
	    		     cube.zcompile();  
	    		 */
	    		/*
	    		   // Rotation about y and z axis
	    		     cube.ycompile(); 
	    		     cube.zcompile();  
	    		 */
	    		/*
	    		   // Rotation about x and y and z axis
	    		     cube.xcompile(); 
	    		     cube.ycompile(); 
	    		     cube.zcompile(); 
	    		     */ 
	    		 
	    	
	    	g.setColor(new Color(0,0,255,255));
	    	//draw back face no 1.
	    	this.fillPoly(cube.getf1()[0],cube.getf1()[1],cube.getf1()[2],4);
	    	
	    	g.setColor(new Color(255,0,0,255));
	    	this.fillPoly(cube.getf2()[0],cube.getf2()[1],cube.getf2()[2],4);
	    	
	    	g.setColor(new Color(0,255,0,255));
	    	this.fillPoly(cube.getf3()[0],cube.getf3()[1],cube.getf3()[2],4);
	    	
	    	g.setColor(new Color(0,255,255,255));
	    	this.fillPoly(cube.getf4()[0],cube.getf4()[1],cube.getf4()[2],4);
	    	
	    	g.setColor(new Color(255,0,255,255));
	    	this.fillPoly(cube.getf5()[0],cube.getf5()[1],cube.getf5()[2],4);
	    	
	    	try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	}
	    	/*
	    	g.setColor(new Color(0,0,255,100));
	    	this.fillPoly(cube.getf4()[0],cube.getf4()[1],cube.getf4()[2],4);
	    	
	    	g.setColor(new Color(0,0,255,55));
	    	this.fillPoly(cube.getf5()[0],cube.getf5()[1],cube.getf5()[2],4);
	    	*/
	    	/*this.fillPoly(
	    			new int[]{0,0,l,l},
	    			
	    			new int[]{0,-l,-l,0},
	    			
	    			new int[]{0,0,0,0},
	    			4);
	    	
	    	g.setColor(new Color(0,0,255,255));
	    	//draw bottom  face no 2.
	    	this.fillPoly(
	    			new int[]{0,l,l,0},
	    			
	    			new int[]{0,0,0,0},
	    			
	    			new int[]{0,0,l,l},
	    			4);
	    	
	    	g.setColor(new Color(0,0,255,155));
	    	//draw right side face no 3.
	    	this.fillPoly(
	    			
	    			
	    			new int[]{l,l,l,l},
	    			
	    			new int[]{0,-l,-l,0},
	    			
	    			new int[]{0,0,l,l},
	    			4);
	    	
	    	
	    	g.setColor(new Color(0,0,255,100));
	    	//draw left side  face no 4.
	    	this.fillPoly(
	    			new int[]{0,0,0,0},
	    			
	    			new int[]{0,-l,-l,0},
	    			
	    			new int[]{0,0,l,l},
	    			4);
	    	g.setColor(new Color(0,0,255,55));
	    	//draw front  face no 5.
	    	this.fillPoly(
	    			new int[]{0,l,l,0},
	    			
	    			new int[]{-l,-l,0,0},
	    			
	    			new int[]{l,l,l,l},
	    			4);
	    			*/
	    	
	    }
	} 
	
	class NPointc
	{
		public int[] convert(int p[])
		{
			if(p.length<3)
				return null;
			
			
			double x=p[0];
			double y=p[1];
			double z=p[2];
			
			x = x + z * Math.cos(90.0*(Math.PI/180));
			y = y + z * Math.sin(90.0*(Math.PI/180));
			
			System.out.println("\nx : "+x+" y : "+y);
			return new int[]{(int)x,(int)y};
		}
	}
	
}
