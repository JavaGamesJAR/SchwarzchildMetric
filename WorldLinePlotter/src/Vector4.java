import JGJAR.libraries.MyMath.vectors.Vector;


public class Vector4 extends Vector
{
	public Vector4(double t, double x, double y, double z)
	{
		super(x, y, z);
		this.t = t;
		
	}
	public Vector4(Vector4 a)
	{
		super(a.x, a.y, a.z);
		this.t = a.t;
		
	}
	public double t;
	
	public double getCoord(int i)
	{
		return (i==0)?t:(i==1)?x:(i==2)?y:z;
		
	}
	
	public double abs2()
	{
		return t*t-x*x-y*y-z*z;
		
	}
	public Vector space()
	{
		return new Vector(x,y,z);
		
	}
	public static Vector4 add(Vector4 a, Vector4 b)
	{
		return new Vector4(a.t+b.t, a.x+b.x, a.y+b.y, a.z+b.z);
		
	}
	public static Vector4 sub(Vector4 a, Vector4 b)
	{
		return new Vector4(a.t-b.t, a.x-b.x, a.y-b.y, a.z-b.z);
		
	}
	public static Vector4 mult(Vector4 a, double b)
	{
		return new Vector4(a.t*b, a.x*b, a.y*b, a.z*b);
		
	}
	public static Vector4 convolve(Vector4 a, Vector4 b, double[][][] g)
	{
		Vector4 c = new Vector4(0,0,0,0);
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4; j++)
			{
				c.t += g[0][i][j]*a.getCoord(i)*b.getCoord(j);
				c.x += g[1][i][j]*a.getCoord(i)*b.getCoord(j);
				c.y += g[2][i][j]*a.getCoord(i)*b.getCoord(j);
				c.z += g[3][i][j]*a.getCoord(i)*b.getCoord(j);
				
			}
		return c;
	}
	public static double convolve(Vector4 a, Vector4 b, double[][] g)
	{
		double c = 0;
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4; j++)
				c+=g[i][j]*a.getCoord(i)*b.getCoord(j);
		return c;
	}
	public static double abs2(Vector4 a)
	{
		return a.abs2();
		
	}

}
