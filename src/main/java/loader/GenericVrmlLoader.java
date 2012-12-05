package loader;

import java.io.FileNotFoundException;

import javax.media.j3d.TransformGroup;

import object.VirtualObject;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.Scene;

public class GenericVrmlLoader {
	
	
	private String fileName;

	public GenericVrmlLoader(String fileName) {
		this.fileName = fileName;
	}


	public VirtualObject load() {
		VirtualObject objTrans = new VirtualObject();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		VrmlLoader loader = new VrmlLoader();
		try {
			Scene scene = loader.load(fileName);
			objTrans.addChild(scene.getSceneGroup());
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return  objTrans;
	}


	
}