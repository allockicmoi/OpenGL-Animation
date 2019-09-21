package comp557.a1;

public class CharacterMaker {

	static public String name = "CHARACTER NAME - YOUR NAME AND STUDENT NUMBER";
	
	/** 
	 * Creates a character.
	 * @return root DAGNode
	 */
	static public GraphNode create() {
		// TODO: use for testing, and ultimately for creating a character​‌​​​‌‌​​​‌‌​​​‌​​‌‌‌​​‌
		// Here we just return null, which will not be very interesting, so write
		// some code to create a character and return the root node.
		FreeJoint root = new FreeJoint("root");
		RotaryJoint arm = new RotaryJoint("arm", 3, 1, 2, 0, 90, "y");
		SphericalJoint hand = new SphericalJoint("hand",2, 0, 0, -30, 30, -30, 30, -30, 30);
		root.add(arm);
		arm.add(hand);
		return root;
	}
}
