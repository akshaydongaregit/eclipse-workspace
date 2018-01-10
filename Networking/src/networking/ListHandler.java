package networking;

import java.io.File;

public class ListHandler {
	static File[] list ;
	static String imgnm;
	static boolean successful;

	public void show_back()
	{
		String path=list[0].toString();
		//if(list[0].isFile())
		//{
		   path=path.substring(0,path.lastIndexOf("\\"));
		   if(path.contains("\\"))
		   {
		    path=path.substring(0,path.lastIndexOf("\\"));
		    showFiles(path+"\\");
		   }else
		   {
			   showFiles(path+"\\");   
		   }
		   
		/*}else
		{
			path=path.substring(0,path.lastIndexOf("\\"));
			showFiles(path);
		}*/
		
	}
	
	public void shownum(int num)
	{
		if(num < list.length)
			 showFiles(list[num ].toString());
			else 
				System.out.println("\nInvalid number ");
	}
	public String get_filename(int num)
	{
		if(num < list.length)
			 return (list[num ].toString());
			else 
				System.out.println("\nInvalid number ");
		return null;
	}
	public static void showFiles(String filenm)
	{
		System.out.println("filenm : "+filenm);
		 File file = null;
		try{
		  file = new File(filenm);
		}catch(Exception e){
			e.printStackTrace();
			e.printStackTrace();
			System.out.print("error + e");
			
		}
		if(file.isDirectory())
	    {
			
			list = file.listFiles();
			
			int i=0;
			while(i<list.length)
			{
				//if(list[i].toString().endsWith(".jpginc"))
				System.out.println("\n"+i+":"+list[i].toString());
				i++;
				}
		}else 
		System.out.println("\n Path is unvalid ");
		successful=false;
	}
}

