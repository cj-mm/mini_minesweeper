/***********************************************************
* Exercise 08: GUI (Modified Minesweeper Game Application)
* 
* Main Class
* 	- Main program.
* 
* @author Carl Joven M. Marasigan
* @created_date 2020-11-05 17:01
*
***********************************************************/
package main;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.GameStage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		GameStage theGameStage = new GameStage();
		theGameStage.setStage(stage);
	}
}
