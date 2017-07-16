package bin;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x,y,width,height;
	protected BufferedImage bg;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public BufferedImage getBg() {
		return bg;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public void setWidth(int width) {
		this.width=width;
	}
	public void setHeight(int height) {
		this.height=height;
	}
	
	public abstract void step();
	
	public abstract boolean outOfBounds();
	
	public abstract boolean shootBy(Bullet b);

}
