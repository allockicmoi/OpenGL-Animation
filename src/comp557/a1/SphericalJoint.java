package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class SphericalJoint extends GraphNode {
	
	DoubleParameter rx,ry,rz;
	double x,y,z;
	
	public SphericalJoint(String name,double tx,double ty,double tz, double xmin, double xmax
			, double ymin, double ymax, double zmin, double zmax, double xdef,double ydef,double zdef) {
		super(name);
		dofs.add( rx = new DoubleParameter( name+" rx", xdef, xmin, xmax ) );		
		dofs.add( ry = new DoubleParameter( name+" ry", ydef, ymin, ymax ) );
		dofs.add( rz = new DoubleParameter( name+" rz", zdef, zmin, zmax ) );
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
	public void setPosition(Tuple3d t) {
		this.x=t.x;
		this.y=t.y;
		this.z=t.z;
		
	}
}
