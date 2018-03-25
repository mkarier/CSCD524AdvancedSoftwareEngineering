package cs524BlockBuilder;

public class tester
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Block a = new Block("a.myHull", 6, 4, 3, 0, 0, -1.5);
		Block.printXML(a, "testA.xml");
		Block.exportToGnuplot(a, new Triple(0,0,0));
		
		Block b = new Block("b.myHull", 6, 4, 3, 0, 0, -1.5);
		b.connectsTo(new Block("b.myTurret", 2, 2, 1, 0, 0, -0.5));
		b.addBall(new Triple(-1, 0, 1.5));
		Block.printXML(b, "testB.xml");//*/
		Block.exportToGnuplot(b, new Triple(0,0,0));
		
		Block c = new Block("c.myHull", new Triple(6, 4, 3), new Triple(0, 0, -1.5));
		Block cTurret = new Block("c.myTurret", 2, 2, 1, 0, 0, -0.5);
		Block cGun = new Block("c.myGun", 5, 0.5, 0.5, -2.5, 0, 0);
		c.connectsTo(cTurret);
		cTurret.connectsTo(cGun);
		c.addBall(-1, 0, 1.5);
		cTurret.addBall(1, 0, 0);
		Block.printXML(c, "testC.xml");
		Block.exportToGnuplot(c, new Triple(0,0,0));
	}//end of main

}//end of tester class
