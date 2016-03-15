package src.com.xebia.tondeuse.object;

import src.com.xebia.tondeuse.commun.OrientationEnum;

public class Instruction {

	private int largeurSurface;
	
	private int longueurSurface;
	
	private int tondeusePositionX1;
	
	private int tondeusePositionY1;
	
	private int tondeusePositionX2;
	
	private int tondeusePositionY2;
	
	private OrientationEnum orientation1;
	
	private OrientationEnum orientation2;
	
	private String instruction1;
	
	private String instruction2;

	public int getLargeurSurface() {
		return largeurSurface;
	}

	public void setLargeurSurface(int largeurSurface) {
		this.largeurSurface = largeurSurface;
	}

	public int getLongueurSurface() {
		return longueurSurface;
	}

	public void setLongueurSurface(int longueurSurface) {
		this.longueurSurface = longueurSurface;
	}

	public int getTondeusePositionX1() {
		return tondeusePositionX1;
	}

	public void setTondeusePositionX1(int tondeusePositionX1) {
		this.tondeusePositionX1 = tondeusePositionX1;
	}

	public int getTondeusePositionY1() {
		return tondeusePositionY1;
	}

	public void setTondeusePositionY1(int tondeusePositionY1) {
		this.tondeusePositionY1 = tondeusePositionY1;
	}

	public int getTondeusePositionX2() {
		return tondeusePositionX2;
	}

	public void setTondeusePositionX2(int tondeusePositionX2) {
		this.tondeusePositionX2 = tondeusePositionX2;
	}

	public int getTondeusePositionY2() {
		return tondeusePositionY2;
	}

	public void setTondeusePositionY2(int tondeusePositionY2) {
		this.tondeusePositionY2 = tondeusePositionY2;
	}

	public OrientationEnum getOrientation1() {
		return orientation1;
	}

	public void setOrientation1(OrientationEnum orientation1) {
		this.orientation1 = orientation1;
	}

	public OrientationEnum getOrientation2() {
		return orientation2;
	}

	public void setOrientation2(OrientationEnum orientation2) {
		this.orientation2 = orientation2;
	}

	public String getInstruction1() {
		return instruction1;
	}

	public void setInstruction1(String instruction1) {
		this.instruction1 = instruction1;
	}

	public String getInstruction2() {
		return instruction2;
	}

	public void setInstruction2(String instruction2) {
		this.instruction2 = instruction2;
	}

	public Instruction() {
	}

	
	
}
