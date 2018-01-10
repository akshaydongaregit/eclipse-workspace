package watchConsole;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class Watch {
	public static int es=0;
public static void main(String args[])
{
	
/* File disks[] = File.listRoots();
	for(int i=0;i<disks.length;i++)
	  System.out.println("\n"+disks[i]);*/
	start();
	
	/*
	System.out.println("-----------------------------------------------------");
	System.out.println("\n Name \t Size \t Used \t Free");
	for(int i=0;i<disks.length;i++)
	{
		String dname=disks[i].getPath();
		float total=(float)disks[i].getTotalSpace()/(1024*1024*1024);
		float free=(float)(disks[i].getFreeSpace())/(1024*1024*1024);
		float used=total-free;
	  System.out.println(" "+dname+" "+total+" "+used+" "+free);
	}
	System.out.println("-----------------------------------------------------"); */
	//System.out.println(new Date());

	/*
	 
	for(int i=0;i<disks.length;i++)
	{
		File dirs[] = null;
		if(disks[i].getTotalSpace()>0)
		  dirs=disks[i].listFiles();
		else
			continue;
		for(int j=0;j<dirs.length;j++)
		{
			System.out.println(j+" "+dirs[j]+" "+dirs[j].isDirectory());
		}
	}   */
	
   //code to check disks 
   
	/*File disks[]=File.listRoots();             //Array of root disks path.
    String DNames[]=new String[disks.length];
	float totals[]=new float[disks.length];
	float free[]=new float[disks.length];
	float used[]=new float[disks.length];
	
	//loading current data into arrays.
	   for(int i=0;i<disks.length;i++)
	    {
		  DNames[i]=disks[i].getPath();
		   totals[i]=(float)disks[i].getTotalSpace()/(1024*1024*1024);
		   free[i]=(float)(disks[i].getFreeSpace())/(1024*1024*1024);
		   used[i]=totals[i]-free[i];
	    }
	
   String logfname="e:\\memlog.txt";       //log file path.
   File logif=new File(logfname);          // File object for input log file.
   File logof=new File("e:\\temp.txt");    // for temp log file. 
   Scanner cin=null;
   Scanner in=null;                        //scanner for input file.
   PrintWriter out=null;                   //Writer for output temp file.
   // checking presens of input log file.
   /*if(!logif.exists())
   {
	try {
		logif.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("\nError while creating file .");
		System.exit(es++);
	}   
   }
   
   //loading input log file.
   try 
    {
	    in=new Scanner(logif);
	    cin=new Scanner(logif);
    } catch (FileNotFoundException e) 
    {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  System.exit(es++);
     }
   
   //loading temp file to write current data.
   	try
	{
		FileWriter outf=new FileWriter(logof);
		BufferedWriter br=new BufferedWriter(outf);
		out=new PrintWriter(outf);
	}catch(IOException e)
	{
		e.printStackTrace();
		System.exit(es++);
	} 
   
   	//check if ile is empty.
   	if(!cin.hasNextLine())
    {
 	   System.out.println("\nNot initialized...\n\tInitializing......");
 	   
 	   for(int i=0;i<disks.length;i++)
 	    {
 		  out.println(DNames[i]+" "+free[i]); //writing data to file.
 	    }
 	System.out.println("\nInitialized  Close and Reopen Application.");
   }
   	else
   	{
   	 /*************************************
   	        code for checking log file
   	     and comparing with current state . 
   	    *******************************************/
  /* 	int lcnt=0;
   	int cdcnt=0;
   	
   	System.out.println("----------------------------------------------------------------------------------------------");
   	System.out.println(" Disk Name \t Old \t\t New \t\t Last Modify \t\t\t Change(MB)");
   	
   	while(in.hasNextLine())
   	{
   		String line=in.nextLine();
   		String DName=line.substring(0,line.indexOf(" "));
   		float frees=(float) Double.parseDouble(line.substring(line.indexOf(" ")+1,line.length()));
   		
   		//System.out.println("DName : "+DName+" free : "+frees);
   		
   		if(frees!=free[lcnt])
   		{
   			Date mdate=new Date(disks[lcnt].lastModified());
   			float change=(frees-free[lcnt])*1024;
   			System.out.println("----------------------------------------------------------------------------------------------");
   			System.out.println(" "+DName+"\t\t"+frees+"\t"+free[lcnt]+"\t"+mdate+"\t"+change);
   		    cdcnt++;
   		}
   		lcnt++;
   	}
   	System.out.println("----------------------------------------------------------------------------------------------");
   	
   	//updating log file with current data.
   	for(int i=0;i<disks.length;i++)
	    {
		  out.println(DNames[i]+" "+free[i]); //writing data to file.
	    }
   	}
   	
   	
   	//closing of in and out streams.
   	out.close();
   	cin.close();
   	in.close();
   
   	//stuff for update log file with temp.
   	logif.delete();
   	logof.renameTo(logif);
   	
   	                                 */
   	//closing program .
	System.exit(0);
}
public static void start()
{
	//System.out.println("\nin start ");
	
	String logfname="e:\\memlog.txt";       //log file path.
	   File logif=new File(logfname);          // File object for input log file.
	   Scanner in=null;                        //scanner for input file.
	   
	   // checking presens of input log file.
	   if(!logif.exists())
	   {
		   Write_Log();
		   System.out.println("\n |_*_| Please Reopen the Application . |_*_|");
		   System.exit(es++);
	   }

	   //loading input log file.
	   try 
	    {
		    in=new Scanner(logif);
	    } catch (FileNotFoundException e) 
	    {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		  System.exit(es++);
	     } 
	 //check if ile is empty.
	   	if(!in.hasNextLine())
	    {
	   		in.close();
	 	   System.out.println("\n|___ Not initialized...\n\tInitializing.. ");
	 	   Write_Log();
	 	   System.out.println("\n|_*_|  Initialized  Close and Reopen Application.  |_*_|");
	       System.exit(es++);
	    }
	   	
	   	//Doing comparison .
	   	
	   	// Main operation ENDS Here
	   	System.out.println("\n|___________ -----__ CHECKED __-----_____________|");
	   	
	   	in.close();
	   	logif.delete();
	   	Write_Log();
}
public static void Write_Log()
{
	   String logfname="e:\\memlog.txt";       //log file path.
	   File logf=new File(logfname);          // File object for input log file.
	   PrintWriter out=null;                   //Writer for output temp file.

		try
		{
			FileWriter outf=new FileWriter(logf);
			BufferedWriter br=new BufferedWriter(outf);
			out=new PrintWriter(outf);
		}catch(IOException e)
		{
			e.printStackTrace();
			System.exit(es++);
		}
		
	   File disks[]=File.listRoots(); 
	   String DNames[]=new String[disks.length];
	   float totals[]=new float[disks.length];
	   float free[]=new float[disks.length];
	   float used[]=new float[disks.length];
			
		//loading current data into arrays.
	   for(int i=0;i<disks.length;i++)
	    {
		  DNames[i]=disks[i].getPath();
	      totals[i]=(float)disks[i].getTotalSpace()/(1024*1024*1024);
		  free[i]=(float)(disks[i].getFreeSpace())/(1024*1024*1024);
		  used[i]=totals[i]-free[i];
	    }
		
	   //Updating log file with current values.
	  out.println(new Date());		   
	 /* for(int i=0;i<disks.length;i++)
      {
	    out.println(DNames[i]+" "+used[i]); //writing data to file.
      }
	*/
	  //code for writing data of e drive in file.
	  File disk=new File("e:\\");
	  
	  File Dirs[]=disk.listFiles();
	
}
   public static long getSize(File file)
   {
	  long size=0;
	   
	  if(file.isDirectory())
	  {
		  File subfiles[]=file.listFiles();
		  for(int i=0;i<subfiles.length;i++)
			  size+=getSize(subfiles[i]);
	  }
	  size+=file.length();
	  
	   return size;
   }
	
}
