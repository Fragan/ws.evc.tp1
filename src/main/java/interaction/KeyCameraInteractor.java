package interaction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyCameraInteractor implements KeyListener {

	private MouseInteractor mi;
	public KeyCameraInteractor(MouseInteractor mi) {
		this.mi = mi;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_C : 
				mi.setMode(MouseInteractor.MODE.CAMERA);
				System.out.println("Camera ON");
				break;
			default :
				mi.setMode(MouseInteractor.MODE.OBJECT);
				System.out.println("Object ON");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_C : 
			mi.setMode(MouseInteractor.MODE.OBJECT);
			System.out.println("Camera OFF");
			break;
		default :
			System.out.println("mettre un etat qui evolu à chaque appui sur la touche");
			// TODO mettre un etat qui evolu à chaque appui sur la touche
		}

	}

}
