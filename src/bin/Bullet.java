package bin;

public class Bullet extends FlyingObject {
	int speed=3;

	Bullet(int x,int y){
		this.x=x;
		this.y=y;
		bg=ShootGame.bullet;
	}
	@Override
	public void step(){
		// TODO Auto-generated method stub
		y-=speed;

	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return y<0;

	}

	@Override
	public boolean shootBy(Bullet b) {
		// TODO Auto-generated method stub
		return false;

	}

}
