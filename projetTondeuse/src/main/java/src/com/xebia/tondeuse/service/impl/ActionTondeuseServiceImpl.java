package src.com.xebia.tondeuse.service.impl;

import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;

import src.com.xebia.tondeuse.commun.OrientationEnum;
import src.com.xebia.tondeuse.commun.TondeuseUtils;
import src.com.xebia.tondeuse.object.Surface;
import src.com.xebia.tondeuse.object.Tondeuse;
import src.com.xebia.tondeuse.service.ActionTondeuseService;

public class ActionTondeuseServiceImpl implements ActionTondeuseService {

	private Lock verrou;

	public ActionTondeuseServiceImpl() {
	}

	public ActionTondeuseServiceImpl(Lock verrou) {
		super();
		this.verrou = verrou;
	}

	@Override
	public void bougerTondeuse(Tondeuse tondeuseMoove, Tondeuse tondeuseStatic,
			String instructions) {

		if (StringUtils.isNotEmpty(instructions)) {

			char tabInsctruct[] = instructions.toCharArray();

			for (char instruct : tabInsctruct) {

				traiterInstruct(tondeuseMoove, tondeuseStatic, instruct);

			}

			System.out.println("Position finale tondeuse n° "
					+ tondeuseMoove.getNumero() + TondeuseUtils.SAUT_DE_LIGNE
					+ tondeuseMoove.toString());

		}

	}

	@Override
	public Tondeuse positionnerTondeuse(int numeroTondeuse, int positionX,
			int positionY, OrientationEnum orientation, Surface surface) {

		Tondeuse tondeuse = new Tondeuse(numeroTondeuse, positionX, positionY,
				orientation, surface);

		// On evite de faire déborder la tondeuse de la surface
		if (surface != null) {

			boolean depasseLargeur = depassement(positionX,
					surface.getLargeur(), TondeuseUtils.LARGEUR_SURFACE_MIN);
			if (depasseLargeur) {
				tondeuse.setPositionX(surface.getLargeur());
			}

			boolean depasseLongeur = depassement(positionY,
					surface.getLongueur(), TondeuseUtils.LONGUEUR_SURFACE_MIN);
			if (depasseLongeur) {
				tondeuse.setPositionY(surface.getLongueur());
			}

		}

		return tondeuse;

	}

	protected void traiterInstruct(Tondeuse tondeuseMoove,
			Tondeuse tondeuseStatic, char instruction) {

		int coordonnee[] = new int[2];
		coordonnee[0] = tondeuseMoove.getPositionX();
		coordonnee[1] = tondeuseMoove.getPositionY();
		OrientationEnum orientation = tondeuseMoove.getOrientationEnum();

		System.out.println("Moove " + tondeuseMoove.getNumero()
				+ " insctruct = " + instruction + " coordonnee = "
				+ tondeuseMoove.toString() + " Static "
				+ tondeuseStatic.getNumero() + " coordonnee = "
				+ tondeuseStatic.toString());

		switch (instruction) {
		case TondeuseUtils.CODE_AVANCER:
			//On vérouille la portion de code avancer
			verrou.lock();
			
			System.out.println("Départ avance : "+getMsgAvancer(tondeuseMoove, tondeuseStatic));
			
			coordonnee = avancerTondeuse(tondeuseMoove, tondeuseStatic);
			tondeuseMoove.setPositionX(coordonnee[0]);
			tondeuseMoove.setPositionY(coordonnee[1]);
			
			System.out.println("Fin avance : "+getMsgAvancer(tondeuseMoove, tondeuseStatic));
			
			//On d�verouille cette portion
			verrou.unlock();
			
			break;

		case TondeuseUtils.CODE_DROITE:

			orientation = OrientationEnum.getOrientationEnumRight(orientation);
			break;

		case TondeuseUtils.CODE_GAUCHE:

			orientation = OrientationEnum.getOrientationEnumLeft(orientation);
			break;
		}

		tondeuseMoove.setOrientationEnum(orientation);
		

	}

	protected int[] avancerTondeuse(Tondeuse tondeuseMoove,
			Tondeuse tondeuseStatic) {

		OrientationEnum orientation = tondeuseMoove.getOrientationEnum();
		int positionX = tondeuseMoove.getPositionX();
		int positionY = tondeuseMoove.getPositionY();
		int coordonne[] = { positionX, positionY };

		int positionFinalX = tondeuseMoove.getPositionX();
		int positionFinalY = tondeuseMoove.getPositionY();

		if (OrientationEnum.NORD.equals(orientation)) {
			positionY++;

		} else if (OrientationEnum.SUD.equals(orientation)) {
			positionY--;

		} else if (OrientationEnum.WEST.equals(orientation)) {
			positionX--;

		} else if (OrientationEnum.EST.equals(orientation)) {
			positionX++;

		}

		int postionTestX = positionX;
		int positionTestY = positionY;

		if (isOkDeplacement(postionTestX, positionTestY, tondeuseMoove,
				tondeuseStatic)) {
			positionFinalX = positionX;
			positionFinalY = positionY;
		}

		coordonne[0] = positionFinalX;
		coordonne[1] = positionFinalY;

		return coordonne;
	}

	protected boolean isOkDeplacement(int positionX, int positionY,
			Tondeuse tondeuseMoove, Tondeuse tondeuseStatic) {

		boolean okFrontiere = isOkFrontiereSurface(positionX, positionY,
				tondeuseMoove);

		boolean pasColision = !isColision(positionX, positionY, tondeuseStatic);

		return okFrontiere && pasColision;

	}

	protected boolean isOkFrontiereSurface(int positionX, int positionY,
			Tondeuse tondeuseMoove) {

		Surface surface = tondeuseMoove.getSurface();

		boolean ok = false;

		if (surface != null) {

			boolean okLargeur = !depassement(positionX, surface.getLargeur(),
					TondeuseUtils.LARGEUR_SURFACE_MIN);
			boolean okLongeur = !depassement(positionY, surface.getLongueur(),
					TondeuseUtils.LONGUEUR_SURFACE_MIN);

			ok = okLargeur && okLongeur;

		}

		return ok;
	}

	protected boolean isColision(int positionX, int positionY,
			Tondeuse tondeuseStatic) {
		boolean colision = false;

		if (tondeuseStatic != null) {

			if (positionX == tondeuseStatic.getPositionX()
					&& positionY == tondeuseStatic.getPositionY()) {

				System.out.println(TondeuseUtils.MSG_COLISION
						+ StringUtils.SPACE + tondeuseStatic.getNumero());
				colision = true;
			}

		}

		return colision;

	}

	protected boolean depassement(int position, int surface, int minimum) {
		boolean depasse = false;

		if (surface < position || position < minimum) {

			depasse = true;

		}

		return depasse;
	}

	protected String getMsgAvancer(Tondeuse tondeuseMoove, Tondeuse tondeuseStatic){
		String msg = "Tondeuse moove n° "+tondeuseMoove.getNumero()+" coordonnée "+tondeuseMoove.toString()+" Tondeuse static n° "+tondeuseStatic.getNumero()+" coordonnée "+tondeuseStatic.toString();
		return msg;
	}
	
}
