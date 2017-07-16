package bin;

public class Hero extends FlyingObject {
	int doubleFire;
	int life;
	int index;
	
	Hero(){
		bg=ShootGame.heroplane;
		x=300-bg.getWidth()/2;
		y=700;
		life=3;
		doubleFire=0;
	}
	
	public void addDF() {
		doubleFire+=50;
	}
	public void addLife() {
		life++;
	}
	public void reduceLife() {
		life--;
	}
	public void moveTO(int x,int y) {
		this.x=x-bg.getWidth()/2;
		this.y=y-bg.getHeight()/2;
	}
	public int getLife() {
		return life;
	}
	public Bullet[] shoot() {
		int dx=bg.getWidth()/4;
		if(doubleFire>0) {
			Bullet[] bullets=new Bullet[2];
			bullets[0]=new Bullet(dx+x,y);
			bullets[1]=new Bullet(3*dx+x,y);
			return bullets;
		}else {
			Bullet[] bullet=new Bullet[1];
			bullet[0]=new Bullet(2*dx+x,y);
			return bullet;
		}
	}
	public boolean hit(FlyingObject f) {
		int x1=x+bg.getWidth()/2;
		int y1=y+bg.getHeight()/2;
		int x2=f.x+f.bg.getWidth()/2;
		int y2=f.y+f.bg.getHeight()/2;
		int averagex=bg.getWidth()/2+f.bg.getWidth()/2;
		int averagey=bg.getHeight()/2+f.bg.getHeight()/2;
		return Math.abs(x1-x2)<averagex&&Math.abs(y1-y2)<averagey;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;

	}

	@Override
	public boolean shootBy(Bullet b) {
		// TODO Auto-generated method stub
		return false;

	}

}
