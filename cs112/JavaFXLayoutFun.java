
import java.io.File;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXLayoutFun extends Application {

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		// Load images
		String path = "/Accounts/turing/faculty/tneller/public_html/cs112/examples/images/";
		String[] imageFilenames = {"green-club-100.png", "pink-heart-100.png", "black-spade-100.png", "blue-diamond-100.png", "die1.png", "die2.png", "die3.png", "die4.png", "die5.png", "die6.png", "hold.png", "roll.png"}; 
		int numImages = imageFilenames.length;
		Image[] images = new Image[numImages];
		ImageView[] imageViews = new ImageView[numImages];
		for (int i = 0; i < numImages; i++) {
			images[i] = new Image(new File(path + imageFilenames[i]).toURI().toString());
			imageViews[i] = new ImageView(images[i]);
		}
		
		// 2. Add HBox layout to top of border layout
		HBox topPane = new HBox();
		topPane.getChildren().add(imageViews[0]);
		topPane.getChildren().add(imageViews[1]);
		
		// 3. Add VBox layout to left of border layout
		VBox leftPane = new VBox();
		leftPane.getChildren().add(imageViews[8]);
		leftPane.getChildren().add(imageViews[9]);
		
		// 4. Add Flow layout to bottom of border layout
		FlowPane bottomPane = new FlowPane();
		for (Image image : images)
			bottomPane.getChildren().add(new ImageView(image)); 
		// try it without duplication and note that an imageview can't be added to multiple panes
		
		// 5. Add Stack layout to right of border layout
		StackPane rightPane = new StackPane();
		for (Image image : images)
			rightPane.getChildren().add(new ImageView(image));
		
		// 6. Add Grid layout to center of border layout
		GridPane centerPane = new GridPane();
		centerPane.setAlignment(Pos.CENTER);
		centerPane.setPadding(new Insets(10, 5, 10, 5));
		centerPane.setHgap(5);
		centerPane.setVgap(5);
		int numCols = 3;
		for (int i = 0; i < numImages; i++) {
			ImageView view = new ImageView(images[i]);
			centerPane.add(view, i % numCols, i / numCols);
			GridPane.setHalignment(view, HPos.RIGHT);
			GridPane.setValignment(view, VPos.CENTER);
		}
		
		// 1. Border Pane layout at root 	
		BorderPane rootPane = new BorderPane(); // Create a new pane. 
		rootPane.setTop(topPane);
		rootPane.setLeft(leftPane);
		rootPane.setBottom(bottomPane);
		rootPane.setRight(rightPane);
		rootPane.setCenter(centerPane);
		
		// 7. Center alignment of panes
		topPane.setAlignment(Pos.TOP_CENTER);
		leftPane.setAlignment(Pos.CENTER_LEFT);
		bottomPane.setAlignment(Pos.BOTTOM_CENTER);
		
		Scene scene = new Scene(rootPane, 1000, 800); // Create a scene with the pane
		primaryStage.setTitle("Layout Demo"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}

}
