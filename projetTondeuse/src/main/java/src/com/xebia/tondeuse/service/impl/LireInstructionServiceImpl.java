package src.com.xebia.tondeuse.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import src.com.xebia.tondeuse.commun.OrientationEnum;
import src.com.xebia.tondeuse.commun.TondeuseException;
import src.com.xebia.tondeuse.commun.TondeuseUtils;
import src.com.xebia.tondeuse.object.Instruction;
import src.com.xebia.tondeuse.service.LireInstructionService;

public class LireInstructionServiceImpl implements LireInstructionService {

	
	@Override
	public Instruction getInsctruction(String nomFichier) {
		
		Map<String, String> mapInstructions = lireFichierInstruction(TondeuseUtils.NOM_FICHIER);
		int tailleSurface[] = getTailleSurface(mapInstructions.get(TondeuseUtils.KEY_SURFACE));
		String position1[] = getPositionOrientationTondeuse(mapInstructions.get(TondeuseUtils.KEY_COORDONNEE_1));
		String position2[] = getPositionOrientationTondeuse(mapInstructions.get(TondeuseUtils.KEY_COORDONNEE_2));
		String instruction1 = getInstruction(mapInstructions.get(TondeuseUtils.INSTRUCTION_1));
		String instruction2 = getInstruction(mapInstructions.get(TondeuseUtils.INSTRUCTION_2));
		
		int positionX1 = TondeuseUtils.formatingCaractere(position1[0]);
		int positionY1 = TondeuseUtils.formatingCaractere(position1[1]);
		int positionX2 = TondeuseUtils.formatingCaractere(position2[0]);
		int positionY2 = TondeuseUtils.formatingCaractere(position2[1]);
		OrientationEnum orient1 = OrientationEnum.getOrientationEnum(position1[2]);
		OrientationEnum orient2 = OrientationEnum.getOrientationEnum(position2[2]);
		
		Instruction instruction = new Instruction();
		instruction.setInstruction1(instruction1);
		instruction.setInstruction2(instruction2);
		instruction.setLargeurSurface(tailleSurface[0]);
		instruction.setLongueurSurface(tailleSurface[1]);
		instruction.setOrientation1(orient1);
		instruction.setOrientation2(orient2);
		instruction.setTondeusePositionX1(positionX1);
		instruction.setTondeusePositionY1(positionY1);
		instruction.setTondeusePositionX2(positionX2);
		instruction.setTondeusePositionY2(positionY2);
		
		return instruction;
	}

	

	protected Map<String, String> lireFichierInstruction(String nomFichier) {

		Map<String, String> mapInstructions = new HashMap<>();

		try {

			InputStream ips = new FileInputStream(nomFichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;

			while ((ligne = br.readLine()) != null) {

				ligne = StringUtils.strip(ligne);

				ligne = StringUtils.upperCase(ligne);

				ligne = StringUtils.replace(ligne, StringUtils.SPACE,
						StringUtils.EMPTY);

				if (ligne.length() == 2 && isContainNumber(ligne)) {

					mapInstructions.putIfAbsent(TondeuseUtils.KEY_SURFACE, ligne);

				} else if (ligne.length() == 3 && isContainNumber(ligne)) {

					if (mapInstructions.containsKey(TondeuseUtils.KEY_COORDONNEE_1)) {

						mapInstructions.putIfAbsent(TondeuseUtils.KEY_COORDONNEE_2, ligne);

					} else {

						mapInstructions.put(TondeuseUtils.KEY_COORDONNEE_1, ligne);

					}

				} else if (StringUtils.isNotEmpty(ligne)) {

					if (mapInstructions.containsKey(TondeuseUtils.INSTRUCTION_1)) {

						mapInstructions.putIfAbsent(TondeuseUtils.INSTRUCTION_2, ligne);

					} else {

						mapInstructions.put(TondeuseUtils.INSTRUCTION_1, ligne);

					}
				}
			}

			br.close();
		} catch (Exception e) {

			throw new TondeuseException(1, "fichier ilisible ou introuvable");

		}

		return mapInstructions;
	}

	protected int[] getTailleSurface(String ligneFichier) {
		int[] tabTailleSurface = {TondeuseUtils.LARGEUR_SURFACE_DEFAUT,
				TondeuseUtils.LONGUEUR_SURFACE_DEFAUT };// Par d�faut

		if (StringUtils.isNotEmpty(ligneFichier)) {
			String tabStr[] = ligneFichier.split(StringUtils.EMPTY);

			if (tabStr.length == 2) {

				tabTailleSurface[0] = TondeuseUtils.formatingCaractere(tabStr[0]);
				tabTailleSurface[1] = TondeuseUtils.formatingCaractere(tabStr[1]);
				
			}

		}

		return tabTailleSurface;
	}

	protected String[] getPositionOrientationTondeuse(String ligneFichier) {
		String[] tabCoordonnee = { TondeuseUtils.POSITION_X_DEFAUT, TondeuseUtils.POSITION_Y_DEFAUT,
				TondeuseUtils.ORIENTATION_DEFAUT };// Par d�faut

		if (StringUtils.isNotEmpty(ligneFichier)) {
			char tabChar[] = ligneFichier.toCharArray();

			if (tabChar.length == 3) {
				
				tabCoordonnee[0] = Character.toString(tabChar[0]);
				tabCoordonnee[1] = Character.toString(tabChar[1]);
				tabCoordonnee[2] = Character.toString(tabChar[2]);

			}

		}

		return tabCoordonnee;
	}

	protected String getInstruction(String ligneFichier) {
		return ligneFichier;
	}
	
	protected boolean isContainNumber(String chaine){
		char tabChar[] = chaine.toCharArray();
		boolean presenceNumber = false;
		
		for (char c : tabChar) {
			
			if (Character.isDigit(c)) {
				
				presenceNumber = true;
				
			}
			
		}
		
		return presenceNumber;
	}


}
