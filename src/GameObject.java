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
	public void setPosition(int x0, int y0){ 
		this.x=x0; this.y=y0;
	}
	public void changePosition(int dx, int dy){ 
		this.x+=dx;this.y=dy;
	}
	//movement
	public abstract void update();
	public boolean collide(GameObject o){
		if(getX()+getWidth()>=o.getX() && 
			getX()<=o.getX()+o.getWidth() &&
			getY()+getHeight()>=o.getY() &&
			getY()<=o.getY()+o.getHeight()){
			return true;
		}
		return false;
	}
}