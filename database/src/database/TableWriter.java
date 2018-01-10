package database;

import java.util.Scanner;

public class TableWriter {
	
	static int colSize[][]=new int[10][2];
	private static int colcnt=0;
	static int Tsize=100;
	static String BrRow="";
	TableWriter()
	{
		colcnt=0;
	}
	public static void main(String args[])
	{
		TableWriter tbw=new TableWriter();
		
		tbw.addCol(30);
		tbw.addCol(10);
		tbw.addCol(15);
		tbw.addCol(20);
		tbw.printHeader(new String[]{" name"," marks"," address"," city"});
		tbw.printrow(new String[]{"Akshay Dongare","89","Polgaon","Ajara"});
		tbw.printrow(new String[]{"Anil Sutar","84","Vite","Kolhapur"});
		tbw.printBreakRow();
	}
	public void addCol(int Size)
	{
		if(colcnt==0)
		{
			colSize[colcnt][0]=0;
			colSize[colcnt][1]=Size;
		}
		else
		{
	        colSize[colcnt][0]=colSize[colcnt-1][1];
	        colSize[colcnt][1]=colSize[colcnt][0]+Size;
		}
	    colcnt++;
	    //System.out.println("\n added "+colcnt);
	}
	public  void printHeader(String[] values)
	{
		String br="";
		int ccnt=0;
		for(int i=0;i<Tsize;i++)
		{   
			if(ccnt<colcnt)
			{
				if(i==colSize[ccnt][1])
				{
					br+="+";
					ccnt++;
					continue;
				}
			}else
				break;
			
			br+="-";
			
		}
		
		System.out.print("\n+"+br);
		printrow(values);
		System.out.print("\n+"+br);
		BrRow=br;
	}
	public void printrow(String[] values)
	{
		String row="";
		int ccnt=0;
		//System.out.println("\n values 1");
		//System.out.println(values[0]);
		for(int i=0;i<Tsize;i++)
		{   
			if(ccnt<colcnt)
			{
				//System.out.print(" ccnt :"+ccnt+"colcnt :"+colcnt);
				if(i==colSize[ccnt][0])
				{
					if(colSize[ccnt][0]!=0)
					{
						row+="+";
						i++;
					}
					
					int l=colSize[ccnt][1]-(values[ccnt].length()+colSize[ccnt][0])-2;
					if(l<0)
					{
						row+=" "+values[ccnt].substring(0,values[ccnt].length()+l);
						i+=values[ccnt].length()+l;
						
					}else
					{
					    row+=" "+values[ccnt];
					    //System.out.println("\n i : "+i+" colSize["+ccnt+"][0] : "+colSize[ccnt][0]+" row : "+row);
					    i+=values[ccnt].length();
					}
					ccnt++;
					continue;
				}
			}else
			{
				//System.out.print(" checking at "+i);
				if(i==colSize[ccnt-1][1])
				{
					//System.out.print("breaking at "+i);
				   //System.out.print("\t "+i+" "+row+"|");
				  break;
				}
			}
			row+=" ";
			//System.out.print("\t "+i+" "+row+"|");
			
		}
		
		System.out.print("\n+"+row+"+");
	}
    public void printBreakRow()
    {
	  System.out.print("\n+"+BrRow);
    }
    
    public void ascii()
    {
    	int c=0;
    	Scanner in=new Scanner(System.in);
    	
    	do{
    		for(int i=c;i<c+10;i++)
    			System.out.printf("\t%c",i);
    		c+=10;
    	}while(in.nextInt()==1);
    	
    }
}
/*
  TableWriter tbw=new TableWriter();
  tbw.addCol(int ColSize);
  
 */
