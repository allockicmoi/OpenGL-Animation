package comp557.a1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class SphericalJoint extends GraphNode {
	
	DoubleParameter rx,ry,rz;
	double x,y,z;

	public SphericalJoint(String name,double tx,double ty,double tz, double xmin, double xmax
			, double ymin, double ymax, double zmin, double zmax) {
		super(name);
		dofs.add( rx = new DoubleParameter( name+" rx", 0, xmin, xmax ) );		
		dofs.add( ry = new DoubleParameter( name+" ry", 0, ymin, ymax ) );
		dofs.add( rz = new DoubleParameter( name+" rz", 0, zmin, zmax ) );
		x=tx;y=ty;z=tz;
	}
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslated(x, y, z);
		gl.glRotated(rx.getValue(), 1, 0, 0);
		gl.glRotated(ry.getValue(), 0, 1, 0);
		gl.glRotated(rz.getValue(), 0, 0, 1);
		
		super.display(drawable);
		gl.glPopMatrix();
	}
}
