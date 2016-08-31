package lab10;

import java.util.*;

public class MaydayVessel {
	private double xPos, yPos;
	private String name;
	private int personnel;
	private Date time;

	public MaydayVessel() {
	}

	public MaydayVessel(double x, double y, String n, int p, Date t) {
		xPos = x;
		yPos = y;
		name = n;
		personnel = p;
		time = t;
	}

	public void load(double x, double y, String n, int p, Date t) {
		xPos = x;
		yPos = y;
		name = n;
		personnel = p;
		time = t;
	}

	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public String getName() {
		return name;
	}

	public Date getTime() {
		return time;
	}

	public int getPersonnel() {
		return personnel;
	}
}