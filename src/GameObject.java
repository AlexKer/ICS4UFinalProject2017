import Graphics.Animation;
public abstract class GameObject {
	//position, vector, dimensions, 
	private int ID;
	private int x, y, width, height;
	private Animation animation;
	//ID:1=Player,2=FlyMonster,3=SpikeMonster,4=Platform
	//5=Coin,6Shield,6=Jetpack
	public GameObject(int iD, int x, int y, int width, int height){
		 this.ID=iD; this.x=x; this.y=y; this.width=width; this.height=height;
	}
	//getter/accessor
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }
	public int getID(){ return this.ID; }
	public int getWidth(){ return this.width; }
	public int getHeight(){ return this.height; }
	//setter/mutator
	public void setX(int x0){ this.x=x0; }
	public void setY(int y0){ this.y=y0; }
	public void changeX(int dx){ this.x+=dx; }
	public void changeY(int dy){ this.y+=dy; }
	//movement
	public abstract void move();
}