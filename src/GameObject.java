
public abstract class GameObject {
	private int x, y, ID;
	//ID:1=Player,2=FlyMonster,3=SpikeMonster,4=Platform
	//5=Coin,6Shield,6=Jetpack
	public GameObject(int x, int y, int ID){
		this.x=x; this.y=y; this.ID=ID;
	}
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }
	public abstract void update();
	public void del(){};
	public boolean collide(int x0, int y0, int tol){
		if(Math.sqrt(Math.pow(x0-this.x,2)+Math.pow(y0-this.y,2))>=tol) return true;
		return false;
	}
	public int[] getRandomPosition(int x, int y, int l, int w){
		int[] a={(int)Math.random()*l, (int)Math.random()*w};
		return a;
	}
}
