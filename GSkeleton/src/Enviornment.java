import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Enviornment extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean New_Frame;
	static 	JFrame panel= new JFrame();
	static Graphics2D g2d;
	static BufferedImage OSC;
	static Image img;
	int Width,Height;
	static boolean Print = false;
	static String Print_Text="";
	static String Print_lines[]=new String[100];
	static int print_line_count=0;
	static int reffer_count=0;
	static Color Text_Color;
	static int X = 10;
	static int Y = 20;
	static boolean get_value = false;
	static String cmd_line = "";
	static String data;
	static String words[];
	//static int wordcount;
	//load vars
	static String mode;
	static Scanner loaded;
	public static int skip=0;
	public static int line=0;
	public static String instructions[][]=new String[500][2];
	public static int noinstr=0;
	public static String loop_instructs[]=new String[100];
	public static int inscount=0;
	public static String sub_nm[]=new String[100];
	public static int[][] sub_pos=new int[100][100];
	public static int sub_no=0;
	
	
	static String command[] = new String[2];
	static String variables[][]=new String[100][2];
	static int varno = 0;
	static boolean varflag = true;
	static String var_val="";
	public Enviornment(){
		Color bgColor = new Color(10,0,10);
		setBackground(bgColor);
		keylistener listener = new keylistener();
		panel.addKeyListener(listener);
	}
	public void createOSC()
	{
		 Width = getWidth();
		 Height = getHeight();
		Width = Width-(int)(Width/5.2);
		Height = Height-(int)(Height/20);
		//int scale = Width/200;
OSC = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB_PRE);
Graphics gh = OSC.getGraphics();

ClassLoader cl = Enviornment.class.getClassLoader();
URL url = cl.getResource("jarvis.png");
  img = Toolkit.getDefaultToolkit().createImage(url);

MediaTracker tracker = new MediaTracker(this);
tracker.addImage(img, 0);
try {
tracker.waitForAll();
} catch(InterruptedException ie) {
	ie.printStackTrace();
	System.out.print("exception");
}



//
gh.setColor(new Color(5,5,20));
gh.fill3DRect(0, 0,OSC.getWidth() ,OSC.getHeight(),true);
gh.setColor(new Color(00,00,100,100));
gh.drawImage(img,0,0,OSC.getWidth() ,OSC.getHeight(),null);
gh.fillRect(0,0,OSC.getWidth() ,OSC.getHeight());
//gh.fillArc((getWidth()/2)-100, (getHeight()/2)-100,200,200,0,360);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		renderHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	  
		g2d = (Graphics2D)g;
		g2d.setRenderingHints(renderHints);
		if(OSC == null)
			createOSC();
		g.drawImage(OSC,0,0,getWidth(),getHeight(),null);
		g2d.setColor(new Color(0,0,100,200));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(new Color(0,0,100,150));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		//if(Print)
		 print();
		
	}
	public void print(){
		Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
		g2d.setFont(font);
		g2d.setColor(Text_Color);
		X = 0;
		Y = 10;
		Load_Lines();
		while(!setLines(30));
		int i = reffer_count;
		/*while(i < Print_Text.length())
		{
			char  ch =Print_Text.charAt(i);
			//System.out.println("text = "+ch);
			if(ch == '\n')
			 {
					Y = Y+20;
					X = 0;
					
				
			 }else{
				
			     g2d.drawString(""+ch,X,Y);
				 X = X+getx(ch);
				
			 }
			i++; 
		}*/

		while(i<=print_line_count)
		{
			g2d.drawString(Print_lines[i], X, Y);
			Y=Y+20;
			i++;
		}
		Print = false;
	}
	public void Load_Lines()
	{
		int i=0;
		print_line_count=0;
		//System.out.println("\nIn load_lines() ");
		Print_lines[0]="";
		while(i<Print_Text.length())
		{
			char ch=Print_Text.charAt(i);
			if(ch=='\n')
			{
				//System.out.println(print_line_count+" "+Print_lines[print_line_count]);
				print_line_count++;
				Print_lines[print_line_count]="";
			}else
			{
				Print_lines[print_line_count]+=ch;
			}
			i++;
		}
	}
	public boolean setLines(int num_lines)
	{
		if(print_line_count > num_lines+reffer_count)
		{
			reffer_count++;
			return false;
		}
		return true;
	}
	public int getx(char ch){
		
		switch(ch)
		{
		case 'i': return 5;
		case 'j' : return 5;
		case 'l' : return 5;
		case 'm': return 14;
		case 't': return 7;
		case 'w': return 14;
		case 'O' : return 14;
		case 'r' : return 7;
		case 'I' : return 5;
		
		}
		return 9;
		
	}
	static  void main(String args[]){
	/*	Enviornment env = new Enviornment();
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(env,BorderLayout.CENTER);
		
        panel = new JFrame("Jarvis Enviornment");
		panel.setContentPane(content);
		panel.setSize(1000,600);
		panel.setLocation(0,0);
		panel.setExtendedState(panel.MAXIMIZED_BOTH);
		panel.setVisible(true);
		*/
	}
	public  void OpenEnviornment(){
	
		Print_Text="";
	     varno=0;
	   panel = new JFrame("Jarvis Enviornment");
		
		Enviornment env = new Enviornment();
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(env,BorderLayout.CENTER);
		
	
		panel.setContentPane(content);
		panel.setSize(1000,600);
		panel.setLocation(0,0);
		panel.setExtendedState(Frame.MAXIMIZED_BOTH);
		panel.setVisible(true);
		
		
		drawLogo();
		start();
		return;
	}
	public void start(){
       // print("\n Enter your command below : ");    
        print("\n---: ");
      
		return;
	}
	public void print(String string ){
		//Text_Color = new Color(0,0,255,255);
		
		Print_Text += string;
		Print = true;
		repaint();
		//repaint(X,Y-10,X+200,Y+10);
		
	}
	
	public void scan(){
		
		
	}
	public void new_env()
	{
		Clear_vars();
		Print_Text="";
		reffer_count=0;
		drawLogo();
	}
	 public  void Clear_vars()
	  {
		  varno=0;
	  }
	public void drawLogo()
	{
	 Text_Color = new Color(0,0,255,200);	
		print("\n######################################################");
		print("\n#  T-resk Organisation                                                                      #");
		print("\n#  jarvis 1 st Intigration                                                                     #");
		print("\n#                                                                                                    #");
		print("\n######################################################");
	}
	public class keylistener implements KeyListener{
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char ch = e.getKeyChar();
		if(get_value)
		{
			if(ch=='\n')
			{
				//print("var_val = "+var_val);
				set_value(solve(words[1]),solve(var_val));
				get_value=false;
				
				cmd_line = "";
				if(mode.equals("loaded"))
					run();
				else
				    start();
			}
			else
			{
				if(ch == (char)KeyEvent.VK_BACK_SPACE)
				 {
					 if(var_val.length() > 0)
					 {
					 var_val = var_val.substring(0,var_val.length()-1);
				      Print_Text = Print_Text.substring(0,Print_Text.length()-1);	
				     repaint();
					 }
				 }else
				    {
			           var_val=var_val+ch;
			           print(""+ch);
					 }
			}
		}else if(ch == '\n'){
			/*if(get_value)
			  {
				  val=cmd_line;
				  print("val ="+val);
				  print("words[1] = "+words[1]);
				  set_value(solve(words[1]),solve(val));
				  cmd_line = "";
				  get_value = false;
			  }
		      else
			  { */
			     analyze(cmd_line);
			     cmd_line = "";
			     if(!get_value)
			        start();
			  //}
		 }else if(ch == (char)KeyEvent.VK_BACK_SPACE)
		 {
			 if(cmd_line.length() > 0)
			 {
			 cmd_line = cmd_line.substring(0,cmd_line.length()-1);
		      Print_Text = Print_Text.substring(0,Print_Text.length()-1);	
		     repaint();
			 }
		 } else
		 {
		    cmd_line +=ch;
			 print(""+ch);
		 }
		
	}
	public void analyze(String cmd_line) {
		// TODO Auto-generated method stub
		/*String cmd = cmd_line ; //.substring(0,cmd_line.length()-1);
		// System.out.println("cmd = " +cmd);
		if(cmd.equalsIgnoreCase("exit"))
		{
		   System.exit(200);
		   
		}else if(cmd.equalsIgnoreCase("new"))
			drawLogo();
		else if(cmd.startsWith("$"))
		{
		
		}
		else
		{
			if(Analyze(cmd));
			 switch(command[0])
			 {
			 
			 }
		}
		*/
		
		int i=0,pos=0,space=0;
		int wordcount=0;
		data=cmd_line;
		//System.out.print("\nanalyzig.....");
		do
		{

			wordcount ++;
			//print("\nwordcount = "+ wordcount );
			pos=data.indexOf(" ",pos+1);

		}while(pos>0);

		words=new String[wordcount];

	//	print("\nwordcount = "+wordcount);
		pos=0;
		while(true)
		{

			space=data.indexOf(" ",space+1);
			//System.out.println("space = "+space);
			if(space>0)
			{
				words[i]=data.substring(pos,space);
				// System.out.println(i +" " +words[i]);
				i++;
				pos=space+1;
				//System.out.println("pos = "+pos);
			}else
			{
				
				words[i]=data.substring(pos,data.length());
				break;
			}
		}
		//System.out.print("\nComplete .");
		exicute();
		return;

	}
	public void exicute()
	{
		 // System.out.print("\nexicuting..."+words[0]);
		  //print(" word = "+words[0]);
		  
		 if(words[0].startsWith("//"))
		  {
			  line++;
			  return;
		  }
			
		  //code for checking subroutine .
		  if(words[0].startsWith("[")&&words[0].endsWith("]"))
			{
				String sub=words[0].substring(1,words[0].length()-1);
				if(sub.contains("$")||sub.contains("+")||sub.contains("-")||sub.contains("*")||sub.contains("/")||sub.contains("%")){
					//System.out.println("in if sub is "+sub);
					exicute_sub(solve(sub));
					return;
				}else
				exicute_sub(sub);
				return;
			}
		  else  if(words[0].contains("$"))
		  {
			   solve(data);
			  varflag=false;
		  }
		  
		  
	  if(varflag)
	   switch(words[0])
	   {
		   case "new":new_env();
		          	break;
		   case "load":lexical_analyzer(data) ;
		            break;
		   case "exit":System.exit(0);
		  			break;
		   case "write":write(data);
		   			break;
		/*   case "print"://System.out.println("printing...");
			         analyzer.print();
		  			break; */
		   case "get":get();
		 			break;
		   case "FOR":if(mode.equals("shell")){}
		    		  else for_loop();
			 		break; 
		   case "IF":
			   if(mode.equals("loaded"))
			        control_if();
		   			break;  
		   default :print("\n Invalid instruction"+words[0]);
		  			return;
	   }
	varflag = true;
	}
	public boolean Analyze(String cmd)
	{
		if(cmd.indexOf(" ") < 0)
			return false;
			//print("\n Please enter arguments ");
		else
		{
			command[0] = cmd.substring(0,cmd.indexOf(" "));
			command[1] = cmd.substring(cmd.indexOf(" ")+1, cmd.length());
		}
		return true;
	}
	public void write(String input)
	{
		char Char;
		int pos=0;
		int next=0;
		//System.out.println("writing...");
		for(int i=6;i<input.length();i++)
	    {
		   Char=input.charAt(i);
			if(Character.compare(Char,'+')==0)
			{
				//System.out. println("plus encouterd at "+i);
				pos=i+1;
				if(input.indexOf("+",pos)<0) next=input.length();
				else next=input.indexOf("+",pos);
		          //print( "\n solving"+input.substring(pos,next));
				print(solve(input.substring(pos,next)));
				i=next;
			}else if(Character.compare(Char,'\\')==0)
			{
				i++;
				switch(input.charAt(i))
				{
					case 't':print("\t");
						break;
					case 'n':print("\n");
						break;
					case '+':print("+");
					    break;
				    default :print("\\");
						i--;
				}
			}else
				print(""+input.charAt(i));
	    }

	}
	public void load(){
		
	}
	
	public void lexical_analyzer(String line)
	  {
		  mode="load";
		  String instr;
		  if(words.length<2)
		  { print("\nerror: give path after load");
			  return;}
		  String path="E:\\tresk\\"+words[1];
		  //System.out.print("\n Path is "+path);
		  
		  // This will create file pointer
		  try{
			  loaded=new Scanner(new File(path));
		  }catch(FileNotFoundException e)
		  {
			  print("\n error : "+e);
			  return;
		  }
		  mode="loaded";
		  //System.out.println("counting...");
		  int i=0;
		  noinstr=0;
		  while(loaded.hasNextLine()) // load instrunctions in array
		  {
			   instr=loaded.nextLine();
			  instructions[noinstr][1]=instr;
			  //System.out.println("instruction is "+instructions[noinstr][1]);
			  noinstr++;
		  }
			  //System.out.println("lines are "+noinstr);

		  i=0;
		 while(i<noinstr) // determine functions and seprete it.
		 {
			// System.out.println("in loop i is "+i);
			instr=instructions[i][1];
			if(instr.startsWith("SUB"))
			{
			   sub_nm[sub_no]=solve( instr.substring(4,instr.length()));
			   //System.out.println("in subnm is "+sub_nm[sub_no]);
			   sub_pos[sub_no][0]=i+1;
			}else if(instr.startsWith("END SUB"))
			{
				//System.out.println("in end");
				sub_pos[sub_no][1]=i-1;
				sub_no++;
			}
			i++;
		 }
		 //System.out.println("\n compiling completed.");
		 loaded.close();
		 run();
		 
		  return;
		  
	  }
	
	  public void run()
	  {
		//int i=0;
		boolean flag=false;
		
		//line=0;
		boolean complete=false;
		while(line<noinstr)
		{
			
			String instr=instructions[line][1];
			
			if(instr.startsWith("SUB"))
			{
				flag=true;
				while(flag)
				{
					//System.out.println("skipping liens i is "+i);
					line++;
					instr=instructions[line][1];
					if(instr.startsWith("END SUB"))
					{
						//System.out.println("end encountered i is "+i);
						flag=false;
						line++;
						
					}
						
				}
			}else{
				//System.out.println(" line is "+line+" analyzing "+instr);
			    if(get_value)
			    {
			    	complete=false;
			    	break;
			    }
			    analyze(instr);
			line++;
			//complete=true;
			}
			complete=true;
		}
		if(complete)
		{
	        print ("\nExicution complited successfully .");
		    mode="shell";
		    line=0;
		    Clear_vars();
		    noinstr=0;
		    start();
		}
	  return;
	  }

	public void get()
	{
		if(words.length>1) 
		{
			get_value = true;
			//String s=((Scanner)new Scanner(System.in)).nextLine();
			 //set_value(solve(words[1]),solve(val));
			}else{
				print("error: enter var name");
		}
			return;
	}

	public String solve(String term)
	{
		String p1=words[0];
		String p2=null;
        data=term;
		int ip1=data.indexOf("=");
		if(ip1>0)
		{
		p1=data.substring(0,ip1);
		//System.out.println(p1);
		p2=data.substring(ip1+1,data.length());
		//System.out.println(p2);
			if((data.indexOf("<")>0)||(data.indexOf(">")>0)||(data.indexOf("<=")>0)||(data.indexOf(">=")>0)||(data.indexOf("==")>0))
			{
				set_value(p1,check_con(p2));
			}else
        if((data.indexOf("+")>0)||(data.indexOf("-")>0)||(data.indexOf("*")>0)||(data.indexOf("/")>0)||(data.indexOf("%")>0))
		{
			set_value(p1,solve(p2));
		}else if(p2.indexOf("$")>=0)
		{
			set_value(p1,get_value(p2));
		}else
		{
			set_value(p1,p2);
		}
		}
		else if((data.indexOf("+")>0)||(data.indexOf("-")>0)||(data.indexOf("*")>0)||(data.indexOf("/")>0)||(data.indexOf("%")>0))
		{
			//System.out.println("in solve if term is "+term);
			return  (solve_rel(term)).toString();
		}else if(data.indexOf("$")>=0){
			return get_value(term);
		}else
		{
			return term;
		}
		return null;
		
	}
	
	public void set_value(String var, String value) {
		// TODO Auto-generated method stub
		boolean found=false;
		for(int i=0;i<varno;i++)
		{
			if(variables[i][0].equals(var))
			{
				variables[i][1]=value;
				//System.out.println("updated"+variables[i][1]);
				found=true;
			}
		}
		if(!found)
		{ varno++;
			variables[varno-1][0]=var;
			variables[varno-1][1]=value;
			//print("\nadded "+varno+variables[varno-1][1]);
		}	
	}
	public String get_value(String var) {
		// TODO Auto-generated method stub
		boolean f=false;
	//	print("\n in get value");
		
		for(int i=0;i<varno;i++)
		{
			if(variables[i][0].equals(var))
			{
	           f=true;
			 return variables[i][1];
			}
		}
		if(!f){
			if(get_value)
				return var;
			else
		        print("\n error: variable "+var+" not found .");
		}
		
		return null;
	}
	public Double solve_rel(String term) {
		// TODO Auto-generated method stub
		if(term.indexOf("+")>0)
		{
			int iop=term.indexOf("+");
			return Double.parseDouble(solve(term.substring(0,iop)))+Double.parseDouble(solve(term.substring(iop+1,term.length())));
		}else
		if(term.indexOf("-")>0)
		{
			int iop=term.indexOf("-");
			return Double.parseDouble(solve(term.substring(0,iop)))-Double.parseDouble(solve(term.substring(iop+1,term.length())));
		}else
		if(term.indexOf("%")>0)
		{
			int iop=term.indexOf("%");
			return Double.parseDouble(solve(term.substring(0,iop)))%Double.parseDouble(solve(term.substring(iop+1,term.length())));
		}else
		if(term.indexOf("*")>0)
		{
			int iop=term.indexOf("*");
			return Double.parseDouble(solve(term.substring(0,iop)))*Double.parseDouble(solve(term.substring(iop+1,term.length())));
		}else
		if(term.indexOf("/")>0)
		{
			int iop=term.indexOf("/");
			return Double.parseDouble(solve(term.substring(0,iop)))/Double.parseDouble(solve(term.substring(iop+1,term.length())));
		}
		
		return null;
	}

	public String check_con(String term) {
		// TODO Auto-generated method stub
		if(term.indexOf("<")>0&&term.indexOf("=")>0)
		  {
			  int iop=term.indexOf("<");
			  int p1=(int)Double.parseDouble(solve( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble( solve(term.substring(iop+2,term.length())));
			  if(p1<=p2) return "true";
			  else return "false";}else
		  if(term.indexOf(">")>0&&term.indexOf("=")>0)
		  {
			  int iop=term.indexOf(">");
			  int p1=(int)Double.parseDouble(solve( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble( solve(term.substring(iop+2,term.length())));
			  if(p1>=p2) return "true";
			  else return "false";}else
		  if(term.indexOf("=")!=term.lastIndexOf("="))
		  {
			  int iop=term.indexOf("=");
			  int p1=(int)Double.parseDouble(solve( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble( solve(term.substring(iop+2,term.length())));
			  if(p1==p2) return "true";
			  else return "false";}else
		  if(term.indexOf("<")>0)
		  {
			  int iop=term.indexOf("<");
			 int p1=(int)Double.parseDouble(solve( term.substring(0,iop)));
			 int p2=(int)Double.parseDouble( solve(term.substring(iop+1,term.length())));
			 if(p1<p2) return "true";
			 else return "false";}else
		  if(term.indexOf(">")>0)
		  {
			  int iop=term.indexOf(">");
			  int p1=(int)Double.parseDouble(solve( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble( solve(term.substring(iop+1,term.length())));
			  if(p1>p2) return "true";
			  else return "false";}

			  else return "false";		  
	}
	/*********************
	 * code for IF Block
	 **************************************/
	  public void  control_if()
	  {
		  String input;
		  if(words.length<2)
		  {
			  print("error : syntax error in if structure");
			  return;
		  }
		  String condition=words[1];
		  //System.out.println("in if checking..."+words[1]);
		  while(true)
		  {
			  line++;
		  input=instructions[line][1];
		  if(input.equals("END IF"))
		  {
			  //print("\nEnd encountered");
			  break;
		  }
		  if(check_con(condition).equals("true"))
		  {
		       //System.out.println("rel is true going to analyze "+input);
			   analyze(input);
		  }else continue;
		 }
		 return;
	  }
	/********************** IF END ********************************************/
	  /*******************
	   * code for FOR Loop 
	   
	   ****************************/
	  public void start_for(String condition,String increment)
		{
			if(check_con(condition).equals("true"))
			{
				for(int i=0;i<inscount;i++)
					analyze(loop_instructs[i]);
				solve(increment);
				start_for(condition,increment);
			}else return;
		}
	  public void for_loop()
	  {
		  if(words.length<4)
		  {
			  System.out.println("error :syntax error in loop structuter");
			  return;
		  }
		  String initial=words[1];
		  String condition=words[2];
		  String increment=words[3];
	    
		  String input="";
		  while(true)
		  {
			  line++;
			  input=instructions[line][1];
			  if(input.equals("END FOR"))
				  break;
			  loop_instructs[inscount]=input;
			  inscount++;
		  }
		  solve(initial);
		  start_for(condition,increment);
		  inscount=0;
		  return;
	  }
	  /************************  FOR END ******************************************/
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*********************************************** 
	        Function for exicuting sub .
	 ****************************************************/
	
	  public String exicute_sub(String name)
	  {
		  int temp=line;
		  int i=0;
		  int start=0;
		  int end=0;
		  boolean flag=false;
		  while(i<sub_no)
		  {
			  String subnm=sub_nm[i];
			  if(name.equals(subnm))
			  {
				   start=sub_pos[i][0];
				   end=sub_pos[i][1];
				   flag=true;
			  }
			  i++;
		  }
		  if(!flag)
		  {
			  print("Function "+name+" not found ");
			  return null;
		  }
		  if(flag)
		  {
			 // System.out.println("exicuting...");
			  line=start;
			  while(line<=end)
			  {
				  String instr=instructions[line][1];
				  if(instr.startsWith("return"))
				  {
					  //System.out.println("in return solving "+instr.substring(7,instr.length()));
					  line=temp;
					  return solve(instr.substring(7,instr.length()));
				  }else{
				  analyze(instr);
				  line++;
				  }
			  }
		  }
		  line=temp;
		  return null;
	  }
 /*********************** EXICUTE_SUB ENDS Here *********************************/
	//  Code for clear variables

	}
	
}
class Geter {
	public Geter()
	{
		
	}
	
}