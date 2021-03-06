package drawing;

import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Creep;
import logic.GameLogic;
import logic.GameLogic.STATE;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class Loading extends javafx.scene.canvas.Canvas{
	private GameLogic gameLogic;
	private int deg1;
	private int deg2;
	private int deg3;
	private int count;
	private long textTime;
	private long loadingTime;
	
	public Loading(double width, double height,GameLogic gameLogic) {
		super(width,height);
		this.setVisible(true);
		this.gameLogic = gameLogic;
		this.textTime = -1;
		this.loadingTime = -1;
		this.count = 1;
		this.deg1 = 5;
		this.deg2 = 4;
		this.deg3 = 3;
	}
	
	public void tick() {
		if(gameLogic.getNextStage() != 0) {
			if(loadingTime < 0) {
				loadingTime = gameLogic.getNow();
			}
			if((gameLogic.getNow()-loadingTime)/1000000000>2) {
				for(IRenderable x : RenderableHolder.getInstance().getEntities()) {
					if(x instanceof Creep) {
						Creep o = (Creep)x;
						o.setDestroy();
					}
				}
				gameLogic.setGameState(STATE.Game);
				loadingTime = -1;
				textTime = -1;
				RenderableHolder.state = "dead";
			}
		}
	}

	
	public void paintComponent() {
		if(textTime < 0) {
			textTime = gameLogic.getNow();
		}
		paintLoadingScreen();
		paintText(gameLogic.getNextStage());
	}
	
	public void paintLoadingScreen() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.translate(this.getWidth()/2, this.getHeight()/2);
		gc.rotate(deg1);
		gc.drawImage(RenderableHolder.loadingCircle, -100, -100);
		gc.rotate(-deg1);
		gc.translate(-this.getWidth()/2, -this.getHeight()/2);
		
		gc.translate(this.getWidth()/2 - 110, this.getHeight()/2 - 130);
		gc.rotate(deg2);
		gc.drawImage(RenderableHolder.loadingCircle, -50, -50, 100, 100);
		gc.rotate(-deg2);
		gc.translate(-(this.getWidth()/2 - 110), -(this.getHeight()/2 - 130));
		
		gc.translate(this.getWidth()/2 - 35 , this.getHeight()/2 - 110);
		gc.rotate(deg3);
		gc.drawImage(RenderableHolder.loadingCircle, -30, -30, 60, 60);
		gc.rotate(-deg3);
		gc.translate(-(this.getWidth()/2 - 35 ), -(this.getHeight()/2 - 110));
		
		deg1 += 5;
		deg2 += 8;
		deg3 -= 5;
	}
	
	public void paintText(int textCase) {
		GraphicsContext gc = this.getGraphicsContext2D();
		Font theFont = Font.font("Times New Roman", FontWeight.LIGHT, 30);
		gc.setFont(theFont);
		gc.setFill(Color.WHITE);
		if((gameLogic.getNow()-textTime)/1000000000>0.5) {
			count++;
			if(count > 3) {
				count = 1;
			}
			textTime = gameLogic.getNow();
		}
		
		switch (count) {
		case 1:
			if(textCase == 0) {
				gc.fillText("Loading.", this.getWidth()/2-80, 500);
			}else {
				gc.fillText("Stage "+textCase+".", this.getWidth()/2-80, 500);
			}
			break;

		case 2:
			if(textCase == 0) {
				gc.fillText("Loading..", this.getWidth()/2-80, 500);
			}else {
				gc.fillText("Stage "+textCase+"..", this.getWidth()/2-80, 500);
			}
			break;
			
		case 3:
			if(textCase == 0) {
				gc.fillText("Loading...", this.getWidth()/2-80, 500);
			}else {
				gc.fillText("Stage "+textCase+"...", this.getWidth()/2-80, 500);
			}
			break;
		}
	}
	
}
