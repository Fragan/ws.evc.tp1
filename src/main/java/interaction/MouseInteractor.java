package interaction;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;

import universe.SharedUniverse;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;

public class MouseInteractor extends Behavior {

	public final double PRECISION = 40.0;

	private BranchGroup branch;
	private TransformGroup viewPoint;
	private WakeupOr wEvents;
	private int buttonsInUse;
	private boolean button1Pressed;
	private boolean button2Pressed;
	private boolean button3Pressed;
	private TransformGroup objectInInteraction;

	public static enum MODE {
		OBJECT, CAMERA
	};

	private MODE mode;

	private int x1;
	private int x2;
	private int y1;
	private int y2;

	private VirtualUniverse virtualUniverse;

	public MouseInteractor(VirtualUniverse virtualUniverse, BranchGroup branch, TransformGroup viewPoint) {
		this.mode = MODE.OBJECT;
		this.branch = branch;
		this.viewPoint = viewPoint;
		this.virtualUniverse = virtualUniverse;
	}

	public MODE getMode() {
		return mode;
	}

	public void setMode(MODE mode) {
		this.mode = mode;
	}

	@Override
	public void initialize() {
		WakeupOnAWTEvent wAWTEvent1 = new WakeupOnAWTEvent(
				AWTEvent.MOUSE_EVENT_MASK);

		WakeupOnAWTEvent wAWTEvent2 = new WakeupOnAWTEvent(
				AWTEvent.MOUSE_MOTION_EVENT_MASK);

		WakeupCriterion[] conditions = { wAWTEvent1, wAWTEvent2 };

		wEvents = new WakeupOr(conditions);
		wakeupOn(wEvents);

		buttonsInUse = 0;
		button1Pressed = false;
		button2Pressed = false;
		button3Pressed = false;
	}

	@SuppressWarnings("unchecked")
	public void processStimulus(Enumeration criteria) {
		if (mode == MODE.OBJECT) {
			objectProcessStimulus(criteria);
		} else if (mode == MODE.CAMERA) {
			cameraProcessStimulus(criteria);
		}
	}

	private void cameraProcessStimulus(Enumeration criteria) {
		while (criteria.hasMoreElements()) {
			WakeupOnAWTEvent w = (WakeupOnAWTEvent) criteria.nextElement();
			AWTEvent events[] = w.getAWTEvent();
			for (int i = 0; i < events.length; i++) {
				if (events[i].getID() == MouseEvent.MOUSE_PRESSED) {
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1) {
						button1Pressed = true;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2) {
						button2Pressed = true;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3) {
						button3Pressed = true;
					}
					if (buttonsInUse == 0) {						
						x1 = ((MouseEvent) events[i]).getX();
						y1 = ((MouseEvent) events[i]).getY();						
					}
					buttonsInUse++;
				} else if (events[i].getID() == MouseEvent.MOUSE_RELEASED) {
					buttonsInUse--;
					if (buttonsInUse == 0) {
						objectInInteraction = null;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1) {
						button1Pressed = false;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2) {
						button2Pressed = false;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3) {
						button3Pressed = false;
					}
				} else if (events[i].getID() == MouseEvent.MOUSE_DRAGGED) {
					if (objectInInteraction != null) {
						double dx = 0, dy = 0, dz = 0;
						double dh = 0, dp = 0, dr = 0;
						x2 = ((MouseEvent) events[i]).getX();
						y2 = ((MouseEvent) events[i]).getY();
						if (button1Pressed) { // rotation
							dh = Math.PI * (x2 - x1) / 40.0;
							dp = Math.PI * (y1 - y2) / 40.0;
							dr = (dh - dp) / 2.0;
						}
						if (button2Pressed) { // zoom
							dz = (x1 - x2 + y2 - y1) / 40.0;
						}
						if (button3Pressed) { // translation dans le plan de
												// l'écran
							dx = (x2 - x1) / 40.0;
							dy = (y1 - y2) / 40.0;
						}
						((SharedUniverse)virtualUniverse).cameraRelativeTranslate(dx, dy, dz);
						//((SharedUniverse)virtualUniverse).objectRotate(objectInInteraction, dh, dp, dr);
						x1 = x2;
						y1 = y2;
					}
				}
			}
		}
		wakeupOn(wEvents);
	}

	private void objectProcessStimulus(Enumeration criteria) {
		while (criteria.hasMoreElements()) {
			WakeupOnAWTEvent w = (WakeupOnAWTEvent) criteria.nextElement();
			AWTEvent events[] = w.getAWTEvent();
			for (int i = 0; i < events.length; i++) {
				if (events[i].getID() == MouseEvent.MOUSE_PRESSED) {
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1) {
						button1Pressed = true;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2) {
						button2Pressed = true;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3) {
						button3Pressed = true;
					}
					if (buttonsInUse == 0) {
						PickCanvas pickShape = new PickCanvas(
								(Canvas3D) events[i].getSource(), branch);
						pickShape.setShapeLocation((MouseEvent) events[i]);
						x1 = ((MouseEvent) events[i]).getX();
						y1 = ((MouseEvent) events[i]).getY();
						PickResult[] sgPath = pickShape.pickAllSorted();
						if (sgPath != null) {
							try {
								objectInInteraction = (TransformGroup) sgPath[0]
										.getNode(PickResult.TRANSFORM_GROUP);
							} catch (Exception e) {
								System.out.println(e);
							}
						}
					}
					buttonsInUse++;
				} else if (events[i].getID() == MouseEvent.MOUSE_RELEASED) {
					buttonsInUse--;
					if (buttonsInUse == 0) {
						objectInInteraction = null;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1) {
						button1Pressed = false;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2) {
						button2Pressed = false;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3) {
						button3Pressed = false;
					}
				} else if (events[i].getID() == MouseEvent.MOUSE_DRAGGED) {
					if (objectInInteraction != null) {
						double dx = 0, dy = 0, dz = 0;
						double dh = 0, dp = 0, dr = 0;
						x2 = ((MouseEvent) events[i]).getX();
						y2 = ((MouseEvent) events[i]).getY();
						if (button1Pressed) { // rotation
							dh = Math.PI * (x2 - x1) / 40.0;
							dp = Math.PI * (y1 - y2) / 40.0;
							dr = (dh - dp) / 2.0;
						}
						if (button2Pressed) { // zoom
							dz = (x1 - x2 + y2 - y1) / 40.0;
						}
						if (button3Pressed) { // translation dans le plan de
												// l'écran
							dx = (x2 - x1) / 40.0;
							dy = (y1 - y2) / 40.0;
						}
						((SharedUniverse)virtualUniverse).objectTranslate(objectInInteraction, dx, dy, dz);
						((SharedUniverse)virtualUniverse).objectRotate(objectInInteraction, dh, dp, dr);
						x1 = x2;
						y1 = y2;
					}
				}
			}
		}
		wakeupOn(wEvents);
	}

	

}
