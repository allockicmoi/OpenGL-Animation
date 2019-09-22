package comp557.a1;

public class CharacterMaker {

	static public String name = "TECHNOCRAWLER - Julien Courbebaisse 260614548";
	
	/** 
	 * Creates a character.
	 * @return root DAGNode
	 */
	static public GraphNode create() {
		// TODO: use for testing, and ultimately for creating a character​‌​​​‌‌​​​‌‌​​​‌​​‌‌‌​​‌
		// Here we just return null, which will not be very interesting, so write
		// some code to create a character and return the root node.
		
		
//		FreeJoint root = new FreeJoint("root");
//		RotaryJoint arm = new RotaryJoint("arm", 3, 1, 2, 0, 90, "y");
//		SphericalJoint neck = new SphericalJoint("hand",2, 0, 0, -30, 30, -30, 30, -30, 30);	
//		HeadBox head = new HeadBox("head", 1.2, 0.65, 1, 1, 1, 1, 2);
//		root.add(arm);
//		arm.add(neck);
//		neck.add(head);
		
		GraphNode root =  CharacterFromXML.load("a1data\\character.xml");
		
		
		return root;
	}
}
