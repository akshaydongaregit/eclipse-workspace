package algorithms;

import java.util.Scanner;

public class AssignmentP {
	
	private static final int tick = 1;
	static int ap[][]=new int[100][100];
	static int dap[][]=new int[100][100];
	static int aap[][]=new int[100][100];
	static final int Squared=1;
	static final int Crossed=2;
	static int tap[][]=new int[100][100];
	static final int ON_L=1;
	static final int ON_I=2;
	static int size;
	public static void main(String args[])
	{

		
		get();  //get data from user .
		initialize_aap();
		copyap();
		solve();
		
		cal_answer();
		/*
		1	2	6
	    6	4	1
	    9	3	7
		 */
		
		/*
		 5 2 6 2 8 8
         1 7 4 4 8 8
         5 0 3 0 8 8
         2 4 5 1 8 8
         3 6 4 5 8 8
         5 1 0 4 8 8
		 */
	}

	private static void cal_answer() {
		
		int answer=0;
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				if(aap[i][j]==Squared)
					answer+=dap[i][j];
			}
		System.out.print("\n+----------------------------------------+");
		System.out.println("\n+  Optimal solution is : "+answer);
		System.out.print("\n+----------------------------------------+");
		
	}

	static void get()
	{
		System.out.println("\nEnetr size : ");
		Scanner in=new Scanner(System.in);
		size=in.nextInt();
		System.out.print("\nEnter elemets : ");
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				ap[i][j]=in.nextInt();
		
		in.close(); //closing of resource in.
	}
	static void copyap()
	{
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				dap[i][j]=ap[i][j];
	}
	static void initialize_aap()
	{
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				aap[i][j]=0;
	}
	static void initialize_tap()
	{
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				tap[i][j]=0;
	}
	static void printap()
	{
		System.out.println("");
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
				System.out.print("\t"+ap[i][j]);
			System.out.println("");
		}
		
	}
	
	static void print_aap()
	{
		System.out.println("");
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
				if(aap[i][j]==Crossed)
			         System.out.print("\t*");
				else if(aap[i][j]==Squared)
					System.out.print("\t[]");
				else
					System.out.print("\t"+ap[i][j]);
			System.out.println("");
		}
	}
	static void solve()
	{
		System.out.print("\nSolving problem");
		System.out.print("\n initial ");
		printap();
		subCol(); //subtracts smaller value from columns.
		System.out.print("\nafter sub columns :");
		printap();
		subRow();   //subtracts smaller value from columns.
		System.out.print("\nafter sub rows :");
		printap();
		
		
		assign(); 
		while(!check_ap())
		{
			ticking_process();
		}
		
		print_aap();
		System.out.print("\nproblem solved");
	}
	private static void ticking_process() {
		System.out.println("\nApplying ticking process...");
	    
		initialize_tap();
		tick_ap();
		initialize_aap();
		assign(); //reassign zeros.
	}
    static void tick_ap()
    {
    	int t_rows[]=new int[size];
    	int t_cols[]=new int[size];
    	
    	for(int i=0;i<size;i++)
    	{
    		//this will check presence of squared zero in row.
    		boolean np=true;
    		for(int j=0;j<size;j++)
    		{
    			if(aap[i][j]==Squared)
    				np=false;
    			
    		}
    		
    		//take action if zero is not present.
    		if(np)
    		{
    			t_rows[i]=tick;
    			for(int c=0;c<size;c++)
    			{
    				if(aap[i][c]==Crossed)
    				{
    					for(int r=0;r<size;r++) //examine corrs. col.
    					{
    						if(aap[r][c]==Squared)
    						{
    							t_rows[r]=tick;
    							t_cols[c]=tick;
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	
    	//now pass this arrays to update ap;
    	update_tap(t_rows, t_cols);
    	print_tap();
    	update_ap();
    }
    static void print_tap()
    {
    	System.out.println("");
    	for(int i=0;i<size;i++)
    	{
    		for(int j=0;j<size;j++)
    			if(tap[i][j]==ON_L)
    				System.out.print("\t-");
    			else if(tap[i][j]==ON_I)
    				System.out.print("\t+");
    			else
    				System.out.print("\t"+ap[i][j]);
    		System.out.println("");
    	}
    }
    static void update_tap(int t_rows[],int t_cols[])
    {
    	/*System.out.println("\nrows : ");
 
    	for(int i=0;i<size;i++)
    		System.out.println("\t"+t_rows[i]);
     	
    	System.out.println("\nCols");
    	for(int i=0;i<size;i++)
    		System.out.println("\t"+t_cols[i]);
    	*/
    	
    	//check row first
    	for(int i=0;i<size;i++)
    		if(t_rows[i]!=tick)
    		{
    			for(int j=0;j<size;j++)
    			{
    				if(tap[i][j]==ON_L)
    					tap[i][j]=ON_I;
    				else
    					tap[i][j]=ON_L;
    			}
    		}
    	
    	//now check columns.
    	for(int j=0;j<size;j++)
    	{
    		if(t_cols[j]==tick)
    		{
    			for(int i=0;i<size;i++)
    			{
    				if(tap[i][j]==ON_L)
    					tap[i][j]=ON_I;
    				else
    					tap[i][j]=ON_L;
    			}
    		}
    	}
    }
    static void update_ap()
    {
    	int min=getmini();
    	//System.out.println("\nmin : "+min);
    	for(int i=0;i<size;i++)
    		for(int j=0;j<size;j++)
    		{
    			if(tap[i][j]==ON_L)
    				continue;
    			else if(tap[i][j]==ON_I)
    				ap[i][j]=ap[i][j]+min;
    			else if(aap[i][j]==Crossed)
    				continue;
    			else
    				ap[i][j]=ap[i][j]-min;
    		}
    }
    static int getmini()
    {
    	int min=0;
    	for(int i=0;i<size;i++)
    		for(int j=0;j<size;j++)
    		{
    			if(tap[i][j]!=ON_L && tap[i][j]!=ON_I && aap[i][j]!=Crossed)
    			{
    				if(min==0)
    					min=ap[i][j];
    				else if(ap[i][j]<min)
    					min=ap[i][j];
    			}
    		}
    	return min;
    }
	static void assign()
	{
		System.out.print("\n Assisnging zeros . . .");	
		
		int itr=0;
		int mode=0;
		
		//int it=0;
		while(itr<10)
		{
			System.out.println("\nitr "+itr+" mode "+mode);
			if(itr>2)
				mode=1;
			boolean erow=ExamineRow(mode);
		    while(erow)
		    	{
		    	   erow=ExamineRow(mode);
		    	}
		    boolean ecol=ExamineCol(0);
		    while(ecol)
		    {
		    	ecol=ExamineCol(0);	
		    }
		    itr++;
		    
		    if(mode==1)
		    	if(!erow && !ecol)
		    		break;
		    print_aap();
		}
		print_aap();
	}
	static boolean check_ap()
	{
		boolean ok=true;
		
		for(int i=0;i<size;i++)
		{
			boolean rok=false;
			for(int j=0;j<size;j++)
			{
				if(aap[i][j]==Squared)
					rok=true;
			}
			if(!rok)
				ok=false;
		}
		return ok;
	}
	static void subRow()
	{
		for(int i=0;i<size;i++)
		{
			//this will detect smaller number in row.
			int small=ap[i][0];
			for(int j=0;j<size;j++)
				if(ap[i][j]<small)
					small=ap[i][j];
			
			//this will subtracts smaller value from each elements of rows.
			for(int j=0;j<size;j++)
				ap[i][j]=ap[i][j]-small;
		}
	}
	static void subCol()
	{
		
		for(int j=0;j<size;j++)  // for column.
		{
			//this will detect smaller number.
			int small=ap[0][j];
			for(int i=0;i<size;i++)  //for rows.
			{
				if(ap[i][j]<small)
				     small=ap[i][j];
			}
			
			//this will subtract small value.
			for(int i=0;i<size;i++)
				ap[i][j]=ap[i][j]-small;
			
		}
	}
	static int getsmall(int num[])
	{
		int small=num[0];
		for(int i=0;i<num.length;i++)
			if(num[i]<small)
				small=num[i];
		
		System.out.println("\nsmall = "+small);
		return small;
	}
	static boolean ExamineRow(int m)
	{
		boolean assign=false;
		for(int i=0;i<size;i++)
		{
			//this loop will check no of zeros in row.
			int noz=0;
			int zeros[]=new int[size];
			
			//initialize zeros array.
			for(int n=0;n<size;n++)
				zeros[n]=0;
			
			//counts zeros in row.
			boolean sqr=false;
			for(int j=0;j<size;j++)
			{
				if(ap[i][j]==0)
					if(aap[i][j]==Squared)
					{
						sqr=true;
						break;
					}
					else if(aap[i][j]!=Crossed)
					{
						zeros[j]=1; 
					    noz++;
					}
			}
			
			if(sqr)
				continue;
			if(noz==1)  //if one zero present then square it.
			{
			   	for(int j=0;j<size;j++)
			   		if(zeros[j]==1)
			   			updateaap_rw(i,j);
			   	assign=true;
			}
			if(noz==2)  //if two zero presents.
			{
				if(m==1)
					for(int j=0;j<size;j++)
				   		if(zeros[j]==1)
				   		{
				   			updateaap_rw(i,j);
				   			assign=true;
				   			break;
				   		}
			}
			
			print_aap();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("\nreturning "+assign);
		return assign;
	}
	
	static boolean ExamineCol(int m)
	{
		boolean assign=false;
		for(int j=0;j<size;j++)
		{
			//this loop will check no of zeros in row.
			int noz=0;
			int zeros[]=new int[size];
			
			//initialize zeros array.
			for(int n=0;n<size;n++)
				zeros[n]=0;
			
			//counts zeros in row.
			for(int i=0;i<size;i++)
			{
				if(ap[i][j]==0)
				{
					if(aap[i][j]==Squared)
						continue;
					else if(aap[i][j]==Crossed)
						continue;
					else if(aap[i][j]!=Crossed)
					{
						zeros[i]=1; 
					    noz++;
					}
				}
			}
			
			if(noz==1)  //if one zero present then square it.
			{
			   	for(int i=0;i<size;i++)
			   		if(zeros[i]==1)
			   			updateaap_cw(i,j);
			   	assign=true;
			}
			
			
		}
		System.out.println("\n col returning "+assign);
		print_aap();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return assign;
	}
	static void updateaap_rw(int r,int c)
	{
		aap[r][c]=Squared;
		for(int i=0;i<size;i++)
		{
			if(ap[i][c]==0)
			{
				if(aap[i][c]!=Crossed && aap[i][c]!=Squared)
					aap[i][c]=Crossed;
			}
		}
	}
	static void updateaap_cw(int r,int c)
	{
		aap[r][c]=Squared;
		for(int j=0;j<size;j++)
		{
			if(ap[r][j]==0)
			{
				if(aap[r][j]!=Crossed && aap[r][j]!=Squared)
					aap[r][j]=Crossed;
			}
		}
	}

}
