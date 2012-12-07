package app;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CameraControlsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton teleportToButton;
	
	
	
	private JSpinner spCoordX;
	private JSpinner spCoordY;
	private JSpinner spCoordZ;
	
	private JRadioButton rbNormalMCR;
	private JRadioButton rbSceneMCR;
	
	public CameraControlsPanel() {
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setSize(230, 300);
		setPreferredSize(getSize());
			
		/*
		 * TEXT INFO
		 */
		JPanel infoPanel = new JPanel();
		infoPanel.setSize(220, 50);
		infoPanel.setPreferredSize(infoPanel.getSize());
		infoPanel.setBorder(BorderFactory.createTitledBorder(""));
		//Use html to break the line
		JLabel infoLabel = new JLabel("<html>To regain focus and use the keyboard,<br/> first click on the canvas.</html>");
		infoPanel.add(infoLabel);
		
		/*
		 * MODES
		 */
		JPanel modesPanel = new JPanel();
		modesPanel.setSize(220, 100);
		modesPanel.setPreferredSize(modesPanel.getSize());
		modesPanel.setBorder(BorderFactory.createTitledBorder("Modes"));
		
		//Camera rotation scene ?
		ButtonGroup bgModeCameraRotation = new ButtonGroup();
		JPanel modesMCRPanel = new JPanel();
		modesMCRPanel.setLayout(new BoxLayout(modesMCRPanel, BoxLayout.X_AXIS));
		modesMCRPanel.setSize(210, 50);
		modesMCRPanel.setPreferredSize(modesMCRPanel.getSize());
		modesMCRPanel.setBorder(BorderFactory.createTitledBorder("Camera rotation mode"));
		rbNormalMCR = new JRadioButton("First person");		
		rbSceneMCR = new JRadioButton("Scene");
		bgModeCameraRotation.add(rbNormalMCR);
		bgModeCameraRotation.add(rbSceneMCR);
		modesMCRPanel.add(rbNormalMCR);
		modesMCRPanel.add(rbSceneMCR);
		rbNormalMCR.setSelected(true);
		modesPanel.add(modesMCRPanel);
				
		/*
		 * COORDINATES
		 */
		SpinnerNumberModel modelSpinner1 = new SpinnerNumberModel(0, -1000, 1000, 0.1); 
		SpinnerNumberModel modelSpinner2 = new SpinnerNumberModel(0, -1000, 1000, 0.1); 
		SpinnerNumberModel modelSpinner3 = new SpinnerNumberModel(0, -1000, 1000, 0.1); 
		spCoordX = new JSpinner(modelSpinner1);
		spCoordY = new JSpinner(modelSpinner2);
		spCoordZ = new JSpinner(modelSpinner3);		
		
		spCoordX.setSize(40, 20);
		spCoordX.setPreferredSize(spCoordX.getSize());
		spCoordY.setSize(40, 20);
		spCoordY.setPreferredSize(spCoordY.getSize());
		spCoordZ.setSize(40, 20);
		spCoordZ.setPreferredSize(spCoordZ.getSize());
		
		JPanel containerCoordPanel = new JPanel();
		containerCoordPanel.setSize(220, 70);
		containerCoordPanel.setPreferredSize(containerCoordPanel.getSize());
		JPanel coordPanel = new JPanel();
		coordPanel.setLayout(new BoxLayout(coordPanel, BoxLayout.X_AXIS));
		coordPanel.add(new JLabel("x : "));
		coordPanel.add(Box.createRigidArea(new Dimension(2, 10)));
		coordPanel.add(spCoordX);
		coordPanel.add(Box.createRigidArea(new Dimension(8, 10)));
		coordPanel.add(new JLabel("y : "));
		coordPanel.add(Box.createRigidArea(new Dimension(2, 10)));
		coordPanel.add(spCoordY);
		coordPanel.add(Box.createRigidArea(new Dimension(8, 10)));
		coordPanel.add(new JLabel("z : "));
		coordPanel.add(Box.createRigidArea(new Dimension(8, 10)));
		coordPanel.add(spCoordZ);		
		containerCoordPanel.setBorder(BorderFactory.createTitledBorder("Coordinates"));
		containerCoordPanel.add(coordPanel);
		
		/*
		 * ACTIONS
		 */
		teleportToButton = new JButton("TELEPORT TO");	
		JPanel controlsButtonsPanel = new JPanel();		
		controlsButtonsPanel.setSize(220, 80);
		controlsButtonsPanel.setPreferredSize(controlsButtonsPanel.getSize());
		controlsButtonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		controlsButtonsPanel.add(teleportToButton);
		
		/*
		 * ADD ALL
		 */
		add(infoPanel);
		add(modesPanel);
		add(containerCoordPanel);
		add(controlsButtonsPanel);		
	}

	public double getCoordX() {
		return (Double) spCoordX.getValue();
	}

	public double getCoordY() {
		return (Double) spCoordY.getValue();
	}

	public double getCoordZ() {
		return (Double) spCoordZ.getValue();
	}

	public JButton getTeleportToButton() {
		return teleportToButton;
	}

	public JRadioButton getRbNormalMCR() {
		return rbNormalMCR;
	}

	public JRadioButton getRbSceneMCR() {
		return rbSceneMCR;
	}
}
