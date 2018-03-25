package f15cs349.task5.node.visitor;
import java.util.*;
public class BucketXML extends A_Bucket
{

	private java.util.List<String> categories = new ArrayList<String>();
	
	public BucketXML()
	{
		super();
		this.contents = "";
	}//BucketXML()

	public List<String>getCategories(){return this.categories;}
	public void addField(String name, String value)
	{
		this.contents = String.format("%s<%s>\n", this.contents, name);
		this.contents = String.format("%s%s\n",this.contents, value);
		this.contents = String.format("%s</%s>\n", this.contents, name);
	}//end of addField()

	public void beginComponent(String category, String id)
	{
		this.categories.add(category);
		this.contents = String.format("%s<%s identifier=\"%s\">\n",this.contents, category, id);
	}//end of beginComponent()
	
	public void beginComponentList(String category)
	{
		this.categories.add(category);
		this.contents = String.format("%s<%s>\n", this.contents, category);
	}//end of beginCompanentList()

	
	public void end()
	{
		if(this.categories.size() > 0)
		{
			this.contents = String.format("%s</%s>\n", this.contents, this.categories.remove((this.categories.size() -1)));
		}//end of outer if
	}//end of end()


	@Override
	public void addField(String name, double x, double y, double z)
	{
		this.contents = String.format("%s<%s>\n", this.contents, name);
		this.contents = String.format("%s<triple x=\"%f\"",this.contents, x);
		this.contents = String.format("%s y=\"%f\"",this.contents, y);
		this.contents = String.format("%s z=\"%f\"/>\n",this.contents, z);
		this.contents = String.format("%s</%s>\n", this.contents, name);
	}

}//end of BucketXML class
