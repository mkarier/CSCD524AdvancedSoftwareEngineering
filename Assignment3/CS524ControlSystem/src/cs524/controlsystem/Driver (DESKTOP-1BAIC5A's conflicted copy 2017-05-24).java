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
   public static void main(final String[] arguments)
   {
      Driver driver = new Driver();
      double xy[] = new double[]{-49,-9, -45, -23, -35, -35};
      double angle[] = new double[]{10, 27, 45};
      double speed[] = new double[]{1, 2, 3};
      for(int i = 0; i <=3; i ++)
      {
    	  for(int j = 0; j < 3; j++)
    	  {
    		  for(int k = 0; k < 3; k++)
    		  {
				  driver.runTest1("test" + (i+1) + ".gnu", -49, -9, i, 10);
				  driver.runTest2("test" + (i +2) + ".gnu", -49, -9, i, 10);
    		  }//end of for loop for angle
    	  }//end of speed
      }//and of xy
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

      // TODO: your task
      double targetY = 0;
      double agentY = agent.getY();
      if(agentY < 5 && agentY > -5)
    	  return;
      else if( agentY > 0)
    	  agent.setPitchDeltaTarget(-1);
      else
    	  agent.setPitchDeltaTarget(1);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Runs a sample test with the bang-bang controller.
    */
   private void runTest1(String filename, double x, double y, double speed, double pitch)
   {
      // set your iterations to whatever is necessary to achieve stability
		try
		{
			  int iterationCount = 5000;
			  BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			  // this defines the agent at position (0,-20) with pitch 45 degrees and speed 1, with instantaneous delta deflection
			  Agent agent = new Agent(x, y, speed, pitch, true);
		
			  out.write(agent.getStateGnuplot());
		
			  for (int iIteration = 0; iIteration < iterationCount; ++iIteration)
			  {
			     doControllerPID(agent);
		
			     agent.update_();
		
			     out.write(agent.getStateGnuplot());
			  }//end of for loop
			  out.close();
		}///end of catch
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
   }//end of runTest1
// ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Runs a sample test with the bang-bang controller.
    */
   
   private void runTest2(String filename, double x, double y, double speed, double pitch)
   {
      // set your iterations to whatever is necessary to achieve stability
	    try
		{
			  int iterationCount = 5000;
			  BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			  // this defines the agent at position (0,-20) with pitch 45 degrees and speed 1, with instantaneous delta deflection
			  Agent agent = new Agent(x, y, speed, pitch, false);
		
			  out.write(agent.getStateGnuplot());
		
			  for (int iIteration = 0; iIteration < iterationCount; ++iIteration)
			  {
			     doControllerBangBang(agent);
		
			     agent.update_();
		
			     out.write(agent.getStateGnuplot());
			  }//end of for loop
			  out.close();
		}///end of catch
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
   }//end of runTest2
}//end of driver class
