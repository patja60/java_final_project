package logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class SlowOrbitEntity extends OrbitEntity{
	private int deg;
	private double x1;
	private double y1;
	private int dimension;
	private double slow;
	
	public SlowOrbitEntity(int x, int y, int orbitRange, int posiotion,double speed) {
		this.dimension = 20;
		this.x = x-dimension/2;
		this.y = y-dimension/2;
		this.speed = speed;
		this.slow = 35;
		this.orbitRange = orbitRange;
		this.locationX = this.x+orbitRange*Math.sin(Math.toRadians(posiotion));
		this.locationY = this.y-orbitRange*Math.cos(Math.toRadians(posiotion));;
		x1 = locationX-this.x;
		y1 = locationY-this.y;
		this.deg = 0;
		this.damage = 5;
	}

	@Override
	public void tick() {
		orbit();
	}

	@Override
	public void draw(GraphicsContext gc) {
		this.cX = locationX + dimension/2;
		this.cY = locationY + dimension/2;
		
		gc.setFill(Color.ALICEBLUE);
		gc.fillOval(locationX, locationY, dimension, dimension);
		//gc.drawImage(RenderableHolder.normalOrbitEntity, locationX, locationY);
	

	}

	@Override
	protected Shape getBound() {
		return new Circle(cX, cY, dimension/2);
	}
	
	public void orbit() {
		deg+= speed;
		double angle = Math.toRadians(deg);

		//APPLY ROTATION
		double newx1 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
		double newy1 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

		//TRANSLATE BACK
		locationX = (int)Math.ceil(newx1) + this.x;
		locationY = (int)Math.ceil(newy1) + this.y;
		if(deg > 360) deg = 0;
		
	}

	public double getSlow() {
		return slow;
	}

	public void setSlow(double slow) {
		this.slow = slow;
	}

}
