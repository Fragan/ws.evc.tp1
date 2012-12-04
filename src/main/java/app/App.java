package app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import universe.SharedUniverse;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private CanvasLoader canvas;
	private ControlsPanel cp;


	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		cp = new ControlsPanel();
		canvas = new CanvasLoader();
		
		
		cp.getTeleportToButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((SharedUniverse) canvas.getUniverse()).cameraTeleportTo(cp.getTfCoordX(), cp.getTfCoordY(), cp.getTfCoordZ()); 
			}
		});
		
		
		add(cp, BorderLayout.WEST);
		add(canvas, BorderLayout.CENTER);
		
		setSize(800, 600);
		setPreferredSize(getSize());
		
		pack();
	}
	


	public static void main(String[] args) {
		App app = new App();
		app.setVisible(true);
	}

}
