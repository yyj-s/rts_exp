package bin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	private int score;
	private int intervel=10;
	private int state=START;
	Timer timer;
	
	public static final int START=0;
	public static final int RUNNING=1;
	public static final int PAUSE=2;
	public static final int GAME_OVER=3;
	public static final int WIDTH=600;
	public static final int HEIGHT=900;
	
	
	public static BufferedImage airplane;
	public static BufferedImage award;
	public static BufferedImage background;
	public static BufferedImage bullet;
	public static BufferedImage gameover;
	public static BufferedImage heroplane;
	public static BufferedImage pause;
	public static BufferedImage start;
	
	Hero hero=new Hero();
	FlyingObject[] flyings= {};
	Bullet[] bullets= {};
	public ShootGame() {};
	static {
		try {
			airplane=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/airplane.png"));
			award=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/award.png"));
			background=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/background.png"));
			bullet=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/bullet.png"));
			gameover=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/gameover.png"));
			heroplane=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/heroplane.png"));
			pause=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/pause.png"));
			start=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/shootgamex/src/background/start.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintBullets(g);
		paintFlyingObjects(g);
		paintScore(g);
		paintState(g);
		
	}
	
	public void paintHero(Graphics g) {
		g.drawImage(heroplane, hero.getX(), hero.getY(), null);
	}
	public void paintBullets(Graphics g) {
		for(Bullet b:bullets) {
			g.drawImage(bullet, b.getX(), b.getY(), null);
		}
	}
	public void paintFlyingObjects(Graphics g) {
		for(FlyingObject f:flyings) {
			g.drawImage(f.getBg(), f.getX(), f.getY(), null);
		}
	}
	public void paintScore(Graphics g) {
		int x=10;
		int y=25;
		Font font=new Font(Font.SANS_SERIF,Font.BOLD,14);
		g.setColor(new Color(0x3a3fff));
		g.setFont(font);
		g.drawString("Score: "+score, x, y);
		y+=20;
		g.drawString("LIFE: "+hero.getLife(), x, y);
	}
	public void paintState(Graphics g) {
		switch(state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	
	public static void main(String[] args) {
		ShootGame game=new ShootGame();
		JFrame jframe=new JFrame("FLY");
		jframe.add(game);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setAlwaysOnTop(true);
		game.action();
		
	}
	public void action() {
		MouseAdapter lis=new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
					int x=e.getX();
					int y=e.getY();
					hero.moveTO(x, y);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {
					state=RUNNING;
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) {
					state=PAUSE;
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(state) {
				case GAME_OVER:
					flyings=new FlyingObject[0];
					bullets=new Bullet[0];
					score=0;
					hero=new Hero();
					state=START;
					break;
				case START:
					state=RUNNING;
					break;
				
				}
			}
		};
		this.addMouseListener(lis);
		this.addMouseMotionListener(lis);
		timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(state==RUNNING) {
					enterAction();
					stepAction();
					shootAction();
					checkShootBYAction();
					outOfBoundAction();
					checkGameOverAction();
					
				}
				repaint();
			}
		},intervel,intervel);
	}
	public static FlyingObject nextOne() {
		Random random=new Random();
		int type = random.nextInt(20);
		if(type==0) {
			return new Award();
		}else {
			return new Airplane();
		}
	}
	int flyingEnteredIndex=0;
	public void enterAction() {
		flyingEnteredIndex++;
		if(flyingEnteredIndex%40==0) {
			flyingEnteredIndex=0;
			FlyingObject f=nextOne();
			flyings=Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1]=f;
		}
	}
	public  void stepAction() {
		for(FlyingObject f:flyings) {
			f.step();
		}
		for(Bullet b:bullets) {
			b.step();
		}
		hero.step();
	}
	int shootIndex=0;
	public void shootAction() {
		shootIndex++;
		if(shootIndex%40==0) {
			shootIndex=0;
			Bullet[] bs=hero.shoot();
			bullets=Arrays.copyOf(bullets, bs.length+bullets.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
	}
	public void checkShootBYAction() {
		for(Bullet b:bullets) {
			isShootBY(b);
		}
	}
	public void isShootBY(Bullet b) {
		int index=-1;
		for(int i=0;i<flyings.length;i++) {
			FlyingObject f=flyings[i];
			if(f.shootBy(b)) {
				index=i;
				break;
			}
		}
		if(index!=-1) {
			FlyingObject one=flyings[index];
			flyings[index]=flyings[flyings.length-1];
			flyings=Arrays.copyOf(flyings, flyings.length-1);
			if(one instanceof Enemy) {
				Enemy e=(Enemy)one;
				score+=e.getScore();
			}
			if(one instanceof AwardInt) {
				AwardInt a=(AwardInt)one;
				int type=a.getType();
				switch(type) {
				case AwardInt.DOUBLE_FIRE:
					hero.addDF();
					break;
				case AwardInt.LIFE:
					hero.addLife();
					break;
				}
				}
			}
		}
	public void outOfBoundAction() {
		int index=0;
		FlyingObject[] falive=new FlyingObject[flyings.length];
		for(FlyingObject f:flyings) {
			if(!f.outOfBounds()) {
				falive[index++]=f;
			}
		}
		flyings=Arrays.copyOf(falive, index);
		index=0;
		Bullet[] balive=new Bullet[bullets.length];
		for(Bullet b:bullets) {
			if(!b.outOfBounds()) {
				balive[index++]=b;
			}
		}
		bullets=Arrays.copyOf(balive, index);
	
	}
	public void checkGameOverAction() {
		if(isGameOver()) {
			state=GAME_OVER;
		}
	}
	public boolean isGameOver() {
		int index=-1;
		for(int i=0;i<flyings.length;i++) {
			FlyingObject f=flyings[i];
			if(hero.hit(f)) {
				hero.reduceLife();
				index=i;
				break;
			}
		}
		if(index!=-1) {
			FlyingObject one=flyings[index];
			flyings[index]=flyings[flyings.length -1];
			flyings=Arrays.copyOf(flyings, flyings.length-1);
		}
		return hero.getLife()<=0;
	}
	
	
	

}
