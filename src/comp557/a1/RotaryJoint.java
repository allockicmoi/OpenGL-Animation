package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class RotaryJoint extends GraphNode {
	
	String axis;
	DoubleParameter rx,ry,rz;
	double x,y,z;
	

	public RotaryJoint(String name,double tx,double ty,double tz, double min, double max,String axis,double def) {
		super(name);
		this.axis=axis;
		if(axis.equals("x"))
		dofs.add(this.rx = (new DoubleParameter(name+"rx", def, min, max)));
		if(axis.equals("y"))
		dofs.add(this.ry = (new DoubleParameter(name+"ry", def, min, max)));
		if(axis.equals("z"))
		dofs.add(this.rz = (new DoubleParameter(name+"rz", def, min, max)));
		x=tx;y=ty;z=tz;
		
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslated(x, y, z);
		if(axis.equals("x"))
		gl.glRotated(rx.getValue(), 1, 0, 0);
		if(axis.equals("y"))
		gl.glRotated(ry.getValue(), 0, 1, 0);
		if(axis.equals("z"))
		gl.glRotated(rz.getValue(), 0, 0, 1);
		
		super.display(drawable);
		gl.glPopMatrix();
	}

	public void setPosition(Tuple3d t) {
		this.x=t.x;
		this.y=t.y;
		this.z=t.z;
		
	}

	

	
	
}
