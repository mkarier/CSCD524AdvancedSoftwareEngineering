import java.util.Random;
public class RandomWalk
{
	private int steps;
	private int bits;
	private int shift;
	
	public RandomWalk(int steps, int bits, int shift)
	{
		this.steps = steps;
		this.bits = bits;
		this.shift = shift;
	}
	

	public int getSteps(){return steps;}
	public void setSteps(int steps){this.steps = steps;}
	public int getBits(){return bits;}
	public void setBits(int bits){this.bits = bits;}
	public int getShift(){return shift;}
	public void setShift(int shift){this.shift = shift;}
	
	private class Point
	{
		private float x;
		private float y;
		private float z;
		
		private Point(float x, float y, float z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}//end of constructor
	}//end of private point class
	
	/*
	 * start at position (0,0,0). The coordinate system is the standard mathematical one, but this information is irrelevant because
	 * we are interested in the total error over the path length, not specifically where we end up.
	 */
	public float execute(int seed)
	{
		/*
		 * TODO:Start at position (0,0,0). The coordinate system is the standard mathematical one, but this information is irrelevant because
			we are interested in the total error over the path length, not specifically where we end up. We are not visualizing this virtual movement.
			Iterate over the path length such that at every step, you generate random signed x, y, and z offsets from the current
			position, which then becomes the new position. Use Java's nextFloat() method uniformly distributed over
			[-1000000,+1000000].
			Maintain two simultaneous paths. The first uses Java's float datatype for the (x,y,z) position, whereas the second uses
			your Real. Use the same three random step triplet for each. At the end of execution, return the error between the final
			positions as a float Euclidean distance.
		 */
		
		int currentstep = 0;
		float fx = 0;
		float fy = 0;
		float fz = 0;
		Point curFPoint = new Point(fx, fy, fz);
		float fDistance = 0;
		Random rnd = new Random(seed);
		Real rx = new Real(this.bits, this.shift, fx);
		Real ry = new Real(this.bits, this.shift, fy);
		Real rz = new Real(this.bits, this.shift, fz);
		Point curRPoint = new Point(rx.getValue(), ry.getValue(), rz.getValue());
		float rDistance = 0;
		
		float error = 0;
		float offsetx = (float) ((rnd.nextFloat() * 2000000.0) - 1000000);
		float offsety = (float) ((rnd.nextFloat() * 2000000.0) - 1000000);
		float offsetz = (float) ((rnd.nextFloat() * 2000000.0) - 1000000);
		for(currentstep = 0; currentstep < this.steps; currentstep++)
		{
			fx += offsetx;
			fy += offsety;
			fz += offsetz;
			fDistance += calcDistance(curFPoint, fx, fy, fz);
			curFPoint = new Point(fx, fy, fz);
			rx = new Real(this.bits, this.shift, rx.getValue() + offsetx);
			ry = new Real(this.bits, this.shift, ry.getValue() + offsety);
			rz = new Real(this.bits, this.shift, rx.getValue() + offsetz);
			rDistance = calcDistance(curRPoint, rx.getValue(), ry.getValue(), rz.getValue());
			curRPoint = new Point(rx.getValue(), ry.getValue(), rz.getValue());
			offsetx = (float) ((rnd.nextFloat() * 2000000.0) - 1000000);
			offsety = (float) ((rnd.nextFloat() * 2000000.0) - 1000000);
			offsetz = (float) ((rnd.nextFloat() * 2000000.0) - 1000000);
			error += Math.abs(fDistance - rDistance);
		}//end of for loop
		
		return error;
	}//end of exectue
	
	private float calcDistance(Point point, float x, float y, float z)
	{
		return (float) Math.sqrt(Math.pow((point.x - x), 2) + Math.pow(point.y -y, 2) + Math.pow(point.z + z, 2));
	}//end of clacDistance

}//end of RandomWalk class


