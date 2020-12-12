/***********************************************************
* Exercise 08: GUI (Modified Minesweeper Game Application)
* 
* GameOverStage Class
* 	- This class sets up the GUI of the game once the game is over.
* 
* @author Carl Joven M. Marasigan
* @created_date 2020-11-05 17:01
*
***********************************************************/
package minesweeper;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverStage {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	
	public final Image lose_BG = new Image("images/explosionBG.jpg",500,600,false,false);
	public final Image win_BG = new Image("images/winnerBG.jpg",500,600,false,false);
	
	GameOverStage(int result){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties(result);
	}
	
	private void setProperties(int result){									
		this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		Font theFont = Font.font("Calibri",FontWeight.BOLD,40);//set font type, style and size
		this.gc.setFont(theFont);
		if (result == 0) {
			this.gc.drawImage(this.lose_BG, 0, 0);
			this.gc.setFill(Color.BLACK);										//set font color of text
			this.gc.fillText("Game Over! You lose!", GameStage.WINDOW_WIDTH*0.15, GameStage.WINDOW_HEIGHT*0.45);
		} else  {
			this.gc.drawImage(this.win_BG, 0, 0);
			this.gc.setFill(Color.DARKGREEN);										//set font color of text
			this.gc.fillText("Congratulations! You win!", GameStage.WINDOW_WIDTH*0.08, GameStage.WINDOW_HEIGHT*0.4);
		}
		
		Button exitbtn = new Button("Exit Game");
		this.addEventHandler(exitbtn);
		
		
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(exitbtn);
	}
	
	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});
		
	}
	

	
	Scene getScene(){
		return this.scene;
	}
	
}
