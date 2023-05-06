import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

 

public class Chomp extends Application {
	
   static public final int MAX_SIZE = 9, IMAGE_SIZE = 50;
   static final String PATH = "http://cs.gettysburg.edu/~tneller/cs112/chomp/images/";
   static final Image COOKIE_IMAGE = new Image(PATH + "cookie.png");
   static final Image SKULL_IMAGE = new Image(PATH + "cookie-skull.png");
   static final Image BLACK_IMAGE = new Image(PATH + "black.png");
   ChompButton[][] buttons = new ChompButton[MAX_SIZE][MAX_SIZE];

   @Override
   public void start(Stage primaryStage) throws Exception {
       // Create a GridPane with Hgap and Vgap of 1 pixel.
	   GridPane gridPane = new GridPane();
	   gridPane.setVgap(1);
	   gridPane.setHgap(1);
	   
       // Populate the 9x9 grid with ChompButton objects that are constructed
       //   with knowledge of their row and column indices, and
       //   call a chomp(ActionEvent e) method when clicked.
	   for (int row = 0; row < 9; row++) {
		   for (int col = 0; col < 9; col++) {
			   ChompButton button = new ChompButton(row, col);
			   gridPane.add(button, col, row);
			   buttons[row][col] = button;
			   
			   button.setOnAction(e -> {
				   chomp(e, button);
			   });
		   }
	   }
       // Create a scene with the GridPane.
	   Scene scene = new Scene(gridPane, 460, 460);
	   
       // Set the primary stage to have that scene, sized to the scene,
       // be non-resizable, centered on the screen with title "Chomp", and show it.
	   primaryStage.setTitle("Chomp");
	   primaryStage.setScene(scene);
	   primaryStage.setResizable(false);
	   primaryStage.show();
   }

  

   void chomp(ActionEvent e, ChompButton button) {
	   int row = button.row;
	   int col = button.col;
	   
	   
       // If the game is over (button at [0][0] is chomped, reset all buttons
       // in the grid rectangle from [0][0] to the source button to be unchomped.
	   if (this.isGameOver()) {
		   for (int i = 0; i <= row; i++) {
			   for (int j = 0; j <= col; j++) {
				   buttons[i][j].setChomped(false);
			   }
		   }
	   }
	   
	   // Else chomp all buttons in the grid rectangle from the source button to [8][8].
	   else {
		   for (int i = row; i < 9; i++) {
			   for (int j = col; j < 9; j++) {
				   buttons[i][j].setChomped(true);
			   }
		   }
	   }
	 
   }
   
   void reset() {
	   for (int row = 0; row < 9; row++) {
		   for (int col = 0; col < 9; col++) {
			   buttons[row][col].setChomped(true);
		   }
	   }
   }
   
   boolean isGameOver() {
	   for (int row = 0; row < 9; row++) {
		   for (int col = 0; col < 9; col++) {
			   if (!buttons[row][col].isChomped) {
				   return false;
			   }
		   }
	   }
	   return true;
   }

   class ChompButton extends Button {
	   boolean isChomped = true;
	   int row, col;

	   public ChompButton(int row, int col) {
		   // Store the row, col, and set both preferred and minimum size to the
		   // image size.
		   super();
		   this.row = row; 
		   this.col = col;

		   ImageView temp = new ImageView(BLACK_IMAGE);
		   temp.setFitHeight(50);
		   temp.setFitWidth(50);
		   this.setGraphic(temp);

		   this.setPadding(new Insets(0));
	   }

	   public void setChomped(boolean isChomped) {

		   // Store the new button state, and set the graphic of the button to be an
		   //   ImageView with the appropriate image for the state and [row][col].
		   //   (Remember that [0][0] has a special unchomped image.)

		   this.isChomped = isChomped;

		   if (!isChomped) {
			   if (row == 0 && col == 0) {
				   ImageView temp = new ImageView(SKULL_IMAGE);
				   temp.setFitHeight(50);
				   temp.setFitWidth(50);
				   this.setGraphic(temp);
			   } else {
				   ImageView temp = new ImageView(COOKIE_IMAGE);
				   temp.setFitHeight(50);
				   temp.setFitWidth(50);
				   this.setGraphic(temp);
			   }
		   } else {
			   ImageView temp = new ImageView(BLACK_IMAGE);
			   temp.setFitHeight(50);
			   temp.setFitWidth(50);
			   this.setGraphic(temp);
		   }
	   }
   }

   public static void main(String[] args) {
	   launch(args);
   }
}

 