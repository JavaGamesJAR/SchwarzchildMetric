import JGJAR.libraries.MyMath.vectors.*;
import Constants.Constants;

public class MetricTensor
{
	public MetricTensor(double mass)
	{
		this.m = mass;
	}
	public final double m;
	public static final Vector4 e0 = new Vector4(1,0,0,0), 
								 e1 = new Vector4(0,1,0,0),
								 e2 = new Vector4(0,0,1,0),
								 e3 = new Vector4(0,0,0,1);
	public static final Vector4[] e = new Vector4[]{e0,e1,e2,e3};
	
	public double[][] g(Vector4 r)
	{
		double[][] res = new double[4][4];
		
		for(int i = 0; i<16; i++)
			res[i/4][i%4] = 0;
		
		/*res[0][0] = 1-2*Constants.G*m/(r.space().abs()*Constants.c2);
		res[1][1] = res[0][0]-2;*/
		double G = Constants.G*m/(2*r.space().abs()*Constants.c2);
		res[0][0] = (1-G)*(1-G)/((1+G)*(1+G));
		res[1][1] = -(1+G)*(1+G)*(1+G)*(1+G);
		res[2][2] = res[1][1];
		res[3][3] = res[1][1];
		
		return res;
		
	}
	private double[][] recipg(Vector4 r)
	{
		double[][] res = new double[4][4];
		
		for(int i = 0; i<16; i++)
			res[i/4][i%4] = 0;
		double G = Constants.G*m/(2*r.space().abs()*Constants.c2);
		/*res[0][0] = 1/(1-2*Constants.G*m/(r.space().abs()*Constants.c2));
		res[1][1] = 1/(-1-2*Constants.G*m/(r.space().abs()*Constants.c2));*/
		res[0][0] = (1+G)*(1+G)/((1-G)*(1-G));
		res[1][1] = -1/((1+G)*(1+G)*(1+G)*(1+G));
		res[2][2] = res[1][1];
		res[3][3] = res[1][1];
		
		return res;
		
	}
	public double[][] dg(Vector4 r, int rho, double dx)
	{
		double[][] res = new double[4][4];
		double[][] gp = g(Vector4.add(r, Vector4.mult(e[rho], dx)));
		double[][] g = g(Vector4.add(r, Vector4.mult(e[rho], -dx)));
		
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4; j++)
				res[i][j] = (gp[i][j]-g[i][j])/dx/2;
		return res;
		
	}
	public double[][][] dg(Vector4 r, double dx)
	{
		double[][][] res = new double[4][4][4];
		
		for(int i = 0; i<4; i++)
			res[i] = dg(r, i, dx);
		return res;
		
	}
	//^Lambda, _mu, _nu
	public double[][][] ChristoffelSymbol(Vector4 r, double dx)
	{
		double[][][] dg = dg(r, dx);
		double[][] recipg = recipg(r);
		double[][][] res = new double[4][4][4];
		for(int l = 0; l<4; l++)
			for(int m = 0; m<4; m++)
				for(int n = 0; n<4; n++)
				{
					res[l][m][n] = 0;
					for(int p = 0; p<4; p++)
						res[l][m][n]+=recipg[l][p]*(dg[n][p][m]+dg[m][p][n]-dg[p][m][n]);
					res[l][m][n] *=0.5;
					
				}
		
		return res;
		
	}

}
