/**
 * 
 */
package falstad;



/**
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 */
public class Seg {
	public int x, y, dx, dy, dist;
	public int [] col = new int [3];
	public boolean partition, seen;

	/**
	 * Constructor
	 * @param psx
	 * @param psy
	 * @param pdx
	 * @param pdy
	 * @param cl
	 * @param cc
	 */
	Seg(int psx, int psy, int pdx, int pdy, int cl, int cc) {
		x = psx;
		y = psy;
		dx = pdx;
		dy = pdy;
		dist = cl;
		seen = false;
		cl /= 4;
		int add = (dx != 0) ? 1 : 0;
		int part1 = cl & 7;
		int part2 = ((cl >> 3) ^ cc) % 6;
		int val1 = ((part1 + 2 + add) * 70)/8 + 80;
		switch (part2) {
		case 0: col[0] = val1;col[1] =20; col[2]= 20; break;
		case 1: col[0] = 20; col[1] =val1;col[2]= 20; break;
		case 2: col[0] = 20;col[1]=20;col[2]= val1; break;
		case 3: col[0] = val1;col[1]= val1; col[2]= 20; break;
		case 4: col[0] = 20; col[1]= val1; col[2]= val1; break;
		case 5: col[0] = val1;col[1]= 20;col[2]= val1; break;
		}
	}

	int getDir() {
		if (dx != 0)
			return (dx < 0) ? 1 : -1;
		return (dy < 0) ? 2 : -2;
	}
}
