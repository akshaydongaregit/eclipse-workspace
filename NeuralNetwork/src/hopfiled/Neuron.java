package hopfiled;

public class Neuron {

	private int wv[];
	private int ncn;
	private int activation;
	public Neuron(int wv[])
	{
		this.wv=wv;
		ncn=wv.length;
	}
	
	public void activate(boolean p[])
	{
		activation=0;
		
		for(int i=0;i<ncn;i++)
			if(p[i])
				activation+=wv[i];
	}
	public int getactivation()
	{
		return activation;
	}
}
