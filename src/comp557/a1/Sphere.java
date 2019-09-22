package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Sphere extends GraphNode {
	
	//position
	double x,y,z;
	//scaling
	double scale_x,scale_y,scale_z;
	
	float red,green,blue;
	private double radius;
	private int slices;
	private int stacks;
	
	public Sphere(String name,double scale_x,double scale_y,double scale_z,double x, double y, double z,double radius,int slices,int stacks) {
		super(name);
		this.x=x;
		this.y=y;
		this.z=z;
		this.scale_x=scale_x;
		this.scale_y=scale_y;
		this.scale_z=scale_z;
		this.radius=radius;
		this.slices=slices;
		this.stacks=stacks;
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslated(x, y, z);
		gl.glScaled(scale_x, scale_y, scale_z);
		gl.glEnable(gl.GL_COLOR_MATERIAL);
		gl.glColor4f(red,green,blue,1.0f);
		glut.glutSolidSphere(radius, slices, stacks);;
		
		super.display(drawable);
		gl.glPopMatrix();
		
	}

	public void setColor(Tuple3d t) {
		this.red=(float) t.x;
		this.green=(float) t.y;
		this.blue=(float)t.z;
		
	}

	public void setScale(Tuple3d t) {
		this.scale_x = t.x;
		this.scale_y= t.y;
		this.scale_z = t.z;
		
	}

	public void setCentre(Tuple3d t) {
		this.x=t.x;
		this.y=t.y;
		this.z=t.z;
		
	}
}
