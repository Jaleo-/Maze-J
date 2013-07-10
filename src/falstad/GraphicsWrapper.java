/**
 * 
 */
package falstad;

import java.awt.Color;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Font;

/**
 * @author aablohm and jaleo
 * 
 * This class encapsulates all of the graphics methods from FirstPersonDrawer, MapDrawer, Seg, and RangeSet.
 *
 */
public class GraphicsWrapper {
	
	Graphics gc;
	Color color;
	
	/**
	 * Sets the color using a Color input.
	 * @param c
	 */
	public void setColor(Color c){
		gc.setColor(c);
	}
	
	/**
	 * Sets the color using an array input
	 * @param c
	 */
	public void setColor(int[] c){
		gc.setColor(new Color(c[0],c[1],c[2]));
	}
	
	/**
	 * Sets the color using a string input
	 * @param c
	 */
	public void setColor(String c){
		if (c=="white")
			gc.setColor(Color.white);
		if(c =="red")
			gc.setColor(Color.red);
		if(c=="yellow")
			gc.setColor(Color.yellow);
		if(c=="gray")
			gc.setColor(Color.gray);
		if(c=="black")
			gc.setColor(Color.black);
		if(c=="darkGray")
			gc.setColor(Color.darkGray);
	}
	
	/**
	 * Calls the graphics fillRect() method
	 * @param i
	 * @param j
	 * @param width
	 * @param halfHeight
	 */
	public void fillRect(int i, int j, int width, int halfHeight){
		gc.fillRect(i, j, width, halfHeight);
	}
	
	/**
	 * Calls the graphics fillPolygon() method
	 * @param xps
	 * @param yps
	 * @param z
	 */
	public void fillPolygon(int[] xps, int[] yps, int z){
		gc.fillPolygon(xps, yps, z);
	}
	
	/**
	 * Calls the graphics setFont() method
	 * @param f
	 */
	public void setFont(Font f){
		gc.setFont(f);
	}
	
	/**
	 * Calls the graphics getFontMetrics() method
	 * @return
	 */
	public FontMetrics getFontMetrics(){
		return gc.getFontMetrics();
	}
	
	/**
	 * Calls the graphics drawString() method
	 * @param str
	 * @param x
	 * @param y
	 */
	public void drawString(String str,int x, int y){
		gc.drawString(str, x, y);
	}

	/**
	 * Calls the graphics drawLine() method
	 * @param nx1
	 * @param ny1
	 * @param nx2
	 * @param ny12
	 */
	public void drawLine(int nx1, int ny1, int nx2, int ny12) {
		gc.drawLine(nx1, ny1, nx2, ny12);
		
	}

	/**
	 * Calls the graphics fillOval() method
	 * @param i
	 * @param j
	 * @param cirsiz
	 * @param cirsiz2
	 */
	public void fillOval(int i, int j, int cirsiz, int cirsiz2) {
		gc.fillOval(i, j, cirsiz, cirsiz2);
		
	}

}
