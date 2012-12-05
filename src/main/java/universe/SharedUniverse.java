package universe;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import app.CanvasLoader;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class SharedUniverse extends SimpleUniverse {

	private TransformGroup camera;

	public SharedUniverse(TransformGroup transCamera) {
		super();
		this.camera = transCamera;
	}

	public SharedUniverse(CanvasLoader canvas) {
		super(canvas);
		getViewer().getView().setSceneAntialiasingEnable(true); //Yeah !!
		camera = canvas.getVpTrans();
	}

	/**
	 * Deplacement vers un point absolu de l'univers
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void cameraTeleportTo(double x, double y, double z) {

		Vector3d translate = new Vector3d();
		translate.set(x, y, z);
		
		Transform3D t3D = new Transform3D();
		
		camera.getTransform(t3D);
		
		// setTranslation est absolu a l'univers
		t3D.setTranslation(translate);
		camera.setTransform(t3D);
	}
	
	public void rotateTo(double h, double d, double r) {
		Vector3d rotate = new Vector3d();
		rotate.set(h, d, r);
		
		Transform3D t3D = new Transform3D();
		camera.getTransform(t3D);
		
		Vector3d translate = new Vector3d();
		
		t3D.get(translate);
		t3D.setEuler(rotate);
		t3D.setTranslation(translate);
		
		camera.setTransform(t3D);	
	}
	
	public void cameraRelativeTranslate(double dx, double dy, double dz) {
		Transform3D oldT3D =  new Transform3D();
		camera.getTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		
		Transform3D localT3D = new Transform3D();
		localT3D.setTranslation(translate);
		
		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		camera.setTransform(newT3D);	
		
	}
	
	public void cameraRelativeRotate(double dh, double dp, double dr) {
		Transform3D oldT3D =  new Transform3D();
		camera.getTransform(oldT3D);
		Vector3d rotate = new Vector3d();
		rotate.set(dh, dp, dr);
		
		Transform3D localT3D = new Transform3D();
		localT3D.setEuler(rotate);
		
		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		camera.setTransform(newT3D);	
		
	}
	
	public void objectRotate(TransformGroup objectInInteraction, double dh, double dp, double dr) {
		Transform3D oldT3D = new Transform3D();
		objectInInteraction.getTransform(oldT3D);

		Vector3d rotate = new Vector3d();
		rotate.set(dh, dp, dr);

		Transform3D localT3D = new Transform3D();
		localT3D.setEuler(rotate);

		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);

		objectInInteraction.setTransform(newT3D);
	}

	public void objectTranslate(TransformGroup objectInInteraction, double dx, double dy, double dz) {
		Transform3D vpT3D = new Transform3D();
		camera.getTransform(vpT3D);
		Transform3D vpT3Dinv = new Transform3D();
		vpT3Dinv.invert(vpT3D);

		Transform3D oldT3D = new Transform3D();
		objectInInteraction.getTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		Transform3D localDeltaT3D = new Transform3D();
		localDeltaT3D.setTranslation(translate);
		Transform3D absoluteDeltaT3D = new Transform3D();
		absoluteDeltaT3D.mul(vpT3D, localDeltaT3D);
		absoluteDeltaT3D.mul(vpT3Dinv);
		Transform3D newT3D = new Transform3D();
		newT3D.mul(absoluteDeltaT3D, oldT3D);
		objectInInteraction.setTransform(newT3D);
	}
	
	public void setTransCamera(TransformGroup transCamera) {
		this.camera = transCamera;
	}

}
