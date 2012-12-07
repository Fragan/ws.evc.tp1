package interaction.mouse;

import interaction.mouse.MouseInteractor.MouseInteractorData;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Enumeration;

import javax.media.j3d.WakeupOnAWTEvent;

public class MouseStimulusCamera extends AMouseStimulusState {

	
	public MouseStimulusCamera(MouseInteractorData msd) {
		super(msd);
	}

	@Override
	public void stimulus(@SuppressWarnings("rawtypes") Enumeration criteria) {
		while (criteria.hasMoreElements()) {
			WakeupOnAWTEvent w = (WakeupOnAWTEvent) criteria.nextElement();
			AWTEvent events[] = w.getAWTEvent();
			for (int i = 0; i < events.length; i++) {
				if (events[i].getID() == MouseEvent.MOUSE_PRESSED) {
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1) {
						msd.button1Pressed = true;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2) {
						msd.button2Pressed = true;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3) {
						msd.button3Pressed = true;
					}
					if (msd.buttonsInUse == 0) {
						msd.x1 = ((MouseEvent) events[i]).getX();
						msd.y1 = ((MouseEvent) events[i]).getY();
					}
					msd.buttonsInUse++;
				} else if (events[i].getID() == MouseEvent.MOUSE_RELEASED) {
					msd.buttonsInUse--;
					if (msd.buttonsInUse == 0) {
						msd.objectInInteraction = null;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON1) {
						msd.button1Pressed = false;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON2) {
						msd.button2Pressed = false;
					}
					if (((MouseEvent) events[i]).getButton() == MouseEvent.BUTTON3) {
						msd.button3Pressed = false;
					}
				} else if (events[i].getID() == MouseEvent.MOUSE_DRAGGED) {
					double dx = 0, dy = 0, dz = 0;
					double dh = 0, dp = 0, dr = 0;
					msd.x2 = ((MouseEvent) events[i]).getX();
					msd.y2 = ((MouseEvent) events[i]).getY();
					if (msd.button1Pressed && msd.button3Pressed) {
						dr = ((msd.x2 - msd.x1) + (msd.y1 - msd.y2));
					} else {

						if (msd.button1Pressed) { // rotation
							dh = (msd.x2 - msd.x1) / 3.0;
							dp = (msd.y1 - msd.y2) / 3.0;
							dr = 0;
						}
						if (msd.button2Pressed) { // zoom
							dz = (msd.x1 - msd.x2 + msd.y2 - msd.y1) / 40.0;
						}
						if (msd.button3Pressed) { // translation dans le plan de
													// l'�cran
							dx = (msd.x2 - msd.x1) / 40.0;
							dy = (msd.y1 - msd.y2) / 40.0;
						}
					}
					msd.sharedUniverse.cameraRelativeTranslate(-dx, -dy, dz);
					msd.sharedUniverse.cameraRelativeRotate(dh, dp, dr);
					msd.x1 = msd.x2;
					msd.y1 = msd.y2;
				} else if (events[i].getID() == MouseEvent.MOUSE_WHEEL) {
					MouseWheelEvent event = (MouseWheelEvent) events[i];
					int rotates = event.getWheelRotation();
					double dz = rotates;
					msd.sharedUniverse.cameraRelativeTranslate(0, 0, dz);

				}
			}
		}

	}

}
