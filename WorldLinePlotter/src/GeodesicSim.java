import Constants.Constants;

public class GeodesicSim
{
	public GeodesicSim(Vector4 r0, Vector4 u0, MetricTensor g)
	{
		/*if(u0.abs2()!=Constants.c2&&u0.abs2()!=0)
			System.out.println("Sad");*/
		
		this.r = new Vector4(r0);
		this.u = new Vector4(u0);
		this.g = g;
		
	}
	Vector4 r, u;
	MetricTensor g;
	
	public void step(double dtau, double dx)
	{
		//Buffer
		Vector4 v = new Vector4(u);
		r = Vector4.add(r, Vector4.mult(u, dtau));
		
		double[][][] Gamma = g.ChristoffelSymbol(r, dx);
		
		Vector4 du = new Vector4(0,0,0,0);
		
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4; j++)
				du = Vector4.add(du, Vector4.mult(Vector4.convolve(u, u, Gamma), 1/Math.sqrt(Vector4.convolve(u, u, g.g(r)))));
		//System.out.println(Vector4.convolve(u, du, g.g(r)));
		du = Vector4.mult(du, -dtau);
		u = Vector4.add(u, du);
		
		
	}

}
