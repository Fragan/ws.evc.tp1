package app;

import interaction.KeyCameraInteractor;
import interaction.MouseInteractor;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Point3d;

import loader.GenericVrmlLoader;
import scene.Scene;
import universe.SharedUniverse;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class CanvasLoader extends Canvas3D {

	private static final long serialVersionUID = 1L;
	private GenericVrmlLoader gvl;
	private VirtualUniverse universe;
	private TransformGroup vpTrans;
	private BranchGroup scene;
	private MouseInteractor mouseInteractor;
	
	public CanvasLoader() {
		super(SimpleUniverse.getPreferredConfiguration());
		
		scene = Scene.createDefaultScene();
		
		gvl = new GenericVrmlLoader("samples/colorcube2.wrl");
		TransformGroup cube = gvl.load();
		TransformGroup cube2 = gvl.load();
		scene.addChild(cube);
		scene.addChild(cube2);
		
		universe = new SharedUniverse(this);
		((SharedUniverse) universe).getViewingPlatform().setNominalViewingTransform();
		
//		enableInteraction(scene, canvas);
		
		// creation d'un navigateur
		vpTrans = ((SharedUniverse) universe).getViewingPlatform().getViewPlatformTransform(); // noeud camera
		try {
			vpTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		}
		catch(Exception e) {}
		
		((SharedUniverse) universe).setTransCamera(vpTrans);
		
//		KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(vpTrans);
//		keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
//		scene.addChild(keyNavBeh);
		
		mouseInteractor = new MouseInteractor(universe, scene, vpTrans);
		
		
		
		mouseInteractor.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
		scene.addChild(mouseInteractor);

		
		scene.compile();
		((SharedUniverse) universe).addBranchGraph(scene);
		
	
	}
	
	
	public MouseInteractor getMouseInteractor() {
		return mouseInteractor;
	}


	public TransformGroup getVpTrans() {
		return vpTrans;
	}
	
	public VirtualUniverse getUniverse() {
		return universe;
	}
	
}
