package src.com.xebia.tondeuse.lanceur;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import src.com.xebia.tondeuse.commun.OrientationEnum;
import src.com.xebia.tondeuse.commun.TondeuseUtils;
import src.com.xebia.tondeuse.object.Instruction;
import src.com.xebia.tondeuse.object.Surface;
import src.com.xebia.tondeuse.object.Tondeuse;
import src.com.xebia.tondeuse.service.ActionTondeuseService;
import src.com.xebia.tondeuse.service.LireInstructionService;
import src.com.xebia.tondeuse.service.impl.ActionTondeuseServiceImpl;
import src.com.xebia.tondeuse.service.impl.LireInstructionServiceImpl;
import src.com.xebia.tondeuse.service.impl.ServiceThread;


public class Lanceur{

	public static void main(String[] args) {
		
		//Les instructions
		LireInstructionService insctructionService = new LireInstructionServiceImpl();
		Instruction instruction = insctructionService.getInsctruction(TondeuseUtils.NOM_FICHIER);
		
		//La surface
		Surface surface = new Surface(instruction.getLargeurSurface(), instruction.getLongueurSurface());
		
		//Action sur les tondeuse
		ActionTondeuseService tondeuseService = new ActionTondeuseServiceImpl();
		
		//Positionnement des tondeuse
		int positionX1 = instruction.getTondeusePositionX1();
		int positionY1 = instruction.getTondeusePositionY1();
		OrientationEnum orientation1 = instruction.getOrientation1();
		Tondeuse tondeuse1 = tondeuseService.positionnerTondeuse(1, positionX1, positionY1, orientation1, surface);
		
		int positionX2 = instruction.getTondeusePositionX2();
		int positionY2 = instruction.getTondeusePositionY2();
		OrientationEnum orientation2 = instruction.getOrientation2();
		Tondeuse tondeuse2 = tondeuseService.positionnerTondeuse(2, positionX2, positionY2, orientation2, surface);
		
		//Mouvement des tondeuses
		
		//Verrou thread safe
		Lock lock = new ReentrantLock();
		
		//les threads
		String instruction1 = instruction.getInstruction1();
		String instruction2 = instruction.getInstruction2();
		Thread tondeuseThread1 = new Thread(new ServiceThread(tondeuse1, tondeuse2, instruction1, lock));
		Thread tondeuseThread2 = new Thread(new ServiceThread(tondeuse2, tondeuse1, instruction2, lock));
		
		//On démarre les tondeuses
		tondeuseThread1.start();
		tondeuseThread2.start();
		
		
	}

}
