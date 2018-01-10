import javax.swing.*;




import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.URL;
import java.util.Date;
public  class GSkeleton extends JPanel implements MouseListener
 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public static final MouseListener MouseHandler = null;
	static Dimension d = new Dimension(1570,680);  //Default 1570,680.
	static Point l = new Point(-10,0);
	 static int scale ;
	 static int lscale;
	static int lstartAngle = 140 ;
	static int rstartAngle = -27;
	static Timer timer ;
	static Timer t;
	static Graphics2D scin;
	static Graphics2D g2d;
	static JFrame panel;
	static boolean PullArm;
	static String VoiceString;
	static Timer Waittimer;
	static String TypedWord = "";
	static boolean  Arm[]= new boolean[2];
    static boolean login_Field = false;
    static JPanel content;
    static String User_Name = "";
    static String Password = "";
    static boolean User_Box = true;
    static boolean Pass_Box = false;
    static boolean show_wating = false;
    static Timer wating;
    static int PX;
    static int DX=0;
    static boolean New_Note = false;
    static int NoteCount =0;
    static int Line_Mask = 0;
    static String Lines[];
    static boolean Search_field = false;
    //parameters
    
    static String Time[] = new String[7];
    static Timer wtt;
    static String current_loc;
    static String c_locd;
    static String c_locgem;
    static String volume;
    static String Notes[] = new String[100];
    static  String apps [] = new String[10];
    static String search_text = ""; 
	public  class keylistener implements KeyListener,WindowListener {
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getKeyChar() == '\n')
				playAudio("LASER");
			else
				playAudio("buttonclick");
		//	System.out.println("typed "+ TypedWord );
			if(login_Field){
				if(e.getKeyChar() == '\n')
				{
					if(User_Box){
						User_Box = false;
						Pass_Box = true;
					}else if(Pass_Box){
						Pass_Box = false;
						User_Box = true;
						Authorize(User_Name,Password);
						Repaint();
					}
				}else if(User_Box){
					if(e.getKeyChar() == (char)KeyEvent.VK_BACK_SPACE )
					{
						if(User_Name.length() > 0)
						User_Name = User_Name.substring(0, User_Name.length()-1);
					}
					else User_Name = User_Name + e.getKeyChar();
					Repaint();
				}else if(Pass_Box)
				{
					if(e.getKeyChar() == (char)KeyEvent.VK_BACK_SPACE ){
						if(Password.length() > 0)
						Password	= Password.substring(0, Password.length()-1);
					}
					else Password = Password + e.getKeyChar();
					Repaint();
				}
				
			}else if(Search_field){
				if(e.getKeyChar() == '\n')
				{
					setnotification("Not connected to Internet .");
					Search_field = false;
					search_text = ""; 
				}else if(e.getKeyChar() == (char)KeyEvent.VK_BACK_SPACE )
				{
					if(search_text.length() > 0)
					search_text=search_text.substring(0,search_text.length()-1);
				}
				else
					search_text +=e.getKeyChar();
				Repaint();
			}
			else if(e.getKeyChar() == '\n'){
				if( TypedWord.equalsIgnoreCase ("quit")){
					System.exit(0); 
				}
				else if( TypedWord.equalsIgnoreCase ("login")){
					Start_login();
				}
				
				TypedWord = "";
			}else{
			TypedWord = TypedWord + e.getKeyChar();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			/* if(e.getKeyCode() == KeyEvent.VK_ENTER ){
				if( TypedWord.equalsIgnoreCase ("quit")){
					System.exit(0); }
				else if( TypedWord.equalsIgnoreCase ("login")){
					System.out.println("got login");
					Start_login();
				} 
			} */
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		//	t.stop();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			//t.stop();
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			//t.start();
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			//t.stop();
		}
		
	}
	
	public GSkeleton(){
			addMouseListener(this);
			keylistener listener = new keylistener();
			panel.addKeyListener(listener);
			panel.addWindowListener(listener);
		}


		
		int sangles[] = new int[40];
		int aangles[] = new int[40];
		int angleCount = 0;
		int x,y;
		static boolean repainting = false; 
		BufferedImage OSC;
		Image img;

		
	public  void Repaint()
		{
			repaint();
		}
		public void createOSC()
		{
			int Width = d.width;
			int Height = d.height;
			Width = Width-(int)(Width/5.2);
			Height = Height-(int)(Height/20);
			scale = Width/200;
  OSC = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB_PRE);
  Graphics gh = OSC.getGraphics();
  
  int d = scale*9;
  ClassLoader cl = GSkeleton.class.getClassLoader();
	URL url = cl.getResource("blue.png");
	 img = Toolkit.getDefaultToolkit().createImage(url);
	
	MediaTracker tracker = new MediaTracker(this);
	tracker.addImage(img, 0);
	try {
	tracker.waitForAll();
	} catch(InterruptedException ie) {}
	
	
	
 //
	gh.setColor(new Color(5,5,20));
    gh.fill3DRect(0, 0, getWidth(), getHeight(),true);
    gh.setColor(new Color(00,00,100,100));
    gh.drawImage(img, scale*20+5,scale*60+5,d-10,d-10,null);
	gh.fillRect(scale*20, scale*60, d, d);
  //gh.fillArc((getWidth()/2)-100, (getHeight()/2)-100,200,200,0,360);
		}
	public void paintComponent(Graphics g)
	{
		RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		renderHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		super.paintComponent(g);
		 g2d = (Graphics2D)g;
		//g.drawLine(10, 20, 70, 70);
		g2d.setRenderingHints(renderHints);
		if(OSC==null)
			createOSC();
	 //  panel.setBackground(new Color());
		g2d.setColor(new Color(0,5,15));
		g2d.clearRect(0, 0,d.width,d.height);
	    g2d.drawImage(OSC, 0,0, null);
	 
	    DrawNet(g2d);
	    DrawScale(g2d);
	    DrawLogo(g2d);

	  	//Graphics2D Img2d =(Graphics2D) img.getGraphics();
	    //g2d.clearRect(0, 0, 600,300);
	    
	    DrawCircle(g2d);
	    
	    DrawDisks(g2d);
	    drawSearchField(g2d);
	    if(login_Field){
	    	 draw_loginfield(g2d);
	    	 
	    }
	    if(show_wating)
	    	Draw_Wating(g2d);
	    if(New_Note)
	    	Draw_Notes();
	    
	    draw_sideArcLine();
	   // g2d.dispose();
	    
	}
	public void DrawDisks(Graphics2D g) {
		// TODO Auto-generated method stub
		
		int Start_DX = scale*200;
		int Start_DY = scale*10;
		int Height =scale*24;
		int Width = (int) (scale*1.9);
	    int nod = 36;
	    int nh = Height/nod;
		g.setStroke(new BasicStroke(2));
		g.drawRect((int) (Start_DX), Start_DY, Width, Height);
		g.drawRect((int) (Start_DX+Width*2.5), Start_DY, Width, Height);
		g.drawRect((int) (Start_DX+Width*5), Start_DY, Width, Height);
		
		File Disks[] =File.listRoots();
		int e=(int) (Disks[2].getFreeSpace()/(1024*1024*1024));
		int ep=((155-e)*100)/155;
		int fd=(ep*Height)/100;
		for(int y=Start_DY+Height;y>Start_DY;y-=nh)
		{
			if(y > Start_DY+Height-fd){
				g.fillRect(Start_DX+1,y-nh,Width-1,nh);
			}
				else
			g.drawRect(Start_DX+1,y-nh,Width-2,nh);
		}
	// for drive H:\	
		 e=(int) (Disks[5].getFreeSpace()/(1024*1024*1024));
		 ep=((155-e)*100)/155;
		 fd=(ep*Height)/100;
		for(int y=Start_DY+Height;y>Start_DY;y-=nh)
		{
			if(y > Start_DY+Height-fd){
				g.fillRect((int) (Start_DX+Width*2.5)+1,y-nh,Width-1,nh);
			}
				else
			g.drawRect((int) (Start_DX+Width*2.5)+1,y-nh,Width-2,nh);
		}
		 e=(int) (Disks[6].getFreeSpace()/(1024*1024*1024));
		 ep=((155-e)*100)/155;
		 fd=(ep*Height)/100;
		for(int y=Start_DY+Height;y>Start_DY;y-=nh)
		{
			if(y > Start_DY+Height-fd){
				g.fillRect((int) (Start_DX+Width*5)+1,y-nh,Width-1,nh);
			}
				else
			g.drawRect((int) (Start_DX+Width*5)+1,y-nh,Width-2,nh);
		}
		
		
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
     System.out.print("state is "+osbean.getSystemLoadAverage());
	}
	public void DrawNet(Graphics2D g){
		
		int Height = d.height;
		int Width = d.width;
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = Height;
		g.setColor(new Color(0.0f, 0.0f, 1.0f,0.5f));
		g.setStroke(new BasicStroke(0.3F));
		//g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10 ,		dashPattern, 0));
		g.translate(00,00);
		//g.rotate(270.0,200,200);
		for(x1=0;x1<Width;x1+=10)
		{
				g.drawLine(x1, y1, x2, y2);
				x2+=10;
		}
		x1 = 0;
		x2 = Width;
		y1 = 0;
		y2 = 0;
		for(y1=0;y1<Height;y1+=10)
		{
			g.drawLine(x1, y1, x2, y2);
			y2+=10;
		}
	}
	public void DrawScale(Graphics2D g){
		int Height = d.height;
		int Width = d.width;
		Width = Width-(int)(Width/5.2);
		Height = Height-(int)(Height/20);
		int xScale = Width/20;
		g.setColor(new Color(50,50,150,200));
		 g.setStroke(new BasicStroke(4));
		g.drawLine(13,13,Width,13);
		g.drawLine(13, 13, 13, Height);
		String Scales[] = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"}; 
		for(int i = 1;i<21; i++)
		{
			g.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,13));
			g.setColor(new Color(40,40,150,200));
			g.drawString(Scales[i-1] ,i*xScale,25);
		}
	int i= 1 ;
	 while(i*xScale<Height)
	{
		g.drawString(Scales[i-1],20 ,i*xScale);
		i++;
		//System.out.println(Height);
	} 
 }
	public void DrawCircle(Graphics2D g)
	{
		int Width = d.width;
		int Height = d.height;
		Width = Width-(int)(Width/5.2);
		Height = Height-(int)(Height/20);
		scale = Width/200;
		int d;
        x = (int)(Width/2.3);
   	    y = Height/2;

		g.setColor(new Color(0,0,200,200));
		
		//draw main circles.
		g.setStroke(new BasicStroke(1.5f));
		//System.out.println(Width/2+" "+Height/2);

		d=scale*25;
		g.drawArc(getx(d/2),gety(d/2),d,d,0,360);
		d = scale*29;
		g.drawArc(getx(d/2),gety(d/2),d,d,0,360);
		d = scale*15;
		g.drawArc(getx(d/2),gety(d/2),d,d,0,360);
		g.setStroke(new BasicStroke(2.0f));
		
		//Draws left side.
		//int startAngle = 150;
		
            
           DrawLeftArm(g);
		//Drwa right sided bar .
        DrawRightArm(g);
		
		//windows logo circle
		g.setStroke(new BasicStroke(3.5f));
		d=scale*9;
		g.drawArc(scale*20,scale*60,d,d,-180,230);
		g.setStroke(new BasicStroke(0.5f));	
		/*int sx = 100;
		int SX =sx ;
		int sy = 500;
		int Logo_xp[] = new int[100];
		int Logo_yp[] = new int[100];
		int Logo_XP[] = new int[]{sx-4,sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,++sx,SX+15};
		int Logo_YP[] = new int[]{sy+12,sy,--sy,sy,--sy,sy,sy,--sy,sy,sy,sy,sy,sy,++sy,sy,++sy,sy,++sy,sy,++sy,++sy,sy,sy,++sy,sy,sy+20};
		
		int rate = 0;
		for(int i=0;i<20;i++){
			
			Logo_xp[i] = ++sx;
			if(rate==2){
				Logo_yp[i] = --sy;
				rate = 0;
				}else{
					Logo_yp[i] = sy;
					rate++;
				}
			
		}*/

		//g.fillPolygon(Logo_XP,Logo_YP,26);
		
		
		//System.out.println("CardImages = "+img);
	
		//drawing base position lines.
		g.setStroke(new BasicStroke(2.5f));
		int Start_base_X = scale*35 ;
		int base_width = scale*40;
		int Start_base_Y = scale*90 ;
		for(int i = 90 ;i<=100;i+=10)
		{
			Start_base_Y = scale*i ;
		g.drawLine(Start_base_X,Start_base_Y,Start_base_X+base_width,Start_base_Y);
		g.drawLine(Start_base_X+base_width,Start_base_Y,Start_base_X+base_width,Start_base_Y-3*scale);
		g.drawLine((int)(Start_base_X+base_width/2),Start_base_Y,(int)(Start_base_X+base_width/2),Start_base_Y-3*scale);
		}
		g.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,14));
		
		current_loc="Ajara";
		c_locgem = "10.39G";
		if(current_loc == null)
			g.drawString("__-__", Start_base_X+scale,scale*93);
		else
		  g.drawString(current_loc, Start_base_X+scale, scale*93);
		if(c_locd == null)
			 g.drawString("0.0",(int)(Start_base_X+base_width/2),scale*93);
		else
			g.drawString(c_locd,(int)(Start_base_X+base_width/2),scale*93);
		if(c_locgem == null)
			g.drawString("_-_",Start_base_X+base_width-base_width/10,scale*93);
		else
			g.drawString(c_locgem,Start_base_X+base_width-base_width/10,scale*93);
		current_loc = null;
		c_locgem = null;
		if(current_loc == null)
			g.drawString("__-__", Start_base_X+scale,scale*103);
		else
		  g.drawString(current_loc, Start_base_X+scale, scale*103);
		if(c_locd == null)
			 g.drawString("0.0",(int)(Start_base_X+base_width/2),scale*103);
		else
			g.drawString(c_locd,(int)(Start_base_X+base_width/2),scale*103);
		if(c_locgem == null)
			g.drawString("_-_",Start_base_X+base_width-base_width/10,scale*103);
		else
			g.drawString(c_locgem,Start_base_X+base_width-base_width/10,scale*103);
		//draw notification area.
		int ox = scale*210;
		int oy = scale*64;
		int bwidth = (int)(scale*1.5);
		int bheight =scale*38;
		int fx = scale*180;
		int fy =scale*60;
		int fwidth = scale*25;
		int fheight = scale*42;
		int xpoints[] = new int[]{ox,ox,ox+bwidth,ox+bwidth,ox+bwidth-bwidth/4,ox+bwidth-bwidth/4,ox+bwidth,ox+bwidth,ox};
		int ypoints[] = new int[]{oy,oy+bheight,oy+bheight,oy+bheight-bheight/12,oy+bheight-bheight/10,oy+bheight/10,oy+bheight/12,oy,oy};
		
		g.fillPolygon(xpoints,ypoints,8);
		g.setColor(new Color(0,0,200,140));
        g.fillRect(fx,fy+fheight,fwidth/13,fwidth/13);	
    	g.setColor(new Color(0,0,200,240));
        for(int i=fx+fwidth/5;i<=fx+fwidth;i+=fwidth/5)
        {
        	 g.fillRect(i,fy+fheight,fwidth/13,fwidth/13);
        }
        Color ccolor = g.getColor();
		g.setColor(new Color(0,0,100,200));
        g.fillRect(fx,fy,fwidth+scale*5,scale);
		g.setColor(new Color(0,0,200,200));
        g.fillRect(fx+scale*2, fy+scale,fwidth/2,scale/2);
        g.fillRect(fx+fwidth/2+2*scale, fy+scale/2,fwidth/2-scale,scale/2);
		g.setColor(new Color(0,0,220,200));
        g.fillRect(fx+fwidth+fwidth/10, fy+scale/2,fwidth/10,scale);
    	g.setColor(new Color(0,0,220,255));
        g.fillRect(fx-scale*2, fy+scale*2,fwidth+scale*6,(int)(scale*0.8));
    	g.setColor(new Color(20,10,180,200));
    	g.fillRect(fx+fwidth/2+scale*4, fy+scale,fwidth/3,scale);
       	g.setColor(new Color(0,0,100,255));
        g.fillRect(fx-scale*3, fy+scale*3,fwidth+scale*8,(int)(scale*0.8));
        g.setColor(new Color(20,10,180,200));
     	g.fillRect(fx-scale*3, fy,scale*2,scale*2);
      	g.fillRect(fx-scale*3, (int)(fy+scale*2.5),scale*4,scale);
        //draw Time field .
        
        Date cdate = new Date();  
        String date = cdate.toString();
        Analyze(date);
      //  System.out.println("Time is "+ date);
  
        int STime_X = scale*10;
        int STime_Y = scale*8;
        int Time_Height = scale*8;
        int Time_Width = scale*32;
        
        
		g.setColor(new Color(0,0,70,100));
		int xp[] = new int[]{STime_X+2*scale,STime_X+Time_Width,STime_X+Time_Width,STime_X+Time_Width-2*scale,STime_X,STime_X};
		int yp[] = new int[]{STime_Y,STime_Y,STime_Y+Time_Height-Time_Height/3,STime_Y+Time_Height,STime_Y+Time_Height,STime_Y+Time_Height/3}; 
		g.fillPolygon(xp,yp,xp.length);
	    g.setColor(new Color(10,10,140,255));
	    g.setStroke(new BasicStroke(2.3f));
	    g.drawPolygon(xp,yp,xp.length);
        
       g.drawLine(STime_X+Time_Width,STime_Y+Time_Height/2 ,STime_X+Time_Width/6,STime_Y+Time_Height/2);
        
        Font font = new Font("Arial",Font.BOLD ,(int)(scale*2.5));
		g.setFont(font);
		String c = "";
		for(int i = 0; i< Time[3].length();i++){
			c = c+Time[3].charAt(i);
         g.drawString(c, STime_X+Time_Width/3+i*12, STime_Y+Time_Height-scale);
         c = "";
		}
         g.setFont(new Font("Arial",Font.BOLD ,scale*2));
         String word = Time[0]+" "+Time[2]+" "+Time[1]+"  "+Time[5];
        for(int i = 0; i< word.length();i++){
 			c = c+word.charAt(i);
 			g.drawString(c, STime_X+3*scale+i*8, STime_Y+Time_Height/2-scale);
          c = "";
 		} 
        //g.drawString(word, STime_X+3*scale, STime_Y+Time_Height/2-scale);
        
          g.setColor(ccolor);
        //Draws side Disks Indicating Fields .
         STime_X = scale*10;
         STime_Y = scale*40;
         Time_Height = scale*2;
         Time_Width = scale*20;
         g.setStroke(new BasicStroke(1.0f));
         g.getColor();
         new Color(180,10,10,200);
         g.setStroke(new BasicStroke(1.5f));
     int pxy1[] ;
     int pxy2[] ;
     


         
         //draws second fill side arc 2 
         pxy1=getxy(x,y,scale*20,sangles[6],0);
         pxy2 = getxy(x,y,scale*21,sangles[6],0);
         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
         pxy1=getxy(x,y,scale*20,sangles[6],aangles[6]);
         pxy2 = getxy(x,y,scale*21,sangles[6],aangles[6]);
         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
         
         int CAngle ;
         int Angleofsq = aangles[6]/70 ;
         int LastAngle = sangles[6] + aangles[6]-Angleofsq;
         int pxy3[] = new int[2];
         int pxy4[] = new int[2];
         //System.out.println("cangle = "+CAngle+" "+Angleofsq);
        for(CAngle = sangles[6]+Angleofsq ; CAngle < LastAngle; CAngle+=2*Angleofsq)
        {
        	pxy1 = getxy(x,y,scale*20,CAngle,0) ; 
        	 pxy2 = getxy(x,y,scale*21,CAngle,0);
        	
        	CAngle += Angleofsq;
        	
        	pxy4 = getxy(x,y,scale*20,CAngle,Angleofsq) ; 
       	    pxy3 = getxy(x,y,scale*21,CAngle,Angleofsq);
       	 g.fillPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
        }
        FillMainCircle(g);
        //Filling of main  outer circles . 
             //draws scales to complete circle
         //g.drawLine(x-d/2, y, x+d/2, y);
        // g.drawLine(x, y-d/2, x, y+d/2);
              //int x1[] = new int[]{100,101,102,103,104,104,105,106,107,108,109,110};
         //int y1[] = new int[]{100,99,98,97,96,95,96,97,98,99};
        // g.drawPolygon(x1,y1,10);
         
        
        
        DrawVolume(g); 
        g.setColor(new Color(0,0,255,255));
       int  sxy[] = new int[2];
       sxy = getxy(x,y,scale*30,45,0);
       g.drawLine(sxy[0],sxy[1],sxy[0]+scale*10,sxy[1]);
       g.drawLine(sxy[0]+scale*10,sxy[1],sxy[0]+scale*10,sxy[1]+scale*45);
       g.drawLine(sxy[0],sxy[1]+scale*45,sxy[0]+scale*10,sxy[1]+scale*45);
       
       int sy = sxy[1]+scale*5;
       int dm = 12;
       while(sy < sxy[1]+scale*45 ){
    	   g.drawLine(sxy[0]+scale*9, sy,sxy[0]+scale*22,sy);
    	   g.fillArc(sxy[0]+scale*25-dm/2,sy-scale*2-dm/2,dm,dm ,0,360);
    	   sy += scale*5;
    	   
       }
       g.setColor(new Color(0,0,220,100));
       g.setStroke(new BasicStroke(4));
       g.drawLine(sxy[0],sxy[1],sxy[0]+scale*10,sxy[1]);
       g.drawLine(sxy[0]+scale*10,sxy[1],sxy[0]+scale*10,sxy[1]+scale*45);
       g.drawLine(sxy[0],sxy[1]+scale*45,sxy[0]+scale*10,sxy[1]+scale*45); 
       
        sy = sxy[1]+scale*5;
        g.setColor(new Color(0,0,200,150));
        dm = 20;
        g.setStroke(new BasicStroke(2));
       while(sy < sxy[1]+scale*45 ){
    	   g.drawLine(sxy[0]+scale*9, sy,sxy[0]+scale*24,sy);
    	   g.fillArc(sxy[0]+scale*25-dm/2,sy-scale*2-dm/2,dm,dm ,45,300);
    	   sy += scale*5;
    	   
       }
       apps[0] = "YouTube";
       apps[1]="Facebook";
       apps[2]="Gmail";
       apps[3]="Microsoft";
       apps[4]="Githib";
       apps[5]="Twitter";
       g.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,13));
       g.setColor(new Color(0,0,200,200));
       int i = 0;
       sy = sxy[1]+scale*5;
       while(i<6){
    	   g.drawString(apps[i],sxy[0]+scale*10+scale,sy-scale);
    	   sy +=scale*5;
    	   i++;
       }
         angleCount = 0;
       
         
        // g.drawImage(img,100,50,100,200,this);
	}
	public void FillMainCircle(Graphics2D g){
		Color Ccolor = g.getColor();
       int  Angleofsq = 360/140;
       int LastAngle = 360 -Angleofsq;
       int pxy1[] = new int[2];
       int pxy2[] = new int[2];
       int pxy3[] = new int[2];
       int pxy4[] = new int[2];
       
        for(int CAngle = 0+Angleofsq ; CAngle < LastAngle; CAngle+=2*Angleofsq)
        {
        	g.setColor(Ccolor);
        	pxy1 = getxy(x,y,(int)(scale*12.5),CAngle,0) ; 
        	 pxy2 = getxy(x,y,(int)(scale*14.5),CAngle,0);
        	
        	 
        	CAngle += Angleofsq;
        	
        	pxy4 = getxy(x,y,(int)(scale*12.5),CAngle,Angleofsq) ; 
       	    pxy3 = getxy(x,y,(int)(scale*14.5),CAngle,Angleofsq);
      	/*  if(num <  Filled ){
       		g.setColor(new Color(0.0f,0.0f,0.9f,0.2f)); 
       		  g.fillPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
       		g.setColor(new Color(0.8f,0.8f,1.0f,0.2f)); 
       		CAngle -= Angleofsq;
       	 pxy1 = getxy(x,y,(int)(scale*12.6),CAngle,0) ; 
    	 pxy2 = getxy(x,y,(int)(scale*14.6),CAngle,0);
    	
    	 
    	CAngle += Angleofsq;
    	
    	pxy4 = getxy(x,y,(int)(scale*12.6),CAngle,Angleofsq) ; 
   	    pxy3 = getxy(x,y,(int)(scale*14.6),CAngle,Angleofsq);
       	g.drawPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
       	g.setColor(new Color(0.8f,0.8f,1.0f,0.2f)); 
       	g.drawPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
     	g.setColor(new Color(0.8f,0.8f,1.0f,0.2f)); 
       	g.drawPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
      
       	num++;
       	 continue;
       	    }
       	        */       	    
       	 g.fillPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
        }/*
        for(int CAngle =Angleofsq*100 ; CAngle > 0 ; CAngle-=3*Angleofsq)
        {
       
        	pxy1 = getxy(x,y,(int)(scale*12.5),CAngle,0) ; 
        	 pxy2 = getxy(x,y,(int)(scale*14.5),CAngle,0);
        	
        	 
        	CAngle += Angleofsq;
        	
        	pxy4 = getxy(x,y,(int)(scale*12.5),CAngle,Angleofsq) ; 
       	    pxy3 = getxy(x,y,(int)(scale*14.6),CAngle,Angleofsq);
       	    

       	 g.fillPolygon(new int[]{pxy1[0],pxy2[0],pxy3[0],pxy4[0]},new int[]{pxy1[1],pxy2[1],pxy3[1],pxy4[1]},4);
         num++;
        } */
        g.setColor(Ccolor);
	}
	public void DrawLeftArm(Graphics2D g){
		if(!PullArm)
		   lscale = 0; 
		int d = lscale*90;
		sangles[0]= lstartAngle;
		aangles[0]= 26;
		angleCount++;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[0],aangles[0]);
		d = lscale*80;
		refreshAngles(10,20);
		//System.out.println("angles are :"+sangles[0]+" "+aangles[0]);
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[1],aangles[1]);
		g.setStroke(new BasicStroke(1.5f));
		d = lscale*65;
		sangles[angleCount] = lstartAngle+lscale/2;
		aangles[angleCount++] = aangles[0]+aangles[1]+lscale/2;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = lscale*65;
		sangles[angleCount] = sangles[1]+aangles[1]+lscale*2;
		aangles[angleCount++] = 13;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = lscale*60;
		refreshAngles(3,13);
		//System.out.println("angles "+angleCount+" "+sangles[angleCount-1]);
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		//other circles .
		g.setStroke(new BasicStroke(3f));
		d = scale*35;
		sangles[angleCount] =( sangles[4]+aangles[4]+ (int)(lscale/1.5)) - 360 ;
		aangles[angleCount++] = sangles[0]-3+(sangles[angleCount-1]*-1);
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		g.setStroke(new BasicStroke(1.5f));
		d = scale*40;
		sangles[angleCount] = ( sangles[4]+aangles[4]+ (int)(scale/1.5)) - 360 + scale*3 ;
		aangles[angleCount++] = 118 ;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = scale*42;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
	//Complete Arcs
		int pxy1[] = new int[2];
		int pxy2[] = new int[2];
		
		   pxy1 = getxy(x,y,lscale*15,lstartAngle,-2);
	         pxy2 = getxy(x,y,lscale*42,lstartAngle,-4);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*45,lstartAngle-10,10);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*45,lstartAngle,aangles[0]);
	         pxy2 = getxy(x,y,lscale*40,sangles[1],0);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*37,sangles[1],aangles[1]+4);
	         pxy2 = getxy(x,y,lscale*15,sangles[1],aangles[1]+3);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy2 = getxy(x,y,lscale*40,sangles[1],aangles[1]);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         //draws second component
	         //
	         pxy1 = getxy(x,y,lscale*21,sangles[3],-lscale/4);
	         pxy2 = getxy(x,y,lscale*15,sangles[3],-lscale/6);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*30,sangles[3],-(int)(lscale/1.5));
	         pxy2 = getxy(x,y,(int)(lscale*32.5),sangles[3],0);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy2 = getxy(x,y,(int)(lscale*22),sangles[3],-(int)(lscale/1.5));
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*21,sangles[3],-lscale/4);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         
	         pxy1 = getxy(x,y,(int)(lscale*32.5),sangles[3],aangles[3]);
	         pxy2 = getxy(x,y,lscale*30,sangles[4],0);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         
	         pxy1 = getxy(x,y,lscale*23,sangles[4],aangles[4]+(int)(lscale));
	         pxy2 = getxy(x,y,lscale*15,sangles[4],aangles[4]+lscale/2);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*28,sangles[4],aangles[4]+(int)(lscale/2));
	         pxy2 = getxy(x,y,lscale*24,sangles[4],aangles[4]+(int)(lscale/2));
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy2 = getxy(x,y,lscale*30,sangles[4],aangles[4]);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*23,sangles[4],aangles[4]+(int)(lscale));
	         pxy2 = getxy(x,y,lscale*24,sangles[4],aangles[4]+(int)(lscale/2));
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);

	         //draws left first components scale .
	         pxy1 = getxy(x,y,lscale*38,sangles[0],aangles[0]);
	         pxy2 = getxy(x,y,lscale*15,sangles[0],aangles[0]);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,lscale*30,sangles[2],-4);
	         pxy2 = getxy(x,y,lscale*15,sangles[2],-3);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]); 
	         pxy2 = getxy(x,y,(int)(lscale*32.5),sangles[2],0);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         
	         pxy1 = getxy(x,y,lscale*15,sangles[2],aangles[2]+lscale/2);
	         pxy2 = getxy(x,y,lscale*30,sangles[2],aangles[2]+lscale/2);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
	         pxy1 = getxy(x,y,(int)(lscale*32.5),sangles[2],aangles[2]);
	         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]); 

		
	}
	public void DrawRightArm(Graphics2D g){
	int	d = scale*52;
		//System.out.println("rihght angle = ")
		sangles[angleCount] = rstartAngle ;
		aangles[angleCount++] = 23 ;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = scale*47;
		refreshAngles(4,70);
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = scale*50;
		refreshAngles(5,20);
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = scale*54;
		refreshAngles(5,12);
		//System.out.println("last anglecount is :"+(angleCount-1));
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		d = scale*58;
		sangles[angleCount] = rstartAngle - scale;
		aangles[angleCount++] = sangles[angleCount-2] +aangles[angleCount-2]  - rstartAngle + 2*scale ;
		g.drawArc(getx(d/2),gety(d/2),d,d,sangles[angleCount-1],aangles[angleCount-1]);
		
		//Complete arc
	   int  dc = scale*47;
	 		g.setStroke(new BasicStroke(1.5f));
		int pxy1[] = new int[2];
		int pxy2[] = new int[2];
        pxy1 = getxy(x,y,scale*26,sangles[7],0);
        pxy2 = getxy(x,y,scale*29,sangles[11],0);
        g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
          pxy1 = getxy(x,y,dc/2,sangles[8],0);
          pxy2 = getxy(x,y,scale*26,sangles[7],aangles[7]);
         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
         pxy1=getxy(x,y,scale*47/2,sangles[8],aangles[8]);
         pxy2 = getxy(x,y,scale*25,sangles[9],0);
         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
         pxy1 = getxy(x,y,scale*25,sangles[9],aangles[9]);
         pxy2 = getxy(x,y,scale*27,sangles[10],0);
         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
         pxy1 = getxy(x,y,scale*29,sangles[11],aangles[11]);
         pxy2 = getxy(x,y,scale*27,sangles[10],aangles[10]);
         g.drawLine(pxy1[0],pxy1[1],pxy2[0],pxy2[1]);
         
         
         int angle=sangles[11];
         int r;
         g.setColor(new Color(0,0,255,240));
         while(angle < sangles[11]+aangles[11]-scale*2)
         {
         pxy1 = getxy(x,y,scale*29,angle,scale);
         pxy2 = getxy(x,y,(int)(scale*27.8),angle,scale);
         g.drawLine(pxy1[0], pxy1[1], pxy2[0], pxy2[1]);
         angle+=scale/2;
         }
          angle=sangles[11];
         
          g.setStroke(new BasicStroke(3));
          r = scale*26;
         while(angle < sangles[11]+aangles[11]-scale*2)
         {
        	 if(angle < sangles[8])
        		 r = scale*26;
        	 else if(angle < sangles[9])
        		 r = scale*47/2;
        	 else if(angle < sangles[10]-scale)
        		 r = scale*25;
        	 else 
        		 r = scale*27;
         pxy1 = getxy(x,y,scale*29,angle,scale);
         pxy2 = getxy(x,y,r,angle,scale);
         g.drawLine(pxy1[0], pxy1[1], pxy2[0], pxy2[1]);
         angle+=scale*2;
         }
         g.setStroke(new BasicStroke(1));
         pxy1 = getxy(x,y,scale*28,sangles[9],scale*3 );
         int nx = pxy1[0];
         int ny = pxy1[1];
         int w = (int) (scale*1.5);
         int h = (int) (scale*2.5);
         int npx[] =new int[]{nx,nx+w/3,nx+w*2/3,nx+w*2/3,nx+w,nx+w,nx+w*2/3,nx+w/3,nx+w/3,nx};
         int npy[] = new int[]{ny,ny,ny+h-h/3,ny,ny,ny+h,ny+h,ny+h/3,ny+h,ny+h};
         g.fillPolygon(npx,npy,npx.length);
	}
	public void DrawVolume(Graphics2D g){
		int x = scale*10;
		int y = scale*25;
		
		int aAngle = 80;
		int sAngle = 180 - aAngle/2;
		sangles[angleCount] = sAngle ;
		aangles[angleCount++] = aAngle ;

		int width = scale*4;
		int height = scale*4;
		int d = width;
		int pxy1[] = new int[2];
		int pxy2[] = new int[2];
		
		g.setStroke(new BasicStroke(2));
		g.drawArc(x-d/2, y-d/2, width, height, sangles[angleCount-1], aangles[angleCount-1]);
	    aAngle =(int) (aAngle + 2.8*scale) ;
		sangles[angleCount] = 0 - aAngle/2 ;
		aangles[angleCount++] = aAngle ;
		d = (int)(scale*6);
		g.drawArc(x-d/2, y-d/2, d, d, sangles[angleCount-1], aangles[angleCount-1]);
		pxy1 = getxy(x,y,width/2,sangles[12],0) ;
		pxy2 = getxy(x,y,d/2,sangles[13],aangles[13]);
		g.drawLine(pxy1[0], pxy1[1],(int) (pxy1[0]+scale*2),pxy1[1]);
		g.drawLine((int) (pxy1[0]+scale*2),pxy1[1],pxy2[0],pxy2[1]);
		pxy1 = getxy(x,y,width/2,sangles[12],aangles[12]) ;
		pxy2 = getxy(x,y,d/2,sangles[13],0);
		g.drawLine(pxy1[0], pxy1[1],(int) (pxy1[0]+scale*2),pxy1[1]);
		g.drawLine((int)(pxy1[0]+scale*2),pxy1[1],pxy2[0],pxy2[1]);
		
		int Cur_Vol = 4;
		for(int i = 7;i<Cur_Vol+7;i++){
			
		
				sangles[angleCount] = 0 - aAngle/2 ;
				aangles[angleCount++] = aAngle ;
				d = (int)(7*i);
				
				g.drawArc((int)(x-d*0.5),(int)( y-d*0.5), d, d, sangles[angleCount-1], aangles[angleCount-1]);
				 //aAngle =(int) (aAngle + 4) ;
		}
		
		volume = "23";
	Color ccolor = g.getColor();
	g.setColor(new Color(10,10,150,220));
		g.drawString(volume, x-2, y+3);
		g.setColor(ccolor);
		//g.dispose();
	}
	
	public void draw_loginfield(Graphics2D g){
		int Start_X = scale*60;
		int Start_Y = scale*35;
		int Width = scale *90;
		int Height = scale*35;
		//System.out.println("in draw field");
		/*g.setColor(new Color(255,255,255));
		g.fillRect(Start_X, Start_Y, Width, Height); */
		Color ccolor = g.getColor();
		Color dcolor = new Color(00,00,80,120);
		g.setColor(dcolor);
		g.fillRect(Start_X, Start_Y, Width, Height);
		g.setColor(new Color(00,00,20,230));
		g.setStroke(new BasicStroke(3));
		//g.drawLine(Start_X, Start_Y, Start_X+Width, Start_Y);
		int uxpoints[] = new int[]{Start_X,Start_X+Width+scale,Start_X+Width+scale,Start_X+Width/2,(int) (Start_X+Width/2-scale*2),Start_X};
		int uypoints[] = new int[]{Start_Y,Start_Y,Start_Y+scale,Start_Y+scale,(int) (Start_Y+scale*2),(int) (Start_Y+scale*2)};
        g.fillPolygon(uxpoints,uypoints,6);
        g.drawLine(Start_X, Start_Y, Start_X, Start_Y+Height);
        g.drawLine(Start_X, Start_Y+Height, Start_X+Width, Start_Y+Height);
        g.drawLine(Start_X+Width, Start_Y, Start_X+Width, Start_Y+Height);
        
		g.setColor(new Color(00,00,30,150));
		g.fillRect(Start_X+Width/10, Start_Y+Height/10,Width - Width/10 -scale *8 , Height/4);
		g.fillRect(Start_X+Width/10, Start_Y+Height/2,Width - Width/10 -scale *8 , Height/4);
		
		g.setColor(new Color(10,10,140,200));
		g.setStroke(new BasicStroke(2));
		g.drawRect(Start_X+Width/10, Start_Y+Height/10,Width - Width/10 -scale *8 , Height/4);
		g.drawRect(Start_X+Width/10, Start_Y+Height/2,Width - Width/10 -scale *8 , Height/4);
		
		g.setColor(ccolor);
		g.setColor(new Color(10,10,200,200));
		Font font = new Font("Arial",Font.PLAIN,20);
		g.setFont(font);
		g.drawString(User_Name, Start_X+Width/10+scale, Start_Y+Height/4);
		
		String Astr="";
		for(int i =0;i <Password.length();i++)
			Astr = Astr + '*';
		 font = new Font("Arial",Font.PLAIN,30);
		g.setFont(font);
		g.drawString(Astr, Start_X+Width/10+scale, Start_Y+Height/2+scale*7);
	}
	public  void Start_login()
	{

	//	System.out.println("starting login...");
		login_Field = true;
		Repaint();
		//login_Field = false;
	}
	public void Authorize(String User,String Pass){
		login_Field = false;
		if(User.equalsIgnoreCase ("tresk") && Pass.equals("jarvis")){
			playAudio("access");
		     WaitFor waitfor = new WaitFor();
		     Waittimer = new Timer(900,waitfor);
		     VoiceString = "authorized";
		     Waittimer.start();
		     
		     Show_wating start =new Show_wating();
		     wating = new Timer(300,start);
		     DX = 0;
		     wating.start();
		     
		    
		}else{
			playAudio("access");
		     WaitFor waitfor = new WaitFor();
		     Waittimer = new Timer(900,waitfor);
		     VoiceString = "denied";
		     Waittimer.start();
		     setnotification(" Wrong password attempt ");
		}
		User_Name = "";
		Password = "";
	}
	public void Draw_Wating(Graphics2D g)
	{
		int Start_X = scale*60;
		int Start_Y = scale*50;
		int Width = scale *90;
		int Height = scale*5;
		Color scolor = new Color(0,0,20,100);
		g.setColor(new Color(10,10,50,150));
		g.fillRect(Start_X,Start_Y, Width, Height);
		g.setColor(scolor);
		g.setStroke(new BasicStroke(6));
		g.drawRect(Start_X,Start_Y, Width, Height);
		g.setColor(new Color(200,0,20,80));
		g.setStroke(new BasicStroke(2));
		g.drawRect(Start_X-4,Start_Y-4, Width+8, Height+8);
		g.setColor(new Color(200,0,20,70));
		g.setStroke(new BasicStroke(3));
		g.drawRect(Start_X-6,Start_Y-6, Width+12, Height+12);
		int block_W = Width/50;
		int block_H = Height;
		if(PX == 0)
		  PX = Start_X;
		else if(PX == Start_X)
			PX = Start_X + scale/2;
		else if(PX == Start_X + scale/2)
			PX = Start_X+scale;
		else if(PX == Start_X + scale)
			PX = Start_X ;
		int CX = PX;
		int LX = Start_X + Width-block_W;
		
		g.setStroke(new BasicStroke(2));
		int d = 0;
		while(CX < LX)
		{
			g.setColor(new Color(0,0,100,150));
			g.fillRect(CX, Start_Y, block_W, block_H);
			g.setColor(scolor);
			g.drawRect(CX, Start_Y, block_W, block_H);
			if(d < DX){
				g.setColor(new Color(25,00,120,220));
				g.fillRect(CX, Start_Y, block_W, block_H);
			}
			CX += block_W+scale;
			d++;
		}
		DX+=2;
	}
	public void draw_sideArcLine(){
		int r = scale*65;
		getx(r);
		gety(r);
	}
	public void refreshAngles(int space,int Arc)
	{
		sangles[angleCount]= sangles[angleCount-1]+aangles[angleCount-1]+space;
		aangles[angleCount] = Arc;
		angleCount++;
		//System.out.println(angleCount+"angles : "+sangles[angleCount-1]+""+aangles[angleCount-1]);
	
	}
	public int getx(int r){
		int Width = d.width;
		Width = Width-(int)(Width/5.2);
		int x = (int)(Width/2.3);
		return x-r;
	}
	public int gety(int r){
		int Height = d.height;
		Height = Height-(int)(Height/20);
		int y = Height/2;
		return y-r;
	}
	public int[] getxy(int x,int y,int r,int startAngle,int arcAngle)
	{
		Double theta;
		
		int totalAngle =(startAngle + arcAngle);
        
		int px=0,py=0;
		
		 if(totalAngle < 0)
			 totalAngle = 360 + totalAngle ; 
       if(totalAngle == 0 ){
			return new int[]{x+r,y};
       }
    	  else if(totalAngle <= 90 ){
    	   if(totalAngle == 90)
    	    return new int[]{x,y-r};
		 	theta = (90-totalAngle)*(Math.PI/180);
			py = (int) (y -(r*( Math.cos(theta))));
			
			px =(int)( x + (r* Math.sin((theta))));
			//System.out.println(theta +" "+x + " "+y+" "+px+" "+py+" "+Math.sin(theta));
			return new int[]{px,py};
		}else if(totalAngle <= 180 ){
			if(totalAngle == 180)
				return new int[]{x-r,y};
			 theta = (totalAngle-90)*(Math.PI/180);
			
			 px = (int)(x - (r*Math.sin(theta)));
			 py = (int)(y - (r*Math.cos(theta)));
			 
			return new int[]{px,py};		 
		}else if(totalAngle <= 270){        
		 	theta = (270-totalAngle)*(Math.PI/180);
			
			px =(int)( x - (r* Math.sin((theta))));
			py = (int) (y +(r*( Math.cos(theta))));
			//System.out.println(theta +" "+x + " "+y+" "+px+" "+py+" "+Math.sin(theta));
			return new int[]{px,py};			
		}else if(totalAngle <= 360){
			
           theta =( totalAngle-270)*(Math.PI/180);
			
			px =(int)( x + (r* Math.sin((theta))));
			py = (int) (y + (r*( Math.cos(theta))));
			//System.out.println(" theta = " +theta+" "+x + " "+y+" "+px+" "+py+" "+Math.sin(theta));
			return new int[]{px,py};	
			
		}
		/* else if(totalAngle <= -270){
			
		}else if(totalAngle <= -180){
			
		}else if(totalAngle < -90){
			
		}else if(totalAngle < 0 ){
			
			}0
	 */
		return null;
	}
	public void DrawLogo(Graphics2D g){
		g.setFont( new Font(Font.SERIF,Font.BOLD,30));
		Color.getHSBColor(0.66f, 0.47F, 1.0F);
		//g.setColor(color);
		g.setColor(new Color(100,100,255,240));
		//g.drawString("T-Resk Orgnisation",400,50);
		int Start_X = scale*80 ;
		int Start_Y = scale*10;
		int Height = 30;
		int space = 2;
		int T_X = Start_X;
		int  T_Y =  Start_Y;
		int T_W = 15;
		int T_H = Height ;
		int T_XP[] = new int[]{(int) (T_X+T_W*0),(int) (T_X+T_W*1),(int) (T_X+T_W*1),(int) (T_X+T_W*0.7),(int) (T_X+T_W*0.7),(int) (T_X+T_W*0.3),(int) (T_X+T_W*0.3),(int) (T_X+T_W*0)};
		int T_YP[] = new int[]{(int)(T_Y+T_H*0.0),(int)(T_Y+T_H*0.0),(int)(T_Y+T_W*0.4),(int)(T_Y+T_W*0.4),(int)(T_Y+T_H*1),(int)(T_Y+T_H*1),(int)(T_Y+T_W*0.4),(int)(T_Y+T_W*0.4)};
	
		g.fillPolygon(T_XP,T_YP,8);
		int Dash_X = T_X+T_W+space ;
		int Dash_Y = Start_Y;
		int Dash_W = 15 ;
		int Dash_H = Height ;
		g.fillRect(Dash_X, Dash_Y+Dash_H/2-(int)((Dash_W*0.4)/2), Dash_W,(int)((Dash_W*0.4)));
		
		int R_X = Dash_X+Dash_W+space;
		int  R_Y = Start_Y ;
		int R_W = 15;
		int R_H = Height ;
		
		int R_XP[] = new int[]{R_X ,(int)(R_X+R_W*0.8),(int)(R_X+R_W*1),(int)(R_X+R_W*1),(int)(R_X+R_W*0.6),(int)(R_X+R_W*0.6),(int)(R_X+R_W*0.4),(int)(R_X+R_W*0.4),(int)(R_X+R_W*0)};
		int R_YP[] = new int[]{R_Y,(int)(R_Y+R_H*0),(int)(R_Y+R_H*0.1),(int)(R_Y+R_H*1),(int)(R_Y+R_H*1),(int)(R_Y+R_H*0.2),(int)(R_Y+R_H*0.2),(int)(R_Y+R_H*1),(int)(R_Y+R_H*1)};
		g.fillPolygon(R_XP,R_YP,9);
		int R_XP2[] = new int[]{(int)(R_X+R_W*0.4),(int)(R_X+R_W*0.6),(int)(R_X+R_W*0.6),(int)(R_X+R_W*0.4)};
		int R_YP2[] = new int[]{(int)(R_Y+R_H*0.4),(int)(R_Y+R_H*0.4),(int)(R_Y+R_H*0.6),(int)(R_Y+R_H*0.6)};
		
		g.fillPolygon(R_XP2,R_YP2,4);
		int E_X = R_X+R_W+space;
		int E_Y = Start_Y;
		int E_W = 15 ;
		int E_H = Height;
		int E_XP[] = new int[]{(int)(E_X+E_W*0.1),(int)(E_X+E_W*0.9),E_X+E_W,E_X+E_W,(int)(E_X+E_W*0.4),(int)(E_X+E_W*0.4),(int)(E_X+E_W*0.95),(int)(E_X+E_W*0.95),(int)(E_X+E_W*0.4),(int)(E_X+E_W*0.4),(int)(E_X+E_W*1),E_X+E_W,(int)(E_X+E_W*0.9),(int)(E_X+E_W*0.1),(int)(E_X+E_W*0),E_X};
		int E_YP[] = new int[]{E_Y,(int)(E_Y+E_H*0),(int)(E_Y+E_H*0.05),(int)(E_Y+E_H*0.25),(int)(E_Y+E_H*0.25),(int)(E_Y+E_H*0.4),(int)(E_Y+E_H*0.4),(int)(E_Y+E_H*0.6),(int)(E_Y+E_H*0.6),(int)(E_Y+E_H*0.75),(int)(E_Y+E_H*0.75),(int)(E_Y+E_H*0.95),(int)(E_Y+E_H*1),(int)(E_Y+E_H*1),(int)(E_Y+E_H*0.95),(int)(E_Y+E_H	*0.07)};
	    g.fillPolygon(E_XP, E_YP, 16);
		
	    int S_X = E_X + E_W + space;
		int S_Y = Start_Y;
		int S_W = 15 ;
		int S_H = Height;
		int S_XP[] = new int[]{(int)(S_X+S_W*0.2),(int)(S_X+S_W*1),(int)(S_X+S_W*1),(int)(S_X+S_W*0.5),(int)(S_X+S_W*0.5),(int)(S_X+S_W*1),(int)(S_X+S_W*1),(int)(S_X+S_W*0.8),(int)(S_X+S_W*0),(int)(S_X+S_W*0),(int)(S_X+S_W*0.5),(int)(S_X+S_W*0.5),(int)(S_X+S_W*0),(int)(S_X+S_W*0)};
		int S_YP[] = new int[]{(int)(S_Y+S_H*0),(int)(S_Y+S_H*0),(int)(S_Y+S_H*0.25),(int)(S_Y+S_H*0.25),(int)(S_Y+S_H*0.35),(int)(S_Y+S_H*0.6),(int)(S_Y+S_H*0.9),(int)(S_Y+S_H*1),(int)(S_Y+S_H*1),(int)(S_Y+S_H*0.75),(int)(S_Y+S_H*0.75),(int)(S_Y+S_H*0.65),(int)(S_Y+S_H*0.4),(int)(S_Y+S_H*0.1)};
	    g.fillPolygon(S_XP, S_YP, 14);
	  
	    BufferedImage   brt = new BufferedImage(100,50,BufferedImage.TYPE_INT_ARGB_PRE);
	  Graphics  gh = brt.getGraphics();

	  gh.setColor(Color.getHSBColor(0.627f, 0.1f, 0.5f) );
	    
	    
	 /*  double[][] SumdgeRed,SumdgeGreen,SumdgeBlue;
	   int w = brt.getWidth();
	   int h = brt.getHeight();
	   
	   
	   SumdgeRed = new double[8][8];
	   SumdgeGreen = new double[8][8];
	   SumdgeBlue = new double[8][8];
	 for(int i=0;i<7;i++)
	    	for(int j=0;j<7;j++)
	    	{
	    		int c = x+i -3;
	    		int r = y+j-3;
	    		
	    		int Curcolor = brt.getRGB(x, y);
	    		SumdgeRed[i][j] =  (Curcolor >> 16 ) & 0xFF;
	    		SumdgeGreen[i][j] =  (Curcolor >> 16 ) & 0xFF;
	    		SumdgeBlue[i][j] = (Curcolor) & 0xFF;
	    	}
	   
	    for(int i=0;i<7;i++)
	    	for(int j=0;j<7;j++)
	    	{
	    		int c = x+i -3;
	    		int r = y+j-3;
	    		
	    		int Curcolor = brt.getRGB(x, y);
	    		int Curred = (Curcolor >> 16 ) & 0xFF;
	    		int Curgreen = (Curcolor >> 8 ) & 0xFF;
	    		int Curblue= (Curcolor) & 0xFF;
	    		int newred = (int)(Curred*0.9+SumdgeRed[i][j]*0.1);
	    		int newgreen = (int)(Curgreen*0.9+SumdgeGreen[i][j]*0.1);
	    		int newblue = (int)(Curblue*0.9+SumdgeBlue[i][j]*0.1);
	    		int newcolor = (newred << 16) | (newgreen << 8)|newblue ;
	    		brt.setRGB(c, r,  newcolor);
	    		System.out.println("ccolor = "+Curcolor+" new color = "+newcolor);
	    		SumdgeRed[i][j] = Curred*0.3 + SumdgeRed[i][j]*0.7;
	    		SumdgeGreen[i][j] = Curgreen*0.3 + SumdgeGreen[i][j]*0.7;
	    		SumdgeBlue[i][j] = Curblue*0.3 + SumdgeBlue[i][j]*0.7; 
	    	}
	    */
	    g.drawImage(brt, 600, 50, null);
	    angleCount= 0;
	    
	}

	// } 
	public static void main(String[] args)
	{
	  panel = new JFrame("Graphics");
		
	 GSkeleton gs = new GSkeleton();
	 content = new JPanel();
	 content.setLayout(new BorderLayout());
	 
	// content.addKeyListener(listener);
	 content.add(gs,BorderLayout.CENTER);
	// GSkeleton.Gskeleton.MouseHandler handler = new GSkeleton.Gskeleton.MouseHandler();
	// content.addMouseListener();

	 panel.setContentPane(content);
	// panel.setBackground(new Color(5,5,15));
	 panel.setSize(d);
	 panel.setLocation(l);
	 panel.setVisible(true);
	 panel.setExtendedState(Frame.MAXIMIZED_BOTH);
	  
	
	 int prc = Runtime.getRuntime().availableProcessors();
	 
	 System.out.println("avilable processors are "+prc);  
	 
	 Background bg =new Background(); 
	  t = new Timer(1700,bg);
	//  t.start();
     //playAudio("denied");
     
	}
	private  class RepaintAction implements ActionListener{

		int Counter = 1;
		boolean inc = true ;
		boolean dec = false;
		int soundcnt=-1;
		int soundl=5;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int Width =  d.width;
			Width = Width-(int)(Width/5.2);
			//System.out.println("timer is started");
			if(soundcnt<0)
				playAudio("hw_spin");
			else if(soundcnt>soundl)
			{
				playAudio("hw_spin");
				soundcnt=0;
			}
			soundcnt++;
			
			if(dec)
			 if(Counter == 1){
				timer.stop();
				Arm[0] = false;
				Arm[1] = false;
			  }else{
				  Counter--;
				  if(Arm[0])
				  lstartAngle-=2;
				  else if(Arm[1])
					  rstartAngle+=2;
				  Repaint();
			  }
			 if(inc){
				 if(Counter < 20)
				 {
					 Counter++;
					 if(Arm[0])
					 lstartAngle+=2;
					 else if(Arm[1])
						  rstartAngle-=2;
					 Repaint();
				 }else {
					 inc=false;
					 dec = true;
				 }
			}
		}
		
	}
	
	private class PullArmAction  implements ActionListener {

		double rate = 0.0;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(PullArm){
		
				if(rate  > 1.1){
					//PullArm = false;
					repainting = false;
					timer.stop();
				}else{
				lscale =(int)(scale*rate);
				rate = rate+0.1;
				Repaint();
				}
			}
		}
		
	}
	private class Show_wating implements ActionListener{

		int Count = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
           show_wating = true;
			if(Count > 20){
				
		
				wating.stop();
				show_wating = false;
				Repaint();
				 Enviornment env = new Enviornment();
				 Enviornment.New_Frame=true;
			     env.OpenEnviornment();
			}else {
				
				repaint();
			}
			
			Count++;
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		playAudio("buttonclick");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//playAudio("buttonclick");
		//System.out.println("x = "+e.getX()+"y = "+e.getY());
		int Width = d.width;
		int Height = d.height;
		Width = Width-(int)(Width/5.2);
		Height = Height-(int)(Height/20);
		int x = e.getX() ;
		int y = e.getY();
		int cx = (int)(Width/2.3);
		int cy = Height/2;
	//	System.out.println("x = "+e.getX()+"y = "+e.getY()+"cx = "+cx+"cy = "+cy);
		
		if((x > cx - scale*7) && (x < cx+ scale*7) && (y > cy - scale*7) && (y < cy + scale*7)){
			PullArmAction action = new PullArmAction();
			
		    timer = new Timer(80,action);
		    
		    repainting = true;
		    if(PullArm){
		     PullArm = false;
		     Repaint();
		    }else{
		    PullArm =  true; 
		    //Arm[0] = true;
		   
			timer.start();
			PullArm = true;
		    }
					}else 	if((x > cx-scale*12 )&& (x < cx+scale*12 ) && ( y > cy-scale*12) && (y < cy+scale*12) )
		{
						RepaintAction action = new RepaintAction();
						
					    timer = new Timer(150,action);
					    Arm[0] = true;
					   
						timer.start();

			}else
	            	if((x > cx+scale*10 )&& (x < cx+scale*20 ) && ( y > cy-scale*25) && (y < cy+scale*15) ){
			RepaintAction action = new RepaintAction();
			System.out.println("right is selected");
			
		    timer = new Timer(100,action);
		    Arm[1] = true;
			timer.start();
	   }else if(x > scale*100 && x < scale*140 && y > scale*95 && y < scale*100 )	
	   {
		   Search_field = true;
		   
		   //setnotification("Not connected to internet ...");  
	   }
	
	/*
		setnotification("Processres are Working well ."); */
		}

private static class WaitFor implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
       playAudio(VoiceString);
       Waittimer.stop();
	}
	
}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//playAudio("buttonclickrelease");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		playAudio("buttonrollover");
	}
	
	private static class Background implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			playBackground("wind1");
		}
		
	}
	
	public static class apt extends JApplet{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void init(){
			GSkeleton content = new GSkeleton();
			setContentPane(content);
 
		}
	}
	private static void playBackground(String audionm){
		AudioClip audio;
		 ClassLoader cl = GSkeleton.class.getClassLoader();
		 URL resource = cl.getResource(audionm+".wav");
		 if(resource!=null)
		 { 
			// System.out.println("called successfully");
		     audio = JApplet.newAudioClip(resource);
		     audio.play();
		     
		 }else
			 System.out.println("error while playing.");
	
	}
	
	private static void playAudio(String audionm)
	{
		AudioClip audio;
		 ClassLoader cl = GSkeleton.class.getClassLoader();
		 URL resource = cl.getResource(audionm+".wav");
		 if(resource!=null)
		 { 
			// System.out.println("called successfully");
		     audio = JApplet.newAudioClip(resource);
		     audio.play();
		     
		 }else
			 System.out.println("error  while playing.");
	}
	public void Analyze(String string)
	{
		int ispace = 0;
		int count = 0;
		while( string.indexOf(" ",ispace+1) > 0 ){
			//System.out.println("ispace = "+ispace+" "+ string.substring(ispace, string.indexOf(" ",ispace+1)));
			Time[count] = string.substring(ispace, string.indexOf(" ",ispace+1));
	        ispace =  string.indexOf(" ",ispace+1);
		    count++;
		}
		Time[count] = string.substring(ispace,string.length());
		return;
	}
	public void setnotification(String notification){
	
		if(NoteCount < 100 ){ 
		Notes[NoteCount] = notification;
		NoteCount++;
		 }
		else{
			for (int i = 0;i < 10;i++)
				Notes[i] = "";
			NoteCount = 0;
			Notes[NoteCount] = notification; 
		}
		New_Note = true;
		repaint();
		
	}
	public void Draw_Notes()
	{
		
		int fx = scale*180;
		int fy =scale*60;
		int fwidth = scale*25;
		int length ;
		String note ;
		int x = fx;
		int y = fy + scale*6;
		//System.out.println("in note = lheight = "+lheight+" " +Lines.length);
		g2d.setFont(new Font(Font.DIALOG_INPUT,Font.ITALIC,15 ));
		g2d.setColor(new Color(100,100,255,240));
		int in;
		//note recongnisation code block
		
		while(!setLines(22,fwidth));//(y+fheight)/lheight,fwidth));
		   
		//System.out.println("passed");
		
		for(int i = 0;i< Lines.length;i++)
	{
		in = 0;
		note = Lines[i];
		length = note.length();
		x = fx;
		y +=scale*1.8;
		while(in < length)
		{
	
			
		  g2d.drawString(""+note.charAt(in), x, y);
			x+=scale*1.5; 
		 in++;
		 }
	   }
	  //  New_Note = false;
	}
	public boolean setLines(int num_lines,int line_width){
		
		 Lines = new String[num_lines];
		 int lc = 0;
		 String note;
		 int length;
		 int in = 0;
		 int x = 0;
		 int lm = 0;
		 boolean set = false;
		 //erasing all Lines array
			for(int i = 0; i <Lines.length;i++ )
			   Lines[i]="";
			for(int i = 0;i < NoteCount;i++)
			{
				in = 0;
				note = 	Notes[i];
				length = note.length();
				x = 0;
				while(in < length)
				{
					if(x <  line_width)
					{
						x+=scale*1.5;
					}else {
						x = 0;
						if(lm < Line_Mask)
						{
						lm++;	
						}else
						 lc++;
						if(lc >=num_lines )
						{
							Line_Mask ++;
							return false;
						}
						
					}
					if(lm >= Line_Mask )
					Lines[lc] = Lines[lc]+ note.charAt(in);
					//System.out.println(" lc = "+lc +" "+Lines[lc]);
				in++;
				}
				if(lm < Line_Mask)
				{
				lm++;	
				}else
			    lc+=2;
				if(lc >=num_lines )
				{
					Line_Mask +=2;
					return false;
				}
			set = true;
			}
			
		return set;
	}
	public void drawSearchField(Graphics2D g)
	{
		int x = scale*100;
		int y = scale*90;
		int fwidth = scale*40;
		int fheight = scale*12;
		g.setColor(new Color(0,0,100,100));
		g.fillRect(x,y,fwidth,fheight);
		g.setColor(new Color(0,0,255,255));
	    g.fillRect(x,y,fwidth/10,3);
	    g.setColor(new Color(10,10,200,200));
	    g.fillRect(x, y-scale/2,4, fheight);
	   // g.fillRect(x,y+fheight-2*scale,fwidth/4,2);
	   // g.fillRect(x,y+fheight-scale,fwidth/4-scale,2);
	    setstroke(2);
	    g.drawLine(x+scale, y+fheight-scale, x+fwidth/4, y+fheight-scale);
	    g.drawLine(x, y+fheight, x+fwidth/4-scale, y+fheight);
	    g.drawLine(x+fwidth/4+2*scale, y+fheight,x+fwidth,y+fheight);
	    g.drawLine(x+fwidth/4+scale, y+fheight+scale,x+fwidth,y+fheight+scale);
	    g.drawLine( x+fwidth/4, y+fheight-scale,x+fwidth/4+2*scale, y+fheight);
	    g.drawLine( x+fwidth/4-scale, y+fheight,x+fwidth/4+scale, y+fheight+scale);
	    g.setColor(new Color(10,10,200,150));
	    setstroke(6);
	    g.drawLine(x+scale,y,x+fwidth/2,y);
	    
	    g.setColor(new Color(0,0,100,255));
	    g.setStroke(new BasicStroke(2.0f));
	    g.drawLine(x+scale*2,y-scale,x+scale*2,y+fheight/2+scale*2);

	    
	    setstroke(4);
	    g.setColor(new Color(0,0,140,200));
	    g.drawLine(x+fwidth/2,y+scale,x+fwidth-scale,y+scale);
	    g.setStroke(new BasicStroke(2));
	    g.setColor(new Color(0,0,255,255));
	    g.drawLine(x+fwidth,y,x+fwidth,y+fheight+scale);
	    g.setStroke(new BasicStroke(3));
	    g.drawLine(x+fwidth/4 +2*scale,y,x+fwidth/2+scale*3,y);
	    g.setStroke(new BasicStroke(4));
	    g.drawLine(x+fwidth/2+scale*3, y+scale/2,x+fwidth-scale*3,y+scale/2);
	    g.setColor(new Color(10,0,60,200));
	    g.fillRect(x+scale*4, (int)(y-scale*1.1),scale*5,scale/2);
	    g.setStroke(new BasicStroke(1));
	    g.setColor(new Color(00,00,100,255));
	    g.drawRect(x+scale*4, (int)(y-scale*1.1),scale*5,scale/2);
	    g.setStroke(new BasicStroke(3));
	    g.setColor(new Color(10,10,180,100));
	    int d = scale*7;
	    g.drawArc((x+fwidth-scale)-d/2, (y+scale)-d/2,d,d,0,360);
	    g.setColor(new Color(10,10,200,150));
	    d = scale*6;
	    g.drawArc((x+fwidth-scale)-d/2, (y+scale)-d/2,d,d,0,360);
	    g.setColor(new Color(10,10,240,200));
	    d = scale*4;
	    g.setStroke(new BasicStroke(4));
	    g.drawArc((x+fwidth-scale)-d/2, (y+scale)-d/2,d,d,0,360);

	    g.setColor(new Color(0,0,255,255));
	    d =(int)( scale*5.5);
	    g.drawArc((x+fwidth-scale)-d/2, (y+scale)-d/2,d,d,0,80);
	    
	    int e = (int)(scale*2);
	    int px[] = new int[]{x-e/2,x+e/2,x-e/2};
	    int py[] = new int[]{y+fheight/2,y+fheight/2+e,y+fheight/2+e*2};
	    
	    g.fillPolygon(px,py,3);
	    e = (int)(scale*3.5);
	    int px2[] = new int[]{x+fwidth/2,x+fwidth/2+e/2,x+fwidth/2+e};
	    int py2[] = new int[]{y+fheight+scale,y+fheight-scale,y+fheight+scale};
	    
	    g.fillPolygon(px2,py2,3);
	    
	    setstroke(2);
	    g.drawLine(x-scale,y+fheight/2-scale,x+scale*7,y+fheight/2-scale);

	    g.setColor(new Color(0,0,200,140));
	    g.setStroke(new BasicStroke(6));
	    g.drawLine(x+fwidth/2,y+fheight/2-scale,x+fwidth-scale*4,y+fheight/2-scale);
	    g.setColor(new Color(0,0,255,255));
	    g.setStroke(new BasicStroke(4));
	    g.drawLine(x+scale*8,y+fheight/2-scale/2,x+scale*14,y+fheight/2-scale/2);
	    g.setStroke(new BasicStroke(3));
	    g.drawLine(x+fwidth/2,y+fheight/2-scale/2,x+fwidth+scale,y+fheight/2-scale/2);
	    
	    g.setStroke(new BasicStroke(3));
	    g.drawLine(x+fwidth/2-scale*6,y+fheight/2-scale,x+fwidth/2+scale,y+fheight/2-scale);
	    g.setStroke(new BasicStroke(5));
	    g.drawLine(x+scale*4,y+fheight/2-scale,x+scale*5,y+fheight/2-scale);
	  
	    g.setFont(new Font("Arial",Font.BOLD,20));
	    g.drawString("Search :", x+scale*4, y+scale*4);
	    g.setFont(new Font("Arial",Font.PLAIN,18));
	    
	   /* for(int i = 0;i < search_text.length();i++)
	    g.drawString(""+search_text.charAt(i), x+scale*2+i*scale*2, y+fheight-scale*3);
	  */
	    g.drawString(search_text, x+scale*2, y+fheight-scale*3);
	    g.setStroke(new BasicStroke(1));
	    if(Search_field)
	    g.drawLine(x+scale*2+(int)((search_text.length())*scale*1.5), y+fheight-scale,x+scale*2+(int)((search_text.length())*scale*1.5),y+fheight/2 );

	}
	public void setcolor(int r,int g,int b,int v)
	{
	g2d.setColor(new Color(r,g,b,v));	
	}
	public void setstroke(int s)
	{
		g2d.setStroke(new BasicStroke(s));
	}
	public static void audio()
	{
	}
}
