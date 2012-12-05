package interaction.mouse;

import java.util.Enumeration;


//Pattern state
public abstract class AMouseStimulusState {

	
	protected MouseInteractor.MouseInteractorData msd;
	
	public AMouseStimulusState(MouseInteractor.MouseInteractorData msd) {
		this.msd = msd;
	}
	
	public abstract void stimulus(@SuppressWarnings("rawtypes") Enumeration criteria);

}
