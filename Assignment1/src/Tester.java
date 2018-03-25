
public class Tester
{

	public static void main(String[] args)
	{
		double a = 150;
		int shift = 3;
		double base = Math.log(a) / Math.log(2);
		double tempBase = base;
		int comp = (int) tempBase;
		for(shift = 0; comp != 0; shift++)
		{
			comp = (int)tempBase/10;
			tempBase = tempBase/10;
		}
		Real temp1 = new Real(19, 3, (float) 314519);
		Real temp2 = new Real(19, 5, (float)3.145);
		//System.out.printf("temp 1 = %f\n bits = %d\n shift = %d\n", temp1.getValue(), temp1.getNumberBits(), temp1.getShift());
		//System.out.printf("temp 2 = %f\n bits = %d\n shift = %d\n", temp2.getValue(), temp2.getNumberBits(), temp2.getShift());		
		try
		{
			Real temp3 = temp1.add(temp2);
			//System.out.printf("temp 3 = %f\n bits = %d\n shift = %d\n", temp3.getValue(), temp3.getNumberBits(), temp3.getShift());
		}
		catch(RuntimeException e)
		{
			//System.out.println(e);
		}
		
		RandomWalk rndwalk = new RandomWalk(3, 32 - shift, shift);
		RandomWalkSimulation sim = new RandomWalkSimulation(1000, 8);
		sim.simulate();
	}//end of main

}//end of tester class
