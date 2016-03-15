package com.xebia.tondeuse.lanceur;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.xebia.tondeuse.commun.OrientationEnum;
import com.xebia.tondeuse.commun.TondeuseUtils;
import com.xebia.tondeuse.object.Surface;
import com.xebia.tondeuse.object.Tondeuse;
import com.xebia.tondeuse.service.ActionTondeuseService;
import com.xebia.tondeuse.service.LireInstructionService;
import com.xebia.tondeuse.service.impl.ActionTondeuseServiceImpl;
import com.xebia.tondeuse.service.impl.LireInstructionServiceImpl;
import com.xebia.tondeuse.service.impl.ServiceThread;


public class Lanceur{

	public static void main(String[] args) {
		
		//Les service
		LireInstructionService insctructionService = new LireInstructionServiceImpl();
		ActionTondeuseService tondeuseService = new ActionTondeuseServiceImpl();

		//Les instructions
		Map<String, String> mapInstructions = insctructionService.lireFichierInstruction(TondeuseUtils.NOM_FICHIER);
		int tailleSurface[] = insctructionService.getTailleSurface(mapInstructions.get(TondeuseUtils.KEY_SURFACE));
		String position1[] = insctructionService.getPositionOrientationTondeuse(mapInstructions.get(TondeuseUtils.KEY_COORDONNEE_1));
		String position2[] = insctructionService.getPositionOrientationTondeuse(mapInstructions.get(TondeuseUtils.KEY_COORDONNEE_2));
		String instruction1 = insctructionService.getInstruction(mapInstructions.get(TondeuseUtils.INSTRUCTION_1));
		String instruction2 = insctructionService.getInstruction(mapInstructions.get(TondeuseUtils.INSTRUCTION_2));
		
		//La surface
		Surface surface = new Surface(tailleSurface[0], tailleSurface[1]);
		
		//Les tondeuses
		int positionX1 = TondeuseUtils.formatingCaractere(position1[0]);
		int positionY1 = TondeuseUtils.formatingCaractere(position1[1]);
		OrientationEnum orient1 = OrientationEnum.getOrientationEnum(position1[2]);
		Tondeuse tondeuse1 = tondeuseService.positionnerTondeuse(1, positionX1, positionY1, orient1, surface);
		
		int positionX2 = TondeuseUtils.formatingCaractere(position2[0]);
		int positionY2 = TondeuseUtils.formatingCaractere(position2[1]);
		OrientationEnum orient2 = OrientationEnum.getOrientationEnum(position2[2]);
		Tondeuse tondeuse2 = tondeuseService.positionnerTondeuse(2, positionX2, positionY2, orient2, surface);

		//Verrou thread safe
		Lock lock = new ReentrantLock();
		
		//les threads
		Thread tondeuseThread1 = new Thread(new ServiceThread(tondeuse1, tondeuse2, instruction1, lock));
		Thread tondeuseThread2 = new Thread(new ServiceThread(tondeuse2, tondeuse1, instruction2, lock));
		
		//On d�marre les tondeuses
		tondeuseThread1.start();
		tondeuseThread2.start();
		
		
	}

}
