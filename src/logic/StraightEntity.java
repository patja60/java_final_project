package logic;

import input.OverridableEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sharedObject.RenderableHolder;

public class StraightEntity extends OverridableEntity{
	private GameLogic gameLogic;
	private String direction;
	private int width;
	private double damage;
	
	public StraightEntity(int x, int y, String direction, GameLogic gameLogic) {
		this.z = 15;
		if(direction == "l") {
			this.x = 0;
			this.y = y;
			this.cX = x;
			this.cY = y;
		}else if(direction == "r") {
			this.x = x+50;
			this.y = y;
			this.cX = 2000;
			this.cY = y;
		}
		this.direction = direction;
		this.gameLogic = gameLogic;
		this.width = 25;
	}

	@Override
	public void tick() {
		double comp;
		Creep a = null;
		switch (direction) {
		case "l":
			comp = -200;
			for(Entity x : gameLogic.getGameObjectContainer()) {
				if(x instanceof Creep) {
					Creep o = (Creep)x;
					if(overrideWith(o)){
						if(o.x>comp) {
							a = (Creep)x;
							comp = o.x+25;
						}
					}
				}
			}
			try {
				a.setHp(a.getHp()-damage);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			this.x = comp;
			break;
			
		case "r":
			comp = 5000;
			for(Entity x : gameLogic.getGameObjectContainer()) {
				if(x instanceof Creep) {
					Creep o = (Creep)x;
					if(overrideWith(o)){
						if(o.x<comp) {
							a = (Creep)x;
							comp = o.x+25;
						}
					}
				}
			}
			try {
				a.setHp(a.getHp()-damage);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			this.cX = comp;
			break;

		}
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		switch (direction) {
		case "l":
			gc.drawImage(RenderableHolder.headLaserL, x, y + 25 - width/2);
			for(double x = this.x+9 ; x <= cX-9 ; x++) {
				gc.drawImage(RenderableHolder.midLaserL, x, y + 25 - width/2);
			}
			gc.drawImage(RenderableHolder.tailLaserL, cX-10, y + 25 - width/2);
			break;
		case "r":
			gc.drawImage(RenderableHolder.tailLaserR, x, y + 25 - width/2);
			for(double x = this.x+9 ; x <= cX - 10 ; x++) {
				gc.drawImage(RenderableHolder.midLaserR, x, y + 25 - width/2);
			}
			gc.drawImage(RenderableHolder.headLaserR, cX-10, y + 25 - width/2);
			break;

		default:
			break;
		}
	

	}

	@Override
	protected Shape getBound() {
		Rectangle r = new Rectangle();
		switch (direction) {
		case "l":
			r.setX(0);
			r.setY(y + 25 - width/2);
			r.setWidth(cX);
			r.setHeight(width);
			break;

		case "r":
			r.setX(x);
			r.setY(y + 25 - width/2);
			r.setWidth(cX-x);
			r.setHeight(width);
			break;
		}
		return r;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

}
