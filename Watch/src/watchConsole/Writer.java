package watchConsole;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class Writer {
	
	
	static String spchar="|";
	
	public static void main(String args[])
	{
	   long	start=System.currentTimeMillis();
		check();
		
	   long end=System.currentTimeMillis();
		System.out.println("In "+(end-start)+" milliseconds");
	}
	public static void check()
	{
		String olfn="e:\\memlog.txt";
		File olf=new File(olfn);
		String clfn="e:\\memlog1.txt";
		File clf=new File(clfn);
		if(!olf.exists())
			while(!Write_Log(olfn));
		
		//write current state in in this file.
		while(!Write_Log(clfn));
		
		Scanner oin=null;    //scanner for old input log file.
		Scanner cin=null;    //scanner for current input log file.
		
		//initialization of scanner for old input log file.
		try
		{
			oin=new Scanner(olf);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		//initialization of scanner for current input log file.
		try
		{
			cin=new Scanner(clf);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//vars for linewise comparision of current state with old state.
		String cline=null;
		String oline=null;
		String pcline=null;
		String poline=null;
		String pcfilename=null;
		String pofilename=null;
		boolean missflag=false;
		int misscnt=0;
		int add=0;
		int del=0;
		int renam=0;
		int changed=0;
		//linewise comparision  of current state with old state.
		while(cin.hasNextLine())
		{
			 cline=cin.nextLine();
			 oline=oin.nextLine();
			
			String cfilename=cline.substring(1,cline.indexOf(spchar));
			String ofilename=oline.substring(1,oline.indexOf(spchar));
			long cfilesize=(long) Double.parseDouble(cline.substring(cline.indexOf(spchar)+1,cline.length()));
			long ofilesize=(long) Double.parseDouble(oline.substring(oline.indexOf(spchar)+1,oline.length()));
		    
			//if missflag then compare two lines for getting change.
			if(missflag)
			{
			    missflag=false;
			    misscnt++;
				if(cfilename.equals(pofilename))
				{
					//added
					System.out.println("\n File added  - "+pcfilename);
					cline=cin.nextLine();
					add++;
					
				}else if(pcfilename.equals(ofilename))
			    {
					//deleted
					System.out.println("\n File deleted - "+pofilename);
					oline=oin.nextLine();
					del++;
				}else //if(cfilename.equals(ofilename))
				{
			         //rename
					 System.out.println("\n"+pofilename+" has Renamed to "+pcfilename);
					  //cline=cin.nextLine();
					  //oline=oin.nextLine();
					 renam++;
				}
				
			}
			//updating values after missflag checking.
			 cfilename=cline.substring(1,cline.indexOf(spchar));
			 ofilename=oline.substring(1,oline.indexOf(spchar));
			 cfilesize=(long) Double.parseDouble(cline.substring(cline.indexOf(spchar)+1,cline.length()));
			 ofilesize=(long) Double.parseDouble(oline.substring(oline.indexOf(spchar)+1,oline.length()));
			
			//checking files with name.
			if(cfilename.equals(ofilename))
			{
				missflag=false;
                // check for change in file size.
                if(cfilesize!=ofilesize)
                {
                	changed++;
                	System.out.println("\n"+cfilename+" is changed "+"old size : "+conSize(ofilesize)+" new size : "+conSize(cfilesize));
                }
				
              
			}else //if file name mismatch set missflag true.
			{
				missflag=true;
			}
				pcline=cline;
				poline=oline;
				pcfilename=cfilename;
				pofilename=ofilename;
		}
		
		System.out.println("\n______________________________________________________________");
		System.out.println("| Files :                                                     |");
		System.out.println("|                                                             |");
		System.out.println("| Added : "+add+"\t Deleted : "+del+"\t Renamed : "+renam+"\t changed : "+changed);
		System.out.println("|_____________________________________________________________|");
		
		System.out.println("\n|__| Scanning Complited Successfully |__|");
		//closing all scanners
		cin.close();
		oin.close();
		olf.delete();
		clf.renameTo(olf);
	}
	public static boolean Write_Log(String path)
	{
	
		int dcnt=0;
		
		//vars dec for input output files;
		String logf=path;
		File inf=new File(logf);
		Scanner in=null;
		PrintWriter out=null;
		
		//Checking for presence of log file.
		if(!inf.exists())
			try {
				inf.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//initializing input scanner.
		try
		{
			in=new Scanner(inf);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		//initializing out BufferedReader.
		try
		{
			FileWriter outf=new FileWriter("e:\\temp.txt");
			out=new PrintWriter(outf);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//loading initial data of disks to Disks[]
		File Disks[]=File.listRoots();
		File disk=new File("h:\\");
		File Idirs[]=disk.listFiles();
		if(!in.hasNextLine()) //if file is empty then write initial data.
		{
			for(int i=0;i<Idirs.length;i++)
			{
				dcnt++;
				out.println(Idirs[i].getPath()+spchar+getSize(Idirs[i]));
			}
		}else
		{
			//System.out.println("\n In Main else ");
			while(in.hasNextLine())
			{
				//System.out.print("While");
				String line=in.nextLine();
				String fname=line.substring(0,line.indexOf(spchar));
				//String fname=line;
				if(fname.startsWith("."))
				{
					out.println(line);
					continue;
				}
				if(fname.equalsIgnoreCase("h:\\System Volume Information"))
					continue;
				//System.out.print("\tchecked "+c+++"\n");
				File ff=new File(fname);
				if(ff.isDirectory())
				{
					dcnt++;
					
					out.println("."+ff.getPath()+spchar+getSize(ff));
					File subf[]=ff.listFiles();
					if(subf.length>0)
					  for(int i=0;i<subf.length;i++)
					  {
						  out.println(subf[i].getPath()+spchar+getSize(subf[i]));
					  }
				}else
				{
					out.println("."+ff.getPath()+spchar+getSize(ff));
				}
			}
		}
		
		//closing in and out and finishing file operations.
		in.close();
		out.close();
		inf.delete();
		File outf=new File("e:\\temp.txt");
		outf.renameTo(inf);
		//returning true value if writing is completed. i.e. no folder found
		if(dcnt>0)
			return false;
		else
			return true;
	}
	
	public static long getSize(File file)
	{
		  long size=0;
		  if(file.isDirectory())
		  {
			  File[] subfiles=file.listFiles();
			  
			  int length=0;
			  try
			  {
			  length=subfiles.length;
			  }catch(NullPointerException e)
			  {
					return 0;
			  }
			  for(int i=0;i<length;i++)
				  size+=getSize(subfiles[i]);
		  }
		  size+=file.length();
		  
		   return size;
	}
		public static String conSize(long size)
		{
		   float csize=size;	
			if(csize<1000)
			{
				return csize+" Byte";
			}else
			{
				csize=csize/1024;
				if(csize<1000)
				{
					return csize+" KB";
				}else
				{
					csize=csize/1024;
					if(csize<1000)
					{
						return csize+" MB";
					}else
					{
						csize=csize/1024;
					  return csize+" GB";
					}
				}
			}
			
			
		}
}
