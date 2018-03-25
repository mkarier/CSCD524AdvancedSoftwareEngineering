
import java.util.Random;
import java.io.*;
public class RandomWalkSimulation
{
	private int iteration;
	private int seed;
	
	public RandomWalkSimulation(int iteration, int seed)
	{
		this.iteration = iteration;
		this.seed = seed;
	}
	

	/*
	 * This method executes the iterations on independent instances of RandomWalk and keeps track of the errors
	 */
	@SuppressWarnings("resource")
	public void simulate()
	{
		float average = 0;
		float count = 0;
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;
		float stdDev = 0;
		float avgAvg[] = new float[146];
		PrintWriter avgOut;
		PrintWriter devOut;
		try
		{
			avgOut = new PrintWriter("avgOut.gnu");
			devOut = new PrintWriter("devOut.gnu");
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		for(int steps = 1, index = 0; steps <= 100; steps = steps *10, index++)
		{
			avgOut.println("reset");
			avgOut.println("set title 'Path Length " + steps + "'");
			avgOut.println("set xlabel 'Shift Bits'");
			avgOut.println("set ylabel 'Value Bits'");
			avgOut.println("set zlabel 'Error'");
			avgOut.println("splot '-' title 'Average Error' with lines, '-' title 'Standard Deviation' with lines");
			avgOut.println("# error-avg x,y->z");
			devOut.println("# error-std x,y->z");
			for (int bitsValue = 0; bitsValue <= 32; ++bitsValue)
		      {
		         double maxBits = Math.ceil(Math.log(bitsValue) / Math.log(2));
		         for (int bitsScale = 0; bitsScale <= maxBits; ++bitsScale)
		         {
		            int bitsTotal = (bitsValue + bitsScale);
		            if (bitsTotal <= 32)
		            {
		            	for(int i = 0; i < this.iteration; i++)
		            	{
		            		RandomWalk rndwalk = new RandomWalk(steps, bitsValue, bitsScale);
		            		float error = rndwalk.execute(this.seed);
		            		if(error > max)
		            			max = error;
		            		if(error < min)
		            			min = error;
		            		average += error;
		            		count++;
		            		stdDev += Math.pow(error - (average/count), 2);
		            		
		            	}//end of inner for loop to run a thousand times.
		            	
		            	avgOut.print(bitsValue +" " + bitsScale);
		            	avgOut.printf(" %10.3f\n", average/count);
		            	devOut.print(bitsValue + " " + bitsScale);
		            	devOut.printf(" %10.3f\n", Math.sqrt(stdDev));
		            	//avgAvg[]
		            }//end of if statment to make sure that the scale doesn't over run the bits
		            
		         }//end of for loop for bit total and scale
		         //System.out.println();
		      }//end of for loop for total bits
			avgAvg[index] = average/ count;
		    //System.out.println("average error for "+ steps + " steps =" + (average /count));
			
		    average = 0;
		    count = 0;
		}//for loops with the steps
		float trueAverage = (avgAvg[0] + avgAvg[1] + avgAvg[2]) / 3;
		stdDev = (float)((Math.pow(avgAvg[0] - trueAverage, 2) + Math.pow(avgAvg[1] - trueAverage, 2) + Math.pow(avgAvg[2]- trueAverage, 2))/ 3.0);
		avgOut.println("EOF");
		devOut.println("EOF");
		avgOut.close();
		devOut.close();
	    //System.out.println("Max error = " + max);
	    //System.out.println("Min error = " + min);
	    //System.out.println("Standard Deviation" + Math.sqrt(stdDev));
	}//end of simulate method
	
	private float standardDev(Float[] array)
	{
		float average = 0;
		for(int i = 0; i < array.length; i++)
			average += array[i];
		float trueAvg = average/array.length;
		average = 0;
		for(int i = 0; i < array.length; i++)
			average += Math.pow(array[i] - trueAvg, 2);
		return (float) Math.sqrt(average / array.length);
	}//end of standardDev method

}//end of RandomWalkSimulatoin

