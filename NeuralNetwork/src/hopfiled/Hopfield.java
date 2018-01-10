package hopfiled;

import java.util.Scanner;
public class Hopfield {
	
	Scanner in;
	String cmd="";
	final int SIZE=4;
	int wm[][];
	Layer net;
	public Hopfield()
	{
		wm=new int[SIZE][SIZE];
		net=new Layer(SIZE);
		
		for(int i=0;i<SIZE;i++)
			for(int j=0;j<SIZE;j++)
				wm[i][j]=0;
	}
	public void start()
	{
		in =new Scanner(System.in);

		while(!cmd.equals("exit"))
		{
			System.out.print("\n--:");
			cmd=in.next();
			
			switch(cmd)
			{
			case "train": train();
				break;
			case "run":  run();
				break;
			case "clear":  clear();
				break;
			case "show":  show();
				break;
			}
		}
		
		in.close();
	}
	
	public void train()
	{
		//first take input.
		int input[]=new int[SIZE];
		System.out.print("\nEnter Input Pattern : ");
		for(int i=0;i<SIZE;i++)
			input[i]=in.nextInt();
		
		//then bias input.
		int bin[]=new int[SIZE];
		for(int i=0;i<SIZE;i++)
			if(input[i]==0)
				bin[i]=-1;
			else
				bin[i]=1;
		
		/*      calculate weight matrix for input pattern by 
				multiplying bin matrix with its transpose
		*/
		int cwm[][]=new int[SIZE][SIZE];
		for(int i=0;i<SIZE;i++)
			for(int j=0;j<SIZE;j++)
				cwm[i][j]=bin[i]*bin[j];
		
		//make diagonal elements zero.
		for(int i=0;i<SIZE;i++)
			cwm[i][i]=0;
		
		/*
		  		update weight matrix by 
		  		adding calculated weight to it
		 */
		for(int i=0;i<SIZE;i++)
			for(int j=0;j<SIZE;j++)
				wm[i][j]+=cwm[i][j];
		
		//now update network with updated weight matrix.
		net.update(wm);
		/* Now network is updated for given input pattern */
	}
	public void run()
	{
		//first take input
		int input[]=new int[SIZE];
		System.out.print("\nEnter Input Pattern : ");
		for(int i=0;i<SIZE;i++)
			input[i]=in.nextInt();
		
		//then convert it to boolean pattern.
		boolean bin[]=new boolean[SIZE];
		for(int i=0;i<SIZE;i++)
			if(input[i]==0)
				bin[i]=false;
			else
				bin[i]=true;
	
		//activate network
		net.activate(bin);
		
		//now get output;
		boolean output[]=net.output();
		
		//print output in form of 0 and 1.
		System.out.print("\nRecognized pattern : ");
		for(int i=0;i<SIZE;i++)
			if(output[i])
				System.out.print("\t"+1);
			else
				System.out.print("\t"+0);
		
	}
	public void clear()
	{
		for(int i=0;i<SIZE;i++)
			for(int j=0;j<SIZE;j++)
				wm[i][j]=0;
		
		net.update(wm);
		System.out.println("\nNet is Cleared");
		//show();
	}
	public void show()
	{
		System.out.print("\n");
		for(int i=0;i<SIZE;i++)
		{
			for(int j=0;j<SIZE;j++)
				System.out.print("\t"+wm[i][j]);
			System.out.print("\n");
		}
	}
	public static void main(String args[])
	{
		Hopfield hopfield=new Hopfield();
		hopfield.start();
	}

}
