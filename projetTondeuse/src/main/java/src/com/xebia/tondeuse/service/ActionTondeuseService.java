package src.com.xebia.tondeuse.service;

import src.com.xebia.tondeuse.commun.OrientationEnum;
import src.com.xebia.tondeuse.object.Surface;
import src.com.xebia.tondeuse.object.Tondeuse;

public interface ActionTondeuseService {

	public void bougerTondeuse(Tondeuse tondeuseMoove, Tondeuse tondeuseStatic, String instructions);
	
	public Tondeuse positionnerTondeuse(int numeroTondeuse, int positionX, int positionY, OrientationEnum orientation, Surface surface);
	
}
