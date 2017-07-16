package bin;

import java.util.Random;

public class Airplane extends FlyingObject implements Enemy {
	int speed,score;
	
	Airplane(){
		bg=ShootGame.airplane;
		Random random=new Random();
		y=0;
		x=random.nextInt(ShootGame.WIDTH-bg.getWidth());
		speed=random.nextInt(3)+1;
		score=random.nextInt(6)+1;
	}

	@Override
	public int getScore(){
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	public void step(){
		// TODO Auto-generated method stub
		y+=speed;

	}

	@Override
	public boolean outOfBounds(){
		// TODO Auto-generated method stub
		return y>ShootGame.HEIGHT;

	}

	@Override
	public boolean shootBy(Bullet b) {
		// TODO Auto-generated method stub
		int x1=x+bg.getWidth()/2;
		int y1=y+bg.getHeight()/2;
		int x2=b.x+b.bg.getWidth()/2;
		int y2=b.y+b.bg.getHeight()/2;
		int averagex=bg.getWidth()/2+b.bg.getWidth()/2;
		int averagey=bg.getHeight()/2+b.bg.getHeight()/2;
		return Math.abs(x1-x2)<averagex&&Math.abs(y1-y2)<averagey;

	}

}
