package com.xebia.tondeuse.service.impl;

import java.util.concurrent.locks.Lock;

import com.xebia.tondeuse.object.Tondeuse;
import com.xebia.tondeuse.service.ActionTondeuseService;

public class ServiceThread implements Runnable {

	private Tondeuse tondeuseMoove;
	private Tondeuse tondeuseStatic;
	private String instructions;
	private Lock verrou;

	public ServiceThread(Tondeuse tondeuseMoove, Tondeuse tondeuseStatic,
			String instructions, Lock verrou) {
		super();
		this.tondeuseMoove = tondeuseMoove;
		this.tondeuseStatic = tondeuseStatic;
		this.instructions = instructions;
		this.verrou = verrou;

	}

	@Override
	public void run() {
		ActionTondeuseService actionTondeuse = new ActionTondeuseServiceImpl(verrou);
		actionTondeuse.bougerTondeuse(tondeuseMoove, tondeuseStatic,
				instructions);

	}

}
