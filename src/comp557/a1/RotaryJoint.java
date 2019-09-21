package comp557.a1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class RotaryJoint extends GraphNode {
	
	String axis;
	DoubleParameter rx,ry,rz;
	double x,y,z;
	

	public RotaryJoint(String name,double tx,double ty,double tz, double min, double max,String axis) {
		super(name);
		this.axis=axis;
		if(axis=="x")
		dofs.add(rx = (new DoubleParameter(name+"rx", 0, min, max)));
		if(axis=="y")
		dofs.add(ry = (new DoubleParameter(name+"ry", 0, min, max)));
		if(axis=="z")
		dofs.add(rz = (new DoubleParameter(name+"ry", 0, min, max)));
		x=tx;y=ty;z=tz;
		
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslated(x, y, z);
		if(axis=="x")
		gl.glRotated(rx.getValue(), 1, 0, 0);
		if(axis=="y")
		gl.glRotated(ry.getValue(), 0, 1, 0);
		if(axis=="z")
		gl.glRotated(ry.getValue(), 0, 0, 1);
		
		super.display(drawable);
		gl.glPopMatrix();
	}
}
