import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class FlashText extends Application {
	private String text = "";
	
	@Override
	public void start(Stage primaryStage) {
		StackPane pane = new StackPane();
		Label lblText = new Label("Programming is fun");
		pane.getChildren().add(lblText);
		
		new Thread(() -> {
			try {
				while (true) {
					if (lblText.getText().trim().length() == 0) {
						text = "Welcome";
					} else {
						text = "";
					}
					
					Platform.runLater(() -> {
						lblText.setText(text);
					});
					
					Thread.sleep(800);
				}
			} catch (Exception ex) {
			}
		}).start();
	
		Scene scene = new Scene(pane, 200, 50);
		primaryStage.setTitle("FlashText");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
