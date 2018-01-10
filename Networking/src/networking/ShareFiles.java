package networking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Scanner;

public class ShareFiles {

	
	public String COM_IP="192.168.43.140";
	public static int SERVER_PORT=2850;
	public static String APP_NAME="Networking app";
	public static String[] data=new String[3];
	public static String END="END OF DATA";
	public static String SEND="SEND";
	public static String DONT_SEND="DONT SEND";
	public static String cmdline="";
	public static String CLOSE="STOP";
	public static String cmd="";
	public static String args[]=null;
	public static ListHandler list=new ListHandler();
	public static void main(String[] margs) {
		// TODO Auto-generated method stub
        
		Scanner in=new Scanner(System.in);
		CommandHandler cmdl=new CommandHandler();
		cmdl.SetexitCommand("close");
		cmdl.SetexitCommand("exit");
		cmdl.setIdent("---: ");
		while(true)
		{
			//System.out.print("\n---: ");
			//cmdline=in.nextLine();
			//exicute(cmdline);
			cmd=cmdl.getCommand();
			args=cmdl.getArgs();
			exicute();
		}
	}
	public static void exicute()
	{
		
		 if(cmd.equalsIgnoreCase("send"))
		{
			String filename=args[0];        //cmdl.substring(cmdl.indexOf(" ")+1); //extracting file name from command line.
			Send_file(filename);       //sending file to send specified client .
			
		}else if(cmd.equalsIgnoreCase("show"))
		{
			//System.out.println("match show");
			list.showFiles(args[0]);
		}else if(cmd.equalsIgnoreCase("shownum"))
		{
			int num=0;
			try
			{
				num=(int) Double.parseDouble(args[0]);
			}catch(Exception e)
			{
				System.out.println("\n Please provide proper arguments");
				return;
			}
			list.shownum(num);
		}else if(cmd.equalsIgnoreCase("back"))
		{
			list.show_back();
		}else if(cmd.equalsIgnoreCase("sendnum"))
		{
			int num=0;
			try
			{
				num=(int) Double.parseDouble(args[0]);
			}catch(Exception e)
			{
				System.out.println("\n Please provide proper arguments");
				return;
			}
			String filenm=list.get_filename(num);
			Send_file(filenm);
		}
		else
		{
			System.out.println("\n Unrecognized command ");
		}
		
	}
	public static void Send_file(String fname)
	{
		File sourcef=new File(fname);
		String file_name=sourcef.getName();
		System.out.println("\n file name : "+file_name);
		FileInputStream source=null;
		
		//to check if file to be send is exists or not
		if(!sourcef.exists())
		{
			System.out.println("file "+fname+" doesnt exists");
		}
	/*	
		try
		{
			source=new FileInputStream(sourcef);
		}catch(FileNotFoundException e)
		{
			System.out.println("\n Error "+e);
			e.printStackTrace();
			
			return;
		}
		*/
		 Scanner ssource=null; // scanner to read file data which have to send .
		//loading file into scanner .
		
		 try
		 {
			ssource=new Scanner(sourcef);
		  }catch (FileNotFoundException e) 
		    {
			   e.printStackTrace();
			   return ;
		    }
		 
		 
		 /*****************************************
		  Actual code which creates 
		  server socket connects 
		  with client and send data.
		  *******************************************/
		   ServerSocket server;  //server socket for connecting to client
		   Socket connection;    //socket for sending data to client
			try {
			       server=new ServerSocket(SERVER_PORT);
			       System.out.println("\n listening on port "+SERVER_PORT);
			       connection=server.accept();
			       server.close();
			       System.out.print("\n connected to "+connection.getInetAddress().toString());
			       System.out.print("\n sending data... ");
			       
			       PrintWriter out=new PrintWriter(connection.getOutputStream());
			       
			       BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			       //sends file name first
			       out.println(file_name);
			       out.flush();
			       String responce=in.readLine();
			       //check for if client is redy for transfer or not 
			       if(!responce.equals(SEND))
			       {
			    	   System.out.println("\nError client is not ready ");
			    	   
			    	   in.close();
			    	   out.close();
			    	   connection.close();
			    	   return;
			       }
			       System.out.println("\nResponce from client : "+responce);
			       //send data in the file.
			       
			       while(true)
			       {
			    	  int data = source.read();
			    	  //System.out.println("\t"+data);
			    	  
			    	  if(data>200)
			    	  {
			    		  int d=102;
			    		  out.write(d);
			    		  out.flush();
			    		  //System.out.println("\t 102 sended ");
			    		  out.write((data-200));
			    		  out.flush();
			    		  continue;
			    	  }else if(data>100)
			    	  {
			    		  int d=101;
			    		  out.write(d);
			    		  out.flush();
			    		  //System.out.println("\t 101 sended ");
			    		  out.write((data-100));
			    		  out.flush();
			    		  continue;
			    	  }else if(data<0)
			    	    {
			    		  if(data==-1)
			    		  {
			    		    //System.out.println("\nSending completed ");
			    		    out.write(105);
			    		    out.flush();
			    		    break;
			    		  }
			    	    }else
			    	    {
			    	  //System.out.println("\t"+data);
			    	     out.write(data);
			    	     out.flush();
			    	    }
			    	/*  int res=in.read();
			    	  if(res==1)
			    		  continue;
			    	  else
			    		  System.out.print("\n error"); */
			    	  
			       }
			        //out.println(END); //send signal to tell client that sending data is completed.
			        //out.flush();
			        System.out.println("\nClosing connection ");
			        //connection.close();
			        System.out.println("\nConnection closed");
			        in.close();
			        out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("\n error while sending data");
				return;
			}
		 
		 /******************************************/
		 try {
			source.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("\n Error while closing file ");
			e.printStackTrace();
			return;
		}
		 System.out.println("\n Sending completed successfully .");
		 
		 
	}            // Send_file() ends here .
	public static void Send_Data()
	{
		ServerSocket server;
		Socket connection;
		try {
		       server=new ServerSocket(SERVER_PORT);
		       System.out.println("\n listening on port "+SERVER_PORT);
		       connection=server.accept();
		       server.close();
		       System.out.print("\n connected to "+connection.getInetAddress().toString());
		       PrintWriter out=new PrintWriter(connection.getOutputStream());
		       
		       for(int i=0;i<data.length;i++)
		       {
			       out.println(data[i]);
		           out.flush();
		       }
		        
		        out.println(CLOSE);
		        out.flush();
		        connection.close();
		       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public static void print_interfaces()
	{
		Enumeration netInterfaces = null;
		try
		{
		  netInterfaces=NetworkInterface.getNetworkInterfaces();
		}catch(Exception e)
		{
			System.out.print("\n Error "+e);
		}
		while(netInterfaces.hasMoreElements())
		{
			NetworkInterface net=(NetworkInterface) netInterfaces.nextElement();
			System.out.println("\n "+net.getName());
			
		    Enumeration	inetadress=net.getInetAddresses();
		    while(inetadress.hasMoreElements())
		    {
		    	InetAddress adress=(InetAddress) inetadress.nextElement();
		    	System.out.print(" "+adress);
		    }
		}
	}

}
