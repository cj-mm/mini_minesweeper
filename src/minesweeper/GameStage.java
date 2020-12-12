/***********************************************************
* Exercise 08: GUI (Modified Minesweeper Game Application)
* 
* GameStage Class
* 	- This class sets up the in-game logic and GUI.
* 
* @author Carl Joven M. Marasigan
* @created_date 2020-11-05 17:01
*
***********************************************************/
package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStage {
	private Scene scene;
	private Stage stage;
	/*Group layout/container is a component which applies no special 
	layout to its children. All child components (nodes) are positioned at 0,0*/
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private Element flag;	
	private GridPane map;
	private int[][] gameBoard;
	private ArrayList<Element> landCells;
	
	
	private boolean flagClicked=false;
	
	public final static int MAX_CELLS = 81;
	public final static int MAP_NUM_ROWS = 9;
	public final static int MAP_NUM_COLS = 9;	
	public final static int MAP_WIDTH = 400;
	public final static int MAP_HEIGHT = 400;
	public final static int CELL_WIDTH = 50;
	public final static int CELL_HEIGHT = 50;
	public final static int FLAG_WIDTH = 70;
	public final static int FLAG_HEIGHT = 70;
	public final static boolean IS_GAME_DONE = false;
	public final static int WINDOW_WIDTH = 500;
	public final static int WINDOW_HEIGHT = 600;
	///
	public final static int MAX_BOMBS = 15;
	private int safeLandOpened_count = 0;
	private int flag_count = 0;
	///
	public final Image bg = new Image("images/background.jpg",500,600,false,false);
	
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.WHITE);	
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);	
		this.gc = canvas.getGraphicsContext2D();
		this.flag = new Element(Element.FLAG_TYPE,this);
		this.map = new GridPane();
		this.landCells = new ArrayList<Element>();
		this.gameBoard = new int[GameStage.MAP_NUM_ROWS][GameStage.MAP_NUM_COLS];
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		//draw the background to the canvas at location x=0, y=70
		this.gc.drawImage(this.bg, 0, 70);			
		
		this.initGameBoard();	//initialize game board with 1s and 0s
		this.createMap();
		
		//set stage elements here	
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(map);		
		this.root.getChildren().add(this.flag.getImageView());
		this.stage.setTitle("Modified Minesweeper Game");
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	private void initGameBoard(){
		Random r = new Random();
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){
				this.gameBoard[i][j] = 0;		
			}
		}
		
		//Place bombs
		int numOfBombs = 0;
		while(numOfBombs < GameStage.MAX_BOMBS) {
			int randomRow = r.nextInt(MAP_NUM_ROWS);
			int randomCol = r.nextInt(MAP_NUM_COLS);
			if (this.gameBoard[randomRow][randomCol] == 0) {
				this.gameBoard[randomRow][randomCol] = 1;
				numOfBombs ++;
			}
		}
		
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			System.out.println(Arrays.toString(this.gameBoard[i]));//print final board content	
		}
	}
	
	private void createMap(){
		//create 81 land cells
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){
					
				// Instantiate land elements
				Element land = new Element(Element.LAND_TYPE,this);
				land.initRowCol(i,j);
				
				//add each land to the array list landCells
				this.landCells.add(land);
			}		
		}
		
		this.setGridPaneProperties();
		this.setGridPaneContents();
	}
	
	//method to set size and location of the grid pane 
	private void setGridPaneProperties(){
		this.map.setPrefSize(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT);
		//set the map to x and y location; add border color to see the size of the gridpane/map  
//	    this.map.setStyle("-fx-border-color: red ;");
		this.map.setLayoutX(GameStage.WINDOW_WIDTH*0.01);
	    this.map.setLayoutY(GameStage.WINDOW_WIDTH*0.15);
	    this.map.setVgap(5);
	    this.map.setHgap(5);
	}
	
	//method to add row and column constraints of the grid pane
	private void setGridPaneContents(){  
	          
		 //loop that will set the constraints of the elements in the grid pane
	     int counter=0;
	     for(int row=0;row<GameStage.MAP_NUM_ROWS;row++){
	    	 for(int col=0;col<GameStage.MAP_NUM_COLS;col++){
	    		 // map each land's constraints
	    		 GridPane.setConstraints(landCells.get(counter).getImageView(),col,row); 
	    		 counter++;
	    	 }
	     }   
	     
	   //loop to add each land element to the gridpane/map
	     for(Element landCell: landCells){
	    	 this.map.getChildren().add(landCell.getImageView());
	     }
	}
	
	boolean isBomb(Element element){
		int i = element.getRow();
		int j = element.getCol();
		
		//if the row col cell value is equal to 1, cell has bomb
		if(this.gameBoard[i][j] == 1){
			System.out.println(">>>>>>>>>Bomb!");
			return true;
		}
		
		System.out.println(">>>>>>>>>SAFE!");
		return false;
	}
	
	public boolean isFlagClicked() {
		return this.flagClicked;
	}
	
	public void setFlagClicked(boolean value) {
		this.flagClicked = value;
	}
	
	Stage getStage() {
		return this.stage;
	}
	
	//
	int getFlagCount() {
		return this.flag_count;
	}
	void setFlagCount() {
		this.flag_count++;
	}
	
	int getSafeLandOpenedCount() {
		return this.safeLandOpened_count;
	}
	
	void setSafeLandOpenedCount() {
		this.safeLandOpened_count++;
	}
	
	//return the number of neighboring bombs
	int numOfNearbyBombs(Element element) { 
		int row = element.getRow();
		int col = element.getCol();
		int[] nearX = {0, 1, 0, -1};
		int[] nearY = {-1, 0, 1, 0};
		int num = 0;
		//loop through neighboring bombs
		for (int i = 0; i < 4; i++) {
			int curX = row + nearX[i];
			int curY = col + nearY[i];
			if (curX < 0 || curX >= GameStage.MAP_NUM_ROWS || curY < 0 || curY >= GameStage.MAP_NUM_COLS) continue;
			if (this.gameBoard[curX][curY] == 1) num++;
		}
		return num;
	}
	
	void setGameOver(int num) {
		this.flashGameOver(num);
	}
	//

	void flashGameOver(int result){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage(result);
				stage.setScene(gameover.getScene());
			}
		});
	}

}

