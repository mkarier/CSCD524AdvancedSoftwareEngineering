public class Generate
{
   public static void main(final String[] args)
   {
      int combinationNum = 0;

      for (int iBitsValue = 0; iBitsValue <= 32; ++iBitsValue)
      {
         double bitsScaleMax = Math.ceil(Math.log(iBitsValue) / Math.log(2));

         for (int iBitsScale = 0; iBitsScale <= bitsScaleMax; ++iBitsScale)
         {
            int bitsTotal = (iBitsValue + iBitsScale);

            if (bitsTotal <= 32)
            {
               combinationNum++;

               System.out.println(combinationNum +", "+ bitsTotal +", "+ iBitsValue + ", " + iBitsScale);
            }
         }//end of for loop for bit total and scale

         System.out.println();
      }//end of for loop for total bits
   }//end of main
}//end of Generate class


