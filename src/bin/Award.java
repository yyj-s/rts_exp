package bin;

import java.util.Random;

public class Award extends FlyingObject implements AwardInt {
	int type;
	int xSpeed,ySpeed;

	Award(){
		bg=ShootGame.award;
		y=0;
		Random random=new Random();
		x=random.nextInt(ShootGame.WIDTH-bg.getWidth());
		xSpeed=random.nextInt(5)+1;
		ySpeed=random.nextInt(3)+1;
		type=random.nextInt(2);
	}
	@Override
	public int getType(){
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void step(){
		// TODO Auto-generated method stub
		y+=ySpeed;
		if(x+xSpeed>ShootGame.WIDTH) {
			x=ShootGame.WIDTH;
			xSpeed=-xSpeed;
		}else if(x+xSpeed<0){
			x=0;
			xSpeed=-xSpeed;
		}else {
			x+=xSpeed;
		}

	}

	@Override
	public boolean outOfBounds() {
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
