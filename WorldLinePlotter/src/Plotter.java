import java.awt.*;

import Constants.Constants;

import javax.swing.*;

public class Plotter extends JPanel
{
	public Plotter()
	{
		super();
		
		MetricTensor g = new MetricTensor(1);
		
		
		double R0 = 300/*1.4*//*15*/;
		double phi = 0;
		double v = 0.2162/*0.216209423*//*0.237*/;
		Vector4 r0 = new Vector4(0, /*3.741*/R0*Math.cos(3.14/180*phi), R0*Math.sin(3.14/180*phi), 0);
		double[][] G = g.g(r0);
		//Massive
		Vector4 u0 = new Vector4(1/Math.sqrt(G[0][0]+G[1][1]*v*v), 0, v/Math.sqrt(G[0][0]+G[1][1]*v*v),0);
		//Massless
		//Vector4 u0 = new Vector4(Math.sqrt(-G[1][1]/G[0][0]), 0, 1, 0);
		
		//u0 = Vector4.mult(u0, 10);
		
		sim = new GeodesicSim(r0, u0, g);
		//setBackground(Color.black);
		
	}
	GeodesicSim sim;
	public static void main(String[] args)
	{
		Plotter panel = new Plotter();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setBounds(50, 50, 1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(true)
		{
				panel.sim.step(1E-3, 0.00001);
			panel.repaint();
			
		}
		
	}
	@Override
	public void paintComponent(Graphics g)
	{
		//SCALING FACTOR
		final double s = .1;
		final double st = 100;
		final int br = 5;
		double d = Constants.G*this.sim.g.m/Constants.c2;
		//super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
    	g2.setStroke(new BasicStroke(1));
        
    	g2.setColor(Color.BLACK);
    	g2.drawString("XY", 10, 10);
    	g2.drawString("XZ", 510, 10);
    	g2.drawString("YZ", 510, 510);
    	g2.drawString("XT,YT,ZT", 10, 510);
    	g2.drawLine(500, 0, 500, 1000);
    	g2.drawLine(0, 500, 1000, 500);
    	
        //xy
    	g2.setColor(Color.BLACK);
        g2.fillOval(250-(int)(d*s/2), 250-(int)(d*s/2), (int)(d*s), (int)(d*s));
        g2.setColor(Color.GREEN);
        //g2.setColor(getColor(0.25+15*(sim.u.space().abs()/sim.u.t-0.3)));
        g2.fillOval(250+(int)(s*sim.r.x-br/2), 250+(int)(s*sim.r.y-br/2), br, br);
        
        //xz
    	g2.setColor(Color.BLACK);
        g2.fillOval(500+250-(int)(d*s/2), 250-(int)(d*s/2), (int)(d*s), (int)(d*s));
        g2.setColor(Color.GREEN);
        //g2.setColor(getColor(0.25+15*(sim.u.space().abs()/sim.u.t-0.3)));
        g2.fillOval(500+250+(int)(s*sim.r.x-br/2), 250+(int)(s*sim.r.z-br/2), br, br);
        
        //yz
    	g2.setColor(Color.BLACK);
        g2.fillOval(500+250-(int)(d*s/2), 500+250-(int)(d*s/2), (int)(d*s), (int)(d*s));
        g2.setColor(Color.GREEN);
        //g2.setColor(getColor(0.25+15*(sim.u.space().abs()/sim.u.t-0.3)));
        g2.fillOval(500+250+(int)(s*sim.r.y-br/2), 500+250+(int)(s*sim.r.z-br/2), br, br);
        
        //rt
        g2.setStroke(new BasicStroke((int)(d*s)));
        g2.setColor(Color.BLACK);
        g2.drawLine(250, 500+(int)(d*s/2), 250, 1000);
        //g2.setColor(Color.GREEN);
        g2.setColor(getColor(0.25+15*(sim.u.space().abs()/sim.u.t-0.3)));
        g2.fillOval(250+(int)(s*sim.r.space().abs()-br/2), 500+(int)(s*sim.r.t/st-br/2), br, br);
        g2.fillOval(250+(int)(-s*sim.r.space().abs()-br/2), 500+(int)(s*sim.r.t/st-br/2), br, br);
        g2.setColor(Color.RED);
        g2.fillOval(250+(int)(s*sim.r.x-br/2), 500+(int)(s*sim.r.t/st-br/2), br, br);
        g2.setColor(Color.BLUE);
        g2.fillOval(250+(int)(s*sim.r.y-br/2), 500+(int)(s*sim.r.t/st-br/2), br, br);
        g2.setColor(Color.YELLOW);
        g2.fillOval(250+(int)(s*sim.r.z-br/2), 500+(int)(s*sim.r.t/st-br/2), br, br);
        
        //g2.drawString(""+Vector4.convolve(sim.u, sim.u, sim.g.g(sim.r)), 100, 100);
        g2.setColor(Color.WHITE);
        g2.fillRect(100, 0, 150, 10);
        g2.setColor(Color.BLACK);
        g2.drawString("v: "+sim.u.space().abs()/sim.u.t,100,10);
        g2.setColor(Color.WHITE);
        g2.fillRect(100, 10, 150, 20);
        g2.setColor(Color.BLACK);
        g2.drawString("u^2: "+Vector4.convolve(sim.u, sim.u, sim.g.g(sim.r)),100,20);
		
	}
	public Color getColor(double y)
    {
    	//return new Color((float)(12.75*y+97.5)/255f, (float)(-12.75*y+127.5)/255f, 0);
    	return Color.getHSBColor((float)(0.25+3*y/(4*HEIGHT)), 1, 1);
    	
    }
}
