package scene;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Scene {

	public static BranchGroup createDefaultScene() {
		BranchGroup objRoot = new BranchGroup();

		Color3f directionalColor = new Color3f(1.0f, 1.0f, 1.0f);
		Color3f ambientColor = new Color3f(0.9f, 0.9f, 0.9f);
		Color3f backgroundColor = new Color3f(0.05f, 0.05f, 0.2f);
		DirectionalLight dl = new DirectionalLight(true, directionalColor,
		      new Vector3f(-1.0f, 0.0f, 0.0f));
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
		      100.0);
		dl.setInfluencingBounds(bounds);
		objRoot.addChild(dl);
		AmbientLight ambientLight = new AmbientLight(true, ambientColor);
		ambientLight.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLight);
		Background bg = new Background(backgroundColor);
		bg.setApplicationBounds(bounds);
		objRoot.addChild(bg);

		
		return objRoot;
	}
}
