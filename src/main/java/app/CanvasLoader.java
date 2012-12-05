package app;

import interaction.keyboard.KeyCameraStateForMouseInteractor;
import interaction.mouse.MouseInteractor;
import interaction.mouse.MouseStimulusCamera;
import interaction.mouse.MouseStimulusObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.J3DGraphics2D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import loader.GenericVrmlLoader;
import scene.Scene;
import universe.SharedUniverse;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class CanvasLoader extends Canvas3D {

	private static final long serialVersionUID = 1L;
	private GenericVrmlLoader gvl;
	private SharedUniverse universe;
	private TransformGroup camera;
	private BranchGroup scene;
	private MouseInteractor mouseInteractor;

	public CanvasLoader() {
		super(SimpleUniverse.getPreferredConfiguration());
		setDoubleBufferEnable(true);
		
		//Create the scene
		scene = Scene.createDefaultScene();

		//Load a vrml model
		gvl = new GenericVrmlLoader("samples/colorcube2.wrl");
		TransformGroup cube = gvl.load();
		TransformGroup cube2 = gvl.load();
		
		//Add object into scene
		scene.addChild(cube);
		scene.addChild(cube2);

		//Create a universe
		universe = new SharedUniverse(this);
		universe.getViewingPlatform().setNominalViewingTransform();

		//Create a user camera
		camera = universe.getViewingPlatform().getViewPlatformTransform(); 
		try {
			camera.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		} catch (Exception e) {
		}

		//Assign camera to universe
		universe.setTransCamera(camera);

		//Add a mouse interactor to the scene
		mouseInteractor = new MouseInteractor(universe, scene, camera);
		mouseInteractor.setSchedulingBounds(new BoundingSphere(new Point3d(),
				1000.0));
		scene.addChild(mouseInteractor);

		//Add a keylistener to the canvas
		addKeyListener(new KeyCameraStateForMouseInteractor(this, getMouseInteractor()));
	
		//Compile the scene
		scene.compile();
		
		//Add the scene to the universe
		universe.addBranchGraph(scene);
		
		//Finally, taking a step back
		universe.cameraRelativeTranslate(0, 0, 5.0); 
	
		
	}


	public MouseInteractor getMouseInteractor() {
		return mouseInteractor;
	}

	public TransformGroup getVpTrans() {
		return camera;
	}

	public SharedUniverse getUniverse() {
		return universe;
	}

	
	
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		super.update(g);
	}


	@Override
	public void postRender() {	
		super.postRender();
		J3DGraphics2D g = getGraphics2D();
		Font myFont = new Font("Courier", Font.BOLD ,16);
  
		g.setFont(myFont);
		g.setColor(Color.WHITE);
		if (getMouseInteractor().getCurrentState() instanceof MouseStimulusObject)
			g.drawString("MODE OBJECT ON", 13, 17);
		else if (getMouseInteractor().getCurrentState() instanceof MouseStimulusCamera)
			g.drawString("MODE CAMERA ON", 13,17);
		else
			g.drawString("UNKNOWN MODE", 13, 17);
		g.flush(false);
		g.dispose();
	}

	

}
