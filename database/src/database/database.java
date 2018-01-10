package database;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class database {

	static String cmdline;
	static String cmd;
	static String[] args;
	static String pfolder="c:\\akshay\\";
	public static void main(String[] margs) {
		// TODO Auto-generated method stub
    
		CommandHandler cmdl=new CommandHandler("Master");
		cmdl.SetexitCommand("exit");
		cmdl.SetexitCommand("close");
		cmdl.SetexitCommand("quit");
		cmdl.SetexitCommand("stop");
		cmdl.setIdent("---: ");
		while(true)
		{
			cmd=cmdl.getCommand();
			args=cmdl.getArgs();
		    exicute();
		}
	}
	public void run(String cmdl)
	{
		CommandHandler cmdh=new CommandHandler("Slave");
		cmdh.setCommandLine(cmdl);
		
			cmd=cmdh.getCommand();
			args=cmdh.getArgs();
		    exicute();
	}
	
	public static void exicute()
	{
		

		switch(cmd)
		{
		case "create":
			create();
			break;
		case "update":
			update();
			break;
		case "select":
			select();
			break;
		case "delete":
			delete();
			break;
		default:
			System.out.println("\nUnrecognized command");
		}
	}
	public static void create()
	{
		if(args.length<3)
		{
			System.out.print("\n USAGE : \n");
			return;
		}
		//System.out.println("\nargs[0]:"+args[0]);
		if(args[0].equalsIgnoreCase("TABLE"))
		{
			if(args.length%2==0)
			  createTable();
			else
				System.out.println("\nInsufficient arguments");
		}
		
	}
	public static void createTable()
	{
		File dbf=new File(pfolder+args[1]+".trtb");
		PrintWriter dbout=null;
		if(!dbf.exists())
			try 
			{
				//System.out.println("creating ....");
				dbf.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//System.out.println("\nAlready exists");
			try
			{
				 dbout=new PrintWriter(dbf);
		    } catch (FileNotFoundException e) 
		    {
		    	// TODO Auto-generated catch block
		    	e.printStackTrace();
		    }
			
			/***********************************
			 writing table confuguaration to file
			 **************************/
			
			dbout.println("TABLE "+args[1]);
			
			dbout.println("STRUCT");
			for(int i=2;i<args.length;i+=2)
				dbout.println(args[i+1]+" "+args[i]);
			dbout.println("END STRUCT");
			
			dbout.println("DATA");
				
			dbout.println("END DATA");
			
			dbout.println("END TABLE");
			/************************
			 end of writing file 
			 *************************/
			dbout.close();
			
	}
	public static void update()
	{
		if(args.length<3)
		{
			System.out.println("\n USAGE : ");
		}
		if(args[0].equalsIgnoreCase("TABLE"))
		{
			if(args[2].equalsIgnoreCase("add"))
		       updatetable(args[1]);
			else if(args[2].equalsIgnoreCase("remove"))
				removecol(args[1],args[3]);
			else if(args[2].equalsIgnoreCase("new"))
				addcol(args[1],args[3],args[4]);
			else if(args[2].equalsIgnoreCase("set"))
			{
				String con=null;
				String value=args[3].substring(args[3].indexOf("=")+1);
				
				if(value.startsWith("\""))
				    for(int i=4;i<args.length;i++)
				    {
				   	     if(args[i].endsWith("\""))
				   	     {
				   	    	 value+=" "+args[i];
				   	    	 break;
				   	     }else
				   	    	 value+=" "+args[i];
				    }
				for(int i=3;i<args.length;i++)
				{
					
					if(args[i].equalsIgnoreCase("where"))
					{
						i++;
						con=args[i];
						for(int j=i+1;j<args.length;j++)
						{
							con+=" "+args[j];
						}
						break;
					}
				}
				if(con!=null)
				{
					//System.out.println("\n con is "+con);
		            set(args[1],value,con);
				}
				else
					set(args[1],value,"true");	
					
			}
		}
	}
	private static void addcol(String tablenm, String datatype, String colnm)
	{
		File dbf=new File(pfolder+tablenm+".trtb");
		File dbtemp=new File(pfolder+"temp.trsk");
		Scanner dbin=null;
		PrintWriter dbout=null;
		//int coln=0;
		if(!dbf.exists())
		{
			System.out.println("\n Table "+tablenm+" Doesent exists");
			return;
		}
		
		// loding database file into scanner in
		try 
		{
			dbin=new Scanner(dbf);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//creating temp file
		try 
		{
			dbtemp.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		//creating output stream for temp database file
		try
		{
			dbout=new PrintWriter(dbtemp);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//code to update data
		String line = "";
		//int colcnt=0;
		//boolean struct=false;
		
		
		while(dbin.hasNextLine())
		{
			line=dbin.nextLine();
			
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				dbout.println(colnm+" "+datatype);
				dbout.println(line);
				break;
			}
			
			dbout.println(line);
		}
		  //code for append null at end of each line
		    dbout.println(dbin.nextLine());
			line=dbin.nextLine();
			while(!line.equalsIgnoreCase("END DATA"))
			{
				
				dbout.print(line);
				
				if(datatype.equalsIgnoreCase("string"))
					dbout.print(" "+"\"null\"");
				else
					dbout.print(" "+0);
				
				dbout.print("\n");
                line=dbin.nextLine();
						     
			}
		
			//code for writing remaining data to file
			dbout.println(line);
			while(dbin.hasNextLine())
			{
				line=dbin.nextLine();
				dbout.println(line);
			}
				
			//code block for closing file resources
			dbin.close();
			dbout.close();
			dbf.delete();
			dbtemp.renameTo(dbf);
			System.out.println("successfully added new colnum");		
	}
	private static void removecol(String tablenm, String colnm) {
		/*
		 * used to delete specific column
		 */
		
		File dbf=new File(pfolder+tablenm+".trtb");
		File dbtemp=new File(pfolder+"temp.trsk");
		Scanner dbin=null;
		PrintWriter dbout=null;
		int coln=0;
		if(!dbf.exists())
		{
			System.out.println("\n Table "+tablenm+" Doesent exists");
			return;
		}
		
		// loding database file into scanner in
		try 
		{
			dbin=new Scanner(dbf);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//creating temp file
		try 
		{
			dbtemp.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		//creating output stream for temp database file
		try
		{
			dbout=new PrintWriter(dbtemp);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//code to update data
		String line = "";
		int colcnt=0;
		boolean struct=false;
		
		
		while(dbin.hasNextLine())
		{
			line=dbin.nextLine();
		
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				struct=false;
			}
			if(struct)
			{
				if(line.startsWith(colnm))
				{
					coln=colcnt;
					continue;
				}
				colcnt++;
			}
			dbout.println(line);
			if(line.equalsIgnoreCase("DATA"))
				break;
			if(line.equalsIgnoreCase("STRUCT"))
			{
				struct=true;
			}
		}
		  //code for eliminating columns values and rewrite table 
			line=dbin.nextLine();
			while(!line.equalsIgnoreCase("END DATA"))
			{
				
				Scanner lines;
				
				lines=new Scanner(line);
				int argcnt=0;
				while(lines.hasNext())
				{
					lines.next();
					argcnt++;
				}
				//System.out.println("\nargcnt : "+argcnt);
				String largs[]=new String[argcnt];
				lines.close();
				lines=new Scanner(line);//=lines.reset();
				argcnt=0;
				while(lines.hasNext())
				{
					largs[argcnt]=lines.next();
					argcnt++;
				}
				
				lines.close();
				ArrayList<String> argvalues=getvalues(largs,0);
				
				//System.out.println("argvalues last : "+argvalues.get(1));
				
				for(int i=0;i<argvalues.size();i++)
				{
					if(i==coln)
						continue;
					else
						if(i==0)
						    dbout.print(argvalues.get(i));
						else
							dbout.print(" "+argvalues.get(i));
			    }
				
				dbout.print("\n");
                line=dbin.nextLine();
		     
			}
		
			//code for writing remaining data to file
			dbout.println(line);
			while(dbin.hasNextLine())
			{
				line=dbin.nextLine();
				dbout.println(line);
			}
				
			//code block for closing file resources
			dbin.close();
			dbout.close();
			dbf.delete();
			dbtemp.renameTo(dbf);
			System.out.println("successfully removed");
		
	}
	public static void set(String tablenm,String value,String cond)
	{
		/*
		 this is used to update table 
		 example -
		 ---: select name from student where rollno==8
         gnesh
         ---: update table student set name=ganesh where rollno==8
         successfully updated
         ---: select name from student where rollno==8
         ganesh
         ---: 
		 */
		
		File dbf=new File(pfolder+tablenm+".trtb");
		File dbtemp=new File(pfolder+"temp.trsk");
		Scanner dbin=null;
		PrintWriter dbout=null;
		String tcolnm=args[3].substring(0,args[3].indexOf("="));
		int tcoln=0;
		int rcoln=0;
		if(!dbf.exists())
		{
			System.out.println("\n Table "+tablenm+" Doesent exists");
			return;
		}
		
		// loding database file into scanner in
		try 
		{
			dbin=new Scanner(dbf);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//creating temp file
		try 
		{
			dbtemp.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		//creating output stream for temp database file
		try
		{
			dbout=new PrintWriter(dbtemp);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//code to update data
		
		//System.out.println("\n getting cons[] from cond : "+cond);
		String cons[]=getconargs(cond); //stores con colname and index of operator
		//System.out.println("\n cons[0] : "+cons[0]+" "+cons[1]);
		String rcolnm=cons[0];
		
		String conpart=cond.substring((int)Double.parseDouble(cons[1]));
		//System.out.println(cons[0]+" "+conpart);
		String line = "";
		int colcnt=0;
		boolean struct=false;
		
		/*   code to get col no. 
		and offset of starting of data section
		which checks line looks for required part and also 
		writes data output file(dbout).
		  */
		
		while(dbin.hasNextLine())
		{
			line=dbin.nextLine();
			dbout.println(line);
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				struct=false;
			}
			if(struct)
			{
				if(line.startsWith(tcolnm))
					tcoln=colcnt;
				else if(line.startsWith(rcolnm))
					    rcoln=colcnt;
				colcnt++;
			}
			if(line.equalsIgnoreCase("DATA"))
				break;
			if(line.equalsIgnoreCase("STRUCT"))
			{
				struct=true;
			}
		}
		  //code for checking corrus. con col value and updating cours. entry 
			String val="";
			line=dbin.nextLine();
			while(!line.equalsIgnoreCase("END DATA"))
			{
				val=getarg(line,rcoln);
				//System.out.println("\n val : "+val+" con : "+conpart);
				if(check_con(val+conpart))
				{
					//System.out.println("cond is true");
					
					Scanner lines;
					
					lines=new Scanner(line);
					int argcnt=0;
					while(lines.hasNext())
					{
						lines.next();
						argcnt++;
					}
					//System.out.println("\nargcnt : "+argcnt);
					String largs[]=new String[argcnt];
					lines.close();
					lines=new Scanner(line);//=lines.reset();
					argcnt=0;
					while(lines.hasNext())
					{
						largs[argcnt]=lines.next();
						argcnt++;
					}
					
					lines.close();
					ArrayList<String> argvalues=getvalues(largs,0);
					
					//System.out.println("argvalues last : "+argvalues.get(1));
					
					for(int i=0;i<argvalues.size();i++)
					{
						if(i==tcoln)
							if(i==0)
							    dbout.print(value);
							else
								dbout.print(" "+value);
						else
							if(i==0)
							    dbout.print(argvalues.get(i));
							else
								dbout.print(" "+argvalues.get(i));
					}
					
					dbout.print("\n");
					line=dbin.nextLine();
					continue;
				}
				else
					dbout.println(line);
				 line=dbin.nextLine();
			}
			
			//code for writing remaining data to file
			dbout.println(line);
			while(dbin.hasNextLine())
			{
				line=dbin.nextLine();
				dbout.println(line);
			}
				
			//code block for closing file resources
			dbin.close();
			dbout.close();
			dbf.delete();
			dbtemp.renameTo(dbf);
			System.out.println("successfully updated");
	}
	public static void updatetable(String tablenm)
	{
		File dbf=new File(pfolder+tablenm+".trtb");
		File dbtemp=new File(pfolder+"temp.trsk");
		Scanner dbin=null;
		PrintWriter dbout=null;
		String section="";
		int coln=0;
		if(!dbf.exists())
		{
			System.out.println("\n Table "+tablenm+" Doesent exists");
			return;
		}
		
		// loding database file into scanner in
		try 
		{
			dbin=new Scanner(dbf);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//creating temp file
		try 
		{
			dbtemp.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		//creating output stream for temp database file
		try
		{
			dbout=new PrintWriter(dbtemp);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//code for updating data .
		ArrayList<String> datatypes = new ArrayList<String>();
		while(dbin.hasNextLine())
		{
			String line=dbin.nextLine();
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				section="";
			}
			if(section.equals("DATA"))
			{
				if((args.length-3)<coln)
				{
					System.out.println("\nInsufficiant number of values coln : "+coln+"args : "+(args.length-3));
					dbout.println(line);
					section="";
				}else
				{
					ArrayList<String> values=getvalues(args,3);
					
					/*for(int i=0;i<datatypes.size();i++)
						System.out.println(datatypes.get(i));
					
					for(int i=0;i<values.size();i++)
						System.out.println(values.get(i));
					*/
					
					//boolean string=false;
					for(int i=0;i<values.size();i++)
					{
						String value=values.get(i);
						if(datatypes.get(i).equalsIgnoreCase("String"))
						{
							
							if(value.startsWith("\"")&&value.endsWith("\""))
							    if(i==0)
								    dbout.print(value);
							    else
								    dbout.print(" "+value);
							else
								System.out.println("\n wrong input");
						 
						}else if(datatypes.get(i).equalsIgnoreCase("float"))
						{
							try
							{
							float val=(float) Double.parseDouble(value);
							if(i==0)
							    dbout.print(val);
						    else
							    dbout.print(" "+val);	
							}catch(NumberFormatException e)
							{
								System.out.println("\n wrong input");
							}
							
							
						}else
							System.out.println("\nwrong datatype");
						//dbout.print(" "+args[i]);
					}
					dbout.print("\n");
					dbout.println(line);
					section="";
					
				}
			}else
			{
			    dbout.println(line);	
			}
			if(line.equalsIgnoreCase("END DATA"))
			{
				section="";
			}
			if(section.equals("STRUCT"))
			{
				//System.out.println("\n getting argument ");
				String datatype=getarg(line, 1);
				//System.out.println("\n adding argument "+datatype);
				datatypes.add(datatype);
				coln++;
			}
			
			if(line.equalsIgnoreCase("STRUCT"))
			{
				section="STRUCT";
			}
			if(line.equalsIgnoreCase("DATA"))
			{
				section="DATA";
			}
			
		}
		dbout.close();
		dbin.close();
		
		dbf.delete();
		dbtemp.renameTo(dbf);
		System.out.println("successfully updated");
	}

	private static ArrayList<String> getvalues(String[] values, int si) {
		
		/*
		  returns ArrayList<String> containing
		  absolutes values in database 
		  by merging two or more arguments 
		  to form value of type String
		  which may contain space  

		 */
		ArrayList<String> valuelist=new ArrayList<String>();
		String value="";
		boolean string=false;
		//System.out.println("\n values length : "+values.length);
		
		/*for(int j=0;j<values.length;j++)
			System.out.println(values[j]);
			*/
		
		for(int i=si;i<values.length;i++)
		{
			if(values[i].startsWith("\""))
			{
				if(values[i].endsWith("\""))
					value=values[i];
				else
				{
				    value=values[i] ; //.substring(1,values[i].length());
				    string=true;
				    //System.out.println("\n got start "+value);
				    continue;
				}
			}else if(values[i].endsWith("\""))
			{
				value=value+" "+values[i]; //.substring(0,values[i].length()-1);
				//System.out.println("\n got end "+value);
				string=false;
			}else if(string)
			{
				value+=" "+values[i];
				continue;
			}else
			{
				value=values[i];
			}
			valuelist.add(value);
		}
		
		return valuelist;
	}
	public static void select()
	{
		
		//code for analyzing arguments. 
		String table_name="";
		String colnms[]=new String[10];
		int coln=0;
		String con ="";
		
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equalsIgnoreCase("FROM"))
			{
				 i++;
			     table_name=args[i];
			     continue;
			}
			else if(args[i].equalsIgnoreCase("where"))
			{
				i++;
				con=args[i];
				for(int j=i+1;j<args.length;j++)
				{
				con+=" "+args[j];
				}
				break;
			}else
			{
				colnms[coln]=args[i];
				coln++;
			}
		}
		
		//System.out.println("\n Colnm : "+colnms[0]);
		ArrayList<String> valueslist= new ArrayList<String>();
		String colnm[]=new String[coln];
		for(int i=0;i<coln;i++)
		{
		    	colnm[i]=colnms[i];
		}
		
		if(args[0].equalsIgnoreCase("*"))
		{
			System.out.print("\nprinting all");
			colnm=getAllRows(table_name);
			valueslist=getdata(colnm,table_name,con);
			
		}else
			valueslist=getdata(colnm,table_name,con);
		
	/*	System.out.print("\n+---------------------------------------+\n+");
		for(int i=0;i<colnm.length;i++)
			System.out.print(colnm[i]+"\t");
		
		System.out.print("\n+---------------------------------------+");
		System.out.print("\n");
		*/
		TableWriter tablewriter=new TableWriter();
		
		//System.out.println("colnm length : "+colnm.length);
		for(int i=0;i<colnm.length;i++)
			tablewriter.addCol(15);
		tablewriter.printHeader(colnm);
		for(int i=0;i<valueslist.size();i+=colnm.length)
		{
			//System.out.print("+");
			String colval[]=new String[colnm.length];
			for(int j=0;j<colnm.length;j++)
				colval[j]=""+valueslist.get(i+j);
			    //System.out.print(" "+valueslist.get(j)+" ");
			//System.out.print("\n+---------------------------------------+\n");
				tablewriter.printrow(colval);
		}
		//System.out.print("\n+---------------------------------------+");
		tablewriter.printBreakRow();
		System.out.println("\nRecords matched : "+valueslist.size()/colnm.length);
		//select city from sample where marks>0
	}
	private static ArrayList<String> getAll(String tablenm,String con)
	{
		
		File dbf=new File(pfolder+tablenm+".trtb");
		Scanner in=null; //scanner for input database file.
		try {
			in=new Scanner(dbf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//code to get all columnames.
		String cols[]=new String[20];
		String line="";
		boolean struct=false;
		int colcnt=0;
		while(in.hasNextLine())
		{
			line=in.nextLine();
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				struct=false;
			}
			if(struct)
			{
				cols[colcnt]=line.substring(0,line.indexOf(" "));
				
				/*
				for(int i=0;i<colnms.length;i++)
				    if(line.startsWith(colnms[i]))
				        coln[i]=colcnt;
				        
				if(line.startsWith(rcolnm))
					rcoln=colcnt;
				*/
				colcnt++;
			}
			if(line.equalsIgnoreCase("DATA"))
				break;
			if(line.equalsIgnoreCase("STRUCT"))
				struct=true;
		}
		
		String colnm[]=new String[colcnt];
		for(int i=0;i<colcnt;i++)
		{
				System.out.print("\nAdding "+cols[i]);
		    	colnm[i]=cols[i];
		}
		
		in.close();
		return getdata(colnm,tablenm,con);
	}
	public static String [] getAllRows(String tablenm)
	{
		File dbf=new File(pfolder+tablenm+".trtb");
		Scanner in=null; //scanner for input database file.
		try {
			in=new Scanner(dbf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//code to get all columnames.
		String cols[]=new String[20];
		String line="";
		boolean struct=false;
		int colcnt=0;
		while(in.hasNextLine())
		{
			line=in.nextLine();
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				struct=false;
			}
			if(struct)
			{
				cols[colcnt]=line.substring(0,line.indexOf(" "));
				
				/*
				for(int i=0;i<colnms.length;i++)
				    if(line.startsWith(colnms[i]))
				        coln[i]=colcnt;
				        
				if(line.startsWith(rcolnm))
					rcoln=colcnt;
				*/
				colcnt++;
			}
			if(line.equalsIgnoreCase("DATA"))
				break;
			if(line.equalsIgnoreCase("STRUCT"))
				struct=true;
		}
		
		String colnm[]=new String[colcnt];
		for(int i=0;i<colcnt;i++)
		{
				System.out.print("\nAdding "+cols[i]);
		    	colnm[i]=cols[i];
		}
		
		in.close();
		return colnm;
	}
	public static ArrayList<String> getdata(String[] colnms,String tablenm,String con)
	{
		File dbf=new File(pfolder+tablenm+".trtb");
		Scanner in=null; //scanner for input database file.
		//System.out.print("\nOL OK");
		try {
			in=new Scanner(dbf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.print("\nOL OK");
		
		String cons[]=getconargs(con); //stores con colname and index of operator
		boolean nullcond=false;
		//System.out.print("\nOL OK");
		String rcolnm="";
		String conpart="";
		//System.out.print("\nOL OK");
		if(cons ==null || cons[0]==null || cons[1]==null || con==null )
		{
			nullcond=true;
			//System.out.print("\nOL OK");
		}else
		{
			//System.out.print("\nOL OK from else");
			rcolnm=cons[0];
			//System.out.print("\nOL OK from else 2");
			conpart="";
			if(con!=null || cons[1]!=null)
				conpart=con.substring((int)Double.parseDouble(cons[1]));
			
		}
		//System.out.print("\nOL OK");
		
		//System.out.println(cons[0]+" "+conpart);
		String line = "";
		int coln[]=new int[colnms.length];
		int rcoln=0,colcnt=0;
		boolean struct=false;
		ArrayList<String> values=new ArrayList<String>();
		//System.out.println("\n coln "+coln.length);
		
		/*   code to get col no. to be print and con col no. 
		and offset of starting of data section  */
		
		while(in.hasNextLine())
		{
			line=in.nextLine();
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				struct=false;
			}
			if(struct)
			{
				
				for(int i=0;i<colnms.length;i++)
				    if(line.startsWith(colnms[i]))
				    {
				    	System.out.print(""+i+colnms[i]+".");
				        coln[i]=colcnt;
				    }
				
					if(line.startsWith(rcolnm))
						rcoln=colcnt;
				colcnt++;
			}
			if(line.equalsIgnoreCase("DATA"))
				break;
			if(line.equalsIgnoreCase("STRUCT"))
				struct=true;
		}
		
		System.out.print("\nPrinting ... "+coln[0]);
		//code for checking corrus. con col value and printing col value.
		String val="",rval="";
		line=in.nextLine();
		while(!line.equalsIgnoreCase("END DATA"))
		{
			//val=getarg(line,coln);
			rval=getarg(line,rcoln);
			//System.out.println("\n val : "+val+" rval : "+rval+"con "+rval+conpart);
			
			if(!nullcond)
			{
				if(check_con(rval+conpart))
				{
					//System.out.println(val);
					for(int i=0;i<coln.length;i++)
					{
						val=getarg(line,coln[i]);
						values.add(val);
					}
				}
			}else
			{
				//System.out.print("\nOL OK");
					for(int i=0;i<coln.length;i++)
					{
						val=getarg(line,coln[i]);
					    values.add(val);
					}
					
			}
			
			line=in.nextLine();
		}
		
		in.close();
		
		return values;
	}
	
	public static void delete()
	{
	
		if(args[0].equalsIgnoreCase("TABLE"))
		{
			deletetable(args[1]);
			return;
		}
		if(args[0].equalsIgnoreCase("ENTRY"))
		{
			String table_name="";
			String con ="";
			for(int i=0;i<args.length;i++)
			{
				if(args[i].equalsIgnoreCase("FROM"))
				{
					 i++;
				     table_name=args[i];
				     continue;
				}
				else if(args[i].equalsIgnoreCase("where"))
				{
					i++;
					con=args[i];
					for(int j=i+1;j<args.length;j++)
					{
					con+=" "+args[j];
					}
					break;
				}
				/*else
				{
					coln++;
				}*/
			}
			
			//System.out.println("\ndeleting data from "+table_name);
			deletedata(table_name,con);
		}
	}
	public static void deletetable(String tablenm)
	{
		File dbf=new File(pfolder+tablenm+".trtb");
		if(!dbf.exists())
		{
			System.out.println("\nTable "+tablenm+" doesent exists");
			return;
		}
		
		dbf.delete();
		System.out.println("Table "+tablenm+" Successfully deleted ");
	}
	public static void deletedata(String tablenm,String con)
	{
		File dbf=new File(pfolder+tablenm+".trtb");
		File dbtemp=new File(pfolder+"temp.trsk");
		Scanner in=null;
		PrintWriter dbout=null;
		
		//code to check if database file exists or not
		if(!dbf.exists())
		{
			System.out.println("\n Table "+args[1]+" Doesent exists");
			return;
		}
		
		// loding database file into scanner in
		try 
		{
			in=new Scanner(dbf);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//creating temp file
		try 
		{
			dbtemp.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		//creating output stream for temp database file
		try
		{
			dbout=new PrintWriter(dbtemp);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		
		String cons[]=getconargs(con); //stores con colname and index of operator
		String colnm=cons[0];
		String conpart=con.substring((int)Double.parseDouble(cons[1]));
		//System.out.println(cons[0]+" "+conpart);
		String line = "";
		int coln=0,colcnt=0;
		boolean struct=false;
		
		/*   code to get col no. 
		and offset of starting of data section
		which checks line looks for required part and also 
		writes data output file(dbout).
		  */
		
		while(in.hasNextLine())
		{
			line=in.nextLine();
			dbout.println(line);
			if(line.equalsIgnoreCase("END STRUCT"))
			{
				struct=false;
			}
			if(struct)
			{
				if(line.startsWith(colnm))
					coln=colcnt;
				colcnt++;
			}
			if(line.equalsIgnoreCase("DATA"))
				break;
			if(line.equalsIgnoreCase("STRUCT"))
			{
				struct=true;
			}
		}
		  //code for checking corrus. con col value and deliting cours. entry 
			String val="";
			line=in.nextLine();
			while(!line.equalsIgnoreCase("END DATA"))
			{
				val=getarg(line,coln);
				//System.out.println("\n val : "+val);
				if(check_con(val+conpart))
				{
					line=in.nextLine();
					continue;
				}
				else
					dbout.println(line);
				 line=in.nextLine();
			}
			
			//code for writing remaining data to file
			dbout.println(line);
			while(in.hasNextLine())
			{
				line=in.nextLine();
				dbout.println(line);
			}
				
			//code block for closing file resources
			in.close();
			dbout.close();
			dbf.delete();
			dbtemp.renameTo(dbf);
			System.out.println("successfully deleted");
		
	}
	public static String getarg(String term,int num)
	{
		//System.out.println("\n in getrgs "+term+" "+num);
	int vcnt=0;
	String arg="";
	boolean string=false;
	String value="";
	
	Scanner in=new Scanner(term);
	
	while(in.hasNext())
	{
		arg=in.next();
		
		if(arg.startsWith("\""))
		{
			if(arg.endsWith("\""))
				value=arg.substring(1,arg.length()-1);
			else
			{
			    value=arg.substring(1);
			    string=true;
			    continue;
			}
		}else if(arg.endsWith("\""))
		{
			value+=" "+arg.substring(0,arg.length()-1);
			string=false;
		}else if(string)
		{
			value+=" "+arg;
			continue;
		}else
			value=arg;
		
		if(vcnt==num)
		{
			in.close();
			return value;
		}
		vcnt++;
	}
	
	    in.close();
		return null;
	}
	public static String[] getconargs(String con)
	{
		String cons[]=new String[2]; // stores 
		int iop;
		
		   if(con.indexOf("<")>0&&con.indexOf("=")>0)
		   {
			  iop=con.indexOf("<");
			  cons[0]=con.substring(0,iop);
			  cons[1]=""+iop;                //con.substring(iop+2,con.length());
			  return cons;
	       }else if(con.indexOf(">")>0&&con.indexOf("=")>0)
		   {
	    	  iop=con.indexOf(">");
			  cons[0]=con.substring(0,iop);
			  cons[1]=""+iop;
			  return cons;
		  }else if(con.indexOf("=")!=con.lastIndexOf("="))
		  {
			  iop=con.indexOf("=");
			  cons[0]=con.substring(0,iop);
			  cons[1]=""+iop;
			  return cons;  
		  }else if(con.indexOf("<")>0)
		  {
			  iop=con.indexOf("<");
			  cons[0]=con.substring(0,iop);
			  cons[1]=""+iop;
			  return cons;
		  }else if(con.indexOf(">")>0)
		  {
			  iop=con.indexOf(">");
			  cons[0]=con.substring(0,iop);
			  cons[1]=""+iop;
			  return cons; 
		  }

		return null;
	}
	public static boolean check_con(String term) {
		// TODO Auto-generated method stub
		if(term.indexOf("<")>0&&term.indexOf("=")>0)
		  {
			  int iop=term.indexOf("<");
			  int p1=(int)Double.parseDouble(( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble((term.substring(iop+2,term.length())));
			  if(p1<=p2) return true;
			  else return false;}else
		  if(term.indexOf(">")>0&&term.indexOf("=")>0)
		  {
			  int iop=term.indexOf(">");
			  int p1=(int)Double.parseDouble(( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble((term.substring(iop+2,term.length())));
			  if(p1>=p2) return true;
			  else return false;}else
		  if(term.indexOf("=")!=term.lastIndexOf("="))
		  {
			  int iop=term.indexOf("=");
			  String c1=term.substring(0,iop);
			  String c2=term.substring(iop+2,term.length());
			 //System.out.println("\n in check_con "+c1+c2);
			  if(c2.startsWith("\"")&&c2.endsWith("\""))
				  if(c1.equals(c2.substring(1,c2.length()-1)))
					  return true;
				  else
					  return false;
			  int p1=(int)Double.parseDouble(( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble((term.substring(iop+2,term.length())));
			  if(p1==p2) return true;
			  else return false;}else
		  if(term.indexOf("<")>0)
		  {
			  int iop=term.indexOf("<");
			 int p1=(int)Double.parseDouble(( term.substring(0,iop)));
			 int p2=(int)Double.parseDouble((term.substring(iop+1,term.length())));
			 if(p1<p2) return true;
			 else return false;}else
		  if(term.indexOf(">")>0)
		  {
			  int iop=term.indexOf(">");
			  int p1=(int)Double.parseDouble(( term.substring(0,iop)));
			  int p2=(int)Double.parseDouble((term.substring(iop+1,term.length())));
			  if(p1>p2) return true;
			  else return false;}

			  else return false;		  
	}
}
