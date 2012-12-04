package interaction;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.picking.*;

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

	private int x1;
	private int x2;
	private int y1;
	private int y2;

	@Override
	public void initialize() {
		WakeupOnAWTEvent wAWTEvent1 = new WakeupOnAWTEvent(
				AWTEvent.MOUSE_EVENT_MASK);

		WakeupOnAWTEvent wAWTEvent2 = new WakeupOnAWTEvent(
				AWTEvent.MOUSE_MOTION_EVENT_MASK);

		WakeupCriterion[] conditions = { wAWTEvent1, wAWTEvent2 };

		wEvents = new WakeupOr(conditions);
		wakeupOn(wEvents);

		buttonsInUse = MouseEvent.NOBUTTON;
		button1Pressed = false;
		button2Pressed = false;
		button3Pressed = false;
	}

	@Override
	public void processStimulus(Enumeration criteria) {
		while (criteria.hasMoreElements()) {
			WakeupOnAWTEvent w = (WakeupOnAWTEvent) criteria.nextElement();
			AWTEvent events[] = w.getAWTEvent();
			for (int i = 0; i < events.length; ++i) {
				if (events[i].getID() == MouseEvent.MOUSE_PRESSED) {
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1)
						button1Pressed = true;
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2)
						button2Pressed = true;
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3)
						button3Pressed = true;
					if (buttonsInUse == MouseEvent.NOBUTTON) {
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
								e.printStackTrace();
							}
						}

					}
					buttonsInUse++;
				} else if (events[i].getID() == MouseEvent.MOUSE_RELEASED) {
					buttonsInUse--;
					if (buttonsInUse == 0)
						objectInInteraction = null;
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1)
						button1Pressed = false;
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2)
						button2Pressed = false;
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3)
						button3Pressed = false;
				} else if (events[i].getID() == MouseEvent.MOUSE_DRAGGED) {
					if (objectInInteraction == null) {
						double dx = 0, dy = 0, dz = 0;
						double dh = 0, dp = 0, dr = 0;

						x2 = ((MouseEvent) events[i]).getX();
						y2 = ((MouseEvent) events[i]).getY();

						if (button3Pressed) { // rotation
							dh = Math.PI * (x2 - x1) / PRECISION;
							dp = Math.PI * (y1 - y2) / PRECISION;
							dr = (dh - dp) / 2.0;
						}
						if (button2Pressed) { // zoom
							dz = (x1 - x2 + y2 - y1) / PRECISION;
						}
						if (button1Pressed) { // translation to screen
							dx = (x2 - x1) / PRECISION;
							dy = (y1 - y2) / PRECISION;
						}
						translate(dx, dy, dz);
						rotate(dh, dp, dr);
						x1 = x2;
						y1 = y2;
					}
				}
			}
		}
		wakeupOn(wEvents);
	}

	public MouseInteractor(BranchGroup branch, TransformGroup viewPoint) {
		this.branch = branch;
		this.viewPoint = viewPoint;
	}

	protected void rotate(double dh, double dp, double dr) {
		Transform3D oldT3D = new Transform3D();
		objectInInteraction.getTransform(oldT3D);

		Vector3d rotate = new Vector3d();
		rotate.set(dh, dp, dr);

		Transform3D localT3D = new Transform3D();
		localT3D.setEuler(rotate);

		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);

		objectInInteraction.setTransform(newT3D);
	}

	protected void translate(double dx, double dy, double dz) {
		Transform3D vpT3D = new Transform3D();
		viewPoint.getTransform(vpT3D);
		Transform3D vpT3Dinv = new Transform3D();
		vpT3Dinv.invert(vpT3D);

		Transform3D oldT3D = new Transform3D();
		objectInInteraction.getTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		Transform3D localDeltaT3D = new Transform3D();
		localDeltaT3D.setTranslation(translate);
		Transform3D absoluteDeltaT3D = new Transform3D();
		absoluteDeltaT3D.mul(vpT3D, localDeltaT3D);
		absoluteDeltaT3D.mul(vpT3Dinv);
		Transform3D newT3D = new Transform3D();
		newT3D.mul(absoluteDeltaT3D, oldT3D);
		objectInInteraction.setTransform(newT3D);
	}

}
