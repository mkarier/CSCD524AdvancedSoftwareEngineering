
public class Real 
{
	private int numberBits;
	private int shift;
	private float value;
	
	public Real(int numberBits, int shift, float value)
	{
		if((numberBits + shift) > 32)
			throw new RuntimeException("To many bits required");
		this.numberBits = numberBits;
		int power = 1;
		int curr_shift = 0; 
		while(value*power % 1.0 != 0.0 )
		{
			power = power * 10;
			curr_shift++;
		}//end of while loop
		if(getBits(curr_shift) > getBits(shift))
			this.shift = shift;
		else
			this.shift = curr_shift;
		
		if(getBits(value * Math.pow(10, this.shift)) > Math.pow(2, numberBits) -1)
			this. value = (float) (Math.pow(2, numberBits));
		else
			this.value = (float)Math.round( (value *Math.pow(10, this.shift)));
	}//end of constructor
	
	public int getNumberBits(){return numberBits;}//end of getNumberBits()
	public int getShift(){return shift;}//end of getShift();
	public float getValue(){return (float) (value / Math.pow(10, shift));}//end of getValue();
	
	private double getBits(float bit)
	{
		return (Math.log(bit)/Math.log(2))-1.0;
	}
	private double getBits(int bit)
	{
		return (Math.log(bit)/Math.log(2))-1.0;
	}
	private double getBits(double bit)
	{
		return (Math.log(bit)/Math.log(2))-1.0;
	}

	/*
	 * Takes a Real value and return  a new Real after performing addition
	 * @parm input a Real to add to the current Real object
	 */
	public Real add(Real input)
	{
		//TODO:
		if(this.numberBits == input.numberBits && this.shift == input.shift)
			return new Real(this.numberBits, this.shift, this.getValue() + input.getValue());
			throw new RuntimeException("Not the same configuration for addition");
		
	}//end of add
	
	/*
	 * takes a Real value and retuns a new Real after performing subtraction
	 * @parm Real input used to subtract to the current Real object
	 */
	public Real subtract(Real input)
	{
		//TODO:
		if(this.numberBits == input.numberBits && this.shift == input.shift)
			return new Real(this.numberBits, this.shift, this.getValue() - input.getValue());
		else
			throw new RuntimeException("Not the same configuration for subtraction");
		
	}//end of subtract();
	
	/*
	 * compare takes a Real and returns an integer -1, 0, +1
	 * based on comparing it to itself, where these values correspond to the argument being grater than, equal to, and less than the object
	 * respectively. Both arguments must have the same configuration. 
	 * @parm input a Real object to compare against itself
	 */
	int compare(Real input)
	{
		if(this.numberBits == input.numberBits && this.shift == input.shift)
		{
			if(this.getValue() > input.getValue())
				return 1;
			else if(this.getValue() < input.getValue())
				return -1;
			else
				return 0;
				
		}//end of if
		throw new RuntimeException("Not the same configuration for comparison");
	}
	

}//end of Real class
