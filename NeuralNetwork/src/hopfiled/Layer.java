package hopfiled;

public class Layer {

	private Neuron neurons[];
	private boolean output[];
	public Layer(int numn)
	{
		neurons=new Neuron[numn];
	}
	
	public void update(int wm[][])
	{
		
		for(int i=0;i<neurons.length;i++)
			neurons[i]=new Neuron(wm[i]);
	}
	
	
	public void activate(boolean p[])
	{
		for(int i=0;i<neurons.length;i++)
			neurons[i].activate(p);
	}
	
	private boolean threshold(int k)
	{
		double a=Math.exp(k);
		double b=Math.exp(-k);
		double tanh=(a-b)/(a+b);
		return (tanh>=0);
	}
	public boolean[] output()
	{
		output=new boolean[neurons.length];
		for(int i=0;i<neurons.length;i++)
		{
			output[i]=threshold(neurons[i].getactivation());
		}
		
		return output;
	}
}
