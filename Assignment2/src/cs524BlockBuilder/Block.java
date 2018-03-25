package cs524BlockBuilder;

import java.io.IOException;
import f15cs349.task5.node.visitor.*;

public class Block implements I_Visitable
{
		private Triple volume;
		private Triple socket;
		private String id;
		private Block connectedTo = null;
		private Triple ball = null;
		Block()
		{
			this.volume = null;
			this.socket = null;
			this.id = null;
		}
		Block(String id, double vx, double vy, double vz, double sx, double sy, double sz)
		{
			this.id = id;
			this.volume = new Triple(vx, vy, vz);
			this.socket = new Triple(sx, sy, sz);
		}//end of of constructor with out origin
		Block(String id, Triple volume, Triple socket)
		{
			this.id = id;
			this.volume = volume;
			this.socket = socket;
		}//end of a constructor
		
		void setID(String id){this.id = id;}//end of set id
		void setVolume(double x, double y, double z){this.volume = new Triple(x, y, z);}
		void steVolume(Triple volume){this.volume = volume;}
		void setSocket(double x, double y, double z){this.socket = new Triple(x, y, z);}
		void setSocket(Triple socket){this.socket = socket;}
		void addBall(double x, double y, double z){this.ball = new Triple(x, y, z);}
		void addBall(Triple ball){this.ball = ball;}
		void connectsTo(Block component){this.connectedTo = component;}
		Triple getVolume(){return this.volume;}
		Triple getSocket(){return this.socket;}
		Triple ball(){return this.ball;}
		String getID(){return this.id;}
		
		public static void printXML(Block a, String fileName)
		{
			try
			{
				java.io.FileWriter out = new java.io.FileWriter(fileName);
				java.io.BufferedWriter writer = new java.io.BufferedWriter(out);
				A_Bucket bucketXML = new BucketXML();
				a.visit_(bucketXML);
				writer.write(bucketXML.getContents());
				writer.close();
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//end of pritnXML
		
		public void visit_(A_Bucket bucket)
		{
			bucket.beginComponent("component", this.id);
			bucket.addField("size", this.volume.x, this.volume.y, this.volume.z);
			bucket.addField("socket", this.socket.x, this.socket.y, this.socket.z);
			if(this.connectedTo != null)
			{
				bucket.beginComponentList("connections");
				bucket.beginComponentList("mount");
				this.connectedTo.visit_(bucket);
				//bucket.end();
			}
			if(ball != null)
			{
				bucket.addField("ball", this.ball.x, this.ball.y, this.ball.z);
			}
			if(this.connectedTo != null)
			{
				bucket.end();
				bucket.end();
			}
			bucket.end();
			
		}

		
		public static void exportToGnuplot(Block b, Triple origin)
		{
			try
			{
				java.io.FileWriter out = new java.io.FileWriter(b.id + ".gnu");
				java.io.BufferedWriter writer = new java.io.BufferedWriter(out);
				BucketGNUPlot bucket = new BucketGNUPlot();
				b.visitGNU(bucket, origin);
				writer.write(bucket.getContents());
				writer.close();
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void visitGNU(BucketGNUPlot bucket, Triple origin)
		{
			bucket.beginComponent("component", this.id);

			bucket.addField("top", origin.x-calcXTop(), origin.y-calcYTop(), origin.z*2-calcZTop());
			bucket.addField("top", origin.x+calcXTop(), origin.y-calcYTop(), origin.z*2-calcZTop());
			bucket.addField("top", origin.x+calcXTop(), origin.y+calcYTop(), origin.z*2-calcZTop());
			bucket.addField("top", origin.x-calcXTop(), origin.y+calcYTop(), origin.z*2-calcZTop());
			bucket.addField("top", origin.x-calcXTop(), origin.y-calcYTop(), origin.z*2-calcZTop());
			bucket.endPoints();
			
			bucket.addField("bottom", origin.x +calcXbottom(), origin.y+calcYbottom(), origin.z*2+calcZbottom());
			bucket.addField("bottom", origin.x -calcXbottom(), origin.y+calcYbottom(), origin.z*2+calcZbottom());
			bucket.addField("bottom", origin.x -calcXbottom(), origin.y-calcYbottom(), origin.z*2+calcZbottom());
			bucket.addField("bottom", origin.x +calcXbottom(), origin.y-calcYbottom(), origin.z*2+calcZbottom());
			bucket.addField("bottom", origin.x +calcXbottom(), origin.y+calcYbottom(), origin.z*2+calcZbottom());
			bucket.endPoints();
			
			if(this.connectedTo != null)
			{
				bucket.beginComponentList("subcomponent");
				this.connectedTo.visitGNU(bucket, new Triple(this.ball.x + origin.x, this.ball.y + origin.y, this.ball.z+origin.z));
				bucket.endSubcomponents();
			}//end of if
			bucket.end();
		}
		
		private double calcXTop(){return (this.socket.x + this.volume.x/2.0);}
		
		private double calcYTop(){return this.socket.y + this.volume.y/2;}
		
		private double calcZTop(){return   this.socket.z - this.volume.z/2;}
		
		private double calcXbottom(){return this.socket.x - (this.volume.x/2.0);}
		
		private double calcYbottom(){return this.socket.y - (this.volume.y/2.0);}
		
	    private double calcZbottom(){return this.socket.z + (this.volume.z/2.0);}
		
}//end of class block
