import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class HtmlCorrecter {

	String cmd="";
	static String FILTER_EXTENSION=".jsp";
	static String DOCTYPE_LINE="<!DOCTYPE html>";
	static String ABOVE_LINE="<html>";
	static boolean DETAILED=false;
	int modifiedCount=0;
	
	public HtmlCorrecter()
	{
		
	}
	
	public void start()
	{
		Scanner in=new Scanner(System.in);
		cmd=in.nextLine();
	}
	public void correctFromFolder(String folder)
	{
		System.out.println("\n------------------------------------------------------");
		System.out.println("Starting Correction Process . . .");
		System.out.println("------------------------------------------------------");
		
		File folderFile=new File(folder);
		if(!folderFile.exists())
		{
			System.exit(0);
		}
		File htmlFiles[]=folderFile.listFiles();
		for(File file:htmlFiles)
		{
			//System.out.println(""+file);
			if(file.isFile())
				if(file.getName().endsWith(FILTER_EXTENSION))
					checkAndCorrect(file);
		}
		
		System.out.println("\n------------------------------------------------------");
		System.out.println("Correction Complited Successfully.");
		System.out.println("Total Files Modified : "+modifiedCount);
		System.out.println("------------------------------------------------------");
	}
	
	public String checkAndCorrect(File file)
	{
		String checkStatus="OK";
		
		System.out.println("|-- Checking File -- "+file);
		
		/*checking and correcting.*/
		Scanner fileScanner=null;
		try {
			fileScanner=new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String currentLine="";
		String preLine="";
		boolean needToCorrect=false;
		while(fileScanner.hasNextLine())
		{
			currentLine=fileScanner.nextLine();
			if(currentLine.equalsIgnoreCase(DOCTYPE_LINE))
			{			
				break;
			}else if(currentLine.equalsIgnoreCase(ABOVE_LINE))
			{
				needToCorrect=true;
			}
			preLine=currentLine;
		}
		
		//Closing Scanner.
		fileScanner.close();
		if(needToCorrect)
			try {
				if(insertDocType(file, 0))
				{
					modifiedCount++;
					checkStatus="MODIFIED";
				}
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		
		System.out.println("\\__   "+checkStatus);
		
		return checkStatus;
	}
	public boolean insertDocType(File file,int line) throws IOException
	{
		String status="N_M";
		
		if(DETAILED)
		System.out.println("Correcting...");
		
		Scanner fileScanner=null;
		try {
			fileScanner=new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		File corrected=new File(file.getParent()+"\\temp.html");
		
		//System.out.println("\n"+file.getParent()+"\\temp.html");
		
		if(corrected.exists())
			corrected.delete();
		
		corrected.createNewFile();
		
		
		FileWriter fileWriter=null;
		
		fileWriter=new FileWriter(corrected);
		
		
		String currentLine="";
		String preLine="";
		
		//writing to file.
		while(fileScanner.hasNextLine())
		{
			currentLine=fileScanner.nextLine();
			
			if(DETAILED)
			System.out.println("|- "+status+" "+currentLine);
			
			if(status.equals("N_M")&&currentLine.equalsIgnoreCase(ABOVE_LINE))
			{
				if(DETAILED)
				System.out.println(" "+DOCTYPE_LINE);
					
				fileWriter.write(DOCTYPE_LINE+"\n");
				status="M";
				
			}else
				fileWriter.write(preLine+"\n");
			
			fileWriter.flush();
			preLine=currentLine;
		}
		
		fileWriter.write(preLine);
		fileWriter.flush();
		fileWriter.close();
		
		//Closing Scanner.
		fileScanner.close();
		
		//renaming temp.
		if(file.delete())
		{
			//System.out.println("\nDeleted");
			corrected.renameTo(file);
			return true;
		}else
			System.out.println("Can't Modify.");
			return false;
	}
	
	public static void main(String[] args) {
		HtmlCorrecter corredcter=new HtmlCorrecter();
		//corredcter.start();
		corredcter.correctFromFolder("H:\\www\\project\\Optician\\backup\\backup-05-03-2018\\opticianOnWeb\\web\\");
	}

}
