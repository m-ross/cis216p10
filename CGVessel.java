package lab10;

import lab10.MaydayVessel;
import java.io.*;

public class CGVessel {
	private double xPos, yPos, speed;
	private int cap;

	public CGVessel(BufferedReader cgFile) throws IOException, NumberFormatException {
		xPos = Double.parseDouble(cgFile.readLine());
		yPos = Double.parseDouble(cgFile.readLine());
		speed = Double.parseDouble(cgFile.readLine());
		cap = Integer.parseInt(cgFile.readLine());
	}

	public CGVessel(double x, double y, double s, int c) {
		xPos = x;
		yPos = y;
		speed = s;
		cap = c;
	}

	public double getSpeed() {
		return speed;
	}

	public int getCap() {
		return cap;
	}

	public double getDist(MaydayVessel maydayVessel) {
		double xDist, yDist, dist;
		xDist = xPos - maydayVessel.getXPos();
		yDist = yPos - maydayVessel.getYPos();
		dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
		return dist;
	}

	public double getETA(MaydayVessel maydayVessel) {
		double dist, eta;
		dist = getDist(maydayVessel);
		eta = dist / speed;
		return eta;
	}
}