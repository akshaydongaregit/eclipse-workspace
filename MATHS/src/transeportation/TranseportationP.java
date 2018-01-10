package transeportation;

import java.util.Scanner;

public class TranseportationP {

	int r,c;
	int tpa[][][]=new int[20][20][4];
	final int TPO=2;
	final int TPT=1;
	final int TPA=3;
	final int TPC=0;
	int tr;
	int tc;
	public void start()
	{
		gettpa();
		print_TPO();
		solve();
		/*
		19 30 50 10 7
		70 30 40 60 9
		40 8 70 20 18
		5 8 7 14 34
		 */
	}
	void gettpa()
	{
		Scanner in=new Scanner(System.in);
		out.print("\nEnter Size r c : ");
		r=in.nextInt();
		c=in.nextInt();
		tr=r+1;
		tc=c+1;
		out.println("\nEnter elements : ");
		for(int i=0;i<r;i++)
		{
			for(int j=0;j<c;j++)
			{
				tpa[i][j][TPO]=in.nextInt();
				tpa[i][j][TPT]=tpa[i][j][TPO];
				tpa[i][j][TPA]=tpa[i][j][TPO];
				tpa[i][j][TPC]=tpa[i][j][TPO];
			}
		}
		in.close();
	}
	void print_TPO()
	{
		System.out.println();
		for(int i=0;i<r;i++)
		{
			for(int j=0;j<c;j++)
			{
				out.print("\t"+tpa[i][j][TPO]);
			}
			out.println("");
		}
	}
	void print_TPC()
	{
		System.out.println();
		for(int i=0;i<tr;i++)
		{
			for(int j=0;j<tc;j++)
			{
				out.print("\t"+tpa[i][j][TPC]);
			}
			out.println("");
		}
	}
	void init_TPA()
	{
		for(int i=0;i<r;i++)
		{
			for(int j=0;j<c+1;j++)
			{
				tpa[i][j][TPC]=-1;;
			}
		}
	}
	void solve()
	{
		cal_diff();
		print_TPC();
		assign();
	}
	void assign()
	{
		/*
		  first find larger.
		 */
		int lr=0,lc=tc-1;
		for(int i=0;i<tr-2;i++)
		{
			if(tpa[i][lc][TPC]>tpa[lr][lc][TPC])
			{
				lr=i;
			}
		}
		
		int tlr=tr-1,tlc=0;
		for(int i=0;i<tc-2;i++)
		{
			if(tpa[tlr][i][TPC]>tpa[tlr][tlc][TPC])
			{
				tlc=i;
			}
		}
		//System.out.println(tpa[lr][lc][TPC]+" "+tpa[tlr][tlc][TPC]);
		if(tpa[tlr][tlc][TPC]>tpa[lr][lc][TPC])
		{
			lr=tlr;
			lc=tlc;
		}
		//System.out.println("\nlarger : "+tpa[lr][lc][TPC]);
		/*******************************************/
		/*
		 
		 */
		
	}
	void cal_diff()
	{
		
		for(int i=0;i<tr-2;i++)
		{
			int is1=0,is2=0;
			for(int j=0;j<tc-2;j++)
			{
				if(tpa[i][j][TPC]<tpa[i][is1][TPC])
				{
					is1=j;
				}
			}
			
			if(is1==0)
				is2=1;
				
			for(int j=0;j<tc-2;j++)
			{
				if(tpa[i][j][TPC]<=tpa[i][is2][TPC] && j!=is1)
				{
					is2=j;
				}
			}
			
			tpa[i][tc-1][TPC]=tpa[i][is2][TPC]-tpa[i][is1][TPC];
		}
		
		for(int j=0;j<tc-2;j++)
		{
			int is1=0,is2=0;
			for(int i=0;i<tr-2;i++)
			{
				if(tpa[i][j][TPC]<tpa[is1][j][TPC])
				{
					is1=i;
				}
			}
			
			if(is1==0)
				is2=1;
				
			for(int i=0;i<tr-2;i++)
			{
				
				if(tpa[i][j][TPC]<=tpa[is2][j][TPC] &&  i!=is1)
				{
					is2=i;
				}
			}
			//System.out.print("\nis1 : "+is1+" is2 : "+is2+"\ntpa[is2][j][TPC] "+tpa[is2][j][TPC]+" tpa[is1][j][TPC] "+tpa[is1][j][TPC]);
			tpa[tr-1][j][TPC]=tpa[is2][j][TPC]-tpa[is1][j][TPC];
		}
	}
	
	public static void main(String args[])
	{
		TranseportationP tp=new TranseportationP();
		tp.start();
	}
}
class out
{
	public static  void print(String string)
	{
		System.out.print(string);
	}
	public static  void println(String string)
	{
		System.out.println(string);
	}
}
