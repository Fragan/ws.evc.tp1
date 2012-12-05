package interaction.keyboard;

import interaction.mouse.MouseInteractor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.CanvasLoader;

public class KeyCameraStateForMouseInteractor implements KeyListener {

	private MouseInteractor mi;
	private CanvasLoader cl;
	public KeyCameraStateForMouseInteractor(CanvasLoader cl, MouseInteractor mi) {
		this.cl = cl;
		this.mi = mi;		
	}

	public void keyTyped(KeyEvent e) {

	}


	public void keyPressed(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_C : 
			mi.switchCurrentState();
			cl.repaint();
			break;
		default :
	
		}

	}

}
