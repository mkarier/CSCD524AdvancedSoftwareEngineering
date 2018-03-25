package f15cs349.task5.node.visitor;

/*
 * Defines the shared elements of any visitor that can communicate with the aircraft
 * structure
 */
public abstract class A_Bucket
{
	protected String contents;
	
	public A_Bucket()
	{
		this.contents = "";
	}//end of dvc
	
	/*
	 * adds a named real data feild according to the 
	 * format specifications.
	 */
	public void addField(String name, double value)
	{
		addField(name, value + "");
	}//end of addField(String name, double value)
	
	public abstract void addField(String name, double x, double y, double z);
	
	/*
	 * adds a named data field according
	 * to the format specifications
	 */
	public void addField(String name, int value)
	{
		addField(name, value + "");
	}// addField(String name, int value)
	
	/*
	 * TODO: adds a named string data field according to the format
	 * specifications
	 */
	public abstract void addField(String name, String value);
	
	/*
	 * TODO: starts the descrpition of a single component
	 */
	public abstract void beginComponent(String category, String id);
	/*
	 * TODO: starts the description of
	 * a List of component
	 */
	public abstract void beginComponentList(String category);
	
	/*
	 * TODO: Ends the description of a single
	 * component or a list of components
	 */
	public abstract void end();
	
	/*
	 * gets the output
	 * representation
	 */
	public String getContents()
	{
		end();
		return this.contents;
	}
}//end of A_Bucket class
