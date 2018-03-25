import cs524task4.PerformanceMapper;
import java.util.*;
import java.io.*;
public class Tester
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		PerformanceMapper mapMassMystery_1 = new PerformanceMapper(PerformanceMapper.E_Data.MASS, PerformanceMapper.E_PerturbationMode.MYSTERY_1);
		PerformanceMapper mapVelocityMystery_1 = new PerformanceMapper(PerformanceMapper.E_Data.VELOCITY, PerformanceMapper.E_PerturbationMode.MYSTERY_1);
		PerformanceMapper mapDragMystery_1 = new PerformanceMapper(PerformanceMapper.E_Data.DRAG, PerformanceMapper.E_PerturbationMode.MYSTERY_1);
		PerformanceMapper mapStabilityMystery_1 = new PerformanceMapper(PerformanceMapper.E_Data.STABILITY, PerformanceMapper.E_PerturbationMode.MYSTERY_1);
		PerformanceMapper maps1[] = {mapMassMystery_1, mapVelocityMystery_1, mapDragMystery_1, mapStabilityMystery_1};
		String names1[] = {"mapMassMystery_1", "mapVelocityMystery_1", "mapDragMystery_1", "mapStabilityMystery_1"};
		runMultipleTests(names1, maps1);
		
		PerformanceMapper mapMassMystery_2 = new PerformanceMapper(PerformanceMapper.E_Data.MASS, PerformanceMapper.E_PerturbationMode.MYSTERY_2);
		PerformanceMapper mapVelocityMystery_2 = new PerformanceMapper(PerformanceMapper.E_Data.VELOCITY, PerformanceMapper.E_PerturbationMode.MYSTERY_2);
		PerformanceMapper mapDragMystery_2 = new PerformanceMapper(PerformanceMapper.E_Data.DRAG, PerformanceMapper.E_PerturbationMode.MYSTERY_2);
		PerformanceMapper mapStabilityMystery_2 = new PerformanceMapper(PerformanceMapper.E_Data.STABILITY, PerformanceMapper.E_PerturbationMode.MYSTERY_2);
		PerformanceMapper maps2[] = {mapMassMystery_2, mapVelocityMystery_2, mapDragMystery_2, mapStabilityMystery_2};
		String names2[] = {"mapMassMystery_2", "mapVelocityMystery_2", "mapDragMystery_2", "mapStabilityMystery_2"};
		runMultipleTests(names2, maps2);
		
		PerformanceMapper mapMassMystery_3 = new PerformanceMapper(PerformanceMapper.E_Data.MASS, PerformanceMapper.E_PerturbationMode.MYSTERY_3);
		PerformanceMapper mapVelocityMystery_3 = new PerformanceMapper(PerformanceMapper.E_Data.VELOCITY, PerformanceMapper.E_PerturbationMode.MYSTERY_3);
		PerformanceMapper mapDragMystery_3 = new PerformanceMapper(PerformanceMapper.E_Data.DRAG, PerformanceMapper.E_PerturbationMode.MYSTERY_3);
		PerformanceMapper mapStabilityMystery_3 = new PerformanceMapper(PerformanceMapper.E_Data.STABILITY, PerformanceMapper.E_PerturbationMode.MYSTERY_3);
		PerformanceMapper maps3[] = {mapMassMystery_3, mapVelocityMystery_3, mapDragMystery_3, mapStabilityMystery_3};
		String names3[] = {"mapMassMystery_3", "mapVelocityMystery_3", "mapDragMystery_3", "mapStabilityMystery_3"};
		runMultipleTests(names3, maps3);
		
	}//end of main
	
	private static void runMultipleTests(String[] names, PerformanceMapper maps[])
	{
		int sample = 1;
		while(sample != 999)
		{
			for(int i = 0; i < 4; i++)
			{
				runTest(names[i] + "sample" + sample, maps[i], sample);
			}
			System.out.println("finished running tests for: " + sample);
			sample = sample * 10;
			if(sample >= 1000)
				sample = 999;
		}//end of while loop
		for(int i = 0; i < 4; i++)
		{
			runTest(names[i] + "sample" + sample, maps[i], sample);
		}
		System.out.println("finished running tests for: " + sample);
	}//end of runMultipleTests
	
	private static void runTest(String filename, PerformanceMapper mapper, int sample)
	{
		try
		{
			BufferedWriter writerAv = new BufferedWriter(new FileWriter(filename+ "average.gnu"));
			BufferedWriter writerDev = new BufferedWriter(new FileWriter(filename+ "StdDev.gnu"));
			double distanceYards = 0;
			double[] runs = new double[sample];
			double stdDev = 0;
			double error = 0;
			for(distanceYards= 0; distanceYards < 1000 ; distanceYards++)
			{	
				
				for(int i = 0; i < sample; i++)
				{
					double run = mapper.map(distanceYards);
					error += run;
					runs[i] = run;
				}
				for(double r : runs)
				{
					stdDev += Math.pow(r - (error/sample), 2);
				}

				writerDev.write(distanceYards + " " + (Math.sqrt(stdDev)/sample) + "\n");
				writerAv.write(distanceYards + " " + (error/sample) + "\n");
				stdDev = 0;
			}//end of for loop
			writerDev.close();
			writerAv.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end of running the test

}//end of tester
