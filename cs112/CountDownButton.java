import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CountDownButton extends Application {
	int counter = 3;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Button button = new Button(counter + "");
		StackPane pane = new StackPane();
		pane.getChildren().add(button); // button is a node
		
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setTitle("Count Down Button");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		button.setOnAction(e -> {
			counter--;
			button.setText(counter + "");
			if (counter == 0) {
				System.exit(0);
			}
		});
	}

}
