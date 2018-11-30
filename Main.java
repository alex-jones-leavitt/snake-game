package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
	
	int x=200;
	int y=200;
	int dx;
	int dy;
	int location1;
	int location2;
	int length;
	int crash=0;
	
	
	AnimationTimer Timer;
	Circle food;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
 

			Rectangle board=new Rectangle(0,0,400,400);
			board.setFill(Color.LIGHTGREY);
			root.getChildren().add(board);
			
			length=0;
 
			Rectangle[] snake=new Rectangle[1000];
					snake[0]=new Rectangle(x,y,10,10);
					snake[0].setFill(Color.GREEN);

			location1=(0+(int)(Math.random()*((39-0)+1))*10)+5;

			location2=(0+(int)(Math.random()*((39-0)+1))*10)+5;
			
			food=new Circle((location1),(location2),5);
			food.setFill(Color.RED);
			root.getChildren().add(food);
			primaryStage.show();


			scene.setOnKeyPressed(new EventHandler<KeyEvent>(){          //Nick help
				public void handle(KeyEvent m) {
					if(m.getCode()==KeyCode.UP){
						dy=-10;
						dx=0;
					}
					if(m.getCode()==KeyCode.DOWN){
						dy=10;
						dx=0;
					}
					if(m.getCode()==KeyCode.LEFT){
						dx=-10;
						dy=0;
					}
					if(m.getCode()==KeyCode.RIGHT){
						dx=10;
						dy=0;
					}


				}
			});
			
			final long startTime=System.nanoTime();
			
			new AnimationTimer(){
				private long lastUpdate=0;
				@Override
				public void handle(long time){
					
					
					if((time-lastUpdate>=(200000000/3)) && x>=0 && x<400 && y>=0 && y<400 && crash!=100){
						x+=dx;
						y+=dy;
						for(int i=length; i>=0; i--){
							if(i==0){
								root.getChildren().remove(snake[0]);
								snake[0].setX(x);
								snake[0].setY(y);
								root.getChildren().add(snake[0]);
							}
							else{
								root.getChildren().remove(snake[i]);
								snake[i].setX(snake[i-1].getX());
								snake[i].setY(snake[i-1].getY());
								root.getChildren().add(snake[i]);
							}
						}
						
						
						
						
						System.out.println(+time);
						lastUpdate=time;
						System.out.println(+length);
					}
						
					if(x<0 || x>=400 || y<0 || y>=400 || crash==100){
						Label gLabel=new Label("SMOOTH MOVE X-LAX");
						gLabel.setLayoutX(100);
						gLabel.setLayoutY(100);			
						root.setCenter(gLabel);
					}
					if(((x+5)==location1) && ((y+5)==location2)){
						root.getChildren().remove(food);
						location1 = (0+(int)(Math.random()*((39-0)+1))*10)+5;
						location2 = (0+(int)(Math.random()*((39-0)+1))*10)+5;
						food.setCenterX(location1);
						food.setCenterY(location2);
						root.getChildren().add(food);
						length+=2;
						snake[length-1]=new Rectangle(snake[length-2].getX()-dx,snake[length-2].getY()-dy,10,10);
						snake[length-1].setFill(Color.GREEN);
						root.getChildren().add(snake[length-1]);
						snake[length]=new Rectangle(snake[length-1].getX()-dx,snake[length-1].getY()-dy,10,10);
						snake[length].setFill(Color.GREEN);
						root.getChildren().add(snake[length]);
						
						
					}
					if(length>0){
					for(int j=length; j>0; j--){
						if(x==snake[j].getX() && y==snake[j].getY()){
							this.stop();
							Label gLabel=new Label("CANNIBAL!");
							gLabel.setLayoutX(100);
							gLabel.setLayoutY(100);			
							root.setCenter(gLabel);
						}
						}
					}
					
		
				}	
				
			}.start();
			
			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
