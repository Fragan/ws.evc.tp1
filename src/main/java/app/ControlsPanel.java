package app;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton teleportToButton;
//	private JRadioButton moveRButton;
//	private JRadioButton rotateRButton;
	
	
	
	private JTextField tfCoordX;
	private JTextField tfCoordY;
	private JTextField tfCoordZ;
	
	public ControlsPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		teleportToButton = new JButton("TELEPORT TO");
//		moveRButton = new JRadioButton("MOVE");
//		rotateRButton = new JRadioButton("ROTATE");
		
		tfCoordX = new JTextField();
		tfCoordY = new JTextField();
		tfCoordZ = new JTextField();
		
		tfCoordX.setText("0");
		tfCoordY.setText("0");
		tfCoordZ.setText("0");
		
		
		tfCoordX.setSize(40, 20);
		tfCoordX.setPreferredSize(tfCoordX.getSize());
		tfCoordY.setSize(40, 20);
		tfCoordY.setPreferredSize(tfCoordY.getSize());
		tfCoordZ.setSize(40, 20);
		tfCoordZ.setPreferredSize(tfCoordZ.getSize());
		
		JPanel containerCoordPanel = new JPanel();
		JPanel coordPanel = new JPanel();
		coordPanel.setLayout(new BoxLayout(coordPanel, BoxLayout.X_AXIS));
		coordPanel.add(new JLabel("x : "));
		coordPanel.add(tfCoordX);
		coordPanel.add(new JLabel("y : "));
		coordPanel.add(tfCoordY);
		coordPanel.add(new JLabel("z : "));
		coordPanel.add(tfCoordZ);
		containerCoordPanel.add(coordPanel);
		
	
		add(teleportToButton);
//		add(moveRButton);
//		add(rotateRButton);
		add(containerCoordPanel);
	}

	public double getTfCoordX() {
		return Double.parseDouble(tfCoordX.getText());
	}

	public double getTfCoordY() {
		return Double.parseDouble(tfCoordY.getText());
	}

	public double getTfCoordZ() {
		return Double.parseDouble(tfCoordZ.getText());
	}

	public JButton getTeleportToButton() {
		return teleportToButton;
	}
}
