package networking;

import java.util.*;
public class CommandHandler
{
String cmdline;
	String cmd;
	String ecmd[];
	int ecnt=0;
	String args[];
	String Ident="--->";
	public CommandHandler()
	{
      ecmd=new String[5];
	  args=new String[0];
	}
	 public String getCommand()
	 {
		 System.out.print(Ident);
		 Scanner in=new Scanner(System.in);
		 cmdline=in.nextLine();
		 
		 checkExitCode();
		 if(analyze())
		  return cmd;
		 else
			 return null;
	 }
	 public void checkExitCode()
	 {
		 for(int i=0;i<ecmd.length;i++)
		 {
			 if(cmdline.equals(ecmd[i]))
				 System.exit(0);
		 }
	 }
	 public boolean analyze()
	 {
		int wordcout=0,pos=0,space=0;
		 if(cmdline.indexOf(" ")<0)
		 {
			 cmd=cmdline;
			 args=new String[0];
		 }
		 else
		 {
			 cmd=cmdline.substring(0,cmdline.indexOf(" "));
			 cmdline=cmdline.substring(cmdline.indexOf(" ")+1,cmdline.length());
			 do
			 {

				 wordcout++;
				 pos=cmdline.indexOf(" ",pos+1);

			 }while(pos>0);

			 args=new String[wordcout];

			 pos=0;
			 int i=0;
			 while(true)
			 {

				 space=cmdline.indexOf(" ",space+1);
				 //System.out.println("space = "+space);
				 if(space>0)
				 {
					 args[i]=cmdline.substring(pos,space);
					 // System.out.println(i +" " +words[i]);
					 i++;
					 pos=space+1;
					 //System.out.println("pos = "+pos);
				 }else
				 {
					 args[i]=cmdline.substring(pos,cmdline.length());
					 break;
				 }
			 }
			 
			 
		 }
		 return true;
	 }
	 public String[] getArgs()
	 {
		 return args;
	 }
	 public boolean SetexitCommand(String ec)
	 {
		 ecmd[ecnt]=ec;
		 ecnt++;
		 return true;
	 }
	public void setIdent(String ind)
	{
		Ident=ind;
	}
}
