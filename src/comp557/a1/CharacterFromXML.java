package comp557.a1;
 		  	  				   
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Loads an articulated character hierarchy from an XML file. 
 */
public class CharacterFromXML {

	public static GraphNode load( String filename ) {
		try {
			InputStream inputStream = new FileInputStream(new File(filename));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			return createScene( null, document.getDocumentElement() ); // we don't check the name of the document elemnet
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load simulation input file.", e);
		}
	}
	
	/**
	 * Load a subtree from a XML node.
	 * Returns the root on the call where the parent is null, but otherwise
	 * all children are added as they are created and all other deeper recursive
	 * calls will return null.
	 */
	public static GraphNode createScene( GraphNode parent, Node dataNode ) {
        NodeList nodeList = dataNode.getChildNodes();
        for ( int i = 0; i < nodeList.getLength(); i++ ) {
            Node n = nodeList.item(i);
            // skip all text, just process the ELEMENT_NODEs
            if ( n.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = n.getNodeName();
            GraphNode node = null;
            if ( nodeName.equalsIgnoreCase( "node" ) ) {
            	node = CharacterFromXML.createJoint( n );
            } else if ( nodeName.equalsIgnoreCase( "geom" ) ) {        		
        		node = CharacterFromXML.createGeom( n ) ;            
            }
            // recurse to load any children of this node
            createScene( node, n );
            if ( parent == null ) {
            	// if no parent, we can only have one root... ignore other nodes at root level
            	return node;
            } else {
            	parent.add( node );
            }
        }
        return null;
	}
	
	/**​‌​​​‌‌​​​‌‌​​​‌​​‌‌‌​​‌
	 * Create a joint
	 * 
	 * TODO: Objective 5: Adapt commented code in createJoint() to create your joint nodes when loading from xml
	 */
	public static GraphNode createJoint( Node dataNode ) {
		String type = dataNode.getAttributes().getNamedItem("type").getNodeValue();
		String name = dataNode.getAttributes().getNamedItem("name").getNodeValue();
		Tuple3d t, f1,f2,f3,f4;
		if ( type.equals("free") ) {
			FreeJoint joint = new FreeJoint( name );
			return joint;
		} else if ( type.equals("spherical") ) {
			// position is optional (ignored if missing) but should probably be a required attribute!​‌​​​‌‌​​​‌‌​​​‌​​‌‌‌​​‌
			// Could add optional attributes for limits (to all joints)
			f1=getTuple3dAttr(dataNode,"xboundaries");
			f2=getTuple3dAttr(dataNode,"yboundaries");
			f3=getTuple3dAttr(dataNode,"zboundaries");
			f4=getTuple3dAttr(dataNode,"defs");

			SphericalJoint joint = new SphericalJoint( name, 2, 2, 2, f1.x, f1.y, f2.x, f2.y, f3.x, f3.y, f4.x, f4.y, f4.z );
			if ( (t=getTuple3dAttr(dataNode,"position")) != null ) joint.setPosition( t );			
			return joint;
			
		} else if ( type.equals("rotary") ) {
			// position and axis are required... passing null to set methods
			// likely to cause an execption (perhaps OK)
			String axis = dataNode.getAttributes().getNamedItem("axis").getNodeValue();
			f1=getTuple3dAttr(dataNode,"minmaxdef");
			RotaryJoint joint = new RotaryJoint( name, 0, 0, 0, f1.x, f1.y, axis, f1.z );
			joint.setPosition( getTuple3dAttr(dataNode,"position") );
			return joint;
			
		}
		return null;
	}

	/**
	 * Creates a geometry DAG node 
	 * 
	 * TODO: Objective 5: Adapt commented code in greatGeom to create your geometry nodes when loading from xml
	 */
	public static GraphNode createGeom( Node dataNode ) {
		String type = dataNode.getAttributes().getNamedItem("type").getNodeValue();
		String name = dataNode.getAttributes().getNamedItem("name").getNodeValue();
		Tuple3d t;
		if ( type.equals("dodeca" ) ) {
			Dodeca head = new Dodeca( name, 0, 0, 0, 0, 0, 0, 0 );
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) head.setCentre( t );
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) head.setScale( t );
			if ( (t=getTuple3dAttr(dataNode,"color")) != null ) head.setColor( t );
			return head;
		} else if ( type.equals( "cube" )) {
			Cube geom = new Cube( name, 0, 0, 0, 0, 0, 0, 3 );				
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) geom.setCentre( t );
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) geom.setScale( t );
			if ( (t=getTuple3dAttr(dataNode,"color")) != null ) geom.setColor( t );
			return geom;	
		}
		else if ( type.equals( "sphere" )) {
			double radius = Double.parseDouble(dataNode.getAttributes().getNamedItem("radius").getNodeValue());
			int slices = Integer.parseInt(dataNode.getAttributes().getNamedItem("slices").getNodeValue());
			int stacks = Integer.parseInt(dataNode.getAttributes().getNamedItem("stacks").getNodeValue());
			Sphere geom = new Sphere( name, 0, 0, 0, 0, 0, 0, radius, slices, stacks );				
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) geom.setCentre( t );
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) geom.setScale( t );
			if ( (t=getTuple3dAttr(dataNode,"color")) != null ) geom.setColor( t );
			return geom;	
		}
		else if ( type.equals( "neckcylinder" )) {
			double height = Double.parseDouble(dataNode.getAttributes().getNamedItem("height").getNodeValue());
			double radius = Double.parseDouble(dataNode.getAttributes().getNamedItem("radius").getNodeValue());
			int slices = Integer.parseInt(dataNode.getAttributes().getNamedItem("slices").getNodeValue());
			int stacks = Integer.parseInt(dataNode.getAttributes().getNamedItem("stacks").getNodeValue());
			NeckCylinder geom = new NeckCylinder( name, 0, 0, 0, 0, 0, 0, radius, slices, stacks,height );				
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) geom.setCentre( t );
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) geom.setScale( t );
			if ( (t=getTuple3dAttr(dataNode,"color")) != null ) geom.setColor( t );
			return geom;	
		}
		else if ( type.equals( "legcylinder" )) {
			double height = Double.parseDouble(dataNode.getAttributes().getNamedItem("height").getNodeValue());
			double radius = Double.parseDouble(dataNode.getAttributes().getNamedItem("radius").getNodeValue());
			int slices = Integer.parseInt(dataNode.getAttributes().getNamedItem("slices").getNodeValue());
			int stacks = Integer.parseInt(dataNode.getAttributes().getNamedItem("stacks").getNodeValue());
			LegCylinder geom = new LegCylinder( name, 0, 0, 0, 0, 0, 0, radius, slices, stacks,height );				
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) geom.setCentre( t );
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) geom.setScale( t );
			if ( (t=getTuple3dAttr(dataNode,"color")) != null ) geom.setColor( t );
			return geom;	
		}
		else if ( type.equals( "foot" )) {
			Foot geom = new Foot( name, 0, 0, 0, 0, 0, 0, 3 );				
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) geom.setCentre( t );
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) geom.setScale( t );
			if ( (t=getTuple3dAttr(dataNode,"color")) != null ) geom.setColor( t );
			return geom;}
		return null;		
	}
	
	/**
	 * Loads tuple3d attributes of the given name from the given node.
	 * @param dataNode
	 * @param attrName
	 * @return null if attribute not present
	 */
	public static Tuple3d getTuple3dAttr( Node dataNode, String attrName ) {
		Node attr = dataNode.getAttributes().getNamedItem( attrName);
		Vector3d tuple = null;
		if ( attr != null ) {
			Scanner s = new Scanner( attr.getNodeValue() );
			tuple = new Vector3d( s.nextDouble(), s.nextDouble(), s.nextDouble() );			
			s.close();
		}
		return tuple;
	}

}