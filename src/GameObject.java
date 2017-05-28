public abstract class GameObject {
	// position, dimensions, and velocities
	private int x, y, width, height;
	private int vx, vy;

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// movement
	public void move() {
		this.x += vx;
		this.y += vy;
	}

	// getter or accessor for the GameObject's...
	// top, left corner coordinates (x,y);
	// width and height;
	// velocity for x(horizontal), velocity for y(vertical)
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

	// setter or mutator for instance variables listed above
	// "set" methods set directly the value passed in
	// "change" methods add the value to the current variable
	public void setX(int x0) {
		this.x = x0;
	}

	public void setY(int y0) {
		this.y = y0;
	}

	public void changeX(int dx) {
		this.x += dx;
	}

	public void changeY(int dy) {
		this.y += dy;
	}

	public void setVx(int vx0) {
		vx = vx0;
	}

	public void setVy(int vy0) {
		vy = vy0;
	}

	public void changeVx(int dx) {
		vx += dx;
	}

	public void changeVy(int dy) {
		vy += dy;
	}

	// two abstract methods for animation
	public abstract void animate();

	public abstract void stopAnimate();
}