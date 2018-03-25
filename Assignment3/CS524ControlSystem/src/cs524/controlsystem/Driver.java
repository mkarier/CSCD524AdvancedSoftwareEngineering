package cs524.controlsystem;
import java.io.*;
//=============================================================================================================================================================
/**
 * Defines the driver for the tests. You may change this however you want.
 * 
 * @author Dan Tappan
 */
public class Driver
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Runs the driver.
    * 
    * @param arguments - the are no command-line arguments
    */
	private java.util.ArrayList<Double> averages= new java.util.ArrayList<Double>();
	private java.util.ArrayList<Double> areaUnderTheCurve= new java.util.ArrayList<Double>();
	private java.util.ArrayList<Double> distance= new java.util.ArrayList<Double>();
   public static void main(final String[] arguments)
   {
      Driver driver = new Driver();

      //driver.runTest1();
      double xy[] = new double[]{-49,-9,-45,-23,-35,-35};
      double angle[] = new double[]{10,27,45};
      String angleType[] = new String[]{"shallow", "intermediate", "steep"};
      double speed[] = new double[]{1,2,3};
      String speedType[] = new String[]{"slow", "medium", "fast"};
      for(int angleIndex = 0; angleIndex < 3; angleIndex++)
      {
    	  for(int speedIndex = 0; speedIndex < 3; speedIndex++)
    	  {
    		  for(int x = 0, y = 1; x < 5 && y < 6; x += 2, y +=2)
    		  {
    			  String filename = "" + speedType[speedIndex]+angleType[angleIndex]+"true" +xy[x]+ xy[y];
    			  driver.runTest1(filename, xy[x], xy[y], speed[speedIndex], angle[angleIndex], true);
    			  String filename1 = "" + speedType[speedIndex]+angleType[angleIndex]+"false"+xy[x]+ xy[y];
    			  driver.runTest1(filename1, xy[x], xy[y], speed[speedIndex], angle[angleIndex], false);
    		  }
    	  }//end of for loop to increase speedIndex
      }//end of for loop to increase angle
      double mean = 0;
      double averageUnderCurve = 0;
      double averageDistance = 0;
      java.util.ListIterator<Double> it = driver.averages.listIterator();
      java.util.ListIterator<Double> areaIT = driver.areaUnderTheCurve.listIterator();
      java.util.ListIterator<Double> distanceIT = driver.distance.listIterator();
      while(it.hasNext())
      {
    	  mean += it.next();
    	  averageUnderCurve += areaIT.next();
    	  averageDistance += distanceIT.next();
      }//end of while loop
      System.out.println("Average area under curve = " + averageUnderCurve/ driver.areaUnderTheCurve.size());
      System.out.println("Average distance = " + averageDistance/driver.distance.size());
      it = driver.averages.listIterator();
      mean = mean / driver.averages.size();
      System.out.println("average of averages = " + mean);
      double means = 0;
      while(it.hasNext())
      {
    	  	means += Math.pow(it.next() - mean, 2);
      }//end of for loop to get standard deviation 
      means = means/ driver.averages.size();
      System.out.println("Standard Deviation = " + Math.sqrt(means));
   }//end of main

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * The constructor.
    */
   public Driver()
   {
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Adjusts the target pitch delta based on where the agent is with respect to the horizontal line at y=0. The agent should be aiming to be on the line. The
    * only element of the agent that may written to is setPitchDeltaTarget(). Anything may be read.
    * 
    * This solution defines a "bang-bang" controller with poor stability.
    * 
    * @param agent - the agent being controlled
    */
   private void doControllerBangBang(final Agent agent)
   {
      Assert.nonnull(agent);

      double targetY = 0;
      double agentY = agent.getY();
      System.out.println("agentY ; " + agentY);
      if (agentY < targetY)
      {
         agent.setPitchDeltaTarget(+1);
      }
      else
      {
         agent.setPitchDeltaTarget(-1);
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Adjusts the target pitch delta based on where the agent is with respect to the horizontal line at y=0. The agent should be aiming to be on the line. The
    * only element of the agent that may written to is setPitchDeltaTarget(). Anything may be read.
    * 
    * This solution will define a PID controller. The implementation is your choice, but it must be a PID controller.
    * 
    * @param agent - the agent being controlled
    */
   private void doControllerPID(final Agent agent)
   {
      Assert.nonnull(agent);

      //TODO: your task
      if(agent.getY() == 0)
    	  return;
      else if(agent.isProximate(agent.getX(), 0, 1))
      {
    	  if(agent.getY() > 0)
    		  agent.setPitchDeltaTarget(-1);
    	  else if(agent.getY() < 0)
    		  agent.setPitchDeltaTarget(+1);
      }
      else if(agent.isProximate(agent.getX(), 0, 2))
      {
    	  if(agent.getY() > 0)
    		  agent.setPitchDeltaTarget(-2);
    	  else if(agent.getY() < 0)
    		  agent.setPitchDeltaTarget(+2);
      }
      else if(agent.isProximate(agent.getX(), 0, 3))
      {
    	  if(agent.getY() > 0)
    		  agent.setPitchDeltaTarget(-3);
    	  else if(agent.getY() < 0)
    		  agent.setPitchDeltaTarget(+3);
      }
      else if(agent.isProximate(agent.getX(), 0, 4))
      {
    	  if(agent.getY() > 0)
    		  agent.setPitchDeltaTarget(-4);
    	  else if(agent.getY() < 0)
    		  agent.setPitchDeltaTarget(+4);
      }
      else if(agent.isProximate(agent.getX(), 0, 5))
      {
    	  if(agent.getY() > 0)
    		  agent.setPitchDeltaTarget(-5);
    	  else if(agent.getY() < 0)
    		  agent.setPitchDeltaTarget(+5);
      }
   }//end of doControllerPid

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Runs a sample test with the bang-bang controller.
    */
   private void runTest1(String filename, double x, double y, double speed, double angle, boolean deflection)
   {
      // set your iterations to whatever is necessary to achieve stability
      int iterationCount = 5000;
      double average = 0, dx = 0;
      int counter = 0; 
      double area = 0;
      double k1 =0, k2=0, k3=0, k4=0;
      
      try
      {
	      // this defines the agent at position (0,-20) with pitch 45 degrees and speed 1, with instantaneous delta deflection
	      BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".gnu"));
	      Agent agent = new Agent(x, y, speed, angle, deflection);
	      
	      writer.write(agent.getStateGnuplot() + "\n");
	      for (int iIteration = 0; iIteration < iterationCount; ++iIteration, dx++)
	      {
	    	   k1 = agent.getY();
	    	   k2=agent.getY()+dx/2*k1;
	    	   k3=agent.getY()+dx/2*k2;
	    	   k4=agent.getY()+dx*k3;
	    	   area += dx/6*(k1+2*k2+2*k3+k4);
	         //doControllerBangBang(agent);
	         doControllerPID(agent);
	         agent.update_();
	         
	         if(agent.getY() == 0)
	         {
	        	 System.out.println(filename + " countToStability = " + counter);
	        	 counter = 0;
	         }
	         else
	         {
	        	 counter++;
	         }
	         average += Math.abs(agent.getY());
	         writer.write(agent.getStateGnuplot() +"\n");
	      }//end of for loop
	      writer.close();
	      this.averages.add(Math.abs(average)/iterationCount);
	      System.out.println(filename + " average = " + Math.abs(average)/iterationCount);
	      System.out.println(filename + " distance = " +(agent.getX() - Math.abs(x)));
	      this.distance.add(agent.getX() - Math.abs(x));
	      System.out.println(filename + " steps in count = " + counter);
	      this.areaUnderTheCurve.add(area);
	      System.out.println(filename + " area under the curve =" + area);
	      System.out.println();
      }//end of try
      catch(IOException e)
      {
    	  System.out.println(e);
      }//end of catch
   }//end of runTest1
}
