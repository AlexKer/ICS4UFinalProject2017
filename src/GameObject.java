import java.awt.Graphics;

import Graphics.Animation;
public abstract class GameObject {
	//position, vector, dimensions, 
	private int ID;
	private int x, y, width, height;
	private Animation animation;
	private int vx, vy;
	//ID:1=Player,2=FlyMonster,3=SpikeMonster,4=Platform
	//5=Coin,6Shield,6=Jetpack
	public GameObject(int iD, int x, int y, int width, int height){
		 this.ID=iD; this.x=x; this.y=y; this.width=width; this.height=height;
	}
	//movement
	public void move(){
		x+=vx;
		y+=vy;
	}
	//getter/accessor
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }
	public int getWidth(){ return this.width; }
	public int getHeight(){ return this.height; }
	//setter/mutator
	public void setX(int x0){ this.x=x0; }
	public void setY(int y0){ this.y=y0; }
	public void changeX(int dx){ this.x+=dx; }
	public void changeY(int dy){ this.y+=dy; }
	public void setVelocity(int vx0, int vy0){
		this.vx=vx0;
		this.vy=vy0;
	}
	public abstract void draw(Graphics g);
	public abstract void animate();
	public abstract void stopAnimate();
}