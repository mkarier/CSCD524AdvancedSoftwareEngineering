package f15cs349.task5.node.visitor;

import java.util.ArrayList;

public class BucketGNUPlot extends A_Bucket
{

	private java.util.List<String> categories = new ArrayList<String>();
	private java.util.List<String> points = new ArrayList<String>();
	
	public BucketGNUPlot()
	{
		super();
	}

	@Override
	public void addField(String name, double x, double y, double z)
	{
		if(!this.points.contains(name))
		{
			this.points.add(name);
			this.contents = String.format("%s# %s\n %2.2f %2.2f %2.2f\n", this.contents, name, x, y, z);
		}
		else
		{
			this.contents = String.format("%s%2.2f %2.2f %2.2f\n", this.contents, x, y, z);
		}

	}

	@Override
	public void addField(String name, String value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void beginComponent(String category, String id)
	{
		
		this.categories.add(id);
		this.contents = String.format("%s# component [%s] {\n",this.contents, id);
	}

	@Override
	public void beginComponentList(String category)
	{
		// TODO Auto-generated method stub
		this.categories.add(category);
		this.contents = String.format("%s# subcomponents {\n", this.contents);

	}

	@Override
	public void end()
	{
		if(this.categories.size() > 0)
		{
			this.contents = String.format("%s# } component [%s]\n", this.contents, this.categories.remove((this.categories.size() -1)));
		}//end of outer if

	}
	
	public void endSubcomponents()
	{
		if(this.categories.size() > 0)
		{
			this.contents = String.format("%s# } subcomponents\n", this.contents);
			this.categories.remove(this.categories.size()-1);
		}
	}
	
	public void endPoints()
	{
		if(points.size() > 0)
		{
			this.points.remove(this.points.size()-1);
		}
	}

}
